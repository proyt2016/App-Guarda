package com.fedoraapps.www.appguarda;

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
import com.fedoraapps.www.appguarda.Model.Viaje;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Main extends AppCompatActivity implements View.OnClickListener{
    ListView listTrip;
    ArrayAdapter<Viaje> adapter;
    List<Viaje> ListResponse;
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
        listTrip.setTextFilterEnabled(true);

        Call<List<Viaje>> call = ViajeApi.createService().getAll();
        call.enqueue(new Callback<List<Viaje>>() {
            @Override
            public void onResponse(Call<List<Viaje>> call, Response<List<Viaje>> response) {
                ListResponse = response.body();
                adapter = new InteractiveArrayAdapterTerminales(Main.this,getModel());
                listTrip.setAdapter(adapter);
                for (Viaje t : ListResponse) {
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<List<Viaje>> call, Throwable t) {
                System.out.println("onFailure");}
        });



        //CALL LOGIN
       // Intent i = new Intent(Main.this,Login.class);
      //  startActivity(i);


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
    private List<Viaje> getModel() {
        List<Viaje> list = new ArrayList<Viaje>();
        for (Viaje t : ListResponse) {
            list.add(t);
        }return list;
    }

    @Override
    public void onClick(View v) {

    }
}
