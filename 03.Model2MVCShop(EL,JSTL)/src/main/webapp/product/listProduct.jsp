<%@page import="com.model2.mvc.common.util.CommonUtil"%>
<%@ page contentType="text/html; charset=euc-kr" %>

<%@ page import="java.util.*"  %>
<%@ page import="com.model2.mvc.service.domain.*" %>
<%@ page import="com.model2.mvc.common.*" %>
<%@page import="com.model2.mvc.common.util.CommonUtil"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
	//List<Product> list = (List<Product>)request.getAttribute("list");
	//Page resultPage=(Page)request.getAttribute("resultPage");

	//Search search = (Search)request.getAttribute("searchVO");
	
	
	
	//null 을 null String으로 변환해주는 작업
	//String searchCondition = CommonUtil.null2str(search.getSearchCondition());
	//String searchKeyword = CommonUtil.null2str(search.getSearchKeyword());
	
%>

<html>
<head>
<title>상품 목록조회</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script type="text/javascript">
<!--
function fncGetUserList(currentPage) {
	document.getElementById("currentPage").value = currentPage;
   	document.detailForm.submit();
}
-->
</script>
</head>

<body bgcolor="#ffffff" text="#000000">

<div style="width:98%; margin-left:10px;">

<form name="detailForm" action="/listProduct.do?menu=${requestScope.menu}" method="post">

<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37">
			<img src="/images/ct_ttl_img01.gif" width="15" height="37">
		</td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left:10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>	
					<td width="93%" class="ct_ttl01">${requestScope.menu=='manage' ? '상품 관리' : '상품 목록' }</td>
				</tr>
			</table>
		</td>
		<td width="12" height="37">
			<img src="/images/ct_ttl_img03.gif" width="12" height="37">
		</td>
	</tr>
</table>


<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td align="right">
			<select name="searchCondition" class="ct_input_g" style="width:80px">
				<%-- 
				<%
				if(search.getSearchCondition().equals("0")){
				%>
					<option value="0" selected>상품번호</option>
					<option value="1">상품명</option>
					<option value="2">상품가격</option>
				<%
				}else if(search.getSearchCondition().equals("1")){
				%>
					<option value="0">상품번호</option>
					<option value="1" selected>상품명</option>
					<option value="2">상품가격</option>
				<%
				}else{
				%>
					<option value="0">상품번호</option>
					<option value="1">상품명</option>
					<option value="2" selected>상품가격</option>
				<%
				}
				%>
				--%>
				
				<option value="0" ${! empty searchVO.searchCondition && searchVO.searchCondition == 0 ? "selected" : "" }>상품번호</option>
				<option value="1" ${! empty searchVO.searchCondition && searchVO.searchCondition == 1 ? "selected" : "" }>상품명</option>
				<option value="2" ${! empty searchVO.searchCondition && searchVO.searchCondition == 2 ? "selected" : "" }>상품가격</option>
			</select>
			<input 	type="text" name="searchKeyword"  value="${! empty searchVO.searchKeyword ? searchVO.searchKeyword : ""}" 
							class="ct_input_g" style="width:200px; height:19px" >
		</td>
		
		<td align="right" width="70">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="17" height="23">
						<img src="/images/ct_btnbg01.gif" width="17" height="23">
					</td>
					<td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top:3px;">
						<a href="javascript:fncGetUserList('${resultPage.currentPage }')">검색</a>
					</td>
					<td width="14" height="23">
						<img src="/images/ct_btnbg03.gif" width="14" height="23">
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td colspan="11" >전체  ${resultPage.totalCount} 건수, 현재 ${resultPage.currentPage} 페이지</td>
	</tr>
	<tr>
		<td class="ct_list_b" width="100">No</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">상품명</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">가격</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">등록날짜</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">현재상태</td>		
	</tr>
	<tr>
		<td colspan="11" bgcolor="808285" height="1"></td>
	</tr>
	
	
<c:set var="i" value="0" />
	<c:forEach var="product" items="${list}">
		<c:set var="i" value="${ i+1 }" />
		<tr class="ct_list_pop">
			<td align="center">${ i }</td>
			<td></td>
			<td align="left"><a href="/getProduct.do?menu=${menu}&prodNo=${product.prodNo}">${product.prodName}</a></td>
			<td></td>
			<td align="left">${product.price}</td>
			<td></td>
			<td align="left">${product.regDate}</td>
			<td></td>
			<td align="left">
				<c:if test="${sessionScope.user.role eq 'admin'}">
					<c:if test="${menu eq 'manage' }">
					<c:if test="${product.proTranCode eq '0'}">판매중</c:if>
					<c:if test="${product.proTranCode eq '1'}">
						구매완료 
						<a href="/updateTranCodeByProd.do?prodNo=${product.prodNo}&tranCode=2&currentPage=${resultPage.currentPage}">배송하기</a>
					</c:if>
					<c:if test="${product.proTranCode eq '2'}">배송 중</c:if>
					<c:if test="${product.proTranCode eq '3'}">배송 완료</c:if>
					</c:if>
					
					<c:if test="${product.proTranCode eq '0'}">판매중</c:if>
					<c:if test="${product.proTranCode eq '1'}">품절</c:if>
					<c:if test="${product.proTranCode eq '2'}">품절</c:if>
					<c:if test="${product.proTranCode eq '3'}">품절</c:if>
				</c:if>
				
				<c:if test="${sessionScope.user.role eq 'user'}">
					<c:if test="${product.proTranCode eq '0'}">판매중</c:if>
					
					<c:if test="${product.proTranCode eq '1'}">품절</c:if>
						 
					<c:if test="${product.proTranCode eq '2'}">품절</c:if>
					<c:if test="${product.proTranCode eq '3'}">품절</c:if>
				</c:if>
			</td>
		</tr>
		<tr>
		<td colspan="11" bgcolor="D6D7D6" height="1"></td>
		</tr>
	</c:forEach>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td align="center">
		<input type="hidden" id="currentPage" name="currentPage" value=""/>
		<jsp:include page="../common/pageNavigator.jsp"/>
    	</td>
	</tr>
</table>
<!--  페이지 Navigator 끝 -->
</form>
</div>

</body>
</html>