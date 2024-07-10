package ck0927.impl;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;


class HolidayCalculator {
	    public static boolean isHoliday(LocalDate date) {
	        // Independence Day (July 4th)
	        LocalDate independenceDay = LocalDate.of(date.getYear(), Month.JULY, 4);
	        if (independenceDay.getDayOfWeek() == DayOfWeek.SATURDAY) {
	            independenceDay = independenceDay.minusDays(1);
	        } else if (independenceDay.getDayOfWeek() == DayOfWeek.SUNDAY) {
	            independenceDay = independenceDay.plusDays(1);
	        }

	        // Labor Day (First Monday in September)
	        LocalDate laborDay = LocalDate.of(date.getYear(), Month.SEPTEMBER, 1)
	                .with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY));

	        return date.equals(independenceDay) || date.equals(laborDay);
	    }
}