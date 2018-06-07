<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/base/include/tag_declare.jsp" %>
<script>
jQuery(function($) {
	
	$('.btn_nav').click(function() {
		$('#header nav').addClass('open');
		$('.dim_header').fadeIn();
	});
	
	$('.dim_header').click(function() {
		$('#header nav').removeClass('open');
		$('.dim_header').fadeOut();
	});
	
	$('.btn_top').click(function(event) {
		event.preventDefault();
		$('html, body').animate({scrollTop: 0}, {duration:500});
		return false;
	});
	
	<%-- main  --%>
    $('#btnMain').on('click', function() {
		cmn.goUrl('/modern/cmn/main/main.do');
    });
	
	<%-- 로그아웃  --%>
    $('#btnLogout').on('click', function() {
    	if(confirm("确定要注销吗？")) {
			cmn.ajax({
				url:'<c:url value="/modern/cmn/login/logout.json"/>',
				success:function(data) {
					cmn.goUrl('/modern/cmn/login/login.do');
		        }
		    });
    	}
    });
    
});
</script>

<h1><button type="button" id="btnMain"><span class="hide">FSS WMS</span></button></h1>

<button type="button" class="btn_nav"><span class="hide">Open</span></button>
<nav>
	<div class="nav_wrap">
		<p>${sessionScope[cmnConstants.sessionCust].custNm} 欢迎您访问~</p>
		<ul>
			<li><button type="button" id="btnLogout" class="log">注销</button></li>
			<!-- <li><button type="button" class="log">로그인</button></li> -->
			<li><a href="<c:url value="/modern/msal/msal01/msal01Form.do" />">登记购物信息</a></li>
			<li><a href="<c:url value="/modern/msal/msal02/msal02Form.do" />">购物内容</a></li>
		</ul>
	</div>
</nav>

<div class="dim_header"></div>

<button type="button" class="btn_top"><span class="hide">Top</span></button>