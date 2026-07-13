// (C) 2026 uchicom
package com.uchicom.kame.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

import com.uchicom.kame.AbstractTest;
import com.uchicom.kame.Constants;
import com.uchicom.kame.dao.ApplicationDao;
import com.uchicom.kame.entity.Application;
import jakarta.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

/**
 * {@link ApplicationAuthService}のテストケース.
 *
 * @author uchicom
 */
@Tag("service")
public class ApplicationAuthServiceTest extends AbstractTest {

  @Mock ApplicationDao applicationDao;
  @Mock HttpServletRequest req;

  @Spy @InjectMocks ApplicationAuthService service;

  @Test
  public void auth_noApiKey() throws Exception {
    doReturn(null).when(req).getHeader(Constants.APPLICATION_KEY_HEADER);
    doReturn("token").when(req).getHeader(Constants.TOKEN_HEADER);

    // test
    var result = service.auth(req);

    // assert
    assertThat(result).isNull();
  }

  @Test
  public void auth_noToken() throws Exception {
    doReturn("app-key").when(req).getHeader(Constants.APPLICATION_KEY_HEADER);
    doReturn(null).when(req).getHeader(Constants.TOKEN_HEADER);

    // test
    var result = service.auth(req);

    // assert
    assertThat(result).isNull();
  }

  @Test
  public void auth_applicationNotFound() throws Exception {
    doReturn("app-key").when(req).getHeader(Constants.APPLICATION_KEY_HEADER);
    doReturn("token").when(req).getHeader(Constants.TOKEN_HEADER);
    doReturn(null).when(applicationDao).findByApiKey("app-key");

    // test
    var result = service.auth(req);

    // assert
    assertThat(result).isNull();
  }

  @Test
  public void auth_invalidToken() throws Exception {
    doReturn("app-key").when(req).getHeader(Constants.APPLICATION_KEY_HEADER);
    doReturn("invalid-token").when(req).getHeader(Constants.TOKEN_HEADER);
    var application = new Application();
    application.api_secret = "secret".getBytes(StandardCharsets.UTF_8);
    doReturn(application).when(applicationDao).findByApiKey("app-key");

    // test
    var result = service.auth(req);

    // assert
    assertThat(result).isNull();
  }

  @Test
  public void auth_success() throws Exception {
    var application = new Application();
    application.id = 1L;
    application.api_secret = "secret".getBytes(StandardCharsets.UTF_8);
    doReturn("app-key").when(req).getHeader(Constants.APPLICATION_KEY_HEADER);
    doReturn(application).when(applicationDao).findByApiKey("app-key");
    var token =
        Base64.getUrlEncoder()
            .withoutPadding()
            .encodeToString(service.generateToken("app-key", application.api_secret));
    doReturn(token).when(req).getHeader(Constants.TOKEN_HEADER);

    // test
    var result = service.auth(req);

    // assert
    assertThat(result).isEqualTo(application);
  }

  @Test
  public void generateToken_deterministic() throws Exception {
    var secret = "secret".getBytes(StandardCharsets.UTF_8);

    // test
    var result1 = service.generateToken("app-key", secret);
    var result2 = service.generateToken("app-key", secret);

    // assert
    assertThat(result1).isEqualTo(result2);
  }

  @Test
  public void verifyToken_malformedToken() throws Exception {
    // test
    var result =
        service.verifyToken(
            "app-key", "secret".getBytes(StandardCharsets.UTF_8), "not base64 url!!");

    // assert
    assertThat(result).isFalse();
  }
}
