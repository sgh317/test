var BasicListUtil = 
{
	initListToSelect : function(selObj, listKey, hasBlank)
	{
		var listJ = sessionStorage.getItem(listKey);
		listJ = listJ == null ? "" : listJ;
		listJ = listJ.split(";");
		if(selObj != null && listJ != null && listJ.length > 0)
		{
			$.each(selObj,function(){
				var selO = $(this);
				selO.html('');
				if(hasBlank)
				{
					selObj.append("<option value=\"\">-请选择-</option>");
				}
				$.each(listJ,function(){
					var kv = this.split(",");
					if(kv.length == 2 && kv[0] != null && kv[0] != "" && kv[1] != null && kv[1] != "")
					{
						selObj.append("<option value=\""+kv[0]+"\">"+kv[1]+"</option>");
					}
				});
			});
		}
	},
	initBasicProperty : function()
	{
		if(!sessionStorage.getItem("_basic_list_is_init"))
		{
			sessionStorage.setItem("_sexList", "0,男;1,女;");
			sessionStorage.setItem("_relation_0_List", "01,丈夫;03,父亲;05,儿子;19,公公;21,岳父;24,女婿;");
			sessionStorage.setItem("_relation_1_List", "02,妻子;04,母亲;06,女儿;20,婆婆;22,岳母;23,儿媳;");
			sessionStorage.setItem("_relation_2_List", "'',请选择;00,本人;03,父亲;04,母亲;");
			sessionStorage.setItem("_orderStatusList", "000,待提交;001,待自核;100,待支付;102,已支付;200,处理中;998,已取消;999,已完成;900,已删除;101,支付中;997,支付失败;");
			sessionStorage.setItem("_wx_appnt_id_type_List", "0,身份证;2,军人证（军官证）;D,警官证;A,士兵证;");
			sessionStorage.setItem("_wx_insure_id_type_List", "0,身份证;4,户口本;7,出生证;2,军人证（军官证）;D,警官证;A,士兵证;");
			sessionStorage.setItem("_xpx_insure_id_type_List", "0,身份证;4,户口本;7,出生证;");
			sessionStorage.setItem("_lax_appnt_id_type_List", "0,身份证;4,户口本;2,军人证（军官证）;D,警官证;A,士兵证;");
			sessionStorage.setItem("_bank_account_type", "0,卡;1,存折");
			sessionStorage.setItem("_pay_Interval_type", "0,趸交;1,月交;3,季交;6,半年交;12,年交;-1,不定期交;");
			sessionStorage.setItem("_bank_name_type", "'',请选择;0201,中国工商银行;0301,中国银行;0401,中国农业银行;0501,中国建设银行;0601,中国交通银行;0801,招商银行;0901,中国民生银行;1001,中国邮政储蓄银行;1101,深发银行;1201,中信银行;1301,兴业银行;");
			sessionStorage.setItem("_pay_type", "1,现金;2,现金支票;3,转账支票;4,银行转账;5,内部转帐;6,银行托收;9,网银;A,邮保通;B,银保通;C,虚拟收费;P,POS收费;Z,在线支付;");
		}
	},
	getDescByCode : function(basicKey, code)
	{
		var desc = "";
		var l = sessionStorage.getItem(basicKey);
		if(l != null && l != "")
		{
			var arr = l.split(";");
			$.each(arr, function(){
				var a = this.split(",");
				if(a[0] == code)
				{
					desc = a[1];
				}
			});
		}
		return desc;
	}
};


/*******************身份证相关校验 begin********************/
var IdCard = {};
IdCard.aCity = {11 : "北京", 12 : "天津", 13 : "河北", 14 : "山西", 15 : "内蒙古", 21 : "辽宁", 22 : "吉林", 23 : "黑龙江", 31 : "上海",
				 32 : "江苏", 33 : "浙江", 34 : "安徽", 35 : "福建", 36 : "江西", 37 : "山东", 41 : "河南", 42 : "湖北 ", 43 : "湖南",
				 44 : "广东", 45 : "广西", 46 : "海南", 50 : "重庆", 51 : "四川", 52 : "贵州", 53 : "云南", 54 : "西藏", 61 : "陕西",
				 62 : "甘肃", 63 : "青海", 64 : "宁夏", 65 : "新疆", 71 : "台湾", 81 : "香港", 82 : "澳门", 91 : "国外" };
IdCard.birth = null;
IdCard.sex   = null;

