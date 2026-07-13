// (C) 2025 uchicom
package com.uchicom.kame.factory.di;

import com.uchicom.kame.dao.AccountDao;
import com.uchicom.kame.dao.ApplicationDao;
import com.uchicom.kame.dao.CustomerDao;
import com.uchicom.kame.dao.FileDao;
import com.uchicom.kame.dao.ProjectDao;
import com.uchicom.kame.dao.RefreshTokenDao;
import com.uchicom.kame.dao.TaskDao;
import com.uchicom.kame.dao.helper.DbHelper;
import com.uchicom.kame.entity.AbstractTable;
import com.uchicom.kame.entity.Account;
import com.uchicom.kame.entity.Application;
import com.uchicom.kame.entity.Customer;
import com.uchicom.kame.entity.File;
import com.uchicom.kame.entity.Project;
import com.uchicom.kame.entity.RefreshToken;
import com.uchicom.kame.entity.Task;

public class DaoFactory {

  static <T extends AbstractTable> DbHelper<T> helper(T entity) {
    return new DbHelper<>(entity);
  }

  static AccountDao accountDao() {
    return new AccountDao(helper(new Account()));
  }

  static ApplicationDao applicationDao() {
    return new ApplicationDao(helper(new Application()));
  }

  static FileDao fileDao() {
    return new FileDao(helper(new File()));
  }

  static ProjectDao projectDao() {
    return new ProjectDao(helper(new Project()));
  }

  static CustomerDao customerDao() {
    return new CustomerDao(helper(new Customer()));
  }

  static RefreshTokenDao refreshTokenDao() {
    return new RefreshTokenDao(helper(new RefreshToken()));
  }

  static TaskDao taskDao() {
    return new TaskDao(helper(new Task()));
  }
}
