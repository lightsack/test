<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/base/include/tag_declare.jsp" %>

<!--realgrid-->
<script src="/ldserp/resources/js/scripts/realgridjs-lic.js"></script>
<script src="/ldserp/resources/js/scripts/realgridjs_eval.1.1.27.min.js"></script>
<script src="/ldserp/resources/js/scripts/realgridjs-api.1.1.27.js"></script>
<script src="/ldserp/resources/js/scripts/jszip.min.js"></script>
<script src="/ldserp/resources/js/scripts/grid_style.js"></script>
<script src="/ldserp/resources/js/scripts/realgrid_common.js"></script>


<!--공통코드관리-->
<script>
jQuery(function($) {

    var sForm$ = $('#sys01Form #searchForm');
    
	fssReal.setRealGridJS(); // set Root Context

	var gridMain = new RealGridJS.GridView("gridMainSys01");  // Grid
	var gridDetl = new RealGridJS.GridView("gridDetlSys01");  // Grid
	var dataMain = new RealGridJS.LocalDataProvider();   // Data Provider
	var dataDetl = new RealGridJS.LocalDataProvider();   // Data Provider
	
	gridMain.setDataSource(dataMain);  // Link Grid & Provider
	gridDetl.setDataSource(dataDetl);  // Link Grid & Provider
	
	fssReal.setGridOptions([gridMain,gridDetl],["공통코드_마스터","공통코드_상세"]);        // Set Grid Style & Option
	fssReal.setProviderOptions([dataMain,dataDetl]);    // SedataProvider Option
	
	//-----------------------------------
	// Provider Field Design
	//-----------------------------------
	var fields =    [
					 {fieldName: "orgMainCd"}
					,{fieldName: "mainCd"}
					,{fieldName: "mainNm"}
					,{fieldName: "sort"}
					,{fieldName: "useYn"}
					];
	
	dataMain.setFields(fields);
	
	//-----------------------------------
	// 마스터 그리드 컬럼 디자인
	//-----------------------------------
	var columns =	[
					 {name:"col01", fieldName:"mainCd",  width:60, textInputCase: "upper", editor:{maxLength: 5}, styles: {textAlignment: "center",background:"#ffffffbd",figureName: "leftTop",figureBackground: "#ffff0000",figureSize: "25%"}, header:{text: "코드"},
					 footer: {
							styles: {
								textAlignment: "far",
								numberFormat: "0,000",
								paddingRight: 3
							},
							text: "Count",
							expression: "count"
						}}
					,{name:"col02", fieldName:"mainNm",  width:140, styles: {textAlignment: "near",  background:"#ffffffbd",figureName: "leftTop",figureBackground: "#ffff0000",figureSize: "25%"}, header:{text: "명"} }
					,{name:"col03", fieldName:"sort", width:40, styles: {textAlignment: "center"},                                                                                              header:{text: "정렬"} }
					,{name:"col07", fieldName:"useYn", width:40,editable: false,
																renderer: {
														            type: "check",
														            editable: true,
														            threeState: false,
														            startEditOnClick: true,
														            spaceKey: true,
														            trueValues: "Y",
														            falseValues: "N",
														            labelPosition: "center"
														        },
														        header:{text: "사용"},
														        styles: {
													            	paddingLeft: 8,
													            	textAlignment: "center"}
														        }
					];

	gridMain.setColumns(columns);
	

	//-----------------------------------
	// Provider Field Design
	//-----------------------------------
	var fields =    [
					 {fieldName: "mainCd"}
					,{fieldName: "orgSubCd"}
					,{fieldName: "subCd"}
					,{fieldName: "subNm"}
					,{fieldName: "sort"}
					,{fieldName: "useYn"}
					];
	
	dataDetl.setFields(fields);
	
	//-----------------------------------
	// 디테일 그리드 컬럼 디자인
	//-----------------------------------
	var columns =	[
					 {name:"col01", fieldName:"subCd",  width:100, textInputCase: "upper", editor:{maxLength: 5}, styles: {textAlignment: "center",background:"#ffffffbd",figureName: "leftTop",figureBackground: "#ffff0000",figureSize: "25%"}, header:{text: "코드"},
					 footer: {
							styles: {
								textAlignment: "far",
								numberFormat: "0,000",
								paddingRight: 3
							},
							text: "Count",
							expression: "count"
						}}
					,{name:"col02", fieldName:"subNm",  width:200, styles: {textAlignment: "near",  background:"#ffffffbd",figureName: "leftTop",figureBackground: "#ffff0000",figureSize: "25%"}, header:{text: "명"} }
					,{name:"col03", fieldName:"sort", width:110, styles: {textAlignment: "center"},                                                                                              header:{text: "정렬"} }
					,{name:"col07", fieldName:"useYn", width:50,editable: false,
																renderer: {
														            type: "check",
														            editable: true,
														            threeState: false,
														            startEditOnClick: true,
														            spaceKey: true,
														            trueValues: "Y",
														            falseValues: "N",
														            labelPosition: "center"
														        },
														        header:{text: "사용"},
														        styles: {
													            	paddingLeft: 8,
													            	textAlignment: "center"}
														        }
					];

	gridDetl.setColumns(columns);
	
	
	//===============================
	// 그리드 옵션 지정
	//===============================
	gridMain.setOptions({
		stateBar: { visible: true },
		edit: {
			editable: true,
		    insertable: true,
		    appendable: true,
		    updatable: true,
		    deletable: true	    
		    }
		});
	gridDetl.setOptions({
		stateBar: { visible: true },
		checkBar: { visible: true },
		edit: {
			editable: true,
		    insertable: true,
		    appendable: true,
		    updatable: true,
		    deletable: true	    
		    }
		});
	
	$( document ).ready(function() {
	    console.log( "ready!" );
	    $('#sys01Form #btnSearch').trigger('click');
	});
	
	
	//=================================
	// 마스터 그리드 row 변경시
	//=================================
    gridMain.onCurrentRowChanged =  function (grid, oldRow, newRow) {
    	//alert("onCurrentRowChanged: (" + oldRow + " => " + newRow + ")");

  		gridDetl.commit(false);
  		
    	var param = dataMain.getJsonRow(newRow); // Data Provider Currow

    	cmn.ajax({
			url: "<c:url value='/classic/sys/sys01/selectDtlList.json'/>",
			data: param,
			success: function(data) {
				//console.log(JSON.stringify(data));
				dataDetl.fillJsonData(data.list, {fillMode: "set"});
	        },
	        error: function (xhr, status, error) {
	            //$("#loadResult").css("color", "red").text("Load failed: " + error).show();
	        },
	        complete: function (data) {
	            // setLoading(false);
	            // gridMain.closeProgress();
	            // gridMain.setFocus();
	        }
	    });
    };
    
	//================================
	// 마스터 그리드에 data가 변경되었을때.
	//================================
    gridMain.onCellEdited =  function (grid, itemIndex, dataRow, field) {
    	
		// console.log( "field=" + field  + " itemIndex=" + itemIndex + " dataRow=" + dataRow);
    	
		var currow = grid.getCurrent();
    	
		// 공통코드 중복 체크
		if( currow.fieldName == "mainCd" ) {
    		var newValue = [grid.getValue(itemIndex, field)];
    		
    		var dupRow = fssReal.getFindDataRow(grid,["mainCd"],newValue);

    		if( dupRow > -1 ) {
				cmn.alert("공통코드가 중복됩니다.", function() {
					grid.cancel();
	    			grid.setFocus();
    			});
			}
		}
	};
	
	//================================
	// 마스터 그리드에 data가 변경되었을때.
	//================================
    gridDetl.onCellEdited =  function (grid, itemIndex, dataRow, field) {
    	
		var currow = grid.getCurrent();
    	
		// 공통코드 중복 체크
		if( currow.fieldName == "subCd" ) {
    		var newValue = [grid.getValue(itemIndex, field)];
    		
    		var dupRow = fssReal.getFindDataRow(grid,["subCd"],newValue);

    		if( dupRow > -1 ) {
    			cmn.alert("공통코드가 중복됩니다.", function() {
    				gridDetl.cancel();
    				gridDetl.setFocus();
    			});
			}
		}
	};
	
    
    <%-- [행추가] 버튼 클릭  --%>
    $('#sys01Form #btnComDtlGridAddRow').on('click', function(e) {
		gridDetl.commit(false);
    	
    	var values = {	subCd: "",
    					subNm: "",
    					useYn: "Y" };
        
        dataDetl.addRow(values);  // 행추가
        
        gridDetl.setCurrent({
			itemIndex: Math.max(0, gridDetl.getItemCount()),
			fieldName: "subCd"
    	});
        
        gridDetl.showEditor();
        gridDetl.setFocus();
    });
    
    <%-- [행삭제] 버튼 클릭  --%>
    $('#sys01Form #btnComDtlGridDelRow').on('click', function(e) {
		gridDetl.commit(false);
		var checkedRows = gridDetl.getCheckedRows(true);
    	dataDetl.removeRows(checkedRows, false);
    	gridDetl.setFocus();
    });
    
    
	//====================================
	//  조회버튼 클릭
	//====================================
    $('#sys01Form #btnSearch').on('click', function(e) {
		sForm$.submit();
	});
    
    <%-- 검색폼 submit  --%>
    sForm$.on('submit', function() {
		
    	gridMain.commit(false);
    	
    	var schParam = sForm$.serializeJson();
    	
    	cmn.ajax({
			url: "<c:url value='/classic/sys/sys01/selectMstList.json'/>",
			data: schParam,
			success: function(data) {
				dataMain.fillJsonData(data.list, {fillMode: "set"});
	        },
	        error: function (xhr, status, error) {
	            //$("#loadResult").css("color", "red").text("Load failed: " + error).show();
	        },
	        complete: function (data) {
	            gridMain.setFocus();
	        }
	    });
    	
    	return false;
	});

    
	//====================================
	//  신규버튼 클릭
	//====================================
    $('#sys01Form #btnNew').on('click', function(e) {

    	gridMain.commit(false);
    	
    	var values = {	whCd: "",
    					whNm: "",
    					useYn: "Y" };
        
        dataMain.addRow(values);  // 행추가
        
        gridMain.setCurrent({
			itemIndex: Math.max(0, gridMain.getItemCount()),
			fieldName: "whCd"
    	});
        
        gridMain.showEditor();
        gridMain.setFocus();
	});

    
	//====================================
	//  삭제버튼 클릭
	//====================================
    $('#sys01Form #btnDelete').on('click', function(e) {
        
    	gridMain.commit(false);

		// 추가된 row 인지 조회된 row인지 체크
		var currow = gridMain.getCurrent();
		
		if( dataMain.getRowState(currow.dataRow) == "created" ) {
			dataMain.removeRow(currow.dataRow);
			return;
		}
		else {
			
			var v = gridMain.getValue(currow.itemIndex, "mainCd");
			cmn.confirm('[' + v + '] 삭제 하시겠습니까?', function() {
    			
				var rowData = dataMain.getJsonRow(currow.dataRow);
				var schParam = sForm$.serializeJson();
	    		
	    		cmn.ajax({
    				url:'<c:url value="/classic/sys/sys01/deleteMst.json"/>',
    				data:{
    					'inData' : JSON.stringify(rowData),
    	    			'schData': JSON.stringify(schParam)
    				},
    				success:function(data) {
    					dataMain.fillJsonData(data.list, {fillMode: "set"});
    					cmn.alert("삭제가 완료되었습니다.", function() {
    						gridMain.setFocus();
    					});
    		        }
    		    });
    		});
		}
	});
    
	
	//====================================
	//  저장버튼 클릭
	//====================================
    $('#sys01Form #btnSave').on('click', function(e) {
    	gridMain.commit(false);
    	gridDetl.commit(false);
    	
		// 변경체크
    	if( !fssReal.getUpdate(dataMain) && !fssReal.getUpdate(dataDetl) ){
    		cmn.alert('변경된 데이터가 없습니다.', function() {
    			gridMain.setFocus();
    		});
    	 	return;
		}
    	
		// 공통코드 null 값 체크
		var iFindRow = fssReal.getDataRowNullValue(dataMain,"mainCd");
		if( iFindRow > -1 ) {
			cmn.alert("코드가 필요합니다.",function() {
				gridMain.setCurrent({dataRow: iFindRow, fieldName: "mainCd"});
				gridMain.showEditor();
				gridMain.setFocus();
			});
			return;
		}
		var iFindRow = fssReal.getDataRowNullValue(dataDetl,"subCd");
		if( iFindRow > -1 ) {
			cmn.alert("코드가 필요합니다.",function() {
				gridDetl.setCurrent({dataRow: iFindRow, fieldName: "subCd"});
				gridDetl.showEditor();
				gridDetl.setFocus();
			});
			return;
		}
		
		var mstData  = fssReal.getJsonRow(dataMain, gridMain.getCurrent().dataRow); // Data Provider Currow
		var mstDatas = fssReal.getJsonRows(dataMain, "U");  // Argu : dataProvider, "A"(All Row), "U"(create,update,delete)
		var dtlDatas = fssReal.getJsonRows(dataDetl, "U");  // Argu : dataProvider, "A"(All Row), "U"(create,update,delete)
    	var schParam = sForm$.serializeJson();
    	
		var mainCd   = mstData["mainCd"];
    	
		cmn.ajax({
			url:'<c:url value="/classic/sys/sys01/saveMst.json"/>',
			data:{
				'mstData' : JSON.stringify(mstData),
				'mstDatas': JSON.stringify(mstDatas),
				'dtlDatas': JSON.stringify(dtlDatas),
				'schData' : JSON.stringify(schParam)
			},
			success: function(data) {
				dataMain.fillJsonData(data.list, {fillMode: "set"});
				cmn.alert("저장이 완료되었습니다.",function() {
					gridMain.setFocus();
				});
	        },
	        error: function (xhr, status, error) {
	            //$("#loadResult").css("color", "red").text("Load failed: " + error).show();
	        },
	        complete: function (data) {
	        	
	    		var iRow = fssReal.getFindDataRow(gridMain,["mainCd"],[mainCd]);
				
	        	gridMain.setCurrent({dataRow: iRow});	        	
	        	gridMain.setFocus();
	        }
	    });
	});
	
    $( window ).resize(function() {
    	// alert($( window ).height());
    	
    	//alert($("#testtest").height());
    	
    	//$("#gridMainSys01").css('height',$(window).height()-400);
    	// $( window ).height()
    	
    	//$( "#log" ).append( "<div>Handler for .resize() called.</div>" );
    });

});
</script>

