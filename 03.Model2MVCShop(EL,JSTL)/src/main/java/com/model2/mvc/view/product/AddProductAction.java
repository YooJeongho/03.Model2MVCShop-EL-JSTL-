package com.model2.mvc.view.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;

public class AddProductAction extends Action {

	public AddProductAction() {
		
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		System.out.println("AddProduction Action 실행");
		
		Product productVO = new Product();
		
		HttpSession session = request.getSession();
		
		productVO.setProdName(request.getParameter("prodName"));
		productVO.setProdDetail(request.getParameter("prodDetail"));
		productVO.setFileName(request.getParameter("fileName"));
		productVO.setManuDate(request.getParameter("manuDate"));
		productVO.setPrice(Integer.parseInt(request.getParameter("price")));
		
		System.out.println(productVO.toString());
		
		ProductService proSer = new ProductServiceImpl();
		
		proSer.addProduct(productVO);
		
		session.setAttribute("productVO", productVO);
		
		System.out.println("AddProduction Action 실행 종료");
		
		return "redirect:/product/addProduct.jsp";
		
	}

}
