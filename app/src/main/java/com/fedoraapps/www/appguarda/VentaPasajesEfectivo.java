package com.fedoraapps.www.appguarda;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.fedoraapps.www.appguarda.Api.PasajeApi;
import com.fedoraapps.www.appguarda.Api.PrecioApi;
import com.fedoraapps.www.appguarda.Api.PuntosRecorridoApi;
import com.fedoraapps.www.appguarda.Shares.DataEmpleado;
import com.fedoraapps.www.appguarda.Shares.DataPasajeConvertor;
import com.fedoraapps.www.appguarda.Shares.DataPrecio;
import com.fedoraapps.www.appguarda.Shares.DataPtoRecWrapper;
import com.fedoraapps.www.appguarda.Shares.DataPuntoRecorridoConverter;
import com.fedoraapps.www.appguarda.Shares.DataRecorridoConvertor;
import com.fedoraapps.www.appguarda.Shares.DataViajeConvertor;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.google.maps.android.SphericalUtil.computeDistanceBetween;

/**
 * Created by maxi on 05/06/2016.
 */
public class VentaPasajesEfectivo extends AppCompatActivity implements View.OnClickListener {

    Farcade controller = new Farcade();
    Spinner origen;
    Farcade farcade = new Farcade();
    DataPrecio precio = new DataPrecio();
    String fechaFormat = null;
    DataPuntoRecorridoConverter punto = new DataPuntoRecorridoConverter();
    DataViajeConvertor VIAJE = new DataViajeConvertor();
    DataRecorridoConvertor reco;
    DataEmpleado emp = new DataEmpleado();
    Double distancia;
    Float precioPasaje;
    Calendar dt;
    Button generar;
    String valOfSpinner;
    List<DataPuntoRecorridoConverter> puntosCercanos = new ArrayList<>();
    DataPuntoRecorridoConverter puntoOrigen;
    static String codRecorrido;
    static String codViaje;
    ArrayAdapter<DataPtoRecWrapper> adapter;
    private ListView listaPuntos;
    ArrayAdapter<DataPuntoRecorridoConverter> paradas;
    private List<DataPtoRecWrapper> temp = new ArrayList<DataPtoRecWrapper>();
    TextView tituloOrigen;
    TextView tituloDestino;
    RelativeLayout fondoDePantalla;
    Date ja = new Date();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.venta_efectivo_new);
        Date FechaCompra = new Date();
        Date fe = new Date();
        
        DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        String s = f.format(FechaCompra);
        try {
         fe  = f.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        System.out.println("FECHAAAAAAAAAAAAAAAAAAA COMPRAAAAAAAAAA------"+" "+fe);

        //System.out.println("----------> FECHAAAA"+new SimpleDateFormat("yyyy-MM-dd").format(new Date()));



        codRecorrido = controller.getRecorridoSeleccionado().getRecorrido().getId();
        codViaje = controller.getRecorridoSeleccionado().getId();

        origen = (Spinner) findViewById(R.id.spinner);
        listaPuntos = (ListView) findViewById(R.id.listaPuntos);
        //     destino = (Spinner) findViewById(R.id.spinner2);
        generar = (Button) findViewById(R.id.button);
        generar.setOnClickListener(this);

        listaPuntos.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listaPuntos.setDividerHeight(3);

        fondoDePantalla = (RelativeLayout) findViewById(R.id.ventaefectivolayout);

        tituloDestino = (TextView)findViewById(R.id.titulodestino);
        tituloOrigen = (TextView)findViewById(R.id.tituloorigen);


        if(Farcade.configuracionEmpresa.getId()!=null){
            if(Farcade.configuracionEmpresa.getColorFondosDePantalla()!=null){
                fondoDePantalla.setBackgroundColor(Color.parseColor(Farcade.configuracionEmpresa.getColorFondosDePantalla()));}
            else{
                fondoDePantalla.setBackgroundColor(Color.parseColor("#E12929"));
            }
            if(Farcade.configuracionEmpresa.getColorBoton()!=null){
                generar.setBackgroundColor(Color.parseColor(Farcade.configuracionEmpresa.getColorBoton()));}
            else{
                generar.setBackgroundColor(Color.parseColor("#5a595b"));
            }
            if(Farcade.configuracionEmpresa.getColorLetras()!=null){
                generar.setTextColor(Color.parseColor(Farcade.configuracionEmpresa.getColorLetras()));}
            else{
                generar.setTextColor(Color.parseColor("#ffffffff"));
            }
            if(Farcade.configuracionEmpresa.getColorFondoLista()!=null){
                listaPuntos.setBackgroundColor(Color.parseColor(Farcade.configuracionEmpresa.getColorFondoLista()));}
            else{
                listaPuntos.setBackgroundColor(Color.parseColor("#E12929"));
            }
            if(Farcade.configuracionEmpresa.getColorTitulo()!=null){
                tituloOrigen.setTextColor(Color.parseColor(Farcade.configuracionEmpresa.getColorTitulo()));}
            else{
                tituloOrigen.setTextColor(Color.parseColor("#ffffffff"));
            }
            if(Farcade.configuracionEmpresa.getColorTitulo()!=null){
                tituloDestino.setTextColor(Color.parseColor(Farcade.configuracionEmpresa.getColorTitulo()));}
            else{
                tituloDestino.setTextColor(Color.parseColor("#ffffffff"));
            }
        }else{
            //NO EXISTE CONFIGURACION
            fondoDePantalla.setBackgroundColor(Color.parseColor("#E12929"));
            generar.setBackgroundColor(Color.parseColor("#5a595b"));
            generar.setBackgroundColor(Color.parseColor("#ffffffff"));
            listaPuntos.setBackgroundColor(Color.parseColor("#E12929"));
            tituloOrigen.setTextColor(Color.parseColor("#ffffffff"));
            tituloDestino.setTextColor(Color.parseColor("#ffffffff"));
        }

        System.out.println("DESTINO SELECCIONADO ID !!!----!!!"+Farcade.recorridoSeleccionado.getRecorrido().getId());

        /* Uso de la clase LocationManager para obtener la localizacion del GPS */
        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Localizacion Local = new Localizacion();
        Local.setMainActivity(this);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;}
        mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0,(LocationListener) Local);

        reco = farcade.getRecorridoSeleccionado().getRecorrido();
        Call<DataRecorridoConvertor> call4 = PuntosRecorridoApi.createService().getRecorrido(Farcade.recorridoSeleccionado.getRecorrido().getId());
        call4.enqueue(new Callback<DataRecorridoConvertor>() {
            @Override
            public void onResponse(Call<DataRecorridoConvertor> call, Response<DataRecorridoConvertor> response) {

                DataRecorridoConvertor recos= response.body();

                for(DataPuntoRecorridoConverter iter : recos.getPuntosDeRecorrido()){

                    temp.add(new DataPtoRecWrapper(iter));

                }
                adapter = new InteractiveArrayAdapterPuntosRecorrido(VentaPasajesEfectivo.this, temp);
                listaPuntos.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<DataRecorridoConvertor> call, Throwable t) {
                System.out.println("*****FALLO EL SERVICIO*****" + t.getCause());
            }
        });
        origen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            //GET SPINNER
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                valOfSpinner = origen.getSelectedItem().toString();
                puntoOrigen = (DataPuntoRecorridoConverter) origen.getSelectedItem();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });



       generar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {



                if (v.getId() == R.id.button) {
                    for(int i=0 ; i < listaPuntos.getAdapter().getCount(); i++)
                    {
                        listaPuntos.setItemChecked(i, false);
                        listaPuntos.setItemsCanFocus(false);
                        listaPuntos.clearChoices();
                    }

                    if (valOfSpinner != null) {
                        if (controller.getDestinoSeleccionado()!= null) {

                            if (valOfSpinner.equals(controller.getDestinoSeleccionado().getNombre())) {

                                Call<DataRecorridoConvertor> call4= PuntosRecorridoApi.createService().getRecorrido(Farcade.recorridoSeleccionado.getRecorrido().getId());
                                call4.enqueue(new Callback<DataRecorridoConvertor>() {
                                    @Override
                                    public void onResponse(Call<DataRecorridoConvertor> call, Response<DataRecorridoConvertor> response) {

                                        DataRecorridoConvertor recorrido = response.body();

                                        controller.setDestinoSeleccionado(null);
                                        final DataPasajeConvertor pe = new DataPasajeConvertor();
                                        for(DataPtoRecWrapper d : temp){
                                            d.setChecked(false);
                                        }
                                        adapter = new InteractiveArrayAdapterPuntosRecorrido(VentaPasajesEfectivo.this, temp);
                                        listaPuntos.setAdapter(adapter);
                                        dialogoTryOrigenDestino().show();
                                    }
                                    @Override
                                    public void onFailure(Call<DataRecorridoConvertor> call, Throwable t) {
                                        System.out.println("*****FALLO EL SERVICIO*****" + t.getCause());
                                    }
                                });


                            } else {

                                Date FechaCompra = new Date();
                                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                                fechaFormat = df.format(FechaCompra);
                                VIAJE.setId(controller.getRecorridoSeleccionado().getId());


                                final DataPuntoRecorridoConverter ori = new DataPuntoRecorridoConverter();

                                 punto = controller.getDestinoSeleccionado();
                                    if(Farcade.empleado!=null){
                                        emp = Farcade.empleado;
                                    }
                                ///aca
                                System.out.println("RECORRIDOOOO>>>>>>>>>>>>>>>>"+" "+reco.getNombre());

                                Call<Integer> call4= PasajeApi.createService().obtenerPasajesDisponibles(Farcade.recorridoSeleccionado.getId(),puntoOrigen.getId(),punto.getId());
                                call4.enqueue(new Callback<Integer>() {
                                    @Override
                                    public void onResponse(Call<Integer> call, Response<Integer> response) {

                                        int i = response.body();
                                        System.out.println("CANTIDAD DE ASIENTOS DISPONIBLES ===========>"+" "+i);

                                        System.out.println("ORIGEN ID ===========>"+" "+puntoOrigen.getId());
                                        System.out.println("DESTIONO ID ===========>"+" "+punto.getId());
                                        System.out.println("RECORRIDO ID ===========>"+" "+Farcade.recorridoSeleccionado.getRecorrido().getId());


                                        if(i>1) {
                                            Call<Float> call4 = PrecioApi.createService().getPrecio(puntoOrigen.getId(),punto.getId(),Farcade.recorridoSeleccionado.getRecorrido().getId());
                                            call4.enqueue(new Callback<Float>() {
                                                @Override
                                                public void onResponse(Call<Float> call, Response<Float> response) {

                                                     precioPasaje = response.body();
                                                    System.out.println(" ===========>"+" "+response.body());


                                                    if(response.isSuccessful()) {


                                                        DataPasajeConvertor pasaje = new DataPasajeConvertor(VIAJE, puntoOrigen, punto, fechaFormat, null, null, emp, true, true, false);


                                                        Call<DataPasajeConvertor> call3 = PasajeApi.createService().venderPasaje(pasaje);
                                                        call3.enqueue(new Callback<DataPasajeConvertor>() {

                                                            @Override
                                                            public void onResponse(Call<DataPasajeConvertor> call, Response<DataPasajeConvertor> response) {
                                                                final DataPasajeConvertor pasajeConvertor = response.body();
                                                                System.out.println("PASAJE---->" + pasajeConvertor + "<----PASAJE");

                                                                if (pasajeConvertor != null) {


                                                                    Call<DataRecorridoConvertor> call4 = PuntosRecorridoApi.createService().getRecorrido(Farcade.recorridoSeleccionado.getRecorrido().getId());
                                                                    call4.enqueue(new Callback<DataRecorridoConvertor>() {
                                                                        @Override
                                                                        public void onResponse(Call<DataRecorridoConvertor> call, Response<DataRecorridoConvertor> response) {

                                                                            DataRecorridoConvertor recorrido = response.body();

                                                                            controller.setDestinoSeleccionado(null);

                                                                            for (DataPtoRecWrapper d : temp) {
                                                                                d.setChecked(false);
                                                                            }
                                                                            adapter = new InteractiveArrayAdapterPuntosRecorrido(VentaPasajesEfectivo.this, temp);
                                                                            listaPuntos.setAdapter(adapter);
                                                                            dialogoPasajeVendido(pasajeConvertor).show();
                                                                        }

                                                                        @Override
                                                                        public void onFailure(Call<DataRecorridoConvertor> call, Throwable t) {
                                                                            System.out.println("*****FALLO EL SERVICIO*****" + t.getCause());
                                                                        }
                                                                    });

                                                                } else {


                                                                    Call<DataRecorridoConvertor> call4 = PuntosRecorridoApi.createService().getRecorrido(Farcade.recorridoSeleccionado.getRecorrido().getId());
                                                                    call4.enqueue(new Callback<DataRecorridoConvertor>() {
                                                                        @Override
                                                                        public void onResponse(Call<DataRecorridoConvertor> call, Response<DataRecorridoConvertor> response) {

                                                                            DataRecorridoConvertor recorrido = response.body();

                                                                            controller.setDestinoSeleccionado(null);
                                                                            final DataPasajeConvertor pe = new DataPasajeConvertor();
                                                                            for (DataPtoRecWrapper d : temp) {
                                                                                d.setChecked(false);
                                                                            }
                                                                            adapter = new InteractiveArrayAdapterPuntosRecorrido(VentaPasajesEfectivo.this, temp);
                                                                            listaPuntos.setAdapter(adapter);
                                                                            dialogoPasajeNoVendido(pe).show();
                                                                        }

                                                                        @Override
                                                                        public void onFailure(Call<DataRecorridoConvertor> call, Throwable t) {
                                                                            System.out.println("*****FALLO EL SERVICIO*****" + t.getCause());
                                                                        }
                                                                    });
                                                                }
                                                            }

                                                            @Override
                                                            public void onFailure(Call<DataPasajeConvertor> call, Throwable t) {
                                                                System.out.println("*****FALLO EL SERVICIO*****" + t.getCause());
                                                            }
                                                        });
                                                   }else{
                                                        noSeCreoPrecio().show();
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<Float> call, Throwable t) {
                                                    System.out.println("*****FALLO EL SERVICIO*****" + t.getCause());
                                                }
                                            });
                                        }else{

                                            SinPasajeDisponible().show();

                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Integer> call, Throwable t) {
                                        System.out.println("*****FALLO EL SERVICIO*****" + t.getCause());
                                    }
                                });


                                //ACA FINALIZA EL VER CANT PASAJES

                            }
                                } else {

                                        if (controller.getDestinoSeleccionado() == null) {

                                            Call<DataRecorridoConvertor> call4= PuntosRecorridoApi.createService().getRecorrido(Farcade.recorridoSeleccionado.getRecorrido().getId());
                                            call4.enqueue(new Callback<DataRecorridoConvertor>() {
                                                @Override
                                                public void onResponse(Call<DataRecorridoConvertor> call, Response<DataRecorridoConvertor> response) {

                                                    DataRecorridoConvertor recorrido = response.body();

                                                    controller.setDestinoSeleccionado(null);
                                                    final DataPasajeConvertor pe = new DataPasajeConvertor();
                                                    for(DataPtoRecWrapper d : temp){
                                                        d.setChecked(false);
                                                    }
                                                    adapter = new InteractiveArrayAdapterPuntosRecorrido(VentaPasajesEfectivo.this, temp);
                                                    listaPuntos.setAdapter(adapter);
                                                    dialogoListaSeleccionVacia().show();
                                                }
                                                @Override
                                                public void onFailure(Call<DataRecorridoConvertor> call, Throwable t) {
                                                    System.out.println("*****FALLO EL SERVICIO*****" + t.getCause());
                                                }
                                            });
                            }
                          /*  if (controller.getListaDestinoSeleccionado().size() > 1) {

                                        //REFRESH LISTA
                                adapter = new InteractiveArrayAdapterPuntosRecorrido(VentaPasajesEfectivo.this, reco.getPuntosDeRecorrido());
                                listaPuntos.setAdapter(adapter);
                                        controller.getListaDestinoSeleccionado().clear();

                                        dialogoMuchosDestino().show();

                            }*/

                        }

                    }else{

                        Call<DataRecorridoConvertor> call4= PuntosRecorridoApi.createService().getRecorrido(Farcade.recorridoSeleccionado.getRecorrido().getId());
                        call4.enqueue(new Callback<DataRecorridoConvertor>() {
                            @Override
                            public void onResponse(Call<DataRecorridoConvertor> call, Response<DataRecorridoConvertor> response) {

                                DataRecorridoConvertor recorrido = response.body();

                                controller.setDestinoSeleccionado(null);
//                                final DataPasajeConvertor pe = new DataPasajeConvertor();
                                for(DataPtoRecWrapper d : temp){
                                    d.setChecked(false);
                                }
                                adapter = new InteractiveArrayAdapterPuntosRecorrido(VentaPasajesEfectivo.this,temp);
                                listaPuntos.setAdapter(adapter);
                                spinnerNull().show();
                            }
                            @Override
                            public void onFailure(Call<DataRecorridoConvertor> call, Throwable t) {
                                System.out.println("*****FALLO EL SERVICIO*****" + t.getCause());
                            }
                        });



                    }
                }
            }
        });
    }




    private AlertDialog dialogoPasajeVendido(DataPasajeConvertor pasaje)
    {
        // Instanciamos un nuevo AlertDialog Builder y le asociamos titulo y mensaje
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Pasaje Vendido");
        alertDialogBuilder.setMessage("Destino:"+" "+pasaje.getDestino().toString()+"\n"
                  +"Monto:"+" "+precioPasaje);//+"Precio:"+" "+pasaje.getPrecio().getMonto());
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




            }
        };

        // Asignamos los botones positivo y negativo a sus respectivos listeners
        alertDialogBuilder.setPositiveButton(R.string.ACEPTAR, listenerOk);
        // alertDialogBuilder.setNegativeButton(R.string.Cancelar, listenerCancelar);

        return alertDialogBuilder.create();
    }

    private AlertDialog spinnerNull()
    {
        // Instanciamos un nuevo AlertDialog Builder y le asociamos titulo y mensaje
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Atencion!");
        alertDialogBuilder.setMessage("No existen Paradas Cercanas");//+" "+pasaje.getDestino().getNombre()+"\n"+"Precio:"+" "+pasaje.getPrecio().getMonto());
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

    private AlertDialog dialogoMuchosDestino()
    {
        // Instanciamos un nuevo AlertDialog Builder y le asociamos titulo y mensaje
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Atencion!");
        alertDialogBuilder.setMessage("Debe seleccionar un solo destino");//+" "+pasaje.getDestino().getNombre()+"\n"+"Precio:"+" "+pasaje.getPrecio().getMonto());
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

    private AlertDialog dialogoListaSeleccionVacia()
    {
        // Instanciamos un nuevo AlertDialog Builder y le asociamos titulo y mensaje
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Atencion!");
        alertDialogBuilder.setMessage("Debe Seleccionar un Destino");//+" "+pasaje.getDestino().getNombre()+"\n"+"Precio:"+" "+pasaje.getPrecio().getMonto());
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


    private AlertDialog dialogoPasajeNoVendido(DataPasajeConvertor pasaje)
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
    private AlertDialog SinPasajeDisponible()
    {
        // Instanciamos un nuevo AlertDialog Builder y le asociamos titulo y mensaje
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Atencion!");
        alertDialogBuilder.setMessage("No hay pasajes disponibles");
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
    private AlertDialog noSeCreoPrecio()
    {
        // Instanciamos un nuevo AlertDialog Builder y le asociamos titulo y mensaje
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Atencion!");
        alertDialogBuilder.setMessage("No es posible calcular Monto");
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

    public void calcularPuntos(final Location loc) {

        puntosCercanos = new ArrayList<>();
        for (final DataPuntoRecorridoConverter pto : reco.getPuntosDeRecorrido()) {
             LatLng ubicacionPunto = new LatLng(Double.parseDouble(pto.getUbicacionMapa().split(",")[0]), Double.parseDouble(pto.getUbicacionMapa().split(",")[1]));
             LatLng miUbicacion = new LatLng(loc.getLatitude(),loc.getLongitude());
             final Double distComp = computeDistanceBetween(ubicacionPunto, miUbicacion);
             System.out.println("DISTANCIA-COMPRENDIDA***************************"+" "+distComp);
            System.out.println("DISTANCIA***************************"+" "+distancia);
            System.out.println("DISTANCIA***************************"+" "+pto.getNombre());
             if (distancia == null) {
                 distancia = distComp;
                    puntosCercanos.add(pto);
                    paradas = new InteractiveArrayAdapterSpinner(VentaPasajesEfectivo.this, puntosCercanos);
                    origen.setAdapter(paradas);
             }
             if (distComp < distancia) {
                 if(!puntosCercanos.contains(pto)) {
                     puntosCercanos.add(pto);
                     paradas = new InteractiveArrayAdapterSpinner(VentaPasajesEfectivo.this, puntosCercanos);
                     origen.setAdapter(paradas);
                 }
            }
        }
    }




      /*  Set<DataPuntoRecorridoConverter> listaSinRepetidos = new HashSet<DataPuntoRecorridoConverter>(puntosCercanos);
        puntosCercanos.clear();
        puntosCercanos.addAll(listaSinRepetidos);
        */

    public void setLocation(Location loc) {
        //Obtener la direccion de la calle a partir de la latitud y la longitud
        if (loc.getLatitude() != 0.0 && loc.getLongitude() != 0.0) {
            try {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> list = geocoder.getFromLocation(
                        loc.getLatitude(), loc.getLongitude(), 1);
                if (!list.isEmpty()) {
                    Address DirCalle = list.get(0);
                   /* gps.setText("Mi direccion es: \n"
                            + DirCalle.getAddressLine(0));*/
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

/* Aqui empieza la Clase Localizacion */
    class Localizacion implements LocationListener {
    VentaPasajesEfectivo venta_efectivo;

    public VentaPasajesEfectivo getMainActivity() {
        return venta_efectivo;
    }

    public void setMainActivity(VentaPasajesEfectivo mainActivity) {
        this.venta_efectivo = mainActivity;
    }

    @Override
    public void onLocationChanged(Location loc) {
        // Este metodo se ejecuta cada vez que el GPS recibe nuevas coordenadas
        // debido a la deteccion de un cambio de ubicacion

        loc.getLatitude();
        loc.getLongitude();
        String Text = "Mi ubicacion actual es: " + "\n Lat = "
                + loc.getLatitude() + "\n Long = " + loc.getLongitude();
        System.out.println("UBICACION ACTUAL:"+"----->"+" "+Text);
        this.venta_efectivo.setLocation(loc);

        this.venta_efectivo.calcularPuntos(loc);
        //this.venta_efectivo.puntosCercanos.clear();
    }


        @Override
        public void onProviderDisabled(String provider) {
            // Este metodo se ejecuta cuando el GPS es desactivado
            System.out.println("**************"+"GPS Desactivado"+"******************");

        }

        @Override
        public void onProviderEnabled(String provider) {
            // Este metodo se ejecuta cuando el GPS es activado
            System.out.println("**************"+"GPS Activado"+"******************");

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

    }/* Fin de la clase localizacion */




