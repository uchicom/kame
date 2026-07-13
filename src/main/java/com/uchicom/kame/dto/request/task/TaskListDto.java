// (C) 2025 uchicom
package com.uchicom.kame.dto.request.task;

import com.uchicom.kame.annotation.Form;
import jakarta.validation.constraints.NotNull;

@Form("taskList")
public class TaskListDto {
  @NotNull(message = "必須です。")
  public Long projectId;
}
