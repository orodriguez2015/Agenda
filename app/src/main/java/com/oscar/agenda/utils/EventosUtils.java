package com.oscar.agenda.utils;

import com.oscar.agenda.database.entity.EventoVO;
import com.oscar.agenda.utils.log.LogCat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * Clase con operaciones de utilidad sobre el manejo de eventos
 * Created by oscar on 28/01/17.
 */
public class EventosUtils {

    /**
     * Convierte una colección de ventos en un Map cuya clave es la fecha de un día, y que contiene
     * una colección de eventos
     * @param eventos List<EventoVO>
     * @return HashMap<Calendar,List<EventoVO>>
     */
    public static HashMap<Calendar,List<EventoVO>> convert(List<EventoVO> eventos) {
        HashMap<Calendar,List<EventoVO>> map = new HashMap<Calendar,List<EventoVO>>();

        try {

            for(int i=0;eventos!=null && i<eventos.size();i++) {
                Calendar fecha = DateOperations.getCalendar(eventos.get(i).getFechaDesde());
                if(map.containsKey(fecha)) {
                   map.get(fecha).add(eventos.get(i));
                } else {
                    List<EventoVO> aux = new ArrayList<EventoVO>();
                    aux.add(eventos.get(i));
                    map.put(fecha,aux);
                }

            }//for

        } catch(Exception e) {
            LogCat.error("Error al convertir la colección de eventos en un map: " + e.getMessage());
        }
        return map;
    }

}