/**** 校验身份证号码有效性 ****/
IdCard.isIdCardNo = function(value)
{
	value = value.toUpperCase();
	// 身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X。
	if (!(/^\d{17}([0-9]|X|x)$/.test(value))) {
		console.log('身份证号长度不对，或者号码不符合规定！15位号码应全为数字，18位号码末位可以为数字或X。');
		return false;
	}
	// 下面分别分析出生日期和校验位
	var len = value.length; 
	if (len == 18) {
		re = new RegExp(/^(\d{2})(\d{4})(\d{4})(\d{2})(\d{2})(\d{3})([0-9]|X|x)$/);
		var arrSplit = value.match(re);
		if (this.aCity[parseInt(arrSplit[1])] == null) {
			console.log("18位身份证的地区非法");
			return false;
		}
		// 检查生日日期是否正确
		var dtmBirth = new Date(arrSplit[3] + "/" + arrSplit[4] + "/" + arrSplit[5]);
		var bGoodDay;
		bGoodDay = (dtmBirth.getFullYear() == Number(arrSplit[3])) && ((dtmBirth.getMonth() + 1) == Number(arrSplit[4])) && (dtmBirth.getDate() == Number(arrSplit[5]));
		if (!bGoodDay) {
			console.log('18位身份证的生日非法');
			return false;
		} else {
			// 检验18位身份证的校验码是否正确。
			// 校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。
			var valnum;
			var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2);
			var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2');
			var nTemp = 0, i;
			for (i = 0; i < 17; i++) {
				nTemp += value.substr(i, 1) * arrInt[i];
			}
			valnum = arrCh[nTemp % 11];
			if (valnum != value.substr(17, 1)) {
				console.log('18位身份证的校验码不正确！末位应为：' + valnum);
				return false;
			}
		}
//		this.birth = dtmBirth.getFullYear() + "-" + (dtmBirth.getMonth() + 1) + "-" + dtmBirth.getDate();
		this.birth = arrSplit[3] + "-" + arrSplit[4] + "-" + arrSplit[5];
		this.sex = value.substr(16, 1) % 2 ? "男" : "女";
	}

	return true;
};

/**** 校验生日是否与身份证号符合 ****/
IdCard.vBirthday = function(icV, birV, separator)
{
	separator = trim(separator) == "" ? "-" : trim(separator);
	icV = trim(icV);
	var len = icV.length;
	
	if (len == 18) {
		re = new RegExp(/^(\d{2})(\d{4})(\d{4})(\d{2})(\d{2})(\d{3})([0-9]|X|x)$/);
		var arrSplit = icV.match(re);
		// 检查生日日期是否正确
		var dtmBirth = birV.split(separator);
		this.birth = new Date(arrSplit[3] + "/" + arrSplit[4] + "/" + arrSplit[5]);
		var bGoodDay;
		bGoodDay = (Number(dtmBirth[0]) == Number(arrSplit[3])) && (Number(dtmBirth[1]) == Number(arrSplit[4])) && (Number(dtmBirth[2]) == Number(arrSplit[5]));
		if (bGoodDay) {
			return true;
		}
	}

	return false;
};

/**** 验证身份证性别[汉字性别] ****/
IdCard.vSex = function(icV, sexV)
{
	icV = trim(icV);
	var len = icV.length; 
	var icSex = "";
	if (len == 18) 
	{
		var icSex = icV.substr(16, 1) % 2 ? "男" : "女";
	}

	this.sex = icSex;
	if(sexV == icSex)
	{
		return true;
	}
	else
	{
		return false;
	}
};
/*******************身份证相关校验 end********************/

/*******************根据出生日期算出年龄 begin********************/
var BirthId = {};
BirthId.Age =function(value){
	//var str=value.toString()
	var currentDate = new Date();

	var currentYear = parseInt(currentDate.getFullYear());
	var currentMonth = parseInt(currentDate.getMonth()+1);
	var currentDay = parseInt(currentDate.getDate());
	var clientYear = value.substring(0,4);
	var clientMonth = value.substring(5,7);
	var clientDay = value.substring(8,10);
	if(currentMonth>clientMonth){
		return currentYear-clientYear;
	}else if(currentMonth<clientMonth){
		return currentYear-clientYear-1;
	}else{
		if(currentDay>=clientDay)
			return currentYear-clientYear;
		else
			return currentYear-clientYear-1;
	}
}
BirthId.Aged =function(value){
	//var str=value.toString()
	var currentDate = new Date();

	var currentYear = parseInt(currentDate.getFullYear());
	var currentMonth = parseInt(currentDate.getMonth()+1);
	var currentDay = parseInt(currentDate.getDate());
	var clientYear = value.substring(0,4);
	var clientMonth = value.substring(4,6);
	var clientDay = value.substring(6,8);
	if(currentMonth>clientMonth){
		return currentYear-clientYear;
	}else if(currentMonth<clientMonth){
		return currentYear-clientYear-1;
	}else{
		if(currentDay>=clientDay)
			return currentYear-clientYear;
		else
			return currentYear-clientYear-1;
	}
}
/*******************根据出生日期算出年龄 end********************/

