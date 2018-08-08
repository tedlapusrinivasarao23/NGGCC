import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TestDates {

	public static void main(String[] args) {

		String todayDate = getCurrentSystemDateAndTime();

		System.out.println(todayDate);

		String laterDate = getOneYearLaterCurrentSystemDateAndTime();

		System.out.println(laterDate);
		
		System.out.println(daysBetween());
		
		getScheduleDays("","");

	}
	
private static void getScheduleDays(String strDate,String strDate2) {

SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
Date date1 = null;
Date date2 = null;
if((!strDate.equals("") || !strDate.isEmpty())&& (!strDate2.equals("") || !strDate2.isEmpty())) {
try {
	date1 = sdf.parse(strDate);
	
	Calendar cal = Calendar.getInstance();
	cal.setTime(date1);
	Date dt=cal.getTime();
	
	date2 = sdf.parse(strDate2);
	Calendar cal1 = Calendar.getInstance();
	cal1.setTime(date2);
	Date dt2=cal1.getTime();
	
	System.out.println(dt.getTime()+":::::::::::");
	System.out.println(dt2.getTime()+":::::::::::");
	long l=(dt2.getTime()- dt.getTime())/86400000;
	System.out.println("DAys  Left"+l);
} catch (ParseException e) {
	e.printStackTrace();
}

}
System.out.println("<<>>");
	}

	private static long daysBetween() {
		
		Date today1 = Calendar.getInstance().getTime();
		
		Calendar ca2 = Calendar.getInstance();
		Date today = ca2.getTime();
		ca2.add(Calendar.YEAR, 1); // to get previous year add -1
		Date nextYear = ca2.getTime();
		
		long difference = (nextYear.getTime() - today1.getTime()) / 86400000;
		return Math.abs(difference);
	}

	private static String getCurrentSystemDateAndTime() {
		DateFormat df = new SimpleDateFormat("dd-MMM-yy hh.mm.ss aa");

		Date today = Calendar.getInstance().getTime();
		String reportDate = df.format(today);
		return reportDate;

	}

	private static String getOneYearLaterCurrentSystemDateAndTime() {
		DateFormat df = new SimpleDateFormat("dd-MMM-yy hh.mm.ss aa");

		Calendar cal = Calendar.getInstance();
		Date today = cal.getTime();
		cal.add(Calendar.YEAR, 1); // to get previous year add -1
		Date nextYear = cal.getTime();

		// Date today = Calendar.getInstance().getTime();
		String reportDate = df.format(nextYear);
		return reportDate;

	}
}
