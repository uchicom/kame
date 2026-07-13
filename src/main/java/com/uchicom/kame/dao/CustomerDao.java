// (C) 2025 uchicom
package com.uchicom.kame.dao;

import com.uchicom.kame.dao.helper.DbHelper;
import com.uchicom.kame.entity.Customer;

public class CustomerDao extends AbstractDao<Customer> {

  /** コンストラクタ. */
  public CustomerDao(DbHelper<Customer> helper) {
    super(helper);
  }
}
