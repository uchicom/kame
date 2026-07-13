// (C) 2025 uchicom
package com.uchicom.kame.service;

import com.uchicom.kame.dao.CustomerDao;
import com.uchicom.kame.dto.request.customer.CustomerRegisterDto;
import com.uchicom.kame.dto.request.customer.CustomerUpdateDto;
import com.uchicom.kame.entity.Customer;
import java.util.List;

public class CustomerService {
  private final CustomerDao customerDao;

  public CustomerService(CustomerDao customerDao) {
    this.customerDao = customerDao;
  }

  public List<Customer> getList() {
    return customerDao.findAll();
  }

  public void register(CustomerRegisterDto dto) {
    var customer = new Customer();
    customer.company_name = dto.companyName;
    customer.pic_name = dto.picName;
    customer.email_address = dto.emailAddress;
    customer.telephon_number = dto.telephonNumber;
    customer.fax_number = dto.faxNumber;
    customer.address = dto.address;
    customer.building = dto.building;
    customerDao.insert(customer);
  }

  public void update(List<CustomerUpdateDto> dtoList) {
    for (var dto : dtoList) {
      var customer = customerDao.findById(dto.customerId);
      if (customer == null) continue;
      customer.company_name = dto.companyName;
      customer.pic_name = dto.picName;
      customer.email_address = dto.emailAddress;
      customer.telephon_number = dto.telephonNumber;
      customer.fax_number = dto.faxNumber;
      customer.address = dto.address;
      customer.building = dto.building;
      customerDao.update(customer);
    }
  }
}
