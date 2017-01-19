package dateformat;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by b06514a on 12/20/2016.
 */
public class DateFormatter {

	public static void main(String[] args) throws ParseException {
		Date currdate = new Date();
//		String dateStr = currdate.toString();
		String dateStr = "Tue Dec 20 15:36:40 EET 2016";
		System.out.println(dateStr);

		SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
		Date dateDate = format.parse(dateStr);
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(dateDate);
//		DateFormat date = DateFormat.getDateInstance();
//		Date dateDate = date.parse(dateStr);
		System.out.println(calendar.getTime());
	}

}
