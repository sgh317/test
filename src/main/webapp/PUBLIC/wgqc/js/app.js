/*!
 * =====================================================
 * App v1.0.0
 * =====================================================
 */

/**
 * App业务JS
 */

/*!
 * =====================================================
 * 首页 (快捷入口点击效果)
 * =====================================================
 */

(function($){
    var _this,thisHref,Time;
    $("#sectionLink ul li").click(function(e) {
        _this = $(this);
        thisHref = _this.find(".link-item").data("href");
        var ripple = $(this).find(".ripple");
        ripple.removeClass("animate");
        var x = parseInt(e.pageX - $(this).offset().left) - (ripple.width() / 2);
        var y = parseInt(e.pageY - $(this).offset().top) - (ripple.height() / 2);
        ripple.css({
            top: y,
            left: x
        }).addClass("animate");
        ripple.css({top:0,left:0}).addClass("animate");
        clearTimeout(Time);
        Time = setTimeout(function(){
            location.href = thisHref;
        },300);
    });
})(Zepto);

/*!
 * =====================================================
 * 列表页 (加入购物车效果)
 * =====================================================
 */

(function($){
    $(window).load(function(){
        var offset = $("#cart").offset(),cartNum = $("#cart .badge").text();
        $(".div-num").click(function(event){
            var addcar = $(this);
            var img = addcar.parents(".pro-info").siblings(".pro-img").find('img').attr('src');
            var flyer = $('<img class="u-flyer" src="' + img + '">');
            flyer.fly({
                start: {
                    left: event.pageX,
                    top: event.pageY
                },
                end: {
                    left: offset.left + 20,
                    top: offset.top + 20,
                    width: 0,
                    height: 0
                },
                onEnd: function(){
                    this.destory();
                    // 购物车数量值改变
                    cartNum ++;
                    $("#cart").find(".badge").text(cartNum);
                }
            });
            event.preventDefault()
        });
    });
})(jQuery);

/*!
 * =====================================================
 * 首页 (底部Tab页切换)
 * =====================================================
 */

/*
(function($){
    var _this,thisHref;
    $(".bar-tab .tab-item").on("click",function(){
        _this = $(this);
        thisHref = _this.attr("href");
        _this.addClass("active").siblings(".tab-item").removeClass("active");
        $(thisHref).addClass("page-section-current").siblings(".page-section").removeClass("page-section-current");
        $(thisHref).find(".page-loading").addClass("show-animate");
    })
})(Zepto);*/


/*!
 * =====================================================
 * 配送时间选择
 * =====================================================
 */

(function($){
    // 获取服务器时间
    function getTime(){
        var time = "2016-09-27 09:30:30";
        return time
    }
    // 计算当前时间

    // 选择时间后
    $(document).on("click",".time-list > ul > li",function(){
        // 标识选中时间
        $(this).addClass("current").siblings("li").removeClass("current");
        // 将时间返回到页面
        var setValue = $(this).find("span").text();
        $("#openTime").find(".select-value").text(setValue);
        // 关闭弹出层
        $.closeModal();
    })
})(Zepto);