/*******************界面规则校验相关 begin*********************/
var Validate = {};
Validate.msg = new Array();

/**** 获取当前存放某类总校验信息 ****/
Validate.getMsgByIndex = function(ind)
{
	var index = 0;
	ind = this.trim(""+ind);
	if(ind != null && ind != "" && !isNaN(ind))
	{
		index = parseInt(ind);
	}
	
	return this.msg[index] == null ? "" : this.msg[index];
};

/**** 存放某类校验信息 ****/
Validate.setMsgByIndex = function(ind, msg)
{
	var index = 0;
	ind = this.trim(""+ind);
	if(ind != null && ind != "" && !isNaN(ind))
	{
		index = parseInt(ind);
	}
	
	this.msg[index] = ""+msg;
};

/**** 去除前后空格 ****/
Validate.trim = function(str){
	if(str != null && str != "")
		return str.replace(/(^\s*)|(\s*$)/g, "");
	else
		return "";
};

/**** 通过条件字符串整理成条件数据数组 "10,'aa',dd" ===> [10, aa, dd] ****/
Validate.getPArrByStr = function(params){
	var pArr = new Array();
	params = this.trim(params);
	if(params == "")
		return pArr;
	
	pArr = params.split(",");
	for(var i = 0; i < pArr.length; i++)
	{
		var p = this.trim(pArr[i]);
		p = this.trim(p.substring(p.indexOf('\'')+1, p.lastIndexOf('\'') <= 0 ? p.length : p.lastIndexOf('\'')));
		pArr[i] = p;
	}
	return pArr;
};
/**** 通过条件字符串整理成条件数据数组,若传递过来的为this，则将当前input对象作为参数 "10,'aa',dd" ===> [10, aa, dd] ****/
Validate.getPArrByStrWithObj = function(params, obj){
	var pArr = new Array();
	params = this.trim(params);
	if(params == "")
		return pArr;
	
	pArr = params.split(",");
	for(var i = 0; i < pArr.length; i++)
	{
		var p = this.trim(pArr[i]);
		p = this.trim(p.substring(p.indexOf('\'')+1, p.lastIndexOf('\'') <= 0 ? p.length : p.lastIndexOf('\'')));
		if(p == "this")
		{
			pArr[i] = obj;
		}
		else
		{
			pArr[i] = p;
		}
	}
	return pArr;
};


/************************************规则校验*****************************************/

/**** 是否为合法Email地址[email] ****/
Validate.isEmail = function(value) {
    if(value.search(/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/) == -1)
        return false;
    else
        return true;
};

/**** 是否为合法Email地址前缀[prefixemail] ****/
Validate.isEmailPrefix = function(value) {
    if(value.search(/^\w+([-+.]\w+)*$/) == -1)
        return false;
    else
        return true;
};

/**** 是否为合法Email地址后缀[suffixemail] ****/
Validate.isEmailSuffix = function(value) {
    if(value.search(/^\w+([-.]\w+)*\.\w+([-.]\w+)*$/) == -1)
        return false;
    else
        return true;
};

/**** 是否为合法的国内电话号码[telphone] ****/
Validate.isTelphone = function(value) {
//    if(value.search(/^(\d{3}-\d{8}|\d{4}-\d{7}|\d{4}-\d{8})$/) == -1)
    if(value.search(/^(0{1}\d{2,3}-\d{7,8})$/) == -1)
    {
    	return false;
    }
    else
    {
    	var s1 = "0123456789";
    	var s2 = "9876543210";
    	for(var i=0; i <= value.length-7; i++)
    	{
    		var subS = value.substr(i, 7);
    		if(s1.indexOf(subS) != -1 || s2.indexOf(subS) != -1)
    		{
    			return false;
    		}
    		if(subS.search(/^(0{7}|1{7}|2{7}|3{7}|4{7}|5{7}|6{7}|7{7}|8{7}|9{7})$/) != -1)
    		{
    			return false;
    		}
    	}
    	return true;
    }
};

