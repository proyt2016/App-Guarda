package com.fedoraapps.www.appguarda;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
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
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import com.fedoraapps.www.appguarda.Api.PasajeApi;
import com.fedoraapps.www.appguarda.Api.PuntosRecorridoApi;
import com.fedoraapps.www.appguarda.Model.Pasaje;
import com.fedoraapps.www.appguarda.Model.Precio;
import com.fedoraapps.www.appguarda.Shares.DataEmpleado;
import com.fedoraapps.www.appguarda.Shares.DataPasajeConvertor;
import com.fedoraapps.www.appguarda.Shares.DataPrecio;
import com.fedoraapps.www.appguarda.Shares.DataPuntoRecorridoConverter;
import com.fedoraapps.www.appguarda.Shares.DataRecorridoConvertor;
import com.fedoraapps.www.appguarda.Shares.DataUsuario;
import com.fedoraapps.www.appguarda.Shares.DataViajeConvertor;

import java.io.IOException;
import java.util.ArrayList;
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
    String idPunto = null;
    CheckBox chek;
    Spinner origen;
    DataRecorridoConvertor reco;
    Spinner destino;
    private int codigoPasaje;
    private List<DataRecorridoConvertor> recorrido;
    Double distancia;
    String idPto;
    Button generar;
    String valOfSpinner;
    String valOfSpinner2;
    List<DataPuntoRecorridoConverter> puntosCercanos = new ArrayList<>();
    DataPuntoRecorridoConverter puntoOrigen;
    DataPuntoRecorridoConverter puntoDestino;
    private List<DataPasajeConvertor> PasajesVendidos = new ArrayList<>();
    static String codRecorrido;
    private TextView gps;
    int ultimoIdPasaje;
    int idPrecio;
    int montoPrecio;
    private Pasaje pasaje;
    static String codViaje;
    ArrayAdapter<DataPuntoRecorridoConverter> adapter;
    private ListView listaPuntos;
    ArrayAdapter<DataPuntoRecorridoConverter> paradas;
    private List<Precio> listadoPrecios = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.venta_efectivo_new);

        codRecorrido = controller.getRecorridoSeleccionado().getRecorrido().getId();
        codViaje = controller.getRecorridoSeleccionado().getId();

        origen = (Spinner) findViewById(R.id.spinner);
        listaPuntos = (ListView) findViewById(R.id.listaPuntos);
        //     destino = (Spinner) findViewById(R.id.spinner2);
        generar = (Button) findViewById(R.id.button);

        listaPuntos.setItemsCanFocus(true);
        listaPuntos.animate().start();
        listaPuntos.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listaPuntos.setDividerHeight(3);

        System.out.println("DESTINO SELECCIONADO ID !!!----!!!"+Farcade.recorridoSeleccionado.getRecorrido().getId());



        generar.setOnClickListener(this);
        /* Uso de la clase LocationManager para obtener la localizacion del GPS */
        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Localizacion Local = new Localizacion();

        Local.setMainActivity(this);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0,
                (LocationListener) Local);

        Farcade farcade = new Farcade();

        reco = farcade.getRecorridoSeleccionado().getRecorrido();
        Call<DataRecorridoConvertor> call4 = PuntosRecorridoApi.createService().getRecorrido(Farcade.recorridoSeleccionado.getRecorrido().getId());
        call4.enqueue(new Callback<DataRecorridoConvertor>() {
            @Override
            public void onResponse(Call<DataRecorridoConvertor> call, Response<DataRecorridoConvertor> response) {

                DataRecorridoConvertor recos= response.body();
                adapter = new InteractiveArrayAdapterPuntosRecorrido(VentaPasajesEfectivo.this, recos.getPuntosDeRecorrido());
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
                                        adapter = new InteractiveArrayAdapterPuntosRecorrido(VentaPasajesEfectivo.this, recorrido.getPuntosDeRecorrido());
                                        listaPuntos.setAdapter(adapter);
                                        dialogoTryOrigenDestino().show();
                                    }
                                    @Override
                                    public void onFailure(Call<DataRecorridoConvertor> call, Throwable t) {
                                        System.out.println("*****FALLO EL SERVICIO*****" + t.getCause());
                                    }
                                });


                            } else {
                                        DataUsuario usuario = new DataUsuario();
                                        DataEmpleado empleado = new DataEmpleado();
                                        Date fechaVenc = new Date();
                                        String ciUsuario = "4444";
                                        DataPrecio precio = new DataPrecio();
                                        DataViajeConvertor VIAJE = new DataViajeConvertor();
                                        VIAJE.setId(controller.getRecorridoSeleccionado().getId());

                                        DataPuntoRecorridoConverter ori = new DataPuntoRecorridoConverter();

                                        ori.setId(puntoOrigen.getId());
                                        ori.setTipo(puntoOrigen.getTipo());

                                        DataPuntoRecorridoConverter punto = controller.getDestinoSeleccionado();
                                        DataPuntoRecorridoConverter dest = new DataPuntoRecorridoConverter();

                                        if (punto != null) {
                                            dest.setId(punto.getId());
                                            dest.setTipo(punto.getTipo());
                                        }

                                        DataPasajeConvertor pasaje = new DataPasajeConvertor(VIAJE, null, ori, dest, null, null, null, null, true, true, false);

                                        Call<DataPasajeConvertor> call3 = PasajeApi.createService().venderPasaje(pasaje);
                                        call3.enqueue(new Callback<DataPasajeConvertor>() {

                                            @Override
                                            public void onResponse(Call<DataPasajeConvertor> call, Response<DataPasajeConvertor> response) {
                                                DataPasajeConvertor p = response.body();
                                                System.out.println("PASAJE---->" + p + "<----PASAJE");

                                                if (p != null) {


                                                    Call<DataRecorridoConvertor> call4= PuntosRecorridoApi.createService().getRecorrido(Farcade.recorridoSeleccionado.getRecorrido().getId());
                                                    call4.enqueue(new Callback<DataRecorridoConvertor>() {
                                                        @Override
                                                        public void onResponse(Call<DataRecorridoConvertor> call, Response<DataRecorridoConvertor> response) {

                                                            DataRecorridoConvertor recorrido = response.body();

                                                            controller.setDestinoSeleccionado(null);
                                                            final DataPasajeConvertor pe = new DataPasajeConvertor();
                                                            adapter = new InteractiveArrayAdapterPuntosRecorrido(VentaPasajesEfectivo.this, recorrido.getPuntosDeRecorrido());
                                                            listaPuntos.setAdapter(adapter);
                                                            dialogoPasajeVendido(pe).show();
                                                        }
                                                            @Override
                                                            public void onFailure(Call<DataRecorridoConvertor> call, Throwable t) {
                                                                System.out.println("*****FALLO EL SERVICIO*****" + t.getCause());
                                                            }
                                                        });

                                                }else{


                                                    Call<DataRecorridoConvertor> call4= PuntosRecorridoApi.createService().getRecorrido(Farcade.recorridoSeleccionado.getRecorrido().getId());
                                                    call4.enqueue(new Callback<DataRecorridoConvertor>() {
                                                        @Override
                                                        public void onResponse(Call<DataRecorridoConvertor> call, Response<DataRecorridoConvertor> response) {

                                                            DataRecorridoConvertor recorrido = response.body();

                                                            controller.setDestinoSeleccionado(null);
                                                            final DataPasajeConvertor pe = new DataPasajeConvertor();
                                                            adapter = new InteractiveArrayAdapterPuntosRecorrido(VentaPasajesEfectivo.this, recorrido.getPuntosDeRecorrido());
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
                                                    adapter = new InteractiveArrayAdapterPuntosRecorrido(VentaPasajesEfectivo.this, recorrido.getPuntosDeRecorrido());
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
                                adapter = new InteractiveArrayAdapterPuntosRecorrido(VentaPasajesEfectivo.this, recorrido.getPuntosDeRecorrido());
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
             System.out.println("DISTANCIA***************************"+" "+distComp);
                 if (distancia == null) {
                     distancia = distComp;
                 }
                     if (distComp < distancia) {
                         if(!puntosCercanos.contains(pto)) {
                             puntosCercanos.add(pto);
                             paradas = new InteractiveArrayAdapterSpinner(VentaPasajesEfectivo.this, puntosCercanos);
                             origen.setAdapter(paradas);}
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

        // Este metodo se ejecuta cada vez que se detecta un cambio en el
        // status del proveedor de localizacion (GPS)
        // Los diferentes Status son:
        // OUT_OF_SERVICE -> Si el proveedor esta fuera de servicio
        // TEMPORARILY_UNAVAILABLE -> Temporalmente no disponible pero se
        // espera que este disponible en breve
        // AVAILABLE -> Disponible
        }

    }/* Fin de la clase localizacion */




