<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html >
<html>
<head>
    <title>Welcome Security with Spring Boot!</title>
	<meta charset="utf-8"/>
	<meta name="viewport" content="width=device-width, initial-scale=1"/>
	<link rel="stylesheet"
		href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"/>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body class="container" style="margin:50px">
	
	<div class="row col-sm-6" 
				style="border: 1px ridge #003312; padding:20px; float: none; margin: 0 auto;">
		<h5 class="text-center" style="font-size: 25px">Sign In</h5>
		<c:if test="${param.error}">
			<div>
				<p style="color: red">UserName or PassWord is wrong. Please
					check again!</p>
			</div> 
		</c:if>
		<c:if test="${param.logout}">
			<div >
				<h1 style="color: blue">Logged out.</h1>
			</div>
		</c:if>
		<form action="login" method="post">
			<div class="form-group">
				<label for="username">User Name: </label>
				<input type="text" class="form-control" id="username" placeholder="Enter UserName" name="username"/>
			</div>
			<div class="form-group">
				<label for="password">Password: </label>
				<input type="password" class="form-control" id="password" placeholder="Enter Password" name="password"/>
			</div>
			<button type="submit" class="btn btn-primary btn-block">Submit</button>
		</form>
	</div>
	
	
</body>
</html>