package net.objectlab.kit.datecalc.joda;

import java.util.ArrayList;
import java.util.List;

import net.objectlab.kit.datecalc.common.AbstractIMMDateCalculator;
import net.objectlab.kit.datecalc.common.IMMPeriod;

import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;

public class LocalDateIMMDateCalculator extends AbstractIMMDateCalculator<LocalDate> {
    protected static final int MONTHS_IN_QUARTER = 3;

    protected static final int MONTH_IN_YEAR = 12;

    protected static final int DAYS_IN_WEEK = 7;

    /**
     * Returns a list of IMM dates between 2 dates, it will exclude the start
     * date if it is an IMM date but would include the end date if it is an IMM.
     * 
     * @param start
     *            start of the interval, excluded
     * @param end
     *            end of the interval, may be included.
     * @param period
     *            specify when the "next" IMM is, if quarterly then it is the
     *            conventional algorithm.
     * @return list of IMM dates
     */
    public List<LocalDate> getIMMDates(final LocalDate start, final LocalDate end, final IMMPeriod period) {
        final List<LocalDate> dates = new ArrayList<LocalDate>();

        LocalDate date = start;
        while (true) {
            date = getNextIMMDate(true, date, period);
            if (!date.isAfter(end)) {
                dates.add(date);
            } else {
                break;
            }
        }

        return dates;
    }

    @Override
    protected LocalDate getNextIMMDate(final boolean requestNextIMM, final LocalDate start, final IMMPeriod period) {
        LocalDate date = start;

        final int month = date.getMonthOfYear();
        date = calculateIMMMonth(requestNextIMM, date, month);

        LocalDate imm = calculate3rdWednesday(date);
        final int immMonth = imm.getMonthOfYear();
        final boolean isMarchSept = DateTimeConstants.MARCH == immMonth || DateTimeConstants.SEPTEMBER == immMonth;

        if (period == IMMPeriod.BI_ANNUALY_JUN_DEC && isMarchSept || period == IMMPeriod.BI_ANNUALY_MAR_SEP && !isMarchSept) {
            imm = getNextIMMDate(requestNextIMM, imm, period);
        } else if (period == IMMPeriod.ANNUALLY) {
            // second jump
            imm = getNextIMMDate(requestNextIMM, imm, IMMPeriod.QUARTERLY);
            // third jump
            imm = getNextIMMDate(requestNextIMM, imm, IMMPeriod.QUARTERLY);
            // fourth jump
            imm = getNextIMMDate(requestNextIMM, imm, IMMPeriod.QUARTERLY);
            // fifth jump
            imm = getNextIMMDate(requestNextIMM, imm, IMMPeriod.QUARTERLY);
        }

        return imm;
    }

    private LocalDate calculateIMMMonth(final boolean requestNextIMM, LocalDate date, final int month) {
        int monthOffset = 0;

        switch (month) {
        case DateTimeConstants.MARCH:
        case DateTimeConstants.JUNE:
        case DateTimeConstants.SEPTEMBER:
        case DateTimeConstants.DECEMBER:
            final LocalDate immDate = calculate3rdWednesday(date);
            if (requestNextIMM && !date.isBefore(immDate)) {
                date = date.plusMonths(MONTHS_IN_QUARTER);
            } else if (!requestNextIMM && !date.isAfter(immDate)) {
                date = date.minusMonths(MONTHS_IN_QUARTER);
            }
            break;

        default:
            if (requestNextIMM) {
                monthOffset = (MONTH_IN_YEAR - month) % MONTHS_IN_QUARTER;
                date = date.plusMonths(monthOffset);
            } else {
                monthOffset = month % MONTHS_IN_QUARTER;
                date = date.minusMonths(monthOffset);
            }
            break;
        }
        return date;
    }

    /**
     * Assumes that the month is correct, get the day for the 2rd wednesday.
     * 
     * @param original
     *            the start date
     * @return the 3rd Wednesday of the month
     */
    private LocalDate calculate3rdWednesday(final LocalDate original) {
        final LocalDate firstOfMonth = original.withDayOfMonth(1);
        LocalDate firstWed = firstOfMonth.withDayOfWeek(MONTHS_IN_QUARTER);
        if (firstWed.isBefore(firstOfMonth)) {
            firstWed = firstWed.plusWeeks(1);
        }
        return firstWed.plusWeeks(2);
    }

    /**
     * Checks if a given date is an official IMM Date (3rd Wednesdays of
     * March/June/Sept/Dec.
     * 
     * @param date
     * @return true if that date is an IMM date.
     */
    public boolean isIMMDate(final LocalDate date) {
        boolean same = false;

        final List<LocalDate> dates = getIMMDates(date.minusDays(1), date, IMMPeriod.QUARTERLY);

        if (!dates.isEmpty()) {
            same = date.equals(dates.get(0));
        }

        return same;
    }
}