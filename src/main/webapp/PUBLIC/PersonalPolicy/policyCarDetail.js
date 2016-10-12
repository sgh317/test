window.$ && $(document).ready(function(){
	 var policyNo= sessionStorage.getItem("policyNo");
	
	
	RpcContext.getBean('queryPolicyJsBean').queryPolicyDetail(policyNo,
			
		{
			onSucess : function(result){
				
				var res = eval('('+result+')');
				
				var orderId = res.orderInfo.orderId;
				
				if(orderId!=null && orderId.length>0){
					var policyDetail = res.orderInfo;
											
					$("#policyDetailDiv").html( _.template($("#policyDetailTemplate").html(), {datas:policyDetail})); 
					
				}else{
					$("#policyDetailDiv").html("无保单详细信息");
					
				}

			},
			
			
			onError : function(err) {
                alert(err);
			}
		
		});
	
	
});