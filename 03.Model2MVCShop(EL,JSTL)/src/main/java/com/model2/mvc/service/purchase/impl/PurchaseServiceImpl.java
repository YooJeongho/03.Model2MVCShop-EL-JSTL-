package com.model2.mvc.service.purchase.impl;

import java.util.HashMap;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.product.dao.ProductDAO;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.dao.PurchaseDao;

public class PurchaseServiceImpl implements PurchaseService {
	//field
	private PurchaseDao dao;
	private ProductDAO prodDAO;
	
	
	
	
	public PurchaseServiceImpl() {
	}

	@Override
	public Purchase addPurchase(Purchase purchaseVO) {		
		return null;
	}

	@Override
	public Purchase getPurchase(int tranNo) {
		return null;
	}

	@Override
	public HashMap<String, Object> getPurchaseList(Search search, String s) {
		return null;
	}

	@Override
	public HashMap<String, Object> getSaleList(Search search) {
		return null;
	}

	@Override
	public Purchase updatePurchase(Purchase purchase) {
		return null;
	}

	@Override
	public void updateTranCode(Purchase purchase) {

	}

}
