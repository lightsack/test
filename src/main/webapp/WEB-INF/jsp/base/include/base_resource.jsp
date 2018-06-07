<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/base/include/tag_declare.jsp" %>
<link rel="stylesheet" href="<c:url value="/resources/plugins/jqueryUi/css/jquery-ui.min.css" />" />
<link rel="stylesheet" href="<c:url value="/resources/plugins/jqGrid/css/ui.jqgrid.css" />" />
<link rel="stylesheet" href="<c:url value="/resources/classic/css/jquery-ui.custom.css" />" />
<link rel="stylesheet" href="<c:url value="/resources/classic/css/ui.jqgrid.custom.css" />" />
<link rel="stylesheet" href="<c:url value="/resources/classic/css/common.css" />" />
<link rel="stylesheet" href="<c:url value="/resources/classic/css/content.css" />" />
<link rel="stylesheet" href="<c:url value="/resources/classic/css/popup.css" />" />
<%-- <link rel="stylesheet" href="<c:url value="/resources/css/datepicker.css" />" /> --%>

<script>var _contextPath_='${pageContext.request.contextPath}';</script>

<script src="<c:url value="/resources/classic/js/jquery-3.1.1.min.js" />"></script>
<!-- <script src="http://code.jquery.com/jquery-latest.js"></script> -->
<script src="<c:url value="/resources/classic/js/jquery.form.min.js" />"></script>
<script src="<c:url value="/resources/classic/js/jquery.fileDownload.js" />"></script>
<script src="<c:url value="/resources/plugins/jqueryUi/js/jquery-ui-1.12.1.min.js" />"></script>
<script src="<c:url value="/resources/classic/js/ui.datepicker-ko.js" />"></script>
<script src="<c:url value="/resources/plugins/jqGrid/js/grid.locale-kr.js" />"></script>
<script src="<c:url value="/resources/plugins/jqGrid/js/jquery.jqGrid-5.2.0.min.js" />"></script>
<script src="<c:url value="/resources/classic/js/base.min.js" />"></script>
<script src="<c:url value="/resources/classic/js/common.js" />"></script>

<!--realgrid-->
<script src="<c:url value="/resources/plugins/realgrid/realgridjs-lic.js" />"></script>
<script src="<c:url value="/resources/plugins/realgrid/realgridjs_eval.1.1.27.min.js" />"></script>
<script src="<c:url value="/resources/plugins/realgrid/realgridjs-api.1.1.27.js" />"></script>
<script src="<c:url value="/resources/plugins/realgrid/jszip.min.js" />"></script>
<script src="<c:url value="/resources/classic/js/realgrid_style.js" />"></script>
<script src="<c:url value="/resources/classic/js/realgrid_common.js" />"></script>

<script src="https://ssl.daumcdn.net/dmaps/map_js_init/postcode.v2.js"></script>