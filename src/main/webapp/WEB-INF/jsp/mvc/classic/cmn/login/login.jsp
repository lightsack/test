<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/base/include/tag_declare.jsp" %>
<script>
jQuery(function($) {

	var lForm$ = $('#loginForm');
	var userId$ = $('#userId');
	var psWd$ = $('#psWd');
	
	userId$.focus();
	
	lForm$.on('submit', function(e) {
		e.preventDefault();
		
		if(!cmn.checkRequired(userId$, '아이디')) {
			return false;			
		}
		if(!cmn.checkRequired(psWd$, '비밀번호')) {
			return false;			
		}

		lForm$.attr("action", "${pageContext.request.contextPath}/classic/cmn/login/loginProc.json"); 
		lForm$.ajax({
			success:function(data) {
				var loginYn = data.loginYn;
				var error = data.error;
				
				if ("Y" === loginYn) {
					cmn.goUrl('/classic/cmn/main/main.do');
				} else {
					if (error === "${cmnConstants.loginNoId}") {
						alert("등록되지 않은 아이디 입니다");
					} else if (error === "${cmnConstants.loginPasswordNotEqual}") {
						alert("비밀번호가 맞지 않습니다");
					} else {
						alert("로그인 실패");
					}
				}
			}
		});
	});
});
</script>


<div id="wrapper">
	<section id="container" class="login">
		<!-- Contents -->
		<div class="contents" style="background:#fff;">
			<form id="loginForm" method="post">
			<div class="login_wrap">
				<h1><img src="<c:url value='/resources/classic/images/login_logo.jpg' />" alt="FSS WMS" /></h1>
				<dl>
					<dt class="hide">아이디</dt>
					<dd><input type="text" id="userId" name="userId" title="아이디" placeholder="아이디" maxlength="20" autocomplete="off" /></dd>
					<dt class="hide">패스워드</dt>
					<dd><input type="password" id="psWd" name="psWd" title="비밀번호" placeholder="비밀번호" maxlength="30" autocomplete="off" /></dd>
				</dl>
				<button type="submit" class="btn_login" style="width:100%;">로그인</button>
			</div>
			</form>
		</div>
		
		<!-- //Contents -->
	</section>

</div>
