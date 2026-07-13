// (C) 2026 uchicom
package com.uchicom.kame.dao;

import com.uchicom.kame.dao.helper.DbHelper;
import com.uchicom.kame.entity.Application;

/**
 * {@link Application}アプリケーションリポジトリ.
 *
 * @author uchicom
 */
public class ApplicationDao extends AbstractDao<Application> {

  /** コンストラクタ. */
  public ApplicationDao(DbHelper<Application> helper) {
    super(helper);
  }

  public Application findByApiKey(String apiKey) {
    var application = new Application();
    return helper.from(application).where(application.api_key).is(apiKey).selectFirst();
  }
}
