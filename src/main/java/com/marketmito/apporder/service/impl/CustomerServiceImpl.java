package com.marketmito.apporder.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marketmito.apporder.entity.Customer;
import com.marketmito.apporder.repository.CustomerRepository;
import com.marketmito.apporder.service.CustomerService;

//https://www.baeldung.com/transaction-configuration-with-jpa-and-spring

@Service
public class CustomerServiceImpl implements CustomerService{
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Transactional(readOnly = true)
	@Override
	public List<Customer> getAll() throws Exception {
		return (List<Customer>)customerRepository.findAll();
	}

	@Transactional(readOnly = true)
	@Override
	public Page<Customer> getAll(Pageable pageable) throws Exception {
		return customerRepository.findAll(pageable);
	}

	@Transactional
	@Override
	public Customer saveOrUpdate(Customer entity) throws Exception {
		return customerRepository.save(entity);
	}

	@Transactional(readOnly = true)
	@Override
	public Optional<Customer> getOne(Long id) throws Exception {
		return customerRepository.findById(id);
	}

	@Transactional
	@Override
	public void deleteById(Long id) throws Exception {
		customerRepository.deleteById(id);
		
	}

	@Transactional(readOnly = true)
	@Override
	public List<Customer> getCustomerByDni(String dni) {
		return customerRepository.findByDni(dni);
	}

}
