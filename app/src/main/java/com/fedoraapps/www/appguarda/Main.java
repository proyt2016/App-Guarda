package com.fedoraapps.www.appguarda;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.fedoraapps.www.appguarda.Api.EmpresaApi;
import com.fedoraapps.www.appguarda.Api.ViajeApi;
import com.fedoraapps.www.appguarda.Shares.DataConfiguracionEmpresa;
import com.fedoraapps.www.appguarda.Shares.DataViajeConvertor;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Main extends AppCompatActivity implements View.OnClickListener, SearchView.OnQueryTextListener {

    ListView listTrip;
    ArrayAdapter<DataViajeConvertor> adapter;
    List<DataViajeConvertor> ListResponse;
    CoordinatorLayout pantalla;


    Farcade farcade = new Farcade();
    EditText filtro;
    DataConfiguracionEmpresa d = new DataConfiguracionEmpresa();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Asociar Elementos XML

        pantalla = (CoordinatorLayout) findViewById(R.id.main);



        listTrip = (ListView) findViewById(R.id.listTrip);
        filtro = (EditText)findViewById(R.id.filtro);
        filtro.addTextChangedListener(filterTextWatcher);


        listTrip.setTextFilterEnabled(true);

        JsonObject filtroviaje = new JsonObject();

        filtroviaje.addProperty("fechaSalida", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

        //TRAIGO LA CONFIGURACION DE LA EMPRESA

        Call<DataConfiguracionEmpresa> call3 = EmpresaApi.createService().getConfiguracionEmpresa();
        call3.enqueue(new Callback<DataConfiguracionEmpresa>() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onResponse(Call<DataConfiguracionEmpresa> call, Response<DataConfiguracionEmpresa> response) {
                if(response.isSuccessful()) {
                    Farcade.configuracionEmpresa =  response.body();
                    //GUARDO EN MEMORIA LA CONFIGURACION PARA UTILIZARLA EN EL RESTO DE LA APP
                    if(Farcade.configuracionEmpresa.getId()!=null) {


                        if (Farcade.configuracionEmpresa.getColorFondosDePantalla() != null){
                            pantalla.setBackgroundColor(Color.parseColor(Farcade.configuracionEmpresa.getColorFondosDePantalla()));}
                            else{
                                pantalla.setBackgroundColor(Color.parseColor("#ffff4444"));
                        }
                        if(Farcade.configuracionEmpresa.getColorFondoLista()!=null){
                            listTrip.setBackgroundColor(Color.parseColor(Farcade.configuracionEmpresa.getColorFondoLista()));}
                            else{
                                listTrip.setBackgroundColor(Color.parseColor("#ffff4444"));
                        }
                        // listTrip.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(Farcade.configuracionEmpresa.getColorFondoLista())));
                        if(Farcade.configuracionEmpresa.getColorFondosDePantalla()!=null){
                            filtro.setBackgroundColor(Color.parseColor(Farcade.configuracionEmpresa.getColorFondosDePantalla()));}
                            else{
                                filtro.setBackgroundColor(Color.parseColor("#ffff4444"));
                        }
                        if(Farcade.configuracionEmpresa.getColorLetras()!=null){
                            filtro.setHintTextColor(Color.parseColor(Farcade.configuracionEmpresa.getColorLetras()));}
                            else{
                                filtro.setHintTextColor(Color.parseColor("#ff4c4c4c"));
                        }


                       // pantallaLista.setBackgroundColor(Color.parseColor(Farcade.configuracionEmpresa.getColorFondosDePantalla()));
                    }





                    System.out.println("CONFIGURACION!!!!!!!!!!!!!!!!!!!!" + Farcade.configuracionEmpresa.getColorFondosDePantalla());
                }else {
                    pantalla.setBackgroundColor(Color.parseColor("#ffff4444"));
                    Toast.makeText(Main.this,"NO SE PUDO CARGAR LA CONFIGURACION",Toast.LENGTH_LONG).show();
                }

            }
            @Override
            public void onFailure(Call<DataConfiguracionEmpresa> call, Throwable t) {
                System.out.println("******FALLO EL SERVICIO******" + t.getCause() + " " + call.request().toString());
            }
        });
       Intent i = new Intent(Main.this,Login.class);
       startActivity(i);









        Call<List<DataViajeConvertor>> call2 = ViajeApi.createService().getAll(filtroviaje);
        call2.enqueue(new Callback<List<DataViajeConvertor>>() {
            @Override
            public void onResponse(Call<List<DataViajeConvertor>> call, Response<List<DataViajeConvertor>> response) {
                ListResponse = response.body();
                if (ListResponse != null) {
                    adapter = new InteractiveArrayAdapterRecorridos(Main.this, ListResponse);

                    listTrip.setAdapter(adapter);
                    for (DataViajeConvertor t : ListResponse) {
                        adapter.notifyDataSetChanged();
                    }


                } else {
                    ListaVaciaDialog().show();
                }
            }

            @Override
            public void onFailure(Call<List<DataViajeConvertor>> call, Throwable t) {
                System.out.println("******FALLO EL SERVICIO******" + t.getCause() + " " + call.request().toString());
            }
        });


        }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);


         return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement



        return super.onOptionsItemSelected(item);
    }

    private List<DataViajeConvertor> getModel() {
        List<DataViajeConvertor> list = new ArrayList<DataViajeConvertor>();
        for (DataViajeConvertor t : ListResponse) {
            list.add(t);
        }
        return list;
    }

    private TextWatcher filterTextWatcher = new TextWatcher() {

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
            if (adapter != null) {
                adapter.getFilter().filter(s);
            } else {
                Log.d("filter", "no filter availible");
            }
        }
    };

    private String getModelEmpty() {
        return "No existen recorridos cargados";
    }

    @Override
    public void onClick(View v) {
    }

    private AlertDialog ListaVaciaDialog() {
        // Instanciamos un nuevo AlertDialog Builder y le asociamos titulo y mensaje
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Atencion!");
        alertDialogBuilder.setMessage("No existen Viajes cargados");
        alertDialogBuilder.setIcon(R.drawable.icono_alerta);
        ;
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
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}



