 <!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">

    <title>商品分类</title>
    <meta name="keywords" content="">
    <meta name="description" content="">

    <link href="${_ContextPath}/CacheableResource/css/bootstrap.min.css?v=3.4.0" rel="stylesheet">
    <link href="${_ContextPath}/CacheableResource/css/font-awesome.min.css?v=4.3.0" rel="stylesheet">
    <link href="${_ContextPath}/CacheableResource/css/plugins/iCheck/custom.css" rel="stylesheet">
    <link href="${_ContextPath}/CacheableResource/css/animate.min.css" rel="stylesheet">
    <link href="${_ContextPath}/CacheableResource/css/style.min.css?v=3.0.0" rel="stylesheet">
    <link href="${_ContextPath}/CacheableResource/css/style.wgqc.css?v=1.0.0" rel="stylesheet">
</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">
        <div class="row">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>商品分类</h5>
                        <div class="ibox-tools">
                            <a class="collapse-link">
                                <i class="fa fa-chevron-up"></i>
                            </a>
                        </div>
                    </div>
                    <div class="ibox-content">
                        <div class="row">
                            <div class="col-sm-1 m-b-xs">
                                <select class="input-sm form-control input-s-sm inline">
                                    <option value="0">全部</option>
                                    <option value="1">未上架</option>
                                    <option value="2">已上架</option>
                                </select>
                            </div>
                            <div class="col-sm-3">
                                <div class="input-group">
                                    <input type="text" placeholder="请输入分类名称" class="input-sm form-control" id="classifyNname"> <span class="input-group-btn">
                                        <button type="button" class="btn btn-sm btn-primary"> 搜索</button> </span>
                                </div>
                            </div>
                            <div class="col-sm-8 m-b-xs">&nbsp;</div>
                        </div>
                        <input  id="currentPage" type="hidden" value="1"/>
	        			<input id="totalPage" type="hidden"/>
                        <div class="table-responsive">
                            <table class="table table-striped">
                                <thead>
                                    <tr>
                                        <th>编号</th>
                                        <th>分类名称</th>
                                        <th>所属分类</th>
                                        <th>操作</th>
                                    </tr>
                                </thead>
                                <tbody id="orderDetailResultDiv"></tbody>
                            </table>
                            <nav id="orderDetailPaginBarDiv"></nav>
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
    
    <script>
        $(document).ready(function () {
            search(1);
        });
        
        function search(currentPage){
    		var classifyNname = $("#classifyNname").val();
    		$("#currentPage").val(currentPage);
    		var html = '';
    		$("#orderDetailResultDiv").html(html);		
    		var url = "${_ContextPath}/product/queryClassifyList"
    		var data="currentPage="+currentPage+"&classifyNname="+classifyNname;
    		$.ajax({contentType:"application/x-www-form-urlencoded; charset=UTF-8",
    			url:url,
    			data:data,
    			success:function(result){
    				var res = eval('('+result+')'); 
    				
    				if(res.ClassifyList !=null){
    					var i = 1;
    					$.each(res.ClassifyList,function(ky,vl) { 
    						html += "<tr>"; 
    						html += ("<td>"+ i +"</td>");
    						html += ("<td>"+ vl.cname +"</td>");
    						html += ("<td>"+ vl.parentCname +"</td>");
    						html += "<td>"; 
    						html += ("<input type='hidden' id='classifyId' value='"+vl.cid+"'/>");
	    					html += ("<td><button type='button' class='btn btn-primary btn-xs'><i class='fa fa-pencil'></i>&nbsp;<a style='color: #FFFFFF' href='${_ContextPath}/product/redirectProductInfo?productId="+vl.cid+"'>修改</a></button>&nbsp;&nbsp;</tr>");
    						i++
    					});
    					$("#orderDetailResultDiv").html(html);
    				}
    				
    				
    				$("#totalPage").val(res.totalPage);
    				var totalPage = parseInt(res.totalPage);
    				var paginHtml = '';
    				
    				paginHtml += "<ul class='pagination'>";
    				paginHtml += "<li class='active'><a href='javascript:search("+1+")'>首页</a></li>";
    				if(currentPage != 1){
    					
    					paginHtml += "<li class='active'><a href='javascript:search("+(currentPage-1)+")'>上一页</a></li>";
    				}
    				
    				paginHtml += "<li class='active'><a>"+currentPage+"</a></li>";
    				
    				if(currentPage != totalPage){
    					paginHtml += "<li class='active'><a href='javascript:search("+(currentPage+1)+")'>下一页</a></li>";
    				}
    				paginHtml += "<li class='active'><a href='javascript:search("+totalPage+")'>尾页</a></li>";
    				paginHtml += "</ul>";
    				$("#orderDetailPaginBarDiv").html(paginHtml);
    			},				
    			type:"POST"
    		});
        }
    </script>

</body>

</html>