/**** 是否为合法的手机号码，为了兼容国际写法，目前只判断了是否是+数字[mobile] ****/
Validate.isMobilePhone = function(value) {
    if(value.search(/^(\+\d{2,3})?(13|14|15|17|18){1}\d{9}$/) == -1)
    {
    	return false;
    }
    else
    {
    	var s1 = "0123456789";
    	var s2 = "9876543210";
    	for(var i=0; i <= value.length-7; i++)
    	{
    		var subS = value.substr(i, 7);
    		if(s1.indexOf(subS) != -1 || s2.indexOf(subS) != -1)
    		{
    			return false;
    		}
    		if(subS.search(/^(0{7}|1{7}|2{7}|3{7}|4{7}|5{7}|6{7}|7{7}|8{7}|9{7})$/) != -1)
    		{
    			return false;
    		}
    	}
    	return true;
    }
};

/**** 是否为合法的国内邮政编码[zipcode] ****/
Validate.isZipcode = function(value) {
//    if(value.search(/^[1-9]\d{5}$/) == -1)
    if(value.search(/^\d{6}$/) == -1)
        return false;
    else
        return true;
};

/**** 是否为合法的确认书号码[isconfirmcode] ****/
Validate.isConfirmcode = function(value) {
    if(value.search(/^139999\d{10}$/) == -1)
        return false;
    else
        return true;
};

/**** 是否为合法的QQ号[qq] ****/
Validate.isQQ = function(value) {
    if(value.search(/^[1-9][0-9]{4,}$/) == -1)
        return false;
    else
        return true;
};

/**** 是否为合法的日期[date] ****/
Validate.isDate = function(value, splitChar) {
	splitChar = this.trim(splitChar) == "" ? "-" : this.trim(splitChar);
	var vSplit = value.split(splitChar);
	if(vSplit.length != 3)
	{
		return false;
	}
	var vDate = new Date(vSplit[0] + "/" + vSplit[1] + "/" + vSplit[2]);
	var bGoodDay;
	bGoodDay = (vDate.getFullYear() == Number(vSplit[0])) && ((vDate.getMonth() + 1) == Number(vSplit[1])) && (vDate.getDate() == Number(vSplit[2]));
	if (!bGoodDay) {
		return false;
	}
	else
	{
		return true;
	}
};

/**** 是否为给定日期之前[datebefore] ****/
Validate.dateBefore = function(value, referenceDate, splitChar) {
	splitChar = this.trim(splitChar) == "" ? "-" : this.trim(splitChar);
	var vSplit = value.split(splitChar);
	var rSplit = this.trim(referenceDate).split(splitChar);
	if(vSplit.length != 3 || rSplit.length != 3)
	{
		return false;
	}
	var vDate = new Date(vSplit[0] + "/" + vSplit[1] + "/" + vSplit[2]);
	var rDate = new Date(rSplit[0] + "/" + rSplit[1] + "/" + rSplit[2]);
	if (vDate < rDate) {
		return true;
	}
	else
	{
		return false;
	}
};

/**** 是否为给定日期[dateEquals] ****/
Validate.dateEquals = function(value, referenceDate, splitChar) {
	splitChar = this.trim(splitChar) == "" ? "-" : this.trim(splitChar);
	var vSplit = value.split(splitChar);
	var rSplit = this.trim(referenceDate).split(splitChar);
	if(vSplit.length != 3 || rSplit.length != 3)
	{
		return false;
	}
	var vDate = new Date(vSplit[0] + "/" + vSplit[1] + "/" + vSplit[2]);
	var rDate = new Date(rSplit[0] + "/" + rSplit[1] + "/" + rSplit[2]);
	if (vDate - rDate == 0) {
		return true;
	}
	else
	{
		return false;
	}
};

/**** 是否为给定日期之后[dateafter] ****/
Validate.dateAfter = function(value, referenceDate, splitChar) {
	splitChar = this.trim(splitChar) == "" ? "-" : this.trim(splitChar);
	var vSplit = value.split(splitChar);
	var rSplit = this.trim(referenceDate).split(splitChar);
	if(vSplit.length != 3 || rSplit.length != 3)
	{
		return false;
	}
	var vDate = new Date(vSplit[0] + "/" + vSplit[1] + "/" + vSplit[2]);
	var rDate = new Date(rSplit[0] + "/" + rSplit[1] + "/" + rSplit[2]);
	if (vDate > rDate) {
		return true;
	}
	else
	{
		return false;
	}
};

