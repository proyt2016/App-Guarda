package com.fedoraapps.www.appguarda;

/**
 * Created by maxi on 07/06/2016.
 */

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.fedoraapps.www.appguarda.Api.PagosOnlineApi;
import com.fedoraapps.www.appguarda.Api.PasajeApi;
import com.fedoraapps.www.appguarda.Api.PrecioApi;
import com.fedoraapps.www.appguarda.Api.PuntosRecorridoApi;
import com.fedoraapps.www.appguarda.Model.PagoOnline;
import com.fedoraapps.www.appguarda.Model.Pasaje;
import com.fedoraapps.www.appguarda.Model.Precio;
import com.fedoraapps.www.appguarda.Model.PuntosDeRecorrido;
import com.google.zxing.integration.android.IntentIntegrator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class VentaPasajesOnline extends AppCompatActivity implements View.OnClickListener{
    Spinner origen;
    Spinner destino;
    Button generar;
    String valOfSpinner;
    String valOfSpinner2;
    PuntosDeRecorrido puntoOrigen;
    PuntosDeRecorrido puntoDestino;
    private String codViaje;
    private String codRecorrido;
    int ultimoIdPasaje;
    List<PuntosDeRecorrido> lista;
    ArrayAdapter<PuntosDeRecorrido> paradas;
    private List<Pasaje> PasajesVendidos = new ArrayList<>();
    private List<PagoOnline> pagosOnline = new ArrayList<>();
    private List<Precio> listadoPrecios = new ArrayList<>();
    Pasaje pasaje;
    int idPrecio;
    int montoPrecio;
   private boolean pago;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_venta_online);

        codViaje = getIntent().getExtras().getString("codigoViaje");
        codRecorrido = getIntent().getExtras().getString("codigo");

        origen = (Spinner) findViewById(R.id.spinner);
        destino = (Spinner) findViewById(R.id.spinner2);
        generar = (Button) findViewById(R.id.button);
        generar.setOnClickListener(this);
    }
