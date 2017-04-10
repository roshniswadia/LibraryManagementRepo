<%@page import="org.springframework.ui.Model"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="error.jsp"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
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
		if (session.getAttribute("username") == null && request.getAttribute("userName")==null
				&& !(session.getAttribute("username").equals(request
						.getAttribute("userName")))) {
			response.sendRedirect("loginRedirect");
		}
	%>
	
	<a
		href="logout">Logout</a>
	<div align="center">
		<table>
			<tr>
				<td><div style="color: Blue">${message}</div></td>
			</tr>
			<tr>
				<td><a
					href="viewAllUser?userName=<%=request.getAttribute("userName")%>">View
						All User</a></td>
			</tr>
			<tr>
				<td><a
					href="viewAllBooks?userName=<%=request.getAttribute("userName")%>">View
						All Available books</a></td>
			</tr>
			<tr>
				<td><a
					href="addBook?userName=<%=request.getAttribute("userName")%>">Add
						a book</a></td>
			</tr>
		</table>
		<div style="color: red">${error}</div>
	</div>

</body>
</html>