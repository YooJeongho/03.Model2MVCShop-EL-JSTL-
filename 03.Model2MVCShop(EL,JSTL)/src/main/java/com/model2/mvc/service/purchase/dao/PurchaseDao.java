package com.model2.mvc.service.purchase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.Purchase;


public class PurchaseDao {

	public PurchaseDao() {
	}
	
	public void insertPurchase(Purchase purchase) throws Exception {
		System.out.println("PurchaseDao의 insertPurchase 실행");
		
		Connection con = DBUtil.getConnection();
		
		//INSERT INTO purchase values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
		String sql = "INSERT INTO purchase values (seq_transaction_tran_no.nextval, ?, ?, ?, ?, ?, ?, ?, ?, sysdate, ?)";
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		
		
		pStmt.setInt(1, purchase.getPurchaseProd().getProdNo());
		pStmt.setString(2, purchase.getReceiverName());
		pStmt.setString(3, purchase.getPaymentOption());
		pStmt.setString(4, purchase.getReceiverName());
		pStmt.setString(5, purchase.getReceiverPhone());
		pStmt.setString(6, purchase.getDivyAddr());
		pStmt.setString(7, purchase.getDivyRequest());
		pStmt.setString(8, purchase.getTranCode());
		pStmt.setString(9, purchase.getDivyDate());
		
		System.out.println("PurchaseDao의 insertPurchase sql 실행 준비");
		pStmt.executeUpdate();
		System.out.println("PurchaseDao의 insertPurchase sql 실행완료");
		pStmt.close();
		con.close();
		
		System.out.println("PurchaseDao의 insertPurchase 종료");
	}
	
	
	
}
