package com.fedoraapps.www.appguarda;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.fedoraapps.www.appguarda.Api.PasajeApi;
import com.fedoraapps.www.appguarda.Api.PuntosRecorridoApi;
import com.fedoraapps.www.appguarda.Api.ViajeApi;
import com.fedoraapps.www.appguarda.Model.Pasaje;
import com.fedoraapps.www.appguarda.Model.Precio;
import com.fedoraapps.www.appguarda.Shares.DataEmpleado;
import com.fedoraapps.www.appguarda.Shares.DataPasaje;
import com.fedoraapps.www.appguarda.Shares.DataPrecio;
import com.fedoraapps.www.appguarda.Shares.DataPuntoRecorridoConverter;
import com.fedoraapps.www.appguarda.Shares.DataRecorridoConvertor;
import com.fedoraapps.www.appguarda.Shares.DataUsuario;
import com.fedoraapps.www.appguarda.Shares.DataViaje;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by maxi on 05/06/2016.
 */
public class VentaPasajesEfectivo extends AppCompatActivity implements View.OnClickListener {

    Spinner origen;
    Spinner destino;
    private int codigoPasaje;
    Button generar;
    String valOfSpinner;
    String valOfSpinner2;
    DataPuntoRecorridoConverter puntoOrigen;
    DataPuntoRecorridoConverter puntoDestino;
    private List<DataPasaje> PasajesVendidos = new ArrayList<>();
    private String codRecorrido;
    int ultimoIdPasaje;
    int idPrecio;
    int montoPrecio;
    private Pasaje pasaje;
    private  String codViaje;

    //List<DataPuntoRecorridoConverter> lista;
    ArrayAdapter<DataPuntoRecorridoConverter> paradas;

    private List<Precio> listadoPrecios = new ArrayList<>();



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_venta_efectivo);

        codRecorrido = getIntent().getExtras().getString("codigo");
        codViaje = getIntent().getExtras().getString("codigoViaje");

        origen = (Spinner) findViewById(R.id.spinner);
        destino = (Spinner) findViewById(R.id.spinner2);
        generar = (Button) findViewById(R.id.button);
        generar.setOnClickListener(this);

        Call<List<DataRecorridoConvertor>> call = PuntosRecorridoApi.createService().getAll();
        call.enqueue(new Callback<List<DataRecorridoConvertor>>() {
            @Override
            public void onResponse(Call<List<DataRecorridoConvertor>> call, Response<List<DataRecorridoConvertor>> response) {
               List<DataRecorridoConvertor> recorrido = response.body();
                System.out.println(recorrido);
                if(recorrido!=null){
                for(DataRecorridoConvertor data: recorrido){
                    if(data.getId().equals(codRecorrido)){
                        paradas = new InteractiveArrayAdapterSpinner(VentaPasajesEfectivo.this, data.getPuntosDeRecorrido());
                        origen.setAdapter(paradas);
                        destino.setAdapter(paradas);
                    }
                }
                }

            }
            @Override
            public void onFailure(Call<List<DataRecorridoConvertor>> call, Throwable t) {
                System.out.println("*****FALLO EL SERVICIO*****");
            }
        });

        origen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            //GET SPINNER
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                valOfSpinner = origen.getSelectedItem().toString();
                puntoOrigen = (DataPuntoRecorridoConverter) origen.getSelectedItem();
              //  System.out.println(puntoOrigen.getId()+" "+puntoOrigen.getCantAsientos());

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        destino.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            //GET SPINNER
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                valOfSpinner2 = destino.getSelectedItem().toString();
                puntoDestino = (DataPuntoRecorridoConverter) destino.getSelectedItem();
                //System.out.println(puntoDestino.getCantAsientos());
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        generar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (v.getId() == R.id.button) {
                    if (valOfSpinner.equals(valOfSpinner2)) {
                            dialogoTryOrigenDestino().show();
                        }else {
                        Call<DataViaje> call = ViajeApi.createService().getViaje(codViaje);
                        call.enqueue(new Callback<DataViaje>() {
                            @Override
                            public void onResponse(Call<DataViaje> call, Response<DataViaje> response) {
                                DataViaje viaje = response.body();
                                System.out.println("VIAJE ***********************"+viaje);
                              //  if ( viaje.getCoche().getCantidadAsientos()>0) {

                                DataUsuario usuario = new DataUsuario();
                                //usuario.getEmail().setEmail("dfdfdfdf");
                                DataEmpleado empleado = new DataEmpleado();

                                Date fechaVenc = new Date();
                                String ciUsuario = "4444";
                                DataPrecio precio = new DataPrecio();
                               // precio.setId("666");
                                DataViaje VIAJE = new DataViaje();
                                VIAJE.setId(viaje.getId());

                                DataPuntoRecorridoConverter ori = new DataPuntoRecorridoConverter();
                                DataPuntoRecorridoConverter dest = new DataPuntoRecorridoConverter();
                                ori.setId(puntoOrigen.getId());
                                ori.setTipo(puntoOrigen.getTipo());
                                dest.setId(puntoDestino.getId());
                                dest.setTipo(puntoDestino.getTipo());

                                    DataPasaje pasaje = new DataPasaje(VIAJE,null,ori,dest,null,null,null,null,true,true,false);

                                    //DataPasaje pasaje = new DataPasaje("5555");
                                    Call<DataPasaje> call3 = PasajeApi.createService().venderPasaje(pasaje);
                                    call3.enqueue(new Callback<DataPasaje>() {

                                        @Override
                                        public void onResponse(Call<DataPasaje> call, Response<DataPasaje> response) {
                                            DataPasaje p = response.body();
                                            System.out.println("PASAJE---->"+p+"<----PASAJE");

                                            if(p!=null){
                                                final DataPasaje pe = new DataPasaje();
                                                dialogoPasajeVendido(pe).show();}
                                            else {
                                                final DataPasaje pe = new DataPasaje();
                                                dialogoPasajeNoVendido(pe).show();
                                            }
                                        }
                                        @Override
                                        public void onFailure(Call<DataPasaje> call, Throwable t) {
                                            System.out.println("*****FALLO EL SERVICIO*****"+t.getCause());
                                        }
                                    });

                            }
                            @Override
                            public void onFailure(Call<DataViaje> call, Throwable t) {
                                System.out.println("*****FALLO EL SERVICIO*****");
                            }
                        });


                    }

                }
            }
        });
    }
    private AlertDialog dialogoPasajeVendido(DataPasaje pasaje)
    {
        // Instanciamos un nuevo AlertDialog Builder y le asociamos titulo y mensaje
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Pasaje Vendido");
        alertDialogBuilder.setMessage("Pasaje con destino:");//+" "+pasaje.getDestino().getNombre()+"\n"+"Precio:"+" "+pasaje.getPrecio().getMonto());
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

    private AlertDialog dialogoPasajeNoVendido(DataPasaje pasaje)
    {
        // Instanciamos un nuevo AlertDialog Builder y le asociamos titulo y mensaje
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Atencion!");
        alertDialogBuilder.setMessage("No se puedo realizar la venta");
        alertDialogBuilder.setIcon(R.drawable.icono_alerta);

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

    private AlertDialog dialogoTryOrigenDestino()
    {
        // Instanciamos un nuevo AlertDialog Builder y le asociamos titulo y mensaje
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Atencion!");
        alertDialogBuilder.setMessage("Origen y Destino Deben ser distintos");
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


    @Override
    public void onClick(View v) {


    }

}
