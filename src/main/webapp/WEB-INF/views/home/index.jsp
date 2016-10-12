<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">

    <title>万果千仓后台管理</title>

    <meta name="keywords" content="万果千仓后台管理">
    <meta name="description" content="万果千仓后台管理">

    <!--[if lt IE 8]>
    <script>
        alert('本系统已不支持IE6-8，请使用谷歌、火狐等浏览器\n或360、QQ等国产浏览器的极速模式浏览本页面！');
    </script>
    <![endif]-->

    <link href="${_ContextPath}/CacheableResource/css/bootstrap.min.css?v=3.4.0" rel="stylesheet">
    <link href="${_ContextPath}/CacheableResource/css/font-awesome.min.css?v=4.3.0" rel="stylesheet">
    <link href="${_ContextPath}/CacheableResource/css/animate.min.css" rel="stylesheet">
    <link href="${_ContextPath}/CacheableResource/css/style.min.css?v=3.0.0" rel="stylesheet">
    <link href="${_ContextPath}/CacheableResource/css/style.wgqc.css?v=1.0.0" rel="stylesheet">
    
    <script type="text/javascript">
		sessionStorage.setItem('_user_info', '${_user_info_json }');
		sessionStorage.setItem('orderCount', '${order_count}');
	</script>
</head>

