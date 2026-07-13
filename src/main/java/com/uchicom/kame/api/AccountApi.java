// (C) 2022 uchicom
package com.uchicom.kame.api;

import com.uchicom.kame.annotation.Path;
import com.uchicom.kame.dto.request.account.AccountRegisterDto;
import com.uchicom.kame.dto.request.account.LoginDto;
import com.uchicom.kame.dto.response.ErrorDto;
import com.uchicom.kame.dto.response.MessageDto;
import com.uchicom.kame.dto.response.account.ResultDto;
import com.uchicom.kame.enumeration.ApiResult;
import com.uchicom.kame.service.AccountService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Path("/account")
public class AccountApi extends AbstractApi {

  private final AccountService accountService;

  public AccountApi(AccountService accountService) {
    this.accountService = accountService;
  }

  @Path("/register")
  public Object register(AccountRegisterDto dto, HttpServletRequest req, HttpServletResponse res) {
    return trans(
        req,
        () -> {
          var account = accountService.register(dto);
          if (account == null) {
            return new ErrorDto("既に登録済みのログインIDです。");
          }
          return new MessageDto("アカウント登録しました。");
        });
  }

  @Path("/login")
  public Object login(LoginDto dto, HttpServletRequest req, HttpServletResponse res) {
    return trans(
        req,
        () -> {
          if (!accountService.login(dto, res)) {
            return new ErrorDto("認証エラー");
          }
          return new ResultDto(ApiResult.OK);
        });
  }

  @Path("/refresh")
  public Object refresh(HttpServletRequest req, HttpServletResponse res) {
    return trans(
        req,
        () -> {
          if (accountService.refresh(req, res)) {
            return new ResultDto(ApiResult.OK);
          }
          return new ResultDto(ApiResult.NG);
        });
  }

  @Path("/logout")
  public Object logout(HttpServletRequest req, HttpServletResponse res) {
    return trans(
        req,
        () -> {
          accountService.logout(req, res);
          return new ResultDto(ApiResult.OK);
        });
  }
}
