<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/base/include/tag_declare.jsp" %>


<!--사용자별 Role관리-->
<script>
jQuery(function($) {

	var sForm$ = $('#sys05Form #searchForm');
    
	fssReal.setRealGridJS(); // set Root Context

	var gridUser = new RealGridJS.GridView("gridUserSys05");  // Grid
	var gridRole = new RealGridJS.GridView("gridRoleSys05");  // Grid
	var gridProg = new RealGridJS.GridView("gridProgSys05");  // Grid

	var dataUser = new RealGridJS.LocalDataProvider();   // Data Provider
	var dataRole = new RealGridJS.LocalDataProvider();   // Data Provider
	var dataProg = new RealGridJS.LocalDataProvider();   // Data Provider

	gridUser.setDataSource(dataUser);  // Link Grid & Provider
	gridRole.setDataSource(dataRole);  // Link Grid & Provider
	gridProg.setDataSource(dataProg);  // Link Grid & Provider

	fssReal.setGridOptions([gridUser,gridRole,gridProg],["사용자","Role","사용자_프로그램"]);     // Set Grid Style & Option
	fssReal.setProviderOptions([dataUser,dataRole,dataProg]); // SedataProvider Option

	//-----------------------------------
	// Provider Field Design
	//-----------------------------------
	dataUser.setFields([
		{fieldName: "userId"},
		{fieldName: "userNm"}
	]);

	//-----------------------------------
	// 마스터 그리드 컬럼 디자인
	//-----------------------------------
	var columns = [
		{
			name:"col01",
			fieldName:"userId",
			header:{text: "ID"},
			width:80,
			styles: {textAlignment: "near"},
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
			fieldName:"userNm",
			header:{text: "성명"},
			width:140,
			styles: {textAlignment: "near"},
		}
	];

	gridUser.setColumns(columns);

	//-----------------------------------
	// Provider Field Design
	//-----------------------------------
	dataRole.setFields([
		{fieldName: "roleCd"},
		{fieldName: "roleNm"},
		{fieldName: "chk"}
	]);

	//-----------------------------------
	// 마스터 그리드 컬럼 디자인
	//-----------------------------------
	var columns = [
		{
			name:"col01",
			fieldName:"roleCd",
			header:{text: "Role"},
			width:60,
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
			styles: {textAlignment: "near"}
		}
	];

	gridRole.setColumns(columns);

	
	//-----------------------------------
	// Provider Field Design
	//-----------------------------------
	dataProg.setFields([
		{fieldName: "progId"},
		{fieldName: "progNm"},
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
	gridRole.setOptions({
		checkBar: { visible: true }
	});
	gridProg.setOptions({
		checkBar: { visible: true }
	});
	gridRole.setCheckBar({
		showAll: false  
	});
	gridProg.setCheckBar({
		showAll: false  
	});
	

	$( document ).ready(function() {
		$('#sys05Form #btnSearch').trigger('click');
	});


	//=================================
	// 마스터 그리드 row 변경시
	//=================================
	gridUser.onCurrentRowChanged =  function (grid, oldRow, newRow) {
		//alert("onCurrentRowChanged: (" + oldRow + " => " + newRow + ")");
  		
		var param = dataUser.getJsonRow(newRow); // Data Provider Currow

		cmn.ajax({
			url: "<c:url value='/classic/sys/sys05/roleList.json'/>",
			data: param,
			success: function(data) {
				// Role
				dataRole.fillJsonData(data.role_list, {fillMode: "set"});
				
				var checkRows = [];
				$.each(data.role_list, function(k, v){
					if( data.role_list[k].chk == "Y" ) {
						checkRows.push(k);
					}
				});
				gridRole.checkRows(checkRows, true);

				// Pgm
				dataProg.fillJsonData(data.prog_list, {fillMode: "set"});

				gridProg.setCheckableExpression("values['enblYn'] = 'Y'", true);
				
				var checkRows = [];
				$.each(data.prog_list, function(k, v){
					if( data.prog_list[k].chk == "Y" ) {
						checkRows.push(k);
					}
				});
				console.log(gridProg.getCheckBar());
				gridProg.checkRows(checkRows, true);
				
			},
			error: function (xhr, status, error) {
				//$("#loadResult").css("color", "red").text("Load failed: " + error).show();
			},
			complete: function (data) {
			}
		});
	};

	//==========================
	// 체크바에 클릭했을때.
	//==========================
	gridRole.onItemChecked = function(grid, itemIndex, checked) {	    
	    var dataRow = gridRole.getDataRow(itemIndex);	    
	    dataRole.setValue(dataRow, "chk", checked?"Y":"N");
	};

	//==========================
	// 체크바에 클릭했을때.
	//==========================
	gridProg.onItemChecked = function(grid, itemIndex, checked) {	    
	    var dataRow = gridProg.getDataRow(itemIndex);	    
	    dataProg.setValue(dataRow, "chk", checked?"Y":"N");
	};
	
	
	//====================================
	//  조회버튼 클릭
	//====================================
	$('#sys05Form #btnSearch').on('click', function(e) {
		sForm$.submit();
	});

	<%-- 검색폼 submit  --%>
	sForm$.on('submit', function() {

		gridUser.commit(false);
		gridRole.commit(false);
		gridProg.commit(false);

		var schParam = sForm$.serializeJson();

		cmn.ajax({
			url: "<c:url value='/classic/sys/sys05/mainList.json'/>",
			data: schParam,
			success: function(data) {
				dataUser.fillJsonData(data.list, {fillMode: "set"});
			},
			error: function (xhr, status, error) {
				//$("#loadResult").css("color", "red").text("Load failed: " + error).show();
			},
			complete: function (data) {
				gridUser.setFocus();
			}
		});

		return false;
	});


	//====================================
	//  저장버튼 클릭
	//====================================
	$('#sys05Form #btnSave').on('click', function(e) {

		gridUser.commit(false);
		gridRole.commit(false);
		gridProg.commit(false);

		// 변경체크
		if( !fssReal.getUpdate(dataRole) && !fssReal.getUpdate(dataProg) ){
			cmn.alert('변경된 데이터가 없습니다.', function() {
				gridUser.setFocus();
			});
		 	return;
		}

		var userData  = fssReal.getJsonRow(dataUser, gridUser.getCurrent().dataRow); // Data Provider Currow
		var roleDatas = fssReal.getJsonRows(dataRole, "U");  // Argu : dataProvider, "A"(All Row), "U"(create,update,delete)
		var progDatas = fssReal.getJsonRows(dataProg, "U");  // Argu : dataProvider, "A"(All Row), "U"(create,update,delete)
		var schParam  = sForm$.serializeJson();

		cmn.ajax({
			url:'<c:url value="/classic/sys/sys05/mainSave.json"/>',
			data:{
				'userData':  JSON.stringify(userData),
				'roleDatas': JSON.stringify(roleDatas),
				'progDatas': JSON.stringify(progDatas),
				'schData':   JSON.stringify(schParam)
			},
			success: function(data) {
				dataUser.fillJsonData(data.list, {fillMode: "set"});
				cmn.alert("저장이 완료되었습니다.",function() {
					gridUser.setFocus();
				});
			},
			error: function (xhr, status, error) {
				//$("#loadResult").css("color", "red").text("Load failed: " + error).show();
			},
			complete: function (data) {
				gridUser.setFocus();
			}
		});
	});


});
</script>

<div class="cont_wrap" id="sys05Form">
	<!-- top area -->
	<div class="top_area">
		<h2>사용자별 Role sys05</h2>
		<div class="btn_wrap">
			<button type="button" id="btnSearch">조회</button>
		<!--<button type="button" id="btnNew">신규</button>-->
		<!--<button type="button" id="btnDelete">삭제</button>-->
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
						<th scope="row">사용자</th>
						<td><input type="text" name="userNm" /></td>
					</tr>
				</tbody>
			</table>
			</form>
		</div>

			<!-- RealGrid Canvas 삽입-->
		<table width="100%">
		<tr><td width="30%"><div id="gridUserSys05" style="width: 100%; height: 500px;"></div></td>
			<td width="70%"><div id="gridRoleSys05" style="width: 100%; height: 200px;"></div><br>
							<div id="gridProgSys05" style="width: 100%; height: 300px;"></div></td></tr>
		</table>
	</div>
	<!-- //cont area -->
</div>