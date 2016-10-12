swfobject.addDomLoadEvent(function () {
    var webcamAvailable = false;
    var sourcePic1Url = $.Cookie('swf1');
    var swf1 = new fullAvatarEditor('swf1', 335, {
        id : 'swf1',
        upload_url : 'product/uploadImageForThumbnail?imageAddr=thumbnail',          //上传图片的接口。该接口需返回一个json字符串
        src_url : sourcePic1Url,			//默认加载的原图片的url
        src_size:'2MB',                     //选择的本地图片文件所允许的最大值，必须带单位，如888Byte，88KB，8MB
        src_size_over_limit:'您选择的原图片的大小超出最大值',             //当选择的原图片文件的大小超出指定最大值时的提示文本。
        tab_visible : false,
        button_visible : false,				//不显示按钮，外部自定义
        src_upload : 1,						//是否上传原图片的选项：2-显示复选框由用户选择，0-不上传，1-上传
        checkbox_visible : false,			//不显示复选框，外部自定义
        browse_box_align : 'left',				//图片选择框的水平对齐方式。left：左对齐；center：居中对齐；right：右对齐；数值：相对于舞台的x坐标
        avatar_sizes : '250*200',			//定义单个头像
        avatar_sizes_desc :'250*200像素', 	//头像尺寸的提示文本

        avatar_intro : '最终图片的尺寸为以下尺寸 * 2(设置的缩放系数)',
        avatar_tools_visible:true,			//是否显示颜色调整工具
        browse_box_width:300,
        browse_box_height:300,
        src_box_width:300,
        src_box_height:300
    }, function (json) {
        var id = this.id;
        switch (json.code) {
            case 2:
                if (json.type == 0) {
                    if(id == "swf1")
                    {
                        $('#webcamPanelButton').hide();
                        $('#editorPanelButtons').show();
                    }
                }
                else {
                    if(id == "swf1")$('#editorPanelButtons,#webcamPanelButton').hide();
                }
                break;
            case 3:
                if (json.type == 0) {
                    if(id == "swf1")
                    {
                        $('.button_shutter').removeClass('Disabled');
                        $('#webcamPanelButton').show();
                        webcamAvailable = true;
                    }
                }
                else {
                    if(id == "swf1")
                    {
                        webcamAvailable = false;
                        $('#webcamPanelButton').hide();
                    }
                }
                break;
            case 4:
                alert("您选择的原图片文件大小（" + json.content + "）超出了指定的值(2MB)。");
                break;
            case 5:
                if (json.type == 0) {
                    var e = this;
                    var html = $('<div class="imgList"/>');
                    for(var i = 0; i < json.content.avatarUrls.length; i++)
                    {
                        html.append('<dl><dt>图片'+(i+1)+'</dt><dd><img src="' + json.content.avatarUrls[i] + '" /></dd></dl>');
                    }
                    var button = [];
                    if(json.content.sourceUrl)
                    {
                        button.push({text : '修改图片', callback:function(){
                            this.close();
                            $.Cookie(id, json.content.sourceUrl);
                            location.reload();
                            //e.call('loadPic', json.content.sourceUrl);
                        }});
                    }
                    else
                    {
                        $.Cookie(id, null);
                    }
                    button.push({text : '关闭窗口'});
                    $.dialog({
                        title:'图片已成功保存至服务器',
                        content:html,
                        button:button,
                        mask:true,
                        draggable:false
                    });
                }
                else {
                    alert(json.type);
                }
                break;
        }
    });
    $('#src_upload').change(function () {
        swf1.call('srcUpload', this.checked);
    });
    $('.button_upload').click(function () {
        swf1.call('upload');
    });
    $('.button_cancel').click(function () {
            swf1.call('changepanel', 'upload');
            $('#editorPanelButtons,#webcamPanelButton').hide();
    });
    $('.button_shutter').click(function () {
        if (!$(this).hasClass('Disabled')) {
            $(this).addClass('Disabled');
            swf1.call('pressShutter');
        }
    });
});