 <!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">

    <title>商品列表</title>
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
                        <h5>商品列表</h5>
                        <div class="ibox-tools">
                            <a class="collapse-link">
                                <i class="fa fa-chevron-up"></i>
                            </a>
                        </div>
                    </div>
                    <div class="ibox-content">
                        <div class="row">
                            <div class="col-sm-1 m-b-xs">
                                <select id="productStatus" class="input-sm form-control input-s-sm inline">
                                    <option value="">全部</option>
                                    <option value="N">未上架</option>
                                    <option value="Y">已上架</option>
                                </select>
                            </div>
                            <div class="col-sm-3">
                                <div class="input-group">
                                    <input type="text" id="produectName" placeholder="请输入商品名称" class="input-sm form-control"> <span class="input-group-btn">
                                        <button type="button" class="btn btn-sm btn-primary" onclick="search(1)"> 搜索</button> </span>
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
                                        <th>商品图片</th>
                                        <th>商品名称</th>
                                        <th>简介</th>
                                        <th>规格</th>
                                        <th>是否特价</th>
                                        <th>状态</th>
                                        <th>操作</th>
                                    </tr>
                                </thead>
                                <tbody id="orderDetailResultDiv">
                                </tbody>
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
    		var produectName = $("#produectName").val();
    		$("#currentPage").val(currentPage);
    		var productStatus = $("#productStatus").val();
    		var html = '';
    		$("#orderDetailResultDiv").html(html);		
    		var url = "${_ContextPath}/product/PorductInfoList"
    		var data="currentPage="+currentPage+"&produectName="+produectName+"&productStatus="+productStatus;
    		$.ajax({contentType:"application/x-www-form-urlencoded; charset=UTF-8",
    			url:url,
    			data:data,
    			success:function(result){
    				var res = eval('('+result+')'); 
    				
    				if(res.productInfoMap !=null){
    					var i = 1;
    					$.each(res.productInfoMap,function(ky,vl) { 
    						html += "<tr>"; 
    						html += ("<td>"+ i +"</td>");
    						html += ("<td><a href=''><img width='80' src='${_ContextPath}/product/showImage?imageAddress="+vl[0].colorpic+"' /> </a></td>");
    						html += ("<td>"+ vl[0].proname +"</td>");
    						html += ("<td>"+ vl[0].prodescri +"</td>");
    						html += "<td>"; 
    						$.each(vl,function(cky,cvl) {
    							html += "<div class='order-product'>";
    							if('Y' == vl[0].isdiscount) {
	    							html += "<span>规格："+cvl.skuname+"</span><span>价格："+cvl.sellprice+"</span><span>特价："+cvl.discountprice+"</span><span>库存："+cvl.inventory+"</span>"; 
    							} else {
    								html += "<span>规格："+cvl.skuname+"</span><span>价格："+cvl.sellprice+"</span><span>库存："+cvl.inventory+"</span>";
    							}
    							html += "</div>"; 
    						});
    						if('Y' == vl[0].isdiscount) {
	    						html += ("<td><label class='label label-danger'>是</label></td>");
    						} else {
    							html += ("<td><label class='label label-default'>否</label></td>");
    						}
    						if('Y' == vl[0].isshow) {
    							html += ("<td><span class='text-navy'>已上架</span></td>");
    						} else {
	    						html += ("<td><span class='text-primary'>未上架</span></td>");
    							
    						}
    						html += ("<input type='hidden' id='productId' value='"+vl[0].proid+"'/>");
    						html += ("<input type='hidden' id='productStatus' value='"+vl[0].isshow+"'/>");
                            
	    					html += ("<td><button type='button' class='btn btn-primary btn-xs'><i class='fa fa-pencil'></i>&nbsp;<a style='color: #FFFFFF' href='${_ContextPath}/product/redirectProductInfo?productId="+vl[0].proid+"'>修改</a></button>&nbsp;&nbsp;");
    						if('Y' == vl[0].isshow) {
	    						html += ("<button type='button' class='btn btn-danger btn-xs' onclick=updateProductStatus(this)><i class='fa fa-hand-o-down'></i>&nbsp;下架</button></td>");
    						} else {
	    						html += ("<button type='button' class='btn btn-warning btn-xs' onclick=updateProductStatus(this)><i class='fa fa-hand-o-up'></i>&nbsp;上架</button></td>");
    						}
    						html += ("</tr>");
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
        
        function updateProductStatus(obj) {
        	var productId = $(obj).parent().parent().parent().children().children("#productId").val();
        	var productStatus = $(obj).parent().parent().children("#productStatus").val();
        	var url = "${_ContextPath}/product/updateProductStatus"
       		var data="productId="+productId+"&productStatus="+productStatus;
       		$.ajax({contentType:"application/x-www-form-urlencoded; charset=UTF-8",
       			url:url,
       			data:data,
       			success:function(result){
       				if (productStatus == "Y") {
	       				$(obj).parent().parent().children("#productStatus").val('N');
	       				$($(obj).parent().parent().children("td").get(6)).html("<span class='text-primary'>未上架</span>");
	       				$($(obj).parent().parent().children("td").get(8)).html("<button type='button' class='btn btn-warning btn-xs' onclick=updateProductStatus(this)><i class='fa fa-hand-o-up'></i>&nbsp;上架</button>");
       				} else {
	       				$(obj).parent().parent().children("#productStatus").val('Y');
	           			$($(obj).parent().parent().children("td").get(6)).html("<span class='text-primary'>已上架</span>");
	           			$($(obj).parent().parent().children("td").get(8)).html("<button type='button' class='btn btn-danger btn-xs' onclick=updateProductStatus(this)><i class='fa fa-hand-o-down'></i>&nbsp;下架</button>");
       				}
       			},				
       			type:"POST"
       		});
        }
    </script>

</body>

</html>