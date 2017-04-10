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

	<%
		//allow access only if session exists
		String user = null;
		if (session.getAttribute("username") == null
				&& request.getAttribute("userName") == null
				&& !(session.getAttribute("username").equals(request
						.getAttribute("userName")))) {
			response.sendRedirect("loginRedirect");
		}
	%>

	<header> <big>Library Management System</big> </header>
	<nav> <a
		href="homePage?userName=<%=request.getAttribute("userName")%>">Admin
		Home Page</a> <a href="logout">Logout</a>

	<table cellpadding="10" border="1">
		<caption>
			<h1>List of All Books</h1>
		</caption>
		<tr>
			<th>ID</th>
			<th>Name</th>
			<th>Author Name</th>
			<th>Issue Status</th>
			<th>Price</th>
		</tr>
		<c:forEach var="bookList" items="${bookList}">
			<tr>
				<td><c:out value="${bookList.bookId}" /></td>
				<td><c:out value="${bookList.bookName}" /></td>
				<td><c:out value="${bookList.authorName}" /></td>
				<td><c:out value="${bookList.issueStatus}" /></td>
				<td><c:out value="${bookList.amount}" /></td>

				<td><a
					href="edit?bookId=<c:out value='${bookList.bookId}' />&userName=<%=request.getAttribute("userName")%>">Edit</a>
					&nbsp;&nbsp;&nbsp;&nbsp; <a
					href="delete?bookId=<c:out value='${bookList.bookId}' />&userName=<%=request.getAttribute("userName")%>">Delete</a>
				</td>
			</tr>
		</c:forEach>
	</table>
	<div style="color: red">${error}</div>
</body>
</html>