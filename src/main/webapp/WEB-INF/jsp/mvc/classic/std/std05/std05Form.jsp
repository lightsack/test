<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/base/include/tag_declare.jsp" %>

<script>
jQuery(function($) {

    var sForm$ = $('#std05Form #searchForm');
    
	fssReal.setRealGridJS(); // set Root Context
	
	var gridMain = new RealGridJS.GridView("gridMainStd05");  // Grid
	var dataMain = new RealGridJS.LocalDataProvider();   // Data Provider
	
	gridMain.setDataSource(dataMain);  // Link Grid & Provider	
	fssReal.setGridOptions([gridMain]);        // Set Grid Style & Option
	fssReal.setProviderOptions([dataMain]);    // SedataProvider Option

	
	//-----------------------------------
	// Provider Field Design
	//-----------------------------------
	var fields =    [
					 {fieldName: "orgWhCd"}
					,{fieldName: "whCd"}
					,{fieldName: "whNm"}
					,{fieldName: "telNo"}
					,{fieldName: "zipCd"}
					,{fieldName: "addr1"}
					,{fieldName: "addr2"}
					,{fieldName: "useYn"}
					];
	
	dataMain.setFields(fields);
	
	//-----------------------------------
	// 그리드 컬럼 디자인
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
			fieldName:"whCd",
			header:{text: "센터코드"},
			width:100,
			textInputCase: "upper",
			styles: {
				textAlignment: "center",
				figureName: "leftTop",
				figureBackground: "#ffff0000",
				figureSize: "25%"
			},
			editor:{maxLength: 5},
			dynamicStyles: dynamicStyles,
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
			fieldName:"whNm",
			header:{text: "센터명"},
			width:200,
			styles: {
				textAlignment: "near",
				background:"#ffffffbd",
				figureName: "leftTop",
				figureBackground: "#ffff0000",
				figureSize: "25%"
			}
		},
		{
			name:"col03",
			fieldName:"telNo",
			header:{text: "전화번호"},
			width:110,
			styles: {textAlignment: "center"}
		},
		{
			name:"col04",
			fieldName:"zipCd",
			header:{text: "우편번호"},
			width:70
		}
		,{
			name:"col05",
			fieldName:"addr1",
			header:{text: "주소1"},
			width:200,
			styles: {textAlignment: "near"},
			button: "action",
			buttonVisibility:"always"
		},
		{
			name:"col06",
			fieldName:"addr2",
			header:{text: "주소2"},
			width:200,
			styles: {textAlignment: "near"}
		},
		{
			name:"col07",
			fieldName:"useYn",
	        header:{text: "사용"},
			width:50,
			editable: false,
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
	        styles: {
            	paddingLeft: 8,
            	textAlignment: "center"}
	        }
		];

	gridMain.setColumns(columns);
	
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

	$( document ).ready(function() {
	    $('#std05Form #btnSearch').trigger('click');
	});

	//=======================================
	// 추가된 행만 수정가능.
	//=======================================
	gridMain.onCurrentRowChanged = function (grid, oldRow, newRow) {

        var rowState = dataMain.getRowState(newRow);
        
        //그리드에 beginInsertRow(), beginAppendRow()로 행이 추가된 경우 || dataProvider에 새로 추가된 행인 경우
        var editable = (rowState == "created");
		
        var column = grid.columnByField("whCd");
        grid.setColumnProperty(column, "editable", editable);
	};
	
    //============================
    // cell의 data가 변경되었을때.
    //============================
    gridMain.onCellEdited =  function (grid, itemIndex, dataRow, field) {
    	// console.log( "field=" + field  + " itemIndex=" + itemIndex + " dataRow=" + dataRow);
    	
		var currow = gridMain.getCurrent();
    	
		// 센터코드 중복 체크
		if( currow.fieldName == "whCd" ) {
    		var newValue = [grid.getValue(itemIndex, field)];

    		var options = {
    		    fields: ["whCd"],
    		    values: newValue,
    		    caseSensitive: false,
    		    startIndex: 0,
    		    wrap: true,
    		    select: false
    		};
    		
    		var iFindItemIndex = gridMain.searchItem(options);
    		
    		if( iFindItemIndex > -1 ) {
    			cmn.alert("센터코드가 중복됩니다.", function() {
    				gridMain.cancel();
        			gridMain.setFocus();
    			});
    		}
    	}
    };
    
	gridMain.onCellButtonClicked = function (grid, itemIndex, column) {
		//alert("CellButton Clicked: itemIndex=" + itemIndex + ", fieldName=" + column.fieldName + " dataRow=" + grid.getDataRow(itemIndex));
	    sample2_execDaumPostcode();
    };


    
	//====================================
	//  조회버튼 클릭
	//====================================
    $('#std05Form #btnSearch').on('click', function(e) {
		sForm$.submit();        	
	});
    
    <%-- 검색폼 submit  --%>
    sForm$.on('submit', function() {
		
    	gridMain.commit(false);
    	
    	var schParam = sForm$.serializeJson();
    	
    	cmn.ajax({
			url: "<c:url value='/classic/std/std05/mainList.json'/>",
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
    $('#std05Form #btnNew').on('click', function(e) {

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
    $('#std05Form #btnDelete').on('click', function(e) {
        
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
    				url:'<c:url value="/classic/std/std05/deleteWhList.json"/>',
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
    $('#std05Form #btnSave').on('click', function(e) {

    	gridMain.commit(false);

		// 변경체크
    	if( !fssReal.getUpdate(dataMain) ){
    		cmn.alert('변경된 데이터가 없습니다.', function() {
    			gridMain.setFocus();
    		});
    	 	return;
		}
    	
		// 센터코드 null 값 체크
		var iFindRow = fssReal.getDataRowNullValue(dataMain,"whCd");
		
		if( iFindRow > -1 ) {
			cmn.alert("센터코드가 필요합니다.",function() {
				gridMain.setCurrent({dataRow: iFindRow, fieldName: "whCd"});					
				gridMain.setFocus();
			});
			return;
		}
		
		var allDatas = fssReal.getJsonRows(dataMain, "U");  // Argu : dataProvider, "A"(All Row), "U"(create,update,delete)
    	
		var schParam = sForm$.serializeJson();
		
		cmn.ajax({
			url:'<c:url value="/classic/std/std05/saveWhList.json"/>',
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
	
    $( window ).resize(function() {
    	// alert($( window ).height());
    	
    	//alert($("#testtest").height());
    	
    	//$("#gridMainStd05").css('height',$(window).height()-400);
    	// $( window ).height()
    	
    	//$( "#log" ).append( "<div>Handler for .resize() called.</div>" );
    });
    
    
    
    
    
    
//================================================================
    // 우편번호 찾기 화면을 넣을 element
    var element_layer = document.getElementById('layer');

    function closeDaumPostcode() {
        // iframe을 넣은 element를 안보이게 한다.
        element_layer.style.display = 'none';
    }

    function sample2_execDaumPostcode() {
        new daum.Postcode({
            oncomplete: function(data) {
                console.log(1);
                // 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var fullAddr = data.address; // 최종 주소 변수
                var extraAddr = ''; // 조합형 주소 변수

                // 기본 주소가 도로명 타입일때 조합한다.
                if(data.addressType === 'R'){
                    //법정동명이 있을 경우 추가한다.
                    if(data.bname !== ''){
                        extraAddr += data.bname;
                    }
                    // 건물명이 있을 경우 추가한다.
                    if(data.buildingName !== ''){
                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                    }
                    // 조합형주소의 유무에 따라 양쪽에 괄호를 추가하여 최종 주소를 만든다.
                    fullAddr += (extraAddr !== '' ? ' ('+ extraAddr +')' : '');
                }
                
                console.log("=============================================");

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                var currow = gridMain.getCurrent();

                console.log(currow);
                console.log("=============================================");
                console.log(currow.dataRow);
                console.log("=============================================");
                
                dataMain.setValue(currow.dataRow,"zipCd",data.zonecode);
                dataMain.setValue(currow.dataRow,"addr1",fullAddr);
                console.log("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$=");
            	
                // document.getElementById('sample2_postcode').value = data.zonecode; //5자리 새우편번호 사용
                // document.getElementById('sample2_address').value = fullAddr;
                // document.getElementById('sample2_addressEnglish').value = data.addressEnglish;

                // iframe을 넣은 element를 안보이게 한다.
                // (autoClose:false 기능을 이용한다면, 아래 코드를 제거해야 화면에서 사라지지 않는다.)
                element_layer.style.display = 'none';
            },
            width : '100%',
            height : '100%',
            maxSuggestItems : 5
        }).embed(element_layer);

        // iframe을 넣은 element를 보이게 한다.
        element_layer.style.display = 'block';

        // iframe을 넣은 element의 위치를 화면의 가운데로 이동시킨다.
        initLayerPosition();
    }

    // 브라우저의 크기 변경에 따라 레이어를 가운데로 이동시키고자 하실때에는
    // resize이벤트나, orientationchange이벤트를 이용하여 값이 변경될때마다 아래 함수를 실행 시켜 주시거나,
    // 직접 element_layer의 top,left값을 수정해 주시면 됩니다.
    function initLayerPosition(){
        var width = 350; //우편번호서비스가 들어갈 element의 width
        var height = 470; //우편번호서비스가 들어갈 element의 height
        var borderWidth = 4; //샘플에서 사용하는 border의 두께

        // 위에서 선언한 값들을 실제 element에 넣는다.
        element_layer.style.width = width + 'px';
        element_layer.style.height = height + 'px';
        element_layer.style.border = borderWidth + 'px solid';
        // 실행되는 순간의 화면 너비와 높이 값을 가져와서 중앙에 뜰 수 있도록 위치를 계산한다.
        element_layer.style.left = (((window.innerWidth || document.documentElement.clientWidth) - width)/2 - borderWidth) + 'px';
        element_layer.style.top = (((window.innerHeight || document.documentElement.clientHeight) - height)/2 - borderWidth) + 'px';
    }
    

});
</script>



<div class="cont_wrap" id="std05Form">
	<!-- top area -->
	<div class="top_area">
		<h2>센터 std05....xzzy</h2>
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
						<th scope="row">센터코드/명</th>
						<td><input type="text" name="whNm" /></td>
					</tr>
				</tbody>
			</table>
			</form>
		</div>
		
			<!-- RealGrid Canvas 삽입-->
			<div id="gridMainStd05" style="width: 100%; height: 300px;"></div>
	</div>
	<div id="layer" style="display:none;position:fixed;overflow:hidden;z-index:1;-webkit-overflow-scrolling:touch;">
	<img src="//t1.daumcdn.net/localimg/localimages/07/postcode/320/close.png" id="btnCloseLayer" style="cursor:pointer;position:absolute;right:-3px;top:-3px;z-index:1" onclick="closeDaumPostcode()" alt="닫기 버튼">

</div>
	<!-- //cont area -->
</div>