// (C) 2025 uchicom
package com.uchicom.kame.dao;

import com.uchicom.kame.dao.helper.DbHelper;
import com.uchicom.kame.entity.TaskToAccount;
import java.util.List;

public class TaskToAccountDao extends AbstractDao<TaskToAccount> {

  /** コンストラクタ. */
  public TaskToAccountDao(DbHelper<TaskToAccount> helper) {
    super(helper);
  }

  public List<TaskToAccount> findByTaskId(long taskId) {
    var taskToAccount = new TaskToAccount();
    return helper.from(taskToAccount).where(taskToAccount.task_id).is(taskId).select();
  }

  public List<TaskToAccount> findByAccountId(long accountId) {
    var taskToAccount = new TaskToAccount();
    return helper.from(taskToAccount).where(taskToAccount.account_id).is(accountId).select();
  }
}
