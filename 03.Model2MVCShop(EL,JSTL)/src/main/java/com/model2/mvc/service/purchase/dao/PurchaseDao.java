package com.model2.mvc.service.purchase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;


public class PurchaseDao {

	public PurchaseDao() {
	}
	
	public void updatePurchase(Purchase purchase) throws Exception{
		System.out.println("PurchaseDAO의 updatePurchase() 실행");
		
		Connection con = DBUtil.getConnection();
		
		String sql = "UPDATE transaction SET (PAYMENT_OPTION, RECEIVER_NAME, RECEIVER_PHONE, DEMAILADDR, DLVY_REQUEST, DLVY_DATE) = ('?', '?', '?', '?', '?', '?',) WHERE tranNo = ?";
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		System.out.println("sql 준비완료");
		System.out.println(purchase.getTranNo());
		System.out.println(purchase.toString());
		//update할 내용 setting
		pStmt.setString(1, purchase.getPaymentOption());
		pStmt.setString(2, purchase.getReceiverName());
		pStmt.setString(3, purchase.getReceiverPhone());
		pStmt.setString(4, purchase.getDivyAddr());
		pStmt.setString(5, purchase.getDivyRequest());
		pStmt.setString(6, purchase.getDivyDate());
		
		//WHERE 절에 들어갈 내용 setting
		pStmt.setInt(7, purchase.getTranNo());
		
		System.out.println("sql에 값 setting 완료");
		
		pStmt.executeUpdate();
		System.out.println("sql querry 완료");
		
		pStmt.close();
		con.close();
		System.out.println("PurchaseDAO의 updatePurchase() 완료");
	}
	
	public Purchase findPurchase(int tranNo) throws Exception{
		
		System.out.println("PurchaseDao의 findPurchase() 실행");
		
		Connection con = DBUtil.getConnection();
		
		String sql = "SELECT * FROM transaction  WHERE tran_no = "+tranNo;
		PreparedStatement pStmt = con.prepareStatement(sql);
		
		ResultSet rs = pStmt.executeQuery();
		
		Purchase purchase = new Purchase();
		
		if(rs.next()) {
			
			ProductService prodS = new ProductServiceImpl();
			UserService userS = new UserServiceImpl();
			
			purchase.setBuyer(userS.getUser(rs.getString("BUYER_ID")));
			purchase.setPurchaseProd(prodS.getProduct(rs.getInt("PROD_NO")));
			purchase.setTranNo(rs.getInt("tran_no"));
			purchase.setPaymentOption(rs.getString("PAYMENT_OPTION"));
			purchase.setReceiverName(rs.getString("RECEIVER_NAME"));
			purchase.setReceiverPhone(rs.getString("RECEIVER_PHONE"));
			purchase.setDivyAddr(rs.getString("DEMAILADDR"));
			purchase.setDivyRequest(rs.getString("DLVY_REQUEST"));
			purchase.setDivyDate(rs.getString("DLVY_DATE"));
			purchase.setOrderDate(rs.getDate("ORDER_DATA"));
			
		}
		System.out.println("Select된 data들 purchase에 저장 완료 : "+purchase.toString());
		
		rs.close();
		pStmt.close();
		con.close();
		
		
		System.out.println("PurchaseDao의 findPurchase() 종료");
		return purchase;
	}
	
	public void insertPurchase(Purchase purchase) throws Exception {
		System.out.println("PurchaseDao의 insertPurchase 실행");
		
		Connection con = DBUtil.getConnection();
		
		//INSERT INTO purchase values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
		String sql = "INSERT INTO transaction values (seq_transaction_tran_no.nextval, ?, ?, ?, ?, ?, ?, ?, 1, sysdate, ?)";
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		
		
		pStmt.setInt(1, purchase.getPurchaseProd().getProdNo());
		pStmt.setString(2, purchase.getBuyer().getUserId());
		pStmt.setString(3, purchase.getPaymentOption());
		pStmt.setString(4, purchase.getReceiverName());
		pStmt.setString(5, purchase.getReceiverPhone());
		pStmt.setString(6, purchase.getDivyAddr());
		pStmt.setString(7, purchase.getDivyRequest());
		pStmt.setString(8, purchase.getDivyDate());
		
		System.out.println("PurchaseDao의 insertPurchase sql 실행 준비");
		pStmt.executeUpdate();
		System.out.println("PurchaseDao의 insertPurchase sql 실행완료");
		pStmt.close();
		con.close();
		
		System.out.println("PurchaseDao의 insertPurchase 종료");
	}
	
