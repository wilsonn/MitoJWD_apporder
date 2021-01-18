package com.marketmito.apporder.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.marketmito.apporder.entity.Customer;

@Repository
public interface CustomerRepository extends 
			PagingAndSortingRepository<Customer, Long> {

	List<Customer> findByDni(String dni);
	
}
