<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Spring Login Form</title>
</head>

<script>  
function validateform(){  
var adminLoginName=document.adminLoginForm.adminLoginName.value;  
var password=document.adminLoginForm.password.value;  
  
if (adminLoginName==null || adminLoginName==""){  
  alert("Name can't be blank");  
  return false;  
}else if(password==null || password==""){
	alert("Password can't be blank");  
	  return false; 
}else if(password.length<6){  
  alert("Password must be at least 6 characters long.");  
  return false;  
  }  
}  
</script>  

<body>
    <form:form name="adminLoginForm" action="adminLogin" method="POST" onsubmit=" return validateform()">
        <div align="center">
            <table>
                <tr>
                    <td>User Name</td>
                    <td><input type="text" name="adminLoginName" /></td>
                </tr>
                <tr>
                    <td>Password</td>
                    <td><input type="password" name="password" /></td>
                </tr>
                <tr>
                    <td></td>
                    <td><input type="submit" value="Submit" /></td>
                </tr>
            </table>
            
					<div style="color: red">${error}</div>
			
        </div>
    </form:form>
</body>
</html>