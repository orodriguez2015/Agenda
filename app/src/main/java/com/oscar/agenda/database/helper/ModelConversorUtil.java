package com.oscar.agenda.database.helper;

import android.content.ContentValues;

import com.oscar.agenda.database.colums.ColumnasBD;
import com.oscar.agenda.database.entity.EventoVO;
import com.oscar.agenda.database.entity.SerieVO;


/**
 * Created by oscar on 06/11/16.
 */

public class ModelConversorUtil {

    /**
     * Convierte un objeto de la clase SerieVO en el ContentValues que se usará para
     * persistir la información en la base de datos
     * @param serie SerieVO
     * @return ContentValues
     */
    public static ContentValues toContentValues(SerieVO serie) {

        ContentValues values = new ContentValues();
        values.put(ColumnasBD.SerieEntry.TITULO,serie.getTitulo());
        values.put(ColumnasBD.SerieEntry.DESCRIPCION,serie.getDescripcion());
        values.put(ColumnasBD.SerieEntry.FECHA_PUBLICACION,serie.getFechaPublicacion());
        return values;
    }


    /**
     * Convierte un objeto de la clase SerieVO en el ContentValues que se usará para
     * persistir la información en la base de datos
     * @param evento EventoVO
     * @return ContentValues
     */
    public static ContentValues toContentValues(EventoVO evento) {
        ContentValues values = new ContentValues();
        values.put(ColumnasBD.AgendaEntry.NOMBRE,evento.getNombre());
        values.put(ColumnasBD.AgendaEntry.FECHA_DESDE, evento.getFechaDesde());
        values.put(ColumnasBD.AgendaEntry.FECHA_HASTA, evento.getFechaHasta());
        values.put(ColumnasBD.AgendaEntry.HORA_DESDE,evento.getHoraDesde());
        values.put(ColumnasBD.AgendaEntry.HORA_HASTA,evento.getHoraHasta());
        values.put(ColumnasBD.AgendaEntry.FECHA_PUBLICACION,evento.getFechaPublicacion());
        values.put(ColumnasBD.AgendaEntry.HORA_PUBLICACION,evento.getHoraPublicacion());
        values.put(ColumnasBD.AgendaEntry.RECORDATORIO_1,evento.getRecordatorio1());
        values.put(ColumnasBD.AgendaEntry.RECORDATORIO_2,evento.getRecordatorio2());
        values.put(ColumnasBD.AgendaEntry.RECORDATORIO_3,evento.getRecordatorio3());
        values.put(ColumnasBD.AgendaEntry.ESTADO,evento.getEstado());

        return values;
    }
}
