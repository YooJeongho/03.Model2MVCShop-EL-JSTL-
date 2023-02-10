package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;


public class AddPurchaseViewAction extends Action {

	public AddPurchaseViewAction() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		System.out.println("AddPurchaseViewAction 실행");
		
		
		Purchase purchaseVO = new Purchase();
		
		System.out.println("상품정보 가져오기 실행");
		int productNo = Integer.parseInt(request.getParameter("prodNo")); 
		ProductService prodS = new ProductServiceImpl();
		Product prodVO = prodS.getProduct(productNo);
		System.out.println(prodVO.toString());
		System.out.println("상품정보 가져오기 종료");
		System.out.println("--------------------------------------------");
		
		System.out.println("고객정보 가져오기 실행");
		HttpSession session = request.getSession();
		User userVO = (User)session.getAttribute("user");
		System.out.println(userVO.toString());
		System.out.println("고객정보 가져오기 종료");
		System.out.println("--------------------------------------------");
		
		purchaseVO.setBuyer(userVO);
		purchaseVO.setPurchaseProd(prodVO);
		request.setAttribute("purchase", purchaseVO);
		System.out.println(purchaseVO.getPurchaseProd().getProdNo());
		
		System.out.println("AddPurchaseViewAction 종료");
		return "forward:/purchase/addPurchaseView.jsp";
	}

}