	public Map<String, Object> getPurchaseList(Search search, String userId) throws Exception{
		
		System.out.println("PurchaseDao의 getPurchaseList 실행");
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		Connection con = DBUtil.getConnection();
		
		String sql = "SELECT tran_no, buyer_id, receiver_name, RECEIVER_PHONE, TRAN_STATUS_CODE, ORDER_DATA "
				+ "FROM transaction WHERE buyer_id = '"+userId+"'";
		
		System.out.println("현재 SQL : "+sql);
		System.out.println("PurchaseDao의 getPurchaseList sql준비 완료");
		
		//TotalCount Get
		int totalCount = this.getTotalCount(sql);
		System.out.println("select된 전체 row의 수 : "+totalCount);
		map.put("totalCount", totalCount);
		
		//현재 페이지에 해당하는 게시물만 받도록 Querry 재구성
		sql = makeCurrentPageSql(sql, search);
		PreparedStatement pStmt = con.prepareStatement(sql);
		System.out.println(search);
		System.out.println("select된 전체 row의 수 : "+totalCount);
		
		ResultSet rs = pStmt.executeQuery();
		System.out.println("Querry 보내기 성공");
		
		List<Purchase> list = new ArrayList<Purchase>();
		
		//select 결과가 1개라도 있으면
		if(totalCount>0) {
			UserService userS = new UserServiceImpl();
				while(rs.next()) {
				
					User user = null;
					user = userS.getUser(rs.getString("buyer_id"));
					System.out.println("user의 정보 잘 넘어오는지 : "+user.toString());
			
					Purchase purchase = new Purchase();
					purchase.setBuyer(user);
					purchase.setTranNo(rs.getInt("tran_no"));
					purchase.setReceiverName(rs.getString("receiver_name") );
					purchase.setReceiverPhone(rs.getString("receiver_phone"));
					purchase.setTranCode(rs.getString("tran_status_code").trim());
					purchase.setOrderDate(rs.getDate("order_data"));
					
					System.out.println(purchase.getBuyer().getUserName());
					list.add(purchase);
			
			}				
		}
		
		System.out.println("판매목록 뽑아오기 성공 : "+list.size());
		System.out.println(list.get(0));
		map.put("list", list); //list에 mapping
		System.out.println("map에 totalCount와 list mapping 완료 : "+map.size());
		
		rs.close();
		pStmt.close();
		con.close();
		System.out.println("PurchaseDao의 insertPurchase 종료");
		
		return map;
	}
	
	// 게시판 currentPage Row 만  return 
			private String makeCurrentPageSql(String sql , Search search){
				sql = 	"SELECT * "+ 
							"FROM (		SELECT inner_table. * ,  ROWNUM AS row_seq " +	// view table을 두번 사용해서 rownum 두번 호출 => 최종적으로 rownum 순으로 출력 가능
											" 	FROM (	"+sql+" ) inner_table "+
											"	WHERE ROWNUM <="+search.getCurrentPage()*search.getPageSize()+" ) " +
							"WHERE row_seq BETWEEN "+((search.getCurrentPage()-1)*search.getPageSize()+1) +" AND "+search.getCurrentPage()*search.getPageSize(); // 내부 테이블에서 allign을 이용해서 rownum을 colum으로 만들어서 추출가능
				
				System.out.println("UserDAO :: make SQL :: "+ sql);	
				
				return sql;
			}
			
			
			// 게시판 Page 처리를 위한 전체 Row(totalCount)  return
			private int getTotalCount(String sql) throws Exception {
				System.out.println("ProductDAO의 getTotalCount 실행");
				
				sql = "SELECT COUNT(*) "+					// 전체 row의 수를 count 하여 select
				          "FROM ( " +sql+ ") countTable";	// 날린 sql문을 table로 생성하여 조건에 맞는 row select
				
				Connection con = DBUtil.getConnection();
				PreparedStatement pStmt = con.prepareStatement(sql);
				ResultSet rs = pStmt.executeQuery();	// 연결 및 querry 날림
				
				int totalCount = 0;
				if( rs.next() ){
					totalCount = rs.getInt(1);	//querry의 결과값의 row는 1개만 나오기 때문에 1번 row를 int값으로 불러와 전체 row의 값을 totalCount에 저장
				}
				
				pStmt.close();
				con.close();
				rs.close();
				
				System.out.println("ProductDAO의 getTotalCount 종료");
				return totalCount;
			}
	
	
}
