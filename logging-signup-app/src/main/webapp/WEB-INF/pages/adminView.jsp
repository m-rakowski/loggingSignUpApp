<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Listing user sign-in logs</title>
</head>
<style>
td
{
border: 1px solid black;
}
</style>
<body>
<div align="center">
	<h2>User sign-in logs:</h2>
	    <a href="/logging-signup-app/">go back</a>
	<table >
		<c:forEach items="${logs}" var="post">
			<tr>
				<td>${post.timestamp}</td>
				<td>${post.name}</td>
				<td>${post.email}</td>
				<td>${post.role}</td>
			</tr>
		</c:forEach>
	</table>
	
</div>

</body>
</html>