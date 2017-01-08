package com.oscar.agenda.adapter;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.oscar.agenda.activities.WelcomeActivity;
import com.oscar.agenda.database.entity.EventoVO;
import com.oscar.agenda.utils.listener.api.OnItemClickListener;
import com.oscar.agenda.utils.listener.eventos.OnItemClickCallbackBorrarEvento;

import java.util.List;

import agenda.oscar.com.agenda.R;

/**
 * Created by oscar on 21/11/16.
 */


/**
 * Adapter para las noticias.
 * Implementa la interfaz View.OnClickListener para detectar el evento de selección de uno
 * de los elementos del adaptador
 * Created by oscar on 11/06/16.
 */
public class EventosAdapter extends RecyclerView.Adapter<EventosAdapter.EventoViewHolder> implements View.OnClickListener {

    private List<EventoVO> items = null;
    private String origen = null;
    private Resources resources = null;
    private View.OnClickListener listener = null;
    private WelcomeActivity actividad = null;

    /**
     * Clase EventoViewHolder que contiene los componentes que forman
     * parte de la vista a renderizar para cada serie recuperada de la base de datos
     *
     */
    public static class EventoViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        public TextView titulo;
        public TextView fechaHora;
        public ImageView imgBorrarEvento;
        public ImageView imgEditarEvento;


        /**
         * Constructor
         * @param v: View
         */
        public EventoViewHolder(View v) {
            super(v);
            titulo          = (TextView) v.findViewById(R.id.titulo);
            fechaHora       = (TextView)v.findViewById(R.id.fechaHora);
            imgEditarEvento = (ImageView)v.findViewById(R.id.imgEditarEvento);
            imgBorrarEvento = (ImageView)v.findViewById(R.id.imgBorrarEvento);


        }
    }


    /**
     * Constructor
     * @param items List<EventoVO>
     */
    public EventosAdapter(List<EventoVO> items, Resources resources, WelcomeActivity actividad) {
        this.items       = items;
        this.resources   = resources;
        this.actividad   = actividad;
    }


    /**
     * Devuelve la lista de eventos
     * @return List<EventoVO>
     */
    public List<EventoVO> getItems() {
        return items;
    }

    /**
     * Establece la lista de eventos
     * @param items List<EventoVO>
     */
    public void setItems(List<EventoVO> items) {
        this.items = items;
    }

    /**
     * Devuelve el número de noticias que se muestran
     * @return int
     */
    @Override
    public int getItemCount() {
        return items.size();
    }


    /**
     * Añade una serie
     * @param evento EventoVO
     */
    public void addItem(EventoVO evento) {
        this.items.add(evento);
    }


    /**
     * Elimina un elemento del adapter de series
     * @param pos Posición del elemento a borrar
     */
    public void removeItem(int pos) {
        getItems().remove(pos);
    }


    /**
     * Devuelve un evento
     * @param pos int que indica una posición válida de la colección de eventos
     * @return EventoVO
     */
    public EventoVO getEvento(int pos) {
        EventoVO evento = null;
        if(pos>=0 && pos<getItems().size()) {
            evento = getItems().get(pos);
        }
        return evento;
    }

    @Override
    public EventoViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        /** Se carga el layout noticia.xml para mostrar la información de cada noticia **/
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.detalle_evento, viewGroup, false);

        v.setOnClickListener(this);
        return new EventoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(EventoViewHolder viewHolder, int i) {

        viewHolder.titulo.setText(items.get(i).getNombre());
        viewHolder.fechaHora.setText(items.get(i).getFechaDesde() + "  " + items.get(i).getHoraDesde() + " - " + items.get(i).getHoraHasta());

        OnItemClickCallbackBorrarEvento p = new OnItemClickCallbackBorrarEvento(items,this.actividad);
        viewHolder.imgBorrarEvento.setOnClickListener(new OnItemClickListener(i,p));
    }


    /**
     * Establecer el listener de tipo OnClickListener
     * @param listener
     */
    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    /**
     * Cuando el usuario ejecuta el método onClick sobre la vista
     * @param view: View
     */
    public void onClick(View view) {
        if(listener != null) {
            listener.onClick(view);
        }
    }
}