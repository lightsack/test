<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/base/include/tag_declare.jsp" %>
<script type="text/javascript">
function isMobile(){
	var UserAgent = navigator.userAgent;
	if (UserAgent.match(/iPhone|iPod|Android|Windows CE|BlackBerry|Symbian|Windows Phone|webOS|Opera Mini|Opera Mobi|POLARIS|IEMobile|lgtelecom|nokia|SonyEricsson/i) != null || UserAgent.match(/LG|SAMSUNG|Samsung/) != null) {
		return true;
	} else {
		return false;
	}
}

if (isMobile()){
	/* location.href = "${pageContext.request.contextPath}/modern/cmn/login/login.do"; */		<%-- MOBILE --%>
	location.href = "${pageContext.request.contextPath}/classic/cmn/login/login.do";	<%-- PC --%>
} else {
	location.href = "${pageContext.request.contextPath}/classic/cmn/login/login.do";	<%-- PC --%>
}

</script>