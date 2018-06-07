<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/base/include/tag_declare.jsp" %>
<script>
jQuery(function($) {
	
	<%-- 로그아웃  --%>
    $('#btnLogout').on('click', function() {
    	if(confirm("로그아웃 하시겠습니까?")) {
			cmn.ajax({
				url:'<c:url value="/classic/cmn/login/logout.json"/>',
				success:function(data) {
					cmn.goUrl('/classic/cmn/login/login.do');
		        }
		    });
    	}
    });
    
    
});
</script>

<c:set var="menuList" scope="page" value="${sessionScope[cmnConstants.sessionUserProgAuthList]}" />
<div class="header">
	<h1>
		<a href="#none"><img src="<c:url value='/resources/classic/images/h1_logo.png' />" alt="FSS WMS"></a>
	</h1>
	<div class="util_menu">
		<p class="name"><span>${sessionScope[cmnConstants.sessionUserNm]}</span> 님</p>
		<!-- <a href="#none">로그인</a>  -->
		<button type="button" id="btnLogout">로그아웃</button>
	</div>

	<div class="gnb">
		<ul>
			<c:set var="preUpProgId" value="-1"/>
			<c:set var="curTopMenuNo" value="0" />
			<c:set var="curSubMenuNo" value="0" />
			<c:forEach items="${menuList}" var="menu" varStatus="status">
			<fmt:parseNumber var="upProgHasCnt" value="${menu.upProgHasCnt}" integerOnly="true" />
			<fmt:parseNumber var="ulCnt" value="${upProgHasCnt%5==0 ? upProgHasCnt/5 : upProgHasCnt/5 +1}" integerOnly="true" />
			<c:if test="${preUpProgId ne menu.upProgId}">
			<c:set var="curTopMenuNo" value="${curTopMenuNo+1}" />
			<c:set var="curSubMenuNo" value="0" />
			<li class="m<fmt:formatNumber value='${curTopMenuNo}' minIntegerDigits='2' />"><a style="cursor:pointer;">${menu.upProgNm}</a>
				<fmt:parseNumber var="depthWidth" value="${ulCnt*180}" integerOnly="true" />
				<div class="depth" style="width:${depthWidth}px">
			</c:if>
			<c:set var="curSubMenuNo" value="${curSubMenuNo+1}" />
				<c:if test="${(curSubMenuNo == 1) or ((curSubMenuNo-1)%5 == 0)}">
					<ul>
				</c:if>
						<li><a style="cursor:pointer;" class="subMenu" data-progNm="${menu.progNm}" data-progUrl="${menu.progUrl}">${menu.progNm}</a></li>
				<c:if test="${(curSubMenuNo%5 == 0) or (curSubMenuNo eq menu.upProgHasCnt)}">
					</ul>
				</c:if>
			<c:if test="${curSubMenuNo eq menu.upProgHasCnt}">
				</div>
			</li>
			</c:if>
			<c:set var="preUpProgId" value="${menu.upProgId}"/>
			</c:forEach>
		</ul>
	</div>

</div>
<!-- //Header -->