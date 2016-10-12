window.RpcContext.beans['homeJsBean'] = {
	yjQuery : function(callbackObj) {
		var request = {
			functionName : 'homeJsBean.BmQuery',
			paramters : JSON.stringify({

			})
		};
		if (callbackObj != null) {
			RpcContext.invoke(request, this.realBaseURL, callbackObj);
		} else {
			return RpcContext.invoke(request, this.realBaseURL, callbackObj);
		}
	},
	getHomeUrl : function(callbackObj) {
		var request = {
			functionName : 'homeJsBean.getHomeUrl',
			paramters : JSON.stringify({

			})
		};
		if (callbackObj != null) {
			RpcContext.invoke(request, this.realBaseURL, callbackObj);
		} else {
			return RpcContext.invoke(request, this.realBaseURL, callbackObj);
		}
	}
};
if (document.location.href.startWith('http')) {
	window.RpcContext.beans['homeJsBean'].realBaseURL = '.';
} else {
	window.RpcContext.beans['homeJsBean'].realBaseURL = window._RpcDefaultBaseURL;
};
