package com.model2.mvc.service.purchase;

import java.util.HashMap;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;


public interface PurchaseService {
	
	public Purchase addPurchase(Purchase purchaseVO);
	
	public Purchase getPurchase(int tranNo);
	
	public HashMap<String, Object> getPurchaseList(Search search, String s);
	
	public HashMap<String, Object> getSaleList(Search search);
	
	public Purchase updatePurchase(Purchase purchase);
	
	public void updateTranCode(Purchase purchase);
}
