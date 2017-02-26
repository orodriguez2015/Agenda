package com.oscar.agenda.decorator;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.oscar.agenda.database.entity.EventoVO;
import com.oscar.agenda.utils.EventosUtils;
import com.oscar.agenda.utils.date.CalendarDayOperations;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import agenda.oscar.com.agenda.R;

/**
 * Decorador para los días que tiene eventos en el calendario
 * Created by oscar on 28/01/17.
 */
public class DecoratorEventDay implements DayViewDecorator {

    private Context context = null;
    private HashMap<Calendar,List<EventoVO>> mapEventos = null;

    /**
     * Constructor
     * @param context Context
     * @param eventos List<EventoVO>
     */
    public DecoratorEventDay(Context context, List<EventoVO> eventos) {
        this.context = context;
        this.mapEventos = EventosUtils.convert(eventos);
    }

    /**
     * Método que indica si se decora un día determinado del calendario
     * @param day CalendarDay
     * @return boolean
     */
    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return mapEventos.containsKey(CalendarDayOperations.convert(day));
    }


    /**
     * Decora un día determinado del MaterialCalendarview para el que hay eventos
     * @param view DayViewFacade
     */
    @Override
    public void decorate(DayViewFacade view) {

        int color = ContextCompat.getColor(context, R.color.darkgreen);
        view.addSpan(new DotSpanTop(7.5f,color));

    }

}