<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/base/include/tag_declare.jsp" %>

<script>
jQuery(function($) {

	var sForm$ = $('#sys02Form #searchForm');
    
	fssReal.setRealGridJS(); // set Root Context

	var gridMain = new RealGridJS.GridView("gridMainSys02");  // Grid
	var dataMain = new RealGridJS.LocalDataProvider();   // Data Provider

	gridMain.setDataSource(dataMain);  // Link Grid & Provider
	fssReal.setGridOptions([gridMain],["메뉴"]);		// Set Grid Style & Option
	fssReal.setProviderOptions([dataMain]);	// SedataProvider Option

	//-----------------------------------
	// Provider Field Design
	//-----------------------------------
	var fields = [
		{fieldName: "orgProgId"},
		{fieldName: "progId"},
		{fieldName: "progNm"},
		{fieldName: "progDiv"},
		{fieldName: "progDiv01"},
		{fieldName: "progDiv02"},
		{fieldName: "progUrl"},
		{fieldName: "upProgId"},
		{fieldName: "sort", dataType: "number"}
	];

	dataMain.setFields(fields);

	//-----------------------------------
	// 그리드 컬럼 디자인
	//-----------------------------------
	var columns =[
		{
			name:"col01",
			fieldName:"progId",
			header:{text: "메뉴ID"},
			width:100,
			textInputCase: "upper",
			styles: {textAlignment: "center", background:"#ffffffbd",figureName: "leftTop",figureBackground: "#ffff0000",figureSize: "25%"},
			editor:{maxLength: 10},
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
			fieldName:"progNm",
			header:{text: "메뉴명"},
			width:200,
			styles: {textAlignment: "near", background:"#ffffffbd",figureName: "leftTop",figureBackground: "#ffff0000",figureSize: "25%"}
		},
		{
			type:"group",
			name:"col03",
			header:{text: "구분"},
			width:100,  // hideChildHeaders: true,
			styles: {textAlignment: "center"},
			columns: [
				{
					name: "col03_01",
					fieldName: "progDiv01",
					width: "50",
					editable: false,
					styles: {
						paddingLeft: 8,
						textAlignment: "center"
					},
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
					header: {text: "폴더"}
				},
				{
					name: "col03_02",
					fieldName: "progDiv02",
					width: "50",
					styles: {
						paddingLeft: 8,
						textAlignment: "center"
					},
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
					header: {text: "화면"}
				}
			]
		},
		{
			name:"col04",
			fieldName:"progUrl",
			header:{text: "Url"},
			width:250,
			styles: {textAlignment: "near"}
		},
		{
			name:"col05",
			fieldName:"upProgId",
			header:{text: "상위\n메뉴"},
			width:80,
			//editButtonVisibility:"always",
			styles: {textAlignment: "center"},
			editor: {type: "dropDown",dropDownCount: 12,textReadOnly: true,dropDownWhenClick :false},
			lookupSourceId:"upProgIdLookUp",
			lookupKeyFields: ["upProgId"],
			lookupDisplay: true,
			sortable: false
		},
		{
			name:"col06",
			fieldName:"sort",
			header:{text: "정렬"},
			width:60,
			styles: {textAlignment: "far",numberFormat: "###0"},
			editor: {type: "number",textAlignment: "far",editFormat: "###0"}
		}
	];

	gridMain.setColumns(columns);

	//===============================
	// 그리드 옵션 지정
	//===============================
	gridMain.setOptions(
		{
			stateBar: { visible: true },
			checkBar: { visible: true },
			edit: {
				editable: true,
				insertable: true,
				appendable: true,
				updatable: true,
				deletable: true
			}
		}
	);

	gridMain.setLookups([
		{
			id:"upProgIdLookUp",
			levels:1,
			ordered: true,
			keys: [""],
			values: [""]
		}
	]);

	function fn_SetLookup() {
		var arrKeys = [];
			arrKeys.push(" ");
		for( var i=0; i<dataMain.getRowCount(); i++  ) {
			if( dataMain.getValue(i,"progDiv") == "F" ) {
				arrKeys.push(dataMain.getValue(i,"progId"));
			}
		}
		gridMain.setLookups([
			{
				id:"upProgIdLookUp",
				levels:1,
				ordered: true,
				keys: arrKeys,
				values: arrKeys
			}
		]);
	}

	$( document ).ready(function() {
		$('#sys02Form #btnSearch').trigger('click');
	});

	//=======================================
	// 추가된 행만 수정가능.
	//=======================================
	gridMain.onCurrentRowChanged = function (grid, oldRow, newRow) {
		var curr = grid.getCurrent();
		var rowState = dataMain.getRowState(newRow);

		var sProgDiv = dataMain.getValue(newRow,"progDiv");

		var column = grid.columnByField("upProgId");
		grid.setColumnProperty(column, "readOnly", (sProgDiv == "F"));
	};


	gridMain.onEditRowChanged = function (grid, itemIndex, dataRow, field, oldValue, newValue) {
		console.log( "onEditRowChanged >>> field=" + field  + " itemIndex=" + itemIndex + " dataRow=" + dataRow);
		var currow = gridMain.getCurrent();

		if( currow.fieldName == "progId" ) {
			gridMain.commit(false);
			for( var i=0; i<dataMain.getRowCount(); i++  ) {
				if( dataMain.getValue(i,"upProgId") == oldValue ) {
					dataMain.setValue(i,"upProgId",  newValue);
				}
			}
		}
		gridMain.commitEditor(true);
		fn_SetLookup();
	};

	//============================
	// cell의 data가 변경되었을때.
	//============================
	gridMain.onCellEdited =  function (grid, itemIndex, dataRow, field) {
		console.log( "onCellEdited >>> field=" + field  + " itemIndex=" + itemIndex + " dataRow=" + dataRow);

		var currow = gridMain.getCurrent();
		var newValue = [grid.getValue(itemIndex, field)];

		// 중복 체크
		if( currow.fieldName == "progId" ) {

			var dupRow = fssReal.getFindDataRow(grid,["progId"],newValue);

			if( dupRow > -1 ) {
				cmn.alert("ID가 중복됩니다.", function() {
					grid.cancel();
					grid.setFocus();
				});
				return;
			}
		}

		if( currow.fieldName == "progDiv01" ) {
			if( newValue == "Y" ) {
				grid.commit(false);
				dataMain.setValue(dataRow,"progDiv",  "F");
				dataMain.setValue(dataRow,"progDiv02","N");
			}
		}
		if( currow.fieldName == "progDiv02" ) {
			if( newValue == "Y" ) {
				grid.commit(false);
				dataMain.setValue(dataRow,"progDiv",  "P");
				dataMain.setValue(dataRow,"progDiv01","N");
			}
		}

		gridMain.commit(false);
		fn_SetLookup();
	};



	<%-- [행추가] 버튼 클릭  --%>
	$('#sys02Form #btnComDtlGridAddRow').on('click', function(e) {
		gridMain.commit(false);

		var curr = gridMain.getCurrent();

		var values = {
			progId: "",
			progNmNm: "",
			progDiv: "P",
			progDiv01: "N",
			progDiv02: "Y"
		};

		dataMain.insertRow(Math.max(0, curr.itemIndex+1), values);

		gridMain.setCurrent({
			itemIndex: Math.max(0, curr.itemIndex+1),
			fieldName: "progId"
		});

		gridMain.showEditor();
		gridMain.setFocus();
	});

	<%-- [행삭제] 버튼 클릭  --%>
	$('#sys02Form #btnComDtlGridDelRow').on('click', function(e) {
		gridMain.commit(false);

		var checkedRows = gridMain.getCheckedRows(true);

		dataMain.removeRows(checkedRows, false);

		gridMain.setFocus();
	});

	//====================================
	//  조회버튼 클릭
	//====================================
	$('#sys02Form #btnSearch').on('click', function(e) {
		sForm$.submit();
	});

	<%-- 검색폼 submit  --%>
	sForm$.on('submit', function() {

		gridMain.commit(false);

		gridMain.orderBy([], []);

		var schParam = sForm$.serializeJson();

		cmn.ajax({
			url: "<c:url value='/classic/sys/sys02/mainList.json'/>",
			data: schParam,
			success: function(data) {
				dataMain.fillJsonData(data.list, {fillMode: "set"});
			},
			error: function (xhr, status, error) {
				//$("#loadResult").css("color", "red").text("Load failed: " + error).show();
			},
			complete: function (data) {
				fn_SetLookup();
				gridMain.setFocus();
			}
		});

		return false;
	});


	//====================================
	//  신규버튼 클릭
	//====================================
	$('#sys02Form #btnNew').on('click', function(e) {

		gridMain.commit(false);

		var values = {	progId: "",
						whNm: "",
						useYn: "Y" };

		dataMain.addRow(values);  // 행추가

		gridMain.setCurrent({
			itemIndex: Math.max(0, gridMain.getItemCount()),
			fieldName: "progId"
		});

		gridMain.showEditor();
		gridMain.setFocus();
	});


	//====================================
	//  삭제버튼 클릭
	//====================================
	$('#sys02Form #btnDelete').on('click', function(e) {

		gridMain.commit(false);

		// 추가된 row 인지 조회된 row인지 체크
		var currow = gridMain.getCurrent();

		if( dataMain.getRowState(currow.dataRow) == "created" ) {
			dataMain.removeRow(currow.dataRow);
			return;
		}
		else {
			cmn.confirm('삭제 하시겠습니까?', function() {

				var rowData = dataMain.getJsonRow(currow.dataRow);
				var schParam = sForm$.serializeJson();

				cmn.ajax({
					url:'<c:url value="/classic/sys/sys02/mainDelete.json"/>',
					data:{
						'orgWhCd': gridMain.getValue(currow.itemIndex,"orgWhCd"),
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
	$('#sys02Form #btnSave').on('click', function(e) {

		gridMain.commit(false);

		// 변경체크
		if( !fssReal.getUpdate(dataMain) ){
			cmn.alert('변경된 데이터가 없습니다.', function() {
				gridMain.setFocus();
			});
		 	return;
		}

		// null 값 체크
		var iFindRow = fssReal.getDataRowNullValue(dataMain,"progId");

		if( iFindRow > -1 ) {
			cmn.alert("ID가 필요합니다.",function() {
				gridMain.setCurrent({dataRow: iFindRow, fieldName: "progId"});
				gridMain.setFocus();
			});
			return;
		}

		var iFindRow = fssReal.getDataRowNullValue(dataMain,"progNm");

		if( iFindRow > -1 ) {
			cmn.alert("메뉴명이 필요합니다.",function() {
				gridMain.setCurrent({dataRow: iFindRow, fieldName: "progNm"});
				gridMain.setFocus();
			});
			return;
		}

		var allDatas = fssReal.getJsonRows(dataMain, "U");  // Argu : dataProvider, "A"(All Row), "U"(create,update,delete)

		var schParam = sForm$.serializeJson();

		cmn.ajax({
			url:'<c:url value="/classic/sys/sys02/mainSave.json"/>',
			data:{
				'inData'  : JSON.stringify(allDatas),
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
				// gridMain.setFocus();
			}
		});
	});


});
</script>

<div class="cont_wrap" id="sys02Form">
	<!-- top area -->
	<div class="top_area">
		<h2>메뉴 sys02</h2>
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

			</form>
		</div>

			<!-- RealGrid Canvas 삽입-->
			<button type="button" id="btnComDtlGridAddRow"> 행삽입</button>
							<button type="button" id="btnComDtlGridDelRow">- 행삭제</button>
			<div id="gridMainSys02" style="width: 100%; height: 500px;"></div>
	</div>
	<!-- //cont area -->
</div>