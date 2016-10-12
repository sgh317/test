<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/views/comm/taglibs.jsp"%>
<%@ include file="/WEB-INF/views/comm/meta.jsp"%>
<html>
<head>

<title>评价管理</title>

<link
	href="${_ContextPath}/CacheableResource/css/bootstrap.min.css?v=3.4.0"
	rel="stylesheet">
<link
	href="${_ContextPath}/CacheableResource/css/font-awesome.min.css?v=4.3.0"
	rel="stylesheet">
<link
	href="${_ContextPath}/CacheableResource/css/plugins/iCheck/custom.css"
	rel="stylesheet">
<link href="${_ContextPath}/CacheableResource/css/animate.min.css"
	rel="stylesheet">
<link
	href="${_ContextPath}/CacheableResource/css/plugins/summernote/summernote.css"
	rel="stylesheet">
<link
	href="${_ContextPath}/CacheableResource/css/plugins/summernote/summernote-bs3.css"
	rel="stylesheet">
<link
	href="${_ContextPath}/CacheableResource/css/plugins/sweetalert/sweetalert.css"
	rel="stylesheet">
<link
	href="${_ContextPath}/CacheableResource/css/plugins/webuploader/webuploader.css"
	rel="stylesheet">
<link href="${_ContextPath}/CacheableResource/css/style.min.css?v=3.0.0"
	rel="stylesheet">
<link
	href="${_ContextPath}/CacheableResource/css/style.wgqc.css?v=1.0.0"
	rel="stylesheet">
<link rel="stylesheet"
	href="${_ContextPath}/CacheableResource/simplePagination/simplePagination.css" />
<script id="jquery"
	src="${_ContextPath}/CacheableResource/simplePagination/jquery.simplePagination.js"></script>
<script
	src="${_ContextPath}/CacheableResource/DatePicker/WdatePicker.js"></script>
<style type="text/css">
table tr td {
	white-space: nowrap;
	padding: 2px 5px;
}
</style>

<script type="text/javascript">
	var itemsOnPage = 4;
	$(function() {
		if("${evaluateList[0]['nickname']}".length>0){
			
			$("#pagingBar").pagination({			
				items : ${fn:length(evaluateList)},
				itemsOnPage : itemsOnPage,
				cssStyle : 'compact-theme',
				onPageClick: function(){paging()},
				onInit:function(){paging()}
			});
			function paging(){
		        page_index = $("#pagingBar").pagination('getCurrentPage')-1;
		        $("#table tr:gt(0)").hide();//表头
		         for(var i = page_index * itemsOnPage+1; i < page_index * itemsOnPage+1 + itemsOnPage; i++){
		            $("#table tr:eq(" + i + ")").show();
		        } 
			}
		}
			
	}); 
</script>
</head>


<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>用户评价</h5>
						<div class="ibox-tools">
							<a class="collapse-link"> <i class="fa fa-chevron-up"></i>
							</a>
						</div>
					</div>
					<div class="ibox-content">
						<div class="row">
							<div class="col-sm-1 m-b-xs">
								<select class="input-sm form-control input-s-sm inline">
									<option value="0">全部</option>
									<option value="1">未审核</option>
									<option value="2">评价成功</option>
								</select>
							</div>
							<div class="col-sm-3">
								<div class="input-group">
									<input type="text" placeholder="请输入微信账户名"
										class="input-sm form-control"> <span
										class="input-group-btn">
										<button type="button" class="btn btn-sm btn-primary">
											搜索</button>
									</span>
								</div>
							</div>
						</div>
					</div>
					<div class="table-responsive">
						<table class="table table-striped">
							<!-- <thead>
							<tr>
								<th>编号</th>
								<th>微信账户名</th>
								<th>评价商品</th>
								<th>评价星级</th>
								<th>评价内容</th>
								<th>评价时间</th>
								<th>状态</th>
								<th>操作</th>
							</tr>
						</thead> -->
							<thead>
								<tr class="table-title">
									<td width="4%" style="text-align: center">编号</td>
									<td width="15%" style="text-align: center">微信账户名</td>
									<td width="15%" style="text-align: center">评价商品</td>
									<td width="15%" style="text-align: center">评价星级</td>
									<td width="15%" style="text-align: center">评价内容</td>
									<td width="15%" style="text-align: center">评价时间</td>
									<td width="15%" style="text-align: center">状态</td>
									<td width="6%" style="text-align: center">操作</td>
								</tr>
							</thead>
							<tbody>
								<c:if test="${evaluateList[0]['nickname'] != ''}">
									<c:forEach items="${evaluateList}" var="info" varStatus="i">
										<tr class="table-tr">
											<td style="text-align: center">${i.index+1}</td>
											<td style="text-align: center">${info['nickname']}</td>
											<%-- 				<td style="text-align: center"><a
							href="evaluate_list?nickname=${info['nickname']}">${info['nickname']}</a>
						</td> --%>
											<td style="text-align: center">${info['skugood']}</td>
											<td style="text-align: center">${info['praisestar']}</td>
											<td style="text-align: center">${info['praisecontent']}</td>
											<td style="text-align: center">${info['praisetime']}</td>
											<td style="text-align: center">${info['auditstatus']}</td>

											<%--  <c:choose>
       		<c:when test="${info['auditStatus'] eq '03'}">
      			 <td style="text-align: center">未审核</td>
       		</c:when>       		
       		<c:when test="${info['orderStatus'] eq '02'}">
      			<td style="text-align: center"> 
	   			<a href="../resell/toLksOrderResell?OrderId=${info['orderId']}&OrderType=${info['orderType']}">处理</a>
       			</td>  
       		</c:when>       		       			      	
       		<c:otherwise>
       		<td style="text-align: center"> 
	   			<a href="../resell/toOutletsOrderResell?OrderId=${info['orderId']}&OrderType=${info['orderType']}">处理</a>
       			</td>  
       		</c:otherwise>
       </c:choose>  --%>
										</tr>
									</c:forEach>
								</c:if>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
	<c:if test="${evaluateList[0]['nickname'] != ''}">
		<div id="pagingBar"></div>
	</c:if>

</body>
</html>