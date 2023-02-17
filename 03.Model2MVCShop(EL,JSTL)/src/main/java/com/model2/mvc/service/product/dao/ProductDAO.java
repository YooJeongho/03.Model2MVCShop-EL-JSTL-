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
		System.out.println("ProductDAO의 insertProduct 실행");
		
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
		
		System.out.println("ProductDAO의 insertProduct 완료");
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
		System.out.println("getProductList의 Select를 실행합니다.");
		System.out.println("serchCondition = "+searchVO.getSearchCondition());
		System.out.println("serchKeyword = "+searchVO.getSearchKeyword());
		System.out.println("serchKeyword = "+searchVO.getPageSize());
		
		String sql = "SELECT * FROM product";
		
		//query 구성
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
		
		System.out.println("현재 SQL : "+sql);
		System.out.println("getProductList의 sql 준비완료");
		
		//==> TotalCount GET
		int totalCount = this.getTotalCount(sql); // 아래에 method를 생성해서 사용
		System.out.println("ProductDAO :: totalCount  :: " + totalCount);
		
		//==> CurrentPage 게시물만 받도록 Querry 재구성
		sql = makeCurrentPageSql(sql, searchVO);
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();
		System.out.println("querry 보내기 완료");
		System.out.println("searchVO = "+searchVO);
		
		List<Product> list = new ArrayList<Product>();	// interface 기반 코딩
		
		while(rs.next()) {		//DB에서 select의 내용을 한줄씩 읽으면서 setting
			Product product = new Product();
			product.setProdNo(rs.getInt("prod_no"));
			product.setProdName(rs.getString("prod_name"));
			product.setPrice(rs.getInt("price"));
			product.setRegDate(rs.getDate("reg_date"));
			product.setProTranCode(rs.getString("tran_code").trim());
			list.add(product);
		}
		System.out.println("list에 정보가 잘 들어있는지"+list);
		//==> totalCount 정보를 map에 key=value형태로 저장
		map.put("totalCount", new Integer(totalCount));
		//==> currentPage의 표시되는 게시물의 정보를 갖는 list를 key=value형태로 저장
		map.put("list", list);
		
		
		rs.close();
		pStmt.close();
		con.close(); // DB 이용이 끝났으므로 Stream close
		
		return map;
	}
	
	//product update method
	public void updateProduct(Product productVO) throws Exception {
		
		System.out.println("ProductDAO에서 updateProduct실행");
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
		
		System.out.println("table에 update 완료");
		System.out.println("ProductDAO에서 updateProduct종료");
		
		pStmt.close();
		con.close();
		
	}
	
	
	// 게시판 currentPage Row 만  return 
		private String makeCurrentPageSql(String sql , Search search){
			sql = 	"SELECT otb.*, NVL(tran.tran_no,0) AS tran_no, NVL(tran.tran_status_code,0) AS tran_code"+ 
						" FROM (		SELECT inner_table. * ,  ROWNUM AS row_seq " +	// view table을 두번 사용해서 rownum 두번 호출 => 최종적으로 rownum 순으로 출력 가능
										" 	FROM (	"+sql+" ) inner_table "+
										"	WHERE ROWNUM <="+search.getCurrentPage()*search.getPageSize()+" ) otb, transaction tran" +
						" WHERE otb.prod_no = tran.prod_no(+)"
						+ " AND row_seq BETWEEN "+((search.getCurrentPage()-1)*search.getPageSize()+1) +" AND "+search.getCurrentPage()*search.getPageSize(); // 내부 테이블에서 allign을 이용해서 rownum을 colum으로 만들어서 추출가능
			
			System.out.println("DAO :: make SQL :: "+ sql);	
			
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
