window.RpcContext.beans['basicImageJSBean']={
		uploadOrderImages:function(arg0,arg1,arg2,arg3,callbackObj){
		      var request={
		              functionName:'basicImageJSBean.uploadOrderImages',
		              paramters:JSON.stringify({
		              arg0:arg0,
		              arg1:arg1,
		              arg2:arg2,
		              arg3:arg3
		              })
		          };
		          if(callbackObj!=null){
		                RpcContext.invoke(request,this.realBaseURL,callbackObj);
		          }else{
		                return RpcContext.invoke(request,this.realBaseURL,callbackObj);
		          }
		    }
}
;
if(document.location.href.startWith('http')){ window.RpcContext.beans['basicImageJSBean'].realBaseURL='.'; } else {
window.RpcContext.beans['basicImageJSBean'].realBaseURL=window._RpcDefaultBaseURL; } ;