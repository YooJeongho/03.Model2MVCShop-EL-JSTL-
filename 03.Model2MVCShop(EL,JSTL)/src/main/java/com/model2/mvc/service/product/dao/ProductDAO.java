package com.model2.mvc.service.product.dao;

import java.security.ProtectionDomain;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.User;

public class ProductDAO {

	public ProductDAO() {
	}
	
	// product insert method
	public void insertProduct (Product productVO) throws Exception {
		System.out.println("ProductDAO�� insertProduct ����");
		
		Connection con = DBUtil.getConnection();
		
		String sql = "INSERT INTO product values (seq_product_prod_no.nextval, ?, ?, ?, ?, ?, sysdate)";
		
		String date = productVO.getManuDate().replaceAll("-", "");
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setString(1, productVO.getProdName());
		pStmt.setString(2, productVO.getProdDetail());
		pStmt.setString(3, date);	
		pStmt.setInt(4, productVO.getPrice());
		pStmt.setString(5, productVO.getFileName());
		pStmt.executeUpdate();
		
		con.close();
		
		System.out.println("ProductDAO�� insertProduct �Ϸ�");
	}
	
	//product search method
	public Product findProduct (int productNO) throws Exception {
		Connection con = DBUtil.getConnection();
		
		String sql = "SELECT prod.*, NVL(tran.tran_status_code, 0) AS tran_status_code, NVL(tran.tran_no,0) AS tran_no"
				+ " FROM product prod, transaction tran"
				+ " WHERE prod.prod_no = tran.prod_no(+) AND prod.prod_no = ?";
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setInt(1, productNO);
		
		Product productVO = new Product();
		
		ResultSet rs = pStmt.executeQuery();
		
		while(rs.next()) {
			productVO.setProdNo(rs.getInt("PROD_NO"));
			productVO.setProdName(rs.getString("PROD_NAME"));
			productVO.setProdDetail(rs.getString("PROD_DETAIL"));
			productVO.setFileName(rs.getString("IMAGE_FILE"));
			productVO.setManuDate(rs.getString("MANUFACTURE_DAY"));
			productVO.setPrice(rs.getInt("PRICE"));
			productVO.setRegDate(rs.getDate("REG_DATE"));
			productVO.setProTranCode(rs.getString("tran_status_code"));
		}
		
		con.close();
		
		return productVO;
		
	}
	
	//product List get method
	public Map<String, Object> getProductList (Search searchVO) throws Exception{
		
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		Connection con = DBUtil.getConnection();
		System.out.println("getProductList�� Select�� �����մϴ�.");
		System.out.println("serchCondition = "+searchVO.getSearchCondition());
		System.out.println("serchKeyword = "+searchVO.getSearchKeyword());
		System.out.println("serchKeyword = "+searchVO.getPageSize());
		
		String sql = "SELECT * FROM product";
		
		//query ����
		if(searchVO.getSearchCondition() != null) {
			if(searchVO.getSearchCondition().equals("0") && !searchVO.getSearchKeyword().equals("")) {
				sql += " where PROD_NO LIKE '%"+searchVO.getSearchKeyword()+"%'";
				
			} else if(searchVO.getSearchCondition().equals("1") && !searchVO.getSearchKeyword().equals("")) {
				sql += " where PROD_NAME LIKE '%"+searchVO.getSearchKeyword()+"%'";
				
			} else if(searchVO.getSearchCondition().equals("2") && !searchVO.getSearchKeyword().equals("")) {
				sql += " where price LIKE '%"+searchVO.getSearchKeyword()+"%'";				
			}
		} // end if
		sql += " ORDER BY prod_name";
		
		System.out.println("���� SQL : "+sql);
		System.out.println("getProductList�� sql �غ�Ϸ�");
		
		//==> TotalCount GET
		int totalCount = this.getTotalCount(sql); // �Ʒ��� method�� �����ؼ� ���
		System.out.println("ProductDAO :: totalCount  :: " + totalCount);
		
		//==> CurrentPage �Խù��� �޵��� Querry �籸��
		sql = makeCurrentPageSql(sql, searchVO);
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();
		System.out.println("querry ������ �Ϸ�");
		System.out.println("searchVO = "+searchVO);
		
		List<Product> list = new ArrayList<Product>();	// interface ��� �ڵ�
		
		while(rs.next()) {		//DB���� select�� ������ ���پ� �����鼭 setting
			Product product = new Product();
			product.setProdNo(rs.getInt("prod_no"));
			product.setProdName(rs.getString("prod_name"));
			product.setPrice(rs.getInt("price"));
			product.setRegDate(rs.getDate("reg_date"));
			product.setProTranCode(rs.getString("tran_code").trim());
			list.add(product);
		}
		System.out.println("list�� ������ �� ����ִ���"+list);
		//==> totalCount ������ map�� key=value���·� ����
		map.put("totalCount", new Integer(totalCount));
		//==> currentPage�� ǥ�õǴ� �Խù��� ������ ���� list�� key=value���·� ����
		map.put("list", list);
		
		
		rs.close();
		pStmt.close();
		con.close(); // DB �̿��� �������Ƿ� Stream close
		
		return map;
	}
	
	//product update method
	public void updateProduct(Product productVO) throws Exception {
		
		System.out.println("ProductDAO���� updateProduct����");
		Connection con = DBUtil.getConnection();
		
		String sql = "UPDATE product set PROD_NAME=?, PROD_DETAIL=?, MANUFACTURE_DAY=?, PRICE=?, IMAGE_FILE=? WHERE PROD_NO=?";
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		
		pStmt.setString(1, productVO.getProdName());
		pStmt.setString(2, productVO.getProdDetail());
		pStmt.setString(3, productVO.getManuDate());
		pStmt.setInt(4, productVO.getPrice());
		pStmt.setString(5, productVO.getFileName());
		pStmt.setInt(6, productVO.getProdNo());
		
		pStmt.executeUpdate();
		
		System.out.println("table�� update �Ϸ�");
		System.out.println("ProductDAO���� updateProduct����");
		
		pStmt.close();
		con.close();
		
	}
	
	
	// �Խ��� currentPage Row ��  return 
		private String makeCurrentPageSql(String sql , Search search){
			sql = 	"SELECT otb.*, NVL(tran.tran_no,0) AS tran_no, NVL(tran.tran_status_code,0) AS tran_code"+ 
						" FROM (		SELECT inner_table. * ,  ROWNUM AS row_seq " +	// view table�� �ι� ����ؼ� rownum �ι� ȣ�� => ���������� rownum ������ ��� ����
										" 	FROM (	"+sql+" ) inner_table "+
										"	WHERE ROWNUM <="+search.getCurrentPage()*search.getPageSize()+" ) otb, transaction tran" +
						" WHERE otb.prod_no = tran.prod_no(+)"
						+ " AND row_seq BETWEEN "+((search.getCurrentPage()-1)*search.getPageSize()+1) +" AND "+search.getCurrentPage()*search.getPageSize(); // ���� ���̺��� allign�� �̿��ؼ� rownum�� colum���� ���� ���Ⱑ��
			
			System.out.println("DAO :: make SQL :: "+ sql);	
			
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
