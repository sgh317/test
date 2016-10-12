/**
 
* Phonegap callActivity by liboqiang
 
 
*
 
*/
 
var CallActivity = function() { 

}
 
//回退
CallActivity.prototype.goBack = function(success, fail, str) {
   cordova.exec(function(args){ success(args);},function(args) { fail(args);}, 'CallActivity', 'goBack', [ str ]);
};

//关闭窗口
CallActivity.prototype.closeWindow = function(success, fail, str) {
   cordova.exec(function(args){ success(args);},function(args) { fail(args);}, 'CallActivity', 'closeWindow', [ str ]);
};

//角标通知
CallActivity.prototype.subscriptNotify = function(success, fail, str) {
        /* These are the strings used in the dialog that appears if ZXing isn't installed */
        
   cordova.exec(function(args){ success(args);},function(args) { fail(args);}, 'CallActivity', 'subscriptNotify', [ str ]);
};
//设置帐号
CallActivity.prototype.setAccount = function(success, fail, str) {
    /* These are the strings used in the dialog that appears if ZXing isn't installed */
    
cordova.exec(function(args){ success(args);},function(args) { fail(args);}, 'CallActivity', 'setAccount', [ str ]);
};

cordova.addConstructor(function() {
 
  //如果不支持window.plugins,则创建并设置   
 
    if(!window.plugins){   
 
        window.plugins={};   
 
    }   
 
    window.plugins.callActivity=new CallActivity(); 

});