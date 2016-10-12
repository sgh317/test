
!function($){
     
    $(".formCard").find('.edit').bind("click",function(){
        var _this = $(this);
        var act = _this.parents("dt").siblings("dd").find(".updateList");
        if(_this.hasClass('state')){
            _this.removeClass("state").text("编辑");
            act.find(".delete").hide();
        }else{
            _this.addClass("state").text("完成");
            // 使图片进入编辑状态
            act.find(".delete").show();
            act.find(".delete").bind("click",function(){
                var _this = $(this);
                _this.parent("li").remove();
            })
        }

    })
}(Zepto);