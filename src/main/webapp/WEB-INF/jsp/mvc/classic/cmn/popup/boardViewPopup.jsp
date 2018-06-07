<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/base/include/tag_declare.jsp" %>
<script>
(function($) {
// 	console.log("");	
}(jQuery));
</script>

<!-- Layer_popup -->
<div id="boardViewPopup" class="layer_pop" style="${boardInfo.picUrl eq null ? 'max-width:450px;' : 'max-width:900px;'} min-height:400px;">

	<div class="layer_content">
		<div class="top_area">
			<h1>
			<c:choose>
				<c:when test="${boardDiv eq '1'}">쇼핑정보</c:when>
				<c:when test="${boardDiv eq '2'}">공지사항</c:when>
				<c:when test="${boardDiv eq '3'}">기타</c:when>
				<c:otherwise>게시판정보</c:otherwise>
			</c:choose>
			</h1>
			<div class="btn_wrap">
				<button type="button">닫기</button>
			</div>
		</div>

		<div class="table_wrap" style="width:100%;">
			<table>
				<colgroup>
					<col style="width:100px">
					<col style="">
					<c:if test="${boardInfo.picUrl ne null}">
						<col width="100px"></col>
						<col style="width:40%"></col>
					</c:if>
				</colgroup>

				<tbody>
					<tr>
						<th scope="row">제목</th>
						<td>${boardInfo.title}</td>
						<c:if test="${boardInfo.picUrl ne null}">
							<th scope="row" rowspan="2">사진</th>
							<td rowspan="2" style="vertical-align:middle;">
								<div style="height:340px;overflow-x:hidden;overflow-y:auto;">
									<a href="${boardInfo.picUrl}" target="_blank"><img src="${boardInfo.picUrl}" width="100%" border="0" /></a>
								</div>
							</td>
						</c:if>
					</tr>
					<tr>
						<th scope="row">내용</th>
						<td>
							<div style="height:300px;overflow-x:hidden;overflow-y:auto;">
								${fn:replace(boardInfo.content, crlf, "<br/>")}<br/>
								<%-- <c:out value='${boardInfo.content}' escapeXml='false' /> --%>							
							</div>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		
	</div>
</div>
<!-- //Layer_popup -->