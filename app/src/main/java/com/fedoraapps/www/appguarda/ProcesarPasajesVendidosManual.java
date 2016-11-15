package com.fedoraapps.www.appguarda;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.fedoraapps.www.appguarda.Api.PasajeApi;
import com.fedoraapps.www.appguarda.Shares.DataPasajeConvertor;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by maxi on 11/06/2016.
 */
public class ProcesarPasajesVendidosManual extends AppCompatActivity implements View.OnClickListener {

    private List<DataPasajeConvertor> PasajesVendidos = new ArrayList<>();
    private RelativeLayout pantalla;
    private String codViaje;
    private String codRecorrido;
    private int codigoPasaje;
    private EditText inputCodigo;
    private Button confirmar;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_procesar_pasajes_manual);

        pantalla = (RelativeLayout) findViewById(R.id.dialogprocesarmanual);



        codViaje = getIntent().getExtras().getString("codigoViaje");


        codRecorrido = getIntent().getExtras().getString("codigo");

        confirmar = (Button)findViewById(R.id.confirmarManual);
        inputCodigo = (EditText)findViewById(R.id.codigoPasaje);

        confirmar.setOnClickListener(this);

        if(Farcade.configuracionEmpresa.getId()!=null) {
            if (Farcade.configuracionEmpresa.getColorLetras() != null){
                confirmar.setTextColor(Color.parseColor(Farcade.configuracionEmpresa.getColorLetras()));}
            else{
                confirmar.setTextColor(Color.parseColor("#ffffffff"));
            }
            if(Farcade.configuracionEmpresa.getColorBoton()!=null){
                confirmar.setBackgroundColor(Color.parseColor(Farcade.configuracionEmpresa.getColorBoton()));}
            else{
                confirmar.setBackgroundColor(Color.parseColor("#5a595b"));
            }
            if(Farcade.configuracionEmpresa.getColorLetras()!=null){
                inputCodigo.setTextColor(Color.parseColor(Farcade.configuracionEmpresa.getColorLetras()));}
            else{
                inputCodigo.setTextColor(Color.parseColor("#ffffffff"));
            }
        }else{
            //no exite configuracion
            confirmar.setTextColor(Color.parseColor("#ffffffff"));
            confirmar.setBackgroundColor(Color.parseColor("#5a595b"));
            inputCodigo.setTextColor(Color.parseColor("#ffffffff"));
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.confirmarManual) {

            if (isEmpty(inputCodigo) == true) {
                //VALIDO QUE SE HAYA INGRESADO DATOS
                dialogCodigoIncorrectoTipeo().show();
            }else {
                //OBTENGO CODIGO DEL PASAJE
                 codigoPasaje = Integer.parseInt(inputCodigo.getText().toString());
                //VERIFICO SI EXISTE PASAJE EN MEMORIA
                Call<DataPasajeConvertor> call = PasajeApi.createService().getPasajePorCodigo(codigoPasaje);
                call.enqueue(new Callback<DataPasajeConvertor>() {
                    @Override
                    public void onResponse(Call<DataPasajeConvertor> call, Response<DataPasajeConvertor> response) {
                        final DataPasajeConvertor pasaje = response.body();

                            if (pasaje != null && pasaje.getUsado() == false) {
                                //CAMBIO ESTADO A PASAJE UTILIZADO
                                Call<Void> call2 = PasajeApi.createService().procesarPasaje(pasaje.getId());
                                call2.enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        //MUESTRO EN DIALOG OPERACION EXITOSA
                                        crearDialogoConexion(pasaje).show();
                                    }
                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {
                                        System.out.println("*****FALLO EL SERVICIO*****");}
                            });


                        }else {
                                if (pasaje == null) {
                                    dialogNoExistePasaje().show();

                                }else {
                                    //PASAJE YA FUE UTILIZADO
                                    if (pasaje.getUsado() == true) {
                                        dialogPasajeUsado().show();
                                    } else {
                                        //NO EXISTE EL PASAJE SE MUESTRA EL DIALOG CORRESPONDIENTE
                                        dialogNoExistePasaje().show();
                                    }
                                }
                            }
                    }
                    @Override
                    public void onFailure(Call<DataPasajeConvertor> call, Throwable t) {
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

    private AlertDialog dialogPasajeUsado()
    {
        // Instanciamos un nuevo AlertDialog Builder y le asociamos titulo y mensaje
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Atencion");
        alertDialogBuilder.setMessage("El pasaje ya fue utilizado!");
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
    private AlertDialog crearDialogoConexion(DataPasajeConvertor pasaje)
    {
        // Instanciamos un nuevo AlertDialog Builder y le asociamos titulo y mensaje
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Pasaje Vendido");
        alertDialogBuilder.setMessage("Numero de Pasaje:"+" "+pasaje.getCodigoPasaje()+"\n"
                +"Monto:"+" "+/*pasaje.getPrecio().getMonto()*/"\n"
                + "Con destino a"+" "+pasaje.getDestino().getNombre());
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

}
