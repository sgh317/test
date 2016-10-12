if($._WAPPERED_AJAX==null)
{
	$._WAPPERED_AJAX=true;
	$._ORIG_AJAX=$.ajax;
	$._AJAX_SETTING_DB={};
	$._AJAX_SETTING_DB_INDEX=1;
	
	$.AjaxSSOSupport=function(origSettings)
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
				 window.location.replace("/WGQC"); 
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


function getTopWindow(win)
{
	if(win.parent==null||win.parent==win)
	{
		return win;
	}
	return getTopWindow(win.parent);
}


