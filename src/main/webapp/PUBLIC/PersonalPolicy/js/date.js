
!(function($){
    var dateInput = $("input[type=date]"),dateLabel,thisValue;
    dateInput.bind("focus",function(){
        dateLabel = $(this).siblings("label"),
            thisValue = $(this).val();
        if( dateLabel.length ){
            dateLabel.hide();
        }
    })
    dateInput.bind("blur",function(){
        thisValue = $(this).val();
        if( thisValue != "" ){
            dateLabel.hide();
        }else{
            dateLabel.show();
        }
    })
})(Zepto);