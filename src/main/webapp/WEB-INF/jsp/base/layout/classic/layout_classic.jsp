<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/base/include/tag_declare.jsp" %>
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
	<div id="skip_navi">
		<a href="#container">본문 바로가기</a>
		<a href="#nav">메뉴 바로가기</a>
	</div>

	<div id="wrapper">
		<section id="header">
			<!-- Header -->
			<tiles:insertAttribute name="header" />
			<!-- //Header -->
		</section>

		<section id="container">
			<!-- Contents -->
			<tiles:insertAttribute name="content" />
			<!-- //Contents -->
		</section>

		<!-- footer -->
		<footer id="footer">
			<tiles:insertAttribute name="footer" />
		</footer>
		<!-- //footer -->
	</div>
</body>
</html>