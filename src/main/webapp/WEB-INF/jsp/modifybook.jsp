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
<script>
	function validateform() {
		var numbers = /^[0-9]+$/;
		var letters = /^[A-Za-z]+$/;
		var bookName = document.addmodify.bookName.value;
		var authorName = document.addmodify.authorName.value;
		var amount = document.addmodify.amount.value;

		if (bookName == null || bookName == "") {
			alert("Book Name can't be blank");
			return false;
		} else if (authorName == null || authorName == "") {
			alert("Author Name can't be blank");
			return false;
		} else if (amount == null || amount == "") {
			alert("Amount can't be blank");
			return false;
		} else if (!(amount.match(numbers))) {
			alert('Amount should be numeric characters only');
			return false;
		} else if (!isNaN(bookName)) {
			alert('Book Name should be alphabets characters only');
			return false;
		} else if (!isNaN(authorName)) {
			alert('Author Name should be alphabets characters only');
			return false;
		}
	}
</script>
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
	<div class="container">
		<header> <big>Library Management System</big> </header>
		<nav> <a
			href="homePage?userName=<%=request.getAttribute("userName")%>">Admin
			Home Page</a> <article> <a href="logout">Logout</a> <c:if
			test="${book!=null}">
			<form name="addmodify" action="update" method="post"
				onsubmit="return validateform()">
		</c:if> <c:if test="${book==null}">
			<form name="addmodify" action="insert" method="post"
				onsubmit="return validateform()">
		</c:if>
		<table cellpadding="10" border="1">
			<caption>
				<h1>
					<c:if test="${book!=null}">
						EDIT BOOK
					</c:if>
					<c:if test="${book==null}">
						ADD NEW BOOK
					</c:if>
				</h1>
			</caption>
			<c:if test="${book != null}">
				<input type="hidden" name="bookId"
					value="<c:out value='${book.bookId}' />" />

				<input type="hidden" name="issueStatus"
					value="<c:out value='${book.issueStatus}' />" />
			</c:if>

			<c:if test="${book == null}">
				<input type="hidden" name="issueStatus"
					value="<c:out value="Not Issued" />" />
			</c:if>

			<tr>
				<td>Book Name :</td>
				<td><input type="text" name="bookName"
					value="<c:out value='${book.bookName}'/>" /></td>
			</tr>
			<tr>
				<td>Author Name :</td>
				<td><input type="text" name="authorName"
					value="<c:out value='${book.authorName}'/>" /></td>
			</tr>
			<tr>
				<td>Amount :</td>
				<td><input type="text" name="amount"
					value="<c:out value='${book.amount}'/>" /></td>
			</tr>
			<tr>
				<td></td>
				<td><input type="submit" name="Submit" value="Submit"
					id="submit" /></td>
			</tr>
		</table>

		<input type="hidden" name="userName"
			value="<%=request.getAttribute("userName")%>" />
		</form>
		</article>
</body>
</html>