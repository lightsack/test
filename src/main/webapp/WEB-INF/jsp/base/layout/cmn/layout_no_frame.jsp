<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<title><tiles:getAsString name="title" /></title>
<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" />
<meta http-equiv='Cache-Control' content='no-cache'>
<meta http-equiv='Pragma' content='no-cache'>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/resources/classic/images/login_logo.jpg" />
<tiles:insertAttribute name="resource" />
</head>
<body>
<tiles:insertAttribute name="content" />
</body>
</html>