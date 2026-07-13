// (C) 2026 uchicom
package com.uchicom.kame.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

import com.uchicom.kame.AbstractTest;
import com.uchicom.kame.Constants;
import com.uchicom.kame.dto.request.file.FileDownloadDto;
import com.uchicom.kame.dto.request.file.FileUploadDto;
import com.uchicom.kame.dto.response.ErrorDto;
import com.uchicom.kame.dto.response.MessageDto;
import com.uchicom.kame.dto.response.file.FileDto;
import com.uchicom.kame.entity.Application;
import com.uchicom.kame.entity.File;
import com.uchicom.kame.service.ApplicationAuthService;
import com.uchicom.kame.service.FileService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

@Tag("api")
public class FileApiTest extends AbstractTest {
  @Mock HttpServletRequest req;
  @Mock HttpServletResponse res;

  @Mock FileService fileService;
  @Mock ApplicationAuthService applicationAuthService;

  @Captor ArgumentCaptor<FileUploadDto> fileUploadDtoCaptor;
  @Captor ArgumentCaptor<Long> applicationIdCaptor;

  @Spy @InjectMocks FileApi api;

  @BeforeEach
  public void setUp() {
    createApiMock(api);
  }

  @Test
  public void upload() throws Exception {
    // mock
    doReturn("app-key").when(req).getHeader(Constants.APPLICATION_KEY_HEADER);
    var application = new Application();
    application.id = 1L;
    doReturn(application).when(applicationAuthService).auth(req);
    doNothing()
        .when(fileService)
        .register(applicationIdCaptor.capture(), fileUploadDtoCaptor.capture());

    var dto = new FileUploadDto();
    dto.path = "/hoge/a.txt";
    dto.dataUrl = "data:text/plain;base64,aGVsbG8=";

    // test
    var result = api.upload(dto, req, res);

    // assert
    if (result instanceof MessageDto messageDto) {
      assertThat(messageDto.message).isEqualTo("Registered");
    } else {
      fail();
    }
    assertThat(applicationIdCaptor.getValue()).isEqualTo(1L);
    assertThat(fileUploadDtoCaptor.getValue()).isEqualTo(dto);
  }

  @Test
  public void upload_authFailed() throws Exception {
    // mock
    doReturn("app-key").when(req).getHeader(Constants.APPLICATION_KEY_HEADER);
    doReturn(null).when(applicationAuthService).auth(req);

    var dto = new FileUploadDto();
    dto.path = "/hoge/a.txt";
    dto.dataUrl = "data:text/plain;base64,aGVsbG8=";

    // test
    var result = api.upload(dto, req, res);

    // assert
    if (result instanceof ErrorDto errorDto) {
      assertThat(errorDto.errorMessage).isEqualTo("認証エラー");
    } else {
      fail();
    }
  }

  @Test
  public void download() throws Exception {
    // mock
    doReturn("app-key").when(req).getHeader(Constants.APPLICATION_KEY_HEADER);
    var application = new Application();
    application.id = 1L;
    doReturn(application).when(applicationAuthService).auth(req);
    var file = new File();
    file.content_type = "text/plain";
    file.data = "hello".getBytes();
    doReturn(file).when(fileService).get(1L, "/hoge/a.txt");
    doReturn("data:text/plain;base64,aGVsbG8=").when(fileService).toDataUrl(file);

    var dto = new FileDownloadDto();
    dto.path = "/hoge/a.txt";

    // test
    var result = api.download(dto, req, res);

    // assert
    if (result instanceof FileDto fileDto) {
      assertThat(fileDto.path).isEqualTo("/hoge/a.txt");
      assertThat(fileDto.dataUrl).isEqualTo("data:text/plain;base64,aGVsbG8=");
    } else {
      fail();
    }
  }

  @Test
  public void download_authFailed() throws Exception {
    // mock
    doReturn("app-key").when(req).getHeader(Constants.APPLICATION_KEY_HEADER);
    doReturn(null).when(applicationAuthService).auth(req);

    var dto = new FileDownloadDto();
    dto.path = "/hoge/a.txt";

    // test
    var result = api.download(dto, req, res);

    // assert
    if (result instanceof ErrorDto errorDto) {
      assertThat(errorDto.errorMessage).isEqualTo("認証エラー");
    } else {
      fail();
    }
  }

  @Test
  public void download_notFound() throws Exception {
    // mock
    doReturn("app-key").when(req).getHeader(Constants.APPLICATION_KEY_HEADER);
    var application = new Application();
    application.id = 1L;
    doReturn(application).when(applicationAuthService).auth(req);
    doReturn(null).when(fileService).get(1L, "/hoge/a.txt");

    var dto = new FileDownloadDto();
    dto.path = "/hoge/a.txt";

    // test
    var result = api.download(dto, req, res);

    // assert
    if (result instanceof ErrorDto errorDto) {
      assertThat(errorDto.errorMessage).isEqualTo("ファイルが見つかりません。");
    } else {
      fail();
    }
  }
}
