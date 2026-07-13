// (C) 2025 uchicom
package com.uchicom.kame.factory.di;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.uchicom.kame.service.AccountService;
import com.uchicom.kame.service.ApplicationAuthService;
import com.uchicom.kame.service.AuthService;
import com.uchicom.kame.service.CookieService;
import com.uchicom.kame.service.CustomerService;
import com.uchicom.kame.service.DateTimeService;
import com.uchicom.kame.service.FileService;
import com.uchicom.kame.service.ProjectService;
import com.uchicom.kame.service.RefreshTokenService;
import com.uchicom.kame.service.TaskService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ServiceFactory {

  static AccountService accountService() {
    return new AccountService(
        authService(), cookieService(), refreshTokenService(), DaoFactory.accountDao());
  }

  static AuthService authService() {
    return new AuthService(dateTimeService(), cookieService(), DIFactory.logger());
  }

  static CookieService cookieService() {
    return new CookieService();
  }

  static DateTimeService dateTimeService() {
    return new DateTimeService();
  }

  static ApplicationAuthService applicationAuthService() {
    return new ApplicationAuthService(DaoFactory.applicationDao());
  }

  static CustomerService customerService() {
    return new CustomerService(DaoFactory.customerDao());
  }

  static FileService fileService() {
    return new FileService(DaoFactory.fileDao());
  }

  static ProjectService projectService() {
    return new ProjectService(DaoFactory.projectDao());
  }

  static RefreshTokenService refreshTokenService() {
    return new RefreshTokenService(dateTimeService(), DaoFactory.refreshTokenDao());
  }

  static TaskService taskService() {
    return new TaskService(DaoFactory.taskDao());
  }

  static ObjectMapper objectMapper() {
    ObjectMapper mapper = new ObjectMapper();
    JavaTimeModule javaTimeModule = new JavaTimeModule();
    javaTimeModule.addSerializer(LocalTime.class, LocalTimeSerializer.INSTANCE);
    javaTimeModule.addDeserializer(LocalTime.class, LocalTimeDeserializer.INSTANCE);
    javaTimeModule.addSerializer(LocalDate.class, LocalDateSerializer.INSTANCE);
    javaTimeModule.addDeserializer(LocalDate.class, LocalDateDeserializer.INSTANCE);
    javaTimeModule.addSerializer(LocalDateTime.class, LocalDateTimeSerializer.INSTANCE);
    javaTimeModule.addDeserializer(LocalDateTime.class, LocalDateTimeDeserializer.INSTANCE);
    mapper.registerModule(javaTimeModule);
    return mapper;
  }
}
