/**
 * Copyright 2015 Jan Lolling jan.lolling@gmail.com
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cimt.talendcomp.calendar;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CalendarBuilder {
	
	private Date startDate;
	private Calendar endCal;
	private Locale locale = null;
	private boolean firstLoop = true;
	private Calendar currentCal;
	private Calendar tomorrowCal;
	private Calendar helperCal; 
	private String[] monthNames = null;
	private String[] monthShortNames = null;
	private String[] weekDayNames = null;
	private String[] weekDayShortNames = null;
	private int firstDay = Calendar.SUNDAY;
	private Calendar financialYearStartCal = null;
	private int finMonthOffset = -1;
	
	public void setLocale(String localeString) {
		locale = createLocale(localeString);
	}
	
	public void initializeCalendar() {
		currentCal = Calendar.getInstance(locale);
		if (startDate != null) {
			currentCal.setTime(startDate);
		}
		currentCal.set(Calendar.HOUR_OF_DAY, 0);
		currentCal.set(Calendar.MINUTE, 0);
		currentCal.set(Calendar.SECOND, 0);
		currentCal.set(Calendar.MILLISECOND, 0);
		currentCal.setMinimalDaysInFirstWeek(4);
		tomorrowCal = Calendar.getInstance(locale);
		tomorrowCal.setTime(currentCal.getTime());
		tomorrowCal.add(Calendar.DAY_OF_YEAR, 1);
		helperCal = Calendar.getInstance(locale);
		helperCal.setMinimalDaysInFirstWeek(4);
		firstDay = currentCal.getFirstDayOfWeek();
		DateFormatSymbols symbols = new DateFormatSymbols(locale);
		monthNames = symbols.getMonths();
		monthShortNames = symbols.getShortMonths();
		weekDayNames = symbols.getWeekdays();
		weekDayShortNames = symbols.getShortWeekdays();
	}
	
    private static Locale createLocale(String localeName) {
        if (localeName == null || localeName.trim().length() < 2) {
        	return Locale.getDefault();
        } else {
        	localeName = localeName.trim();
            Locale locale = null;
            int pos = localeName.indexOf('_');
            if (pos > 1) {
                String language = localeName.substring(0, pos);
                String country = localeName.substring(pos + 1);
                locale = new Locale(language, country);
            } else {
                locale = new Locale(localeName);
            }
            return locale;
        }
    }

    public boolean nextDay() {
		checkInit();
		if (firstLoop == false) {
			if (currentCal.before(endCal)) {
				// move one day forward
				currentCal.add(Calendar.DAY_OF_YEAR, 1);
				tomorrowCal.add(Calendar.DAY_OF_YEAR, 1);
				return true;
			} else {
				return false;
			}
		} else {
			firstLoop = false;
			return true;
		}
	}
	
	private void checkInit() {
		if (currentCal == null) {
			throw new IllegalStateException("Calendar not initialied");
		}
	}
	
	public Date getCurrentDate() {
		checkInit();
		return currentCal.getTime();
	}
	
	public int getCurrentDateAsInt() {
		checkInit();
		return getDateAsInt(currentCal);
	}
	
	public String getCurrentWeekDayName() {
		checkInit();
		int index = currentCal.get(Calendar.DAY_OF_WEEK);
		return weekDayNames[index];
	}
	
	public String getCurrentWeekDayShortName() {
		checkInit();
		int index = currentCal.get(Calendar.DAY_OF_WEEK);
		return weekDayShortNames[index];
	}

	public String getCurrentMonthName() {
		checkInit();
		int index = currentCal.get(Calendar.MONTH);
		return monthNames[index];
	}

	public String getCurrentMonthShortName() {
		checkInit();
		int index = currentCal.get(Calendar.MONTH);
		return monthShortNames[index];
	}
	
	public int getCurrentCalendarYear() {
		checkInit();
		return currentCal.get(Calendar.YEAR);
	}

	public int getCurrentWeekYear() {
		checkInit();
		int calYear = currentCal.get(Calendar.YEAR);
		int week = currentCal.get(Calendar.WEEK_OF_YEAR);
		int month = currentCal.get(Calendar.MONTH) + 1;
		if (month == 1 && week == 53) {
			return calYear - 1;
		} else if (week == 1 && month == 12) {
			return calYear + 1;
		} else {
			return calYear;
		}
	}

	public int getCurrentMonth() {
		checkInit();
		return currentCal.get(Calendar.MONTH) + 1;
	}
	
	public int getFinancialMonth() {
		checkInit();
		if (finMonthOffset >= 0) {
			int month = getCurrentMonth();
			if (month > finMonthOffset) {
				return month - finMonthOffset;
			} else {
				return month + 12 - finMonthOffset;
			}
		} else {
			return getCurrentMonth();
		}
	}

	public int getCurrentQuarter() {
		int month = getCurrentMonth();
		if (month < 4) {
			return 1;
		} else if (month < 7) {
			return 2;
		} else if (month < 10) {
			return 3;
		} else {
			return 4;
		}
	}

	public int getFinancialQuarter() {
		int month = getFinancialMonth();
		if (month < 4) {
			return 1;
		} else if (month < 7) {
			return 2;
		} else if (month < 10) {
			return 3;
		} else {
			return 4;
		}
	}

	public int getCurrentWeek() {
		checkInit();
		return currentCal.get(Calendar.WEEK_OF_YEAR);
	}

	public int getCurrentDayOfYear() {
		checkInit();
		return currentCal.get(Calendar.DAY_OF_YEAR);
	}

	public int getCurrentDayOfMonth() {
		checkInit();
		return currentCal.get(Calendar.DAY_OF_MONTH);
	}

	public int getCurrentDayOfWeek() {
		checkInit();
		int index = currentCal.get(Calendar.DAY_OF_WEEK);
		if (firstDay == Calendar.MONDAY) {
			if (index == 1) {
				index = 8;
			}
			index--;
		}
		return index;
	}

	private int getDateAsInt(Calendar c) {
		int today = c.get(Calendar.YEAR) * 10000;
		today = today + (c.get(Calendar.MONTH) + 1) * 100;
		today = today + c.get(Calendar.DAY_OF_MONTH);
		return today;
	}

	public Date getDateWithDaysOffset(int days) {
		checkInit();
		helperCal.setTime(currentCal.getTime());
		helperCal.add(Calendar.DAY_OF_YEAR, days);
		return helperCal.getTime();
	}
	
	public Date getCurrentWeekStart() {
		checkInit();
		helperCal.setTime(currentCal.getTime());
		helperCal.set(Calendar.DAY_OF_WEEK, helperCal.getFirstDayOfWeek());
		return helperCal.getTime();
	}
	
	public Date getCurrentWeekEnd() {
		checkInit();
		helperCal.setTime(currentCal.getTime());
		helperCal.set(Calendar.DAY_OF_WEEK, helperCal.getFirstDayOfWeek());
		helperCal.add(Calendar.DAY_OF_YEAR, 7);
		return helperCal.getTime();
	}

	public Date getCurrentMonthStart() {
		checkInit();
		helperCal.setTime(currentCal.getTime());
		helperCal.set(Calendar.DAY_OF_MONTH, 1);
		return helperCal.getTime();
	}
	
	public Date getCurrentMonthEnd() {
		checkInit();
		helperCal.setTime(currentCal.getTime());
		helperCal.set(Calendar.DAY_OF_MONTH, 1);
		helperCal.add(Calendar.MONTH, 1);
		helperCal.add(Calendar.DAY_OF_YEAR, -1);
		return helperCal.getTime();
	}

	public Date getPreviousMonthDate() {
		checkInit();
		helperCal.setTime(currentCal.getTime());
		helperCal.add(Calendar.MONTH, -1);
		return helperCal.getTime();
	}
	
	public int getFinancialYear() {
		checkInit();
		if (financialYearStartCal != null) {
			int fSwitchDay = (financialYearStartCal.get(Calendar.MONTH) * 100) + financialYearStartCal.get(Calendar.DAY_OF_MONTH);
			int currentMonth = currentCal.get(Calendar.MONTH);
			int currentDay = (currentMonth * 100) + currentCal.get(Calendar.DAY_OF_MONTH);
			if (currentDay < fSwitchDay) {
				return getCurrentCalendarYear() - 1;
			} else {
				return getCurrentCalendarYear();
			}
		} else {
			return getCurrentWeekYear();
		}
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setStartDate(String startDate) throws Exception {
		setStartDate(GenericDateUtil.parseDate(startDate));
	}
	
	public void setFinancialYearStartDate(String date) throws Exception {
		this.financialYearStartCal = Calendar.getInstance(locale);
		this.financialYearStartCal.setTime(GenericDateUtil.parseDate(date));
		this.finMonthOffset = financialYearStartCal.get(Calendar.MONTH);
	}
	
	private void setupEndCal(Date endDate) {
		this.endCal = Calendar.getInstance(locale);
		this.endCal.setTime(endDate);
	}

	private void setupEndCal(int years) {
		this.endCal = Calendar.getInstance(locale);
		this.endCal.add(Calendar.YEAR, years);
	}

	public void setEndDate(Date endDate) {
		setupEndCal(endDate);
	}

	public void setNumberYears(int years) {
		setupEndCal(years);
	}
	
	public void setEndDate(String endDate) throws Exception {
		setupEndCal(GenericDateUtil.parseDate(endDate));
	}
	
	public String getCalendarClassName() {
		return currentCal.getClass().getName();
	}
	
	public long getUTCMillisSeconds() {
		return currentCal.getTimeInMillis();
	}
	
	public boolean isLastDayOfMonth() {
		int month = getCurrentCalendarYear() + getCurrentMonth();
		int tomorrowMonth = tomorrowCal.get(Calendar.YEAR) + tomorrowCal.get(Calendar.MONTH) + 1;
		if (month < tomorrowMonth) {
			return true;
		} else {
			return false;
		}
	}
	
}
