<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">

    <title>广告位管理</title>
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
</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">
        <div class="row">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>广告位</h5>
                        <div class="ibox-tools">
                            <a class="collapse-link">
                                <i class="fa fa-chevron-up"></i>
                            </a>
                        </div>
                    </div>
                    <div class="ibox-content">
                    <input  id="adCurrentPage" type="hidden" value="1"/>
	        			<input id="adTotalPage" type="hidden"/>
                        <div class="table-responsive">
                            <table class="table table-striped">
                                <thead>
                                <tr>
                                	<th>编号</th>
                                    <th>广告位图片</th>
                                    <th>广告位置</th>
                                    <th>链接地址</th>
                                    <th>状态</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                 <tbody id="adResultDiv"></tbody>
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
    		adSearch(1);
        });
    	function adSearch(currentPage){
    		$("#adCurrentPage").val(currentPage);
    		var html = '';
    		$("#adResultDiv").html(html);		
    		var url = "${_ContextPath}/order/queryAdList"
    		var data="currentPage="+currentPage;
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
    						html += ("<td><a href=''><img width='80' src='${_ContextPath}/product/showImage?imageAddress="+recordsInfo.ad_picture+"' /> </a></td>");
    						html += ("<td><label class='label label-primary'>"+recordsInfo.cname+"</label></td>");
    						html += ("<td>"+recordsInfo.ad_href+"</td>");
    						html += ("<input type='hidden' id= 'status' value='"+recordsInfo.ad_status+"''/>");
    						html += ("<input type='hidden' id= 'AdId' value='"+recordsInfo.Ad_id+"''/>");
    						if(recordsInfo.ad_status == 'Y') {
    							html += ("<td><span class='text-navy'>已上架</span></td>");
	    						html += ("<td><button type='button' class='btn btn-primary btn-xs'><i class='fa fa-pencil'></i>&nbsp;<a style='color: #FFFFFF' href='${_ContextPath}/ad/redirectAdInfo?adId="+recordsInfo.Ad_id+"'>修改</a></button>&nbsp;&nbsp;");
    							html += ("<button type='button' class='btn btn-danger btn-xs' onclick=processAdY(this)><i class='fa fa-hand-o-down'></i>&nbsp;下架</button></td>");
    						} else {
	    						html += ("<td><span class='text-primary'>未上架</span></td>");
    							html += ("<td><button type='button' class='btn btn-primary btn-xs'><i class='fa fa-pencil'></i>&nbsp;<a style='color: #FFFFFF' href='${_ContextPath}/ad/redirectAdInfo?adId="+recordsInfo.Ad_id+"'>修改</a></button>&nbsp;&nbsp;");
	    						 html += ("<button type='button' class='btn btn-warning btn-xs' onclick=processAdY(this)><i class='fa fa-hand-o-up'></i>&nbsp;上架</button></td>"); 
    						}
    					};
    					$("#adResultDiv").html(html);
    				}
    				
    				
    				$("#adTotalPage").val(res.totalPage);
    				var totalPage = parseInt(res.totalPage);
    				var paginHtml = '';
    				
    				paginHtml += "<ul class='pagination'>";
    				paginHtml += "<li class='active'><a href='javascript:adSearch("+1+")'>首页</a></li>";
    				if(currentPage != 1){
    					
    					paginHtml += "<li class='active'><a href='javascript:adSearch("+(currentPage-1)+")'>上一页</a></li>";
    				}
    				
    				paginHtml += "<li class='active'><a>"+currentPage+"</a></li>";
    				
    				if(currentPage != totalPage){
    					paginHtml += "<li class='active'><a href='javascript:adSearch("+(currentPage+1)+")'>下一页</a></li>";
    				}
    				paginHtml += "<li class='active'><a href='javascript:adSearch("+totalPage+")'>尾页</a></li>";
    				paginHtml += "</ul>";
    				$("#adPaginBarDiv").html(paginHtml);
    			},				
    			type:"POST"
    		});
    	 }
    	
 /*    	 function processAd(obj) {
    		var AdId = $(obj).parent().parent().parent().children("#AdId").val();
    		var url = "${_ContextPath}/order/processAd"
       		var data="AdId="+AdId;
       		$.ajax({contentType:"application/x-www-form-urlencoded; charset=UTF-8",
       			url:url,
       			data:data,
       			success:function(result){
       				$(obj).parent().parent().parent().children("#status").val('Y');
       				$($(obj).parent().parent().children("td").get(4)).html("<i class='fa fa-check text-danger'></i>&nbsp;已上架");
       				$($(obj).parent().parent().children("td").get(5)).html("<td><button type='button' class='btn btn-danger btn-xs' onclick=processAdN(this)><i class='fa fa-hand-o-down'></i>&nbsp;下架</button></td>");
       			},				
       			type:"POST"
       		});
        }
    	
    	
       */
        
        function processAdY(obj) {
        	var AdId = $(obj).parent().parent().parent().children().children("#AdId").val();
        	var ad_status = $(obj).parent().parent().children("#status").val();
        	var url = "${_ContextPath}/order/processAdY"
       		var data="AdId="+AdId+"&adstatus="+ad_status;
       		$.ajax({contentType:"application/x-www-form-urlencoded; charset=UTF-8",
       			url:url,
       			data:data,
       			success:function(result){
       				//var status = $(obj).parent().parent().children("#status").val();
       				if (ad_status == "Y") {
       				$(obj).parent().parent().children("#status").val('N');
       				$($(obj).parent().parent().children("td").get(4)).html("<span class='badge badge-primary'>未上架</span>");
       				$($(obj).parent().parent().children("td").get(6)).html("<button type='button' class='btn btn-warning btn-xs' onclick=processAdY(this)><i class='fa fa-hand-o-up'></i>&nbsp;上架</button>");
       				} else {
       				$(obj).parent().parent().children("#status").val('Y');
           			$($(obj).parent().parent().children("td").get(4)).html("<i class='fa fa-check text-danger'></i>&nbsp;已上架");
           			$($(obj).parent().parent().children("td").get(6)).html("<button type='button' class='btn btn-danger btn-xs' onclick=processAdY(this)><i class='fa fa-hand-o-down'></i>&nbsp;下架</button>");
       				}
       			},				
       			type:"POST"
       		});
        }
    	
    </script>

</body>

</html>