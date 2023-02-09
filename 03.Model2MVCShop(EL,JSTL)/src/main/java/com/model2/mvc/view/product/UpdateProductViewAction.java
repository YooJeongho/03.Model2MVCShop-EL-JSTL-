package com.model2.mvc.view.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;

public class UpdateProductViewAction extends Action {

	public UpdateProductViewAction() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		System.out.println("UPVA 실행");
		
//		HttpSession session = request.getSession(true);
		int prodNo =Integer.parseInt((request.getParameter("prodNo")));
		
		ProductService prodService = new ProductServiceImpl();
		Product productVO = prodService.getProduct(prodNo);
		
		request.setAttribute("VO", productVO);
		
		System.out.println("UPVA 종료");
		return "forward:/product/updateProduct.jsp";
	}

}
