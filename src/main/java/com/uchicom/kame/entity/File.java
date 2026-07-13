// (C) 2026 uchicom
package com.uchicom.kame.entity;

import com.iciql.Iciql.IQColumn;
import com.iciql.Iciql.IQContraintUnique;
import com.iciql.Iciql.IQSchema;
import com.iciql.Iciql.IQTable;

/** ファイル. */
@IQContraintUnique(uniqueColumns = {"application_id", "path"})
@IQSchema("kame")
@IQTable(name = "file", inheritColumns = true)
public class File extends AbstractTable {

  /** アプリケーションID. */
  @IQColumn(nullable = false)
  public Long application_id;

  /** パス. */
  @IQColumn(length = 512, nullable = false)
  public String path;

  /** コンテンツタイプ. */
  @IQColumn(length = 128)
  public String content_type;

  /** データ. */
  @IQColumn(nullable = false)
  public byte[] data;
}
