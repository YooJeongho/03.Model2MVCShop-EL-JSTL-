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
		System.out.println("PurchaseDAO�� updatePurchase() ����");
		
		Connection con = DBUtil.getConnection();
		
		String sql = "UPDATE transaction SET (PAYMENT_OPTION, RECEIVER_NAME, RECEIVER_PHONE, DEMAILADDR, DLVY_REQUEST, DLVY_DATE) = ('?', '?', '?', '?', '?', '?',) WHERE tranNo = ?";
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		System.out.println("sql �غ�Ϸ�");
		System.out.println(purchase.getTranNo());
		System.out.println(purchase.toString());
		//update�� ���� setting
		pStmt.setString(1, purchase.getPaymentOption());
		pStmt.setString(2, purchase.getReceiverName());
		pStmt.setString(3, purchase.getReceiverPhone());
		pStmt.setString(4, purchase.getDivyAddr());
		pStmt.setString(5, purchase.getDivyRequest());
		pStmt.setString(6, purchase.getDivyDate());
		
		//WHERE ���� �� ���� setting
		pStmt.setInt(7, purchase.getTranNo());
		
		System.out.println("sql�� �� setting �Ϸ�");
		
		pStmt.executeUpdate();
		System.out.println("sql querry �Ϸ�");
		
		pStmt.close();
		con.close();
		System.out.println("PurchaseDAO�� updatePurchase() �Ϸ�");
	}
	
	public Purchase findPurchase(int tranNo) throws Exception{
		
		System.out.println("PurchaseDao�� findPurchase() ����");
		
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
		System.out.println("Select�� data�� purchase�� ���� �Ϸ� : "+purchase.toString());
		
		rs.close();
		pStmt.close();
		con.close();
		
		
		System.out.println("PurchaseDao�� findPurchase() ����");
		return purchase;
	}
	
	public void insertPurchase(Purchase purchase) throws Exception {
		System.out.println("PurchaseDao�� insertPurchase ����");
		
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
		
		System.out.println("PurchaseDao�� insertPurchase sql ���� �غ�");
		pStmt.executeUpdate();
		System.out.println("PurchaseDao�� insertPurchase sql ����Ϸ�");
		pStmt.close();
		con.close();
		
		System.out.println("PurchaseDao�� insertPurchase ����");
	}
	
	public Map<String, Object> getPurchaseList(Search search, String userId) throws Exception{
		
		System.out.println("PurchaseDao�� getPurchaseList ����");
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		Connection con = DBUtil.getConnection();
		
		String sql = "SELECT tran_no, buyer_id, receiver_name, RECEIVER_PHONE, TRAN_STATUS_CODE, ORDER_DATA "
				+ "FROM transaction WHERE buyer_id = '"+userId+"'";
		
		System.out.println("���� SQL : "+sql);
		System.out.println("PurchaseDao�� getPurchaseList sql�غ� �Ϸ�");
		
		//TotalCount Get
		int totalCount = this.getTotalCount(sql);
		System.out.println("select�� ��ü row�� �� : "+totalCount);
		map.put("totalCount", totalCount);
		
		//���� �������� �ش��ϴ� �Խù��� �޵��� Querry �籸��
		sql = makeCurrentPageSql(sql, search);
		PreparedStatement pStmt = con.prepareStatement(sql);
		System.out.println(search);
		System.out.println("select�� ��ü row�� �� : "+totalCount);
		
		ResultSet rs = pStmt.executeQuery();
		System.out.println("Querry ������ ����");
		
		List<Purchase> list = new ArrayList<Purchase>();
		
		//select ����� 1���� ������
		if(totalCount>0) {
			UserService userS = new UserServiceImpl();
				while(rs.next()) {
				
					User user = null;
					user = userS.getUser(rs.getString("buyer_id"));
					System.out.println("user�� ���� �� �Ѿ������ : "+user.toString());
			
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
		
		System.out.println("�ǸŸ�� �̾ƿ��� ���� : "+list.size());
		System.out.println(list.get(0));
		map.put("list", list); //list�� mapping
		System.out.println("map�� totalCount�� list mapping �Ϸ� : "+map.size());
		
		rs.close();
		pStmt.close();
		con.close();
		System.out.println("PurchaseDao�� insertPurchase ����");
		
		return map;
	}
	
	// �Խ��� currentPage Row ��  return 
			private String makeCurrentPageSql(String sql , Search search){
				sql = 	"SELECT * "+ 
							"FROM (		SELECT inner_table. * ,  ROWNUM AS row_seq " +	// view table�� �ι� ����ؼ� rownum �ι� ȣ�� => ���������� rownum ������ ��� ����
											" 	FROM (	"+sql+" ) inner_table "+
											"	WHERE ROWNUM <="+search.getCurrentPage()*search.getPageSize()+" ) " +
							"WHERE row_seq BETWEEN "+((search.getCurrentPage()-1)*search.getPageSize()+1) +" AND "+search.getCurrentPage()*search.getPageSize(); // ���� ���̺��� allign�� �̿��ؼ� rownum�� colum���� ���� ���Ⱑ��
				
				System.out.println("UserDAO :: make SQL :: "+ sql);	
				
				return sql;
			}
			
			
			// �Խ��� Page ó���� ���� ��ü Row(totalCount)  return
			private int getTotalCount(String sql) throws Exception {
				System.out.println("ProductDAO�� getTotalCount ����");
				
				sql = "SELECT COUNT(*) "+					// ��ü row�� ���� count �Ͽ� select
				          "FROM ( " +sql+ ") countTable";	// ���� sql���� table�� �����Ͽ� ���ǿ� �´� row select
				
				Connection con = DBUtil.getConnection();
				PreparedStatement pStmt = con.prepareStatement(sql);
				ResultSet rs = pStmt.executeQuery();	// ���� �� querry ����
				
				int totalCount = 0;
				if( rs.next() ){
					totalCount = rs.getInt(1);	//querry�� ������� row�� 1���� ������ ������ 1�� row�� int������ �ҷ��� ��ü row�� ���� totalCount�� ����
				}
				
				pStmt.close();
				con.close();
				rs.close();
				
				System.out.println("ProductDAO�� getTotalCount ����");
				return totalCount;
			}
	
	
}
