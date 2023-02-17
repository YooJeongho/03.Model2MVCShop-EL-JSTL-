package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class UpdatePurchaseViewAction extends Action {

	public UpdatePurchaseViewAction() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int tranNo= Integer.parseInt(request.getParameter("tranNo"));
		
		PurchaseService purcS = new PurchaseServiceImpl();
		Purchase purchase = purcS.getPurchase(tranNo);
		
		System.out.println("payment Option : "+purchase.getPaymentOption());
		
		request.setAttribute("purchase" , purchase);
		return "forward:/purchase/updatePurchaseView.jsp";
	}

}
