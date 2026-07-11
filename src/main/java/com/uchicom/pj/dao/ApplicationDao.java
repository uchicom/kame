// (C) 2026 uchicom
package com.uchicom.pj.dao;

import com.uchicom.pj.dao.helper.DbHelper;
import com.uchicom.pj.entity.Application;

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
