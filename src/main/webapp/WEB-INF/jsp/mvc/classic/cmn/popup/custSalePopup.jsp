<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/base/include/tag_declare.jsp" %>
<style>
	.ui-jqgrid .ui-jqgrid-htable th {border:0 none !important;height:20px !important; line-height:24px !important;border-left:1px solid #dadada !important; background:#646464; color:#fff !important; font-size:13px !important; text-align:center !important;}
	.ui-jqgrid tr.jqgrow td {line-height:17px;height:17px;padding:0px;background:#ececec;font-size: 13px;border-bottom:0;}
	.ui-jqgrid .ui-jqgrid-ftable td{padding:0px;background:#888;color:#fff;line-height:24px;text-align:right;}
</style>
<script>

jQuery(function($) {
	 

	var dutyShopAGrid$ = $('#duty_shop_a');
	var dutyShopBGrid$ = $('#duty_shop_b');
	var dutyShopCGrid$ = $('#duty_shop_c');
	var dutyShopDGrid$ = $('#duty_shop_d');
	var dutyShopEGrid$ = $('#duty_shop_e');
	var dutyShopFGrid$ = $('#duty_shop_f');
	var dutyShopGGrid$ = $('#duty_shop_g');
	var dutyShopHGrid$ = $('#duty_shop_h');
	var dutyShopIGrid$ = $('#duty_shop_i');
	var dutyShopJGrid$ = $('#duty_shop_j');
	var dutyShopKGrid$ = $('#duty_shop_k');
	
	<%-- Onload Event --%>
	fn_init_grid();
	fn_initEvent();
	fn_searchCustSale();
	
	
	function fn_searchCustSale() {
		<%-- 기본 데이터 --%>
		var searchData = $('#custSalePopup #searchForm').serializeJson();
		
		cmn.ajax({
			url: '<c:url value="/classic/cmn/popup/selectCustSale.json"/>',
			data: {
				"searchData" : JSON.stringify(searchData)
			},
			success: function(data) {
				var saleList = data.custSaleList;
				
				var dutyShopAdata = new Array();
				var dutyShopBdata = new Array();
				var dutyShopCdata = new Array();
				var dutyShopDdata = new Array();
				var dutyShopEdata = new Array();
				var dutyShopFdata = new Array();
				var dutyShopGdata = new Array();
				var dutyShopHdata = new Array();
				var dutyShopIdata = new Array();
				var dutyShopJdata = new Array();
				var dutyShopKdata = new Array();
				
				var totDutyShop = 0;
				var totDutyShopGod1 = 0;
				var totDutyShopGod2 = 0;
				var totDutyShopGod3 = 0;
				var totCust = 0;
				var totCustGod1 = 0;
				var totCustGod2 = 0;
				var totCustGod3 = 0;
				var totCustW = 0;
				var totCustGod1W = 0;
				var totCustGod2W = 0;
				var totCustGod3W = 0;
				
				for (var i=0; i<saleList.length; i++) {
					if(saleList[i].dutyShopCd == '1001'){
						dutyShopAdata.push(saleList[i]);
						$('#dutyShopNmA').text(saleList[i].dutyShopNm);
					} else if(saleList[i].dutyShopCd == '1002'){
						dutyShopBdata.push(saleList[i]);
						$('#dutyShopNmB').text(saleList[i].dutyShopNm);
					} else if(saleList[i].dutyShopCd == '1003'){
						dutyShopCdata.push(saleList[i]);
						$('#dutyShopNmC').text(saleList[i].dutyShopNm);
					} else if(saleList[i].dutyShopCd == '1004'){
						dutyShopDdata.push(saleList[i]);
						$('#dutyShopNmD').text(saleList[i].dutyShopNm);
					} else if(saleList[i].dutyShopCd == '1005'){
						dutyShopEdata.push(saleList[i]);
						$('#dutyShopNmE').text(saleList[i].dutyShopNm);
					} else if(saleList[i].dutyShopCd == '1006'){
						dutyShopFdata.push(saleList[i]);
						$('#dutyShopNmF').text(saleList[i].dutyShopNm);
					} else if(saleList[i].dutyShopCd == '1007'){
						dutyShopGdata.push(saleList[i]);
						$('#dutyShopNmG').text(saleList[i].dutyShopNm);
					} else if(saleList[i].dutyShopCd == '1008'){
						dutyShopHdata.push(saleList[i]);
						$('#dutyShopNmH').text(saleList[i].dutyShopNm);
					} else if(saleList[i].dutyShopCd == '1009'){
						dutyShopIdata.push(saleList[i]);
						$('#dutyShopNmI').text(saleList[i].dutyShopNm);
					} else if(saleList[i].dutyShopCd == '1010'){
						dutyShopJdata.push(saleList[i]);
						$('#dutyShopNmJ').text(saleList[i].dutyShopNm);
					}else if(saleList[i].dutyShopCd == '1011'){
						dutyShopKdata.push(saleList[i]);
						$('#dutyShopNmK').text(saleList[i].dutyShopNm);
					}
					totDutyShopGod1 += saleList[i].godDiv1SaleAmt;
					totDutyShopGod2 += saleList[i].godDiv2SaleAmt;
					totDutyShopGod3 += saleList[i].godDiv3SaleAmt;
					totCustGod1 += saleList[i].godDiv1BfFeeAmt*10;
					totCustGod2 += saleList[i].godDiv2BfFeeAmt*10;
					totCustGod3 += saleList[i].godDiv3BfFeeAmt*10;
					totCustGod1W += saleList[i].godDiv1FeeAmt*10;
					totCustGod2W += saleList[i].godDiv2FeeAmt*10;
					totCustGod3W += saleList[i].godDiv3FeeAmt*10;
				}
				totCustGod1 = totCustGod1/10;
				totCustGod2 = totCustGod2/10;
				totCustGod3 = totCustGod3/10;
				
				totCustGod1W = totCustGod1W/10;
				totCustGod2W = totCustGod2W/10;
				totCustGod3W = totCustGod3W/10;
				
				totDutyShop = totDutyShopGod1+totDutyShopGod2+totDutyShopGod3;
				totCust = Math.round((totCustGod1+totCustGod2+totCustGod3)*10)/10.0;
				totCustW = totCustGod1W+totCustGod2W+totCustGod3W;
				
				$('#dutyShopTotSum').text(fn_makeComma(totDutyShop));
				$('#dutyShopGod1Sum').text(fn_makeComma(totDutyShopGod1));
				$('#dutyShopGod2Sum').text(fn_makeComma(totDutyShopGod2));
				$('#dutyShopGod3Sum').text(fn_makeComma(totDutyShopGod3));
				
				$('#custTotSum').text(fn_makeComma(totCust));
				$('#custGod1Sum').text(fn_makeComma(totCustGod1));
				$('#custGod2Sum').text(fn_makeComma(totCustGod2));
				$('#custGod3Sum').text(fn_makeComma(totCustGod3));
				$('#custTotSumW').text(fn_makeComma(totCustW));
				$('#custGod1SumW').text(fn_makeComma(totCustGod1W));
				$('#custGod2SumW').text(fn_makeComma(totCustGod2W));
				$('#custGod3SumW').text(fn_makeComma(totCustGod3W));
				
				dutyShopAGrid$.jqGrid('setGridParam', { 
		            datatype: 'local',
		            data:dutyShopAdata
		        }).trigger("reloadGrid");
				
				dutyShopBGrid$.jqGrid('setGridParam', { 
		            datatype: 'local',
		            data:dutyShopBdata
		        }).trigger("reloadGrid");
				
				dutyShopCGrid$.jqGrid('setGridParam', { 
		            datatype: 'local',
		            data:dutyShopCdata
		        }).trigger("reloadGrid");
				
				dutyShopDGrid$.jqGrid('setGridParam', { 
		            datatype: 'local',
		            data:dutyShopDdata
		        }).trigger("reloadGrid");
				
				dutyShopEGrid$.jqGrid('setGridParam', { 
		            datatype: 'local',
		            data:dutyShopEdata
		        }).trigger("reloadGrid");
				
				dutyShopFGrid$.jqGrid('setGridParam', { 
		            datatype: 'local',
		            data:dutyShopFdata
		        }).trigger("reloadGrid");
				
				dutyShopGGrid$.jqGrid('setGridParam', { 
		            datatype: 'local',
		            data:dutyShopGdata
		        }).trigger("reloadGrid");
				
				dutyShopHGrid$.jqGrid('setGridParam', { 
		            datatype: 'local',
		            data:dutyShopHdata
		        }).trigger("reloadGrid");
				
				dutyShopIGrid$.jqGrid('setGridParam', { 
		            datatype: 'local',
		            data:dutyShopIdata
		        }).trigger("reloadGrid");
				
				dutyShopJGrid$.jqGrid('setGridParam', { 
		            datatype: 'local',
		            data:dutyShopJdata
		        }).trigger("reloadGrid");
				
				dutyShopKGrid$.jqGrid('setGridParam', { 
		            datatype: 'local',
		            data:dutyShopKdata
		        }).trigger("reloadGrid");
				
				fn_searchCustSaleCal();
	        }
	    });
	}
	
	function fn_searchCustSaleCal() {
		<%-- 기본 데이터 --%>
		var searchData = $('#custSalePopup #searchForm').serializeJson();
		
		cmn.ajax({
			url: '<c:url value="/classic/cmn/popup/selectCustSaleCal.json"/>',
			data: {
				"searchData" : JSON.stringify(searchData)
			},
			success: function(data) {
				var saleCalList = data.custSaleCalList;
				
				for (var i=0; i<saleCalList.length; i++) {
					$('#godNm').text(saleCalList[i].godNm);
					$('#godDt').text(saleCalList[i].godDt);
					$('#cGodStrDt').text(saleCalList[i].godStrDt);
					$('#cGodEndDt').text(saleCalList[i].godEndDt);
					$('#cPsptNo').text(saleCalList[i].psptNo);
					$('#cCustNm').text(saleCalList[i].custNm);
					$('#cTelNo').text(saleCalList[i].telNo);
					$('#cCalDt').text(saleCalList[i].calDt);
					$('#cCalAmt').text(fn_makeComma(saleCalList[i].calAmt));
					$('#cCalAmt2').text(fn_makeComma(saleCalList[i].calAmt));
					$('#cCustNm2').text(saleCalList[i].custNm);	
				}
				
				window.print();
	        }
	    });
	}
	
	function fn_init_grid() {
		
		dutyShopAGrid$.jqGrid({
			colNames : ['그룹코드','일자','수입+국산','기타','LV'],
			colModel : [
		        {name:'grupCd',index:'GRUP_CD',width:30,align:'center'},
		        {name:'saleDt',index:'SALE_DT',width:33,align:'center',formatter:'formatDate'},
		        {name:'godDiv1SaleAmt',index:'GOD_DIV_1_SALE_AMT',width:'40',formatter:'integer',align:'center',sortable:false},
		        {name:'godDiv2SaleAmt',index:'GOD_DIV_2_SALE_AMT',width:'30',formatter:'integer',align:'center',sortable:false},
		        {name:'godDiv3SaleAmt',index:'GOD_DIV_3_SALE_AMT',width:'30',formatter:'integer',align:'center',sortable:false}
		    ],
		    height: 185,
		    footerrow: true,
	        userDataOnFooter: true,
	        gridComplete: function () {
				var grid$ = $(this);
				<%-- summary --%>
				var sumAmt1 = grid$.getCol("godDiv1SaleAmt", false, "sum");
				var sumAmt2 = grid$.getCol("godDiv2SaleAmt", false, "sum");
				var sumAmt3 = grid$.getCol("godDiv3SaleAmt", false, "sum");
				
	    	    grid$.jqGrid("footerData", "set", {
	    	    	"grupCd": "합계",
		   	      	"godDiv1SaleAmt": sumAmt1,
			   	    "godDiv2SaleAmt": sumAmt2,
			   	  	"godDiv3SaleAmt": sumAmt3
	   	      	}, true);
	    	    <%-- summary td안에, 약간의 오른쪽여백을 주기 위함 --%>
	    	    $('#gbox_duty_shop_a').find(".ui-jqgrid-ftable tr td").append("&nbsp;");
	    	    $('#gbox_duty_shop_a').find(".ui-jqgrid-ftable tr td:eq(3)").css("text-align", "center");
			}
		});
		
		dutyShopBGrid$.jqGrid({
			colNames : ['그룹코드','일자','수입+국산','기타','LV'],
			colModel : [
		        {name:'grupCd',index:'GRUP_CD',width:30,align:'center'},
		        {name:'saleDt',index:'SALE_DT',width:33,align:'center',formatter:'formatDate'},
		        {name:'godDiv1SaleAmt',index:'GOD_DIV_1_SALE_AMT',width:'40',formatter:'integer',align:'center',sortable:false},
		        {name:'godDiv2SaleAmt',index:'GOD_DIV_2_SALE_AMT',width:'30',formatter:'integer',align:'center',sortable:false},
		        {name:'godDiv3SaleAmt',index:'GOD_DIV_3_SALE_AMT',width:'30',formatter:'integer',align:'center',sortable:false}
		    ],
		    height: 185,
		    footerrow: true,
	        userDataOnFooter: true,
	        gridComplete: function () {
				var grid$ = $(this);
				<%-- summary --%>
				var sumAmt1 = grid$.getCol("godDiv1SaleAmt", false, "sum");
				var sumAmt2 = grid$.getCol("godDiv2SaleAmt", false, "sum");
				var sumAmt3 = grid$.getCol("godDiv3SaleAmt", false, "sum");
				
	    	    grid$.jqGrid("footerData", "set", {
	    	    	"grupCd": "합계",
		   	      	"godDiv1SaleAmt": sumAmt1,
			   	    "godDiv2SaleAmt": sumAmt2,
			   	  	"godDiv3SaleAmt": sumAmt3
	   	      	}, true);
	    	    <%-- summary td안에, 약간의 오른쪽여백을 주기 위함 --%>
	    	    $('#gbox_duty_shop_b').find(".ui-jqgrid-ftable tr td").append("&nbsp;");
	    	    $('#gbox_duty_shop_b').find(".ui-jqgrid-ftable tr td:eq(3)").css("text-align", "center");
			}
		});
		
		dutyShopCGrid$.jqGrid({
			colNames : ['그룹코드','일자','수입+국산','기타','LV'],
			colModel : [
		        {name:'grupCd',index:'GRUP_CD',width:30,align:'center'},
		        {name:'saleDt',index:'SALE_DT',width:33,align:'center',formatter:'formatDate'},
		        {name:'godDiv1SaleAmt',index:'GOD_DIV_1_SALE_AMT',width:'40',formatter:'integer',align:'center',sortable:false},
		        {name:'godDiv2SaleAmt',index:'GOD_DIV_2_SALE_AMT',width:'30',formatter:'integer',align:'center',sortable:false},
		        {name:'godDiv3SaleAmt',index:'GOD_DIV_3_SALE_AMT',width:'30',formatter:'integer',align:'center',sortable:false}
		    ],
		    height: 185,
		    footerrow: true,
	        userDataOnFooter: true,
	        gridComplete: function () {
				var grid$ = $(this);
				<%-- summary --%>
				var sumAmt1 = grid$.getCol("godDiv1SaleAmt", false, "sum");
				var sumAmt2 = grid$.getCol("godDiv2SaleAmt", false, "sum");
				var sumAmt3 = grid$.getCol("godDiv3SaleAmt", false, "sum");
				
	    	    grid$.jqGrid("footerData", "set", {
	    	    	"grupCd": "합계",
		   	      	"godDiv1SaleAmt": sumAmt1,
			   	    "godDiv2SaleAmt": sumAmt2,
			   	  	"godDiv3SaleAmt": sumAmt3
	   	      	}, true);
	    	    <%-- summary td안에, 약간의 오른쪽여백을 주기 위함 --%>
	    	    $('#gbox_duty_shop_c').find(".ui-jqgrid-ftable tr td").append("&nbsp;");
	    	    $('#gbox_duty_shop_c').find(".ui-jqgrid-ftable tr td:eq(3)").css("text-align", "center");
			}
		});
		
		dutyShopDGrid$.jqGrid({
			colNames : ['그룹코드','일자','수입+국산','기타','LV'],
			colModel : [
		        {name:'grupCd',index:'GRUP_CD',width:30,align:'center'},
		        {name:'saleDt',index:'SALE_DT',width:33,align:'center',formatter:'formatDate'},
		        {name:'godDiv1SaleAmt',index:'GOD_DIV_1_SALE_AMT',width:'40',formatter:'integer',align:'center',sortable:false},
		        {name:'godDiv2SaleAmt',index:'GOD_DIV_2_SALE_AMT',width:'30',formatter:'integer',align:'center',sortable:false},
		        {name:'godDiv3SaleAmt',index:'GOD_DIV_3_SALE_AMT',width:'30',formatter:'integer',align:'center',sortable:false}
		    ],
		    height: 185,
		    footerrow: true,
	        userDataOnFooter: true,
	        gridComplete: function () {
				var grid$ = $(this);
				<%-- summary --%>
				var sumAmt1 = grid$.getCol("godDiv1SaleAmt", false, "sum");
				var sumAmt2 = grid$.getCol("godDiv2SaleAmt", false, "sum");
				var sumAmt3 = grid$.getCol("godDiv3SaleAmt", false, "sum");
				
	    	    grid$.jqGrid("footerData", "set", {
	    	    	"grupCd": "합계",
		   	      	"godDiv1SaleAmt": sumAmt1,
			   	    "godDiv2SaleAmt": sumAmt2,
			   	  	"godDiv3SaleAmt": sumAmt3
	   	      	}, true);
	    	    <%-- summary td안에, 약간의 오른쪽여백을 주기 위함 --%>
	    	    $('#gbox_duty_shop_d').find(".ui-jqgrid-ftable tr td").append("&nbsp;");
	    	    $('#gbox_duty_shop_d').find(".ui-jqgrid-ftable tr td:eq(3)").css("text-align", "center");
			}
		});
		
		dutyShopEGrid$.jqGrid({
			colNames : ['그룹코드','일자','수입+국산','기타','LV'],
			colModel : [
		        {name:'grupCd',index:'GRUP_CD',width:30,align:'center'},
		        {name:'saleDt',index:'SALE_DT',width:33,align:'center',formatter:'formatDate'},
		        {name:'godDiv1SaleAmt',index:'GOD_DIV_1_SALE_AMT',width:'40',formatter:'integer',align:'center',sortable:false},
		        {name:'godDiv2SaleAmt',index:'GOD_DIV_2_SALE_AMT',width:'30',formatter:'integer',align:'center',sortable:false},
		        {name:'godDiv3SaleAmt',index:'GOD_DIV_3_SALE_AMT',width:'30',formatter:'integer',align:'center',sortable:false}
		    ],
		    height: 185,
		    footerrow: true,
	        userDataOnFooter: true,
	        gridComplete: function () {
				var grid$ = $(this);
				<%-- summary --%>
				var sumAmt1 = grid$.getCol("godDiv1SaleAmt", false, "sum");
				var sumAmt2 = grid$.getCol("godDiv2SaleAmt", false, "sum");
				var sumAmt3 = grid$.getCol("godDiv3SaleAmt", false, "sum");
				
	    	    grid$.jqGrid("footerData", "set", {
	    	    	"grupCd": "합계",
		   	      	"godDiv1SaleAmt": sumAmt1,
			   	    "godDiv2SaleAmt": sumAmt2,
			   	  	"godDiv3SaleAmt": sumAmt3
	   	      	}, true);
	    	    <%-- summary td안에, 약간의 오른쪽여백을 주기 위함 --%>
	    	    $('#gbox_duty_shop_e').find(".ui-jqgrid-ftable tr td").append("&nbsp;");
	    	    $('#gbox_duty_shop_e').find(".ui-jqgrid-ftable tr td:eq(3)").css("text-align", "center");
			}
		});
		
		dutyShopFGrid$.jqGrid({
			colNames : ['그룹코드','일자','수입+국산','기타','LV'],
			colModel : [
		        {name:'grupCd',index:'GRUP_CD',width:30,align:'center'},
		        {name:'saleDt',index:'SALE_DT',width:33,align:'center',formatter:'formatDate'},
		        {name:'godDiv1SaleAmt',index:'GOD_DIV_1_SALE_AMT',width:'40',formatter:'integer',align:'center',sortable:false},
		        {name:'godDiv2SaleAmt',index:'GOD_DIV_2_SALE_AMT',width:'30',formatter:'integer',align:'center',sortable:false},
		        {name:'godDiv3SaleAmt',index:'GOD_DIV_3_SALE_AMT',width:'30',formatter:'integer',align:'center',sortable:false}
		    ],
		    height: 185,
		    footerrow: true,
	        userDataOnFooter: true,
	        gridComplete: function () {
				var grid$ = $(this);
				<%-- summary --%>
				var sumAmt1 = grid$.getCol("godDiv1SaleAmt", false, "sum");
				var sumAmt2 = grid$.getCol("godDiv2SaleAmt", false, "sum");
				var sumAmt3 = grid$.getCol("godDiv3SaleAmt", false, "sum");
				
	    	    grid$.jqGrid("footerData", "set", {
	    	    	"grupCd": "합계",
		   	      	"godDiv1SaleAmt": sumAmt1,
			   	    "godDiv2SaleAmt": sumAmt2,
			   	  	"godDiv3SaleAmt": sumAmt3
	   	      	}, true);
	    	    <%-- summary td안에, 약간의 오른쪽여백을 주기 위함 --%>
	    	    $('#gbox_duty_shop_f').find(".ui-jqgrid-ftable tr td").append("&nbsp;");
	    	    $('#gbox_duty_shop_f').find(".ui-jqgrid-ftable tr td:eq(3)").css("text-align", "center");
			}
		});
		
		dutyShopGGrid$.jqGrid({
			colNames : ['그룹코드','일자','수입+국산','기타','LV'],
			colModel : [
		        {name:'grupCd',index:'GRUP_CD',width:30,align:'center'},
		        {name:'saleDt',index:'SALE_DT',width:33,align:'center',formatter:'formatDate'},
		        {name:'godDiv1SaleAmt',index:'GOD_DIV_1_SALE_AMT',width:'40',formatter:'integer',align:'center',sortable:false},
		        {name:'godDiv2SaleAmt',index:'GOD_DIV_2_SALE_AMT',width:'30',formatter:'integer',align:'center',sortable:false},
		        {name:'godDiv3SaleAmt',index:'GOD_DIV_3_SALE_AMT',width:'30',formatter:'integer',align:'center',sortable:false}
		    ],
		    height: 185,
		    footerrow: true,
	        userDataOnFooter: true,
	        gridComplete: function () {
				var grid$ = $(this);
				<%-- summary --%>
				var sumAmt1 = grid$.getCol("godDiv1SaleAmt", false, "sum");
				var sumAmt2 = grid$.getCol("godDiv2SaleAmt", false, "sum");
				var sumAmt3 = grid$.getCol("godDiv3SaleAmt", false, "sum");
				
	    	    grid$.jqGrid("footerData", "set", {
	    	    	"grupCd": "합계",
		   	      	"godDiv1SaleAmt": sumAmt1,
			   	    "godDiv2SaleAmt": sumAmt2,
			   	  	"godDiv3SaleAmt": sumAmt3
	   	      	}, true);
	    	    <%-- summary td안에, 약간의 오른쪽여백을 주기 위함 --%>
	    	    $('#gbox_duty_shop_g').find(".ui-jqgrid-ftable tr td").append("&nbsp;");
	    	    $('#gbox_duty_shop_g').find(".ui-jqgrid-ftable tr td:eq(3)").css("text-align", "center");
			}
		});
		
		dutyShopHGrid$.jqGrid({
			colNames : ['그룹코드','일자','수입+국산','기타','LV'],
			colModel : [
		        {name:'grupCd',index:'GRUP_CD',width:30,align:'center'},
		        {name:'saleDt',index:'SALE_DT',width:33,align:'center',formatter:'formatDate'},
		        {name:'godDiv1SaleAmt',index:'GOD_DIV_1_SALE_AMT',width:'40',formatter:'integer',align:'center',sortable:false},
		        {name:'godDiv2SaleAmt',index:'GOD_DIV_2_SALE_AMT',width:'30',formatter:'integer',align:'center',sortable:false},
		        {name:'godDiv3SaleAmt',index:'GOD_DIV_3_SALE_AMT',width:'30',formatter:'integer',align:'center',sortable:false}
		    ],
		    height: 185,
		    footerrow: true,
	        userDataOnFooter: true,
	        gridComplete: function () {
				var grid$ = $(this);
				<%-- summary --%>
				var sumAmt1 = grid$.getCol("godDiv1SaleAmt", false, "sum");
				var sumAmt2 = grid$.getCol("godDiv2SaleAmt", false, "sum");
				var sumAmt3 = grid$.getCol("godDiv3SaleAmt", false, "sum");
				
	    	    grid$.jqGrid("footerData", "set", {
	    	    	"grupCd": "합계",
		   	      	"godDiv1SaleAmt": sumAmt1,
			   	    "godDiv2SaleAmt": sumAmt2,
			   	  	"godDiv3SaleAmt": sumAmt3
	   	      	}, true);
	    	    <%-- summary td안에, 약간의 오른쪽여백을 주기 위함 --%>
	    	    $('#gbox_duty_shop_h').find(".ui-jqgrid-ftable tr td").append("&nbsp;");
	    	    $('#gbox_duty_shop_h').find(".ui-jqgrid-ftable tr td:eq(3)").css("text-align", "center");
			}
		});
		
		dutyShopIGrid$.jqGrid({
			colNames : ['그룹코드','일자','수입+국산','기타','LV'],
			colModel : [
		        {name:'grupCd',index:'GRUP_CD',width:30,align:'center'},
		        {name:'saleDt',index:'SALE_DT',width:33,align:'center',formatter:'formatDate'},
		        {name:'godDiv1SaleAmt',index:'GOD_DIV_1_SALE_AMT',width:'40',formatter:'integer',align:'center',sortable:false},
		        {name:'godDiv2SaleAmt',index:'GOD_DIV_2_SALE_AMT',width:'30',formatter:'integer',align:'center',sortable:false},
		        {name:'godDiv3SaleAmt',index:'GOD_DIV_3_SALE_AMT',width:'30',formatter:'integer',align:'center',sortable:false}
		    ],
		    height: 185,
		    footerrow: true,
	        userDataOnFooter: true,
	        gridComplete: function () {
				var grid$ = $(this);
				<%-- summary --%>
				var sumAmt1 = grid$.getCol("godDiv1SaleAmt", false, "sum");
				var sumAmt2 = grid$.getCol("godDiv2SaleAmt", false, "sum");
				var sumAmt3 = grid$.getCol("godDiv3SaleAmt", false, "sum");
				
	    	    grid$.jqGrid("footerData", "set", {
	    	    	"grupCd": "합계",
		   	      	"godDiv1SaleAmt": sumAmt1,
			   	    "godDiv2SaleAmt": sumAmt2,
			   	  	"godDiv3SaleAmt": sumAmt3
	   	      	}, true);
	    	    <%-- summary td안에, 약간의 오른쪽여백을 주기 위함 --%>
	    	    $('#gbox_duty_shop_i').find(".ui-jqgrid-ftable tr td").append("&nbsp;");
	    	    $('#gbox_duty_shop_i').find(".ui-jqgrid-ftable tr td:eq(3)").css("text-align", "center");
			}
		});
		
		dutyShopJGrid$.jqGrid({
			colNames : ['그룹코드','일자','수입+국산','기타','LV'],
			colModel : [
		        {name:'grupCd',index:'GRUP_CD',width:30,align:'center'},
		        {name:'saleDt',index:'SALE_DT',width:33,align:'center',formatter:'formatDate'},
		        {name:'godDiv1SaleAmt',index:'GOD_DIV_1_SALE_AMT',width:'40',formatter:'integer',align:'center',sortable:false},
		        {name:'godDiv2SaleAmt',index:'GOD_DIV_2_SALE_AMT',width:'30',formatter:'integer',align:'center',sortable:false},
		        {name:'godDiv3SaleAmt',index:'GOD_DIV_3_SALE_AMT',width:'30',formatter:'integer',align:'center',sortable:false}
		    ],
		    height: 185,
		    footerrow: true,
	        userDataOnFooter: true,
	        gridComplete: function () {
				var grid$ = $(this);
				<%-- summary --%>
				var sumAmt1 = grid$.getCol("godDiv1SaleAmt", false, "sum");
				var sumAmt2 = grid$.getCol("godDiv2SaleAmt", false, "sum");
				var sumAmt3 = grid$.getCol("godDiv3SaleAmt", false, "sum");
				
	    	    grid$.jqGrid("footerData", "set", {
	    	    	"grupCd": "합계",
		   	      	"godDiv1SaleAmt": sumAmt1,
			   	    "godDiv2SaleAmt": sumAmt2,
			   	  	"godDiv3SaleAmt": sumAmt3
	   	      	}, true);
	    	    <%-- summary td안에, 약간의 오른쪽여백을 주기 위함 --%>
	    	    $('#gbox_duty_shop_j').find(".ui-jqgrid-ftable tr td").append("&nbsp;");
	    	    $('#gbox_duty_shop_j').find(".ui-jqgrid-ftable tr td:eq(3)").css("text-align", "center");
			}
		});
		
		dutyShopKGrid$.jqGrid({
			colNames : ['그룹코드','일자','수입+국산','기타','LV'],
			colModel : [
		        {name:'grupCd',index:'GRUP_CD',width:30,align:'center'},
		        {name:'saleDt',index:'SALE_DT',width:33,align:'center',formatter:'formatDate'},
		        {name:'godDiv1SaleAmt',index:'GOD_DIV_1_SALE_AMT',width:'40',formatter:'integer',align:'center',sortable:false},
		        {name:'godDiv2SaleAmt',index:'GOD_DIV_2_SALE_AMT',width:'30',formatter:'integer',align:'center',sortable:false},
		        {name:'godDiv3SaleAmt',index:'GOD_DIV_3_SALE_AMT',width:'30',formatter:'integer',align:'center',sortable:false}
		    ],
		    height: 185,
		    footerrow: true,
	        userDataOnFooter: true,
	        gridComplete: function () {
				var grid$ = $(this);
				<%-- summary --%>
				var sumAmt1 = grid$.getCol("godDiv1SaleAmt", false, "sum");
				var sumAmt2 = grid$.getCol("godDiv2SaleAmt", false, "sum");
				var sumAmt3 = grid$.getCol("godDiv3SaleAmt", false, "sum");
				
	    	    grid$.jqGrid("footerData", "set", {
	    	    	"grupCd": "합계",
		   	      	"godDiv1SaleAmt": sumAmt1,
			   	    "godDiv2SaleAmt": sumAmt2,
			   	  	"godDiv3SaleAmt": sumAmt3
	   	      	}, true);
	    	    <%-- summary td안에, 약간의 오른쪽여백을 주기 위함 --%>
	    	    $('#gbox_duty_shop_g').find(".ui-jqgrid-ftable tr td").append("&nbsp;");
	    	    $('#gbox_duty_shop_g').find(".ui-jqgrid-ftable tr td:eq(3)").css("text-align", "center");
			}
		});
		
	}
	
	function fn_initEvent() {
	    <%-- [출력] 버튼 클릭  --%>
	    $('#custSalePopup #btnPrint').on('click', function(e) {
	    	window.print();
	    	/*
	    	if (navigator.userAgent.match(/Chrome\/(\S+)/)) { //크롬
	    		window.print();	
	        } else { //익스플로러
	        	fn_preview_print();	
	        } 
	    	*/
		});
	}
	
	//익스플로러 미리보기 펑션
	function fn_preview_print(){ 
	   var OLECMDID = 7; 
	   var PROMPT = 1; 
	   var WebBrowser = '<OBJECT ID="WebBrowser1" WIDTH=0 HEIGHT=0 CLASSID="CLSID:8856F961-340A-11D0-A96B-00C04FD705A2"></OBJECT>'; 
	   document.body.insertAdjacentHTML('beforeEnd', WebBrowser); 
	   WebBrowser1.ExecWB( OLECMDID, PROMPT); 
	} 

	function fn_makeComma(data_value){
		
		var txtNumber = '' + data_value;    // 입력된 값을 문자열 변수에 저장합니다.

		var rxSplit = new RegExp('([0-9])([0-9][0-9][0-9][,.])');    // 정규식 형태 생성
        var arrNumber = txtNumber.split('.');    // 입력받은 숫자를 . 기준으로 나눔. (정수부와 소수부분으로 분리)
        arrNumber[0] += '.'; // 정수부 끝에 소수점 추가

        do {
            arrNumber[0] = arrNumber[0].replace(rxSplit, '$1,$2'); // 정수부에서 rxSplit 패턴과 일치하는 부분을 찾아 replace 처리
        } while (rxSplit.test(arrNumber[0])); // 정규식 패턴 rxSplit 가 정수부 내에 있는지 확인하고 있다면 true 반환. 루프 반복.

        if (arrNumber.length > 1) { // txtNumber를 마침표(.)로 분리한 부분이 2개 이상이라면 (즉 소수점 부분도 있다면)
            return arrNumber.join(''); // 배열을 그대로 합칩. (join 함수에 인자가 있으면 인자를 구분값으로 두고 합침)
        }
        else { // txtNumber 길이가 1이라면 정수부만 있다는 의미.
            return arrNumber[0].split('.')[0]; // 위에서 정수부 끝에 붙여준 마침표(.)를 그대로 제거함.
        }
	}

});



</script>

<!-- widow_popup -->
<div id="custSalePopup" class="layer_pop" style="display:block">
	<form id="searchForm" method="post">
		<input type="hidden" id="psptNo" name="psptNo" value="${psptNo}"/>
		<input type="hidden" id="calDt" name="calDt" value="${calDt}"/>
		<input type="hidden" id="travAgenCd" name="travAgenCd" value="${travAgenCd}"/>
		<input type="hidden" id="calSeq" name="calSeq" value="${calSeq}"/>
		<input type="hidden" id="boxNo" name="boxNo" value="${boxNo}"/>
	</form>
	<!-- cont area -->
	<div class="cont_area" style="margin-left:15px;margin-right:15px;">
		<div class="table_wrap">
			<table>
				<colgroup>
					<col style="width:14%"></col>
					<col style="width:30%"></col>
					<col style="width:14*"></col>
					<col style="widht:30%"></col>
					<col style="width:8%"></col>
				</colgroup>
				<tbody>
					<tr>
						<th scope="row">상품명</th>
						<td><span id="godNm"></span></td>
						<th scope="row">기간</th>
						<td><span id="godDt"></span></td>
						<td>
							<div class="btn_wrap">
								<button type="button" id="btnPrint" class="btn_base">인쇄</button>
							</div>
						</td>
					</tr>
				</tbody>
			</table>
		</div>	
		<div class="table_wrap_p">
			<table>
				<colgroup>
					<col width="24%"></col>
					<col width="24%"></col>
					<col width="24%"></col>
					<col width="24%"></col>
				</colgroup>
				<tbody>
					<tr>
						<td style="line-height:16px;"><span id="dutyShopNmA">롯데</span></td>
						<td style="line-height:16px;"><span id="dutyShopNmB">두타</span></td>
						<td style="line-height:16px;"><span id="dutyShopNmC">갤러리아</span></td>
						<td style="line-height:16px;"><span id="dutyShopNmD">신세계</span></td>
					</tr>
					<tr>
						<td><div><table id="duty_shop_a"></table></div></td>
						<td><div><table id="duty_shop_b"></table></div></td>
						<td><div><table id="duty_shop_c"></table></div></td>
						<td><div><table id="duty_shop_d"></table></div></td>
					</tr>
				</tbody>
			</table>
			<table>
				<colgroup>
					<col width="24%"></col>
					<col width="24%"></col>
					<col width="24%"></col>
					<col width="24%"></col>
				</colgroup>
				<tbody>
					<tr>
						<td style="line-height:16px;"><span id="dutyShopNmE">신라</span></td>
						<td style="line-height:16px;"><span id="dutyShopNmF">신라아이파크</span></td>
						<td style="line-height:16px;"><span id="dutyShopNmG">동화</span></td>
						<td style="line-height:16px;"><span id="dutyShopNmH">롯데제주</span></td>
					</tr>
					<tr>
						<td><div><table id="duty_shop_e"></table></div></td>
						<td><div><table id="duty_shop_f"></table></div></td>
						<td><div><table id="duty_shop_g"></table></div></td>
						<td><div><table id="duty_shop_h"></table></div></td>
					</tr>
				</tbody>
			</table>
			<table>
				<colgroup>
					<col width="24%"></col>
					<col width="24%"></col>
					<col width="24%"></col>
					<col width="24%"></col>
				</colgroup>
				<tbody>
					<tr>
						<td style="line-height:16px;"><span id="dutyShopNmI">신라제주</span></td>
						<td style="line-height:16px;"><span id="dutyShopNmJ">롯데부산</span></td>
						<td style="line-height:16px;"><span id="dutyShopNmK">신세계부산</span></td>
						<td style="line-height:16px;"></td>
					</tr>
					<tr>
						<td><div><table id="duty_shop_i"></table></div></td>
						<td><div><table id="duty_shop_j"></table></div></td>
						<td><div><table id="duty_shop_k"></table></div></td>
						<td></td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="table_wrap_p">
			<table>
				<colgroup>
					<col style="width:8%"></col>
					<col style="width:10%"></col>
					<col style="width:10%"></col>
					<col style="width:10%"></col>
					<col style="width:10%"></col>
					<col style="width:8%"></col>
					<col style="width:5%"></col>
					<col style="width:5%"></col>
					<col style="width:5%"></col>
					<col style="width:5%"></col>
					<col style="width:5%"></col>
					<col style="width:5%"></col>
					<col style="width:5%"></col>
					<col style="width:5%"></col>
				</colgroup>
				<tbody>
					<tr>
						<td rowspan="2" style="vertical-align:middle;background-color:#E4F7BA">면세점</td>
						<td>합계</td>
						<td>수입+국산</td>
						<td>기타</td>
						<td>LV</td>
						<td rowspan="2" style="vertical-align:middle;background-color:#FFD8D8">고객</td>
						<td colspan="2" >합계</td>
						<td colspan="2" >수입+국산</td>
						<td colspan="2" >기타</td>
						<td colspan="2" >LV</td>
					</tr>
					<tr>
						<td><span id="dutyShopTotSum">0</span></td>
						<td><span id="dutyShopGod1Sum">0</span></td>
						<td><span id="dutyShopGod2Sum">0</span></td>
						<td><span id="dutyShopGod3Sum">0</span></td>
						<td><span id="custTotSum">0</span></td>
						<td><span id="custTotSumW">0</span></td>
						<td><span id="custGod1Sum">0</span></td>
						<td><span id="custGod1SumW">0</span></td>
						<td><span id="custGod2Sum">0</span></td>
						<td><span id="custGod2SumW">0</span></td>
						<td><span id="custGod3Sum">0</span></td>
						<td><span id="custGod3SumW">0</span></td>
					</tr>
				</tbody>
			</table>
		</div>	
		<div class="table_wrap_p">
			<table>
				<colgroup>
					<col style="width:10%"></col>
					<col style="width:13%"></col>
					<col style="width:10%"></col>
					<col style="width:13%"></col>
					<col style="width:10%"></col>
					<col style="width:13%"></col>
					<col style="width:10%"></col>
					<col style="width:17%"></col>
				</colgroup>
				<tbody>
					<tr>
						<td>여권번호</td>
						<td><span id="cPsptNo"></span></td>
						<td>고객명</td>
						<td><span id="cCustNm"></span></td>
						<td>연락처</td>
						<td><span id="cTelNo"></span></td>
						<td>정산방식</td>
						<td><span id="etc2"> 현금 / 이체 </span></td>
						
					</tr>
					<tr>
						<td>도착일자</td>
						<td><span id="cGodStrDt"></span></td>
						<td>귀국일자</td>
						<td><span id="cGodEndDt"></span></td>
						<td>정산일자</td>
						<td><span id="cCalDt"></span></td>
						<td>정산금액(￦)</td>
						<td><span id="cCalAmt"></span></td>
						
					</tr>
					<tr>
						<td colspan="6" style="border-right:none;"></td>
						<td style="border-left:none;border-right:none;">수령인</td>
						<td style="border-left:none;"><span id="cCustNm2"></span> (인)</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
</div>
<!-- //widow_popup -->
