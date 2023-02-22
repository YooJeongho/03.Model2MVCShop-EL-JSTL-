package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class UpdateTranCodeByProdAction extends Action {

	public UpdateTranCodeByProdAction() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("UpdateTranCodeByProdAction start");
		
		int prodNo  = Integer.parseInt(request.getParameter("prodNo"));
		String tranCode = request.getParameter("tranCode");
		
		System.out.println("prdoNo는 잘 넘어오는지 : "+prodNo);
		System.out.println("trancode는 잘 넘어오는지 : "+tranCode);
		
		// 현재 컨디션 유지를 위해 그대로 listProduct.do에 보냄
		String searchCondition =   request.getParameter("searchCondition");
		String searchKeyword = request.getParameter("searchKeyword");	
		String currentPage = request.getParameter("currentPage");
		System.out.println("받아온 현재 page는 :: "+currentPage);
		
		
		PurchaseService purchaseService = new PurchaseServiceImpl();
		ProductService productService = new ProductServiceImpl();
		
		//넘어온 prodNo로 product instance에 값 binding
		Product product = productService.getProduct(prodNo);
		System.out.println(product.toString());
		System.out.println("product에서 trancode : "+product.getProTranCode());
		Purchase purchase = new Purchase();
		
		//purchase 객체에 product정보와 수정된 trancode정보 담아서 보냄
		purchase.setPurchaseProd(product);
		purchase.setTranCode(tranCode);
		
		System.out.println("updateTranCode 전");
		purchaseService.updateTranCodeByProd(purchase);
		System.out.println("updateTranCode 후");
		
		System.out.println("UpdateTranCodeByProdAction end.....");
		return "forward:/listProduct.do?menu=manage&searchCondition="+searchCondition+"&searchKeyword="+searchKeyword+"&currentPage="+currentPage;
	}

}
