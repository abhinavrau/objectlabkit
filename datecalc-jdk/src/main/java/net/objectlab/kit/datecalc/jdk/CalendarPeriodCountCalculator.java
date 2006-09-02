/*
 * $Id:$
 * 
 * Copyright 2006 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package net.objectlab.kit.datecalc.jdk;

import java.util.Calendar;

import net.objectlab.kit.datecalc.common.PeriodCountBasis;
import net.objectlab.kit.datecalc.common.PeriodCountCalculator;

/**
 * TODO javadoc
 *
 * @author Marcin Jekot
 * @author $LastChangedBy$
 * @version $Revision$ $Date$
 *
 */
public class CalendarPeriodCountCalculator implements PeriodCountCalculator<Calendar> {

    public int dayDiff(final Calendar start, final Calendar end, final PeriodCountBasis basis) {
        
        int diff = 0;

        if (basis == PeriodCountBasis.CONV_30_360) {
            int dayStart = start.get(Calendar.DAY_OF_MONTH);
            int dayEnd = end.get(Calendar.DAY_OF_MONTH);
            if (dayEnd == MONTH_31_DAYS && dayStart >= MONTH_30_DAYS) {
                dayEnd = MONTH_30_DAYS;
            }
            if (dayStart == MONTH_31_DAYS) {
                dayStart = MONTH_30_DAYS;
            }
            diff = (end.get(Calendar.YEAR) - start.get(Calendar.YEAR)) * YEAR_360 + (end.get(Calendar.MONTH) - start.get(Calendar.MONTH)) * MONTH_30_DAYS
                    + dayEnd - dayStart;

        } else if (basis == PeriodCountBasis.CONV_360E_ISDA) {
            int dayStart = start.get(Calendar.DAY_OF_MONTH);
            int dayEnd = end.get(Calendar.DAY_OF_MONTH);
            final int monthStart = start.get(Calendar.MONTH);
            if ((monthStart == 2 && start.getActualMaximum(Calendar.DAY_OF_MONTH) == dayStart) || dayEnd == MONTH_31_DAYS) {
                dayEnd = MONTH_30_DAYS;
            }
            if (dayStart == MONTH_31_DAYS) {
                dayStart = MONTH_30_DAYS;
            }

            diff = (end.get(Calendar.YEAR) - start.get(Calendar.YEAR)) * YEAR_360 + (end.get(Calendar.MONTH) - start.get(Calendar.MONTH)) * MONTH_30_DAYS
                    + dayEnd - dayStart;

        } else if (basis == PeriodCountBasis.CONV_360E_ISMA) {
            int dayStart = start.get(Calendar.DAY_OF_MONTH);
            int dayEnd = end.get(Calendar.DAY_OF_MONTH);
            if (dayEnd == MONTH_31_DAYS) {
                dayEnd = MONTH_30_DAYS;
            }
            if (dayStart == MONTH_31_DAYS) {
                dayStart = MONTH_30_DAYS;
            }
            diff = (end.get(Calendar.YEAR) - start.get(Calendar.YEAR)) * YEAR_360 + (end.get(Calendar.MONTH) - start.get(Calendar.MONTH)) * MONTH_30_DAYS
                    + dayEnd - dayStart;
        } else {
            // TODO implement this
            diff = 0;
//            final Period p = new Period(start, end, PeriodType.days());
//            diff = p.getDays();
        }
        return diff;
    }

    public double monthDiff(final Calendar start, final Calendar end, final PeriodCountBasis basis) {
        return yearDiff(start, end, basis) * MONTHS_IN_YEAR;
    }

    public double yearDiff(final Calendar start, final Calendar end, final PeriodCountBasis basis) {
        double diff = 0.0;
        if (basis == PeriodCountBasis.ACT_ACT) {
            final int startYear = start.get(Calendar.YEAR);
            final int endYear = end.get(Calendar.YEAR);
            if (startYear != endYear) {
                final Calendar endOfStartYear = (Calendar) start.clone();
                endOfStartYear.set(Calendar.DAY_OF_YEAR, endOfStartYear.getActualMaximum(Calendar.DAY_OF_YEAR));
                final Calendar startOfEndYear = (Calendar) end.clone();
                startOfEndYear.set(Calendar.DAY_OF_YEAR, startOfEndYear.getActualMinimum(Calendar.DAY_OF_YEAR));

                Calendar c1 = Calendar.getInstance();
                c1.clone();
                
                // TODO implement this
                diff = 0;
//                final int diff1 = new Period(start, endOfStartYear, PeriodType.days()).getDays();
//                final int diff2 = new Period(startOfEndYear, end, PeriodType.days()).getDays();
//                diff = (diff1 + 1.0) / start.dayOfYear().getMaximumValue() + (endYear - startYear - 1.0) + (diff2)
//                        / (double) end.dayOfYear().getMaximumValue();
            }

        } else if (basis == PeriodCountBasis.CONV_30_360 || basis == PeriodCountBasis.CONV_360E_ISDA
                || basis == PeriodCountBasis.CONV_360E_ISMA || basis == PeriodCountBasis.ACT_360) {
            diff = (dayDiff(start, end, basis)) / YEAR_360_0;

        } else if (basis == PeriodCountBasis.ACT_365 || basis == PeriodCountBasis.END_365) {
            diff = (dayDiff(start, end, basis)) / YEAR_365_0;
        } else {
            throw new UnsupportedOperationException("Sorry no ACT_UST yet");
        }
        return diff;
    }

}