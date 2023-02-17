package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;

public class AddPurchaseAction extends Action {
	
	//constructor
	public AddPurchaseAction() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("AddPurchaseAction 실행");
		
		Purchase purchase = new Purchase();
		
		//물품정보 저장
		ProductService prodS = new ProductServiceImpl();
		Product prodVO = prodS.getProduct(Integer.parseInt(request.getParameter("prodNo")));
		
		//구매자 정보 저장
		UserService userS = new UserServiceImpl();
		User user = userS.getUser(request.getParameter("buyerId"));
		
		purchase.setPurchaseProd(prodVO);
		purchase.setBuyer(user);
		purchase.setPaymentOption(request.getParameter("paymentOption"));
		purchase.setReceiverPhone(request.getParameter("receiverPhone"));
		purchase.setDivyAddr(request.getParameter("receiverAddr"));
		purchase.setDivyRequest(request.getParameter("receiverRequest"));
		purchase.setDivyDate(request.getParameter("receiverDate"));
		System.out.println(purchase.toString());
		
		//구매 시 tran_code를 1로 저장 (구매완료) => 2 : 배송 중 / 3 : 배송완료
		purchase.setTranCode("1");
		System.out.println(purchase.toString());
		
		PurchaseService purchaseService = new PurchaseServiceImpl();
		System.out.println("PurchaseServiceImpl로 전송");
		purchaseService.addPurchase(purchase);
		System.out.println("PurchaseServiceImpl에서 PurchaseDAO 호출 완료 및 실행");
		request.setAttribute("purchase", purchase);
		System.out.println(purchase.toString());
		System.out.println("AddPurchaseAction 종료");
		return "forward:/purchase/addPurchase.jsp";
	}


}