/**** 是否连续串[continuation] ****/
Validate.isContinuation = function(value)
{
	var s1 = "0123456789";
	var s2 = "9876543210";
	var s3 = "abcdefghijklmnopqrstuvwxyz";
	var s4 = "zyxwvutsrqponmlkjihgfedcba";
	var s5 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	var s6 = "ZYXWVUTSRQPONMLKJIHGFEDCBA";
	if(s1.indexOf(value) != -1 || s2.indexOf(value) != -1 || s3.indexOf(value) != -1 || s4.indexOf(value) != -1 || s5.indexOf(value) != -1 || s6.indexOf(value) != -1)
	{
		return true;
	}
	else
	{
		return false;
	}
};

/**** 是否相同字符的字符串[samechar] ****/
Validate.isSameCharacter = function(value)
{
	var ch = value.charAt(0);
	for(var i = 1; i < value.length; i++)
	{
		if(ch != value.charAt(i))
			return false;
	}
	return true;
};

/**** 是否符合满足长度[strlen] ****/
Validate.strLen = function(value, len)
{
	len = this.trim(""+len);
    var regx;
    if(len == "")
        regx = new RegExp("^\.*$");
    else
        regx = new RegExp("^\.{" + len + "}$");
    if(value.search(regx) == -1)
        return false;
    else
        return true;
};

/**** 是否符合满足范围长度,包含[strlenrange] ****/
Validate.strLenRange = function(value, minL, maxL)
{
	minL = this.trim(""+minL);
	maxL = this.trim(""+maxL);
    var regx;
    if(minL == "" && maxL == "")
        regx = new RegExp("^\.*$");
    else
        regx = new RegExp("^\.{" + (minL == "" ? "0" : minL) + "," + maxL + "}$");
    if(value.search(regx) == -1)
        return false;
    else
        return true;
};

/**** 是否不包含数字[exnum] ****/
Validate.isExNum = function(value)
{
    if(value.search(/^[^0-9]{0,}$/) == -1)
        return false;
    else
        return true;
};

/**** 是否为合法数字[isnum] ****/
Validate.isNum = function(value) {
    if(value.search(/^[0-9]+.?[0-9]*$/) == -1)
        return false;
    else
        return true;
};

/**** 是否为数字串，length等于0不限制长度[numlen] ****/
Validate.isNumber = function(value, length) {
    var regx;
    if(length==0)
        regx = new RegExp("^\\d*$");
    else
        regx = new RegExp("^\\d{" + length + "}$");
    if(value.search(regx) == -1)
        return false;
    else
        return true;
};

/**** 数字是否在区间之内,包含[numrange] ****/
Validate.numRange = function(value, minN, maxN){
	try {
		if(Number(value) <= Number(maxN) && Number(value) >= Number(minN))
			return true;
		else
			return false;
	} catch (e) {
		return false;
	}
};

/**** 数字是否大于某值,包含[minnum] ****/
Validate.minNum = function(value, minN){
	try {
		if(Number(value) >= Number(minN))
			return true;
		else
			return false;
	} catch (e) {
		return false;
	}
};

/**** 数字是否小于某值,包含[maxnum] ****/
Validate.maxNum = function(value, maxN){
	try {
		if(Number(value) <= Number(maxN))
			return true;
		else
			return false;
	} catch (e) {
		return false;
	}
};


/**** 身份证号验证 ****/
Validate.IdCard = IdCard;

