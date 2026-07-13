// (C) 2025 uchicom
package com.uchicom.kame.entity;

import com.iciql.Iciql.IQColumn;
import com.iciql.Iciql.IQSchema;
import com.iciql.Iciql.IQTable;

/** タスクとアカウントの紐付け. */
@IQSchema("kame")
@IQTable(name = "task_to_account", inheritColumns = true)
public class TaskToAccount extends AbstractTable {

  /** タスクID. */
  @IQColumn(nullable = false)
  public Long task_id;

  /** アカウントID. */
  @IQColumn(nullable = false)
  public Long account_id;
}
