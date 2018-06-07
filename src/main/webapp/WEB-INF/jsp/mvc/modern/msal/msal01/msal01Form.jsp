<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/base/include/tag_declare.jsp" %>
<script>
jQuery(function($) {
	
	var sForm$ = $('#saveForm');
	var today = cmn.todayFmt();
	var saleGrid$ = $('#saleGrid');
	
	fn_init(); //초기화함수
	fn_initEvent(); //이벤트 선언
	fn_initGrid(); //그리드 사용시 초기화 선언
	
	//fn_new();
	fn_search();
	
	function fn_init() {
		$('#saleDt').val(today);
	}
	
	function fn_initEvent() {

	    <%-- [신규] 버튼 클릭  --%>
	    $('#saveForm #btnNew').on('click', function(e) {
	    	fn_new();        	
		});
	    
	    <%-- [저장] 버튼 클릭  --%>
	    $('#saveForm #btnSave').on('click', function(e) {
	    	fn_save();        	
		});
	    
	    <%-- form submit  --%>
		sForm$.on('submit', function() {
	    	sForm$.attr("action", "<c:url value='/modern/msal/msal01/selectSaleList.json' />");
			var param = sForm$.serializeJson();
			saleGrid$.setGridParam({
				datatype : 'json',
				url : sForm$.attr('action'),
				postData:param
			}).trigger('reloadGrid');
			return false;
		});
		
		$('#saveForm #saleDt').on('onSelect', function(e) {
			cmn.ajax({
				url: '<c:url value="/modern/msal/msal01/selectGrupInfo.json"/>',
				data: {
	    			'saleDt': $('#saveForm #saleDt').val(),
	    			'dutyShopCd': $(':radio[name="dutyShopCd"]:checked').val()
				},
				success: function(data) {
					if (data.list.grupCd == undefined) {
						cmn.alert("此Group NO.不存在");
						$('#saveForm #grupCd').val("");
					} else {
						$('#saveForm #grupCd').val(data.list.grupCd);
					}
		        }
		    });	
		});
		
		$('input:radio[name=dutyShopCd]').on('change', function(e) {
			if ($(':radio[name="dutyShopCd"]:checked').val() == undefined) {
				$('#saveForm #grupCd').val("");
				return;
			}		
	    	cmn.ajax({
				url: '<c:url value="/modern/msal/msal01/selectGrupInfo.json"/>',
				data: {
	    			'saleDt': $('#saveForm #saleDt').val(),
	    			'dutyShopCd': $(':radio[name="dutyShopCd"]:checked').val()
				},
				success: function(data) {				
					if (data.list.grupCd == undefined) {
						cmn.alert("此Group NO.不存在");
						$('#saveForm #grupCd').val("");
					} else {
						$('#saveForm #grupCd').val(data.list.grupCd);
					}
		        }
		    });
		});
	}
	
	function fn_initGrid() {
		<%-- 구매현황 jqgrid 생성 --%>
		saleGrid$.jqGrid({
			colNames : ['购物日期','免税店Code','免税店','商品区分编码','商品区分','购物金额','修改','删除'],
			colModel : [
	            {name:'saleDt',index:'SALE_DT',width:'60px',align:'center',formatter:'formatDate'},
	            {name:'dutyShopCd',index:'DUTY_SHOP_CD',width:'50px',align:'center',hidden:true},
	            {name:'dutyShopNm',index:'DUTY_SHOP_NM',width:'50px',align:'center'},
	            //{name:'grupCd',index:'GRUP_CD',width:'45px',align:'center'},
	            {name:'godDiv',index:'GOD_DIV',width:'45px',align:'center',hidden:true},
	            {name:'godDivNm',index:'GOD_DIV_NM',width:'45px',align:'center'},
	            {name:'saleAmt',index:'SALE_AMT',width:'60px',align:'right',formatter:'integer',formatoptions:{thousandsSeparator:","}},
	            //{name:'feeAmt',index:'FEE_AMT',width:'55px',align:'right',formatter:'integer',formatoptions:{thousandsSeparator:","}},
	            {name:'update',width:'30px',align:'center',sortable:false,formatter:'button',formatoptions:{btnText:'修改', rowId:true, attr:{'name':'btnUpdate'}}},
	            {name:'delete',width:'30px',align:'center',sortable:false,formatter:'button',formatoptions:{btnText:'删除', rowId:true, attr:{'name':'btnDel'}}}
	        ],
	        autowidth: true,
	        footerrow: true,
	        userDataOnFooter: true,
	        gridComplete: function () {
				var grid$ = $(this);
				
				//현재 그리드 카운트 입력
				$('#saveForm #totSaleCnt').text(grid$.getDataIDs().length);

				<%-- [수정] 버튼 클릭 --%>        	
	        	$('[data-name=btnUpdate]').on('click', function() {
	        		var rowId = $(this).attr('row-id');
	            	var rowData = grid$.getGridData(rowId);
	            	
	            	if(rowData.wrkStat=='2') {
	            		cmn.alert("已结算，无法修改", function(){});
	            		return;
	            	}
	            	
	            	$('#saveForm #saleDt').val(rowData.saleDt).trigger('blur').datepicker("option", "disabled", true);
	            	$('#saveForm #seq').val(rowData.seq);
	            	$('#saveForm #orgSaleDt').val(rowData.saleDt);
	            	$('#saveForm #saleAmt').val(rowData.saleAmt).trigger('blur');
	            	$('#saveForm #grupCd').val(rowData.grupCd);
 	            	$('input:radio[name=dutyShopCd]:input[value='+rowData.dutyShopCd+']').prop("checked", true);
					//$('input:radio[name=dutyShopCd]').attr('disabled', true); //면세점 변경가능하도록 수정
	            	$('input:radio[name=godDiv]:input[value='+rowData.godDiv+']').prop("checked", true);	
	        	});
	        	
				<%-- [삭제] 버튼 클릭 --%>        	
	        	$('[data-name=btnDel]').on('click', function() {
	        		var rowId = $(this).attr('row-id');
	        		var rowData = grid$.getGridData(rowId);
	            	
	            	if(rowData.wrkStat=='2') {
	            		cmn.alert("已结算，无法删除", function(){});
	            		return;
	            	}
	            	
	        		fn_delete(rowId);
	        	});
        	
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
				    	    
			}
		});
	}
	
	function fn_new() {
		<%-- input type=text 초기화 --%>
        $('#saveForm').find(':text').each(function(idx, item){
			$(item).val('');
		});
        $('#saveForm').find(':hidden').each(function(idx, item){
			$(item).val('');
		});

        $('#saveForm #saleDt').val(today).trigger('blur').datepicker("option", "disabled", false);
        $("input:radio[name=dutyShopCd]:input[value=A]").prop("checked", true);
        $('input:radio[name=dutyShopCd]').attr('disabled', false);
        $('input:radio[name=godDiv]:input[value=1]').prop("checked", true);
        $('#saveForm #saleAmt').val("");
        
        //$('input:radio[name=dutyShopCd]').change();
	}
	
	function fn_save(){
		$('#saveForm #saleDt').trigger('blur').datepicker("option", "disabled", false);
		$('input:radio[name=dutyShopCd]').attr('disabled', false);
		
    	<%-- validation 1 --%>
    	if(!cmn.checkRequired($('#saveForm #saleDt'), "购物日期"))	return false;
    	if(!cmn.checkRequired($('#saveForm #grupCd'), "Group Code"))	return false;
    	if(!cmn.checkRequired($('#saveForm #saleAmt'), "购物金额")) return false;
    	
    	<%-- 기본 데이터 --%>
    	var saveData = $('#saveForm').serializeJson(); 
    	
    	<%-- 구매정보 데이터 저장 --%>
    	cmn.ajax({
			url: '<c:url value="/modern/msal/msal01/saveSaleInfo.json"/>',
			data: {
    			'saveData': JSON.stringify(saveData)
			},
			success: function(data) {
				cmn.alert("已经被登记", function () {
					//수정시 입력된 데이터값 초기화
					$('#saveForm #seq').val("");
	            	$('#saveForm #orgSaleDt').val("");
	            	
					$('#saveForm #saleAmt').val(""); //저장이후 구매금액만 초기화
					fn_search();
				});
	        }
	    });
	}
	
	function fn_delete(rowId){
		<%-- 기본 데이터 --%>
		var rowData = $('#saleGrid').getGridData(rowId);
		
		cmn.confirm('确定要删除吗？', function() {
			<%-- 구매정보 데이터 삭제 --%>
			cmn.ajax({
				url: '<c:url value="/modern/msal/msal01/deleteSaleInfo.json"/>',
				data: {
					'saleDt': rowData.saleDt,
					'psptNo': rowData.psptNo,
					'seq':rowData.seq
				},
				success: function(data) {
					cmn.alert("确定要删除吗？", function () {
						
						//삭제이후 재조회 수행
						fn_search();
						fn_new();
					});
		        }
		    });
		});
	}
	
	function fn_search(){
		sForm$.submit();
		//fn_new();
	}
});



