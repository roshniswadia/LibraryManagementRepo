<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="error.jsp"%>
<%@ page import="java.util.*"%>
<%@ page import="bean.User"%>
<%@ page import="bean.Book"%>
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
		Home Page</a> <nav> <a
		href="logout">Logout</a>

	<table cellpadding="10" border="1">
		<caption>
			<h1>List of All Users</h1>
		</caption>
		<tr>
			<th>User ID</th>
			<th>User Name</th>
			<th>Books Issued</th>
		</tr>
		<%-- <c:forEach var="userList" items="${userList}">
			<tr>
				<td><c:out value="${userList.bookId}" /></td>
				<td><c:out value="${userList.bookName}" /></td>
				<td><c:out value="${userList.authorName}" /></td>
				<td><c:out value="${userList.issueStatus}" /></td>
				<td><c:out value="${userList.amount}" /></td>

				<td><a href="edit?bookId=<c:out value='${bookList.bookId}' />">Edit</a>
					&nbsp;&nbsp;&nbsp;&nbsp; <a
					href="delete?bookId=<c:out value='${bookList.bookId}' />">Delete</a>
				</td>
				</c:forEach>
			</tr> --%>

		<%
			List<User> userList = (List<User>) request.getAttribute("userList");

			for (Iterator<User> iterator = userList.iterator(); iterator
					.hasNext();) {
				User listUser = (User) iterator.next();
		%>
		<tr>
			<td><%=listUser.getUserId()%></td>
			<td><%=listUser.getUserLoginName()%></td>

			<td>
				<table cellpadding="10" border="1">
					<th>Book Name</th>
					<th>Author Name</th>
					<th>Price</th>

					<%
						Iterator it = listUser.getBookSet().iterator();
							while (it.hasNext()) {
								Book booklist = (Book) it.next();
					%>
					<tr>
						<td><%=booklist.getBookName()%></td>
						<td><%=booklist.getAuthorName()%></td>
						<td><%=booklist.getAmount()%></td>
					</tr>


					<%
						}
					%>
					</tr>
				</table>
			</td>
			<%
				}
			%>
		
	</table>
	<div style="color: red">${error}</div>
</body>
</html>