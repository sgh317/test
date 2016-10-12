window.$ && $(document).ready(function(){
	
	
	
	RpcContext.getBean('queryPolicyJsBean').querySocre(
			
		{
			onSucess : function(result){
				
				var res = eval('('+result+')');
				
				var score = res.score;
				var scoreList = res.scoreList;
				
				if(score!="" && score!=null){
					
					$("#ScoreDiv").html("当前积分："+score);					
				}
				
				if(scoreList!=null && scoreList.length>0){
				
											
				
					$("#ScoreListDiv").html( _.template($("#scoreListDivTemplate").html(), {datas:scoreList}));
					
				}else{
					
					$("#ScoreListDiv").html("无积分消费记录,谢谢。");
					
				}

			},
			
			
			onError : function(err) {
                alert(err);
			}
		
		});
	
	
});