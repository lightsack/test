<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/base/include/tag_declare.jsp" %>


<!--Role관리-->
<script>
jQuery(function($) {

	var sForm$ = $('#sys03Form #searchForm');
    
	fssReal.setRealGridJS(); // set Root Context

	var gridMain = new RealGridJS.GridView("gridMainSys03");  // Grid
	var gridProg = new RealGridJS.GridView("gridProgSys03");  // Grid

	var dataMain = new RealGridJS.LocalDataProvider();   // Data Provider
	var dataProg = new RealGridJS.LocalDataProvider();   // Data Provider

	gridMain.setDataSource(dataMain);  // Link Grid & Provider
	gridProg.setDataSource(dataProg);  // Link Grid & Provider

	fssReal.setGridOptions([gridMain,gridProg]);		// Set Grid Style & Option
	fssReal.setProviderOptions([dataMain,dataProg]);	// SedataProvider Option

	//-----------------------------------
	// Provider Field Design
	//-----------------------------------
	dataMain.setFields([
		{fieldName: "roleCd"},
		{fieldName: "roleNm"}
	]);

	//-----------------------------------
	// 마스터 그리드 컬럼 디자인
	//-----------------------------------
    var dynamicStyles = [{
    	criteria: "state = 'c'",
        styles: "background=#ffffffbd"
    	},
    	{
       	criteria: "state <> 'c'",
           styles: "background=#fff2f2f2"
       	}
    ];
	
	var columns = [
		{
			name:"col01",
			fieldName:"roleCd",
			header:{text: "Role"},
			width:60,
			textInputCase: "upper",
			editor:{maxLength: 5,textAlignment: "center"},
			dynamicStyles: dynamicStyles,
			styles: {textAlignment: "center",background:"#ffffffbd",figureName: "leftTop",figureBackground: "#ffff0000",figureSize: "25%"},
			footer: {
				styles: {
					textAlignment: "far",
					numberFormat: "0,000",
					paddingRight: 3
				},
				text: "Count",
				expression: "count"
			}
		},
		{
			name:"col02",
			fieldName:"roleNm",
			header:{text: "Role명"},
			width:140,
			styles: {textAlignment: "near",  background:"#ffffffbd",figureName: "leftTop",figureBackground: "#ffff0000",figureSize: "25%"}
		}
	];

	gridMain.setColumns(columns);

	//-----------------------------------
	// Provider Field Design
	//-----------------------------------
	dataProg.setFields([
		{fieldName: "progId"},
		{fieldName: "progNm"},
		{fieldName: "roleCd"},
		{fieldName: "enblYn"},
		{fieldName: "chk"}
	]);

	//-----------------------------------
	// 디테일 그리드 컬럼 디자인
	//-----------------------------------
	var columns = [
		{
			name:"col01",
			fieldName:"progNm",
			header:{text: "메뉴명"},
			width:240,
			styles: {textAlignment: "near"},
			footer: {
				styles: {
					numberFormat: "0,000",
					paddingRight: 3
				},
				text: "Count",
				expression: "count"
			}
		},
		{
			name:"col02",
			fieldName:"progId",
			header:{text: "메뉴ID"},
			width:80
		}
	];

	gridProg.setColumns(columns);


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

	gridProg.setOptions({
		checkBar: { visible: true }
	});

	$( document ).ready(function() {
		$('#sys03Form #btnSearch').trigger('click');
	});


	//=================================
	// 마스터 그리드 row 변경시
	//=================================
	gridMain.onCurrentRowChanged =  function (grid, oldRow, newRow) {
		//alert("onCurrentRowChanged: (" + oldRow + " => " + newRow + ")");
		console.log(newRow);
		
        var rowState = dataMain.getRowState(newRow);
        
        var editable = (rowState == "created");
		
        var column = grid.columnByField("roleCd");
        grid.setColumnProperty(column, "editable", editable);
        
  		gridProg.commit(false);
  		
		var param = dataMain.getJsonRow(newRow); // Data Provider Currow

		cmn.ajax({
			url: "<c:url value='/classic/sys/sys03/pgmList.json'/>",
			data: param,
			success: function(data) {
				dataProg.fillJsonData(data.list, {fillMode: "set"});
				
				gridProg.setCheckableExpression("values['enblYn'] = 'Y'", true);
				
				var checkRows = [];
				$.each(data.list, function(k, v){
					if( data.list[k].chk == "Y" ) {
						checkRows.push(k);
					}
				});
				gridProg.checkRows(checkRows, true);

			    gridProg.setAllCheck(false); // Head 의 체크표시변경
			},
			error: function (xhr, status, error) {
				//$("#loadResult").css("color", "red").text("Load failed: " + error).show();
			},
			complete: function (data) {
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
		if( currow.fieldName == "roleCd" ) {
			var newValue = [grid.getValue(itemIndex, field)];

			var dupRow = fssReal.getFindDataRow(grid,["roleCd"],newValue);

			if( dupRow > -1 ) {
				cmn.alert("공통코드가 중복됩니다.", function() {
					grid.cancel();
					grid.setFocus();
				});
			}
		}
	};
	
	//==========================
	// 체크바에 클릭했을때.
	//==========================
	gridProg.onItemChecked = function(grid, itemIndex, checked) {	    
	    var dataRow = gridProg.getDataRow(itemIndex);	    
	    dataProg.setValue(dataRow, "chk", checked?"Y":"N");
	};
	
	//==========================
	// 체크바 Head 에 클릭했을때.
	//==========================
	gridProg.onItemAllChecked =  function (grid, checked) {
		for( var i=0; i<dataProg.getRowCount(); i++ ) {
			if( dataProg.getValue(i,"enblYn") == "Y" ) {
				dataProg.setValue(i, "chk", checked?"Y":"N");
			}
		}
		var checkbar = gridProg.getCheckBar();
		console.log(checkbar);
	};
	
	//====================================
	//  조회버튼 클릭
	//====================================
	$('#sys03Form #btnSearch').on('click', function(e) {
		sForm$.submit();
	});

	<%-- 검색폼 submit  --%>
	sForm$.on('submit', function() {

		gridMain.commit(false);
		gridProg.commit(false);

		var schParam = sForm$.serializeJson();

		cmn.ajax({
			url: "<c:url value='/classic/sys/sys03/mainList.json'/>",
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
	$('#sys03Form #btnNew').on('click', function(e) {

		gridMain.commit(false);

		var values = {	roleCd: "",
						roleNm: "" };

		dataMain.addRow(values);  // 행추가

		gridMain.setCurrent({
			itemIndex: Math.max(0, gridMain.getItemCount()),
			fieldName: "roleCd"
		});

		gridMain.showEditor();
		gridMain.setFocus();
	});


	//====================================
	//  삭제버튼 클릭
	//====================================
	$('#sys03Form #btnDelete').on('click', function(e) {

		gridMain.commit(false);

		// 추가된 row 인지 조회된 row인지 체크
		var currow = gridMain.getCurrent();

		if( dataMain.getRowState(currow.dataRow) == "created" ) {
			dataMain.removeRow(currow.dataRow);
			return;
		}
		else {

			var v = gridMain.getValue(currow.itemIndex, "roleCd");
			cmn.confirm('[' + v + '] 삭제 하시겠습니까?', function() {

				var rowData = dataMain.getJsonRow(currow.dataRow);
				var schParam = sForm$.serializeJson();

				cmn.ajax({
					url:'<c:url value="/classic/sys/sys03/mainDelete.json"/>',
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
	$('#sys03Form #btnSave').on('click', function(e) {

		gridMain.commit(false);
		gridProg.commit(false);

		// 변경체크
		if( !fssReal.getUpdate(dataMain) && !fssReal.getUpdate(dataProg) ){
			cmn.alert('변경된 데이터가 없습니다.', function() {
				gridMain.setFocus();
			});
		 	return;
		}

		// 공통코드 null 값 체크
		var iFindRow = fssReal.getDataRowNullValue(dataMain,"roleCd");
		if( iFindRow > -1 ) {
			cmn.alert("코드가 필요합니다.",function() {
				gridMain.setCurrent({dataRow: iFindRow, fieldName: "roleCd"});
				gridMain.showEditor();
				gridMain.setFocus();
			});
			return;
		}
		
		//alert(1);
    	// 체크값 할당
    	//var checkedRows = gridProg.getCheckedItems(false);
    	//
    	//for( var i=0; i<dataProg.getRowCount(); i++) {
    	//	dataProg.setValue(i, "chk", (gridProg.isCheckedRow(i)?"Y":"N"));
        //}

		var mstData  = fssReal.getJsonRow(dataMain, gridMain.getCurrent().dataRow); // Data Provider Currow
		var pgmDatas = fssReal.getJsonRows(dataProg, "U");  // Argu : dataProvider, "A"(All Row), "U"(create,update,delete)
		var schParam = sForm$.serializeJson();

		var roleCd   = mstData["roleCd"];

		cmn.ajax({
			url:'<c:url value="/classic/sys/sys03/mainSave.json"/>',
			data:{
				'mstData' : JSON.stringify(mstData),
				'pgmDatas': JSON.stringify(pgmDatas),
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

				var iRow = fssReal.getFindDataRow(gridMain,["roleCd"],[roleCd]);

				gridMain.setCurrent({dataRow: iRow});
				gridMain.setFocus();
			}
		});
	});

});
</script>

<div class="cont_wrap" id="sys03Form">
	<!-- top area -->
	<div class="top_area">
		<h2>Role관리 sys03</h2>
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
						<th scope="row">Role명</th>
						<td><input type="text" name="roleNm" /></td>
					</tr>
				</tbody>
			</table>
			</form>
		</div>

			<!-- RealGrid Canvas 삽입-->
		<table border="1" width="100%">
		<tr><td><div id="gridMainSys03" style="width: 100%; height: 500px;"></div></td>
			<td><div id="gridProgSys03" style="width: 100%; height: 500px;"></div></td></tr></table>
		
		
		
	</div>
	<!-- //cont area -->
</div>