</script>

<!-- Contents -->
<div class="content">
	<div class="tab_wrap">
		<ul>
			<li class="on"><a href="<c:url value="/modern/msal/msal01/msal01Form.do" />">登记购物信息</a></li>
			<li><a href="<c:url value="/modern/msal/msal02/msal02Form.do" />">购物内容</a></li>
		</ul>
	</div>

	<h2>登记购物信息</h2>
	<div class="table_wrap">
		<form id="saveForm" method="post">
		<input type="hidden" id="orgSaleDt" name="orgSaleDt" />
		<input type="hidden" id="seq" name="seq" />
		<table>
			<caption>구매정보등록 테이블</caption>
			<colgroup>
				<col width="95px" />
				<col width="" />
			</colgroup>
			<tbody>
				<tr>
					<th scope="row">* 购物日期</th>
					<td>
						<div class="date">
							<input type="text" id="saleDt" name="saleDt" class="inputDatePicker" style="width:140px" />
						</div>
						<div class="ea_wrap">
							输入数量 : <span id="totSaleCnt">01</span>
						</div>
					</td>
				</tr>
				<tr>
					<th scope="row">* 免税店</th>
					<td>
						<div class="rdo_wrap row">
							<span class="rdo"><input type="radio" id="dutyShopCd01" name="dutyShopCd"  value="A" checked="checked"/><label for="dutyShopCd01">乐天</label></span>
							<span class="rdo"><input type="radio" id="dutyShopCd02" name="dutyShopCd"  value="B" /><label for="dutyShopCd02">都塔</label></span>
							<span class="rdo"><input type="radio" id="dutyShopCd03" name="dutyShopCd"  value="C" /><label for="dutyShopCd03">韩华格乐丽雅</label></span>
							<span class="rdo"><input type="radio" id="dutyShopCd04" name="dutyShopCd"  value="D" /><label for="dutyShopCd04">新世界</label></span>
							<span class="rdo"><input type="radio" id="dutyShopCd05" name="dutyShopCd"  value="E" /><label for="dutyShopCd05">新罗</label></span>
							<span class="rdo"><input type="radio" id="dutyShopCd06" name="dutyShopCd"  value="F" /><label for="dutyShopCd06">新罗爱宝客</label></span>
							<span class="rdo"><input type="radio" id="dutyShopCd07" name="dutyShopCd"  value="G" /><label for="dutyShopCd07">东和</label></span>							
							<span class="rdo"><input type="radio" id="dutyShopCd08" name="dutyShopCd"  value="H" /><label for="dutyShopCd08">乐天济州</label></span>
							<span class="rdo"><input type="radio" id="dutyShopCd09" name="dutyShopCd"  value="I" /><label for="dutyShopCd09">新罗济州</label></span>
							<span class="rdo"><input type="radio" id="dutyShopCd10" name="dutyShopCd"  value="J" /><label for="dutyShopCd10">新世界釜山</label></span>
							<span class="rdo"><input type="radio" id="dutyShopCd11" name="dutyShopCd"  value="K" /><label for="dutyShopCd11">东和釜山</label></span>
						</div>
					</td>
				</tr>
				<tr>
					<th scope="row">* Group Code</th>
					<td><input type="text" id="grupCd" name="grupCd" style="width:100px;text-transform:uppercase;" readonly /></td>
				</tr>
				<tr>
					<th scope="row">* 商品区分</th>
					<td>
						<div class="rdo_wrap">
							<span class="rdo"><input type="radio" id="godDiv01" name="godDiv" value="1" checked="checked" /><label for="godDiv01">收益</label></span>
							<span class="rdo"><input type="radio" id="godDiv02" name="godDiv" value="2"  /><label for="godDiv02">国内</label></span>
							<span class="rdo"><input type="radio" id="godDiv03" name="godDiv" value="3"  /><label for="godDiv03">L/V</label></span>
						</div>
					</td>
				</tr>
				<tr>
					<th scope="row">* 购物金额</th>
					<td>
						<div class="int_wrap">
							<input type="number" id="saleAmt" name="saleAmt" style="width:140px" class="inputAmount" />
							<div class="buy_wrap">
								<button type="button" id="btnSave" class="btn_base">登录</button>
								<button type="button" id="btnNew" class="btn_initi">初始化</button>
							</div>
						</div>
					</td>
				</tr>
			</tbody>
		</table>
		</form>
	</div>

	<h2>购买状态</h2>
	
	<div class="grid_wrap">
		<table id="saleGrid"></table>
	</div>
				
</div>
<!-- //Contents -->