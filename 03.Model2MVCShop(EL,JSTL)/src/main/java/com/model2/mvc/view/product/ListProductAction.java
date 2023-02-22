package com.model2.mvc.view.product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;

public class ListProductAction extends Action {

	public ListProductAction() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		System.out.println("ListProductAction 실행");
		
		Search searchVO = new Search();
		
		
		String menu = request.getParameter("menu");
		System.out.println("ListProductAction menu type : "+menu);
		System.out.println("ListProductAction 현재 페이지는 : "+request.getParameter("currentPage"));
		
		int currentPage = 1;
		if(request.getParameter("currentPage") != null){
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
			System.out.println("currentPage : "+currentPage);
		}
		System.out.println("currentPage는 잘 setting 되었는지 : "+currentPage);
		
		searchVO.setCurrentPage(currentPage);
		searchVO.setSearchCondition(request.getParameter("searchCondition"));
		searchVO.setSearchKeyword(request.getParameter("searchKeyword"));
		
		System.out.println("ListProductAction의 searchKeyword"+request.getParameter("searchKeyword"));
		//web.xml에서 메타데이터로 pageSize와 unit 추출해서 사용(상수로 지정해주지 않고 메타데이터 사용 )
		int pageSize = Integer.parseInt(getServletContext().getInitParameter("pageSize")); 
		int pageUnit = Integer.parseInt(getServletContext().getInitParameter("pageUnit"));
		System.out.println("pageSize : "+pageSize+" pageUnit : " +pageUnit);
		searchVO.setPageSize(pageSize);
		
		// Business logic
		ProductService productService = new ProductServiceImpl();
		Map <String, Object> map = productService.getProductList(searchVO);
		
		Page resultPage	= 
				new Page( currentPage, ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println("ListProductAction ::"+resultPage);
		
		
		// Model과 view 연결
		request.setAttribute("list", map.get("list"));
		System.out.println("list 내용 :"+map.get("list") );
		request.setAttribute("resultPage", resultPage);
		request.setAttribute("searchVO", searchVO);
		request.setAttribute("menu", menu);
		
		return "forward:/product/listProduct.jsp";
		
	}

}
