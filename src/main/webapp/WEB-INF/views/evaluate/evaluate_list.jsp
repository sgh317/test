 <!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">

    <title>评价管理</title>
    <meta name="keywords" content="">
    <meta name="description" content="">

    <link href="${_ContextPath}/CacheableResource/css/bootstrap.min.css?v=3.4.0" rel="stylesheet">
    <link href="${_ContextPath}/CacheableResource/css/font-awesome.min.css?v=4.3.0" rel="stylesheet">
    <link href="${_ContextPath}/CacheableResource/css/plugins/iCheck/custom.css" rel="stylesheet">
    <link href="${_ContextPath}/CacheableResource/css/animate.min.css" rel="stylesheet">
    <link href="${_ContextPath}/CacheableResource/css/plugins/summernote/summernote.css" rel="stylesheet">
    <link href="${_ContextPath}/CacheableResource/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
    <link href="${_ContextPath}/CacheableResource/css/plugins/webuploader/webuploader.css" rel="stylesheet">
    <link href="${_ContextPath}/CacheableResource/css/style.min.css?v=3.0.0" rel="stylesheet">
    <link href="${_ContextPath}/CacheableResource/css/style.wgqc.css?v=1.0.0" rel="stylesheet">
    <link href="${_ContextPath}/CacheableResource/css/sweetalert.css" rel="stylesheet" />
