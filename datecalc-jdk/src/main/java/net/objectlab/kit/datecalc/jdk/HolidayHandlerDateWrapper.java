/*
 * ObjectLab, http://www.objectlab.co.uk/open is sponsoring the ObjectLab Kit.
 * 
 * Based in London, we are world leaders in the design and development 
 * of bespoke applications for the securities financing markets.
 * 
 * <a href="http://www.objectlab.co.uk/open">Click here to learn more</a>
 *           ___  _     _           _   _          _
 *          / _ \| |__ (_) ___  ___| |_| |    __ _| |__
 *         | | | | '_ \| |/ _ \/ __| __| |   / _` | '_ \
 *         | |_| | |_) | |  __/ (__| |_| |__| (_| | |_) |
 *          \___/|_.__// |\___|\___|\__|_____\__,_|_.__/
 *                   |__/
 *
 *                     www.ObjectLab.co.uk
 *
 * $Id$
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
import java.util.Date;

import net.objectlab.kit.datecalc.common.DateCalculator;
import net.objectlab.kit.datecalc.common.HolidayHandler;
import net.objectlab.kit.datecalc.common.Utils;

/**
 * A Wrapper to handle any HolidayHandler<Calendar> types via a HolidayHandler<Date>
 * delegate
 * 
 * @author Marcin Jekot
 * @author $LastChangedBy$
 * @version $Revision$ $Date$
 * 
 */
public class HolidayHandlerDateWrapper implements HolidayHandler<Calendar> {

    private HolidayHandler<Date> delegate;

    private DateCalculator<Date> calculator;

    public HolidayHandlerDateWrapper(final HolidayHandler<Date> holidayHandler, final DateCalculator<Date> dateCalculator) {
        delegate = holidayHandler;
        calculator = dateCalculator;
    }

    // -----------------------------------------------------------------------
    //
    //    ObjectLab, world leaders in the design and development of bespoke 
    //          applications for the securities financing markets.
    //                         www.ObjectLab.co.uk
    //
    // -----------------------------------------------------------------------

   /**
     * If the current date of the give calculator is a non-working day, it will
     * be moved according to the algorithm implemented.
     * 
     * @param calc
     *            the calculator
     * @return the date which may have moved.
     */
    public Calendar moveCurrentDate(final DateCalculator<Calendar> calc) {
        Calendar ret = calc.getCurrentBusinessDate();
        if (delegate != null) {
            final Date day = delegate.moveCurrentDate(calculator);
            if (day != null) {
                ret = Utils.getCal(day);
            }
        }
        return ret;
    }

    /**
     * Give the type name for this algorithm.
     * 
     * @return algorithm name.
     */
    public String getType() {
        return delegate.getType();
    }
}

/*
 * ObjectLab, http://www.objectlab.co.uk/open is sponsoring the ObjectLab Kit.
 * 
 * Based in London, we are world leaders in the design and development 
 * of bespoke applications for the securities financing markets.
 * 
 * <a href="http://www.objectlab.co.uk/open">Click here to learn more about us</a>
 *           ___  _     _           _   _          _
 *          / _ \| |__ (_) ___  ___| |_| |    __ _| |__
 *         | | | | '_ \| |/ _ \/ __| __| |   / _` | '_ \
 *         | |_| | |_) | |  __/ (__| |_| |__| (_| | |_) |
 *          \___/|_.__// |\___|\___|\__|_____\__,_|_.__/
 *                   |__/
 *
 *                     www.ObjectLab.co.uk
 */
