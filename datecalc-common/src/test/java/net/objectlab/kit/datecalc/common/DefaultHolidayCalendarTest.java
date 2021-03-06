package net.objectlab.kit.datecalc.common;

import static net.objectlab.kit.datecalc.common.Utils.getCal;
import static java.util.Calendar.APRIL;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;


public class DefaultHolidayCalendarTest extends TestCase {
    
    public void testIsHoliday() {
        
        final Set<Calendar> holidays = new HashSet<Calendar>();
        Calendar holiday = Utils.getCal("2009-04-22");
        holidays.add(holiday);

        final HolidayCalendar<Calendar> holidayCalendar = new DefaultHolidayCalendar<Calendar>(holidays, getCal("2009-01-01"), getCal("2009-12-01"));
        
        Calendar testHoliday = Calendar.getInstance();
        testHoliday.set(2009, APRIL, 22);
        assertTrue(holidayCalendar.isHoliday(testHoliday));

        testHoliday.set(2010, APRIL, 21);
        assertFalse(holidayCalendar.isHoliday(testHoliday));

        testHoliday.set(2010, APRIL, 23);
        assertFalse(holidayCalendar.isHoliday(testHoliday));

        testHoliday.set(2009, APRIL + 1, 22);
        assertFalse(holidayCalendar.isHoliday(testHoliday));
        
        testHoliday.set(2010, APRIL, 22);
        assertFalse(holidayCalendar.isHoliday(testHoliday));
    }
}