</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">
        <div class="row">
            <div class="col-lg-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>用户评价</h5>
                        <div class="ibox-tools">
                            <a class="collapse-link">
                                <i class="fa fa-chevron-up"></i>
                            </a>
                        </div>
                    </div>
                    <div class="ibox-content">
						<div class="row">
							<div class="col-sm-1 m-b-xs">
								<select id="auditStatus" class="input-sm form-control input-s-sm inline">
									<option value="">全部</option>
									<option value="N">未审核</option>
									<option value="Y">评价成功</option>
								</select>
							</div>
							<div class="col-sm-3">
								  <div class="input-group">
                                    <input type="text" id="evaluateClientName" placeholder="请输入微信账户名" class="input-sm form-control"> <span class="input-group-btn">
                                        <button type="button" class="btn btn-sm btn-primary" onclick="evaluateSearch(1)"> 搜索</button> </span>
                                </div>
							</div>
					</div>
                        <input  id="evaluateCurrentPage" type="hidden" value="1"/>
	        			<input id="evaluateTotalPage" type="hidden"/>
                        <div class="table-responsive">
							<table class="table table-striped">
								<thead>
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
								</thead>
								<tbody id="evaluateResultDiv"></tbody>
							</table>
							<nav id="evaluatePaginBarDiv"></nav>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- 全局js -->
    <script src="${_ContextPath}/CacheableResource/js/jquery-2.1.1.min.js"></script>
    <script src="${_ContextPath}/CacheableResource/js/bootstrap.min.js?v=3.4.0"></script>



    <!-- Peity -->
    <script src="${_ContextPath}/CacheableResource/js/plugins/peity/jquery.peity.min.js"></script>

    <!-- 自定义js -->
    <script src="${_ContextPath}/CacheableResource/js/content.min.js?v=1.0.0"></script>


    <!-- iCheck -->
    <script src="${_ContextPath}/CacheableResource/js/plugins/iCheck/icheck.min.js"></script>

    <!-- Peity -->
    <script src="${_ContextPath}/CacheableResource/js/demo/peity-demo.min.js"></script>

    <!-- laydate.js -->
    <script src="${_ContextPath}/CacheableResource/js/plugins/layer/laydate/laydate.js"></script>

    <!-- summernote.js -->
    <script src="${_ContextPath}/CacheableResource/js/plugins/summernote/summernote.min.js"></script>
    <script src="${_ContextPath}/CacheableResource/js/plugins/summernote/summernote-zh-CN.js"></script>

    <!-- swal.js -->
    <script src="${_ContextPath}/CacheableResource/js/plugins/sweetalert/sweetalert.min.js"></script>

    <!-- webuploader -->
    <script src="${_ContextPath}/CacheableResource/js/plugins/webuploader/webuploader.min.js"></script>

    <script type="text/javascript">
        
    	$(document).ready(function () {
    		evaluateSearch(1);
        });
    	function evaluateSearch(currentPage){
    		var auditStatus = $("#auditStatus").val()
    		var clientName = $("#evaluateClientName").val();
    		$("#evaluateCurrentPage").val(currentPage);
    		var html = '';
    		$("#evaluateResultDiv").html(html);		
    		var url = "${_ContextPath}/order/queryEvaluateList"
    			var data="currentPage="+currentPage+"&clientName="+clientName+"&auditStatus="+auditStatus;
    		$.ajax({contentType:"application/x-www-form-urlencoded; charset=UTF-8",
    			url:url,
    			data:data,
    			success:function(result){
    				var res = eval('('+result+')'); 
    				
    				if(res.recordsList !=null && res.recordsList.length>0){
    					for(var i=0;i<res.recordsList.length;i++){
    						var recordsInfo = res.recordsList[i];
    						var j = i+1;
    						html += "<tr>"; 
    						html += ("<td>"+ j +"</td>");
    						html += ("<td>"+recordsInfo.nickname+"</td>");
    						html += ("<td>"+recordsInfo.skugood+"</td>");
    						html += ("<td>"+recordsInfo.praisestar+"</td>");
    						html += ("<td>"+recordsInfo.praisecontent+"</td>");
    						html += ("<td>"+recordsInfo.praisetime+"</td>");
    						/* html += ("<td>"+recordsInfo.auditstatus+"</td>"); */
    						html += ("<input type='hidden' value='"+recordsInfo.auditstatus+"''/>");
    						if(recordsInfo.auditstatus == 'Y') {
    							html += ("<td><i class='fa fa-check text-danger'></i>&nbsp;评价成功</td>");
    							html += ("<td></td>");
    						} else {
	    						html += ("<td><span class='badge badge-primary'>未审核</span></td>");
	    						html += ("<td><button type='button' class='btn btn-success btn-xs' onclick=processEvaluate('"+recordsInfo.praiseId+"',this)>审核通过</button></td>");
    						}
    					};
    					$("#evaluateResultDiv").html(html);
    				}
    				
    				
    				$("#evaluateTotalPage").val(res.totalPage);
    				var totalPage = parseInt(res.totalPage);
    				var paginHtml = '';
    				
    				paginHtml += "<ul class='pagination'>";
    				paginHtml += "<li class='active'><a href='javascript:evaluateSearch("+1+")'>首页</a></li>";
    				if(currentPage != 1){
    					
    					paginHtml += "<li class='active'><a href='javascript:evaluateSearch("+(currentPage-1)+")'>上一页</a></li>";
    				}
    				
    				paginHtml += "<li class='active'><a>"+currentPage+"</a></li>";
    				
    				if(currentPage != totalPage){
    					paginHtml += "<li class='active'><a href='javascript:evaluateSearch("+(currentPage+1)+")'>下一页</a></li>";
    				}
    				paginHtml += "<li class='active'><a href='javascript:evaluateSearch("+totalPage+")'>尾页</a></li>";
    				paginHtml += "</ul>";
    				$("#evaluatePaginBarDiv").html(paginHtml);
    			},				
    			type:"POST"
    		});
        }
    	
    	function processEvaluate(praiseId,obj) {
    		 swal({
                 title: "您确定审核通过吗？",
                 text: "如果通过,页面上将能看到这条评论",
                 type: "warning",
                 showCancelButton: true,
                 closeOnConfirm: false,
                 confirmButtonText: "是的，我要审核通过",
                 confirmButtonColor: "#ec6c62"
             }, function() {
        	var url = "${_ContextPath}/evaluate/processEvaluate"
       		var data="praiseId="+praiseId;
       		$.ajax({contentType:"application/x-www-form-urlencoded; charset=UTF-8",
       			url:url,
       			data:data,
       			success:function(result){
       				$(obj).parent().parent().children("input").val('Y');
       				$($(obj).parent().parent().children("td").get(6)).html("<i class='fa fa-check text-danger'></i>&nbsp;评价成功");
       				$(obj).parent().html("");
       			},				
       			type:"POST"
       		}).done(function(data) {
                swal("操作成功!", "您已审核通过！", "success");
        });
    	});
    	}
    </script>

</body>

</html>