// (C) 2026 uchicom
package com.uchicom.kame.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

import com.uchicom.kame.AbstractTest;
import com.uchicom.kame.dao.FileDao;
import com.uchicom.kame.dto.request.file.FileUploadDto;
import com.uchicom.kame.entity.File;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

/**
 * {@link FileService}のテストケース.
 *
 * @author uchicom
 */
@Tag("service")
public class FileServiceTest extends AbstractTest {

  @Mock FileDao fileDao;

  @Captor ArgumentCaptor<File> fileCaptor;

  @Spy @InjectMocks FileService service;

  @Test
  public void register_insert() {
    doReturn(null).when(fileDao).findByApplicationIdAndPath(1L, "/hoge/a.txt");
    doReturn(true).when(fileDao).insert(fileCaptor.capture());

    var dto = new FileUploadDto();
    dto.path = "/hoge/a.txt";
    dto.dataUrl =
        "data:text/plain;base64,"
            + Base64.getEncoder().encodeToString("hello".getBytes(StandardCharsets.UTF_8));

    // test
    service.register(1L, dto);

    // assert
    var file = fileCaptor.getValue();
    assertThat(file.application_id).isEqualTo(1L);
    assertThat(file.path).isEqualTo("/hoge/a.txt");
    assertThat(file.content_type).isEqualTo("text/plain");
    assertThat(file.data).isEqualTo("hello".getBytes(StandardCharsets.UTF_8));
  }

  @Test
  public void register_update() {
    var file = new File();
    file.id = 1L;
    file.application_id = 1L;
    file.path = "/hoge/a.txt";
    doReturn(file).when(fileDao).findByApplicationIdAndPath(1L, "/hoge/a.txt");
    doReturn(true).when(fileDao).update(fileCaptor.capture());

    var dto = new FileUploadDto();
    dto.path = "/hoge/a.txt";
    dto.dataUrl =
        "data:text/plain;base64,"
            + Base64.getEncoder().encodeToString("updated".getBytes(StandardCharsets.UTF_8));

    // test
    service.register(1L, dto);

    // assert
    assertThat(fileCaptor.getValue()).isEqualTo(file);
    assertThat(file.content_type).isEqualTo("text/plain");
    assertThat(file.data).isEqualTo("updated".getBytes(StandardCharsets.UTF_8));
  }

  @Test
  public void getContentType() {
    // test
    var result = service.getContentType("data:image/png;base64,aGVsbG8=");

    // assert
    assertThat(result).isEqualTo("image/png");
  }

  @Test
  public void getContentType_notDataUrl() {
    // test
    var result = service.getContentType("aGVsbG8=");

    // assert
    assertThat(result).isNull();
  }

  @Test
  public void getData() {
    // test
    var result = service.getData("data:text/plain;base64,aGVsbG8=");

    // assert
    assertThat(result).isEqualTo("hello".getBytes(StandardCharsets.UTF_8));
  }

  @Test
  public void getData_notDataUrl() {
    // test
    var result = service.getData("aGVsbG8=");

    // assert
    assertThat(result).isEqualTo("hello".getBytes(StandardCharsets.UTF_8));
  }
}
