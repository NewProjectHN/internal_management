package Com.IFI.InternalTool.Utils;

import java.sql.Date;
import java.text.DecimalFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.stream.IntStream;

public class Business {
	private static int num;

	public static void numberWeekendOfMonth1(int month, int year) {

		Month m = Month.of(month);

		IntStream.rangeClosed(1, YearMonth.of(year, m).lengthOfMonth()).mapToObj(day -> LocalDate.of(year, m, day))
				.filter(date -> date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY)
				.forEach(date -> System.out.print(date.getDayOfMonth() + " "));
	}

	public static int numberWeekendOfMonth(int month, int year) {
		num = 0;
		Month m = Month.of(month);
		IntStream.rangeClosed(1, YearMonth.of(year, m).lengthOfMonth()).mapToObj(day -> LocalDate.of(year, m, day))
				.filter(date -> date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY)
				.forEach(date -> num++);

		return num;
	}

	public static double getAllocation_Plan(int numDaysOfMonth, int numDaysWeekOfMonth, int distanceTime) {
		System.out.println(distanceTime);
		int y = numDaysOfMonth - numDaysWeekOfMonth;
		System.out.println(y);
		double result = ((double) distanceTime / y) * 100;
		System.out.println(result);
		DecimalFormat formatter = new DecimalFormat("#0.00");
		return Double.valueOf(formatter.format(result));
	}

	public static int getDistanceTime(LocalDate start_date, LocalDate end_date) {
		int DistanceTime = 0;
		while (start_date.isBefore(end_date) || start_date.equals(end_date)) {
			if ((start_date.getDayOfWeek() != DayOfWeek.SATURDAY && start_date.getDayOfWeek() != DayOfWeek.SUNDAY)) {
				DistanceTime++;
			}
			start_date = start_date.plusDays(1);

		}
		return DistanceTime;
	}

	public static final String getDefaultYear() {
		int year = LocalDate.now().getYear();
		StringBuilder sb = new StringBuilder();
		sb.append("");
		sb.append(year);
		return sb.toString();
	}

	public static final int getTotalsPages(final long count, final int pageSize) {
		int pages = (int) (count / pageSize);
		if (count % pageSize > 0) {
			pages++;
		}
		return pages;
	}

	// tinh tong so page
	public static int getTotalPage(final long totalResult, final int pageSize) {
		double total = (double) totalResult;
		return (int) Math.ceil(total / pageSize);
	}

	// tang thang len 1
	public static Date increaseMonthByOne(Date date) {
		LocalDate localDate = date.toLocalDate();
		int day = localDate.getDayOfMonth();
		int month = localDate.getMonthValue();
		int newMonth = month + 1;
		int year = localDate.getYear();
		day = validateEndDayOfMonth(LocalDate.of(year, newMonth, 1), day);
		LocalDate newDate = LocalDate.of(year, newMonth, day);
		return java.sql.Date.valueOf(newDate);
	}
	//kiem tra su ton tai cua ngay trong thang
	public static int validateEndDayOfMonth(LocalDate localDate, int day) {
		int lengOfMonth = localDate.lengthOfMonth();
		if (day > lengOfMonth) {
			day = lengOfMonth;
		}		
		return day;
	}

}