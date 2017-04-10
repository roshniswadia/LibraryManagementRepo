<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="error.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

	<header> <big>Library Management System</big> </header>
	<nav> <a
		href="userhomePage?userId=${userId}&userLoginName=<%=request.getAttribute("userLoginName")%>">User
		Home Page</a>
		<a href="userLogout">Logout</a>

	<table cellpadding="10" border="1">
		<caption>
			<h1>List of All Issued Books Books</h1>
		</caption>
		<tr>
			<th>ID</th>
			<th>Name</th>
			<th>Author Name</th>
			<th>Price</th>
		</tr>
		<c:forEach var="bookList" items="${bookList}">
			<tr>

				<td><c:out value="${bookList.bookName}" /></td>
				<td><c:out value="${bookList.authorName}" /></td>
				<td><c:out value="${bookList.amount}" /></td>

				<td><a
					href="return?bookId=<c:out value='${bookList.bookId}' />&userId=${userId}&userLoginName=<%=request.getAttribute("userLoginName")%>">Return
						Book</a> &nbsp;&nbsp;&nbsp;&nbsp;</td>
			</tr>
		</c:forEach>
	</table>
	<div style="color: red">${error}</div>
</body>
</html>