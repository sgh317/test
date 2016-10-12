
//保单列表模板
function getPolicyTmp(value){
	var data = [];
	data = value;				
	var list = "<% for(var i = 0; i < list.length; i++) { %>" +
	"<% var item = list[i] %>" +
	"<tr onclick=getPolicyDetail('<%=item['PolicyNo']%>','<%=item['sign']%>')>" +
	"<td><span class='texred'><%=item['RiskName']%></span><br/><span style='color:gray;font-size:12px;'><%=item['PolicyNo']%></span></td>"+
	"<td style='padding-top:13px; text-align:center;'><a class='<%=statusClass(item['PolStatus'])%>'><%=item['PolStatus']%></a></td>" +
	"</tr>" +
	"<% } %>";
	
	var datalist = {list:data};
	
	var html =  _.template(list,datalist);
	return html;
}

//万能险
function getWanNengTmp(value){
				var data = [];
				data = value;				
				var list = "<% for(var i = 0; i < list.length; i++) { %>" +
				"<% var item = list[i] %>" +
				"<tr>" +
				"<td><span class='texred'><%=item['RiskName']%></span><br/><span style='color:gray;font-size:12px;'><%=item['PolicyNo']%></span></td>"+
				"<td><span class='texred'><%=item['PolicyValue']%></span><br/><span style='color:gray;font-size:12px;'><%=item['Rate']%></span></td>"+
				" </tr>" +
				"<% } %>";
				
				var datalist = {list:data};
				
				var html =  _.template(list,datalist);
				return html;
			}

function GetArgsFromHref(sHref, sArgName)
{
    var args  = sHref.split("?");
    var retval = "";
    
    if(args[0] == sHref) /*参数为空*/
    {
         return retval; /*无需做任何处理*/
    }  
    var str = args[1];
    args = str.split("&");
    for(var i = 0; i < args.length; i ++)
    {
        str = args[i];
        var arg = str.split("=");
        if(arg.length <= 1) continue;
        if(arg[0] == sArgName) retval = arg[1]; 
    }
    return retval;
}

function getPolicyDetail(policyNo,sign)
{
	sessionStorage.queryPolicyNo = policyNo;
	sessionStorage.querySign = sign;
	
	window.location.href  = "detail.html";
}

//被保人信息模板
function getInsListTmp(data){
	var list = "<% for(var i = 0; i < list.length; i++) { %>" +
	"<% var item = list[i] %>" +
	"<table class='table table-bordered table-striped'>" +
	"<tr style='border-top:2px solid #BC111A;'><td class='tabtdw'>姓名</td><td><%=item['Name']%></td></tr>"+
	"<tr><td>性别</td><td><%=item['Sex']%></td></tr>"+
	"<tr><td>证件类型</td><td><%=item['IDType']%></td></tr>"+
	"<tr><td>证件号码</td><td><%=item['ID']%></td></tr>"+
	"<tr><td>生日</td><td><%=item['Birthday']%></td></tr>"+
	" </tr>" +
	"</table>"+
	"<% } %>";
	
	var datalist = {list:data};
	
	var html =  _.template(list,datalist);
	return html;
}

//受益人信息模板
function getBnfListTmp(data){
	var list = "<% for(var i = 0; i < list.length; i++) { %>" +
	"<% var item = list[i] %>" +
	"<table class='table table-bordered table-striped'>" +
	"<tr style='border-top:2px solid #BC111A;'><td class='tabtdw'>姓名</td><td><%=item['Name']%></td></tr>"+
	"<tr><td>受益人类型</td><td><%=item['BnfType']%></td></tr>"+
	"<tr><td>受益顺序</td><td><%=item['BnfGrade']%></td></tr>"+
	"<tr><td>受益比例</td><td><%=item['Scale']%></td></tr>"+
	"<tr><td>性别</td><td><%=item['Sex']%></td></tr>"+
	"<tr><td>证件类型</td><td><%=item['IDType']%></td></tr>"+
	"<tr><td>证件号码</td><td><%=item['ID']%></td></tr>"+
	" </tr>" +
	"</table>"+
	"<% } %>";
	
	var datalist = {list:data};
	
	var html =  _.template(list,datalist);
	return html;
}

//附加险信息模板
function getAddInfoListTmp(data){
	var list = "<% for(var i = 0; i < list.length; i++) { %>" +
	"<% var item = list[i] %>" +
	"<table class='table table-bordered table-striped'>" +
	"<tr style='border-top:2px solid #BC111A;'><td class='tabtdw'>险种名称</td><td><%=item['RiskName']%></td></tr>"+
	"<tr><td>基本保险金额/份数/档次</td><td><%=item['Amt']%></td></tr>"+
	"<tr><td>生效日期</td><td><%=item['EffectiveDate']%></td></tr>"+
	"<tr><td>终止日期</td><td><%=item['EndDate']%></td></tr>"+
	"<tr><td>期交/趸交保险费（元）</td><td><%=item['Prem']%></td></tr>"+
	"<tr><td>险种状态</td><td><%=item['RiskStatus']%></td></tr>"+
	"</tr>" +
	"</table>"+
	"<% } %>";
	
	var datalist = {list:data};
	
	var html =  _.template(list,datalist);
	return html;
}

//保障信息模板
function getRiskInfoListTmp(data){
	var list = "<% for(var i = 0; i < list.length; i++) { %>" +
	"<% var item = list[i] %>" +
	"<table class='table table-bordered table-striped'>" +
	"<tr style='border-top:2px solid #BC111A;'><td  class='tabtdw'>险种名称</td><td><%=item['RiskName']%></td></tr>"+
	"<tr><td>责任名称</td><td><%=item['LiabilityName']%></td></tr>"+
	"<tr><td>保额（元）</td><td><%=item['Amt']%></td></tr>"+
	"</tr>" +
	"</table>"+
	"<% } %>";
	
	var datalist = {list:data};
	
	var html =  _.template(list,datalist);
	return html;
}

function statusClass(polStatus){
	if(polStatus=="停效"){
		return 'btn btn-warning  btn-sm';
	}else if(polStatus=="终止"){
		return 'btn btn-danger  btn-sm';
	}else{
		return 'btn btn-success  btn-sm';
	}
}