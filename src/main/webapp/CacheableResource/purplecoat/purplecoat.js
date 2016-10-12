(function ($) {

  var f = function(purplecoatToggleData, purplecoatColorData, target) {
    var $purplecoatVisible = $("[data-purplecoat-for=" + purplecoatToggleData + "]:visible");
    var $purplecoatHidden = $("[data-purplecoat-for=" + purplecoatToggleData + "]:hidden");

    if ($purplecoatVisible.size()) {
      $purplecoatVisible.fadeOut();
    } else if ($purplecoatHidden.size()) {
      $purplecoatHidden.fadeIn();
    } else {
      target.each(function () {
        var $myself = $(this);

        if ($myself.is(":hidden")) {
          return;
        }

        var $purplecoat = $('<div class="purplecoat"></div>');
        $('body').after($purplecoat);
        
        var $purplecoatInner = $('<div class="purplecoat-inner"></div>');
        $purplecoat.html($purplecoatInner);
        
        var $purplecoatInnerText = $('<div class="purplecoat-inner-text"></div>');
        $purplecoatInnerText.html($myself.data('purplecoat-label'));
        $purplecoatInner.html($purplecoatInnerText);

        if ($myself.data('purplecoat-color')) {
          purplecoatColorData = $myself.data('purplecoat-color');
        }

        if (purplecoatColorData) {
          $purplecoat.css('background-color', purplecoatColorData);
        }

        $purplecoat
        .attr('data-purplecoat-for', purplecoatToggleData)
        .css({
          'top': $myself.offset().top,
          'left': $myself.offset().left,
          'width': $myself.outerWidth(),
          'height': $myself.outerHeight() })
          .fadeIn();

      });
    }
  }

  $.fn.purplecoat = function() {
    var purplecoatToggleData = this.data("purplecoat");
    var purplecoatColorData = this.data("purplecoat-color");
    var target = this;

    f(purplecoatToggleData,
      purplecoatColorData,
      target);
  }

  $(function () {

    var styles = '.purplecoat { display: none; position: absolute; padding: 5px; box-sizing: border-box; background-color: rgba(96,96,96, 0.8); color: #FFF; text-align: center; font-weight: bold; overflow: hidden; z-index: 9999; } .purplecoat-inner { display: table; width: 100%; height: 100%; } .purplecoat-inner-text { font-size:18px; display: table-cell; vertical-align: middle;}';

    var $style = $('<style/>');
    $style.html(styles);
    //$('head').prepend($style);
    
    $('head').before($style);
    
    $('[data-purplecoat-toggle]').on('click', function () {
      var purplecoatToggleData = $(this).data('purplecoat-toggle');
      var purplecoatColorData = $(this).data('purplecoat-color');
      var target = $("[data-purplecoat=" + purplecoatToggleData + "]");

      f(purplecoatToggleData,
        purplecoatColorData,
        target);
    });
  });
})(jQuery);
