window.RpcContext.beans['BmQueryJsBean']={
   BmQuery:function(callbackObj){
      var request={
          functionName:'BmQueryJsBean.BmQuery',
          paramters:JSON.stringify({

          })
      };
      if(callbackObj!=null){
            RpcContext.invoke(request,this.realBaseURL,callbackObj);
      }else{
            return RpcContext.invoke(request,this.realBaseURL,callbackObj);
      }
   },
}
;
  if(document.location.href.startWith('http')){ window.RpcContext.beans['BmQueryJsBean'].realBaseURL='.'; } else {
  window.RpcContext.beans['BmQueryJsBean'].realBaseURL=window._RpcDefaultBaseURL; } ;
