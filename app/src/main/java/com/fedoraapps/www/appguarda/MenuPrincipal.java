package com.fedoraapps.www.appguarda;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.fedoraapps.www.appguarda.Api.PasajeApi;
import com.fedoraapps.www.appguarda.Shares.DataPasajeConvertor;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuPrincipal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private Button scanBtn;
    private List<DataPasajeConvertor> PasajesVendidos = new ArrayList<>();
    private String codViaje;
    private Button Manual;
    private int codigoPasaje;
    private String codRecorrido;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //CONEXION CON MENU
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        codViaje = getIntent().getExtras().getString("codigoViaje");
        codRecorrido = getIntent().getExtras().getString("codigo");
        scanBtn = (Button)findViewById(R.id.scan_button);
        Manual = (Button)findViewById(R.id.manual);
        Manual.setOnClickListener(this);
        scanBtn.setOnClickListener(this);
    }
    public void onClick(View v) {
        if (v.getId() == R.id.scan_button) {
            //LLAMO AL SCAN SI ESTA INSTALADO ABRE LA CAM SINO MOTIVA A INSTALAR BARCODE SCAN
            IntentIntegrator scanIntegrator = new IntentIntegrator(this);
            scanIntegrator.initiateScan();
        }
        if (v.getId() == R.id.manual) {
            //LLAMO AL SCAN SI ESTA INSTALADO ABRE LA CAM SINO MOTIVA A INSTALAR BARCODE SCAN
           Intent i = new Intent(this,ProcesarPasajesVendidosManual.class);
            i.putExtra("codigoViaje",codViaje);
            i.putExtra("codigoRecorrido",codRecorrido);
            startActivity(i);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.ventaEfectivo) {

            codRecorrido = getIntent().getExtras().getString("codigo");
            codViaje = getIntent().getExtras().getString("codigoViaje");
            Intent i = new Intent(MenuPrincipal.this,VentaPasajesEfectivo.class);
            i.putExtra("codigo",codRecorrido);
            i.putExtra("codigoViaje",codViaje);
            startActivity(i);

            // Handle the camera action
        } else if (id == R.id.ventaOnline) {
            codRecorrido = getIntent().getExtras().getString("codigo");
            codViaje = getIntent().getExtras().getString("codigoViaje");
            Intent i = new Intent(MenuPrincipal.this,VentaPasajesOnline.class);
            i.putExtra("codigo",codRecorrido);
            i.putExtra("codigoViaje",codViaje);
            startActivity(i);
        }/*else if (id == R.id.procesarPasajesEscaner) {
            codRecorrido = getIntent().getExtras().getString("codigo");
            codViaje = getIntent().getExtras().getString("codigoViaje");
            Intent i = new Intent(MenuPrincipal.this,ProcesarPasajesVendidosEscaner.class);
            i.putExtra("codigo",codRecorrido);
            i.putExtra("codigoViaje",codViaje);
            startActivity(i);
        }
        else if (id == R.id.procesarPasajesManual) {
            codRecorrido = getIntent().getExtras().getString("codigo");
            codViaje = getIntent().getExtras().getString("codigoViaje");
            Intent i = new Intent(MenuPrincipal.this,ProcesarPasajesVendidosManual.class);
            i.putExtra("codigo",codRecorrido);
            i.putExtra("codigoViaje",codViaje);
            startActivity(i);
        }*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (resultCode != RESULT_CANCELED) {


            if (scanningResult != null) {
                //OBTENGO EL CODIGO DEL PASAJE
                final String scanContent = scanningResult.getContents();
                //OBTENGO EL FORMATE DEL CODIGO DE BARRAS COMO INFORMACION ADICIONAL
                String scanFormat = scanningResult.getFormatName();

                //VERIFICO SI EXISTE PASAJE EN MEMORIA
                Call<DataPasajeConvertor> call = PasajeApi.createService().getPasajePorCodigo(Integer.parseInt(scanContent));
                call.enqueue(new Callback<DataPasajeConvertor>() {
                    @Override
                    public void onResponse(Call<DataPasajeConvertor> call, Response<DataPasajeConvertor> response) {
                        DataPasajeConvertor pasaje = response.body();

                        if (pasaje != null && pasaje.getUsado()==false) {
                            //CAMBIO ESTADO A PASAJE UTILIZADO
                            Call<Void> call2 = PasajeApi.createService().procesarPasaje(pasaje.getId());
                            call2.enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {}
                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {
                                    System.out.println("*****FALLO EL SERVICIO*****");}
                            });
                            //MUESTRO EN DIALOG OPERACION EXITOSA
                            crearDialogoConexion(pasaje).show();

                        }else{
                            //PASAJE YA FUE UTILIZADO
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
            } else {
                //NO SE REALIZO EL ESCANEO
                Toast.makeText(getApplication(), "NO HAY DATOS DEL ESCANEO!", Toast.LENGTH_SHORT).show();}
        }
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
        alertDialogBuilder.setMessage("El pasaje  ya fue utilizado, Te quiere cagar!");
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
            public void onClick(DialogInterface dialog, int which) {}
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
        alertDialogBuilder.setMessage("Numero de Pasaje:"+" "+pasaje.getId()+"\n"
                +"Monto:"+" "+/*pasaje.getMonto()+*/"\n"
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
    public DataPasajeConvertor existePasaje(String codPasaje){
        for(DataPasajeConvertor p: PasajesVendidos){
            if( p.getId().equals(codPasaje)){
                return p;
            }
        }return null;
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
//aquí vendría las acciones que tengo que realizar
            this.finish();


            return true;

        }

        return super.onKeyDown(keyCode, event);
    }
}
