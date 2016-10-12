$(document).ready(function(){
	
	
	setTimeout(function(){
	
		RpcContext.getBean('queryPolicyJsBean').queryPolicyNo(
				
				{
					onSucess : function(result){
					
						
						var res = eval('('+result+')');
						var policyList = res.orderList;
						if(policyList!=null&&policyList.length>0){
							
							var html = "";
							for(var i=0; i < policyList.length; i++)
							{
								var pid = policyList[i];
								
							html +="<div class='form-box list-block white'>";	
							html +="<ul class='product-intro'>";	 
							html +="<li class='item-content'>";                    
							html +="<div class='item-inner'>";    	
							html +="<div class='item-title'>";           
							
							if(pid.policyNo==null||pid.policyNo==''){
								html +="<span class='start-time'>保单号：<b>保单号生成中，请稍后再试</b></span>";
							}else{
								html +="<span class='start-time'>保单号：<b>"+pid.policyNo+"</b></span>";
							}
							                
							html +="</div>";                                                 
							html +="<div class='item-title' style='float: right;'>";                
							html +="<a ontouchstart=\"toPolicyCarDetail('"+pid.policyNo+"')\"><span class='xiangqing'>详情</span></a>";
							html +="</div>";
							html +="</div>";                                             
							html +="</li>";					
							html +="<li class='item-content'>";
							html +="<div class='item-inner'>";
							html +="<div class='item-title'>";
							html +="<span class='start-time'>险种名称：<em style='color: crimson;'>"+pid.riskName+"</em></span>";
							html +="<span class='start-time'>保单状态：有效</span>";
							html +="</div>";
							html +="</div>";
							html +="</li>";
							html +="</ul>";
							html +="</div>";       		
								
							}
							
							$("#policyListDiv").html(html);					            
			            
						}else{
							
							$("#policyListDiv").html("<div class='form-box list-block white'>对不起，没有你的保单</div>");	
							
						}
						

					},
					
					
					onError : function(err) {
		                alert(err);
					}
				
				});		
	}, 500);
});

function toPolicyCarDetail(policyNo){
	
	 if(policyNo !=null && policyNo !=""){
		 sessionStorage.setItem("policyNo",policyNo);

		 window.location.replace("./policyCarDetail.html");
	 }else{
		 alert('您的保单号还未生成，请稍后再查看，谢谢！');
	 }
	
	
}

