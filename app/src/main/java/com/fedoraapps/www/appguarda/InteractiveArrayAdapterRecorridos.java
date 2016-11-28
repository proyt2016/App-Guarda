package com.fedoraapps.www.appguarda;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.TextView;

import com.fedoraapps.www.appguarda.Shares.DataViajeConvertor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maxi on 01/06/2016.
 */
public class InteractiveArrayAdapterRecorridos extends ArrayAdapter<DataViajeConvertor> {

    private final List<DataViajeConvertor> lista;
    private final Activity context;
    private Filter filter;


    public InteractiveArrayAdapterRecorridos(Activity context, List<DataViajeConvertor> lista){
        super(context,R.layout.vista_lista_viajes, lista);
        this.context = context;
        this.lista = lista;
    }
    static class ViewHolder {
        protected TextView titulo;
        protected TextView subTitulo;

      // protected TextView nroCoche;
        protected Button boton;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public DataViajeConvertor getItem(int position) {
        return lista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return lista.indexOf(position);

    }


    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;

        if(view == null){
            final LayoutInflater inflator = context.getLayoutInflater();
            view = inflator.inflate(R.layout.vista_lista_viajes,null);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.titulo = (TextView) view.findViewById(R.id.nroCoche);
            viewHolder.subTitulo = (TextView)view.findViewById(R.id.subTitulo);


            viewHolder.boton = (Button) view.findViewById(R.id.next);
            viewHolder.boton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    DataViajeConvertor recorrido =(DataViajeConvertor) v.getTag();
                    Intent i = new Intent(context,MenuPrincipal.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra("codigo", recorrido.getRecorrido().getId());

                    i.putExtra("codigoViaje", recorrido.getId());
                    getContext().getApplicationContext().startActivity(i);
                    //GUARDO EN MEMORIA EL RECORRIDO SELECCIONADO
                    Farcade farcade = new Farcade();
                    farcade.setRecorridoSeleccionado(recorrido);
                }
            });

            view.setTag(viewHolder);
            viewHolder.titulo.setTag(lista.get(position));
          viewHolder.subTitulo.setTag(lista.get(position));
        //   viewHolder.nroCoche.setTag(lista.get(position));
            viewHolder.boton.setTag(lista.get(position));
        }else {
            view = convertView;
            ((ViewHolder) view.getTag()).boton.setTag(lista.get(position));
        }

        ViewHolder holder = (ViewHolder) view.getTag();
        //CONFIGURACION DE VISTA SEGUN EMPRESA
        if(Farcade.configuracionEmpresa.getId()!=null) {
            if(Farcade.configuracionEmpresa.getColorTextoLista()!=null){
                holder.titulo.setTextColor(Color.parseColor(Farcade.configuracionEmpresa.getColorTextoLista()));}
            else{
                holder.titulo.setTextColor(Color.parseColor("#ff000000"));
            }
            if(Farcade.configuracionEmpresa.getColorTextoLista()!=null){
                holder.subTitulo.setTextColor(Color.parseColor(Farcade.configuracionEmpresa.getColorTextoLista()));}
            else{
                holder.subTitulo.setTextColor(Color.parseColor("#ffffffff"));
            }
            if(Farcade.configuracionEmpresa.getColorFondoLista()!=null){
                holder.titulo.setBackgroundColor(Color.parseColor(Farcade.configuracionEmpresa.getColorFondoLista()));}
            else{
                holder.titulo.setBackgroundColor(Color.parseColor("#E12929"));
            }
            if(Farcade.configuracionEmpresa.getColorFondoLista()!=null){
                holder.subTitulo.setBackgroundColor(Color.parseColor(Farcade.configuracionEmpresa.getColorFondoLista()));}
            else{
                holder.subTitulo.setBackgroundColor(Color.parseColor("#E12929"));
            }
        }else{
            //NO EXISTE CONFIGURACION
            holder.titulo.setTextColor(Color.parseColor("#ff000000"));
            holder.subTitulo.setTextColor(Color.parseColor("#ffffffff"));
            holder.titulo.setBackgroundColor(Color.parseColor("#E12929"));
            holder.subTitulo.setBackgroundColor(Color.parseColor("#E12929"));
        }
        holder.titulo.setText(lista.get(position).getRecorrido().getNombre().toString());
        holder.subTitulo.setText("Horario Salida:"+" "+ String.valueOf(lista.get(position).getHorario().getNombre()));
      //  holder.nroCoche.setText("Numero Coche:"+" "+lista.get(position));
        return view;
    }

    @Override
    public Filter getFilter() {
        if (filter == null)
            filter = new AppFilter<DataViajeConvertor>(lista);
        return filter;
    }

    private class AppFilter<T> extends Filter {

        private ArrayList<T> sourceObjects;

        public AppFilter(List<T> objects) {
            sourceObjects = new ArrayList<T>();
            synchronized (this) {
                sourceObjects.addAll(objects);
            }
        }

        @Override
        protected FilterResults performFiltering(CharSequence chars) {
            String filterSeq = chars.toString().toLowerCase();
            FilterResults result = new FilterResults();
            if (filterSeq != null && filterSeq.length() > 0) {
                ArrayList<T> filter = new ArrayList<T>();

                for (T object : sourceObjects) {
                    // the filtering itself:
                    if (object.toString().toLowerCase().contains(filterSeq))
                        filter.add(object);
                }
                result.count = filter.size();
                result.values = filter;
            } else {
                // add all objects
                synchronized (this) {
                    result.values = sourceObjects;
                    result.count = sourceObjects.size();
                }
            }
            return result;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            // NOTE: this function is *always* called from the UI thread.
            ArrayList<T> filtered = (ArrayList<T>) results.values;
            notifyDataSetChanged();
            clear();
            for (int i = 0, l = filtered.size(); i < l; i++)
                add((DataViajeConvertor) filtered.get(i));
            notifyDataSetInvalidated();
        }
    }






}