/**** 默认提供校验，true-校验通过；false-校验不通过 ****/
Validate.checkDefault = function(vReq, value, pArr)
{
	var flag = true;
	pArr = pArr == null ? new Array() : pArr;
	if(vReq == "notnull" && value == "")
	{
		flag = false;
	}
	else if(vReq == "isnum" && value != "" && !this.isNum(value))
	{
		flag = false;
	}
	else if(vReq == "mobile" && value != "" && !this.isMobilePhone(value))
	{
		flag = false;
	}
	else if(vReq == "telphone" && value != "" && !this.isTelphone(value))
	{
		flag = false;
	}
	else if(vReq == "qq" && value != "" && !this.isQQ(value))
	{
		flag = false;
	}
	else if(vReq == "email" && value != "" && !this.isEmail(value))
	{
		flag = false;
	}
	else if(vReq == "prefixemail" && value != "" && !this.isEmailPrefix(value))
	{
		flag = false;
	}
	else if(vReq == "suffixemail" && value != "" && !this.isEmailSuffix(value))
	{
		flag = false;
	}
	else if(vReq == "zipcode" && value != "" && !this.isZipcode(value))
	{
		flag = false;
	}
	// 避免部分需要证件号确认是身份证类型的情况下判断，所以，不放在默认校验内[在input内直接写myidcardno，然后在checkUser内校验，避免此校验]
	else if(vReq == "idcardno" && value != "" && !this.IdCard.isIdCardNo(value))
	{
		flag = false;
	}
	else if(vReq == "date" && value != "" && !this.isDate(value, pArr[0]))
	{
		flag = false;
	}
	else if(vReq == "datebefore" && value != "" && !this.dateBefore(value, pArr[0], pArr[1]))
	{
		flag = false;
	}
	else if(vReq == "dateafter" && value != "" && !this.dateAfter(value, pArr[0], pArr[1]))
	{
		flag = false;
	}
	else if(vReq == "continuation" && value != "" && !this.isContinuation(value))
	{
		flag = false;
	}
	else if(vReq == "samechar" && value != "" && !this.isSameCharacter(value))
	{
		flag = false;
	}
	else if(vReq == "strlen" && value != "" && !this.strLen(value, pArr[0]))
	{
		flag = false;
	}
	else if(vReq == "strlenrange" && value != "" && !this.strLenRange(value, pArr[0], pArr[1]))
	{
		flag = false;
	}
	else if(vReq == "exnum" && value != "" && !this.isExNum(value))
	{
		flag = false;
	}
	else if(vReq == "numlen" && value != "" && !this.isNumber(value, pArr[0]))
	{
		flag = false;
	}else if(vReq == "isconfirmcode" && value !="" && !this.isConfirmcode(value))
	{
		flag = false;
	}else if(vReq == "numrange" && value !="" && !this.numRange(value, pArr[0], pArr[1]))
	{
		flag = false;
	}else if(vReq == "minnum" && value !="" && !this.minNum(value, pArr[0]))
	{
		flag = false;
	}else if(vReq == "maxnum" && value !="" && !this.maxNum(value, pArr[0]))
	{
		flag = false;
	}
	/*
	 * 还未想好
	else if(vReq == "numlen" && value != "" && !this.isNumber(value))
	{
		flag = false;
	}*/
	return flag;
};


/**** 提供用户重写，true-校验通过；false-校验不通过 ****/
Validate.checkUser = function(vReq, value, pArr)
{
	return true;
};

/**** 单个校验事件方法 ****/
Validate.checkObj = function(valObj)
{
	try{
	var validateStr = valObj.attr("validate");
	if(Validate.trim(validateStr) == "")
	{
		return false;
	}
	var checkBefore = valObj.attr("check_before");
	if(Validate.trim(checkBefore) != "")
	{
		try {
			var funName = checkBefore;
			var params = new Array();

			if(funName.indexOf('(') != -1)
			{
				funName = Validate.trim(checkBefore.substring(0, checkBefore.indexOf('(')));
				params = Validate.getPArrByStr(Validate.trim(checkBefore.substring(checkBefore.indexOf('(')+1, checkBefore.lastIndexOf(')') == -1 ? checkBefore.length-1 : checkBefore.lastIndexOf(')'))));
			}
			var pStr = "(";
			if(params != null && params.length > 0)
			{
				$.each(params, function(ind){
					if(this == "this")
					{
						pStr += (ind != 0 ? "," : "") + "valObj";
					}
					else
					{
						pStr += (ind != 0 ? "," : "")+"'" + this + "'";
					}
				});
			}
			pStr += ")";
			eval(funName+pStr);
		} catch (e) {
		}
	}
	var val = this.trim(valObj.val());
	var vType = validateStr.substring(0, validateStr.indexOf("|"));
	var vArr = validateStr.substr(validateStr.indexOf("|")+1).split("|");
	var msg = this.getMsgByIndex(vType);
	$.each(vArr, function(){
		var vReq = this.split("^")[0];
		var vDesc = this.split("^")[1];
		var pArr = new Array();
		
		if(vReq.indexOf('(') != -1)
		{
			var params = Validate.trim(vReq.substring(vReq.indexOf('(')+1, vReq.lastIndexOf(')') == -1 ? vReq.length-1 : vReq.lastIndexOf(')')));
			vReq = Validate.trim(vReq.substring(0, vReq.indexOf('(')));
			pArr = Validate.getPArrByStrWithObj(params, valObj);
		}

		var flag = Validate.checkDefault(vReq, val, pArr) && Validate.checkUser(vReq, val, pArr);
		if(!flag)
		{
			Validate.addMsg(valObj, vDesc);
			if(msg.indexOf(vDesc + "<br\>") == -1)
			{
				msg = msg + vDesc + "<br\>";
				Validate.setMsgByIndex(vType, msg);
			}
		}
		else
		{
			msg = msg.replace(vDesc + "<br\>", "");
			Validate.setMsgByIndex(vType, msg);
		}
		vReq = "";
		vDesc = "";
	});
	}catch(error2){
		alert(error2);
	}
};

