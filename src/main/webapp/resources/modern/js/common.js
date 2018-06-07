(function($, undefined) {
"use strict";
jQuery.extend(jQuery.fn.fmatter, {
	method:{
		getLinkData:function(cellvalue, opt, rowdata, options) {
	    	var url = opt.url || '';
	    	if(url && cmn.startsWith(url, '/')) {
	    		url = cmn.getContextPath() + url;
	    	}
	    	var paramObj = {};
	    	if(opt.addParam) {
	    		if(typeof opt.addParam === 'string') {
	    			paramObj = cmn.parseParam(opt.addParam, paramObj);
	    		} else if(typeof opt.addParam === 'object') {
	    			$.extend(paramObj, opt.addParam);
	    		}
	    	}
	    	if(opt.dataParam) {
	    		$.each(opt.dataParam, function(k, v) {
	    			paramObj[v] = rowdata[k];
	    		});
	    	}
	    	opt.idName = opt.idName || 'id';
	    	if(opt.idName) {
	    		paramObj[opt.idName] = cellvalue;
	    	}
//	    	var param = paramObj;
	    	var param = cmn.paramUpdateToString(paramObj);
	    	var attr = '';
	    	if(opt.rowId) {
	    		attr += ' row-id="' + options.rowId + '"';
	    	}
	    	if(opt.attr) {
	    		$.each(opt.attr, function(k, v) {
	    			attr += ' data-' + k + '="' + (v||'') + '"';
	    		});
	    	}
	    	var dataAttr = '';
	    	if(opt.dataAttr) {
	    		$.each(opt.dataAttr, function(k, v) {
	    			dataAttr += ' data-' + k + '="' + rowdata[v] + '"';
	    		});
	    	}
	    	return {'url':url, 'param':param, 'attr':attr, 'dataAttr':dataAttr};
		}
	},
    link:function(cellvalue, options, rowdata) {
    	if(!cellvalue) {
    		return '-';
    	}
    	var opt = options.colModel.formatoptions || {};
    	if(opt.validFunc && $.isFunction(opt.validFunc)) {
    		if(!opt.validFunc.apply(this, [cellvalue, rowdata])) {
    			return cellvalue;
    		}
    	}
    	var linkData = $.fn.fmatter.method.getLinkData(cellvalue, opt, rowdata, options);
    	var ignoreLink = [];
    	if(opt.ignoreLink) {
    		if(typeof opt.ignoreLink === 'string') {
    			ignoreLink.push(opt.ignoreLink);
    		} else if(typeof opt.ignoreLink === 'object') {
    			ignoreLink = opt.ignoreLink;
    		}
    		if($.inArray(cellvalue, ignoreLink) > -1) {
    			return cellvalue;
    		}
    	}
    	if(true === opt.amount) {
    		cellvalue = cmn.formatAmount(cellvalue);
    	}
    	return '<a href="' + linkData.url + linkData.param + '"' + linkData.attr + linkData.dataAttr + '>'+cellvalue+'</a>';    		
	},
	button:function(cellvalue, options, rowdata) {
		var opt = options.colModel.formatoptions || {};
		var linkData = $.fn.fmatter.method.getLinkData(cellvalue, opt, rowdata, options);
    	var btnType = opt.btnType || 'button';
    	var btnText = opt.btnText || (cellvalue || '확인');
    	var btnIconClass = opt.btnIconClass;
    	var btnSize = opt.btnSize || 'medium';
    	var btnClass = 'btn_pack';
    	var disabledStr = '';
    	if(opt.validFunc && $.isFunction(opt.validFunc)) {
    		if(!opt.validFunc.apply(this, [cellvalue, rowdata])) {
    			btnClass = 'btn_pack_disable';
    			disabledStr = ' disabled="disabled"';
    		}
    	}
    	if(opt.enableBtn) {
    		var enableBtn = [];
    		if(typeof opt.enableBtn === 'string') {
    			enableBtn.push(opt.enableBtn);
    		} else if(typeof opt.enableBtn === 'object') {
    			enableBtn = opt.enableBtn;
    		}
    		if($.inArray(cellvalue, enableBtn) < 0) {
    			btnClass = 'btn_pack_disable';
    			disabledStr = ' disabled="disabled"';
    		}
    	} else if(opt.disableBtn) {
    		var disableBtn = [];
        	if(opt.disableBtn) {
        		if(typeof opt.disableBtn === 'string') {
        			disableBtn.push(opt.disableBtn);
        		} else if(typeof opt.disableBtn === 'object') {
        			disableBtn = opt.disableBtn;
        		}
        		if($.inArray(cellvalue, disableBtn) > -1) {
        			btnClass = 'btn_pack_disable';
        			disabledStr = ' disabled="disabled"';
        		}
        	}
    	}
    	var btn = '<div class="btn_wrap">';
    	if(btnType === 'link') {
    		btn += '<a href="' + linkData.url + linkData.param + '"' + linkData.attr + linkData.dataAttr + '>'+btnText+'</a>';
    	} else {
    		btn += '<button type="button" style="cursor:pointer;" class="btn_base" ' + linkData.attr + linkData.dataAttr + disabledStr + '>'+btnText+'</button>';
    	}
    	return btn;
	},
	datetime:function(cellvalue, options, rowdata) {
		return cmn.formatTimeToDate(cellvalue, 'yyyy-MM-dd HH:mm:ss') || '-';
	},
	dateday:function(cellvalue, options, rowdata) {
		return cmn.formatTimeToDate(cellvalue, 'yyyy-MM-dd') || '-';
	},
	formatDate:function(cellvalue, options, rowdata) {
		return cmn.formatDate(cellvalue, 'yyyy-MM-dd') || '-';
	},
	amount:function(cellvalue, options, rowdata) {
		return cmn.formatAmount(cellvalue) || '-';
	}
});

$.extend($.jgrid.defaults, {
	shrinkToFit:true,
	autowidth:true,
	autoload:true,
	sortable:true,
	mtype:'POST',
	height:'auto',
	datatype:'local',
	scrollbar : 'auto',
	multiSort:true,
	rowNum:1000000,
	cellsubmit: 'clientArray',
	prmNames:{
		page:'pg',
		sort:'sortColumn',
		order:'sortOrder'
	},
	jsonReader:{
		root:'list',          
		repeatItems:false
	},
	loadComplete:function(data) {
		var grid$ = $(this);
		var gridParam = grid$.getGridParam() || {};
		var list;
		if(gridParam.datatype === 'local') {
			list = gridParam.data;
		} else {
			list = data[gridParam.jsonReader.root];
		}
		gridParam.gridData = list;
		var gridParam = grid$.getGridParam() || {};
		if (false === gridParam.autowidth) {
			var gridWrap = $(this).parents('.grid_wrap');
			gridWrap.css('overflow-x', 'auto');
		}
	},
	afterInsertRow: function (rowId, rowData, rowelem) {
		var grid$ = $(this);
		grid$.setCell(rowId, 'flag', 'I');
    },
	afterEditCell:function(rowId, cellname, value, iRow, iCol){
		var grid$ = $(this);
		$("#"+iRow+"_"+cellname, grid$).blur(function(){
    		grid$.jqGrid("saveCell", iRow, iCol);
        });
		$("#"+iRow+"_"+cellname, grid$).focus(function(e){
			var target = $(e.target);
			if (target.is('input')) {
				this.select();
			}
		});
    },
    afterSaveCell: function(rowId, cellname, value, iRow, iCol){
    	var grid$ = $(this);
    	if ("I" !== grid$.getCell(rowId, 'flag')) {
    		grid$.setCell(rowId, 'flag', 'U');
    	}
    },
    loadError:function(jqXHR, textStatus, errorThrown) {
//		console.log(jqXHR, textStatus, errorThrown);
//		cmn.errorHandle(jqXHR, textStatus, errorThrown);
	},
});

$.jgrid.extend({
    getGridData:function(rowId) {
    	var gridParam = this.getGridParam() || {};
    	var list, data;
		if(gridParam.datatype === 'local') {
			list = gridParam.data;
		} else {
			list = gridParam.gridData;
		}
		if(list) {
			if(rowId) {
				data = data = list[rowId-1];
			} else {
				data = list;
			}
		}
		return data;
    }
});

jQuery.browser = {};
(function () {
    jQuery.browser.msie = false;
    jQuery.browser.version = 0;
    if (navigator.userAgent.match(/MSIE ([0-9]+)\./)) {
        jQuery.browser.msie = true;
        jQuery.browser.version = RegExp.$1;
    }
})();


}(jQuery));