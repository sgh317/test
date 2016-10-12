<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">

    <title>系统设置</title>
    <meta name="keywords" content="">
    <meta name="description" content="">

    <link href="${_ContextPath}/CacheableResource/css/bootstrap.min.css?v=3.4.0" rel="stylesheet">
    <link href="${_ContextPath}/CacheableResource/css/font-awesome.min.css?v=4.3.0" rel="stylesheet">
    <link href="${_ContextPath}/CacheableResource/css/plugins/iCheck/custom.css" rel="stylesheet">
    <link href="${_ContextPath}/CacheableResource/css/animate.min.css" rel="stylesheet">
    <link href="${_ContextPath}/CacheableResource/css/plugins/summernote/summernote.css" rel="stylesheet">
    <link href="${_ContextPath}/CacheableResource/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
    <link href="${_ContextPath}/CacheableResource/css/plugins/webuploader/webuploader.css" rel="stylesheet">
    <link href="${_ContextPath}/CacheableResource/css/plugins/switchery/switchery.css" rel="stylesheet">
    <link href="${_ContextPath}/CacheableResource/css/style.min.css?v=3.0.0" rel="stylesheet">
    <link href="${_ContextPath}/CacheableResource/css/style.wgqc.css?v=1.0.0" rel="stylesheet">
</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">
        <div class="row">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>密码修改</h5>
                        <div class="ibox-tools">
                            <a class="collapse-link">
                                <i class="fa fa-chevron-up"></i>
                            </a>
                        </div>
                    </div>
                    <div class="ibox-content">
                        <form class="form-horizontal m-t" id="signupForm">
                            <div class="form-group">
                                <label class="col-sm-3 control-label">新密码：</label>
                                <div class="col-sm-5">
                                    <input class="form-control" id="passwordN" type="password" validate="1|notnull^新密码填写">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">确认密码：</label>
                                <div class="col-sm-5">
                                    <input class="form-control" id="passwordNT" type="password" validate="1|notnull^确认密码填写">
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-8 col-sm-offset-3">
                                    <input type="button" class="btn btn-primary btn-md" onclick="saveUser()" value="提交"/>
                                </div>
                            </div>
                        </form>
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

    <!-- switchery -->
    <script src="${_ContextPath}/CacheableResource/js/plugins/switchery/switchery.js"></script>
    <script type="text/javascript" charset="utf-8" src="${_ContextPath}/PUBLIC/common/js/cfg.js"></script>
	<script type="text/javascript" charset="utf-8" src="${_ContextPath}/PUBLIC/common/js/jquery-sso-patch_mobile.js"></script>
	<script type="text/javascript" charset="utf-8" src="${_ContextPath}/PUBLIC/common/js/JsonStubMobile.js"></script>
	<script type="text/javascript" charset="utf-8" src="${_ContextPath}/PUBLIC/common/js/CommonUtil.js"></script>
	<script src="${_ContextPath}/CacheableResource/jquery200/jquery-1.9.1.js"></script>
	<script src="${_ContextPath}/CacheableResource/rpc/jquery-sso-patch.js"></script>
	<script src="${_ContextPath}/CacheableResource/rpc/JsonStub.js"></script>
	<script src="${_ContextPath}/PUBLIC/JsonBeanStub/UserJsBean.js"></script>
    <script>
    	var userInfo = sessionStorage.getItem("_user_info");
    	var user = eval("("+userInfo+")");
        $(document).ready(function () {
                var i = document.querySelector(".js-switch"), t = (new Switchery(i, {color: "#1AB394"}), document.querySelector(".js-switch_2")), a = (new Switchery(t, {color: "#ED5565"}), document.querySelector(".js-switch_3"));
                new Switchery(a, {color: "#1AB394"})
        });
        $(document).ready(function () {
            $(document).on("click","#modalSubmit,#cancelSubmit",function(){
                $("#myModal input").val(""); //提交后清空表单
            });
        });
        function saveUser()
        {
        	if(!checkForm())
        	{
        		return false;
        	}

        	var userId = user._user_account;
        	var password = $("#passwordN").val();
        	var userName = user._user_name;
        	RpcContext.getBean('UserJsBean').modifyUser(user._user_id, userId, password, userName, {
            	onSucess:function(rt)
            	{
            		var res = eval('('+rt+')'); 
            		
            		if(res.state)
        			{
        				alert("修改成功！");
        				window.location.href="${_ContextPath}/PUBLIC/logout";
        			}
        			else
        			{
            			alert(res.errorMessage);
        			}
        		},onError:function(err)
        		{
        			alert(err);
        		}
        	});
        }
        function checkForm()
        {
        	/* var password = $("#password").val(); */
        	var passwordN = $("#passwordN").val();
        	var passwordNT = $("#passwordNT").val();
        	Validate.validate(1);
    		if(Validate.getMsgByIndex(1)!=null&&Validate.getMsgByIndex(1)!=''){
    			alert('有录入项不符合校验规则');
    			return false;
    		}
        	if(passwordN != passwordNT)
        	{
        		alert("两次新密码输入不一致");
        		return false;
        	}
        	return true;
        }
    </script>

</body>

</html>