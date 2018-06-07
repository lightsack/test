<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/base/include/tag_declare.jsp" %>
<link rel="stylesheet" href="<c:url value="/resources/classic/css/calendar.css" />" />
<script>
jQuery(function($) {
	
	<%-- gnb --%>
	$(".gnb > ul > li > a").click(function() {
		if($(this).parent().hasClass('on')) {
			$(this).parent().removeClass("on");
		}else{
			$(this).parent().addClass("on").siblings().removeClass("on");
		}
	});
	$("#container").click(function() {
		$('.gnb ul li').removeClass('on');
	});
	
	<%-- 탭 선언 --%>
	var maxTabCnt = 15;
	var tabCounter = 1;
	var tabs = $("#tabs").tabs({
		activate: function(event,ui){
			cmn.initializeForm();
			$(".ui-jqgrid").each(function () {
				var grid$ = $(this).find('.ui-jqgrid-bdiv .ui-jqgrid-btable');
				var gridParam = grid$.getGridParam() || {};
				var parentWidth = $(this).parent().width()-1;
				if (true === gridParam.autowidth) { 
					$(this).find(".ui-jqgrid-btable").setGridWidth(parentWidth, true);
				} else {
					if (parentWidth > $(this).find(".ui-jqgrid-btable").width()) {
// 						$(this).find(".ui-jqgrid-btable").setGridWidth(parentWidth, true);
					} 
				}
			});
			
		}
	});
// 	tabs.find( ".ui-tabs-nav" ).sortable({
// 		axis: "x",
// 		stop: function() {
// 			tabs.tabs( "refresh" );
// 		}
// 	});
	
	<%-- 서브 메뉴 클릭  --%>
	$('.header .gnb .subMenu').on('click', function(e) {
		e.preventDefault();
		fn_addTab($(this).attr('data-progNm'), $(this).attr('data-progUrl'));
		$('.gnb ul li').removeClass('on');
		return false;
	});
	
	<%-- 탭 제거 --%>
	tabs.on("click", "span.ui-icon-close", function() {
		var panelId = $(this).closest("li").remove().attr("aria-controls");
		$("#" + panelId).remove();
		tabs.tabs( "refresh" );
	});
	
	<%-- 탭 생성  --%>
	function fn_addTab(progNm, progUrl) {
		
		if (maxTabCnt < tabs.find(".addTab").length + 1) {
			alert("메뉴는 최대 " + maxTabCnt + "개까지 오픈 할 수 있습니다.");
			return false;
		}
		
		var breakFlag = false;
		tabs.find(".addTab a").each(function(idx, item) {
			if (progNm === $(item).html()) {
				tabs.tabs({active: idx+1});
				breakFlag = true;
				return false;
			}
		});
		if (breakFlag)	return false;
		
		var id = "tabs-" + tabCounter,
		li = "<li class='addTab'><a href='#" + id + "'>" + progNm + "</a> <span class='ui-icon ui-icon-close' role='presentation'>Remove Tab</span></li>",
		tabContentHtml="";

		if ("" !== progUrl) {
			cmn.ajaxHtml({
				url: _contextPath_+'/classic/'+progUrl,
				success: function(htmlData) {
					tabs.find(".ui-tabs-nav").append(li);
					tabs.append("<div id='" + id + "' class='tab_contents'>" + htmlData + "</div>").tabs("refresh");
					var n = tabs.index(li);
				    tabs.tabs({active: n});
				    tabCounter++;
				    
					setTimeout(fn_resizeJqgrid, 10);
		        }
		    });
		} else {
			tabs.find(".ui-tabs-nav").append(li);
			tabs.append("<div id='" + id + "' class='tab_contents'>" + "등록된 url이 없습니다." + "</div>").tabs("refresh");
			var n = tabs.index(li);
		    tabs.tabs({active: n});
		    tabCounter++;
		}

	}
	
	function fn_resizeJqgrid() {
		$(".ui-jqgrid").each(function () {
			var grid$ = $(this).find('.ui-jqgrid-bdiv .ui-jqgrid-btable');
			var gridParam = grid$.getGridParam() || {};
			
			if (true === gridParam.autowidth) {
				var parentWidth = $(this).parent().width()-1;
				$(this).find(".ui-jqgrid-btable").setGridWidth(parentWidth, true);
			} else {
				var parentWidth = $(this).parent().width()-1;
				if (parentWidth > $(this).find(".ui-jqgrid-btable").width()) {
// 					$(this).find(".ui-jqgrid-btable").setGridWidth(parentWidth, true);
				}
			}
		});
	}
	
	
	<%-- == Main Content ============================================================================================== --%>
	<%-- 메뉴 바로가기 버튼 클릭 --%>
	$('#main #shortcut > a').on('click', function(e) {
		e.preventDefault();
		fn_addTab($(this).attr('data-progNm'), $(this).attr('data-progUrl'));
		return false;
	});
	
	<%-- 게시판 제목 클릭(팝업 호출) --%>
	$('#main .board .list').on('click', 'a', function(e) {
		var boardDiv = $(this).attr('data-boardDiv');
		var regNo = $(this).attr('data-regNo');
		cmn.ajaxModal({
			url:'<c:url value="/classic/cmn/popup/boardViewPopup.do" />',
			data:{
				'boardDiv': boardDiv,
				'regNo': regNo
			}
		});
	});
	
	<%-- 이전달 아이콘(<) 클릭 --%>
	$('#main #prev').on('click', function(e) {
		vDate.setMonth(vDate.getMonth() - 1);
		fn_makeCalendar();
	});
	
	<%-- 다음달 아이콘(>) 클릭 --%>
	$('#main #next').on('click', function(e) {
		vDate.setMonth(vDate.getMonth() + 1);
		fn_makeCalendar();
	});
		
	
	var vDate = new Date;
	<%-- 달력 생성 --%>
    function fn_makeCalendar() {
    	var yearMonth = cmn.formatDate(vDate, 'yyyyMM');
    	var year = cmn.formatDate(vDate, 'yyyy');
    	var month = cmn.formatDate(vDate, 'MM');
    	
    	$('#main #year').text(year);
    	$('#main #mon').text(month);
    	$('#main .day_wrap').html('');
    	$('#main .day_wrap').parent('td').removeClass('today');
    	cmn.ajax({
			url: '<c:url value="/classic/cmn/main/selectCalendarList.json"/>',
			data: {
    			'yearMonth': yearMonth
			},
			success: function(data) {
				var calendarlist = data.calendarlist;
				var beforeWeekly = 0;
				var rows = 0; 
				for (var i=0; i<calendarlist.length; i++) {
					var day = calendarlist[i].day;
					var week = calendarlist[i].week;
					var weekly = calendarlist[i].weekly;
					var boardDiv = calendarlist[i].boardDiv;
					var noticeYn = calendarlist[i].noticeYn;
					var regNo = calendarlist[i].regNo;
					var subTitle = calendarlist[i].subTitle;
					var todayYn = calendarlist[i].todayYn;
					if (beforeWeekly !== weekly) {
						beforeWeekly = weekly;
						rows = rows + 1;
					}
					var div$ = $('#main #'+rows+'_'+week);
					
					if (!div$.html()) {
						div$.append('<span class="day">'+day+'</span>');
					}
					if (boardDiv != 1) {
						var html = '<p>';
						if (noticeYn === 'Y') html += '<strong>';
						if (calendarlist[i].subTitle != undefined)
							html += '<span data-regNo='+regNo+' data-boardDiv='+boardDiv+'>'+ '￦ ' + cmn.formatAmount(subTitle)+'</span>';
						if (noticeYn === 'Y') html += '</strong>';
						html += '</p>';
						div$.append(html);
					}
					if ('Y' === todayYn) {
						div$.parent('td').addClass('today');
					}
				}
	        }
	    });
    }
    
    
    <%-- Onload Event --%>
    fn_makeCalendar();
    
    
	
});

