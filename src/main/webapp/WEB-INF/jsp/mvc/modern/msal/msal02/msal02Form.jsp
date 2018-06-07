<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/base/include/tag_declare.jsp" %>
<script>
jQuery(function($) {
	var sForm$ = $('#saveForm');
	var today = cmn.todayFmt();
	var beforeWeek = cmn.getBeforeMonth();
	var saleGrid$ = $('#saleGrid');
	
	fn_init(); //초기화함수
	fn_initEvent(); //이벤트 선언
	fn_initGrid(); //그리드 사용시 초기화 선언
	
	fn_search();
	
	function fn_init() {
		$('#saleDtFr').val(beforeWeek);
		$('#saleDtTo').val(today);
	}
	
	function fn_initEvent() {

	    <%-- [신규] 버튼 클릭  --%>
	    $('#saveForm #btnNew').on('click', function(e) {
	    	fn_new();        	
		});
	    
	    <%-- [조회] 버튼 클릭  --%>
	    $('#saveForm #btnSearch').on('click', function(e) {
	    	fn_search();        	
		});
	    
	    <%-- [저장] 버튼 클릭  --%>
	    $('#saveForm #btnSave').on('click', function(e) {
	    	fn_save();        	
		});
	    
	    <%-- form submit  --%>
		sForm$.on('submit', function() {
	    	sForm$.attr("action", "<c:url value='/modern/msal/msal02/selectSaleList.json' />");
			var param = sForm$.serializeJson();
			saleGrid$.setGridParam({
				datatype : 'json',
				url : sForm$.attr('action'),
				postData:param
			}).trigger('reloadGrid');
			return false;
		});
	}
	
	function fn_initGrid() {
		<%-- 구매현황 jqgrid 생성 --%>
		saleGrid$.jqGrid({
			colNames : ['购物日期','免税店','Group Code','商品区分','购物金额(＄)','返点金额(￦)'],
			colModel : [
	            {name:'saleDt',index:'SALE_DT',width:'60px',align:'center',formatter:'formatDate'},
	            {name:'dutyShopNm',index:'DUTY_SHOP_NM',width:'50px',align:'center'},
	            {name:'grupCd',index:'GRUP_CD',width:'45px',align:'center'},
	            {name:'godDivNm',index:'GOD_DIV_NM',width:'45px',align:'center'},
	            {name:'saleAmt',index:'SALE_AMT',width:'65px',align:'right',formatter:'integer',formatoptions:{thousandsSeparator:","}},
	            {name:'feeAmt',index:'FEE_AMT',width:'60px',align:'right',formatter:'integer',formatoptions:{thousandsSeparator:","}}
	        ],
	        autowidth: true,
	        footerrow: true,
	        userDataOnFooter: true,
	        gridComplete: function () {
				var grid$ = $(this);
				
				<%-- summary --%>
				var sumSaleAmt = grid$.getCol("saleAmt", false, "sum");
	    	    var sumFeeAmt = grid$.getCol("feeAmt", false, "sum");
	    	    grid$.jqGrid("footerData", "set", {
	    	    	"saleDt": "合计",
		   	      	"saleAmt": sumSaleAmt,
		   	     	"feeAmt": sumFeeAmt
	   	      	}, true);
	    	    <%-- summary td안에, 약간의 오른쪽여백을 주기 위함 --%>
	    	    $('#gbox_saleGrid').find(".ui-jqgrid-ftable tr td").append("&nbsp;");
	    	    $('#gbox_saleGrid').find(".ui-jqgrid-ftable tr td:eq(3)").css("text-align", "center");
			},
	        onSelectRow: function(rowId) {
		   	},
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
			<li class="on"><a href="<c:url value="/modern/msal/msal02/msal02Form.do" />">购物内容</a></li>
		</ul>
	</div>
	<h2>购物内容</h2>
	<div class="table_wrap">
		<form id="saveForm" method="post">
		<table>
			<caption>구매정보등록 테이블</caption>
			<colgroup>
				<col width="82px" />
				<col width="" />
			</colgroup>
			<tbody>
				<tr>
					<th scope="row">* 购物日期</th>
					<td>
						<input type="text" id="saleDtFr" name="saleDtFr" class="inputDatePicker" />
						~ <input type="text" id="saleDtTo" name="saleDtTo" class="inputDatePicker" />
						&nbsp;&nbsp;<button type="button" id="btnSearch"  class="btn_base">搜索</button>
					</td>
				</tr>
			</tbody>
		</table>
		</form>
	</div>
	<div class="grid_wrap">
		<table id="saleGrid"></table>
	</div>
	

</div>
<!-- //Contents -->