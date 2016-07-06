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
import android.widget.Toast;

import com.fedoraapps.www.appguarda.Api.PasajeApi;
import com.fedoraapps.www.appguarda.Api.PrecioApi;
import com.fedoraapps.www.appguarda.Api.PuntosRecorridoApi;
import com.fedoraapps.www.appguarda.Model.Pasaje;
import com.fedoraapps.www.appguarda.Model.Precio;
import com.fedoraapps.www.appguarda.Model.PuntosDeRecorrido;

import java.util.ArrayList;
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
    Button generar;
    String valOfSpinner;
    String valOfSpinner2;
    PuntosDeRecorrido puntoOrigen;
    PuntosDeRecorrido puntoDestino;
    int codViaje;
    int ultimoIdPasaje;
    int idPrecio;
    int montoPrecio;
    private Pasaje pasaje;

    List<PuntosDeRecorrido> lista;
    ArrayAdapter<PuntosDeRecorrido> paradas;
    private List<Pasaje> PasajesVendidos = new ArrayList<>();
    private List<Precio> listadoPrecios = new ArrayList<>();



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_venta_efectivo);

        codViaje = getIntent().getExtras().getInt("codigo");

        origen = (Spinner) findViewById(R.id.spinner);
        destino = (Spinner) findViewById(R.id.spinner2);
        generar = (Button) findViewById(R.id.button);
        generar.setOnClickListener(this);

        Call<List<PuntosDeRecorrido>> call = PuntosRecorridoApi.createService().getAll();
        call.enqueue(new Callback<List<PuntosDeRecorrido>>() {
            @Override
            public void onResponse(Call<List<PuntosDeRecorrido>> call, Response<List<PuntosDeRecorrido>> response) {
                lista = response.body();


                for(PuntosDeRecorrido p: lista){
                    System.out.println(p.getCantAsientos());
                }

                paradas = new InteractiveArrayAdapterSpinner(VentaPasajesEfectivo.this, getModel());
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
                System.out.println(puntoOrigen.getId()+" "+puntoOrigen.getCantAsientos());

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
                puntoDestino = (PuntosDeRecorrido) destino.getSelectedItem();
                System.out.println(puntoDestino.getCantAsientos());
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
                        }else{
                            if (puntoOrigen.getCantAsientos() >0) {

                                Call<List<Pasaje>> call = PasajeApi.createService().getAll();
                                call.enqueue(new Callback<List<Pasaje>>() {
                                    @Override
                                    public void onResponse(Call<List<Pasaje>> call, Response<List<Pasaje>> response) {
                                        PasajesVendidos = response.body();
                                    }
                                    @Override
                                    public void onFailure(Call<List<Pasaje>> call, Throwable t) {
                                        System.out.println("onFailure");}
                                });
                                if(PasajesVendidos.isEmpty() == false){
                                   ultimoIdPasaje = PasajesVendidos.get(PasajesVendidos.size() - 1).getId()+1;}
                                if (PasajesVendidos.isEmpty() == true){
                                    ultimoIdPasaje = 1;
                                }
                                //LLAMO LISTA PRECIOS DE API PARA PODER CREAR PASAJE
                                Call<List<Precio>> call3 = PrecioApi.createService().getAll();
                                call3.enqueue(new Callback<List<Precio>>() {
                                    @Override
                                    public void onResponse(Call<List<Precio>> call, Response<List<Precio>> response) {
                                        listadoPrecios = response.body();
                                        for(Precio p: listadoPrecios) {

                                            System.out.println(p.getId()+" "+p.getIdViaje()+" "+p.getMonto());
                                            if(p.getIdViaje() == codViaje){

                                                idPrecio = p.getId();
                                                montoPrecio = p.getMonto();

                                            }
                                        }
                                         pasaje = new Pasaje(ultimoIdPasaje, codViaje, puntoOrigen.getId(), puntoDestino.getId(), idPrecio, 1, montoPrecio, true);
                                        Call<Pasaje> call2 = PasajeApi.createService().setPasaje(pasaje);
                                        call2.enqueue(new Callback<Pasaje>() {
                                            @Override
                                            public void onResponse(Call<Pasaje> call, Response<Pasaje> response) {
                                                Pasaje p = response.body();
                                                if(p!=null){
                                                   dialogoPasajeVendido(p).show();}
                                                else{
                                                    Toast.makeText(VentaPasajesEfectivo.this, "No se pudo generar el pasaje con Destino:" + " " + puntoDestino.getNombre(), Toast.LENGTH_LONG).show();
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
                                        System.out.println("onFailure");}
                                });



                            } else {Toast.makeText(VentaPasajesEfectivo.this, "Es lamentable pero no hay lugar para ti", Toast.LENGTH_LONG).show();}
                        }

                }
            }
        });
    }
    private AlertDialog dialogoPasajeVendido(Pasaje pasaje)
    {
        // Instanciamos un nuevo AlertDialog Builder y le asociamos titulo y mensaje
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Pasaje Vendido");
        alertDialogBuilder.setMessage("Pasaje con destino:"+" "+pasaje.getIdDestino()+"\n"+"Precio:"+" "+pasaje.getMonto());
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

    private List<PuntosDeRecorrido> getModel() {
        List<PuntosDeRecorrido> list = new ArrayList<PuntosDeRecorrido>();
        for(PuntosDeRecorrido e: lista){
            list.add(e);
        }return list;
    }
    @Override
    public void onClick(View v) {


    }

}
