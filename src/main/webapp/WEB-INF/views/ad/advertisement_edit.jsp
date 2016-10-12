 <!DOCTYPE html>
  <%@ page language="java" pageEncoding="UTF-8"%>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">

    <title>新增广告</title>
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
                        <h5>新增广告</h5>
                        <div class="ibox-tools">
                            <a class="collapse-link">
                                <i class="fa fa-chevron-up"></i>
                            </a>
                        </div>
                    </div>
                    <div class="ibox-content">
                        <form class="form-horizontal m-t" id="signupForm">
                        <input type="hidden" name="adId"/>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">分类：</label>
                                <div class="col-sm-5">
                                    <select class="form-control m-b" name="adLocation" id="catalogId"></select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">链接地址：</label>
                                <div class="col-sm-5">
                                    <input class="form-control" type="text" name="adHref" validate="1|notnull^链接地址未填写">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">当前状态：</label>
                                <div class="col-sm-8">
                                    <div class="radio i-checks">
                                        <label><input type="radio" checked value="N" name="adStatus"> <i></i>未上架</label>
                                    </div>
                                    <div class="radio i-checks">
                                        <label><input type="radio" value="Y" name="adStatus"> <i></i>已上架</label>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">广告图片：</label>
                                <div class="col-sm-5">
                                	<div class="file-list">
                                        <ul id="file_all_list" class="clearfix"></ul>
                                    </div>
                                    <div id="uploader" class="wu-example">
                                        <div class="queueList">
                                            <div id="dndArea" class="placeholder">
                                                <div id="filePicker"></div>
                                                <p>或将图片拖到这里，单次最多可选1张</p>
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
                                            <input id="photoAlbumImage" name="adPicture" type="hidden"/>
                                        </div>
                                    </div>
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
    
    <script type="text/javascript" charset="utf-8" src="${_ContextPath}/PUBLIC//common/js/cfg.js"></script>
	<script type="text/javascript" charset="utf-8" src="${_ContextPath}/PUBLIC//common/js/jquery-sso-patch_mobile.js"></script>
	<script type="text/javascript" charset="utf-8" src="${_ContextPath}/PUBLIC//common/js/JsonStubMobile.js"></script>
	<script type="text/javascript" charset="utf-8" src="${_ContextPath}/PUBLIC//common/js/CommonUtil.js"></script>

    <script>
	    function deleteImg(obj){
	    	$("#photoAlbumImage").val("");
	        var _this = obj.parentNode.parentNode,_thisParent = _this.parentNode;
	        _thisParent.removeChild(_this);
	    }
        $(document).ready(function () {
                var i = document.querySelector(".js-switch"), t = (new Switchery(i, {color: "#1AB394"}), document.querySelector(".js-switch_2")), a = (new Switchery(t, {color: "#ED5565"}), document.querySelector(".js-switch_3"));
                new Switchery(a, {color: "#1AB394"})
        });
        $(document).ready(function () {
            $('.i-checks').iCheck({
                checkboxClass: 'icheckbox_square-green',
                radioClass: 'iradio_square-green',
            });
            var res = ${result};
            $.ajax({contentType:"application/x-www-form-urlencoded; charset=UTF-8",
       			url:'${_ContextPath}/product/queryCatalogList',
       			success:function(result){
       				if(result){
		        		var resl = eval('('+result+')');
		        		var classifyList = resl.CatalogList;
		        		for (var i=0;i<classifyList.length;i++) {
		        			var a = ""
		        			$.each(res.adInfoMap,function(ky,vl) {
		        				a = vl.cname;
		        			});
		        			
		        			if(classifyList[i].cname == a) {
			        			$("#catalogId").append("<option selected value='"+classifyList[i].cid+"'>"+classifyList[i].cname+"</option>");
		        			} else {
			        			$("#catalogId").append("<option value='"+classifyList[i].cid+"'>"+classifyList[i].cname+"</option>");
		        			}
		        		}
       				}
       			},				
       			type:"POST"
       		});
            
            
            $.each(res.adInfoMap,function(ky,vl) {
            	$("input[name=adId]").val(vl.Ad_id);
            	$("input[name=adHref]").val(vl.ad_href);
            	if('Y' == vl.ad_status) {
            		$("input[name=adStatus][value=Y]").iCheck('check');
            	} else {
            		$("input[name=adStatus][value=N]").iCheck('check');
            	}
            	if('Y' == vl.isdiscount) {
	            	$("#isDiscount").attr("checked",true);
            	} else {
	            	$("#isDiscount").attr("checked",false);
            	}
            	var html = "";
            	var str= new Array();
            	str = (vl.ad_picture).split(",");
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
            	$("#photoAlbumImage").val(vl.ad_picture);
            });
            
            
            $("#formSubmit").on("click",function(e){
            	/** 规则校验 **/
            	Validate.validate(1);
        		if(Validate.getMsgByIndex(1)!=null&&Validate.getMsgByIndex(1)!=''){
        			alert('有录入项不符合校验规则');
        			return false;
        		}
        		if("" == $("#photoAlbumImage").val()) {
        			alert('请添加商品相册');
        			return false;
        		}
        		var form = document.forms[0]; 
        		// 富文本编辑器
                form.action = "${_ContextPath}/ad/updateAdInfo";  
                form.method = "post";  
                form.submit();
                swal("提交成功!", "您的信息已经成功提交至后台!", "success");
                e.preventDefault();
            });
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
                server: '${_ContextPath}/product/uploadImage?imageAddr=ad',
                fileNumLimit: 1,
                fileSizeLimit: 1 * 1024 * 1024,    // 200 M
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

            uploader.onFileDequeued = function( file ) {
                fileCount--;
                fileSize -= file.size;

                if ( !fileCount ) {
                    setState( 'pedding' );
                }

                removeFile( file );
                updateTotalProgress();

            };
            
            uploader.on('uploadSuccess', function (file, response) {//上传成功事件
            	var fileNameStr = "";
            	var fileName = new Array()
                fileName = response.id
                if(null != fileName && fileName.length != 0) {
	                	fileNameStr = fileName[0];
                }
            	$("#photoAlbumImage").val(fileNameStr)
            });

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