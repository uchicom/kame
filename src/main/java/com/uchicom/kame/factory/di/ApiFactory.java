// (C) 2025 uchicom
package com.uchicom.kame.factory.di;

import com.uchicom.kame.api.AccountApi;
import com.uchicom.kame.api.CustomerApi;
import com.uchicom.kame.api.ProjectApi;
import com.uchicom.kame.api.TaskApi;

public class ApiFactory {

  static AccountApi accountApi() {
    return new AccountApi(ServiceFactory.accountService());
  }

  static CustomerApi customerApi() {
    return new CustomerApi(ServiceFactory.customerService());
  }

  static ProjectApi projectApi() {
    return new ProjectApi(ServiceFactory.projectService());
  }

  static TaskApi taskApi() {
    return new TaskApi(ServiceFactory.taskService());
  }
}
