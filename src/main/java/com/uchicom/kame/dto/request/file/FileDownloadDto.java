// (C) 2026 uchicom
package com.uchicom.kame.dto.request.file;

import com.uchicom.kame.annotation.Form;
import jakarta.validation.constraints.NotBlank;

@Form("fileDownload")
public class FileDownloadDto {
  @NotBlank(message = "必須です。")
  public String path;
}
