<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>万果千仓后台管理</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
	<meta http-equiv="Content-Type" content="textml; charset=utf-8" />
    <meta name="renderer" content="webkit"/>

    <meta name="keywords" content="万果千仓后台管理"/>
    <meta name="description" content="万果千仓后台管理"/>
	<%@ include file="/WEB-INF/views/comm/taglibs.jsp"%>
	<%@ include file="/WEB-INF/views/comm/meta.jsp"%>

    <!--[if lt IE 8]>
    <script>
        alert('本系统已不支持IE6-8，请使用谷歌、火狐等浏览器\n或360、QQ等国产浏览器的极速模式浏览本页面！');
    </script>
    <![endif]-->

	<link href="${_ContextPath}/CacheableResource/login/login.css" rel="stylesheet" media="screen">
    <link href="${_ContextPath}/CacheableResource/css/bootstrap.min.css?v=3.4.0" rel="stylesheet">
    <link href="${_ContextPath}/CacheableResource/css/font-awesome.min.css?v=4.3.0" rel="stylesheet">
    <link href="${_ContextPath}/CacheableResource/css/animate.min.css" rel="stylesheet">
    <link href="${_ContextPath}/CacheableResource/css/style.min.css?v=3.0.0" rel="stylesheet">
    <link href="${_ContextPath}/CacheableResource/css/style.wgqc.css?v=1.0.0" rel="stylesheet">
</head>
<body>
    <style type="text/css">
        body {
            background:url(${_ContextPath}/CacheableResource/images/login_bg.png) no-repeat center center;
        }
    </style>
    <div class="login-box">
        <div class="logo"><image src="${_ContextPath}/CacheableResource/images/logo.png"></image></div>
        <div class="login-form">
            <form id="loginForm" name="loginForm" action="${_ContextPath}/PUBLIC/login" method="post">
                <div class="form-title">用户名：</div>
                <div class="form-input">
                    <input id="userName" name="userName" type="text" />
                </div>
                <div class="form-title">密码：</div>
                <div class="form-input">
                    <input id="password" name="password" type="password"/>
                </div>
                <button type="submit" class="btn-login">登录</button>
                <div id="notice" class="form_warning" ${style}>${msg}</div>
            </form>
        </div>
    </div>
    <!-- 全局js -->
    <script src="${_ContextPath}/CacheableResource/js/jquery-2.1.1.min.js"></script>
    <script src="${_ContextPath}/CacheableResource/js/bootstrap.min.js?v=3.4.0"></script>
    <script src="${_ContextPath}/CacheableResource/js/plugins/metisMenu/jquery.metisMenu.js"></script>
    <script src="${_ContextPath}/CacheableResource/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
    <script src="${_ContextPath}/CacheableResource/js/plugins/layer/layer.min.js"></script>

    <!-- 自定义js -->
    <script src="${_ContextPath}/CacheableResource/js/hplus.min.js?v=3.0.0"></script>
</body>
</html>