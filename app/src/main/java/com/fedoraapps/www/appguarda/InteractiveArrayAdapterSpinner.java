package com.fedoraapps.www.appguarda;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.fedoraapps.www.appguarda.Shares.DataPuntoRecorridoConverter;

import java.util.List;

/**
 * Created by maxi on 06/06/2016.
 */
public class InteractiveArrayAdapterSpinner extends ArrayAdapter<DataPuntoRecorridoConverter> {


    private final List<DataPuntoRecorridoConverter> lista;
    private final Activity context;
    public InteractiveArrayAdapterSpinner(Activity context, List<DataPuntoRecorridoConverter> lista){
        super(context,R.layout.vista_spinner,R.id.titulos, lista);
        this.context = context;
        this.lista = lista;
    }
    static class ViewHolder {
        protected TextView titulo;

    }
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;

        if(view == null){
            final LayoutInflater inflator = context.getLayoutInflater();
            view = inflator.inflate(R.layout.vista_spinner,null);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.titulo = (TextView) view.findViewById(R.id.titulos);
            view.setTag(viewHolder);
            viewHolder.titulo.setTag(lista.get(position));

        }else {

            view = convertView;
            ((ViewHolder) view.getTag()).titulo.setTag(lista.get(position).getNombre().toString());
            }
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.titulo.setText(lista.get(position).getNombre().toString());
        return view;
    }

}
