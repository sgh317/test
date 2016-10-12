window.RpcContext.beans['WxImageJsBean']={
   getWXConfigData:function(arg0,callbackObj){
      var request={
          functionName:'WxImageJsBean.getWXConfigData',
          paramters:JSON.stringify({
          arg0:arg0
          })
      };
      if(callbackObj!=null){
            RpcContext.invoke(request,this.realBaseURL,callbackObj);
      }else{
            return RpcContext.invoke(request,this.realBaseURL,callbackObj);
      }
   },
   uploadClaimImages:function(arg0,callbackObj){
      var request={
          functionName:'WxImageJsBean.uploadClaimImages',
          paramters:JSON.stringify({
          arg0:arg0
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
  if(document.location.href.startWith('http')){ window.RpcContext.beans['WxImageJsBean'].realBaseURL='.'; } else {
  window.RpcContext.beans['WxImageJsBean'].realBaseURL=window._RpcDefaultBaseURL; } ;
