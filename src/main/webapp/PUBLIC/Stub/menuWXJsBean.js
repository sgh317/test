window.RpcContext.beans['wxMenuService']={
		queryWXMenu:function(callbackObj){
		      var request={
		              functionName:'wxMenuService.bindCustomerInfo',
		              paramters:JSON.stringify({})
		          };
		          if(callbackObj!=null){
		                RpcContext.invoke(request,this.realBaseURL,callbackObj);
		          }else{
		                return RpcContext.invoke(request,this.realBaseURL,callbackObj);
		          }
		    },
		    saveWXMenu:function(arg0,callbackObj){
			      var request={
			              functionName:'wxMenuService.saveWXMenu',
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
			    deployWXMenu:function(callbackObj){
				      var request={
				              functionName:'wxMenuService.deployWXMenu',
				              paramters:JSON.stringify({})
				          };
				          if(callbackObj!=null){
				                RpcContext.invoke(request,this.realBaseURL,callbackObj);
				          }else{
				                return RpcContext.invoke(request,this.realBaseURL,callbackObj);
				          }
				    }
}
;
if(document.location.href.startWith('http')){ window.RpcContext.beans['wxMenuService'].realBaseURL='.'; } else {
window.RpcContext.beans['wxMenuService'].realBaseURL=window._RpcDefaultBaseURL; } ;