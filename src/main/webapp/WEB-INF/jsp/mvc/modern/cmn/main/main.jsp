<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/base/include/tag_declare.jsp" %>
<script>
jQuery(function($) {
	var today = cmn.todayFmt();
	var beforeWeek = cmn.getBeforeMonth();
	var sForm$ = $('#searchForm');
	var boardGrid$ = $('#boardGrid');
	
	fn_init(); //초기화함수
	fn_initEvent(); //이벤트 선언
	fn_initGrid(); //그리드 사용시 초기화 선언
	
	fn_search();
	
	function fn_init() {
	}
	
	function fn_initEvent() {	    
	    <%-- form submit  --%>
		sForm$.on('submit', function() {
	    	sForm$.attr("action", "<c:url value='/modern/cmn/main/selectBoardList.json' />");
			var param = sForm$.serializeJson();
			boardGrid$.setGridParam({
				datatype : 'json',
				url : sForm$.attr('action'),
				postData:param
			}).trigger('reloadGrid');
			return false;
		});
	}
	
	function fn_initGrid() {
		<%-- 구매현황 jqgrid 생성 --%>
		boardGrid$.jqGrid({
			colNames : ['主题','日期城市','发布日期','公告板师','注册号'],
			colModel : [
	            {name:'title',index:'TITLE',width:'200px',align:'left'},
	            {name:'regTime',index:'REG_TIME',width:'70px',align:'center'},
	            {name:'regDt',index:'REG_DT',width:'1px',hidden:true},
	            {name:'boardDiv',index:'BOARD_DIV',width:'1px',hidden:true},
	            {name:'regNo',index:'REG_NO',width:'1px',hidden:true}
	        ],
	        autowidth: true
		});
	}

	function fn_new() {
	}
	
	function fn_save(){
	}
	
	function fn_search(){
		sForm$.submit();
	}
});
</script>

<!-- Contents -->
<div class="content">
	<div class="tab_wrap">
		<ul>
			<li><a href="<c:url value="/modern/msal/msal01/msal01Form.do" />">登记购物信息</a></li>
			<li><a href="<c:url value="/modern/msal/msal02/msal02Form.do" />">购物内容</a></li>
		</ul>
	</div>
	<h2>购物信息</h2>
	<div class="table_wrap">
		<form id="searchForm" method="post">
			<input type="hidden" id="paramStr" name="paramStr" />
		</form>
	</div>
	<div class="grid_wrap">
		<table id="boardGrid"></table>
	</div>
</div>
<!-- //Contents -->