// (C) 2026 uchicom
package com.uchicom.kame.service;

import com.uchicom.kame.Constants;
import com.uchicom.kame.dao.ApplicationDao;
import com.uchicom.kame.entity.Application;
import com.uchicom.kame.util.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

/**
 * アプリケーションキー・トークンによる認可サービス.
 *
 * @author uchicom
 */
public class ApplicationAuthService {

  private final ApplicationDao applicationDao;

  public ApplicationAuthService(ApplicationDao applicationDao) {
    this.applicationDao = applicationDao;
  }

  /**
   * ヘッダーのアプリケーションキーとトークンを検証します.
   *
   * @param req リクエスト
   * @return 検証OKの場合はアプリケーション,それ以外はnullを返します
   */
  public Application auth(HttpServletRequest req) throws NoSuchAlgorithmException {
    var apiKey = req.getHeader(Constants.APPLICATION_KEY_HEADER);
    var token = req.getHeader(Constants.TOKEN_HEADER);
    if (apiKey == null || token == null) {
      return null;
    }
    var application = applicationDao.findByApiKey(apiKey);
    if (application == null) {
      return null;
    }
    if (!verifyToken(apiKey, application.api_secret, token)) {
      return null;
    }
    return application;
  }

  boolean verifyToken(String apiKey, byte[] secret, String token) throws NoSuchAlgorithmException {
    byte[] actual;
    try {
      actual = Base64.getUrlDecoder().decode(token);
    } catch (IllegalArgumentException e) {
      return false;
    }
    return Arrays.equals(generateToken(apiKey, secret), actual);
  }

  byte[] generateToken(String apiKey, byte[] secret) throws NoSuchAlgorithmException {
    var salt = Base64.getUrlEncoder().withoutPadding().encodeToString(secret);
    return SecurityUtil.getHash(apiKey, salt);
  }
}
