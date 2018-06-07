<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/base/include/tag_declare.jsp" %>
<script>
jQuery(function($) {
	
// 	$('#wrapper').addClass('login');
	var lForm$ = $('#loginForm');
	var psptNo$ = $('#psptNo');
	
	psptNo$.focus();
	
	lForm$.on('submit', function(e) {
		e.preventDefault();
		
		if(!cmn.checkRequired(psptNo$, '护照号码')) {
			return false;			
		}

		lForm$.attr("action", "${pageContext.request.contextPath}/modern/cmn/login/loginProc.json"); 
		lForm$.ajax({
			success:function(data) {
				var loginYn = data.loginYn;
				var error = data.error;
				
				if ("Y" === loginYn) {
					cmn.goUrl('/modern/cmn/main/main.do');
				} else {
					if (error === "${cmnConstants.loginNoId}") {
						alert("此护照号码不存在");
					} else {
						alert("登录失败");
					}
				}
			}
		});
	});
	
});
</script>

<div class="content">
	<form id="loginForm" method="post">
	<!-- Login -->
	<div class="login_wrap">
		<h1><span class="hide">LKP DPMS</span></h1>
		<p><input type="text" id="psptNo" name="psptNo" title="护照号码" placeholder="护照号码" style="width:100%" maxlength="50" autocomplete="off" class="inputEnNum" /></p>
		<div class="btn_wrap">
			<button type="submit" class="login" style="width:100%;" >登录</button>
		</div>
	</div>
	<!-- //Login -->
	</form>
</div>