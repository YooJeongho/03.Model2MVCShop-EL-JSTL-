package com.model2.mvc.view.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;

public class UpdateProductAction extends Action {

	public UpdateProductAction() {
		
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		System.out.println("UpdateProductAction 시작");
		
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));
		String menu = request.getParameter("menu");
		System.out.println("UpdateProductAction 의 menu type : "+menu);
		Product proVO = new Product();
		proVO.setProdNo(prodNo);
		proVO.setProdName(request.getParameter("prodName"));
		proVO.setProdDetail(request.getParameter("prodDetail"));
		proVO.setManuDate(request.getParameter("manuDate"));
		proVO.setPrice(Integer.parseInt(request.getParameter("price")));
		proVO.setFileName(request.getParameter("fileName"));
		System.out.println("proVO에 setting완료");
		ProductService service = new ProductServiceImpl();
		service.updateProduct(proVO);
	
		
		System.out.println("update 완료");
		
		return "forward:/getProduct.do?prodNo="+prodNo+"&menu="+menu;
	}

}