/*
        Call<List<PuntosDeRecorrido>> call = PuntosRecorridoApi.createService().getAll();
        call.enqueue(new Callback<List<PuntosDeRecorrido>>() {
            @Override
            public void onResponse(Call<List<PuntosDeRecorrido>> call, Response<List<PuntosDeRecorrido>> response) {
                lista = response.body();

                paradas = new InteractiveArrayAdapterSpinner(VentaPasajesOnline.this, getModel());
                origen.setAdapter(paradas);
                destino.setAdapter(paradas);
            }

            @Override
            public void onFailure(Call<List<PuntosDeRecorrido>> call, Throwable t) {
                System.out.println("onFailure");
            }
        });

        origen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            //GET SPINNER
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                valOfSpinner = origen.getSelectedItem().toString();

                puntoOrigen = (PuntosDeRecorrido) origen.getSelectedItem();

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
                puntoDestino = (PuntosDeRecorrido) origen.getSelectedItem();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        generar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (v.getId() == R.id.button) {

                    if (valOfSpinner != null && valOfSpinner2 != null) {

                        if (valOfSpinner.equals(valOfSpinner2)) {
                            dialogOrigenDestinoIguales().show();
                        } else {
                            if (puntoOrigen.getCantAsientos() >= 1) {
                                Call<List<Pasaje>> call = PasajeApi.createService().getAll();
                                call.enqueue(new Callback<List<Pasaje>>() {
                                    @Override
                                    public void onResponse(Call<List<Pasaje>> call, Response<List<Pasaje>> response) {
                                        PasajesVendidos = response.body();

                                        if (PasajesVendidos.isEmpty() == false) {
                                            ultimoIdPasaje = PasajesVendidos.get(PasajesVendidos.size() - 1).getId() + 1;
                                        }
                                        if (PasajesVendidos.isEmpty() == true) {
                                            ultimoIdPasaje = 1;
                                        }
                                        //CONSULTO PRECIOS DE VIAJES EN EL SISTEMA
                                        Call<List<Precio>> call3 = PrecioApi.createService().getAll();
                                        call3.enqueue(new Callback<List<Precio>>() {
                                            @Override
                                            public void onResponse(Call<List<Precio>> call, Response<List<Precio>> response) {
                                                listadoPrecios = response.body();
                                                for (Precio p : listadoPrecios) {

                                                    System.out.println(p.getId() + " " + p.getIdViaje() + " " + p.getMonto());
                                                    if (p.getIdViaje() == codViaje) {

                                                        idPrecio = p.getId();
                                                        montoPrecio = p.getMonto();

                                                    }
                                                }
                                                pasaje = new Pasaje(ultimoIdPasaje, codViaje, puntoOrigen.getId(), puntoDestino.getId(), idPrecio, 1, montoPrecio, false);
                                                Call<Pasaje> call2 = PasajeApi.createService().setPasaje(pasaje);
                                                call2.enqueue(new Callback<Pasaje>() {
                                                    @Override
                                                    public void onResponse(Call<Pasaje> call, Response<Pasaje> response) {
                                                        Pasaje p = response.body();

                                                        if (p != null) {
                                                            dialogPasajeVendido(p).show();
                                                        } else {
                                                            dialogNoExistePasaje().show();
                                                        }
                                                    }

                                                    @Override
                                                    public void onFailure(Call<Pasaje> call, Throwable t) {
                                                        System.out.println("onFailure");
                                                    }
                                                });
                                            }

                                            @Override
                                            public void onFailure(Call<List<Precio>> call, Throwable t) {
                                                System.out.println("onFailure");
                                            }
                                        });
                                        IntentIntegrator integrator = new IntentIntegrator(VentaPasajesOnline.this);
                                        integrator.shareText(String.valueOf(ultimoIdPasaje), "TEXT_TYPE");
                                        System.out.println(integrator.getTitle());



                                    }

                                    @Override
                                    public void onFailure(Call<List<Pasaje>> call, Throwable t) {
                                        System.out.println("onFailure");
                                    }
                                });
                                //COnsulto si pago y notifico al Guarda
                                Call<List<PagoOnline>> call2 = PagosOnlineApi.createService().getAll();
                                call2.enqueue(new Callback<List<PagoOnline>>() {
                                    @Override
                                    public void onResponse(Call<List<PagoOnline>> call, Response<List<PagoOnline>> response) {
                                        pagosOnline = response.body();
                                    }

                                    @Override
                                    public void onFailure(Call<List<PagoOnline>> call, Throwable t) {
                                        System.out.println("onFailure");
                                    }
                                });
                            }else {Toast.makeText(VentaPasajesOnline.this, "Es lamentable pero no hay lugar para ti", Toast.LENGTH_LONG).show();}
                        }

                    } else {
                        Toast.makeText(VentaPasajesOnline.this, "Debe seleccionar un Origen / Destino", Toast.LENGTH_LONG).show();
                    }
                }


        });





    }*/
    private AlertDialog dialogOrigenDestinoIguales()
    {
        // Instanciamos un nuevo AlertDialog Builder y le asociamos titulo y mensaje
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Atencion!");
        alertDialogBuilder.setMessage("Origen y Destino deben ser distintos");
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
    private AlertDialog dialogNoExistePasaje()
    {
        // Instanciamos un nuevo AlertDialog Builder y le asociamos titulo y mensaje
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Atencion!");
        alertDialogBuilder.setMessage("No se genero Pasaje, vuelva a intentarlo");
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
    private AlertDialog dialogPasajeVendido(Pasaje pasaje)
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
    private List<PuntosDeRecorrido> getModel() {
        List<PuntosDeRecorrido> list = new ArrayList<PuntosDeRecorrido>();
        for(PuntosDeRecorrido e: lista){
            list.add(e);
            // adapter.notifyDataSetChanged();
        }

        return list;
    }

    @Override
    public void onClick(View v) {


    }






      //  IntentIntegrator integrator = new IntentIntegrator(this);
      //  integrator.shareText("1", "TEXT_TYPE");




}
