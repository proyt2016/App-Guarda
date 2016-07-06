package com.fedoraapps.www.appguarda;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.fedoraapps.www.appguarda.Api.PasajeApi;
import com.fedoraapps.www.appguarda.Model.Pasaje;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by maxi on 11/06/2016.
 */
public class ProcesarPasajesVendidosManual extends AppCompatActivity implements View.OnClickListener {

    private List<Pasaje> PasajesVendidos = new ArrayList<>();
    private int codViaje;
    private EditText inputCodigo;
    private Button confirmar;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_procesar_pasajes_manual);

        codViaje = getIntent().getExtras().getInt("codigo");

        confirmar = (Button)findViewById(R.id.confirmarManual);
        inputCodigo = (EditText)findViewById(R.id.codigoPasaje);

        confirmar.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.confirmarManual) {

            if (isEmpty(inputCodigo) == true) {
                dialogCodigoIncorrectoTipeo().show();
            }else {
                final int codigoPasaje = Integer.parseInt(inputCodigo.getText().toString());
                Call<List<Pasaje>> call = PasajeApi.createService().getAll();
                call.enqueue(new Callback<List<Pasaje>>() {
                    @Override
                    public void onResponse(Call<List<Pasaje>> call, Response<List<Pasaje>> response) {
                        PasajesVendidos = response.body();
                        Pasaje pa = existePasaje(codigoPasaje);
                        if (pa != null) {

                            crearDialogoConexion(pa).show();

                        }else{dialogNoExistePasaje().show();}
                    }
                    @Override
                    public void onFailure(Call<List<Pasaje>> call, Throwable t) {
                        System.out.println("onFailure");}
                });
            }
        }

    }
    public boolean isEmpty(EditText etText)
    {
        return etText.getText().toString().trim().length() == 0;
    }
    private AlertDialog dialogNoExistePasaje()
    {
        // Instanciamos un nuevo AlertDialog Builder y le asociamos titulo y mensaje
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Atencion");
        alertDialogBuilder.setMessage("No Existe pasaje vendido");
        alertDialogBuilder.setIcon(R.drawable.icono_alerta);;

        // Creamos un nuevo OnClickListener para el boton OK que realice la conexion
        DialogInterface.OnClickListener listenerOk = new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        };

        // Creamos un nuevo OnClickListener para el boton Cancelar
        DialogInterface.OnClickListener listenerCancelar = new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        };

        // Asignamos los botones positivo y negativo a sus respectivos listeners
        alertDialogBuilder.setPositiveButton(R.string.ACEPTAR, listenerOk);
        // alertDialogBuilder.setNegativeButton(R.string.Cancelar, listenerCancelar);

        return alertDialogBuilder.create();
    }



    private AlertDialog dialogCodigoIncorrectoTipeo()
    {
        // Instanciamos un nuevo AlertDialog Builder y le asociamos titulo y mensaje
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Atencion");
        alertDialogBuilder.setMessage("Debe ingresar un codigo valido, unicamente letras");
        alertDialogBuilder.setIcon(R.drawable.icono_alerta);;

        // Creamos un nuevo OnClickListener para el boton OK que realice la conexion
        DialogInterface.OnClickListener listenerOk = new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        };

        // Creamos un nuevo OnClickListener para el boton Cancelar
        DialogInterface.OnClickListener listenerCancelar = new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        };

        // Asignamos los botones positivo y negativo a sus respectivos listeners
        alertDialogBuilder.setPositiveButton(R.string.ACEPTAR, listenerOk);
        // alertDialogBuilder.setNegativeButton(R.string.Cancelar, listenerCancelar);

        return alertDialogBuilder.create();
    }
    private AlertDialog crearDialogoConexion(Pasaje pasaje)
    {
        // Instanciamos un nuevo AlertDialog Builder y le asociamos titulo y mensaje
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Pasaje Vendido");
        alertDialogBuilder.setMessage("Numero de Pasaje:"+" "+pasaje.getId()+"\n"
                +"Monto:"+" "+pasaje.getMonto()+"\n"
                + "Con destino a"+" "+pasaje.getIdDestino());
        alertDialogBuilder.setIcon(R.drawable.icono_cash_black);;

        // Creamos un nuevo OnClickListener para el boton OK que realice la conexion
        DialogInterface.OnClickListener listenerOk = new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        };

        // Creamos un nuevo OnClickListener para el boton Cancelar
        DialogInterface.OnClickListener listenerCancelar = new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        };

        // Asignamos los botones positivo y negativo a sus respectivos listeners
        alertDialogBuilder.setPositiveButton(R.string.ACEPTAR, listenerOk);
        // alertDialogBuilder.setNegativeButton(R.string.Cancelar, listenerCancelar);

        return alertDialogBuilder.create();
    }
    public Pasaje existePasaje(int codPasaje){
        for(Pasaje p: PasajesVendidos){
            if( p.getId() == codPasaje){
                return p;
            }

        }return null;
    }
}
