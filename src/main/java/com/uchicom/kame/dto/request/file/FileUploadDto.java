// (C) 2026 uchicom
package com.uchicom.kame.dto.request.file;

import com.uchicom.kame.annotation.Form;
import jakarta.validation.constraints.NotBlank;

@Form("fileUpload")
public class FileUploadDto {
  @NotBlank(message = "必須です。")
  public String path;

  @NotBlank(message = "必須です。")
  public String dataUrl;
}
