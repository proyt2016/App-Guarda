package com.fedoraapps.www.appguarda;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.fedoraapps.www.appguarda.Model.Viaje;

import java.util.List;

/**
 * Created by maxi on 01/06/2016.
 */
public class InteractiveArrayAdapterTerminales extends ArrayAdapter<Viaje> {

    private final List<Viaje> lista;
    private final Activity context;

    public InteractiveArrayAdapterTerminales(Activity context, List<Viaje> lista){
        super(context,R.layout.vista_lista_viajes, lista);
        this.context = context;
        this.lista = lista;
    }
    static class ViewHolder {
        protected TextView titulo;
        protected TextView subTitulo;
        protected TextView nroCoche;
        protected Button boton;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;

        if(view == null){
            final LayoutInflater inflator = context.getLayoutInflater();
            view = inflator.inflate(R.layout.vista_lista_viajes,null);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.titulo = (TextView) view.findViewById(R.id.titulo);
            viewHolder.nroCoche = (TextView) view.findViewById(R.id.nroCoche);
            viewHolder.subTitulo = (TextView) view.findViewById(R.id.subTitulo);
            viewHolder.boton = (Button) view.findViewById(R.id.next);
            viewHolder.boton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Viaje viaje =(Viaje) v.getTag();
                    Intent i = new Intent(context,MenuPrincipal.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra("codigo", viaje.getId());
                    getContext().getApplicationContext().startActivity(i);
                }
            });

            view.setTag(viewHolder);
            viewHolder.titulo.setTag(lista.get(position));
            viewHolder.subTitulo.setTag(lista.get(position));
            viewHolder.nroCoche.setTag(lista.get(position));
            viewHolder.boton.setTag(lista.get(position));
        }else {
            view = convertView;
            ((ViewHolder) view.getTag()).boton.setTag(lista.get(position));
        }
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.titulo.setText("Nombre:"+" "+lista.get(position).getNombre());
        holder.subTitulo.setText("Horario Salida:"+" "+ String.valueOf(lista.get(position).getHoraSalida()));
        holder.nroCoche.setText("Numero Coche:"+" "+lista.get(position).getNroCoche());
        return view;
    }






        }



