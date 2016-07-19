package com.fedoraapps.www.appguarda;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.fedoraapps.www.appguarda.Api.ViajeApi;
import com.fedoraapps.www.appguarda.Shares.DataViaje;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Main extends AppCompatActivity implements View.OnClickListener{
    ListView listTrip;
    ArrayAdapter<DataViaje> adapter;
    List<DataViaje> ListResponse;
    EditText filtro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Asociar Elementos XML
        listTrip =(ListView)findViewById(R.id.listTrip);
        filtro = (EditText)findViewById(R.id.inputSearch);
        filtro.setOnClickListener(this);
        listTrip.setTextFilterEnabled(true);


        Call<List<DataViaje>> call = ViajeApi.createService().getAll();
        call.enqueue(new Callback<List<DataViaje>>() {
            @Override
            public void onResponse(Call<List<DataViaje>> call, Response<List<DataViaje>> response) {
                ListResponse = response.body();
                System.out.println("----------------***************************->>>"+ListResponse.size());
                if(ListResponse != null){


                        adapter = new InteractiveArrayAdapterRecorridos(Main.this, ListResponse);
                        listTrip.setAdapter(adapter);
                        for (DataViaje t : ListResponse) {
                            adapter.notifyDataSetChanged();
                        }

                }else{
                    ListaVaciaDialog().show();
                }
            }
            @Override
            public void onFailure(Call<List<DataViaje>> call, Throwable t) {
                System.out.println("onFailure***************************************//////*"+t.getCause()+" "+call.request().toString());}
        });



        //CALL LOGIN
       Intent i = new Intent(Main.this,Login.class);
        startActivity(i);


     if (filtro.getText() != null) {
            //FILTRO DE BUSQUEDA
            filtro.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                    if(arg0!=null || arg0!=" "){
                        Main.this.adapter.getFilter().filter(arg0);
                    }
                }

                @Override
                public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

                }

                @Override
                public void afterTextChanged(Editable arg0) {
                    adapter.getFilter().filter(arg0);
                }
            });}
         /* Activando el filtro de busqueda */



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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private List<DataViaje> getModel() {
        List<DataViaje> list = new ArrayList<DataViaje>();
          for (DataViaje t : ListResponse) {
                list.add(t);
            }
            return list;
        }


    private String getModelEmpty() {
        return "No existen recorridos cargados";
    }

    @Override
    public void onClick(View v) {

    }
    private AlertDialog ListaVaciaDialog()
    {
        // Instanciamos un nuevo AlertDialog Builder y le asociamos titulo y mensaje
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Atencion!");
        alertDialogBuilder.setMessage("No existen Viajes cargados");
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
}
