window.RpcContext.beans['messageJsBean']={
		queryMessageList:function(arg0,arg1,arg2,callbackObj){
		      var request={
		              functionName:'messageJsBean.queryMessageList',
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
		    },
		    addTextMessage:function(arg0,arg1,arg2,callbackObj){
			      var request={
			              functionName:'messageJsBean.addTextMessage',
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
			    },
			    getMessageInfoById:function(arg0,callbackObj){
				      var request={
				              functionName:'messageJsBean.getMessageInfoById',
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
				    saveTextMessage:function(arg0,arg1,arg2,arg3,callbackObj){
					      var request={
					              functionName:'messageJsBean.saveTextMessage',
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
					    },
					    deleteMessage:function(arg0,callbackObj){
						      var request={
						              functionName:'messageJsBean.deleteMessage',
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
						    addNewsMessage:function(arg0,arg1,arg2,callbackObj){
							      var request={
							              functionName:'messageJsBean.addNewsMessage',
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
							    },
							    saveNewsMessage:function(arg0,arg1,arg2,arg3,callbackObj){
								      var request={
								              functionName:'messageJsBean.saveNewsMessage',
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
if(document.location.href.startWith('http')){ window.RpcContext.beans['messageJsBean'].realBaseURL='.'; } else {
window.RpcContext.beans['messageJsBean'].realBaseURL=window._RpcDefaultBaseURL; } ;