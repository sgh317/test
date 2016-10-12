window.RpcContext.beans['MemberJsBean']={
	addMember:function(callbackObj){
      var request={
          functionName:'MemberJsBean.addMember',
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
};
if(document.location.href.startWith('http')){ window.RpcContext.beans['memberJsBean'].realBaseURL='.'; } else {
window.RpcContext.beans['memberJsBean'].realBaseURL=window._RpcDefaultBaseURL; } ;