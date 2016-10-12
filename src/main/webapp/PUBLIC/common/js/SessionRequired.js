function _GetCookie(name) 
{ 
    var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
 
    if(arr=document.cookie.match(reg))
 
        return unescape(arr[2]); 
    else 
        return null; 
} ;


function _CallTestSession()
{
	     var request={
	          functionName:'_CallTestSession.test'
	      };
	     
	     
	     if(document.location.href.startWith('http')){
	    	 RpcContext.invoke(request,".",{});
	     }
	     else
	     {
	    	 RpcContext.invoke(request,window._RpcDefaultBaseURL,{});
	     }
	    
}
;


try{
	
	var _USTK=_GetCookie('_USTK');
	if(_USTK==null)
	{
		_CallTestSession();
	}
	else 
	{
		var tokens=_USTK.split("|");
		var sd=new Date(parseInt(tokens[0]));
		var sd2=new Date();
		if(sd.getTime()<sd2.getTime())
		{
			_CallTestSession();
		}
	}
}catch(e){
	
};