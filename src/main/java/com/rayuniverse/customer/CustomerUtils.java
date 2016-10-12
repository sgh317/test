package com.rayuniverse.customer;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class CustomerUtils {

	public static final String BASE_INTEGRAL = "0";//首次赠送积分
	
	public static final String EFFECT_INTEGRAL = "15000";//激活赠送积分
	
	public static final BigDecimal ZSXS_MONTH = new BigDecimal("0.006");//折算月系数
	
	public static final BigDecimal ZSJS_TOTAL = new BigDecimal("0.8");//折算基数 

	public static int getMonth(Date start, Date end) {
        if (start.after(end)) {
            Date t = start;
            start = end;
            end = t;
        }
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(start);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(end);
        Calendar temp = Calendar.getInstance();
        temp.setTime(end);
        temp.add(Calendar.DATE, 1);

        int year = endCalendar.get(Calendar.YEAR)
                - startCalendar.get(Calendar.YEAR);
        int month = endCalendar.get(Calendar.MONTH)
                - startCalendar.get(Calendar.MONTH);

        if ((startCalendar.get(Calendar.DATE) == 1)
                && (temp.get(Calendar.DATE) == 1)) {
            return year * 12 + month + 1;
        } else if ((startCalendar.get(Calendar.DATE) != 1)
                && (temp.get(Calendar.DATE) == 1)) {
            return year * 12 + month;
        } else if ((startCalendar.get(Calendar.DATE) == 1)
                && (temp.get(Calendar.DATE) != 1)) {
            return year * 12 + month;
        } else {
            return (year * 12 + month - 1) < 0 ? 0 : (year * 12 + month-1);
        }
    }

	/**
	 * 计算时间
	 * @param loadDate
	 * @param string
	 * @return
	 * @throws ParseException 
	 */
	public static String calYearAndMonth(String loadDate,Date effectDate,String type) throws ParseException {
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		Date beginDate = sd.parse(loadDate);
		String year = null;
		String month = null;
		
		int monthint = getMonth(beginDate,effectDate);
		int yearint = monthint/12+1;
		
		month = String.valueOf(monthint);
		year = String.valueOf(yearint);
		
		if(StringUtils.equals("Y", type)){
			return year;
		}else{
			return month;
		}
	}

}
