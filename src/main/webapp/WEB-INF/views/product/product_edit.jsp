 <!DOCTYPE html>
 <%@ page language="java" pageEncoding="UTF-8"%>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">

    <title>新增商品</title>
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

<body class="gray-bg animated fadeInRight">
    <div class="wrapper wrapper-content">
        <div class="row">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>新增商品</h5>
                        <div class="ibox-tools">
                            <a class="collapse-link">
                                <i class="fa fa-chevron-up"></i>
                            </a>
                        </div>
                    </div>
                    <div class="ibox-content">
                        <form class="form-horizontal m-t" id="signupForm">
                        	<input type="hidden" name="productId"/>
                        	<div class="form-group">
                                <label class="col-sm-3 control-label">大分类：</label>
                                <div class="col-sm-5">
                                    <select class="form-control m-b" id="catalogId" name="catalogId"></select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">小分类：</label>
                                <div class="col-sm-5">
                                    <select class="form-control m-b" id="classifyId" name="classifyId"></select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">商品名称：</label>
                                <div class="col-sm-5">
                                    <input name="productName" class="form-control" type="text" validate="1|notnull^商品名称未填写">
                                    <!--<span class="help-block m-b-none"><i class="fa fa-info-circle"></i> 这里写点提示的内容</span>-->
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">简介：</label>
                                <div class="col-sm-5">
                                    <input name="productIntroductio" class="form-control" type="text" validate="1|notnull^简介未填写">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">是否加入特价：</label>
                                <div class="col-sm-5">
                                    <input id="isDiscount" type="checkbox" class="js-switch"/>
                                    <input name="isDiscount" type="hidden" value="N"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">规格：</label>
                                <div class="col-sm-5">
                                    <div class="specifications clearfix" id="specification"></div>
                                    <button type="button" class="btn btn-sm btn-success" data-toggle="modal" data-target="#myModal">创建</button>
                                    <div class="modal inmodal" id="myModal" tabindex="-1" role="dialog" aria-hidden="true">
                                        <div class="modal-dialog">
                                            <div class="modal-content animated fadeInDown">
                                                <div class="modal-header">
                                                    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
                                                    </button>
                                                    <i class="fa fa-laptop modal-icon"></i>
                                                    <h4 class="modal-title">创建规格</h4>
                                                </div>
                                                <div class="modal-body" id="newSpecification">
                                                    <div class="form-group"><label>规格名称</label> <input type="text" placeholder="" id="skuName" class="form-control"></div>
                                                    <div class="form-group"><label>库存</label> <input type="text" placeholder="" id="skuInventory" class="form-control"></div>
                                                    <div class="form-group"><label>价格</label> <input type="text" placeholder="" id="skuSellPrice" class="form-control"></div>
                                                    <div class="form-group"><label>折扣价格</label> <input type="text" placeholder="" id="discountPrice" class="form-control"></div>
                                                </div>
                                                <div class="modal-footer">
                                                    <button id="cancelSubmit" type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
                                                    <button id="modalSubmit" type="button" class="btn btn-primary" data-dismiss="modal">提交</button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="modal inmodal" id="editModal" tabindex="-1" role="dialog" aria-hidden="true">
                                        <div class="modal-dialog">
                                            <div class="modal-content animated fadeInDown">
                                                <div class="modal-header">
                                                    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
                                                    </button>
                                                    <i class="fa fa-laptop modal-icon"></i>
                                                    <h4 class="modal-title">修改规格</h4>
                                                </div>
                                                <div class="modal-body" id="editSpecification">
                                                    <div class="form-group"><label>规格名称</label> <input type="text" placeholder="" id="editskuName" class="form-control"></div>
                                                    <div class="form-group"><label>库存</label> <input type="text" placeholder="" id="editskuInventory" class="form-control"></div>
                                                    <div class="form-group"><label>价格</label> <input type="text" placeholder="" id="editskuSellPrice" class="form-control"></div>
                                                    <div class="form-group"><label>折扣价格</label> <input type="text" placeholder="" id="editdiscountPrice" class="form-control"></div>
                                                </div>
                                                <div class="modal-footer">
                                                    <button id="editCancelSubmit" type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
                                                    <button id="editModalSubmit" type="button" class="btn btn-primary" data-dismiss="modal">提交</button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">当前状态：</label>
                                <div class="col-sm-8">
                                    <div class="radio i-checks">
                                        <label><input type="radio" value="N" name="isShow"> <i></i>未上架</label>
                                    </div>
                                    <div class="radio i-checks">
                                        <label><input type="radio" value="Y" name="isShow"> <i></i>已上架</label>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">缩略图：</label>
                                <div class="col-sm-5">
                                    <!-- <div id="flash1">
                                        <p id="swf1">本组件需要安装Flash Player后才可使用，请从<a href="http://www.adobe.com/go/getflashplayer">这里</a>下载安装。</p>
                                    </div>
                                    <div id="editorPanelButtons" style="display:none">
                                        <p>
                                            <a href="javascript:;" class="btn btn-w-m btn-primary button_upload"><i class="fa fa-upload"></i> 上传</a>
                                            <a href="javascript:;" class="btn btn-w-m btn-white button_cancel">取消</a>
                                        </p>
                                    </div> -->
                                    <div class="btn-group">
                                        <div class="control-group js_uploadBox">
                                            <div class="js_showBox" style="display:block"><img class="js_logoBox" src="" width="100px"></div>
                                            <div class="btn-upload">
                                                <label title="上传图片" for="inputImage" class="btn btn-primary">
                                                    <input type="file" accept="image/*" id="inputImage" class="js_upFile" style="opacity:0;">
                                                    <span class="js_uploadText">上传</span>
                                                    <input id="photoThumbnailFile" name="productThumbnail" type="hidden"/>
                                                </label>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">商品相册：</label>
                                <div class="col-sm-5">
                                	<div class="file-list">
                                        <ul id="file_all_list" class="clearfix"></ul>
                                    </div>
                                    <div id="uploader" class="wu-example">
                                        <div class="queueList">
                                            <div id="dndArea" class="placeholder">
                                                <div id="filePicker"></div>
                                                <p>或将图片拖到这里，单次最多可选5张</p>
                                            </div>
                                        </div>
                                        <div class="statusBar" style="display:none;">
                                            <div class="progress">
                                                <span class="text">0%</span>
                                                <span class="percentage"></span>
                                            </div><div class="info"></div>
                                            <div class="btns">
                                                <div id="filePicker2"></div><div class="uploadBtn">开始上传</div>
                                            </div>
                                            <input id="photoAlbumImage" name="productPhotoAlbumImage" type="hidden"/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">商品详情：</label>
                                <div class="col-sm-8">
                                    <div class="summernote" name="productDetail"></div>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-8 col-sm-offset-3">
                                    <button id="formSubmit" class="btn btn-primary btn-lg" type="submit">提交</button>
                                    <button onclick="history.go(-1)" class="btn btn-default btn-md" type="button">返回</button>
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
    <script src="${_ContextPath}/CacheableResource/js/jquery.base64.js"></script>



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

    <!-- fullAvatarEditor -->
    <%-- <script src="${_ContextPath}/CacheableResource/js/plugins/fullavatareditor/scripts/fullAvatarEditor.js"></script>
    <script src="${_ContextPath}/CacheableResource/js/plugins/fullavatareditor/scripts/jQuery.Cookie.js"></script>
    <script src="${_ContextPath}/CacheableResource/js/plugins/fullavatareditor/scripts/swfobject.js"></script>
    <script src="${_ContextPath}/CacheableResource/js/plugins/fullavatareditor/scripts/upload.js"></script> --%>

	<script src="${_ContextPath}/CacheableResource/js/jquery.uploadView.js"></script>
    <script type="text/javascript" charset="utf-8" src="${_ContextPath}/PUBLIC//common/js/cfg.js"></script>
	<script type="text/javascript" charset="utf-8" src="${_ContextPath}/PUBLIC//common/js/jquery-sso-patch_mobile.js"></script>
	<script type="text/javascript" charset="utf-8" src="${_ContextPath}/PUBLIC//common/js/JsonStubMobile.js"></script>
	<script type="text/javascript" charset="utf-8" src="${_ContextPath}/PUBLIC//common/js/CommonUtil.js"></script>

    <script>
    
	    function deleteImg(obj){
	    	var str= new Array();
	    	str = $("#photoAlbumImage").val().split(",");
	    	var b = $(obj).parent().children("#fileSingl").val();
	    	var c = "";
	    	for (i=0;i<str.length ;i++){ 
	    		if(str[i] == b) {
	    			continue;
	    		}
	    		c += str[i] + ","
	    	}
	    	$("#photoAlbumImage").val(c.substring(0,c.length - 1));
	        var _this = obj.parentNode.parentNode,_thisParent = _this.parentNode;
	        _thisParent.removeChild(_this);
	    }
        $(document).ready(function () {
                var i = document.querySelector(".js-switch"), t = (new Switchery(i, {color: "#1AB394"}), document.querySelector(".js-switch_2")), a = (new Switchery(t, {color: "#ED5565"}), document.querySelector(".js-switch_3"));
                new Switchery(a, {color: "#1AB394"});
                
        });
        $(document).ready(function () {
            $('.i-checks').iCheck({
                checkboxClass: 'icheckbox_square-green',
                radioClass: 'iradio_square-green',
            });
            
			var res = ${result};
            
            $.each(res.productInfoMap,function(ky,vl) {
            	$("#classifyId").val(vl[0].classid);
            	$("#catalogId").val(vl[0].cataid);
            	$("input[name=productName]").val(vl[0].proname);
            	$("input[name=productId]").val(vl[0].proid);
            	$("input[name=productIntroductio]").val(vl[0].prodescri);
            	$("input[name=isDiscount]").val(vl[0].isdiscount);
            	if('Y' == vl[0].isshow) {
            		$("input[name=isShow][value=Y]").iCheck('check');
            	} else {
            		$("input[name=isShow][value=N]").iCheck('check');
            	}
            	if('Y' == vl[0].isdiscount) {
	            	$("#isDiscount").attr("checked",true);
            	} else {
	            	$("#isDiscount").attr("checked",false);
            	}
            	$(".js_logoBox").attr("src","${_ContextPath}/product/showImage?imageAddress="+vl[0].colorpic);
            	$("#photoAlbumImage").val(vl[0].colorpic);
            	$(".summernote").summernote("code",vl[0].gooddetail);
            	var html = "";
            	var str= new Array();
            	str = (vl[0].goodpic).split(",");
            	for (i=0;i<str.length ;i++){   
	            	html +="<li class='file-item'>";
	            	html +="<div class='file-panel'><span class='cancel' onclick='deleteImg(this)'>删除</span>";
	            	html +="<input id='fileSingl' type='hidden' value='"+str[i]+"'/></div>";
	            	html +="<div class='img'>";
	            	html +="    <div class='inner'>";
	            	html +="        <img width='100%' src='${_ContextPath}/product/showImage?imageAddress="+str[i]+"'/>";
	            	html +="    </div>";
	            	html +="</div>";
	            	html +="</li>";
                }  
            	$("#file_all_list").append(html);
            	$("#photoAlbumImage").val(vl[0].goodpic);
            	$.each(vl,function(cky,cvl) {
            		var html = "";
                	html += "<div class='item pull-left m-r-md tooltip-demo' data-toggle='modal' data-target='#editModal'>"
                	html += "<i class='fa fa-times-circle specifications-close'></i>"
                	html += "<button type='button' class='btn btn-default' data-price='60' data-num='400' data-toggle='tooltip' data-placement='bottom' title='价格:"+cvl.sellprice +"库存:"+cvl.inventory+"'>"+cvl.skuname+"</button>"
                	html += "<input name='skuName' type='hidden' value='"+cvl.skuname+"'/>";
                	html += "<input name='skuId' type='hidden' value='"+cvl.proskuid+"'/>";
                	html += "<input name='skuInventory' type='hidden' value='"+cvl.inventory+"'/>";
                	html += "<input name='skuSellPrice' type='hidden' value='"+cvl.sellprice+"'/>";
                	html += "<input name='discountPrice' type='hidden' value='"+cvl.discountprice+"'/>";
                	html += "</div>";
                	$("#specification").append(html);
            	});
            });
            
            $("#inputImage").uploadView({
                uploadBox: '.js_uploadBox',//设置上传框容器
                showBox : '.js_showBox',//设置显示预览图片的容器
                allowType: ["gif", "jpeg", "jpg", "bmp", "png"], //允许上传图片的类型
                maxSize :2, //允许上传图片的最大尺寸，单位M
                success:function(e){
                    $(".js_uploadText").text('更改');
                    var data = new FormData();
                    data.append("file", $('#inputImage').get(0).files[0]);
                    data.append("imageAddr", 'productAlbumImage');
                    $.ajax({
                        data : data,
                        type : 'POST',
                        url : '${_ContextPath}/product/uploadImage',
                        cache : false,
                        contentType : false,
                        processData : false,
                        success : function(response) {
                            var json = eval('('+response+')');
                            var fileNameStr = "";
                        	var fileName = new Array()
                            fileName = json.id
                            if(null != fileName && fileName.length != 0) {
                            	for(var i=0;i<fileName.length;i++) {
             	                	fileNameStr = fileName[i];
             	               		$("#photoThumbnailFile").val(fileNameStr);
                            	}
                            }
                        },  
                        error: function (data, status, e) {  
                            alert(e);  
                        }   
                    });  
                }
            });
            
            $.ajax({contentType:"application/x-www-form-urlencoded; charset=UTF-8",
       			url:'${_ContextPath}/product/queryCatalogList',
       			success:function(result){
       				if(result){
		        		var resl = eval('('+result+')');
		        		var classifyList = resl.CatalogList;
		        		for (var i=0;i<classifyList.length;i++) {
		        			var a = ""
		        			$.each(res.productInfoMap,function(ky,vl) {
		        				a = vl[0].cname;
		        			});
		        			
		        			if(classifyList[i].cname == a) {
			        			$("#catalogId").append("<option selected value='"+classifyList[i].cid+"'>"+classifyList[i].cname+"</option>");
		        			} else {
			        			$("#catalogId").append("<option value='"+classifyList[i].cid+"'>"+classifyList[i].cname+"</option>");
		        			}
		        		}
		        		var parentcid=$("#catalogId").children('option:selected').val();//这就是selected的值 
		            	$("#classifyId").empty();
			            $.ajax({contentType:"application/x-www-form-urlencoded; charset=UTF-8",
			       			url:'${_ContextPath}/product/queryClassifyList',
			       			data:"parentcid="+parentcid,
			       			success:function(result){
			       				if(result){
					        		var res = eval('('+result+')');
					        		var classifyList = res.ClassifyList;
					        		for (var i=0;i<classifyList.length;i++) {
					        			$("#classifyId").append("<option value='"+classifyList[i].cid+"'>"+classifyList[i].cname+"</option>");
					        		}
			       				}
			       			},				
			       			type:"POST"
			       		});
       				}
       			},				
       			type:"POST"
       		});
            
            $('#catalogId').change(function(){ 
            	var parentcid=$(this).children('option:selected').val();//这就是selected的值 
            	$("#classifyId").empty();
	            $.ajax({contentType:"application/x-www-form-urlencoded; charset=UTF-8",
	       			url:'${_ContextPath}/product/queryClassifyList',
	       			data:"parentcid="+parentcid,
	       			success:function(result){
	       				if(result){
			        		var res = eval('('+result+')');
			        		var classifyList = res.ClassifyList;
			        		for (var i=0;i<classifyList.length;i++) {
			        			$("#classifyId").append("<option value='"+classifyList[i].cid+"'>"+classifyList[i].cname+"</option>");
			        		}
	       				}
	       			},				
	       			type:"POST"
	       		});
            });
            $(".summernote").summernote({
            	lang:"zh-CN",
            	minHeight:300,
            	dialogsFade : true,
                dialogsInBody : true,
                disableDragAndDrop : false,
                callbacks : {
                    onImageUpload : function(files) {
                        var $files = $(files);
                        $files.each(function() {
                            var file = this;
                            var data = new FormData();
                            data.append("file", file);
	                        data.append("imageAddr", 'productDetail');
	                        $.ajax({
	                            data : data,
	                            type : 'POST',
	                            url : '${_ContextPath}/product/uploadImage',
	                            cache : false,
	                            contentType : false,
	                            processData : false,
	                            success : function(response) {
	                                var json = eval('('+response+')');
	                                var fileNameStr = "";
	                            	var fileName = new Array()
	                                fileName = json.id
	                                if(null != fileName && fileName.length != 0) {
	                                	for(var i=0;i<fileName.length;i++) {
	                 	                fileNameStr = fileName[i];
	                                     var imageUrl = "${_ContextPath}/product/showImage?imageAddress="+fileNameStr;
	                                     $(".summernote").summernote('insertImage', imageUrl, function($image) {

	                                     });
	                                	}
	                                }
	                            },
	                            error : function(XMLHttpRequest, textStatus, errorThrown) {  
	                                alert(XMLHttpRequest.status);  
	                                alert(XMLHttpRequest.readyState);  
	                                alert(textStatus);  
	                            }  
	                        });
                    	});
                	}
                },
                onChange : function(contents, $editable) {

                    if ($(this).parents().length > 0) {
                        var $form = $(this).parents().find("form.required-validate", $p);

                        if ($form.length > 0) {
                            var data = $form.data('bootstrapValidator');
                            if ($(this).summernote('isEmpty')) {
                                data.updateStatus($(this).attr("name"), 'INVALID');
                            } else {
                                data.updateStatus($(this).attr("name"), 'VALID');
                            }
                        }
                    }

                },
                onInit : function() {
                    if ($(this).parents().length > 0) {
                        var $form = $(this).parents().find("form.required-validate", $p);
                        if ($form.length > 0) {
                            var data = $form.data('bootstrapValidator');
                            if (!$(this).summernote('isEmpty')) {
                                data.updateStatus($(this).attr("name"), 'VALID');
                            }
                        }
                    }
                }
            });
            
            var i=document.querySelector(".js-switch");
            i.onchange = function(){
            	if(i.checked){
	            	$("input[name='isDiscount']").val('Y');
            	}else {
            		$("input[name='isDiscount']").val('N');
            	}
            }
            
            
            $("#formSubmit").on("click",function(e){
            	/** 规则校验 **/
            	Validate.validate(1);
        		if(Validate.getMsgByIndex(1)!=null&&Validate.getMsgByIndex(1)!=''){
        			alert('有录入项不符合校验规则');
        			return false;
        		}
        		if($("#specification:has(div)").length == 0) {
        			alert('请添加规格');
        			return false;
        		}
        		if("" == $("#photoAlbumImage").val()) {
        			alert('请添加商品相册');
        			return false;
        		}
        		
        		var form = document.forms[0]; 
        		// 富文本编辑器

                if ($(".summernote").summernote('isEmpty')) {
                } else {
                    // 使用base64对内容进行编码
                    // 1.解决复制不闭合的html文档，保存后显示错乱的bug
                    // 2.解决文本中特殊字符导致的bug
                    var editor = "<input type='hidden' name='" + $(".summernote").attr("name") + "' value='" + $.base64.btoa($(".summernote").summernote('code')) + "' />";
                    $(form).append(editor);
                }

        		
                form.action = "${_ContextPath}/product/modifyProductInfo";  
                form.method = "post";  
                form.submit();  
        		swal("提交成功!", "您的信息已经成功提交至后台!", "success");
                e.preventDefault();
                
            });
            
            $("#modalSubmit").click(function(){
            	var isDiscount = $("input[name='isDiscount']").val();
            	var skuName = $("#skuName").val();
            	var skuInventory = $("#skuInventory").val();
            	var skuSellPrice = $("#skuSellPrice").val();
            	var discountPrice = $("#discountPrice").val();
            	if(null == skuName || skuName =="" || null == skuInventory || skuInventory =="" || null == skuSellPrice || skuSellPrice =="") {
            		alert("有录入项不符合要求");
            		return false
            	}
            	if(isDiscount == 'Y' && (null == discountPrice || discountPrice =="")) {
            		alert("该商品为特价商品请填写折扣价格");
            		return false
            	}
            	var html = "";
            	html += "<div class='item pull-left m-r-md tooltip-demo' data-toggle='modal' data-target='#editModal'>"
            	html += "<i class='fa fa-times-circle specifications-close'></i>"
            	html += "<button type='button' class='btn btn-default' data-price='60' data-num='400' data-toggle='tooltip' data-placement='bottom' title='价格:"+skuSellPrice +"库存:"+skuInventory+"'>"+skuName+"</button>"
            	html += "<input name='skuName' type='hidden' value='"+skuName+"'/>";
            	html += "<input name='skuId' type='hidden' value=''/>";
            	html += "<input name='skuInventory' type='hidden' value='"+skuInventory+"'/>";
            	html += "<input name='skuSellPrice' type='hidden' value='"+skuSellPrice+"'/>";
            	html += "<input name='discountPrice' type='hidden' value='"+discountPrice+"'/>";
            	html += "</div>";
            	$("#specification").append(html);
            	$("#myModal input").val("")
            });
            
            $('#editModal').on('show.bs.modal', function (event) {
            	  eventDom = event.relatedTarget;
            	  $("#editskuName").val($(event.relatedTarget).children("input[name='skuName']").val());
            	  $("#editskuInventory").val($(event.relatedTarget).children("input[name='skuInventory']").val());
            	  $("#editskuSellPrice").val($(event.relatedTarget).children("input[name='skuSellPrice']").val());
            	  $("#editdiscountPrice").val($(event.relatedTarget).children("input[name='discountPrice']").val());
            	  $('#editModalSubmit').on("click",function(){
            		  var isDiscount = $("input[name='isDiscount']").val();
            		  var skuName = $("#editskuName").val();
                  	  var skuInventory = $("#editskuInventory").val();
                  	  var skuSellPrice = $("#editskuSellPrice").val();
                  	  var discountPrice = $("#editdiscountPrice").val();
            		  if(null == skuName || skuName =="" || null == skuInventory || skuInventory =="" || null == skuSellPrice || skuSellPrice =="") {
                  		alert("有录入项不符合要求");
                  		return false
                  	  }
                  	  if(isDiscount == 'Y' && (null == discountPrice || discountPrice =="")) {
                  		alert("该商品为特价商品请填写折扣价格");
                  		return false
                  	  }
            		  $(eventDom).children("button").html($("#editskuName").val());
            		  var title = "价格:"+$("#editskuSellPrice").val() +"库存:"+$("#editskuInventory").val()
            		  $(eventDom).children("button").attr("title",title);
                      $(eventDom).children("input[name='skuName']").val($("#editskuName").val());
                      $(eventDom).children("input[name='skuInventory']").val($("#editskuInventory").val());
                      $(eventDom).children("input[name='skuSellPrice']").val($("#editskuSellPrice").val());
                      $(eventDom).children("input[name='discountPrice']").val($("#editdiscountPrice").val());
                  })

            });
            
         	// 规格删除
            $(document).on("click",".specifications-close",function(event){
                var _this = $(this);
                _this.parents(".item").remove();
                event.preventDefault();
                var data = "skuId="+_this.parent().children("input[name='skuId']").val();
                $.ajax({data : data,
                    type : 'POST',
                    url : '${_ContextPath}/product/deleteSkuInfo',
                    success : function(response) {
                    },  
                    error: function (data, status, e) {  
                    	console.log(e);
                    }   
                }); 
                return false;
            });
            
            /* $(document).on("click","#modalSubmit,#cancelSubmit",function(){
                $("#myModal input").val(""); //提交后清空表单
            }); */
        });
        jQuery(function() {
            var $ = jQuery,    // just in case. Make sure it's not an other libaray.

                    $wrap = $('#uploader'),

            // 图片容器
                    $queue = $('<ul class="filelist"></ul>')
                            .appendTo( $wrap.find('.queueList') ),

            // 状态栏，包括进度和控制按钮
                    $statusBar = $wrap.find('.statusBar'),

            // 文件总体选择信息。
                    $info = $statusBar.find('.info'),

            // 上传按钮
                    $upload = $wrap.find('.uploadBtn'),

            // 没选择文件之前的内容。
                    $placeHolder = $wrap.find('.placeholder'),

            // 总体进度条
                    $progress = $statusBar.find('.progress').hide(),

            // 添加的文件数量
                    fileCount = 0,

            // 添加的文件总大小
                    fileSize = 0,

            // 优化retina, 在retina下这个值是2
                    ratio = window.devicePixelRatio || 1,

            // 缩略图大小
                    thumbnailWidth = 110 * ratio,
                    thumbnailHeight = 110 * ratio,

            // 可能有pedding, ready, uploading, confirm, done.
                    state = 'pedding',

            // 所有文件的进度信息，key为file id
                    percentages = {},

                    supportTransition = (function(){
                        var s = document.createElement('p').style,
                                r = 'transition' in s ||
                                        'WebkitTransition' in s ||
                                        'MozTransition' in s ||
                                        'msTransition' in s ||
                                        'OTransition' in s;
                        s = null;
                        return r;
                    })(),

            // WebUploader实例
                    uploader;

            if ( !WebUploader.Uploader.support() ) {
                alert( 'Web Uploader 不支持您的浏览器！如果你使用的是IE浏览器，请尝试升级 flash 播放器');
                throw new Error( 'WebUploader does not support the browser you are using.' );
            }

            // 实例化
            uploader = WebUploader.create({
                pick: {
                    id: '#filePicker',
                    label: '点击选择图片'
                },
                dnd: '#uploader .queueList',
                paste: document.body,

                accept: {
                    title: 'Images',
                    extensions: 'gif,jpg,jpeg,bmp,png',
                    mimeTypes: 'image/*'
                },

                // swf文件路径
                //swf: BASE_URL + '/js/Uploader.swf',

                disableGlobalDnd: true,

                chunked: true,
                server: '${_ContextPath}/product/uploadImage?imageAddr=photoAlbum',
                fileNumLimit: 5,
                fileSizeLimit: 5 * 1024 * 1024,    // 200 M
                fileSingleSizeLimit: 1 * 1024 * 1024    // 50 M
            });

            // 添加“添加文件”的按钮，
            uploader.addButton({
                id: '#filePicker2',
                label: '继续添加'
            });

            // 当有文件添加进来时执行，负责view的创建
            function addFile( file ) {
                var $li = $( '<li id="' + file.id + '">' +
                                '<p class="title">' + file.name + '</p>' +
                                '<p class="imgWrap"></p>'+
                                '<p class="progress"><span></span></p>' +
                                '</li>' ),

                        $btns = $('<div class="file-panel">' +
                                '<span class="cancel">删除</span>' +
                                '<span class="rotateRight">向右旋转</span>' +
                                '<span class="rotateLeft">向左旋转</span></div>').appendTo( $li ),
                        $prgress = $li.find('p.progress span'),
                        $wrap = $li.find( 'p.imgWrap' ),
                        $info = $('<p class="error"></p>'),

                        showError = function( code ) {
                            switch( code ) {
                                case 'exceed_size':
                                    text = '文件大小超出';
                                    break;

                                case 'interrupt':
                                    text = '上传暂停';
                                    break;

                                default:
                                    text = '上传失败，请重试';
                                    break;
                            }

                            $info.text( text ).appendTo( $li );
                        };

                if ( file.getStatus() === 'invalid' ) {
                    showError( file.statusText );
                } else {
                    // @todo lazyload
                    $wrap.text( '预览中' );
                    uploader.makeThumb( file, function( error, src ) {
                        if ( error ) {
                            $wrap.text( '不能预览' );
                            return;
                        }

                        var img = $('<img src="'+src+'">');
                        $wrap.empty().append( img );
                    }, thumbnailWidth, thumbnailHeight );

                    percentages[ file.id ] = [ file.size, 0 ];
                    file.rotation = 0;
                }

                file.on('statuschange', function( cur, prev ) {
                    if ( prev === 'progress' ) {
                        $prgress.hide().width(0);
                    } else if ( prev === 'queued' ) {
                        $li.off( 'mouseenter mouseleave' );
                        $btns.remove();
                    }

                    // 成功
                    if ( cur === 'error' || cur === 'invalid' ) {
                        console.log( file.statusText );
                        showError( file.statusText );
                        percentages[ file.id ][ 1 ] = 1;
                    } else if ( cur === 'interrupt' ) {
                        showError( 'interrupt' );
                    } else if ( cur === 'queued' ) {
                        percentages[ file.id ][ 1 ] = 0;
                    } else if ( cur === 'progress' ) {
                        $info.remove();
                        $prgress.css('display', 'block');
                    } else if ( cur === 'complete' ) {
                        $li.append( '<span class="success"></span>' );
                    }

                    $li.removeClass( 'state-' + prev ).addClass( 'state-' + cur );
                });

                $li.on( 'mouseenter', function() {
                    $btns.stop().animate({height: 30});
                });

                $li.on( 'mouseleave', function() {
                    $btns.stop().animate({height: 0});
                });

                $btns.on( 'click', 'span', function() {
                    var index = $(this).index(),
                            deg;

                    switch ( index ) {
                        case 0:
                            uploader.removeFile( file );
                            return;

                        case 1:
                            file.rotation += 90;
                            break;

                        case 2:
                            file.rotation -= 90;
                            break;
                    }

                    if ( supportTransition ) {
                        deg = 'rotate(' + file.rotation + 'deg)';
                        $wrap.css({
                            '-webkit-transform': deg,
                            '-mos-transform': deg,
                            '-o-transform': deg,
                            'transform': deg
                        });
                    } else {
                        $wrap.css( 'filter', 'progid:DXImageTransform.Microsoft.BasicImage(rotation='+ (~~((file.rotation/90)%4 + 4)%4) +')');
                    }


                });

                $li.appendTo( $queue );
            }

            // 负责view的销毁
            function removeFile( file ) {
                var $li = $('#'+file.id);

                delete percentages[ file.id ];
                updateTotalProgress();
                $li.off().find('.file-panel').off().end().remove();
            }

            function updateTotalProgress() {
                var loaded = 0,
                        total = 0,
                        spans = $progress.children(),
                        percent;

                $.each( percentages, function( k, v ) {
                    total += v[ 0 ];
                    loaded += v[ 0 ] * v[ 1 ];
                } );

                percent = total ? loaded / total : 0;

                spans.eq( 0 ).text( Math.round( percent * 100 ) + '%' );
                spans.eq( 1 ).css( 'width', Math.round( percent * 100 ) + '%' );
                updateStatus();
            }

            function updateStatus() {
                var text = '', stats;

                if ( state === 'ready' ) {
                    text = '选中' + fileCount + '张图片，共' +
                            WebUploader.formatSize( fileSize ) + '。';
                } else if ( state === 'confirm' ) {
                    stats = uploader.getStats();
                    if ( stats.uploadFailNum ) {
                        text = '已成功上传' + stats.successNum+ '张图片，'+
                                stats.uploadFailNum + '张图片上传失败，<a class="retry" href="#">重新上传</a>失败图片或<a class="ignore" href="#">忽略</a>'
                    }

                } else {
                    stats = uploader.getStats();
                    text = '共' + fileCount + '张（' +
                            WebUploader.formatSize( fileSize )  +
                            '），已上传' + stats.successNum + '张';

                    if ( stats.uploadFailNum ) {
                        text += '，失败' + stats.uploadFailNum + '张';
                    }
                }

                $info.html( text );
            }

            function setState( val ) {
                var file, stats;

                if ( val === state ) {
                    return;
                }

                $upload.removeClass( 'state-' + state );
                $upload.addClass( 'state-' + val );
                state = val;
                switch ( state ) {
                    case 'pedding':
                        $placeHolder.removeClass( 'element-invisible' );
                        $queue.parent().removeClass('filled');
                        $queue.hide();
                        $statusBar.addClass( 'element-invisible' );
                        uploader.refresh();
                        break;

                    case 'ready':
                        $placeHolder.addClass( 'element-invisible' );
                        $( '#filePicker2' ).removeClass( 'element-invisible');
                        $queue.parent().addClass('filled');
                        $queue.show();
                        $statusBar.removeClass('element-invisible');
                        uploader.refresh();
                        break;

                    case 'uploading':
                        $( '#filePicker2' ).addClass( 'element-invisible' );
                        $progress.show();
                        $upload.text( '暂停上传' );
                        break;

                    case 'paused':
                        $progress.show();
                        $upload.text( '继续上传' );
                        break;

                    case 'confirm':
                        $progress.hide();
                        $upload.text( '开始上传' ).addClass( 'disabled' );

                        stats = uploader.getStats();
                        if ( stats.successNum && !stats.uploadFailNum ) {
                            setState( 'finish' );
                            return;
                        }
                        break;
                    case 'finish':
                        stats = uploader.getStats();
                        if ( stats.successNum ) {
                            alert( '上传成功' );
                        } else {
                            // 没有成功的图片，重设
                            state = 'done';
                            location.reload();
                        }
                        break;
                }

                updateStatus();
            }

            uploader.onUploadProgress = function( file, percentage ) {
                var $li = $('#'+file.id),
                        $percent = $li.find('.progress span');

                $percent.css( 'width', percentage * 100 + '%' );
                percentages[ file.id ][ 1 ] = percentage;
                updateTotalProgress();
            };

            uploader.onFileQueued = function( file ) {
                fileCount++;
                fileSize += file.size;

                if ( fileCount === 1 ) {
                    $placeHolder.addClass( 'element-invisible' );
                    $statusBar.show();
                }

                addFile( file );
                setState( 'ready' );
                updateTotalProgress();
            };
            
            
            uploader.on('uploadSuccess', function (file, response) {//上传成功事件
            	var fileNameStr = "";
            	var fileName = new Array()
                fileName = response.id
                if(null != fileName && fileName.length != 0) {
	                	fileNameStr = fileName[0];
                }
            	if(null == $("#photoAlbumImage").val()  || $("#photoAlbumImage").val() == "") {
            		$("#photoAlbumImage").val(fileNameStr)
            	} else {
	            	fileNameStr = fileNameStr + "," +$("#photoAlbumImage").val();
	            	$("#photoAlbumImage").val(fileNameStr)
            	}
            });

            uploader.onFileDequeued = function( file ) {
                fileCount--;
                fileSize -= file.size;

                if ( !fileCount ) {
                    setState( 'pedding' );
                }

                removeFile( file );
                updateTotalProgress();

            };

            uploader.on( 'all', function( type ) {
                var stats;
                switch( type ) {
                    case 'uploadFinished':
                        setState( 'confirm' );
                        break;

                    case 'startUpload':
                        setState( 'uploading' );
                        break;

                    case 'stopUpload':
                        setState( 'paused' );
                        break;

                }
            });

            uploader.onError = function( code ) {
                alert( 'Eroor: ' + code );
            };

            $upload.on('click', function() {
                if ( $(this).hasClass( 'disabled' ) ) {
                    return false;
                }

                if ( state === 'ready' ) {
                    uploader.upload();
                } else if ( state === 'paused' ) {
                    uploader.upload();
                } else if ( state === 'uploading' ) {
                    uploader.stop();
                }
            });

            $info.on( 'click', '.retry', function() {
                uploader.retry();
            } );

            $info.on( 'click', '.ignore', function() {
                alert( 'todo' );
            } );

            $upload.addClass( 'state-' + state );
            updateTotalProgress();
        });
    </script>

</body>

</html>