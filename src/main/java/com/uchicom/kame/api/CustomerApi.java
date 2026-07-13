// (C) 2025 uchicom
package com.uchicom.kame.api;

import com.uchicom.kame.annotation.Auth;
import com.uchicom.kame.annotation.Path;
import com.uchicom.kame.dto.request.customer.CustomerRegisterDto;
import com.uchicom.kame.dto.request.customer.CustomerUpdateDto;
import com.uchicom.kame.dto.response.ListDto;
import com.uchicom.kame.dto.response.MessageDto;
import com.uchicom.kame.entity.Customer;
import com.uchicom.kame.service.CustomerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

@Path("/customer")
public class CustomerApi extends AbstractApi {

  private final CustomerService customerService;

  public CustomerApi(CustomerService customerService) {
    this.customerService = customerService;
  }

  @Auth
  @Path("/list")
  public Object list(HttpServletRequest req, HttpServletResponse res) {
    return refer(
        () -> {
          return new ListDto<Customer>(customerService.getList());
        });
  }

  @Auth
  @Path("/register")
  public Object register(CustomerRegisterDto dto, HttpServletRequest req, HttpServletResponse res) {
    return trans(
        req,
        () -> {
          customerService.register(dto);
          return new MessageDto("顧客を登録しました。");
        });
  }

  @Auth
  @Path("/update")
  public Object update(
      List<CustomerUpdateDto> dtoList, HttpServletRequest req, HttpServletResponse res) {
    return trans(
        req,
        () -> {
          customerService.update(dtoList);
          return new MessageDto("顧客を更新しました。");
        });
  }
}
