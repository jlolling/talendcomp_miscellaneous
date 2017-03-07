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
package de.cimt.talendcomp.daterangenormalize;

import java.util.Calendar;
import java.util.Date;

public class DateRangeNormalizer {
	
	private Date rangeStartDate = null;
	private Date rangeEndDate = null;
	private Calendar cal = Calendar.getInstance();
	private boolean truncateDate = false;
	private boolean excludeStartDate = false;
	
	public void setDateRange(Date start, Date end) {
		if (start == null) {
			start = new Date();
		}
		if (end == null) {
			end = new Date();
		}
		this.rangeStartDate = start;
		this.rangeEndDate = end;
		cal.setTime(rangeStartDate);
		cal.add(Calendar.DAY_OF_YEAR, -1);
		if (truncateDate) {
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
		}
		if (excludeStartDate) {
			// move one day forward to exclude the start date
			cal.add(Calendar.DAY_OF_YEAR, 1);
		}
	}
	
	public boolean next() {
		boolean hasNext = cal.getTime().before(rangeEndDate);
		cal.add(Calendar.DAY_OF_YEAR, 1);
		return hasNext;
	}
	
	public Date getNextDate() {
		return cal.getTime();
	}

	public boolean isTruncateDate() {
		return truncateDate;
	}

	public void setTruncateDate(Boolean truncateDate) {
		if (truncateDate != null) {
			this.truncateDate = truncateDate;
		}
	}
	
	public void excludeStartDate(Boolean excludeStartDate) {
		if (excludeStartDate != null) {
			this.excludeStartDate = excludeStartDate;
		}
	}
	
}
