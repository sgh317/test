 <!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">

    <title>充值记录</title>
    <meta name="keywords" content="">
    <meta name="description" content="">

    <link href="${_ContextPath}/CacheableResource/css/bootstrap.min.css?v=3.4.0" rel="stylesheet">
    <link href="${_ContextPath}/CacheableResource/css/font-awesome.min.css?v=4.3.0" rel="stylesheet">
    <link href="${_ContextPath}/CacheableResource/css/plugins/iCheck/custom.css" rel="stylesheet">
    <link href="${_ContextPath}/CacheableResource/css/animate.min.css" rel="stylesheet">
    <link href="${_ContextPath}/CacheableResource/css/plugins/summernote/summernote.css" rel="stylesheet">
    <link href="${_ContextPath}/CacheableResource/css/plugins/summernote/summernote-bs3.css" rel="stylesheet">
    <link href="${_ContextPath}/CacheableResource/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
    <link href="${_ContextPath}/CacheableResource/css/plugins/webuploader/webuploader.css" rel="stylesheet">
    <link href="${_ContextPath}/CacheableResource/css/style.min.css?v=3.0.0" rel="stylesheet">
    <link href="${_ContextPath}/CacheableResource/css/style.wgqc.css?v=1.0.0" rel="stylesheet">
</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">
        <div class="row">
            <div class="col-md-6">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <span class="label label-success pull-right">总</span>
                        <h5>充值总额</h5>
                    </div>
                    <div class="ibox-content">
                        <h1 class="no-margins">${sum_recharge}</h1>
                        <small>总充值</small>
                    </div>
                </div>
            </div>
            <div class="col-md-6">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <span class="label label-info pull-right">总</span>
                        <h5>消费总额</h5>
                    </div>
                    <div class="ibox-content">
                        <h1 class="no-margins">${sum_consum}</h1>
                        <small>总消费</small>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>充值记录</h5>
                        <div class="ibox-tools">
                            <a class="collapse-link">
                                <i class="fa fa-chevron-up"></i>
                            </a>
                        </div>
                    </div>
                    <div class="ibox-content">
                        <div class="row">
                            <div class="col-sm-5">
                                <div class="input-group">
                                    <input type="text" id="rechargeClientName" placeholder="请输入微信账户名" class="input-sm form-control"> <span class="input-group-btn">
                                        <button type="button" class="btn btn-sm btn-primary" onclick="rechargeSearch(1)"> 搜索</button> </span>
                                </div>
                            </div>
                        </div>
                        <input  id="rechargeCurrentPage" type="hidden" value="1"/>
	        			<input id="rechargeTotalPage" type="hidden"/>
                        <div class="table-responsive">
                            <table class="table table-striped">
                                <thead>
                                <tr>
                                    <th>编号</th>
                                    <th>微信账户名</th>
                                    <th>支付方式</th>
                                    <th>充值金额</th>
                                    <th>充值时间</th>
                                </tr>
                                </thead>
                                <tbody id="rechargeResultDiv"></tbody>
                            </table>
                            <nav id="rechargePaginBarDiv"></nav>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-6">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>消费记录</h5>
                        <div class="ibox-tools">
                            <a class="collapse-link">
                                <i class="fa fa-chevron-up"></i>
                            </a>
                        </div>
                    </div>
                    <div class="ibox-content">
                        <div class="row">
                            <div class="col-sm-5">
                                <div class="input-group">
                                    <input type="text" id="consumeClientName" placeholder="请输入微信账户名" class="input-sm form-control"> <span class="input-group-btn">
                                        <button type="button" class="btn btn-sm btn-primary" onclick="consumeSearch(1)"> 搜索</button> </span>
                                </div>
                            </div>
                        </div>
                        <input  id="consumeCurrentPage" type="hidden" value="1"/>
	        			<input id="consumeTotalPage" type="hidden"/>
                        <div class="table-responsive">
                            <table class="table table-striped">
                                <thead>
                                <tr>
                                    <th>编号</th>
                                    <th>微信账户名</th>
                                    <th>消费订单号</th>
                                    <th>支付方式</th>
                                    <th>消费金额</th>
                                    <th>消费时间</th>
                                </tr>
                                </thead>
                                <tbody id="consumeResultDiv"></tbody>
                            </table>
                            <nav id="consumePaginBarDiv"></nav>
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
    		rechargeSearch(1);
    		consumeSearch(1);
        });
    	function rechargeSearch(currentPage){
    		var clientName = $("#rechargeClientName").val();
    		$("#rechargeCurrentPage").val(currentPage);
    		var html = '';
    		$("#rechargeResultDiv").html(html);		
    		var url = "${_ContextPath}/order/queryRechargeList"
    			var data="currentPage="+currentPage+"&clientName="+clientName;
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
    						html += ("<td>"+recordsInfo.paytype+"</td>");
    						html += ("<td>"+recordsInfo.payamount+"</td>");
    						html += ("<td>"+recordsInfo.paytime+"</td></tr>");
    					}			
    					$("#rechargeResultDiv").html(html);
    				}
    				
    				
    				$("#rechargeTotalPage").val(res.totalPage);
    				var totalPage = parseInt(res.totalPage);
    				var paginHtml = '';
    				
    				paginHtml += "<ul class='pagination'>";
    				paginHtml += "<li class='active'><a href='javascript:rechargeSearch("+1+")'>首页</a></li>";
    				if(currentPage != 1){
    					
    					paginHtml += "<li class='active'><a href='javascript:rechargeSearch("+(currentPage-1)+")'>上一页</a></li>";
    				}
    				
    				paginHtml += "<li class='active'><a>"+currentPage+"</a></li>";
    				
    				if(currentPage != totalPage){
    					paginHtml += "<li class='active'><a href='javascript:rechargeSearch("+(currentPage+1)+")'>下一页</a></li>";
    				}
    				paginHtml += "<li class='active'><a href='javascript:rechargeSearch("+totalPage+")'>尾页</a></li>";
    				paginHtml += "</ul>";
    				$("#rechargePaginBarDiv").html(paginHtml);
    			},				
    			type:"POST"
    		});
        }
    	function consumeSearch(currentPage){
    		var clientName = $("#consumeClientName").val();
    		$("#consumeCurrentPage").val(currentPage);
    		var html = '';
    		$("#consumeResultDiv").html(html);		
    		var url = "${_ContextPath}/order/queryConsumeList"
    		var data="currentPage="+currentPage+"&clientName="+clientName;
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
    						html += ("<td>"+recordsInfo.orderNum+"</td>");
    						html += ("<td>"+recordsInfo.paytype+"</td>");
    						html += ("<td>"+recordsInfo.payamount+"</td>");
    						html += ("<td>"+recordsInfo.paytime+"</td></tr>");
    					}			
    					$("#consumeResultDiv").html(html);
    				}
    				
    				
    				$("#consumeTotalPage").val(res.totalPage);
    				var totalPage = parseInt(res.totalPage);
    				var paginHtml = '';
    				
    				paginHtml += "<ul class='pagination'>";
    				paginHtml += "<li class='active'><a href='javascript:consumeSearch("+1+")'>首页</a></li>";
    				if(currentPage != 1){
    					
    					paginHtml += "<li class='active'><a href='javascript:consumeSearch("+(currentPage-1)+")'>上一页</a></li>";
    				}
    				
    				paginHtml += "<li class='active'><a>"+currentPage+"</a></li>";
    				
    				if(currentPage != totalPage){
    					paginHtml += "<li class='active'><a href='javascript:consumeSearch("+(currentPage+1)+")'>下一页</a></li>";
    				}
    				paginHtml += "<li class='active'><a href='javascript:consumeSearch("+totalPage+")'>尾页</a></li>";
    				paginHtml += "</ul>";
    				$("#consumePaginBarDiv").html(paginHtml);
    			},				
    			type:"POST"
    		});
        }
    </script>

</body>

</html>