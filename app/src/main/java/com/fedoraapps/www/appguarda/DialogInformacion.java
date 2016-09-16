package com.fedoraapps.www.appguarda;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by maxi on 12/09/16.
 */
public class DialogInformacion extends AppCompatActivity implements View.OnClickListener  {

    private TextView titulo;
    private TextView campo1;
    private TextView campo2;
    private TextView campo3;
    private TextView campo4;
    private TextView campo5;
    private ImageView logo;


    int flag  = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_informacion);

        flag = getIntent().getExtras().getInt("flag");

        titulo = (TextView) findViewById(R.id.tituloempleado);

        campo1 = (TextView) findViewById(R.id.txt1);
        campo2 = (TextView) findViewById(R.id.txt2);
        campo3 = (TextView) findViewById(R.id.txt3);
        campo4 = (TextView) findViewById(R.id.txt4);
        campo5 = (TextView) findViewById(R.id.txt5);
        logo = (ImageView)findViewById(R.id.logo);

        if(Farcade.configuracionEmpresa.getId()!=null){
            if(Farcade.configuracionEmpresa.getColorLetras()!=null){
                campo1.setTextColor(Color.parseColor(Farcade.configuracionEmpresa.getColorLetras()));
                campo2.setTextColor(Color.parseColor(Farcade.configuracionEmpresa.getColorLetras()));
                campo3.setTextColor(Color.parseColor(Farcade.configuracionEmpresa.getColorLetras()));
                campo4.setTextColor(Color.parseColor(Farcade.configuracionEmpresa.getColorLetras()));
                campo5.setTextColor(Color.parseColor(Farcade.configuracionEmpresa.getColorLetras()));                          }else{
            }
            if(Farcade.configuracionEmpresa.getIconoEmpresa()!=null){
                logo.setImageURI(Uri.parse(Farcade.configuracionEmpresa.getIconoEmpresa()));
            }else{
                logo.setImageResource(R.drawable.icono_bondi);
            }
        }


        if(flag == 1){
            //DATOS EMPLEADO

            titulo.setText("Empleado Registrado");
            if(Farcade.empleado.getNombrePila()!=null){
            campo1.setText("Nombre:"+" "+Farcade.empleado.getNombrePila());}
            else{
                campo1.setText("Nombre:"+" "+"No se pudo cargar el Nombre");
            }
            if(Farcade.empleado.getApellido()!=null){
                campo2.setText("Apellido:"+" "+Farcade.empleado.getApellido());
            }else{
                campo2.setText("Apellido:"+" "+"No se pudo cargar el Apellido");
            }
            if(Farcade.empleado.getEmail()!=null){
                campo3.setText("E-mail:"+" "+Farcade.empleado.getEmail().getEmail());
            }else{
                campo3.setText("E-mail:"+" "+"No se pudo cargar el E-mail");
            }
            if(Farcade.empleado.getTelefonosContacto()!=null){
                if(!Farcade.empleado.getTelefonosContacto().isEmpty()){
                campo4.setText("Telefono:"+" "+Farcade.empleado.getTelefonosContacto().get(0).getTelefono());}
                else{
                    campo4.setText("Telefono:"+" "+"No es posible cargar el Telefono");
                }
            }else{
                campo4.setText("Telefono:"+" "+"No es posible cargar el Telefono");
            }
            if(Farcade.configuracionEmpresa.getNombre()!=null){
                campo5.setText(Farcade.configuracionEmpresa.getNombre());
            }else{
                campo5.setText("LAC BUS");

            }


        }else{

            //DATOS TERMINAL
            titulo.setText("Terminal Seleccionada");
            if(Farcade.terminalSeleccionada.getNombre()!=null){
                campo1.setText("Nombre:"+" "+Farcade.terminalSeleccionada.getNombre());}
            else{
                campo1.setText("Nombre:"+" "+"No se pudo cargar el Nombre");
            }
            if(Farcade.terminalSeleccionada.getMailsDeContacto()!=null){
                if(!Farcade.terminalSeleccionada.getMailsDeContacto().isEmpty()){
                campo2.setText("E-mail:"+" "+Farcade.terminalSeleccionada.getMailsDeContacto().get(0).getEmail());}
                else{
                    campo2.setText("E-mail:"+" "+"No se pudo cargar el E-mail");
                }
            }else{
                campo2.setText("E-mail:"+" "+"No se pudo cargar el E-mail");
            }
            if(Farcade.terminalSeleccionada.getTelefonosContacto()!=null){
                if(!Farcade.terminalSeleccionada.getTelefonosContacto().isEmpty()){
                    campo3.setText("Telefono:"+" "+Farcade.terminalSeleccionada.getTelefonosContacto().get(0).getTelefono());}
                else{
                    campo3.setText("Telefono:"+" "+"No se pudo cargar el Telefono");
                }
            }else{
                campo3.setText("Telefono:"+" "+"No se pudo cargar el Telefono");
            }
            if(Farcade.terminalSeleccionada.getAceptaEncomiendas()!=null){
                if(Farcade.terminalSeleccionada.getAceptaEncomiendas() == true){
                    campo4.setText("Acepta Encomiendas:"+" "+"Si");
                }else{
                    campo4.setText("Acepta Encomiendas:"+" "+"NO");
                }

            }else{
                campo4.setText("Acepta Encomiendas:"+" "+"Informacion no Disponible");


            }
            if(Farcade.configuracionEmpresa.getNombre()!=null){
                campo5.setText(Farcade.configuracionEmpresa.getNombre());
            }else{
                campo5.setText("LAC BUS");

            }

        }







    }

    @Override
    public void onClick(View v) {

    }
}
