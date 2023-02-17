package com.model2.mvc.view.purchase;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class ListPurchaseAction extends Action {

	public ListPurchaseAction() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("ListPurchaseAction 실행");
		
		//session에 띄워진 user의 정보와 id 가져오기
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		System.out.println("user의 아이디 : "+user.toString());
		String userId = user.getUserId();
		System.out.println("user의 아이디 : "+userId);
				
		//현재 페이지 정보 가져오기 및 셋팅
		Search search = new Search();
		
		int currentPage = 1;
		if(request.getParameter("currentPage") != null) {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
			System.out.println("현재 페이지 정보 : "+currentPage);
		}
		search.setCurrentPage(currentPage);
		
		//메타데이터에서 화면에 띄어질 페이지에 대한 정보 추출
		int pageSize = Integer.parseInt(getServletContext().getInitParameter("pageSize"));
		int pageUnit = Integer.parseInt(getServletContext().getInitParameter("pageUnit"));
		search.setPageSize(pageSize);
		
		
		// getPurchaseList() 실행
		PurchaseService purS = new PurchaseServiceImpl();
		Map <String, Object> map = purS.getPurchaseList(search, userId);
		
		
		// 페이지에 띄어질 정보들 셋팅
		Page pageResult = new Page(currentPage, ((Integer)map.get("totalCount")).intValue() ,pageUnit, pageSize);
		System.out.println("ListPurchaseAction에서 page setting 완료"+pageResult);
		
		//request Object Scope로 jsp에서 사용할 정보 띄우기
		request.setAttribute("list", map.get("list")); // 현재 ArrayList(purchase인 상태)
		System.out.println("list : "+map.get("list"));
		request.setAttribute("pageresult", pageResult);
		request.setAttribute("search", search);		 
		
		System.out.println("ListPurchaseAction 종료");
		return "forward:/purchase/listPurchase.jsp";
	}

}
