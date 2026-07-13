// (C) 2025 uchicom
package com.uchicom.kame.dto.request.project;

import com.uchicom.kame.annotation.Form;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Form("projectRegister")
public class ProjectRegisterDto {
  @NotNull(message = "必須です。")
  public Long customerId;

  public LocalDate startScheduleDate;

  public LocalDate endScheduleDate;

  public LocalDate startDate;

  public LocalDate endDate;

  @NotBlank(message = "必須です。")
  public String subject;

  public String description;
}
