<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/base/include/tag_declare.jsp" %>
<script>
(function($) {

	var travAgenInfoPopup$ = $('#travAgenInfoPopup');
	var sForm$ = $('#travAgenInfoPopup #searchForm');
	
	var cstGrid$ = $('#travAgenInfoPopup_grid').jqGrid({
		colNames : ['여행사코드','여행사명'],
		colModel : [
            {name:'travAgenCd',index:'SHOP_CD',width:30,align:'center'},
            {name:'travAgenNm',index:'SHOP_NM',width:40}
        ],
        height:580,
        onSelectRow: function(rowId) {
        	var rowData = $(this).getGridData(rowId);
        	travAgenInfoPopup$.data('callback').apply(this, [rowData.travAgenCd, rowData.travAgenNm]);
        	cmn.closePopup(travAgenInfoPopup$);
	   	}
	});
	
	<%-- [조회] 버튼 클릭  --%>
    $('#travAgenInfoPopup #btnSearch').on('click', function(e) {
        sForm$.submit();
	});
    
    <%-- 검색폼 submit  --%>
    sForm$.on('submit', function() {
    	sForm$.attr("action", "<c:url value='/classic/cmn/popup/selectTravAgenList.json'/>");
		var param = sForm$.serializeJson();
		cstGrid$.setGridParam({
			datatype : 'json',
			url : sForm$.attr('action'),
			postData:param
		}).trigger('reloadGrid');
		return false;
	});
    
    sForm$.submit();
	
}(jQuery));
</script>

<!-- Layer_popup -->
<div id="travAgenInfoPopup" class="layer_pop" style="max-width:800px; min-height:800px;">

	<div class="layer_content">
		<div class="top_area">
			<h1>여행사정보</h1>
			<div class="btn_wrap">
				<button type="button" id="btnSearch">조회</button>
				<button type="button">닫기</button>
			</div>
		</div>

		<form id="searchForm" method="post">
		<div class="table_wrap" style="width:100%;">
			<table>
				<colgroup>
					<col style="width:150px">
					<col style="">
				</colgroup>
				<tbody>
					<tr>
						<th scope="row">여행사구분</th>
						<td><cmn:codeCombo gbn="SHO01" name="shopDiv" optTxt="전체" /></td>
					</tr>
					<tr>
						<th scope="row">여행사코드/명</th>
						<td><input type="text" id="travAgenCdorNm" name="travAgenCdorNm" value="${travAgenCdorNm}" /></td>
					</tr>
				</tbody>
			</table>
		</div>
		</form>
		
		<div><table id="travAgenInfoPopup_grid"></table></div>
	</div>
</div>
<!-- //Layer_popup -->