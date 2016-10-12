 <!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">

    <title>订单列表</title>
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
                        <h5>订单列表</h5>
                        <div class="ibox-tools">
                            <a class="collapse-link">
                                <i class="fa fa-chevron-up"></i>
                            </a>
                        </div>
                    </div>
                    <div class="ibox-content">
                        <div class="row">
                            <div class="col-sm-1 m-b-xs">
                                <select id="orderStatus" class="input-sm form-control input-s-sm inline">
                                    <option value="">全部</option>
                                    <option value="001">正在备货</option>
                                    <option value="002">正在发货</option>
                                    <option value="003">订单完成</option>
                                </select>
                            </div>
                            <div class="col-sm-3 m-b-xs">
                                <div class="row">
                                    <div class="col-sm-5 m-b-xs">
                                        <input id="start" type="text" class="form-control layer-date" placeholder="开始日期">
                                    </div>
                                    <div class="col-sm-5 m-b-xs">
                                        <input id="end" type="text" class="form-control layer-date" placeholder="结束日期">
                                    </div>
                                    <div class="col-sm-2 m-b-xs">
                                        <button type="button" onclick="search(1)" class="btn btn-primary btn-sm">筛选</button>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-5 m-b-xs">&nbsp;</div>
                            <div class="col-sm-3">
                                <div class="input-group">
                                    <input type="text" id="orderNo" placeholder="请输入订单编号" class="input-sm form-control"> <span class="input-group-btn">
                                        <button type="button" class="btn btn-sm btn-primary" onclick="search(1)"> 搜索</button> </span>
                                </div>
                            </div>
                        </div>
                        <input  id="currentPage" type="hidden" value="1"/>
	        			<input id="totalPage" type="hidden"/>
                        <div class="table-responsive">
                            <table class="table table-striped">
                                <thead>
                                    <tr>
                                        <th><input id="selectAll" type="checkbox" /></th>
                                        <th>编号</th>
                                        <th>订单编号</th>
                                        <th>商品名称</th>
                                        <th>运费</th>
                                        <th>总金额</th>
                                        <th>订单生成时间</th>
                                        <th>收货地址</th>
                                        <th>配送时间</th>
                                        <th>状态</th>
                                        <th>操作</th>
                                    </tr>
                                </thead>
                                <tbody id="orderDetailResultDiv"></tbody>
                            </table>
                            <div class="pull-right">
                                <button type="button" class="btn btn-md btn-warning" id="batchPrint">批量导出</button>&nbsp;
                                <button type="button" class="btn btn-md btn-danger" id="batchProcess">批量处理</button>
                            </div>
                            <nav id="orderDetailPaginBarDiv"> </nav>
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
            $('.i-checks').iCheck({
                checkboxClass: 'icheckbox_square-green',
                radioClass: 'iradio_square-green',
            });
            var start = {
                elem: "#start",
                format: "YYYY/MM/DD",
                min: laydate.now(),
                max: "2099-06-16 23:59:59",
                istime: false,
                istoday: false,
                choose: function (datas) {
                    end.min = datas;
                    end.start = datas
                }
            };
            var end = {
                elem: "#end",
                format: "YYYY/MM/DD",
                min: laydate.now(),
                max: "2099-06-16 23:59:59",
                istime: false,
                istoday: false,
                choose: function (datas) {
                    start.max = datas
                }
            };
            laydate(start);
            laydate(end);
            search(1);
        });
        function search(currentPage){
    		var orderNo = $("#orderNo").val();
    		var startTime = $("#start").val();
    		var endTime = $("#end").val();
    		$("#currentPage").val(currentPage);
    		var orderStatus = $("#orderStatus").val();
    		var html = '';
    		$("#orderDetailResultDiv").html(html);		
    		var url = "${_ContextPath}/order/queryOrderDetailList"
    			var data="currentPage="+currentPage+"&orderNo="+orderNo+"&startTime="+startTime+"&endTime="+endTime+"&orderStatus="+orderStatus;
    		$.ajax({contentType:"application/x-www-form-urlencoded; charset=UTF-8",
    			url:url,
    			data:data,
    			success:function(result){
    				var res = eval('('+result+')'); 
    				
    				if(res.recordsMap !=null){
    					var i = 1;
    					$.each(res.recordsMap,function(ky,vl) { 
    						html += "<tr>"; 
    						html += ("<td><input class='orderList' type='checkbox'/></td>");
    						html += ("<td>"+ i +"</td>");
    						html += ("<td>"+ky+"</td>");
    						html += "<td>"; 
    						$.each(vl,function(cky,cvl) {
    							html += "<div class='order-product'>"; 
    							html += "<span>"+cvl.proname+"</span><span>规格："+cvl.skuname+"</span><span>数量："+cvl.numsku+"</span><span>金额："+cvl.subtotal+"</span>"; 
    							html += "</div>"; 
    						});
    						html += ("<td>"+vl[0].extraPay+"</td>");
    						html += ("<td>"+vl[0].payAmount+"</td>");
    						html += ("<td>"+vl[0].createTime+"</td>");
    						html += ("<td>"+vl[0].userAddress+"</td>");
    						html += ("<td>"+vl[0].deliveryTime+"</td>");
    						html += ("<input type='hidden' value='"+vl[0].orderStatus+"''/>");
    						if(vl[0].orderStatus == '003') {
    							html += ("<td><i class='fa fa-check text-danger'></i>&nbsp;"+vl[0].orderStatusName+"</td>");
    							html += ("<td></td>");
    						} else if(vl[0].orderStatus == '002') {
    							html += ("<td><span class='badge badge-primary'>"+vl[0].orderStatusName+"</span></td>");
    							html += ("<td><button type='button' class='btn btn-success btn-xs' onclick=processOrder('"+vl[0].orderNum+"',this)>处理</button></td>");
    						}else {
	    						html += ("<td><span class='badge badge-primary'>"+vl[0].orderStatusName+"</span></td>");
	    						html += ("<td><button type='button' id='processOrder' class='btn btn-success btn-xs' style='display:none;' onclick=processOrder('"+vl[0].orderNum+"',this)>处理</button></td>");
    						}
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
        
        function processOrder(orderNo,obj) {
        	var url = "${_ContextPath}/order/processOrder"
       		var data="orderNo="+orderNo;
       		$.ajax({contentType:"application/x-www-form-urlencoded; charset=UTF-8",
       			url:url,
       			data:data,
       			success:function(result){
       				$(obj).parent().parent().children("input").val("003");
       				$($(obj).parent().parent().children("td").get(9)).html("<i class='fa fa-check text-danger'></i>&nbsp;完成");
       				$(obj).parent().html("");
       			},				
       			type:"POST"
       		});
        }
        
        function processOrderForBatch(orderNo,obj) {
        	var url = "${_ContextPath}/order/processOrder"
       		var data="orderNo="+orderNo;
       		$.ajax({contentType:"application/x-www-form-urlencoded; charset=UTF-8",
       			url:url,
       			data:data,
       			success:function(result){
       				$(obj).parent().parent().children("input").val("003");
       				$($(obj).parent().parent().children("td").get(9)).html("<i class='fa fa-check text-danger'></i>&nbsp;完成");
       				$($(obj).parent().parent().children("td").get(10)).html("");
       			},				
       			type:"POST"
       		});
        }
        	
        	
      //全选
		$("#selectAll").click(function(){
 			if(this.checked){//全选
				$(".orderList").each(function(i){
					if(!this.checked){
						this.checked = true;
						$(this).attr("checked","true");
					}
				});
			}else{//全不选
				$(".orderList").each(function(i){
					if(this.checked){
						$(this).removeAttr("checked");
						this.checked = undefined;
					}
				});
			}
		});
		$("#batchProcess").click(function(){
			var checkFlag = false
			var checkbhFlag = false
			$(".orderList").each(function(i){
				if(this.checked){
					checkFlag = true
					if("002" == $(this).parent().parent().children("input").val()) {
						checkbhFlag = true
					}
				}
			});
			if(checkFlag) {
				if(checkbhFlag) {
					$(".orderList").each(function(i){
						if(this.checked){
							if("002" == $(this).parent().parent().children("input").val()) {
								var orderNo = $($(this).parent().parent().children("td").get(2)).html();
								processOrderForBatch(orderNo,this);
							}
						}
					});
				} else {
					alert("目前没有需要处理的订单")
				}
			} else {
				alert("请选择要处理的订单")
			}
		});
		$("#batchPrint").click(function(){
			var checkFlag = false
			$(".orderList").each(function(i){
				if(this.checked){
					checkFlag = true
				}
			});
			if(checkFlag) {
				var orderStr = "";
				$(".orderList").each(function(i){
					if(this.checked){
						if("001" == $(this).parent().parent().children("input").val()) {
							orderStr +=$($(this).parent().parent().children("td").get(2)).html()+","
							$(this).parent().parent().children("input").val("002");
							$($(this).parent().parent().children("td").get(9)).children().html("正在发货");
							$($(this).parent().parent().children("td").get(10)).children().show();
						}
					}
				});
				if(orderStr == null || orderStr =="") {
					alert("目前没有处于备货状态的订单")
				} else {
					orderStr = orderStr.substring(0,orderStr.length - 1);
					window.location.href = "${_ContextPath}/order/printOrderDetailList?orderNo="+orderStr
				}
			} else {
				alert("请选择要导出的订单")
			}
		});
    </script>

</body>

</html>