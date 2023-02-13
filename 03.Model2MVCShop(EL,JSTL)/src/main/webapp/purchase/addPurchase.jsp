<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>







<html>
<head>
<title>Insert title here</title>
</head>

<body>

<form name="addPurchase" action="/updatePurchaseView.do?tranNo='${purchase.tranNo}'" method="post">

다음과 같이 구매가 되었습니다.

<table border=1>
	<tr>
		<td>물품번호</td>
		<td>${purchase.purchaseProd.prodNo}1</td>
		<td></td>
	</tr>
	<tr>
		<td>구매자아이디</td>
		<td>${requestScope.purchase.buyer.userId}</td>
		<td></td>
	</tr>
	<tr>
		<td>구매방법</td>
		<td>
			${requestScope.purchase.paymentOption == '1' ? '현금구매' : '신용구매'}
		</td>
		<td></td>
	</tr>
	<tr>
		<td>구매자이름</td>
		<td>${purchase.buyer.userName}</td>
		<td></td>
	</tr>
	<tr>
		<td>구매자연락처</td>
		<td>${purchase.buyer.phone }</td>
		<td></td>
	</tr>
	<tr>
		<td>구매자주소</td>
		<td>${purhcase.divyAddr}</td>
		<td></td>
	</tr>
		<tr>
		<td>구매요청사항</td>
		<td>${purchase.divyRequest }</td>
		<td></td>
	</tr>
	<tr>
		<td>배송희망일자</td>
		<td>${purchase.divyDate }</td>
		<td></td>
	</tr>
</table>
</form>

</body>
</html>