package com.marketmito.apporder.service;

import java.util.List;

import com.marketmito.apporder.entity.Product;

public interface ProductService extends CrudService<Product>{
	List<Product> getProductByName(String term) throws Exception;
}
