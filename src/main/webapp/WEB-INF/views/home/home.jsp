<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">

    <title>H+ 后台主题UI框架 - 首页示例二</title>
    <meta name="keywords" content="H+后台主题,后台bootstrap框架,会员中心主题,后台HTML,响应式后台">
    <meta name="description" content="H+是一个完全响应式，基于Bootstrap3最新版本开发的扁平化主题，她采用了主流的左右两栏式布局，使用了Html5+CSS3等现代技术">

    <link href="${_ContextPath}/CacheableResource/css/bootstrap.min.css?v=3.4.0" rel="stylesheet">
    <link href="${_ContextPath}/CacheableResource/css/font-awesome.min.css?v=4.3.0" rel="stylesheet">

    <!-- Morris -->
    <link href="${_ContextPath}/CacheableResource/css/plugins/morris/morris-0.4.3.min.css" rel="stylesheet">

    <!-- Gritter -->
    <link href="${_ContextPath}/CacheableResource/js/plugins/gritter/jquery.gritter.css" rel="stylesheet">

    <link href="${_ContextPath}/CacheableResource/css/animate.min.css" rel="stylesheet">
    <link href="${_ContextPath}/CacheableResource/css/style.min.css?v=3.0.0" rel="stylesheet">
</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content">
        <div class="row">
            <div class="col-md-6">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <span class="label label-success pull-right">总</span>
                        <h5>销售额</h5>
                    </div>
                    <div class="ibox-content">
                        <h1 class="no-margins" id="salesAmount"></h1>
                        <small>总收入</small>
                    </div>
                </div>
            </div>
            <div class="col-md-6">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <span class="label label-info pull-right">总</span>
                        <h5>订单量</h5>
                    </div>
                    <div class="ibox-content">
                        <h1 class="no-margins" id="salesCount"></h1>
                        <small>总订单</small>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>销售统计</h5>
                    </div>
                    <div class="ibox-content">
                    <div id="main" style="width: 100%; height: 380px;"></div>
                    
                       <div class="row">
                            <div class="col-lg-12">
                                <div class="flot-chart">
                                    <div class="flot-chart-content" id="flot-dashboard-chart"></div>
                                </div>
                            </div>
                        </div>
                    </div> 

                </div>
            </div>
      <!--   </div>
        <div class="row"> -->
            <div class="col-md-6">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>订单统计</h5>
                    </div>
                    
                    <div class="ibox-content">
                    	<div id="main1" style="width: 100%; height: 380px;"></div>
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="flot-chart">
                                    <div class="flot-chart-content" id="flot-dashboard-chart"></div>
                                </div>
                            </div>
                        </div> 
                    </div>

                </div>
            </div>
        </div>
    </div>

    <!-- 全局js -->
    <script src="${_ContextPath}/CacheableResource/js/jquery-2.1.1.min.js"></script>
    <script src="${_ContextPath}/CacheableResource/js/bootstrap.min.js?v=3.4.0"></script>



    <!-- Flot -->
    <script src="${_ContextPath}/CacheableResource/js/plugins/flot/jquery.flot.js"></script>
    <script src="${_ContextPath}/CacheableResource/js/plugins/flot/jquery.flot.tooltip.min.js"></script>
    <script src="${_ContextPath}/CacheableResource/js/plugins/flot/jquery.flot.spline.js"></script>
    <script src="${_ContextPath}/CacheableResource/js/plugins/flot/jquery.flot.resize.js"></script>
    <script src="${_ContextPath}/CacheableResource/js/plugins/flot/jquery.flot.pie.js"></script>
    <script src="${_ContextPath}/CacheableResource/js/plugins/flot/jquery.flot.symbol.js"></script>

    <!-- Peity -->
    <script src="${_ContextPath}/CacheableResource/js/plugins/peity/jquery.peity.min.js"></script>
    <script src="${_ContextPath}/CacheableResource/js/demo/peity-demo.min.js"></script>

    <!-- 自定义js -->
    <script src="${_ContextPath}/CacheableResource/js/content.min.js?v=1.0.0"></script>


    <!-- jQuery UI -->
    <script src="${_ContextPath}/CacheableResource/js/plugins/jquery-ui/jquery-ui.min.js"></script>

    <!-- Jvectormap -->
    <script src="${_ContextPath}/CacheableResource/js/plugins/jvectormap/jquery-jvectormap-1.2.2.min.js"></script>
    <script src="${_ContextPath}/CacheableResource/js/plugins/jvectormap/jquery-jvectormap-world-mill-en.js"></script>

    <!-- EayPIE -->
    <script src="${_ContextPath}/CacheableResource/js/plugins/easypiechart/jquery.easypiechart.js"></script>

    <!-- Sparkline -->
    <script src="${_ContextPath}/CacheableResource/js/plugins/sparkline/jquery.sparkline.min.js"></script>

    <!-- Sparkline demo data  -->
    <script src="${_ContextPath}/CacheableResource/js/demo/sparkline-demo.min.js"></script>

    <script>
        $(document).ready(function () {
        	var a = eval('('+sessionStorage.getItem('orderCount')+')');
        	$("#salesAmount").html(a.sumpay);
        	$("#salesCount").html(a.sumorder);
        });
    </script>
