/**
 * 
 */
package com.rest.app.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import com.rest.app.repo.TransactionsRepository;
import com.rest.app.repo.UseraccountRepository;
import com.rest.app.table.Transactions;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * @author danielf
 *
 */
@Component
public class ReportUtil {

	@Value("${business.tin}")
	private String TIN;
	@Value("${business.contact}")
	private String CONTACT;
	@Value("${business.address}")
	private String ADDRESS;

	@Autowired
	private TransactionsRepository TransactionRepository;

	@Autowired
	private TransactionUtil txutil = new TransactionUtil();

	@Autowired
	private UseraccountRepository UserRepository;

	public File generateReport(String status, String from, String to, String user)
			throws FileNotFoundException, ParseException {
		try {
			Date start = new Date(), end = new Date();
			InputStream reportStream = new FileInputStream(new File("C:\\Users\\danielf\\Desktop\\salesreport.jrxml"));
			JasperReport report = JasperCompileManager.compileReport(reportStream);
			Transactions tx = new Transactions();
			tx.setTransactiontype("SALE");
			if (!status.equals("undefined") && !status.equals("null") && status != null) {
				tx.setTransactionstatus(status);
			}
			if (from != null && !from.equals("null")) {
				start = new SimpleDateFormat("MM/dd/yyyy").parse(from);
			}
			if (to != null && !to.equals("null")) {
				end = new SimpleDateFormat("MM/dd/yyyy").parse(to);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(end);
				int year = calendar.get(Calendar.YEAR);
				int month = calendar.get(Calendar.MONTH);
				int day = calendar.get(Calendar.DATE);
				calendar.set(year, month, day, 23, 59, 59);
				end = calendar.getTime();
			}
			Example<Transactions> example = Example.of(tx);
			List<Transactions> list = new ArrayList<Transactions>();
			if (from != null && !from.equals("null") && to != null) {
				if ((!from.equals("null") && from.equals(to)) || to.equals("null")) {
					tx.setTransactiondate(start);
					example = Example.of(tx);
					list = TransactionRepository.findAll(example);
				} else {
					list = TransactionRepository.findAll(txutil.getSpecFromDatesAndExample(start, end, example));
				}
			} else {
				list = TransactionRepository.findAll(example);
			}
			BigDecimal total = new BigDecimal(0);
			Integer count = 0;
			for (Transactions t : list) {
				total = total.add(t.getTransactionvalue());
				count += 1;
			}
			list.stream().forEach(i -> i.setUsername(UserRepository.findByUsername(i.getUsername()).getName()));
			list.stream().forEach(i -> i.setTransactiondate(setTime(i.getTransactiondate(), i.getTransactiontime())));
			JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(list);
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("generatedby", user);
			parameters.put("dategenerated", new Date().toString());
			parameters.put("from", !from.equals("null") ? from : "");
			parameters.put("totalamount", total);
			parameters.put("to", !to.equals("null") ? to : "");
			parameters.put("totalcount", count);
			JasperPrint print = JasperFillManager.fillReport(report, parameters, source);
			String filePath = "C:\\Users\\danielf\\Desktop\\";
			JasperExportManager.exportReportToPdfFile(print, filePath + "SALESREPORT.pdf");
			return new File(filePath + "SALESREPORT.pdf");
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public static Date setTime(Date date, Time time) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeZone(TimeZone.getTimeZone("UTC"));
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.add(Calendar.MILLISECOND, (int) time.getTime());
		Date d = cal.getTime();
		return d;
	}

}
