// (C) 2023 uchicom
package com.uchicom.kame.dto.response.account;

import com.uchicom.kame.Constants;
import com.uchicom.kame.enumeration.ApiResult;

public class ResultDto {
  public ApiResult result;
  public Integer refreshIntervalMinutes = Constants.REFRESH_INTERVAL_MINUTES;

  public ResultDto(ApiResult result) {
    this.result = result;
  }
}
