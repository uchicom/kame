// (C) 2026 uchicom
package com.uchicom.kame.api;

import com.uchicom.kame.Constants;
import com.uchicom.kame.annotation.Path;
import com.uchicom.kame.dto.request.file.FileDownloadDto;
import com.uchicom.kame.dto.request.file.FileUploadDto;
import com.uchicom.kame.dto.response.ErrorDto;
import com.uchicom.kame.dto.response.MessageDto;
import com.uchicom.kame.dto.response.file.FileDto;
import com.uchicom.kame.service.ApplicationAuthService;
import com.uchicom.kame.service.FileService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Path("/file")
public class FileApi extends AbstractApi {

  private final FileService fileService;
  private final ApplicationAuthService applicationAuthService;

  public FileApi(FileService fileService, ApplicationAuthService applicationAuthService) {
    this.fileService = fileService;
    this.applicationAuthService = applicationAuthService;
  }

  @Path("/upload")
  public Object upload(FileUploadDto dto, HttpServletRequest req, HttpServletResponse res) {
    return transExecutor(
        req.getHeader(Constants.APPLICATION_KEY_HEADER),
        () -> {
          var application = applicationAuthService.auth(req);
          if (application == null) {
            return new ErrorDto("認証エラー");
          }
          fileService.register(application.id, dto);
          return new MessageDto("Registered");
        });
  }

  @Path("/download")
  public Object download(FileDownloadDto dto, HttpServletRequest req, HttpServletResponse res) {
    return transExecutor(
        req.getHeader(Constants.APPLICATION_KEY_HEADER),
        () -> {
          var application = applicationAuthService.auth(req);
          if (application == null) {
            return new ErrorDto("認証エラー");
          }
          var file = fileService.get(application.id, dto.path);
          if (file == null) {
            return new ErrorDto("ファイルが見つかりません。");
          }
          return new FileDto(dto.path, fileService.toDataUrl(file));
        });
  }
}