<body class="fixed-sidebar full-height-layout gray-bg">
    <div id="wrapper">
        <!--左侧导航开始-->
        <nav class="navbar-default navbar-static-side" role="navigation">
            <div class="nav-close"><i class="fa fa-times-circle"></i>
            </div>
            <div class="sidebar-collapse">
                <ul class="nav" id="side-menu">
                    <li class="nav-header">
                        <div class="logo">
                            <img class="img-circle" width="100" src="${_ContextPath}/CacheableResource/img/logo.png" alt="1f5" />
                        </div>
                        <div class="logo-element">万果<br/>千仓
                        </div>
                    </li>
                    <li>
                        <a class="J_menuItem" href="${_ContextPath}/home">
                            <i class="fa fa-home"></i>
                            <span class="nav-label">主页</span>
                        </a>
                    </li>
                    <li>
                        <a href="#"><i class="fa fa-pie-chart"></i> <span class="nav-label">财务管理</span><span class="fa arrow"></span></a>
                        <ul class="nav nav-second-level">
                            <li>
                                <a class="J_menuItem" href="${_ContextPath}/recharge_list">财务总览</a>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <a href="#"><i class="fa fa-columns"></i> <span class="nav-label">订单管理</span><span class="fa arrow"></span></a>
                        <ul class="nav nav-second-level">
                            <li>
                                <a class="J_menuItem" href="${_ContextPath}/order_list">订单列表</a>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <a href="#">
                            <i class="fa fa fa-bar-chart-o"></i>
                            <span class="nav-label">商品管理</span>
                            <span class="fa arrow"></span>
                        </a>
                        <ul class="nav nav-second-level">
                            <li>
                                <a class="J_menuItem" href="${_ContextPath}/product_list">商品列表</a>
                            </li>
                            <li>
                                <a class="J_menuItem" href="${_ContextPath}/product_add">新增商品</a>
                            </li>
                            <li>
                                <a class="J_menuItem" href="${_ContextPath}/classification_list">商品分类</a>
                            </li>
                            <li>
                                <a class="J_menuItem" href="${_ContextPath}/classification_add">新增分类</a>
                            </li>
                            <li>
                                <a class="J_menuItem" href="${_ContextPath}/card_list">充值卡</a>
                            </li>
                            <li>
                                <a class="J_menuItem" href="${_ContextPath}/card_add">新增充值卡</a>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <a href="#">
                            <i class="fa fa-envelope"></i>
                            <span class="nav-label">评价管理</span>
                            <span class="fa arrow"></span>
                        </a>
                        <ul class="nav nav-second-level">
                            <li>
                                <a class="J_menuItem" href="${_ContextPath}/evaluate_list">评价列表</a>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <a href="#"><i class="fa fa-volume-up"></i> <span class="nav-label">广告管理</span><span class="fa arrow"></span></a>
                        <ul class="nav nav-second-level">
                            <li><a class="J_menuItem" href="${_ContextPath}/advertisement_list">广告列表</a></li>
                            <li><a class="J_menuItem" href="${_ContextPath}/advertisement_add">新增广告</a></li>
                        </ul>
                    </li>
                    <li>
                        <a href="#"><i class="fa fa-gear"></i> <span class="nav-label">系统设置</span><span class="fa arrow"></span></a>
                        <ul class="nav nav-second-level">
                            <li><a class="J_menuItem" href="${_ContextPath}/system_set">系统设置</a></li>
                            <li><a class="J_menuItem" href="${_ContextPath}/password_set">密码修改</a></li>
                        </ul>
                    </li>
                </ul>
            </div>
        </nav>
        <!--左侧导航结束-->
        <!--右侧部分开始-->
        <div id="page-wrapper" class="gray-bg dashbard-1">
              <div class="row border-bottom">
                <nav class="navbar navbar-static-top" role="navigation" style="margin-bottom: 0">
                    <div class="navbar-header"><a class="navbar-minimalize minimalize-styl-2 btn btn-primary " href="#"><i class="fa fa-bars"></i> </a>
                    </div>
                    <ul class="nav navbar-top-links navbar-right">
                        
                        <li class="dropdown">
                            
                            <ul class="dropdown-menu dropdown-alerts">
                            </ul>
                        </li>
                    </ul>
                </nav>
            </div>
            <div class="row content-tabs">
                <button class="roll-nav roll-left J_tabLeft"><i class="fa fa-backward"></i>
                </button>
                <nav class="page-tabs J_menuTabs">
                    <div class="page-tabs-content">
                        <a href="javascript:;" class="active J_menuTab" data-id="index_v1.html">主页</a>
                    </div>
                </nav>
                <button class="roll-nav roll-right J_tabRight"><i class="fa fa-forward"></i>
                </button>
                <button class="roll-nav roll-right dropdown J_tabClose"><span class="dropdown-toggle" data-toggle="dropdown">关闭操作<span class="caret"></span></span>
                    <ul role="menu" class="dropdown-menu dropdown-menu-right">
                        <li class="J_tabShowActive"><a>定位当前选项卡</a>
                        </li>
                        <li class="divider"></li>
                        <li class="J_tabCloseAll"><a>关闭全部选项卡</a>
                        </li>
                        <li class="J_tabCloseOther"><a>关闭其他选项卡</a>
                        </li>
                    </ul>
                </button>
                <a href="${_ContextPath}/PUBLIC/logout" class="roll-nav roll-right J_tabExit"><i class="fa fa fa-sign-out"></i> 退出</a>
            </div>
            <div class="row J_mainContent" id="content-main">
                <iframe class="J_iframe" name="iframe0" width="100%" height="100%" src="${_ContextPath}/home" frameborder="0" data-id="index_v1.html" seamless></iframe>
            </div>
            <div class="footer">
                <div class="pull-right">&copy; 2016 <a href="/" target="_blank">万果千仓</a>
                </div>
            </div>
        </div>
        <!--右侧部分结束-->
    </div>

    <!-- 全局js -->
    <script src="${_ContextPath}/CacheableResource/js/jquery-2.1.1.min.js"></script>
    <script src="${_ContextPath}/CacheableResource/js/bootstrap.min.js?v=3.4.0"></script>
    <script src="${_ContextPath}/CacheableResource/js/plugins/metisMenu/jquery.metisMenu.js"></script>
    <script src="${_ContextPath}/CacheableResource/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
    <script src="${_ContextPath}/CacheableResource/js/plugins/layer/layer.min.js"></script>

    <!-- 自定义js -->
    <script src="${_ContextPath}/CacheableResource/js/hplus.min.js?v=3.0.0"></script>
    <script type="text/javascript" src="${_ContextPath}/CacheableResource/js/contabs.min.js"></script>

    <!-- 第三方插件 -->
    <script src="${_ContextPath}/CacheableResource/js/plugins/pace/pace.min.js"></script>

	<script>
    $("#fixednavbar").click(function(){if($("#fixednavbar").is(":checked")){$(".navbar-static-top").removeClass("navbar-static-top").addClass("navbar-fixed-top");$("body").removeClass("boxed-layout");$("body").addClass("fixed-nav");$("#boxedlayout").prop("checked",false);if(localStorageSupport){localStorage.setItem("boxedlayout","off")}if(localStorageSupport){localStorage.setItem("fixednavbar","on")}}else{$(".navbar-fixed-top").removeClass("navbar-fixed-top").addClass("navbar-static-top");$("body").removeClass("fixed-nav");if(localStorageSupport){localStorage.setItem("fixednavbar","off")}}});$("#collapsemenu").click(function(){if($("#collapsemenu").is(":checked")){$("body").addClass("mini-navbar");SmoothlyMenu();if(localStorageSupport){localStorage.setItem("collapse_menu","on")}}else{$("body").removeClass("mini-navbar");SmoothlyMenu();if(localStorageSupport){localStorage.setItem("collapse_menu","off")}}});$("#boxedlayout").click(function(){if($("#boxedlayout").is(":checked")){$("body").addClass("boxed-layout");$("#fixednavbar").prop("checked",false);$(".navbar-fixed-top").removeClass("navbar-fixed-top").addClass("navbar-static-top");$("body").removeClass("fixed-nav");if(localStorageSupport){localStorage.setItem("fixednavbar","off")}if(localStorageSupport){localStorage.setItem("boxedlayout","on")}}else{$("body").removeClass("boxed-layout");if(localStorageSupport){localStorage.setItem("boxedlayout","off")}}});$(".spin-icon").click(function(){$(".theme-config-box").toggleClass("show")});$(".s-skin-0").click(function(){$("body").removeClass("skin-1");$("body").removeClass("skin-2");$("body").removeClass("skin-3")});$(".s-skin-1").click(function(){$("body").removeClass("skin-2");$("body").removeClass("skin-3");$("body").addClass("skin-1")});$(".s-skin-3").click(function(){$("body").removeClass("skin-1");$("body").removeClass("skin-2");$("body").addClass("skin-3")});if(localStorageSupport){var collapse=localStorage.getItem("collapse_menu");var fixednavbar=localStorage.getItem("fixednavbar");var boxedlayout=localStorage.getItem("boxedlayout");if(collapse=="on"){$("#collapsemenu").prop("checked","checked")}if(fixednavbar=="on"){$("#fixednavbar").prop("checked","checked")}if(boxedlayout=="on"){$("#boxedlayout").prop("checked","checked")}};
    </script>
   <%--  <script src="${_ContextPath}/CacheableResource/jquery200/jquery-1.9.1.js"></script> --%>
	<script src="${_ContextPath}/CacheableResource/util/jquery.tree.js"></script>
	<script src="${_ContextPath}/CacheableResource/rpc/jquery-sso-patch.js"></script>
	<script src="${_ContextPath}/CacheableResource/rpc/JsonStub.js"></script>
    <script src="${_ContextPath}/PUBLIC/JsonBeanStub/BmQueryJsBean.js"></script>
    
    <script type="text/javascript" LANGUAGE=JavaScript>
		
		$(document).ready(function(){
			setInterval(function (){
		        RpcContext.getBean('BmQueryJsBean').BmQuery(
						{
							onSucess : function(result) {
								var res = result.NewOrder;
									if (res > "0") {
											var obj=document.getElementById('music');  
											obj.play();
									} else  {
										var v = document.getElementById('music');
										v.pause();
									}
							}
			        	});
			}, 1000);
		});
	</script>
	<style>
    .fixed-nav .slimScrollDiv #side-menu {
        padding-bottom: 60px;
    }
    </style>
</body>
<audio id="music" src="${_ContextPath}/CacheableResource/images/notify.mp3" autostart="false"
loop="1" hidden="true" autostart="true" ></audio>
</html>