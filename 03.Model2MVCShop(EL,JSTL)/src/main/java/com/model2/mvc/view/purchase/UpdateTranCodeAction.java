package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class UpdateTranCodeAction extends Action {

	public UpdateTranCodeAction() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Purchase purchase = new Purchase();
		PurchaseService purchaseS = new PurchaseServiceImpl();
		
		int tranNo = Integer.parseInt(request.getParameter("tranNo"));
		String tranCode = request.getParameter("tranCode");
		String currentPage = request.getParameter("page");
		
		System.out.println("tranCode 넘어 오는지 : "+tranCode);
		
		purchase.setTranCode(tranCode);
		purchase.setTranNo(tranNo);
		
		purchaseS.updateTranCode(purchase);
		return "forward:/listPurchase.do?&currentPage="+currentPage;
	}

}
