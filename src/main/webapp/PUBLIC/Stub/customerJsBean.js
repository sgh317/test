window.RpcContext.beans['customerJsBean']={
		bindCustomerInfo:function(arg0,arg1,arg2,callbackObj){
		      var request={
		              functionName:'customerJsBean.bindCustomerInfo',
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
		    zsProductInfo:function(arg0,callbackObj){
		        var request={
		            functionName:'customerJsBean.zsProductInfo',
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
		     queryCustomerDetailInfo:function(callbackObj){
		        var request={
		            functionName:'customerJsBean.queryCustomerDetailInfo',
		            paramters:JSON.stringify({

		            })
		        };
		        if(callbackObj!=null){
		        	RpcContext.invoke(request,this.realBaseURL,callbackObj);
		        }else{
		             return RpcContext.invoke(request,this.realBaseURL,callbackObj);
		        }
		     },
		     queryCarDetailInfoByCarSeq:function(arg0,callbackObj){
			        var request={
				            functionName:'customerJsBean.queryCarDetailInfoByCarSeq',
				            paramters:JSON.stringify({
				            arg0:arg0
				            })
				        };
				        if(callbackObj!=null){
				        	RpcContext.invoke(request,this.realBaseURL,callbackObj);
				        }else{
				              return RpcContext.invoke(request,this.realBaseURL,callbackObj);
				        }
			 },saveOrderInfo:function(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9,callbackObj){
				 var request={
				            functionName:'customerJsBean.saveOrderInfo',
				            paramters:JSON.stringify({
				            arg0:arg0,
				            arg1:arg1,
				            arg2:arg2,
				            arg3:arg3,
				            arg4:arg4,
				            arg5:arg5,
				            arg6:arg6,
				            arg7:arg7,
				            arg8:arg8,
				            arg9:arg9
				            })
				        };
				        if(callbackObj!=null){
				        	RpcContext.invoke(request,this.realBaseURL,callbackObj);
				        }else{
				              return RpcContext.invoke(request,this.realBaseURL,callbackObj);
				        }
			 },
			 calProductWithAmount:function(arg0,arg1,callbackObj){
			        var request={
				            functionName:'customerJsBean.calProductWithAmount',
				            paramters:JSON.stringify({
				            arg0:arg0,
				            arg1:arg1
				            })
				        };
				        if(callbackObj!=null){
				        	RpcContext.invoke(request,this.realBaseURL,callbackObj);
				        }else{
				              return RpcContext.invoke(request,this.realBaseURL,callbackObj);
				        }
			 },
			 calProduct:function(arg0,callbackObj){
			        var request={
				            functionName:'customerJsBean.calProduct',
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
			 calProductWithPassenger:function(arg0,arg1,arg2,arg3,callbackObj){
			        var request={
				            functionName:'customerJsBean.calProductWithPassenger',
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
			 calProductGlass:function(arg0,arg1,arg2,callbackObj){
			        var request={
				            functionName:'customerJsBean.calProductGlass',
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
			 calProductAll:function(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9,arg10,arg11,arg12,arg13,arg14,arg15,arg16,arg17,arg18,arg19,arg20,arg21,arg22,arg23,arg24,arg25,arg26,arg27,arg28,callbackObj){
			        var request={
				            functionName:'customerJsBean.calProductAll',
				            paramters:JSON.stringify({
				            arg0:arg0,
				            arg1:arg1,
				            arg2:arg2,
				            arg3:arg3,
				            arg4:arg4,
				            arg5:arg5,
				            arg6:arg6,
				            arg7:arg7,
				            arg8:arg8,
				            arg9:arg9,
				            arg10:arg10,
				            arg11:arg11,
				            arg12:arg12,
				            arg13:arg13,
				            arg14:arg14,
				            arg15:arg15,
				            arg16:arg16,
				            arg17:arg17,
				            arg18:arg18,
				            arg19:arg19,
				            arg20:arg20,
				            arg21:arg21,
				            arg22:arg22,
				            arg23:arg23,
				            arg24:arg24,
				            arg25:arg25,
				            arg26:arg26,
				            arg27:arg27,
				            arg28:arg28
				            })
				        };
				        if(callbackObj!=null){
				        	RpcContext.invoke(request,this.realBaseURL,callbackObj);
				        }else{
				              return RpcContext.invoke(request,this.realBaseURL,callbackObj);
				        }
			 },
			 loadProductInfoList:function(callbackObj){
			        var request={
				            functionName:'customerJsBean.loadProductInfoList',
				            paramters:JSON.stringify({

				            })
				        };
				        if(callbackObj!=null){
				        	RpcContext.invoke(request,this.realBaseURL,callbackObj);
				        }else{
				             return RpcContext.invoke(request,this.realBaseURL,callbackObj);
				        }
				     },
			 saveProduct:function(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9,arg10,arg11,arg12,arg13,arg14,arg15,arg16,arg17,arg18,arg19,arg20,arg21,arg22,arg23,arg24,arg25,arg26,arg27,arg28,arg29,callbackObj){
			        var request={
				            functionName:'customerJsBean.saveProduct',
				            paramters:JSON.stringify({
				            arg0:arg0,
				            arg1:arg1,
				            arg2:arg2,
				            arg3:arg3,
				            arg4:arg4,
				            arg5:arg5,
				            arg6:arg6,
				            arg7:arg7,
				            arg8:arg8,
				            arg9:arg9,
				            arg10:arg10,
				            arg11:arg11,
				            arg12:arg12,
				            arg13:arg13,
				            arg14:arg14,
				            arg15:arg15,
				            arg16:arg16,
				            arg17:arg17,
				            arg18:arg18,
				            arg19:arg19,
				            arg20:arg20,
				            arg21:arg21,
				            arg22:arg22,
				            arg23:arg23,
				            arg24:arg24,
				            arg25:arg25,
				            arg26:arg26,
				            arg27:arg27,
				            arg28:arg28,
				            arg29:arg29
				            })
				        };
				        if(callbackObj!=null){
				        	RpcContext.invoke(request,this.realBaseURL,callbackObj);
				        }else{
				              return RpcContext.invoke(request,this.realBaseURL,callbackObj);
				        }
			 },
			 loadOrderReviewInfo:function(callbackObj){
				 var request={
						 functionName:'customerJsBean.loadOrderReviewInfo',
				            paramters:JSON.stringify({})
				 };
				 if(callbackObj!=null){
			        	RpcContext.invoke(request,this.realBaseURL,callbackObj);
			        }else{
			              return RpcContext.invoke(request,this.realBaseURL,callbackObj);
			        }
			 },
			 refreshAreaInfo:function(arg0,callbackObj){
				 var request={
						 functionName:'customerJsBean.refreshAreaInfo',
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
			 refreshNetInfo:function(arg0,callbackObj){
				 var request={
						 functionName:'customerJsBean.refreshNetInfo',
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
			 submitOrderInfo:function(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9,arg10,arg11,arg12,callbackObj){
				 var request={
						 functionName:'customerJsBean.submitOrderInfo',
				            paramters:JSON.stringify({
				            	arg0:arg0,
				            	arg1:arg1,
				            	arg2:arg2,
				            	arg3:arg3,
				            	arg4:arg4,
				            	arg5:arg5,
				            	arg6:arg6,
				            	arg7:arg7,
				            	arg8:arg8,
				            	arg9:arg9,
				            	arg10:arg10,
				            	arg11:arg11,
				            	arg12:arg12
				            	})
				 };
				 if(callbackObj!=null){
			        	RpcContext.invoke(request,this.realBaseURL,callbackObj);
			        }else{
			              return RpcContext.invoke(request,this.realBaseURL,callbackObj);
			        }
			 },
			 callPackagePrem:function(arg0,arg1,arg2,arg3,arg4,arg5,arg6,arg7,callbackObj){
				 var request={
						 functionName:'customerJsBean.callPackagePrem',
				            paramters:JSON.stringify({
				            	arg0:arg0,
				            	arg1:arg1,
				            	arg2:arg2,
				            	arg3:arg3,
				            	arg4:arg4,
				            	arg5:arg5,
				            	arg6:arg6,
				            	arg7:arg7
				            	})
				 };
				 if(callbackObj!=null){
			        	RpcContext.invoke(request,this.realBaseURL,callbackObj);
			        }else{
			              return RpcContext.invoke(request,this.realBaseURL,callbackObj);
			        }
			 },
			 getCarDetail:function(arg0,callbackObj){
				 var request={
						 functionName:'customerJsBean.getCarDetail',
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
			 submitHYX:function(arg0,arg1,callbackObj){
				 var request={
						 functionName:'customerJsBean.submitHYX',
				            paramters:JSON.stringify({
				            	arg0:arg0,
				            	arg1:arg1
				            	})
				 };
				 if(callbackObj!=null){
			        	RpcContext.invoke(request,this.realBaseURL,callbackObj);
			        }else{
			              return RpcContext.invoke(request,this.realBaseURL,callbackObj);
			        }
			 },
			 loadZSInfo:function(callbackObj){
				 var request={
						 functionName:'customerJsBean.loadZSInfo',
				            paramters:JSON.stringify({
				            	})
				 };
				 if(callbackObj!=null){
			        	RpcContext.invoke(request,this.realBaseURL,callbackObj);
			        }else{
			              return RpcContext.invoke(request,this.realBaseURL,callbackObj);
			        }
			 },
			 paySuccess:function(arg0,callbackObj){
				 var request={
						 functionName:'customerJsBean.paySuccess',
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
			 delBind:function(callbackObj){
				 var request={
						 functionName:'customerJsBean.delBind',
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
if(document.location.href.startWith('http')){ window.RpcContext.beans['customerJsBean'].realBaseURL='.'; } else {
window.RpcContext.beans['customerJsBean'].realBaseURL=window._RpcDefaultBaseURL; } ;