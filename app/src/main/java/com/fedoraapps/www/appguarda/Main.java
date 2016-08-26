package com.fedoraapps.www.appguarda;

import android.app.AlertDialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.fedoraapps.www.appguarda.Api.ViajeApi;
import com.fedoraapps.www.appguarda.Shares.DataRecorridoConvertor;
import com.fedoraapps.www.appguarda.Shares.DataViajeConvertor;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Main extends AppCompatActivity implements View.OnClickListener{

    ListView listTrip;
    ArrayAdapter<DataViajeConvertor> adapter;
    List<DataViajeConvertor> ListResponse;
    //SearchView filtro;
    EditText filtro;
    public TextView mensaje1;
    TextView mensaje2;

    private ArrayList<DataRecorridoConvertor> array_sort = new ArrayList<DataRecorridoConvertor>();
    int textlength = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Asociar Elementos XML
        listTrip = (ListView) findViewById(R.id.listTrip);
        filtro = (EditText) findViewById(R.id.filtro);
        filtro.addTextChangedListener(filterTextWatcher);

        listTrip.setTextFilterEnabled(true);


        JsonObject filtroviaje = new JsonObject();

        filtroviaje.addProperty("fechaSalida", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

        Call<List<DataViajeConvertor>> call = ViajeApi.createService().getAll(filtroviaje);
        call.enqueue(new Callback<List<DataViajeConvertor>>() {
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


        //CALL LOGIN
        //  Intent i = new Intent(Main.this,Login.class);
        //    startActivity(i);

            //FILTRO DE BUSQUEDA
         /* filtro.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

                        Main.this.adapter.getFilter().filter(arg0);

                }

                @Override
                public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                }

                @Override
                public void afterTextChanged(Editable arg0) {

                }
            });*/
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



}