<%-- == 세션정보 전역변수 선언 ===================== --%>
// var _loginId_ = "${sessionScope[cmnConstants.sessionUserId]}";
// var _dept_ = "${sessionScope[cmnConstants.sessionUser].dept}";
// var _office_ = "${sessionScope[cmnConstants.sessionUser].office}";
// var _posit_ = "${sessionScope[cmnConstants.sessionUser].posit}";

</script>

<div class="contents">
	<!-- Tab -->
	<div id="tabs" class="tab_wrap">
		<ul>
			<li class="blank"></li>
			<li><a href="#main">메&nbsp;&nbsp;인</a></li>
		</ul>
		<div id='main' class='tab_contents'>
			<div class="cont_wrap">
				<div class="calendar_wrap">
					<div class="fl_left" style="width:100%;">
						<!-- Calendar top -->
						<div class="cal_top">
							<span class="year" id="year"></span>
							<div class="month">
								<button type="button" class="prev" id="prev"><span class="hide" id="">이전달</span></button>
								<span class="mon" id="mon"></span>
								<button type="button" class="next" id="next"><span class="hide" id="">다음달</span></button>
							</div>
						</div>
						<!-- //Calendar top -->

						<!-- Calendar -->
						<div class="table_wrap">
							<table>
							<caption>달</caption>
							<colgroup>
								<col width="14.3%" span="6" />
								<col width="14.2%" />
							</colgroup>
							<thead>
								<tr>
									<th scope="col" class="sun">일</th>
									<th scope="col">월</th>
									<th scope="col">화</th>
									<th scope="col">수</th>
									<th scope="col">목</th>
									<th scope="col">금</th>
									<th scope="col" class="sat">토</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="i" begin="1" end="5" varStatus="status">
								<tr>
									<td class="sun"><div class="day_wrap" id="${i}_1"></div></td>
									<td><div class="day_wrap" id="${i}_2"></div></td>
									<td><div class="day_wrap" id="${i}_3"></div></td>
									<td><div class="day_wrap" id="${i}_4"></div></td>
									<td><div class="day_wrap" id="${i}_5"></div></td>
									<td><div class="day_wrap" id="${i}_6"></div></td>
									<td class="sat"><div class="day_wrap" id="${i}_7"></div></td>
								</tr>
								</c:forEach>
							</tbody>
							</table>
						</div>
						<!-- //Calendar -->

						<div class="board_wrap">
							<div class="board">
								<div class="list">
									<div class="tit_wrap">
										<h2>쇼핑정보</h2>
									</div>
									<ul>
										<c:forEach items="${board1List}" var="board" varStatus="status">
											<li <c:if test="${board.newYn eq 'Y'}">class="new"</c:if>>
												<a style="cursor:pointer;" data-regNo='${board.regNo}' data-boardDiv='${board.boardDiv}'>
													<c:if test="${board.noticeYn eq 'Y'}"><Strong>${board.subTitle}</Strong></c:if>
													<c:if test="${board.noticeYn ne 'Y'}">${board.subTitle}</c:if>
													<c:if test="${board.newYn eq 'Y'}"><span class="new">NEW</span></c:if>
												</a>
											</li>
										</c:forEach>
									</ul>
								</div>
							</div>

							<div class="board">
								<div class="list">
									<div class="tit_wrap">
										<h2>공지사항</h2>
									</div>
									<ul>
										<c:forEach items="${board2List}" var="board" varStatus="status">
											<li <c:if test="${board.newYn eq 'Y'}">class="new"</c:if>>
												<a style="cursor:pointer;" data-regNo='${board.regNo}' data-boardDiv='${board.boardDiv}'>
													<c:if test="${board.noticeYn eq 'Y'}"><Strong>${board.subTitle}</Strong></c:if>
													<c:if test="${board.noticeYn ne 'Y'}">${board.subTitle}</c:if>
													<c:if test="${board.newYn eq 'Y'}"><span class="new">NEW</span></c:if>
												</a>
											</li>
										</c:forEach>
									</ul>
								</div>
							</div>

							<div class="board">
								<div class="list">
									<div class="tit_wrap">
										<h2>기타</h2>
									</div>
									<ul>
										<c:forEach items="${board3List}" var="board" varStatus="status">
											<li <c:if test="${board.newYn eq 'Y'}">class="new"</c:if>>
												<a style="cursor:pointer;" data-regNo='${board.regNo}' data-boardDiv='${board.boardDiv}'>
													<c:if test="${board.noticeYn eq 'Y'}"><Strong>${board.subTitle}</Strong></c:if>
													<c:if test="${board.noticeYn ne 'Y'}">${board.subTitle}</c:if>
													<c:if test="${board.newYn eq 'Y'}"><span class="new">NEW</span></c:if>
												</a>
											</li>
										</c:forEach>
									</ul>
								</div>
							</div>

						</div>
					</div>

<!-- 					<div class="fl_right"> -->
<!-- 						<div class="btn_wrap" id="shortcut"> -->
<%-- 							<c:forEach items="${deptProglist}" var="deptProg" varStatus="status"> --%>
<%-- 								<a data-progNm='${deptProg.progNm}' data-progUrl='${deptProg.progUrl}'>${deptProg.progNm}</a> --%>
<%-- 							</c:forEach> --%>
<!-- 						</div> -->
<!-- 					</div> -->
				</div>

			</div>
		</div>
	</div>
	<!-- Tab -->
</div>
