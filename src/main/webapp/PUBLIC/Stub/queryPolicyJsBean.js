window.RpcContext.beans['queryPolicyJsBean']={
   queryPolicyNo:function(callbackObj){
      var request={
          functionName:'queryPolicyJsBean.queryPolicyNo',
          paramters:JSON.stringify({
        	  
          })
      };
      if(callbackObj!=null){
          RpcContext.invoke(request,this.realBaseURL,callbackObj);
    }else{
          return RpcContext.invoke(request,this.realBaseURL,callbackObj);
    }
   },
   queryPolicyDetail:function(arg0,callbackObj){
	      var request={
	          functionName:'queryPolicyJsBean.queryPolicyDetail',
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
	   querySocre:function(callbackObj){
	      var request={
	          functionName:'queryPolicyJsBean.querySocre',
	          paramters:JSON.stringify({

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
if(document.location.href.startWith('http')){ window.RpcContext.beans['queryPolicyJsBean'].realBaseURL='.'; } else {
window.RpcContext.beans['queryPolicyJsBean'].realBaseURL=window._RpcDefaultBaseURL; } ;