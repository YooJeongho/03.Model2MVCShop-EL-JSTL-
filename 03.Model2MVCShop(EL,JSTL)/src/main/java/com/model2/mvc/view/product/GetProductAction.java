package com.model2.mvc.view.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;

public class GetProductAction extends Action {

	public GetProductAction() {
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("GetProductAction 실행");
		
		String menu = request.getParameter("menu");
		System.out.println("menu type : "+menu);
		
		String productNO = request.getParameter("prodNo"); 
		System.out.println("productNO : "+productNO);
		ProductService productS = new ProductServiceImpl();
		System.out.println("Prdouct Service Impl 접근");
		
		System.out.println("GetProduct 실행");
		Product productVO = productS.getProduct(Integer.parseInt(request.getParameter("prodNo")) );
		System.out.println("GetProduct 종료");
		System.out.println("productVO의 내용 : "+productVO);
		System.out.println("GetProductAction 종료");
		
		request.setAttribute("product", productVO);
		request.setAttribute("menu", menu);
		
		if(menu.equals("manage")) {
			return "forward:/product/updateProduct.jsp";
		} else {
			return  "forward:/product/getProduct.jsp";
		}
	}

}
