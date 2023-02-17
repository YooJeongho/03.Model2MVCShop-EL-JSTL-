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
		System.out.println("ListPurchaseAction ����");
		
		//session�� ����� user�� ������ id ��������
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		System.out.println("user�� ���̵� : "+user.toString());
		String userId = user.getUserId();
		System.out.println("user�� ���̵� : "+userId);
				
		//���� ������ ���� �������� �� ����
		Search search = new Search();
		
		int currentPage = 1;
		if(request.getParameter("currentPage") != null) {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
			System.out.println("���� ������ ���� : "+currentPage);
		}
		search.setCurrentPage(currentPage);
		
		//��Ÿ�����Ϳ��� ȭ�鿡 ����� �������� ���� ���� ����
		int pageSize = Integer.parseInt(getServletContext().getInitParameter("pageSize"));
		int pageUnit = Integer.parseInt(getServletContext().getInitParameter("pageUnit"));
		search.setPageSize(pageSize);
		
		
		// getPurchaseList() ����
		PurchaseService purS = new PurchaseServiceImpl();
		Map <String, Object> map = purS.getPurchaseList(search, userId);
		
		
		// �������� ����� ������ ����
		Page pageResult = new Page(currentPage, ((Integer)map.get("totalCount")).intValue() ,pageUnit, pageSize);
		System.out.println("ListPurchaseAction���� page setting �Ϸ�"+pageResult);
		
		//request Object Scope�� jsp���� ����� ���� ����
		request.setAttribute("list", map.get("list")); // ���� ArrayList(purchase�� ����)
		System.out.println("list : "+map.get("list"));
		request.setAttribute("pageresult", pageResult);
		request.setAttribute("search", search);		 
		
		System.out.println("ListPurchaseAction ����");
		return "forward:/purchase/listPurchase.jsp";
	}

}
