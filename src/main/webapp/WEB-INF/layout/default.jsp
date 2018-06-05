<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html >
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<tiles:insertAttribute name="title"/>
	</head>
	<body>
	
		<tiles:insertAttribute name="header" />
	<br />
	<br />
	<tiles:insertAttribute name="content" />
	<br />
	<br />
	<tiles:insertAttribute name="footer" />
	
	
	
	
	</body>
</html>