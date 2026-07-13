// (C) 2025 uchicom
package com.uchicom.kame.dto.request.customer;

import com.uchicom.kame.annotation.Form;
import jakarta.validation.constraints.NotNull;

@Form("customerUpdate")
public class CustomerUpdateDto extends CustomerRegisterDto {
  @NotNull(message = "必須です。")
  public Long customerId;
}
