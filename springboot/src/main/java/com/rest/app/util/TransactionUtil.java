/**
 * 
 */
package com.rest.app.util;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Component;

/**
 * @author danielf
 *
 */
@Component
public class TransactionUtil {

	public String GenerateID(long count) {
		try {

			Date today = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(today);

			int month = cal.get(Calendar.MONTH) + 1;
			int year = cal.get(Calendar.YEAR) - 2000;
			year = year * 100;
			year = year + month;
			year = year * 10000;
			count = count + 1;
			year = year + (int) count;
			String id = String.valueOf(year);
			return id;

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return null;
	}

}