/**
 * 校验当前界面某一类别域（若ind为空，则校验所有类别域）
 */
Validate.validate = function(ind)
{
	ind = this.trim("" + ind);
	if(ind == "")
	{
		this.validateAll();
	}
	else
	{
		this.msg = new Array();
		$("[validate]").each(function () {
			Validate.removeMsg($(this));			
		});
		$("[validate]").each(function () {
			var validateStr = $(this).attr("validate");
			var vType = validateStr.substring(0, validateStr.indexOf("|"));
			if(vType == ind)
			{
				Validate.checkObj($(this));
			}
		});
	}
};

/**
 * 校验当前界面所有类别域
 */
Validate.validateAll = function()
{
	this.msg = new Array();
	$("[validate]").each(function () {
		Validate.removeMsg($(this));
	});
	$("[validate]").each(function () {
		Validate.checkObj($(this));
	});
};

/**** 移除错误信息提示 ****/
Validate.removeMsg = function(obj)
{
	/*obj.removeClass("input-error");
	var msgObj = obj.parent().find("div[name='msgDiv']").each(function(){
		this.remove();
	});*/
	var errorFlag = obj;
	var errorFlagId = obj.attr("error_flag");
	if(errorFlagId != null && errorFlagId != '' && obj.parent().find("#"+errorFlagId) != null && obj.parent().find("#"+errorFlagId).length > 0)
	{
		errorFlag = obj.parent().find("#"+errorFlagId);
	}
	errorFlag.removeClass("input-error");
	
	/*obj.parent().find("label[name='msgLabel']").each(function(){
		this.remove();
	});*/
	var msgAfter = obj;
	var msgAfterId = obj.attr("msg_after");
	if(msgAfterId != null && msgAfterId != '' && obj.parent().find("#"+msgAfterId) != null && obj.parent().find("#"+msgAfterId).length > 0)
	{
		msgAfter = obj.parent().find("#"+msgAfterId);
	}
	msgAfter.siblings("label[name='msgLabel']").each(function(){
		$(this).remove();
	});
};

/**** 添加错误信息提示 ****/
Validate.addMsg = function(obj, msg)
{
	var errorFlag = obj;
	var errorFlagId = obj.attr("error_flag");
	if(errorFlagId != null && errorFlagId != '' && obj.parent().find("#"+errorFlagId) != null && obj.parent().find("#"+errorFlagId).length > 0)
	{
		errorFlag = obj.parent().find("#"+errorFlagId);
	}
	errorFlag.addClass("input-error");
	
	var msgAfter = obj;
	var msgAfterId = obj.attr("msg_after");
	if(msgAfterId != null && msgAfterId != '' && obj.parent().find("#"+msgAfterId) != null && obj.parent().find("#"+msgAfterId).length > 0)
	{
		msgAfter = obj.parent().find("#"+msgAfterId);
	}
	msgAfter.after(this.getMsgHtml(msg));
};

/**** 获取错误信息提示Html字符串 ****/
Validate.getMsgHtml = function(msg)
{
	var msgHtml = "";
	// msgHtml += "<label name=\"msgLabel\" style=\"color: red; line-height: 20px; margin: 0px; padding: 0px;\"><i>✘</i> "+msg+"</label>";
	msgHtml += "<label name=\"msgLabel\" style=\"color: red; line-height: 20px; margin: 0px; padding: 0px;\"><i>✖</i> "+msg+"</label>";
	return msgHtml;
};
/*<input type="text" id="appntPhone" msg_after="test" error_flag="appntJobShow" validate="1|notnull^手机号码不能为空"/>*/
Validate.addValidateListener = function(pObj)
{
	if(pObj != null && pObj.length > 0)
	{
		pObj.find("[validate]").focus(function(){
			Validate.removeMsg($(this));
		});
		pObj.find("[validate]").blur(function () {
			Validate.checkObj($(this));
		});
	}
	else
	{
		$("[validate]").focus(function(){
			Validate.removeMsg($(this));
		});
		$("[validate]").blur(function () {
			Validate.checkObj($(this));
		});
	}
};

$(document).ready(function()
{
	Validate.addValidateListener();
});


