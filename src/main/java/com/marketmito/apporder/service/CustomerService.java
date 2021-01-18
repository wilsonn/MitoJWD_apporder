package com.marketmito.apporder.service;

import java.util.List;

import com.marketmito.apporder.entity.Customer;

public interface CustomerService extends CrudService<Customer> {
	List<Customer> getCustomerByDni(String dni);
}
