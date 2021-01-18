package com.marketmito.apporder.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.marketmito.apporder.entity.Product;

@Repository
public interface ProductRepository  extends JpaRepository<Product, Long>{

	@Query("SELECT p FROM Product p WHERE p.name LIKE %?1%")//JPQL
	List<Product> findByNameContaint(String term);
	
	Product findByName(String name); //KeyWords
}
