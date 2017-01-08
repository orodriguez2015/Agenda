package com.oscar.agenda.database.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.oscar.agenda.database.colums.ColumnasBD;
import com.oscar.agenda.database.entity.EventoVO;
import com.oscar.agenda.database.entity.SerieVO;
import com.oscar.agenda.exception.DatabaseException;
import com.oscar.agenda.utils.DateOperations;
import com.oscar.agenda.utils.LogCat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Clase SerieDBHelper encargada de crear la base de datos SQLite y de actualizarla
 * cuando sea preciso. Contiene operaciones de utiliza
 * Created by oscar on 05/11/16.
 */
public class SerieDBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "AppyNews.db";

    /**
     * Constructor
     * @param context: Objeto de la clase Context
     */
    public SerieDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    /**
     * Método onCreate que se encarga de crear la base de datos
     * @param sqLiteDatabase: Manejador de la base de datos
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){

        try {
            LogCat.info("onCreate init()");

            // Se crea la tabla Agenda
            sqLiteDatabase.execSQL("CREATE TABLE " + ColumnasBD.AgendaEntry.TABLE_NAME + " ("
                    + ColumnasBD.AgendaEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + ColumnasBD.AgendaEntry.NOMBRE + " TEXT,"
                    + ColumnasBD.AgendaEntry.FECHA_DESDE + " TEXT,"
                    + ColumnasBD.AgendaEntry.HORA_DESDE + " TEXT,"
                    + ColumnasBD.AgendaEntry.FECHA_HASTA + " TEXT,"
                    + ColumnasBD.AgendaEntry.HORA_HASTA + " TEXT,"
                    + ColumnasBD.AgendaEntry.FECHA_PUBLICACION + " TEXT,"
                    + ColumnasBD.AgendaEntry.HORA_PUBLICACION + " TEXT,"
                    + ColumnasBD.AgendaEntry.RECORDATORIO_1 + " TEXT,"
                    + ColumnasBD.AgendaEntry.RECORDATORIO_2 + " TEXT,"
                    + ColumnasBD.AgendaEntry.RECORDATORIO_3 + " TEXT,"
                    + ColumnasBD.AgendaEntry.ESTADO + " INT)");


            /**
            // Se crea la tabla Serie
            sqLiteDatabase.execSQL("CREATE TABLE " + ColumnasBD.SerieEntry.TABLE_NAME + " ("
                    + ColumnasBD.SerieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + ColumnasBD.SerieEntry.DESCRIPCION + " TEXT,"
                    + ColumnasBD.SerieEntry.TITULO + " TEXT,"
                    + ColumnasBD.SerieEntry.FECHA_PUBLICACION + " DATE)");

             **/
            /**
            sqLiteDatabase.execSQL("INSERT INTO " + AppyNewsContract.OrigenEntry.TABLE_NAME
                    + " ("  + AppyNewsContract.OrigenEntry.URL + "," + AppyNewsContract.OrigenEntry.DESCRIPCION + ") "
                + "VALUES('http://feeds.weblogssl.com/xataka2','Xataka')");

            LogCat.info("onCreate end()");
            **/

        }catch(Exception e) {
            e.printStackTrace();
            LogCat.error("Se ha producido un error al crear la BD en el método onCreate: ".concat(e.getMessage()));
        }
    }


    /**
     * Método onUpgrade que se encarga de actualizar la base de datos
     * @param sqLiteDatabase: Manejador de la base de datos
     * @param i: Versión antigua de la base de datos
     * @param i1: Nueva versión de la base de datos
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        LogCat.debug("onUpgrade init");
        LogCat.debug("onUpgrade end");
    }


    /**
     * Recupera las noticias de la base de datos
     * @return List<Noticia>
     * @throws DatabaseException
     */
    public List<SerieVO> getSeries() throws DatabaseException {
        List<SerieVO> series = new ArrayList<SerieVO>();
        SQLiteDatabase db = null;
        Cursor rs = null;

        try {
            LogCat.info("getSeries() init");
            String sql = "select _id,titulo,descripcion,fechaPublicacion from serie order by titulo asc";
            LogCat.debug("sql: " + sql);

            db = getReadableDatabase();
            rs = db.rawQuery(sql,null);

            if(rs!=null && rs.getCount()>0 && rs.moveToFirst()) {
                LogCat.debug("Numero series recuperadas: " + rs.getCount());

                do {
                    SerieVO serie = new SerieVO();
                    serie.setId(rs.getInt(0));
                    serie.setTitulo(rs.getString(1));
                    serie.setDescripcion(rs.getString(2));
                    serie.setFechaPublicacion(rs.getString(3));
                    series.add(serie);

                } while(rs.moveToNext());

            } else
                LogCat.debug("No existe el usuario");

            LogCat.info("getSeries() end");

        } catch(Exception e) {
            e.printStackTrace();
            throw new DatabaseException(DatabaseErrors.ERROR_RECUPERAR_SERIES,"Error al recuperar las series de la base de datos: ".concat(e.getMessage()));
        } finally {
            if(rs!=null) rs.close();
            if(db!=null) db.close();
        }
        return series;
    }

    /**
     * Graba una serie en la base de datos
     * @param serie Objeto de la clase SerieVO a dar de alta en BD
     */
    public void saveSerie(SerieVO serie) throws DatabaseException {
        SQLiteDatabase db = getWritableDatabase();

        try {
            LogCat.info("saveSerie init");
            Long id = db.insert(ColumnasBD.SerieEntry.TABLE_NAME, null, ModelConversorUtil.toContentValues(serie));
            serie.setId(id.intValue());
            LogCat.info("saveSerie end");


        } catch(Exception e) {
            e.printStackTrace();
            throw new DatabaseException(DatabaseErrors.ERROR_INSERTAR_SERIE,"Error al grabar la serie en la base de datos: ".concat(e.getMessage()));
        } finally {
            if(db!=null) {
                db.close();
            }
        }
    }



    /**
     * Elimina una serie de la base de datos
     * @param serie SerieVO
     */
    public void deleteSerie(SerieVO serie) throws DatabaseException {
        SQLiteDatabase db = getWritableDatabase();

        try {
            LogCat.info("deleteSerie init");
            //db.delete(DATABASE_TABLE, KEY_NAME + "=" + name, null) > 0;
            db.delete(ColumnasBD.SerieEntry.TABLE_NAME, ColumnasBD.SerieEntry._ID + "=" + serie.getId(), null);
            LogCat.info("deleteSerie end");

        } catch(Exception e) {
            e.printStackTrace();
            throw new DatabaseException(DatabaseErrors.ERROR_ELIMINAR_SERIE,"Error al eliminar la serie de id " + serie.getId() + " de la base de datos: " + e.getMessage());
        } finally {
            if(db!=null) {
                db.close();
            }
        }
    }


    /**
     * Actualiza una determinada fuente/origen de datos en la base de datos
     * @param serie SerieVO
     */
    public void updateSerie(SerieVO serie) throws DatabaseException {
        SQLiteDatabase db = getWritableDatabase();

        try {
            LogCat.info("updateSerie init");
            String[] paramsWhere = {serie.getId().toString()};
            db.update(ColumnasBD.SerieEntry.TABLE_NAME, ModelConversorUtil.toContentValues(serie),"_id=?",paramsWhere);

            LogCat.info("updateSerie end");

        } catch(Exception e) {
            e.printStackTrace();
            throw new DatabaseException(DatabaseErrors.ERROR_ACTUALIZAR_SERIE,"Error al actualizar la serie en la base de datos: " + e.getMessage());
        } finally {
            if(db!=null) {
                db.close();
            }
        }
    }



    /**
     * Recupera los eventos de la base de datos
     * @param fecha Calendar
     * @return List<EventoVO>
     * @throws DatabaseException
     */
    public List<EventoVO> getEventos(Calendar fecha) throws DatabaseException {
        List<EventoVO> eventos = new ArrayList<EventoVO>();
        String sFecha = "";
        SQLiteDatabase db = null;
        Cursor rs = null;

        try {
            LogCat.info("getEventos() init");
            sFecha = DateOperations.getFecha(fecha, DateOperations.FORMATO.DIA_MES_ANYO);

            String sql = "select _id,nombre,fecha_desde,hora_desde,fecha_hasta,hora_hasta,fecha_publicacion,hora_publicacion,recordatorio_1,recordatorio_2,recordatorio_2 from evento ".
                         concat("where fecha_desde='").concat(sFecha).concat("'").concat(" order by hora_desde desc,_id asc");

            LogCat.debug("sql: " + sql);

            db = getReadableDatabase();
            rs = db.rawQuery(sql,null);

            if(rs!=null && rs.getCount()>0 && rs.moveToFirst()) {
                LogCat.debug("Numero eventos recuperadas: " + rs.getCount());

                do {
                    EventoVO evento = new EventoVO();

                    evento.setId(rs.getInt(0));
                    evento.setNombre(rs.getString(1));
                    evento.setFechaDesde(rs.getString(2));
                    evento.setHoraDesde(rs.getString(3));
                    evento.setFechaHasta(rs.getString(4));
                    evento.setHoraHasta(rs.getString(5));
                    evento.setFechaHasta(rs.getString(6));
                    evento.setRecordatorio1(rs.getString(7));
                    evento.setRecordatorio2(rs.getString(8));
                    evento.setRecordatorio3(rs.getString(9));
                    eventos.add(evento);

                } while(rs.moveToNext());

            } else
                LogCat.debug("No hay eventos para el día actual");

            LogCat.info("getEventos() end");

        } catch(Exception e) {
            e.printStackTrace();
            throw new DatabaseException(DatabaseErrors.ERROR_RECUPERAR_EVENTOS,"Error al recuperar los eventos de fecha_desde ".concat(sFecha).concat(": ").concat(e.getMessage()));
        } finally {
            if(rs!=null) rs.close();
            if(db!=null) db.close();
        }
        return eventos;
    }


    /**
     * Recupera los eventos de un determinado mes de la base de datos
     * @param mes Número de mes
     * @return List<EventoVO>
     * @throws DatabaseException
     */
    public List<EventoVO> getEventosMes(Integer mes) throws DatabaseException {
        List<EventoVO> eventos = new ArrayList<EventoVO>();
        String sFecha = "";
        SQLiteDatabase db = null;
        Cursor rs = null;

        try {
            LogCat.info("getEventosMes() init");

            String sql = "select _id,nombre,fecha_desde,hora_desde,fecha_hasta,hora_hasta,fecha_publicacion,hora_publicacion,recordatorio_1,recordatorio_2,recordatorio_2 from evento ".
                    concat("where fecha_desde like '%").concat(mes.toString()).concat("%'");

            LogCat.debug("sql: " + sql);

            db = getReadableDatabase();
            rs = db.rawQuery(sql,null);

            if(rs!=null && rs.getCount()>0 && rs.moveToFirst()) {
                LogCat.debug("Numero eventos recuperadas: " + rs.getCount());

                do {
                    EventoVO evento = new EventoVO();

                    evento.setId(rs.getInt(0));
                    evento.setNombre(rs.getString(1));
                    evento.setFechaDesde(rs.getString(2));
                    evento.setHoraDesde(rs.getString(3));
                    evento.setFechaHasta(rs.getString(4));
                    evento.setHoraHasta(rs.getString(5));
                    evento.setFechaHasta(rs.getString(6));
                    evento.setRecordatorio1(rs.getString(7));
                    evento.setRecordatorio2(rs.getString(8));
                    evento.setRecordatorio3(rs.getString(9));
                    eventos.add(evento);

                } while(rs.moveToNext());

            } else
                LogCat.debug("No hay eventos para el día actual");

            LogCat.info("getEventos() end");

        } catch(Exception e) {
            e.printStackTrace();
            throw new DatabaseException(DatabaseErrors.ERROR_RECUPERAR_EVENTOS_MES,"Error al recuperar los eventos del mes ".concat(mes.toString()).concat(": ").concat(e.getMessage()));
        } finally {
            if(rs!=null) rs.close();
            if(db!=null) db.close();
        }
        return eventos;
    }



    /**
     * Graba un evento en la base de datos
     * @param evento EventoVO que contiene la info del evento a grabar en base de datos
     */
    public void saveEvento(EventoVO evento) throws DatabaseException {
        SQLiteDatabase db = getWritableDatabase();

        try {
            LogCat.info("saveSerie init");
            Long id = db.insert(ColumnasBD.AgendaEntry.TABLE_NAME, null, ModelConversorUtil.toContentValues(evento));
            evento.setId(id.intValue());
            LogCat.info("saveSerie end");


        } catch(Exception e) {
            e.printStackTrace();
            throw new DatabaseException(DatabaseErrors.ERROR_INSERTAR_EVENTO,"Error al grabar el evento en la base de datos: ".concat(e.getMessage()));
        } finally {
            if(db!=null) {
                db.close();
            }
        }
    }


    /**
     * Elimina un evento de la base de datos
     * @param evento EventoVO
     */
    public void deleteEvento(EventoVO evento) throws DatabaseException {
        SQLiteDatabase db = getWritableDatabase();

        try {
            LogCat.info("deleteEvento init");
            db.delete(ColumnasBD.AgendaEntry.TABLE_NAME, ColumnasBD.AgendaEntry._ID + "=" + evento.getId(), null);
            LogCat.info("deleteEvento end");

        } catch(Exception e) {
            e.printStackTrace();
            throw new DatabaseException(DatabaseErrors.ERROR_ELIMINAR_EVENTO,"Error al eliminar el evento id " + evento.getId() + " de la base de datos: " + e.getMessage());
        } finally {
            if(db!=null) {
                db.close();
            }
        }
    }






}
