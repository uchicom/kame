// (C) 2026 uchicom
package com.uchicom.kame.entity;

import com.iciql.Iciql.IQColumn;
import com.iciql.Iciql.IQContraintUnique;
import com.iciql.Iciql.IQSchema;
import com.iciql.Iciql.IQTable;

/** アプリケーション. */
@IQContraintUnique(uniqueColumns = {"api_key"})
@IQSchema("kame")
@IQTable(name = "application", inheritColumns = true)
public class Application extends AbstractTable {

  /** アプリケーション名. */
  @IQColumn(length = 128, nullable = false)
  public String name;

  /** APIキー. */
  @IQColumn(length = 64, nullable = false)
  public String api_key;

  /** APIシークレット. */
  @IQColumn(length = 128, nullable = false)
  public byte[] api_secret;
}
