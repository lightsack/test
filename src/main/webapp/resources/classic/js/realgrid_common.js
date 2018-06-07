var fssReal = function(options){
	var vars = {myVar  : 'original Value'};

	this.setRealGridJS = function() {
		RealGridJS.setTrace(false);
		RealGridJS.setRootContext("/fsswms/resources/plugins/realgrid");  // Root Context
	};
	
	this.setGridOptions = function(grid, xlsFileName) {
		$.each(grid, function(k, v){
			grid[k].setOptions({
								panel: { visible: false },
								//header: { resizable: true },
								footer: {visible: true},
								sortMode: "explicit",
								selecting: {
								    style: "rows"  // block,rows,columns,singleRow, singleColumn,none 
								},
								hideDeletedRows : true,
								header: {
								    height:0,
									minHeight: 24
								  //heightFill: "default"
								},
								display: {
								    rowHeight: 22
								  //rowResizable: false
								},
								hideDeletedRows: true,
							    //displayOptions:{ columnWidth : 200 },
								edit: {
							        enterToTab:true,
								    editable: false,
									insertable: true,  //행 삽입과 행 추가, 행 삭제가 가능하도록 옵션 조정
								    appendable: true,
								    updatable: true,
								    deletable: true
								  //editWhenFocused :true
							      //deleteRowsConfirm: true,
							      //deleteRowsMessage: "삭제하시겠습니까 ?"
								},
								indicator:{ visible:true },
								checkBar:{ visible:false },
								stateBar:{ visible:false },
								panel:{ visible:false },
								fixed: {
									rowResizable: false,
									colResizable: true,
								    rowCount: 0, colCount: 0
								}
								});
	
			grid[k].setStyles(eval("basicStyle"));
			
			grid[k].setContextMenu({label: "-"});
	
		    //  Popup Menu 클릭후 Event
			grid[k].onContextMenuItemClicked = function (grid, data, index) {
			    // alert("Context menu가 클릭됐습니다: " + label.label + "\n" + JSON.stringify(index));
				
				if( data.label == "좌측 열고정" ) {
					var column = grid.columnByField(index.fieldName);
					grid.setFixedOptions({
						colCount: column.displayIndex+1
					});
				}
				else if( data.label == "우측 열고정" ) {
					var column = grid.columnByField(index.fieldName);
					grid.setFixedOptions({
						rightColCount: grid.getColumns().length - column.displayIndex
					});
				}
				else if( data.label == "열고정 취소" ) {
					grid.setFixedOptions({
						colCount: 0,
						rightColCount: 0
					});
				}
				else if( data.label == "블록" ) {
					grid.setSelectOptions({
						style: 'block'
					});
				}
				else if( data.label == "행" ) {
					grid.setSelectOptions({
						style: 'rows'
					});
				}
				else if( data.label == "컬럼" ) {
					grid.setSelectOptions({
						style: 'columns'
					});
				}
				else if( data.label == "엑셀다운로드" ) {
					
					var sName = "";
					if( xlsFileName == undefined ) {
						sName = "gridExcel";
					}
					else {
						if( xlsFileName[k] == undefined ) {
							sName = "gridExcel";
						}
						else {
							sName = xlsFileName[k];
						} 
					}
					
			    	grid.exportGrid({
									type:     "excel",
									target:   "local",
									fileName: sName + ".xlsx",
									showProgress:       true,
									applyDynamicStyles: true, 
									progressMessage: "Excel Export..",
									indicator:       "default",
									header:          "default",
									footer:          "default",
									lookupDisplay:   "true",
									done: function () {  //내보내기 완료 후 실행되는 함수
									    null; // alert("다운로드완료");
									}
							        });
			    }
			}
		    //  Head 영역에서는 Context Menu 실행하지 않음
			grid[k].onContextMenuPopup = function (grid, x, y, elementName) {
				if( elementName == "IndicatorHead" || elementName == "StateBarHead" ) {
					return false;
				}
				else if( elementName == "HeaderCell" ) {
					grid.setContextMenu([
				                         { label: "좌측 열고정"}, 
				                         { label: "우측 열고정"}, 
				                         { label: "열고정 취소"}
				                        ]);
				}
				else if( elementName == "DataCell" || elementName == "GridBody" || elementName == undefined ) {
					var options = grid.getSelectOptions().style;
					
					grid.setContextMenu([{label: "엑셀다운로드"},
					                    { label: "선택모드",
										    children:[
										        {label:"행",type:"check",checked: options=="rows"},
										        {label:"컬럼",type:"check",checked: options=="columns"},
										        {label:"블록",type:"check",checked: options=="block"}
										    ]
										}
										]);
				}
				return true;  // return elementName != "HeaderCell";
		    }
		});
	};
	
	
	this.setProviderOptions = function(dataProvider) {
		
		$.each(dataProvider, function(k, v){		
			dataProvider[k].setOptions({
										dateFormat: "yyyy-MM-dd a hh:mm:ss",
										amText: "AM",
										pmText: "PM",
										softDeleting : true
										});
		});
	};
	
	//========================================
	// dataProvider에서 변경된 데이터가 있나 체크
	//========================================
	this.getUpdate = function(dataProvider) {
	    
		// alert(JSON.stringify(dataProvider.getAllStateRows()));
		
		if( dataProvider.getRowStateCount(RealGridJS.RowState.CREATED) > 0 ) return true;
	    if( dataProvider.getRowStateCount(RealGridJS.RowState.UPDATED) > 0 ) return true;
	    if( dataProvider.getRowStateCount(RealGridJS.RowState.DELETED) > 0 ) return true;
	    return false;
	};

	//=====================================
	// dataProvider에서 Null값 체크
	//=====================================
	this.getDataRowNullValue = function(dataProvider, fieldName) {

		for( var i=0; i<dataProvider.getRowCount(); i++ ) {
			
			var state = dataProvider.getRowState(i);
			if( state != RealGridJS.RowState.DELETED && state != RealGridJS.RowState.CREATE_AND_DELETED ) {
				var v = $.trim(dataProvider.getValue(i, fieldName));
				if( v.length < 1 ) {
					return i;
				}
			}
		}
		return -1;
	};
	
	//=============================
	// 컬럼에 특정값이 있는 row 찾기 
	//=============================
	this.getFindDataRow = function(grid, fieldName, v) {
		var options =	{
						fields: fieldName,
						values: v,
						caseSensitive: false,
						startIndex: 0,
						wrap: true,
						select: false
						};
		return grid.searchItem(options);
	};

	
	this.getJsonRow = function(dataProvider, dataRow) {

		var state = dataProvider.getRowState(dataRow);
		var jData = dataProvider.getJsonRow(dataRow);
		
		jData.row_state = state;
		
		return jData;
	};
	
	//==========================================================
	// DataProvider 에서 수정된행 또는 전체행을 json 형태로 Return
	//   Para : dataProvider
	//         ,sType : "A"(All Row)
	//                  "U"(create,update,delete)
	//==========================================================
	this.getJsonRows = function(dataProvider, sType) {
		
		var state;
		var jData;
		var jRowsData = [];
		
		if( sType == "A" ) {
			for( var i=0; i<dataProvider.getRowCount(); i++ ) {
				state = dataProvider.getRowState(i);
				
				if( (state != "none") && (state != "createAndDeleted") ) {
					jData = dataProvider.getJsonRow(i);
					jData.row_state = state;
					
					jRowsData.push(jData);
				}
			}
		}
		else {
			var rows = dataProvider.getAllStateRows();
			
			if( rows.updated.length > 0 ){
				$.each(rows.updated, function(k, v){
					jData = dataProvider.getJsonRow(v);
					jData.row_state = "updated";
					jRowsData.push(jData);
				});
			}
			
			if(rows.deleted.length > 0){
				$.each(rows.deleted, function (k, v){
					jData = dataProvider.getJsonRow(v);
					jData.row_state = "deleted";
					jRowsData.push(jData);
				});
			}
			
			if(rows.created.length > 0){
				$.each(rows.created, function (k, v){
					jData = dataProvider.getJsonRow(v);
					jData.row_state = "created";
					jRowsData.push(jData);
				});
			}
		}
		
		return jRowsData;
		
		// if(jRowsData.length == 0){
		// 	dataProvider.clearRowStates(true);
		// 	return;
		// }
	};
	
	

};

var fssReal = new fssReal({ myVar : 'new Value' });


