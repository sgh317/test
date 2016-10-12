var _BROWSER={

		versions:function(){
		var u = navigator.userAgent, app = navigator.appVersion;

		return {
		trident: u.indexOf('Trident') > -1, //IE内核
		presto: u.indexOf('Presto') > -1, //opera内核
		webKit: u.indexOf('AppleWebKit') > -1, //苹果、谷歌内核
		gecko: u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1, //火狐内核
		mobile: !!u.match(/AppleWebKit.*Mobile.*/)||!!u.match(/AppleWebKit/), //是否为移动终端
		ios: !!u.match(/(i[^;]+\;(U;)? CPU.+Mac OS X)/), //ios终端
		android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, //android终端或者uc浏览器
		iPhone: u.indexOf('iPhone') > -1 || u.indexOf('Mac') > -1, //是否为iPhone或者QQHD浏览器
		iPad: u.indexOf('iPad') > -1, //是否iPad
		webApp: u.indexOf('Safari') == -1 //是否web应该程序，没有头部与底部
		};
		}(),
		language:(navigator.browserLanguage || navigator.language).toLowerCase()
		}

if($._WAPPERED_AJAX==null)
{
	$._WAPPERED_AJAX=true;
	$._ORIG_AJAX=$.ajax;
	$._AJAX_SETTING_DB={};
	$._AJAX_SETTING_DB_INDEX=1;
	
	$.ajaxMobile=function(origSettings)
	{
		if(origSettings._REQ_PROXY==true)
		{
			origSettings._SESSION_INVALIDATE=false;
			$._ORIG_AJAX(origSettings);
			return ;
		}
		
		origSettings._ORIG_beforeSend=origSettings.beforeSend;
   		origSettings._ORIG_dataFilter=origSettings.dataFilter;
   		origSettings._ORIG_success=origSettings.success;
   		origSettings._ORIG_error=origSettings.error;
  		origSettings._SESSION_INVALIDATE=false;
   		origSettings._REQ_PROXY=true;
		
		origSettings.beforeSend=function(XMLHttpRequest)
		{
      		XMLHttpRequest.setRequestHeader("_AJAX", "TRUE");
      		if(origSettings._ORIG_beforeSend!=null)
     		{
        		 origSettings._ORIG_beforeSend(XMLHttpRequest);
      		} 
		
   		};
		
		origSettings.dataFilter=function(data, type)
   		{ 
     		if('_SESSION_INVALIDATE'==data)
     		{
				 origSettings._SESSION_INVALIDATE=true;
       			 handSessionInvalidate(origSettings); 
     		}
     		else
     		{
       			if(origSettings._ORIG_dataFilter!=null)
      			{
       				 return origSettings._ORIG_dataFilter(data, type);
       			}
       			else
       			{
        			 return data;
       			}
     		}
     
  		};
		
		 origSettings.success=function(data, textStatus)
		 {
    		 if(origSettings._SESSION_INVALIDATE==false)
     		{
        		 if(origSettings._ORIG_success!=null)
       			 {
        			 
        			 if(data!=null&&data.rt=='_BINDING_REQUIRED')
        			 {
        				 window.location.href="./../Binding/index.html?random="+ Math.random();;
        				 return ;
        			 }
          			 origSettings._ORIG_success(data, textStatus);
        		 }
        		 
     		}
  		 };
  		 
  		 origSettings.error=function(XMLHttpRequest, textStatus, errorThrown)
  		 {
  		  		if(origSettings._SESSION_INVALIDATE==false)
  		  		{
  		  			if(origSettings._ORIG_error!=null)
  		 			{
  		 				origSettings._ORIG_error(XMLHttpRequest, textStatus, errorThrown);
  		 			}
  		  		}
  		 		
  		 };
		
		
		 $._ORIG_AJAX(origSettings);
	}
	
};

function invokeGetJSON(url, data,onSucess,onError)
{
	    
	$.ajaxMobile({
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

function handSessionInvalidate(origSettings)
{
	invokeGetJSON(
	origSettings.serverURL+'/LOGIN.CMD?_CMD=_GET_SRV_INFO&format=json&callback=?',
	{},
	function(Response)
	{
		HandSsoLogin(origSettings,Response.returnObject);
	},
	function(error)
	{
		
		toMobileLoginPage();
	}
	);
}


function HandSsoLogin(origSettings,BIZ_SRV_INFO)
{
	invokeGetJSON(
	BIZ_SRV_INFO._GET_AJAX_LINK_PARAM_URL,
	{
	},
	function(Response)
	{
		invokeGetJSON(
		  BIZ_SRV_INFO._AJAX_LINK_URL,
		  {
			  _AJAX_LINK_REQ_PARAM: Response.returnObject,
			  _WindowLocation:window.location.href
		  },
		  function(Response)
		  {
			if(Response.status)
			{
				invokeGetJSON(
				BIZ_SRV_INFO._AJAX_LINK_SUCESS_RETURN_URL,
				{
					_AJAX_LINK_SUCESS_RETURN_PARAM:Response.returnObject
				},
				function(Response)
				{
					if(Response.status)
					{
						$.ajaxMobile(origSettings);
					}
					else
					{
						alert('When Recover Session Find Error:'+Response.message);
					}
				},
				function(error)
				{
					toMobileLoginPage();
				}
				);
			}
			else
			{
				toMobileLoginPage();
			}
		},
		function(error)
		{
			toMobileLoginPage();
		}
		);
	},
	function(error)
	{
		toMobileLoginPage();
	}
	);
}

function toMobileLoginPage()
{
	 var href=window.location.href;
	 href=href.substr(0,href.lastIndexOf('/'));
	 href=href.substr(0,href.lastIndexOf('/'));
	 href=href+"/Login/index.html?random="+ Math.random();
	 if(window._DEVICE=='WEB')
	 {
		 window.location.href = window._LoginURL+'/SSO/MobileLoginAction?_MOBILE=Y&DEVICE='+window._DEVICE+'&_MOBILE_JUMP='+encodeURI(window.location.href)+"&jump2Lian="+encodeURI(href);
	 }
	 else
	 {
		 window.location.href=href;
	 }
}




		