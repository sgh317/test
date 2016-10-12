function invokeGetJSON(url, data,onSucess,onError)
{
		$.ajax({
			type: 'GET',
			url: url,
			data: data,
			success: function(rtd){ if(onSucess!=null) { onSucess(rtd);} },
			dataType : "jsonp",
			async:false,
			cache:false,
			error:function(XMLHttpRequest, textStatus, errorThrown){ if(onError!=null){onError(errorThrown); } }
		});
}

function login(username,password,validateToken,onSucess,onError)
{
	var data = {_AUTH_USER_NAME:username,_PASSWORD:password,_MOBILE:'Y',_VALIDATE_CODE:validateToken};
	invokeGetJSON(window._LoginURL+'/SSO/AuthAction',data,function(result){
		if(result.status){
			onSucess(result.returnObject);
		}else{
			onError(result.message);
		}
		
	},function(errorThrown){
		var retMsg = "系统错误";
		onError(retMsg);
	});
	
	sessionStorage.setItem('_firstLogin','1')
}

function MobileJumpAction()
{
	invokeGetJSON(window._LoginURL+'/SSO/AjaxGetMobileJumpAction',data,function(result){
		if(result.status){
			if(result.returnObject!=null)
			{
				window.location.href = result.returnObject;
			}
			 
		}
	},function(errorThrown){
	
	});
}



