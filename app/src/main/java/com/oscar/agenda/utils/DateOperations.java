package com.oscar.agenda.utils;

import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Clase DateOperations con operaciones de manejo de fechas
 * Created by oscar on 06/11/16.
 */
public class DateOperations {

    public static final String DD_MM_YYYY_HH_MM    = "dd/MM/yyyy HH:mm";
    public static final String DD_MM_YYYY_HH_MM_SS = "dd/MM/yyyy HH:mm:ss";
    public static final String DD_MM_YYYY          = "dd/MM/yyyy";
    public static final String HH_MM               = "HH:mm";

    public enum FORMATO {
        DIA_MES_ANYO_HORA,
        DIA_MES_ANYO_HORA_MINUTOS_SEGUNDOS,
        DIA_MES_ANYO,
        HORA_MINUTOS
    }


    /**
     * Devuelve la fecha y hora actual según un determinado formato de fecha
     * @param formato
     * @return String
     */
    public static String getFechaHoraActual(FORMATO formato) {

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sf = null;
        String salida = null;
        String formatoFinal = "";

        switch(formato) {
            case DIA_MES_ANYO: {
                formatoFinal = DateOperations.DD_MM_YYYY;
                break;
            }

            case DIA_MES_ANYO_HORA_MINUTOS_SEGUNDOS: {
                formatoFinal = DateOperations.DD_MM_YYYY_HH_MM_SS;
                break;
            }

            case DIA_MES_ANYO_HORA: {
                formatoFinal = DateOperations.DD_MM_YYYY_HH_MM;
                break;
            }

            case HORA_MINUTOS: {
                formatoFinal = DateOperations.HH_MM;
                break;
            }

        }// switch

        sf = new SimpleDateFormat(formatoFinal);
        salida = sf.format(calendar.getTime());
        return salida;
    }



    /**
     * Devuelve la fecha y hora actual según un determinado formato de fecha
     * @param calendar Calendar
     * @param formato DateOperations.FORMATO
     * @return String
     */
    public static String getFecha(Calendar calendar,FORMATO formato) {
        SimpleDateFormat sf = null;
        String salida = null;
        String formatoFinal = "";

        if(calendar!=null) {
            switch (formato) {
                case DIA_MES_ANYO: {
                    formatoFinal = DateOperations.DD_MM_YYYY;
                    break;
                }

                case DIA_MES_ANYO_HORA_MINUTOS_SEGUNDOS: {
                    formatoFinal = DateOperations.DD_MM_YYYY_HH_MM_SS;
                    break;
                }

                case HORA_MINUTOS: {
                    formatoFinal = DateOperations.HH_MM;
                    break;
                }

            }// switch


            sf = new SimpleDateFormat(formatoFinal);
            salida = sf.format(calendar.getTime());
        }

        return salida;
    }


    /**
     * Devuelve el mes actual
     * @return Integer
     */
    public static Integer getActualMonth() {
        Integer month = null;

        try {
            month = Calendar.getInstance().get(Calendar.MONTH) + 1;

        } catch(Exception e) {
            e.printStackTrace();
        }
        return month;
    }


    /**
     * Obtiene un Calendar a partir de una fecha en formato String que se encuentra en formato
     * @param fecha String
     * @return
     */
    public static Calendar getCalendar(String fecha) {
        Calendar calendar = null;
        try {
            SimpleDateFormat sf = new SimpleDateFormat(DateOperations.DD_MM_YYYY);
            Date date = sf.parse(fecha);

            calendar = Calendar.getInstance();
            calendar.clear();
            calendar.setTime(date);

        } catch (Exception e) {
            calendar = null;
        }

        return calendar;
    }




    /**
     * Suma o resta un determinado número de días a un Calendar
     * @param calendar Calendar
     * @param day int
     * @return Calendar
     */
    public static Calendar addDayToCalendar(Calendar calendar,int day) {
        calendar.add(Calendar.DAY_OF_MONTH,day);
        return calendar;
    }


    /**
     * Suma o resta un determinado número de horas a un Calendar
     * @param calendar Calendar
     * @param day int
     * @return Calendar
     */
    public static Calendar addHourToCalendar(Calendar calendar,int day) {
        calendar.add(Calendar.HOUR_OF_DAY,day);
        return calendar;
    }


    /**
     * Convierte un String que contenga una fecha en un objeto de tipo Calendar.
     * La fecha tiene que estar en formato dd/MM/yyyy
     * @param date
     * @return Calendar
     */
    public static Calendar stringToCalendar(String date) {
        Calendar salida = null;

        try {
            SimpleDateFormat sf = new SimpleDateFormat(DD_MM_YYYY_HH_MM);
            Date d = sf.parse(date);

            salida = Calendar.getInstance();
            salida.clear();
            salida.setTime(d);

        } catch(Exception e) {
            salida = null;
            e.printStackTrace();
        }

        return salida;
    }

    /**
     * Devuelve un objeto calendar a partir de un día, mes y año
     * @param day int
     * @param month int
     * @param year int
     * @return Calendar o null si la fecha proporcionada no es válida
     */
    public static Calendar getCalendar(int day,int month, int year) {
        Calendar salida = null;

        try {

            salida = Calendar.getInstance();
            salida.clear();
            salida.set(year,month,day);

        } catch(Exception e) {
            salida = null;
            LogCat.error("Error al obtener un calendar a partir de un dia. mes y anyo:∫ " + e.getMessage());
        }

        return salida;
    }


    /**
     * Devuelve la hora en un determinado formato HH:mm en un String
     * @param hora
     * @param minutos
     * @return
     */
    public static String getHora(int hora,int minutos) {
        String salida = null;

        try {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY,hora);
            calendar.set(Calendar.MINUTE,minutos);

            SimpleDateFormat sf = new SimpleDateFormat(HH_MM);
            salida = sf.format(calendar.getTime());

        } catch(Exception e) {
            e.printStackTrace();
            salida = null;
        }
        return salida;
    }


    /**
     * Obtiene un Calendar a partir de un objeto de la clase CaledarDay
     * @param date CalendarDay
     * @return Calendar
     */
    public static Calendar convert(CalendarDay date) {
        Calendar c = Calendar.getInstance();

        c.clear();
        c.set(Calendar.DAY_OF_MONTH,date.getDay());
        c.set(Calendar.MONTH,date.getMonth());
        c.set(Calendar.YEAR,date.getYear());
        return c;
    }

}