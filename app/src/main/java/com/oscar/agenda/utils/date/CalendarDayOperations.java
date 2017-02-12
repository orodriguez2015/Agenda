package com.oscar.agenda.utils.date;

import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.Calendar;

/**
 * Created by oscar on 12/02/17.
 */

public class CalendarDayOperations {


    /**
     * Convierte un CalendarDay en un Calendar
     * @param day CalendarDay
     * @return Calendar
     */
    public static Calendar convert(CalendarDay day) {

        Calendar c = null;
        try {
            c = Calendar.getInstance();
            c.clear();

            c.set(Calendar.DAY_OF_MONTH,day.getDay());
            c.set(Calendar.YEAR,day.getYear());
            c.set(Calendar.MONTH,day.getMonth());

        }catch(Exception e) {
            e.printStackTrace();
        }
        return c;
    }
}
