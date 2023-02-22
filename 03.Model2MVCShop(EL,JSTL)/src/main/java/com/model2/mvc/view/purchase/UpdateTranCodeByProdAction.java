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
		
		System.out.println("prdoNo�� �� �Ѿ������ : "+prodNo);
		System.out.println("trancode�� �� �Ѿ������ : "+tranCode);
		
		// ���� ����� ������ ���� �״�� listProduct.do�� ����
		String searchCondition =   request.getParameter("searchCondition");
		String searchKeyword = request.getParameter("searchKeyword");	
		String currentPage = request.getParameter("currentPage");
		System.out.println("�޾ƿ� ���� page�� :: "+currentPage);
		
		
		PurchaseService purchaseService = new PurchaseServiceImpl();
		ProductService productService = new ProductServiceImpl();
		
		//�Ѿ�� prodNo�� product instance�� �� binding
		Product product = productService.getProduct(prodNo);
		System.out.println(product.toString());
		System.out.println("product���� trancode : "+product.getProTranCode());
		Purchase purchase = new Purchase();
		
		//purchase ��ü�� product������ ������ trancode���� ��Ƽ� ����
		purchase.setPurchaseProd(product);
		purchase.setTranCode(tranCode);
		
		System.out.println("updateTranCode ��");
		purchaseService.updateTranCodeByProd(purchase);
		System.out.println("updateTranCode ��");
		
		System.out.println("UpdateTranCodeByProdAction end.....");
		return "forward:/listProduct.do?menu=manage&searchCondition="+searchCondition+"&searchKeyword="+searchKeyword+"&currentPage="+currentPage;
	}

}
