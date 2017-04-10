<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" errorPage="error.jsp"%>
    <%@ page isELIgnored="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<a href="userLogout">Logout</a>
<div align="center">
		<table>
			<tr>
				<td><div style="color: Blue">${message}</div></td>
			</tr>
			<tr>
				<td><a href="viewAvailableBooks?userId=${userId}&userLoginName=<%=request.getAttribute("userLoginName")%>">View All Available Books</a></td>
			</tr>
			<tr>
				<td><a href="viewAllIssuedBooks?userId=${userId}&userLoginName=<%=request.getAttribute("userLoginName")%>">View All Issued Books</a></td>
			</tr>
		</table>
		
	</div>

</body>
</html>