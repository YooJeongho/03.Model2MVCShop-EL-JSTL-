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
		System.out.println("GetProductAction ����");
		
		String menu = request.getParameter("menu");
		System.out.println("menu type : "+menu);
		
		String productNO = request.getParameter("prodNo"); 
		System.out.println("productNO : "+productNO);
		ProductService productS = new ProductServiceImpl();
		System.out.println("Prdouct Service Impl ����");
		
		System.out.println("GetProduct ����");
		Product productVO = productS.getProduct(Integer.parseInt(request.getParameter("prodNo")) );
		System.out.println("GetProduct ����");
		System.out.println("productVO�� ���� : "+productVO);
		System.out.println("GetProductAction ����");
		
		request.setAttribute("product", productVO);
		request.setAttribute("menu", menu);
		
		if(menu.equals("manage")) {
			return "forward:/product/updateProduct.jsp";
		} else {
			return  "forward:/product/getProduct.jsp";
		}
	}

}
