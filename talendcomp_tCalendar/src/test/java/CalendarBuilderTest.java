import de.cimt.talendcomp.calendar.CalendarBuilder;

public class CalendarBuilderTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CalendarBuilder cb = new CalendarBuilder();
		try {
			cb.setLocale("de");
			cb.setStartDate("25.12.2013");
//			cb.setEndDate("10.01.2014");
			cb.setNumberYears(1);
			cb.initializeCalendar();
			int count = 0;
			while (cb.nextDay()) {
				System.out.println("##########################");
				System.out.println("getCurrentCalendarYear: " + cb.getCurrentCalendarYear()); //
				System.out.println("getCurrentWeekYear: " + cb.getCurrentWeekYear()); //
				System.out.println("getCurrentDateAsInt: " + cb.getCurrentDateAsInt()); //
				System.out.println("getCurrentDayOfMonth: " + cb.getCurrentDayOfMonth()); 
				System.out.println("getCurrentDayOfWeek: " + cb.getCurrentDayOfWeek());
				System.out.println("getCurrentDayOfYear: " + cb.getCurrentDayOfYear());
				System.out.println("getCurrentMonth: " + cb.getCurrentMonth());
				System.out.println("getCurrentQuarter: " + cb.getCurrentQuarter());
				System.out.println("getCurrentMonthName: " + cb.getCurrentMonthName());
				System.out.println("getCurrentMonthShortName: " + cb.getCurrentMonthShortName());
				System.out.println("getCurrentWeek: " + cb.getCurrentWeek());
				System.out.println("getCurrentWeekDayName: " + cb.getCurrentWeekDayName());
				System.out.println("getCurrentWeekDayShortName: " + cb.getCurrentWeekDayShortName());
				System.out.println("getCurrentDate: " + cb.getCurrentDate());
				System.out.println("getPreviousMonthDate: " + cb.getPreviousMonthDate());
				System.out.println("getDateWithDaysOffset: " + cb.getDateWithDaysOffset(-7));
				count++;
			}
			System.out.println("count=" + count);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
