 <!DOCTYPE html>
 <%@ page language="java" pageEncoding="UTF-8"%>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">

    <title>新增充值卡</title>
    <meta name="keywords" content="">
    <meta name="description" content="">

    <link href="${_ContextPath}/CacheableResource/css/bootstrap.min.css?v=3.4.0" rel="stylesheet">
    <link href="${_ContextPath}/CacheableResource/css/font-awesome.min.css?v=4.3.0" rel="stylesheet">
    <link href="${_ContextPath}/CacheableResource/css/plugins/iCheck/custom.css" rel="stylesheet">
    <link href="${_ContextPath}/CacheableResource/css/animate.min.css" rel="stylesheet">
    <link href="${_ContextPath}/CacheableResource/css/plugins/summernote/summernote.css" rel="stylesheet">
    <link href="${_ContextPath}/CacheableResource/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
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
                        <h5>新增充值卡</h5>
                        <div class="ibox-tools">
                            <a class="collapse-link">
                                <i class="fa fa-chevron-up"></i>
                            </a>
                        </div>
                    </div>
                    <div class="ibox-content">
                        <form class="form-horizontal m-t" id="signupForm">
                            <div class="form-group">
                                <label class="col-sm-3 control-label">充值卡名称：</label>
                                <div class="col-sm-5">
                                    <input class="form-control" type="text" name="productName" validate="1|notnull^充值卡名称未填写">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">充值卡面额：</label>
                                <div class="col-sm-5">
                                    <input class="form-control" type="text" name="skuSellPrice" validate="1|notnull^充值卡面额未填写|isnum^请填写正确的金额">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">充值卡金额：</label>
                                <div class="col-sm-5">
                                    <input class="form-control" type="text" name="discountPrice" validate="1|notnull^充值卡金额未填写|isnum^请填写正确的金额">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">当前状态：</label>
                                <div class="col-sm-8">
                                    <div class="radio i-checks">
                                        <label><input type="radio" checked value="N" name="isShow"> <i></i>未上架</label>
                                    </div>
                                    <div class="radio i-checks">
                                        <label><input type="radio" value="Y" name="isShow"> <i></i>已上架</label>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-8 col-sm-offset-3">
                                    <button id="formSubmit" class="btn btn-primary btn-lg" type="submit">提交</button>
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

    <!-- switchery -->
    <script src="${_ContextPath}/CacheableResource/js/plugins/switchery/switchery.js"></script>
    
    <script type="text/javascript" charset="utf-8" src="${_ContextPath}/PUBLIC//common/js/cfg.js"></script>
	<script type="text/javascript" charset="utf-8" src="${_ContextPath}/PUBLIC//common/js/jquery-sso-patch_mobile.js"></script>
	<script type="text/javascript" charset="utf-8" src="${_ContextPath}/PUBLIC//common/js/JsonStubMobile.js"></script>
	<script type="text/javascript" charset="utf-8" src="${_ContextPath}/PUBLIC//common/js/CommonUtil.js"></script>
    

    <script>
        $(document).ready(function () {
                var i = document.querySelector(".js-switch"), t = (new Switchery(i, {color: "#1AB394"}), document.querySelector(".js-switch_2")), a = (new Switchery(t, {color: "#ED5565"}), document.querySelector(".js-switch_3"));
                new Switchery(a, {color: "#1AB394"})
        });
        $(document).ready(function () {
            $('.i-checks').iCheck({
                checkboxClass: 'icheckbox_square-green',
                radioClass: 'iradio_square-green',
            });
            $(".summernote").summernote({lang:"zh-CN",height:300});
            
            $("#formSubmit").on("click",function(e){
            	/** 规则校验 **/
            	Validate.validate(1);
        		if(Validate.getMsgByIndex(1)!=null&&Validate.getMsgByIndex(1)!=''){
        			alert('有录入项不符合校验规则');
        			return false;
        		}
        		var form = document.forms[0]; 
        		// 富文本编辑器
	            form.action = "${_ContextPath}/product/saveCardInfo";  
                form.method = "post";  
                form.submit();  
        		swal("提交成功!", "您的信息已经成功提交至后台!", "success");
                e.preventDefault();
            });
            $(document).on("click","#modalSubmit,#cancelSubmit",function(){
                $("#myModal input").val(""); //提交后清空表单
            });
        });
    </script>

</body>

</html>