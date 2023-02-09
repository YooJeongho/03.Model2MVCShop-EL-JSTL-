package com.model2.mvc.service.product.impl;

import java.util.HashMap;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.dao.ProductDAO;
import com.model2.mvc.service.domain.Product;

public class ProductServiceImpl implements ProductService {
	
	// field
	private ProductDAO dao;		//setter injection
	
	public ProductServiceImpl() {		// Constrouctor Injection
		dao = new ProductDAO();
	}

	@Override
	public void addProduct(Product productVO) throws Exception {
		dao.insertProduct(productVO);
	}

	@Override
	public Product getProduct(int prodNO) throws Exception {
		return dao.findProduct(prodNO);
	}

	@Override
	public Map<String, Object> getProductList(Search searchVO) throws Exception {
		return dao.getProductList(searchVO);
	}

	@Override
	public void updateProduct(Product productVO) throws Exception {
		dao.updateProduct(productVO);
	}

}
