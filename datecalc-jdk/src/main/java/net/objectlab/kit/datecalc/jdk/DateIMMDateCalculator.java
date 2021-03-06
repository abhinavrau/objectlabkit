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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import net.objectlab.kit.datecalc.common.AbstractIMMDateCalculator;
import net.objectlab.kit.datecalc.common.IMMPeriod;
import net.objectlab.kit.datecalc.common.Utils;

/**
 * Jdk <code>Date</code> based implementation of the
 * {@link net.objectlab.kit.datecalc.common.IMMDateCalculator}. It simply
 * delegates to the Calendar implementation.
 * 
 * @author Marcin Jekot
 * @author $LastChangedBy$
 * @version $Revision$ $Date$
 * 
 */
public class DateIMMDateCalculator extends AbstractIMMDateCalculator<Date> {

    private static final CalendarIMMDateCalculator DELEGATE = new CalendarIMMDateCalculator();

    @Override
    protected Date getNextIMMDate(final boolean requestNextIMM, final Date theStartDate, final IMMPeriod period) {
        return DELEGATE.getNextIMMDate(requestNextIMM, Utils.getCal(theStartDate), period).getTime();
    }

    // -----------------------------------------------------------------------
    //
    //    ObjectLab, world leaders in the design and development of bespoke 
    //          applications for the securities financing markets.
    //                         www.ObjectLab.co.uk
    //
    // -----------------------------------------------------------------------

    public List<Date> getIMMDates(final Date start, final Date end, final IMMPeriod period) {
        return buildList(DELEGATE.getIMMDates(Utils.getCal(start), Utils.getCal(end), period));
    }

    public boolean isIMMDate(final Date date) {
        return DELEGATE.isIMMDate(Utils.getCal(date));
    }

    private List<Date> buildList(final List<Calendar> dates) {
        final List<Date> imms = new ArrayList<Date>();
        for (final Calendar date : dates) {
            imms.add(date.getTime());
        }
        return imms;
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
