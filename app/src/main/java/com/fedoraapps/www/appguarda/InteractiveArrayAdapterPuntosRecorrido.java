package com.fedoraapps.www.appguarda;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.fedoraapps.www.appguarda.Shares.DataPuntoRecorridoConverter;

import java.util.List;

/**
 * Created by maxi on 04/08/16.
 */
public class InteractiveArrayAdapterPuntosRecorrido extends ArrayAdapter<DataPuntoRecorridoConverter> {

    private final List<DataPuntoRecorridoConverter> lista;
    private final Activity context;

    public InteractiveArrayAdapterPuntosRecorrido(Activity context, List<DataPuntoRecorridoConverter> lista){
        super(context,R.layout.vista_puntos_recorrido_checklist, lista);
        this.context = context;
        this.lista = lista;
    }
    static class ViewHolder {
        protected TextView text;
        protected CheckBox checkbox;
        protected Button button;
    }
    public View getView(int position, View convertView, ViewGroup parent){
        View view = null;

        if(convertView == null){
            final LayoutInflater inflator = context.getLayoutInflater();
            view = inflator.inflate(R.layout.vista_puntos_recorrido_checklist,null);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.text = (TextView) view.findViewById(R.id.label);
            viewHolder.checkbox = (CheckBox) view.findViewById(R.id.radio);
           // viewHolder.button = (Button) view.findViewById(R.id.detalle);
           /* viewHolder.button.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    DataPuntoRecorridoConverter e =(DataPuntoRecorridoConverter) v.getTag();

                    /* Intent i = new Intent(context,MenuDetalleEncomienda.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra("codigoEncomienda",e.getId());
                    getContext().getApplicationContext().startActivity(i);
                }
            });*/
            viewHolder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    System.out.println("SELECCIONE---->");
                    DataPuntoRecorridoConverter element = (DataPuntoRecorridoConverter) viewHolder.checkbox.getTag();
                    System.out.println("SELECCIONE---->");
                    if(buttonView.isChecked())
                   element.setSelected("caca");
                    System.out.println("SELECCIONE---->");
                    //buttonView.toggle();


                    if (element.isSelected().equals("caca")) {

                        Farcade.DestinoSeleccionado = element;
                        System.out.println("SELECCIONE---->"+" "+element.getNombre());
                    }else{
                        Farcade.DestinoSeleccionado = null;
                    }

                }
            });
                       ;
            view.setTag(viewHolder);
            viewHolder.checkbox.setTag(lista.get(position));
//            viewHolder.button.setTag(lista.get(position));
        }else {
            view = convertView;
            ((ViewHolder) view.getTag()).checkbox.setTag(lista.get(position));
        }
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.text.setText(lista.get(position).toString());//"Encomienda:"+" "+lista.get(position).getId().toString()+" "+"Estado:"+" "+lista.get(position).getUltimoEstado());//+" "+"Estado:"+" "+lista.get(position).getUltimoEstado().toString());
        System.out.println("CACA---->");
        if(lista.get(position).isSelected().equals("caca"))
        holder.checkbox.setChecked(true);

        return view;

    }
}
