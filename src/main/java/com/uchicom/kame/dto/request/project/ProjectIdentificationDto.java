// (C) 2025 uchicom
package com.uchicom.kame.dto.request.project;

import com.uchicom.kame.annotation.Form;
import jakarta.validation.constraints.NotNull;

@Form("projectIdentification")
public class ProjectIdentificationDto {
  @NotNull(message = "必須です。")
  public Long projectId;
}
