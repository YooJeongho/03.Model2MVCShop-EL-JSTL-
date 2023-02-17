package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class GetPurchaseAction extends Action {

	public GetPurchaseAction() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("GetPurchaseAction 실행");
		
		int tranNo = Integer.parseInt(request.getParameter("tranNo"));
		PurchaseService purchS = new PurchaseServiceImpl(); 
		Purchase purchase = purchS.getPurchase(tranNo);
		
		System.out.println("Select된 purchase의 정보 : "+purchase.toString());
		request.setAttribute("purchase", purchase);
		
		System.out.println("GetPurchaseAction 종료");
		return "forward:/purchase/getPurchase.jsp";
	}

}
