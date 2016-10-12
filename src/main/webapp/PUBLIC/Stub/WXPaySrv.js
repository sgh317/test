window.RpcContext.beans['WXPaySrv']={
	  getParams:function(arg0,arg1,arg2,callbackObj){
      var request={
          functionName:'WXPaySrv.getParams',
          paramters:JSON.stringify({
          arg0:arg0,
          arg1:arg1,
          arg2:arg2
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
  if(document.location.href.startWith('http')){ window.RpcContext.beans['WXPaySrv'].realBaseURL='.'; } else {
  window.RpcContext.beans['WXPaySrv'].realBaseURL=window._RpcDefaultBaseURL; } ;
