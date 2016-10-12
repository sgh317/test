/**
 * a+b,保留2位小数，不做取整
 * return a
 */
function addMoney(a,b){
	a = Math.round(parseFloat(a*100 + b*100))/100;
	return a.toFixed(2);
}

/**
 * a-b,保留2位小数，不做取整
 * return a
 */
function minusMoney(a,b){
	a = Math.round(parseFloat(a*100 - b*100))/100;
	return a.toFixed(2);
}