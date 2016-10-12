<%@ include file="/WEB-INF/views/pub/comm/meta.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>操作结果</title>
<script>
	function getTopWindow(win) {
		if (win.parent == null || win.parent == win) {
			return win;
		}
		return getTopWindow(win.parent);
	}
	function reLoad() {
		getTopWindow(window).location.href = '/ES';
	}
	$(function() {
		if ('${isReloadPage}' == 'Y') {
			setTimeout('reLoad()', 3000);
		}
	})
</script>
</head>
<body>
<div class="transparent_bg"></div>
<div class="pop_bg">
    <div class="success_content">操作成功！ <br />
    	<span>${operationResult}</span></div>  
 		<div style="margin-left:80px;">
   		 </div>   
</div>
</body>
</html>
