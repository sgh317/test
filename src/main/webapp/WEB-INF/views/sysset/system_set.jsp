
<!DOCTYPE html>
<html>

<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="renderer" content="webkit">

<title>系统设置</title>
<meta name="keywords" content="">
<meta name="description" content="">

<link
	href="${_ContextPath}/CacheableResource/css/bootstrap.min.css?v=3.4.0"
	rel="stylesheet">
<link
	href="${_ContextPath}/CacheableResource/css/font-awesome.min.css?v=4.3.0"
	rel="stylesheet">
<link
	href="${_ContextPath}/CacheableResource/css/plugins/iCheck/custom.css"
	rel="stylesheet">
<link href="${_ContextPath}/CacheableResource/css/animate.min.css"
	rel="stylesheet">
<link
	href="${_ContextPath}/CacheableResource/css/plugins/summernote/summernote.css"
	rel="stylesheet">
<link
	href="${_ContextPath}/CacheableResource/css/plugins/sweetalert/sweetalert.css"
	rel="stylesheet">
<link
	href="${_ContextPath}/CacheableResource/css/plugins/webuploader/webuploader.css"
	rel="stylesheet">
<link
	href="${_ContextPath}/CacheableResource/css/plugins/switchery/switchery.css"
	rel="stylesheet">
<link href="${_ContextPath}/CacheableResource/css/style.min.css?v=3.0.0"
	rel="stylesheet">
<link
	href="${_ContextPath}/CacheableResource/css/style.wgqc.css?v=1.0.0"
	rel="stylesheet">
</head>

<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>系统设置</h5>
						<div class="ibox-tools">
							<a class="collapse-link"> <i class="fa fa-chevron-up"></i>
							</a>
						</div>
					</div>
					<div class="ibox-content">
						<form class="form-horizontal m-t" id="signupForm">
							<div class="form-group">
								<label class="col-sm-3 control-label">包邮价格配置：</label>
								<div class="col-sm-5">
									<input id='freepost' value='${freepost}'>
									<input type='hidden' id='setId' value='${setId}'/>
								</div>
							</div>
							<div class="form-group">
								<div class="col-sm-8 col-sm-offset-3">
									<button id="formSubmit" class="btn btn-primary btn-lg" onclick=updatepost(setId,this) type="submit">提交</button>
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
	<script
		src="${_ContextPath}/CacheableResource/js/bootstrap.min.js?v=3.4.0"></script>



	<!-- Peity -->
	<script
		src="${_ContextPath}/CacheableResource/js/plugins/peity/jquery.peity.min.js"></script>

	<!-- 自定义js -->
	<script
		src="${_ContextPath}/CacheableResource/js/content.min.js?v=1.0.0"></script>


	<!-- iCheck -->
	<script
		src="${_ContextPath}/CacheableResource/js/plugins/iCheck/icheck.min.js"></script>

	<!-- Peity -->
	<script
		src="${_ContextPath}/CacheableResource/js/demo/peity-demo.min.js"></script>

	<!-- laydate.js -->
	<script
		src="${_ContextPath}/CacheableResource/js/plugins/layer/laydate/laydate.js"></script>

	<!-- summernote.js -->
	<script
		src="${_ContextPath}/CacheableResource/js/plugins/summernote/summernote.min.js"></script>
	<script
		src="${_ContextPath}/CacheableResource/js/plugins/summernote/summernote-zh-CN.js"></script>

	<!-- swal.js -->
	<script
		src="${_ContextPath}/CacheableResource/js/plugins/sweetalert/sweetalert.min.js"></script>

	<!-- webuploader -->
	<script
		src="${_ContextPath}/CacheableResource/js/plugins/webuploader/webuploader.min.js"></script>

	<!-- switchery -->
	<script
		src="${_ContextPath}/CacheableResource/js/plugins/switchery/switchery.js"></script>

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
                swal("提交成功!", "您的信息已经成功提交至后台!", "success");
                e.preventDefault();
            });
            $(document).on("click","#modalSubmit,#cancelSubmit",function(){
                $("#myModal input").val(""); //提交后清空表单
            });
        });
        
        
        function updatepost(setId,obj) {
        	var setId = $("#setId").val();
        	var freepost = $("#freepost").val();
        	var url = "${_ContextPath}/order/updateFreepost"
       		var data="setId="+setId+"&freepost="+freepost;
       		$.ajax({contentType:"application/x-www-form-urlencoded; charset=UTF-8",
       			url:url,
       			data:data,
       			success:function(result){
       			var res = eval("("+result+")")	
       			 $("#freepost").val(res.freepost);
       			},				
       			type:"POST"
       		});
        }
        
    </script>
</body>

</html>