/*******************界面规则校验相关 end*********************/

/***********************界面交互时loading*********************/
var DIALOG = {};
DIALOG.loading = function()
{
	var h = "<div class=\"lian-loader-bg\">\n";
	h += "<div class=\"lian-loader-box\"></div>\n";
	h += "<div class=\"lian-loader\"><div></div></div>\n";
	h += "</div>\n";
	
	$("body").prepend(h);
};
DIALOG.closeLoader = function()
{
	try {
		$(".lian-loader-bg").remove();
	} catch (e) {}
};
DIALOG.close = function()
{
	DIALOG.closeLoader();
};


/***********************界面交互时loading*********************/

/***********************cookie 处理*********************/
var CookieUtil = {};
CookieUtil.setItem = function(name, value)
{
	// var exdate = new Date();
	// exdate.setDate(exdate.getDate() + expiredays);
	// document.cookie = name + "=" + escape(value) + "^" + p_name + "=" + escape(p_value) + ((expiredays == null) ? "" : "^;expires=" + exdate.toGMTString());
	// console.log(document.cookie)
	document.cookie = name + "=" + escape(value);
	console.log(document.cookie);
};
CookieUtil.getItem = function(name)
{
	try {
		if (document.cookie.length > 0) {
			var c_start = document.cookie.indexOf(name + "=")
			if (c_start != -1) {
				c_start = c_start + name.length + 1;
				var c_end = document.cookie.indexOf("^", c_start);
				if (c_end == -1)
					c_end = document.cookie.length;
				var c_end2 = document.cookie.indexOf(";", c_start);
				if (c_end2 == -1)
					c_end2 = document.cookie.length;
				c_end = c_end > c_end2 ? c_end2 : c_end;
				return unescape(document.cookie.substring(c_start, c_end));
			}
		}
	} catch (e) {
	}
	return "";
};
CookieUtil.cleanCookie = function(name) { // 使cookie过期
	document.cookie = name + "=" + ";expires=Thu, 01-Jan-70 00:00:01 GMT";
	// document.cookie = c_name + "=" + ";" + p_name + "=" + ";expires=Thu, 01-Jan-70 00:00:01 GMT";
};

/***********************cookie 处理*********************/

/********************日期 处理*************************/
function resetDateV(obj)
{
	$(obj).val($(obj).val().replace(/-/g,''));
}
function changeDateV(obj)
{
	var dv = $(obj).val();
	var dvT = "";
	var re = new RegExp(/^(\d{4})(\d{2})(\d{2})$/);
	var dvArr = dv.match(re);
	dvT = dvArr[1] + "-" + dvArr[2] + "-" + dvArr[3];
	$(obj).val(dvT);
}

/***********************滑动删除*********************/
/*var SlideDelete = {};
SlideDelete.x = null;

SlideDelete.prevent_default = function(e) {
    e.preventDefault();
};
SlideDelete.disable_scroll = function() {
    $(document).on('touchmove', this.prevent_default);
};
SlideDelete.enable_scroll = function() {
    $(document).unbind('touchmove', this.prevent_default)
};
SlideDelete.addListener = function(obj)
{
	obj.on('touchstart', function(e) {
        // console.log(e.originalEvent.pageX)
        $(this).css('left', '0px'); // close em all
        $(e.currentTarget).addClass('open');
        x = e.originalEvent.targetTouches[0].pageX; // anchor point
    })
    .on('touchmove', function(e) {
        var change = e.originalEvent.targetTouches[0].pageX - x;
        change = Math.min(Math.max(-100, change), 0); // restrict to -100px left, 0px right
        e.currentTarget.style.left = change + 'px';
        if (change < -10) SlideDelete.disable_scroll(); // disable scroll once we hit 10px horizontal slide
    })
    .on('touchend', function(e) {
        var left = parseInt(e.currentTarget.style.left);
        var new_left;
        if (left < -35) {
            new_left = '-100px';
        } else if (left > 35) {
            new_left = '100px';
        } else {
            new_left = '0px';
        }
        // e.currentTarget.style.left = new_left
        $(e.currentTarget).animate({left: new_left}, 200);
        SlideDelete.enable_scroll();
    });
};
*/
/***********************滑动删除*********************/





var trim = function(str){
	if(str != null && str != "")
		return str.replace(/(^\s*)|(\s*$)/g, "");
	else
		return "";
};
var toStr = function(s){
	var str = "";
	if(s != null && s != "" && s != "null")
	{
		str = s;
	}
	return str;
};

BasicListUtil.initBasicProperty();