<div class="cont_wrap" id="sys01Form">
	<!-- top area -->
	<div class="top_area">
		<h2>공통코드x sys01</h2>
		<div class="btn_wrap">
			<button type="button" id="btnSearch">조회</button>
			<button type="button" id="btnNew">신규</button>
			<button type="button" id="btnDelete">삭제</button>
			<button type="button" id="btnSave">저장</button>
		</div>
	</div>

			
	<!-- //top area -->
	
	<!-- cont area -->
	<div class="cont_area">
		<div class="table_wrap">
			<form id="searchForm" method="post">
			<table>
				<colgroup>
					<col style="width:110px"></col>
					<col style=""></col>
					<col style="width:110px"></col>
					<col style=""></col>
				</colgroup>

				<tbody>
					<tr>
						<th scope="row">공통코드/명</th>
						<td><input type="text" name="mainNm" /></td>
					</tr>
				</tbody>
			</table>
			</form>
		</div>
		
			<!-- RealGrid Canvas 삽입-->
		<table width="100%">
		<tr><td width="30%"><div id="gridMainSys01" style="width: 100%; height: 500px;"></div></td>
			<td width="70%"><button type="button" id="btnComDtlGridAddRow">+ 행추가</button>
							<button type="button" id="btnComDtlGridDelRow">- 행삭제</button>
						<div id="gridDetlSys01" style="width: 100%; height: 500px;"></div></td></tr>
		</table>
	</div>
	<!-- //cont area -->
</div>