<!--     获取12个月的订单数据 -->
	<script src="${_ContextPath}/CacheableResource/js/plugins/echarts/echarts1.js"></script>
	<script src="${_ContextPath}/CacheableResource/jquery200/jquery-1.9.1.js"></script>
	<script src="${_ContextPath}/CacheableResource/util/jquery.tree.js"></script>
	<script src="${_ContextPath}/CacheableResource/rpc/jquery-sso-patch.js"></script>
	<script src="${_ContextPath}/CacheableResource/rpc/JsonStub.js"></script>
    <script src="${_ContextPath}/PUBLIC/JsonBeanStub/BmQueryJsBean.js"></script>
		<script type="text/javascript">
        // 基于准备好的dom，初始化echarts实例
        var myChart1 = echarts.init(document.getElementById('main'));

        // 指定图表的配置项和数据 
        var option = {
            title: {
                text: '近一年的销售额统计',
                x: 'center',
                textStyle: {
                 	fontSize: 14,
                 	fontWeight: 'bolder',
                 	color:'#0000'
                },
               
            },
            tooltip : {
               trigger: 'axis',
                axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                    type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                } 
            },
           legend: {
                data: ['销售额(元)'],
              x: '80%',
              y: '7%'
            },
            grid: {
                left: '5%',
                right: '2%',
                bottom: '10%',
                containLabel: false
            },
            xAxis: {
                data: [],
                axisLabel: {
                	interval : 0 ,//横轴信息全部显示
                	rotate: 0 ,//60度角倾斜显示
                	normal: {
                        show: true,
                        position: 'top'
                    },
                    textStyle: {
                    	color: '#28c6de',
                    	fontSize:3
                	},
                
                }
                },
            yAxis: 
            	  {
                     type : 'value',
                      min : 0,
                  },
            series: [
                {
                name: '销售额(元)',
                type: 'bar',
                data: [],
                   label: {
                       normal: {
                              show: true,
                              position: 'top',
                              textStyle: {
                           	   fontSize : 12,
                           	   color : 'black'
                              }
                          }
                      }, 
                itemStyle:{
                       normal:{color:'#FF9797'},
                       
                   },
                }                
           ]
        };
        </script>
        
    <script type="text/javascript" LANGUAGE=JavaScript>
		$(document).ready(function(){
			 myChart1.showLoading();
			setInterval(function (){
		        RpcContext.getBean('BmQueryJsBean').BmQuery(
						{
							onSucess : function(result) {
								var res1 = result.salebymonth;
					    		var titles1 = new Array();
								var prems1 = new Array();
					    		
					        	if(res1 != null && res1.length > 0)
					        	{
					        		for(var i = 0; i < res1.length; i++)
					        		{
					        			var o = res1[i];
					        			titles1.push(o.month);
					        			prems1.push(o.sumsale);
					        		}
					        	}
								option.xAxis.data = titles1;
								option.series[0].data = prems1;
								 myChart1.hideLoading();
 								myChart1.setOption(option);
							}
			        	});
			}, 1000);
		});
	
</script>

<!-- 获取近一年的订单统计-->
	<script type="text/javascript">
        // 基于准备好的dom，初始化echarts实例
        var myChart2 = echarts.init(document.getElementById('main1'));

        // 指定图表的配置项和数据 
        var option1 = {
            title: {
                text: '近一年的订单量统计',
                x: 'center',
                textStyle: {
                 	fontSize: 14,
                 	fontWeight: 'bolder',
                 	color:'#0000'
                },
               
            },
            tooltip : {
               trigger: 'axis',
                axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                    type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                } 
            },
           legend: {
                data: ['订单(个)'],
              x: '85%',
              y: '7%'
            },
            grid: {
                left: '5%',
                right: '2%',
                bottom: '10%',
                containLabel: false
            },
            xAxis: {
                data: [],
                axisLabel: {
                	interval : 0 ,//横轴信息全部显示
                	rotate: 0 ,//60度角倾斜显示
                	normal: {
                        show: true,
                        position: 'top'
                    },
                    textStyle: {
                    	color: '#28c6de',
                    	fontSize:3
                	},
                
                }
                },
            yAxis: 
            	  {
                     type : 'value',
                      min : 0,
                  },
            series: [
                {
                name: '订单(个)',
                type: 'bar',
                data: [],
                   label: {
                       normal: {
                              show: true,
                              position: 'top',
                              textStyle: {
                           	   fontSize : 12,
                           	   color : 'black'
                              }
                          }
                      }, 
                itemStyle:{
                       normal:{color:'#9393FF'},
                       
                   },
                }                
           ]
        };
        </script>
        
    <script type="text/javascript" LANGUAGE=JavaScript>
		$(document).ready(function(){
			 myChart2.showLoading();
			setInterval(function (){
		        RpcContext.getBean('BmQueryJsBean').BmQuery(
						{
							onSucess : function(result) {
								var res2 = result.orderbymonth;
					    		var title2 = new Array();
								var prems2 = new Array();
					    		
					        	if(res2 != null && res2.length > 0)
					        	{
					        		for(var i = 0; i < res2.length; i++)
					        		{
					        			var o = res2[i];
					        			title2.push(o.month);
					        			prems2.push(o.order_num);
					        		}
					        	}
								option1.xAxis.data = title2;
								option1.series[0].data = prems2;
								 myChart2.hideLoading();
 								myChart2.setOption(option1);
							}
			        	});
			}, 1000);
		});
	
</script>

 <script>
        $(document).ready(function () {
        	var a = eval('('+sessionStorage.getItem('orderCount')+')');
        	$("#salesAmount").html(a.sumpay);
        	$("#salesCount").html(a.sumorder);
        });
    </script>

</body>
<style type="text/css">
.ui-loader-default {
	display: none
}

.ui-mobile-viewport {
	border: none;
}

.ui-page {
	padding: 0;
	margin: 0;
	outline: 0
}
</style>
</html>