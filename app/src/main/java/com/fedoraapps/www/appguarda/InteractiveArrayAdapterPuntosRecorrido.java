package com.fedoraapps.www.appguarda;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fedoraapps.www.appguarda.Shares.DataPtoRecWrapper;

import java.util.List;

/**
 * Created by maxi on 04/08/16.
 */
public class InteractiveArrayAdapterPuntosRecorrido extends ArrayAdapter<DataPtoRecWrapper> implements Filterable {

    private List<DataPtoRecWrapper> lista;

    private Activity context;

    private RadioButton mSelectedRB;
    private int mSelectedPosition = -1;



    public InteractiveArrayAdapterPuntosRecorrido(Activity context, List<DataPtoRecWrapper> lista) {
        super(context, R.layout.vista_puntos_recorrido_checklist, lista);
        this.context = context;
        this.lista = lista;

    }

    static class ViewHolder {
        protected TextView text;
        protected RadioButton radioBtn;
        protected Button button;
        protected RelativeLayout layoutPadre;
        protected LinearLayout layout;
    }



    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = null;


        if (convertView == null) {
            final LayoutInflater inflator = context.getLayoutInflater();
            view = inflator.inflate(R.layout.vista_puntos_recorrido_checklist, null);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.layoutPadre = (RelativeLayout) view.findViewById(R.id.layoutpadre);
            viewHolder.layout = (LinearLayout) view.findViewById(R.id.layoutinterno);
            viewHolder.text = (TextView) view.findViewById(R.id.label);
            viewHolder.radioBtn = (RadioButton) view.findViewById(R.id.radio);
            view.setTag(viewHolder);
            viewHolder.radioBtn.setChecked(lista.get(position).getChecked());
            viewHolder.radioBtn.setTag(lista.get(position));
           // viewHolder.text.setTag(lista.get(position));
        }else{
                view = convertView;
            ((ViewHolder) view.getTag()).radioBtn.setTag(lista.get(position));

            }

       final ViewHolder holder = (ViewHolder) view.getTag();
        holder.radioBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(holder.radioBtn.isChecked()){
                    DataPtoRecWrapper d = (DataPtoRecWrapper)holder.radioBtn.getTag();
                    Farcade.DestinoSeleccionado = d.getPunto();
                    System.out.println("DESTINO SELECCIONADO MAXIISSS---------------->"+" "+Farcade.DestinoSeleccionado.getNombre());
                }

                if(position != mSelectedPosition && mSelectedRB != null){
                    mSelectedRB.setChecked(false);
                }

                mSelectedPosition = position;
                mSelectedRB = (RadioButton)v;
            }
        });
        if(mSelectedPosition != position){
            holder.radioBtn.setChecked(false);
        }else{
            holder.radioBtn.setChecked(true);

            if(mSelectedRB != null && holder.radioBtn != mSelectedRB){
                mSelectedRB = holder.radioBtn;

            }
        }

        holder.text.setText(lista.get(position).toString());
        //\holder.radioBtn.setChecked(lista.get(position).getChecked());

        if(Farcade.configuracionEmpresa.getId()!=null){
            if(Farcade.configuracionEmpresa.getColorTextoLista()!=null){
                holder.text.setTextColor(Color.parseColor(Farcade.configuracionEmpresa.getColorTextoLista()));}
            else{
                holder.text.setTextColor(Color.parseColor("#ffffffff"));
            }
            if(Farcade.configuracionEmpresa.getColorFondoLista()!=null){
                holder.text.setBackgroundColor(Color.parseColor(Farcade.configuracionEmpresa.getColorFondoLista()));}
            else{
                holder.text.setBackgroundColor(Color.parseColor("#E12929"));
            }
            if(Farcade.configuracionEmpresa.getColorFondoLista()!=null){
                holder.layout.setBackgroundColor(Color.parseColor(Farcade.configuracionEmpresa.getColorFondoLista()));}
            else{
                holder.layout.setBackgroundColor(Color.parseColor("#E12929"));
            }
            if(Farcade.configuracionEmpresa.getColorFondoLista()!=null){
                holder.layoutPadre.setBackgroundColor(Color.parseColor(Farcade.configuracionEmpresa.getColorFondoLista()));}
            else{
                holder.layoutPadre.setBackgroundColor(Color.parseColor("#E12929"));
            }

        }else{
            //NO EXISTE CONFIGURACION
            holder.text.setTextColor(Color.parseColor("#ffffffff"));
            holder.text.setBackgroundColor(Color.parseColor("#E12929"));
            holder.layout.setBackgroundColor(Color.parseColor("#E12929"));
            holder.layoutPadre.setBackgroundColor(Color.parseColor("#E12929"));

        }

        return view;

    }





}

