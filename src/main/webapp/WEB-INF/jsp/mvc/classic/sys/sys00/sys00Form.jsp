<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/base/include/tag_declare.jsp" %>

<script>
jQuery(function($) {

    var sForm$ = $('#sys00Form #searchForm');
    
	RealGridJS.setTrace(false);
	RealGridJS.setRootContext("/fsswms/resources/plugins/realgrid");  // Root Context
	
	var gridMain = new RealGridJS.GridView("gridMain");  // Grid
	var gridDtl  = new RealGridJS.GridView("gridDtl");   // Grid

	var dataMain   = new RealGridJS.LocalDataProvider();  // Data Provider
	var dataDtl    = new RealGridJS.LocalDataProvider();  // Data Provider
	
	// Link Grid & Provider
	gridMain.setDataSource(dataMain);
	gridDtl.setDataSource(dataDtl);

	// Set Grid Style & Option
	setGridOptions([gridMain,gridDtl]);
	
	// SedataProvider Option
	setProviderOptions([dataMain,dataDtl]);
	
	//-----------------------------------
	// Main Grid Column Design
	//-----------------------------------
	var columns =	[
	             	 {name:"col01", fieldName:"godCd",     width:70, editable:false, styles: {textAlignment: "center",background:"#ffffffbd",figureName: "leftTop",figureBackground: "#ffff0000",figureSize: "25%"}, header:{text: "상품코드"} }
					,{name:"col02", fieldName:"godNm",     width:200,styles: {textAlignment: "near"}, button: "action", buttonVisibility:"always",                             header:{text: "상품명"} }
					,{name:"col03", fieldName:"godDivNm",  width:70, styles: {textAlignment: "near"},                                                                         header:{text: "상품구분"} }
					,{name:"col04", fieldName:"szPtNm",    width:70,                                                                                                          header:{text: "사이즈패턴"} }
					,{name:"col05", fieldName:"sort",      width:70, editor: {type: "number",textAlignment: "far",editFormat: "#,##0.##"}, styles: {textAlignment: "far",numberFormat: "#,##0.##"},                       header:{text: "순서",styles: {background: "linear,#22ffd500,#ffffd500,90"}} }
					,{name:"col06", fieldName:"maker",     width:70,                                                                                                          header:{text: "제조사"} }
					,{name:"col07", fieldName:"origin",    width:110,editor: {type: "dropDown",dropDownCount: 12,textReadOnly: true,dropDownWhenClick :false}
																			,values:["값1", "값2", "값1", "VICTE", "THREE", "SEVEN"]
																			,labels:["<VINET>", "<HANAR>", "<SUPRD>", "<VICTE>", "<THREE>", "<SEVEN>"], lookupDisplay: true, "sortable": false, header:{text: "원산지-"} }
					,{name:"col08", fieldName:"outshell",  width:70,                                                                                                          header:{text: "겉감"} }
					,{name:"col09", fieldName:"inshell",   width:70,                                                                                                          header:{text: "안감"} }
					,{name:"col10", fieldName:"filler",    width:70,                                                                                                          header:{text: "충전재"} }
					,{name:"col11", fieldName:"godMoq",    width:70,                                                                                                          header:{text: "상품최소\n생산수량"} }
					,{name:"col12", fieldName:"crMoq",     width:70,                                                                                                          header:{text: "색상최소\n생산수량"} }
					,{name:"col13", fieldName:"chkEnblYn", width:40,                                                                                                          header:{text: "chkEnbl"} }
					,{name:"col14", fieldName:"chk",       width:40,                                                                                                          header:{text: "chk"} }
					,{name:"col15", fieldName:"saleDt",    width:120, editor: {type: "date",dataType: "datetime",datetimeFormat: "yyyyMMdd"}, styles:{datetimeFormat: "yyyy/MM/dd"}, header:{text:"판매일"}}
					,{name:"col16", fieldName:"saleDt",    width:120, editor: {
				        type: "btdate",dataType: "datetime",datetimeFormat: "yyyyMMdd",
				        btOptions: {
				            "startView": 1,
				            "minViewMode": 1,
				            "todayBtn": "linked",
				            "language": "kr",
				            "todayHighlight": true,
				            "language": "ko"
				        },
				        datetimeFormat: "yyyy/MM/dd",
				        textReadOnly: true,
				        mask: {
				            "editMask": "9999-99-99"
				        } },                                                   header:{text:"dt2"}}

					   
					
					//,{type: "group", width: 350,
				    //    header: {text: "Company"},
				    //    columns:[
					//			 {name: "CompanyName",fieldName: "CompanyName",type: "data",width: "200",header: {text: "Company Name"},styles: {textAlignment: "near"}}
					//			,{name: "Country",    fieldName: "Country", type: "data",width: "150",header: {text: "Country"},styles: {textAlignment: "center"}}
					//        	]
				    // }
					];

	gridMain.setColumns(columns);
	gridDtl.setColumns(columns);
	
	var options =  {
		    checkBar: { visible: true },
		    stateBar: { visible: true },
		    edit: {
			    editable: true,
		        insertable: true,
		        appendable: true,
		        updatable: true,
		        deletable: true		    
		    }
		};
	gridMain.setOptions(options);
	
	// Provider Field Design
	var fields =    [
					 {fieldName: "godCd", dataType: "text"}
					,{fieldName: "godNm"}
					,{fieldName: "godDivNm"}
					,{fieldName: "szPtNm"}
					,{fieldName: "sort", dataType: "number"}
					,{fieldName: "maker"}
					,{fieldName: "origin"}
					,{fieldName: "outshell"}
					,{fieldName: "inshell"}
					,{fieldName: "filler"}
					,{fieldName: "godMoq"}
					,{fieldName: "crMoq"}
					,{fieldName: "chkEnblYn"}
					,{fieldName: "chk"}
					,{fieldName: "saleDt",dataType: "datetime",datetimeFormat: "yyyyMMdd"}
					];

	dataMain.setFields(fields);
	dataDtl.setFields(fields);
	
	// var data = [
	// 			 ["data1", "data1-2", "data1-3"]
	// 			,["data2", "data2-2", "data2-3"]
	// 			,["data3", "data3-2", "data3-3"]
	//            ];
	// dataMain.setRows(data);
	
	
	// 컬럼정보변경.
	var header = gridMain.getColumnProperty("col1", "header");
	
	// alert(header.text);
	
	// header.text = "Col1";
	
	// gridMain.setColumnProperty("col1","header",header);
	
	var header = gridMain.getColumnProperty("col01", "width");
	gridMain.setColumnProperty("col1","width",60);
	
	
	
    //=============================================
    //  Head 영역에서는 Context Menu 실행하지 않음
    //=============================================
    //gridMain.onContextMenuPopup = function (grid, x, y, elementName) {
    //    return elementName != "HeaderCell";
    //}
    gridMain.onCurrentRowChanged =  function (grid, oldRow, newRow) {
    	//alert("onCurrentRowChanged: (" + oldRow + " => " + newRow + ")");
    	  
  		var param = dataMain.getJsonRow(newRow); // Data Provider Currow

    	cmn.ajax({
			url: "<c:url value='/god/sys00/selectGodDtl.json'/>",
			data: param,
			success: function(data) {
				console.log(JSON.stringify(data));
				dataDtl.fillJsonData(data.list, {fillMode: "set"});
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
  		
  		// alert(param["godCd"]);
    };
	
    // Cell Button Click
    gridMain.onCellButtonClicked = function (grid, itemIndex, column) {
        alert("CellButton Clicked: itemIndex=" + itemIndex + ", fieldName=" + column.fieldName + " dataRow=" + grid.getDataRow(itemIndex));

		var dataRow = grid.getDataRow(itemIndex);
		var jsonRow = dataMain.getJsonRow(dataRow); // Data Provider Currow
		
		var sGodCd = jsonRow["godNm"]
		
		console.log(JSON.stringify(jsonRow));
    };
    
    //============================
    // cell의 data가 변경되었을때.
    //============================
    gridMain.onCellEdited =  function (grid, itemIndex, dataRow, field) {
    	// console.log( field.fieldName);
    	// console.log( itemIndex);
    	// console.log( dataRow);
    	var currow = gridMain.getCurrent();
    	
    	if( currow.fieldName == "godNm" ) {  // 상품명 변경시
    		var newValue = grid.getValue(itemIndex, field);
    		console.log(newValue);

    		<%-- 팝업 확인 --%>
    		cmn.ajax({
    			url: '<c:url value="/cmn/commInfo/selectPopGodList.json"/>',
    			data: {
        			'searchStr': newValue,
        			'param2': "AA"
    			},
    			success: function(data) {
    				var popChkList = data.popGodList;
    				console.log(popChkList);
    				if (1 === popChkList.length) {
    					gridMain.setValue(itemIndex, "godCd", popChkList[0].godCd);
    					gridMain.setValue(itemIndex, "godNm", popChkList[0].godNm);
    					//gridMain.showEditor();
    					gridMain.setFocus();
    				} else {
    					cmn.ajaxModal({
    						url:'<c:url value="/cmn/popup/cstInfoPopup.do" />',
    						data:{
    							'cstCdorNm':newValue
    						},
    						callback: function(cstCd, cstNm) {
    							$('#god001Form #cstCd').val(cstCd);
    							$('#god001Form #cstNm').val(cstNm);
    						}
    					});
    					gridMain.showEditor();
    					gridMain.setFocus();
    				}
    	        }
    	    });
    	}
    };
    

	//====================================================
	// 버튼을 클릭하면 포커스된 셀을 다른 셀로 변경합니다.
	//====================================================
    $("#btnToggleFocus").on("click", function() {
    	var focusCell = gridMain.getCurrent();
    	
        //focusCell.dataRow = 1;
        focusCell.itemIndex = 1;
        
        alert(" getCurrent.dataRow=" + focusCell.dataRow           // DataProvider 속성
        		+"\n getCurrent.itemIndex=" + focusCell.itemIndex  // Grid 속성
				+"\n getCurrent.fieldName=" + focusCell.fieldName
				+"\n getCurrent.column=" + focusCell.column);
        
        if( focusCell.fieldName == "field1" ) {
            focusCell.column = "col2";
            //focusCell.fieldName = "field2";
        }
        else {
            focusCell.column = "col1";
            //focusCell.fieldName = "field1";
        }
        gridMain.setCurrent(focusCell);
    })
    
    $("#goTwo").on("click", function() {
    	var cellInfo = {};
    	
    	// cellInfo.dataRow = 1;
    	
    	cellInfo.itemIndex = 2;
    	gridMain.setCurrent(cellInfo);
    })
    $("#info").on("click", function() {
    	var focusCell = gridMain.getCurrent();
        alert("ItemIndex:" + focusCell.itemIndex + ", RowId:" + focusCell.dataRow);
    })
    
    $("#goOrder").on("click", function() {
    	var fields = ["field2"];
        var directions = [RealGridJS.SortDirection.DESCENDING];
        gridMain.orderBy(fields, directions);
    })
    
    
    $("#setOption").on("click", function() {
    	//gridMain.setSortingOptions({
      	//   style: "INCLUSIVE"  // EXCLUSIVE 
      	//  ,showSortOrder: true
      	//  ,sortOrderStyles: {
      	//     foreground: "#88ececec" 
      	//    ,fontSize: 11 
      	//    ,fontBold: true 
      	//    ,textAlignment: "far" 
      	//    ,lineAlignment: "far"
      	//  }
      	//});
    	
    	var options = {};
        options.style = RealGridJS.SortStyle.INCLUSIVE;  // RealGridJS.SortStyle.NONE, RealGridJS.SortStyle.EXCLUSIVE, RealGridJS.SortStyle.INCLUSIVE, RealGridJS.SortStyle.REVERS
        gridMain.setSortingOptions(options);
        
        //gridMain.orderBy([], []); // 정렬 초기화.
        
        gridMain.orderBy(["field2", "field3"], ["ascending", "ascending"]);
    })
    
    function getUpdate(dataProvider){
        
    	// alert(JSON.stringify(dataMain.getAllStateRows()));
		
		if( dataProvider.getRowStateCount(RealGridJS.RowState.CREATED) > 0 ) return true;
        if( dataProvider.getRowStateCount(RealGridJS.RowState.UPDATED) > 0 ) return true;
        if( dataProvider.getRowStateCount(RealGridJS.RowState.DELETED) > 0 ) return true;
        return false;
    }

	//====================================
	//  Search
	//====================================
    $('#sys00Form #btnSearch').on('click', function(e) {
		sForm$.submit();        	
	});
    
    <%-- 검색폼 submit  --%>
    sForm$.on('submit', function() {
		
    	gridMain.commit(false);
    	
    	if( getUpdate(dataMain) ) {
        	if( !confirm("변경된 데이터가 있습니다.\n\n무시하고 조회하시겠습니까?") ) {
        		return false;
        	}
    	}
		
    	var param = sForm$.serializeJson();
    	
    	cmn.ajax({
			url: "<c:url value='/god/sys00/selectGodList.json'/>",
			data: param,
			success: function(data) {
				// console.log(JSON.stringify(data));
				dataMain.fillJsonData(data.list, {fillMode: "set"});
	        },
	        error: function (xhr, status, error) {
	            //$("#loadResult").css("color", "red").text("Load failed: " + error).show();
	        },
	        complete: function (data) {
	            // setLoading(false);
	            gridMain.closeProgress();
	            gridMain.setFocus();
	            
	            gridMain.setCheckableExpression("values['chkEnblYn'] = 'Y'", true);
	        }
	    });
    	
    	return false;
	});
    
	//====================================
	//  Save
	//====================================
    $('#sys00Form #btnSave').on('click', function(e) {

    	gridMain.commit(false);

		// if( changedRowData.length == 0){
    	// 	cmn.alert('변경된 데이터가 없습니다.');
    	// 	return false;
		// }

    	
    	// validation
    	// if(!cmn.checkRequired($('#god001Form #godCd'), "상품코드"))	return false;
    	// if(!cmn.checkRequired($('#god001Form #godNm'), "상품명"))		return false;
    	
    	<%-- Grid Data --%>
    	var checkedRows = gridMain.getCheckedItems(false);
    	//alert(checkedRows);
    	
    	for( var i=0; i<dataMain.getRowCount(); i++) {
    		dataMain.setValue(i, "chk", (gridMain.isCheckedRow(i)?"1":"0"));
        }

    	// var current = gridMain.getCurrent();
    	//var row = current.dataRow;
    	//var field = current.fieldIndex;
        //
    	//var value = "Cell (" + field + ", " + row + ")";
    	
    	var jsonDatas = dataMain.getJsonRows(0,dataMain.getRowCount());
    	
    	cmn.ajax({
    		url:'<c:url value="/god/sys00/saveGodList.json"/>',
			data:{
				'gridData' : JSON.stringify(jsonDatas),
				'loginId' : "${sessionScope[cmnConstants.sessionUserId]}"
			},
			success: function(data) {
				console.log(JSON.stringify(data));
				dataMain.fillJsonData(data.list, {fillMode: "set"});
	        },
	        error: function (xhr, status, error) {
	            //$("#loadResult").css("color", "red").text("Load failed: " + error).show();
	        },
	        complete: function (data) {
	            gridMain.setFocus();
	        }
	    });
    	
    	
		// cmn.ajax({
		// 	url:'<c:url value="/god/sys00/saveGodList.json"/>',
		// 	data:{
		// 		'gridData' : JSON.stringify(changedRowData),
		// 		'loginId' : "${sessionScope[cmnConstants.sessionUserId]}"
		// 	},
		// 	success:function(data) {
		// 		cmn.alert("처리 되었습니다.", function () {
		// 			$('#sys00Form #btnSearch').trigger('click');
		// 		});
	    //     }
	    // }); 
	});
	
	//===========================
	// testtest
	//===========================
    $("#testtest").on("click", function() {
    	gridMain.showEditor();
    	gridMain.setFocus();
    })
    
    
    $("#beginInsertRow").on("click", function() {
    	// gridMain.beginInsertRow(0);
    	var curr = gridMain.getCurrent();
    	gridMain.beginInsertRow(Math.max(0, curr.itemIndex), false);
    	gridMain.showEditor();
    	gridMain.setFocus();
    })
    
    
    $("#beginAppendRow").on("click", function() {
    	gridMain.beginAppendRow();
    	gridMain.showEditor();
    	gridMain.setFocus();
    })
    
    $("#beginUpdateRow").on("click", function() {
    	var curr = gridMain.getCurrent();
    	gridMain.beginUpdateRow(curr.itemIndex);
    	gridMain.showEditor();
    	gridMain.setFocus();
    })
    
    $( window ).resize(function() {
    	// alert($( window ).height());
    	
    	//alert($("#testtest").height());
    	
    	$("#realgrid").css('height',$(window).height()-400);
    	// $( window ).height()
    	
    	//$( "#log" ).append( "<div>Handler for .resize() called.</div>" );
    });

});
</script>

<div class="cont_wrap" id="sys00Form">
	<!-- top area -->
	<div class="top_area">
		<h2>상품정보현황</h2>
		<div class="btn_wrap">
			<button type="button" id="btnSearch">조회</button>
			<button type="button" id="btnNew" style="display:none">신규</button>
			<button type="button" id="btnDelete" style="display:none">삭제</button>
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
						<th scope="row">상품구분</th>
						<td><cmn:codeCombo gbn="GOD02" name="godDiv" optTxt="전체" /></td>
						<th scope="row">상품코드/명</th>
						<td><input type="text" name="godCdorNm" /></td>
					</tr>
				</tbody>
			</table>
			</form>
		</div>
		
<!-- 
			<div class="btn_col">					
				<button type="button" class="btn_base" id="btnExcelDown">엑셀다운로드</button>
			</div>
 -->
			<div id="gridMain" style="width: 100%; height: 300px;"></div>
			<div id="gridDtl" style="width: 100%; height: 150px;"></div>
	</div>
	<!-- //cont area -->

			<button type="button" id="btnToggleFocus">포커스셀 변경하기</button>..
			<button type="button" id="info">상태값보기</button><br>
			<button type="button" id="goTwo">2행으로 이동</button>..
			<button type="button" id="goOrder">정렬</button>..
			<button type="button" id="setOption">정렬옵션</button><br>
			
			<button type="button" id="beginInsertRow">행추가[맨위에]</button>..
			<button type="button" id="beginAppendRow">행추가[밑에]</button>..
			<button type="button" id="beginUpdateRow">편집모드</button><br>
			
			<button type="button" id="exportGrid">엑셀</button>..
			<button type="button" id="testtest">Test</button>
</div>