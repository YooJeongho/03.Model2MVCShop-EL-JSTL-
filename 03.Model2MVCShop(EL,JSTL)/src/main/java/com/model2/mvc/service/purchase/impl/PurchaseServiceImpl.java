package com.model2.mvc.service.purchase.impl;

import java.util.HashMap;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.product.dao.ProductDAO;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.dao.PurchaseDao;

public class PurchaseServiceImpl implements PurchaseService {
	
	//field
	private PurchaseDao dao;
	private ProductDAO productDAO;
	
	public PurchaseServiceImpl() {
		dao = new PurchaseDao();
		productDAO = new ProductDAO();
	}

	@Override
	public void addPurchase(Purchase purchaseVO) throws Exception {
		System.out.println("PurchaseServiceImpl의 addPurchase 실행");
		dao.insertPurchase(purchaseVO);
		
	}

	@Override
	public Purchase getPurchase(int tranNo) throws Exception {
		System.out.println("PurchaseServiceImpl의 getPurchase 실행");
		return dao.findPurchase(tranNo);
	}

	@Override
	public Map<String, Object> getPurchaseList(Search search, String userId) throws Exception {
		System.out.println("PurchaseServiceImpl의 getPurchaseList() 실행");
		return dao.getPurchaseList(search, userId);
	}

	@Override
	public HashMap<String, Object> getSaleList(Search search) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updatePurchase(Purchase purchase) throws Exception {
		dao.updatePurchase(purchase);
	}

	@Override
	public void updateTranCode(Purchase purchase) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
