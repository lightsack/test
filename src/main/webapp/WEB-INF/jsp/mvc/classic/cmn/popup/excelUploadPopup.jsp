<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/base/include/tag_declare.jsp" %>
<script>
(function($) {

	var uForm$ = $('#excelUploadPopup #uploadForm');
	var fileTarget = $('#excelUploadPopup .filebox .upload-hidden');
	
	fileTarget.on('change', function(){
		var uploadName$ = $(this).siblings('.upload-name');
		var fileValue = $(this).val();
		if (fileValue) {
			if(window.FileReader){ // modern browser
				var filename = $(this)[0].files[0].name;
			} else { // old IE
				var filename = $(this).val().split('/').pop().split('\\').pop(); // 파일명만 추출 
			}
		    var fileExt = filename.substring(filename.lastIndexOf('.'));
		    var validExts = new Array(".xlsx", ".xls");
		    if (validExts.indexOf(fileExt) < 0) {
				cmn.alert("엑셀 파일만 선택할 수 있습니다.");
				if ($.browser.msie) {
					// ie 일때 input[type=file] init.
					fileTarget.replaceWith(fileTarget.clone(true));
				} else {
					// other browser 일때 input[type=file] init.
					fileTarget.val("");
				}
				uploadName$.val("");
				return false;
		    } else {
		    	uploadName$.val(filename); 
		    }
		}
	});
	
	$('#excelUploadPopup .filebox .upload-name').on('click', function() {
		fileTarget.trigger('click');
	});
	
	$('#excelUploadPopup #btnUpload').on('click', function() {
		<%-- validation --%>
    	if(!cmn.checkRequired($('#excelUploadPopup #uploadForm .upload-name'), "파일"))	return false;
    	
    	uForm$.attr("action", "<c:url value='/classic/cmn/popup/excelUpload.json'/>");
    	uForm$.ajax({
			success: function(data) {
				cmn.alert("업로드 되었습니다.", function () {
					$('#excelUploadPopup').data('callback').apply(this, [data.list]);
					cmn.closePopup($('#excelUploadPopup'));
				});
	        }
	    });
	});
	
}(jQuery));
</script>

<!-- Layer_popup -->
<div id="excelUploadPopup" class="layer_pop" style="max-width:500px; min-height:110px;">

	<div class="layer_content">
		<div class="top_area">
			<h1>엑셀업로드</h1>
			<div class="btn_wrap">
				<button type="button" id="btnUpload">업로드</button>
				<button type="button">닫기</button>
			</div>
		</div>

		<form id="uploadForm" method="post" enctype="multipart/form-data">
		<input type="hidden" name="progId" value="${progId}" />
		<input type="hidden" name="travAgenCd" value="${travAgenCd}" />
		<input type="hidden" name="boxNo" value="${boxNo}" />
		<div class="table_wrap filebox" style="width:100%;">
			<table>
				<colgroup>
					<col style="width:100px">
					<col style="">
				</colgroup>
				<tbody>
					<tr>
						<th scope="row"><span class="imp">*</span>파일명</th>
						<td>
							<input type="text" class="upload-name" readonly="readonly" placeholder="선택된 파일 없음" />
							<label for="excelFile">파일 선택</label>
							<input type="file" id="excelFile" name="excelFile" class="upload-hidden" accept=".xls, .xlsx"/>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		</form>
		
	</div>
</div>
<!-- //Layer_popup -->