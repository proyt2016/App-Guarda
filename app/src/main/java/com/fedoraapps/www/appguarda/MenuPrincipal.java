package com.fedoraapps.www.appguarda;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
    private TextView textoButtonEscaner;
    private TextView textoButtonManual;
    private DrawerLayout pantalla;
    private NavigationView barraMenu;
    private RelativeLayout pantalla2;
    private TextView titulo;
    private int codigoPasaje;
    private String codRecorrido;
    private ImageView logo;
    private TextView mailEmpresa;
    private TextView nombreEmpresa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        codViaje = getIntent().getExtras().getString("codigoViaje");
        codRecorrido = getIntent().getExtras().getString("codigo");

        scanBtn = (Button)findViewById(R.id.scan_button);
        Manual = (Button)findViewById(R.id.manual);

        nombreEmpresa = (TextView) findViewById(R.id.nombreempresa);
        mailEmpresa = (TextView)findViewById(R.id.mailempresa);



        Manual.setOnClickListener(this);
        scanBtn.setOnClickListener(this);

        //AGREGAR LOGOOOOOOOOOOOOOO
        logo = (ImageView)findViewById(R.id.imageview);

        pantalla = (DrawerLayout)findViewById(R.id.drawer_layout);
        pantalla2 = (RelativeLayout)findViewById(R.id.contentmenu);

        barraMenu = (NavigationView)findViewById(R.id.nav_view);

        titulo = (TextView)findViewById(R.id.titulomenuprincipal);

        textoButtonEscaner = (TextView)findViewById(R.id.textoescaner);

        textoButtonManual = (TextView)findViewById(R.id.textomanual);


        //CONEXION CON MENU
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if(Farcade.configuracionEmpresa.getId()!=null) {
            if(Farcade.configuracionEmpresa.getColorFondosDePantalla()!=null){
                pantalla.setBackgroundColor(Color.parseColor(Farcade.configuracionEmpresa.getColorFondosDePantalla()));
            }else {
                pantalla.setBackgroundColor(Color.parseColor("#ffff4444"));
            }
            if(Farcade.configuracionEmpresa.getColorFondosDePantalla()!=null){
                pantalla2.setBackgroundColor(Color.parseColor(Farcade.configuracionEmpresa.getColorFondosDePantalla()));}
            else{
                pantalla2.setBackgroundColor(Color.parseColor("#ffff4444"));}
            if (Farcade.configuracionEmpresa.getColorFondosDePantalla() != null) {
                barraMenu.setBackgroundColor(Color.parseColor(Farcade.configuracionEmpresa.getColorFondosDePantalla()));}
            else{
                barraMenu.setBackgroundColor(Color.parseColor("#ffff4444"));
            }
            if(Farcade.configuracionEmpresa.getColorTitulo()!=null){
                titulo.setTextColor(Color.parseColor(Farcade.configuracionEmpresa.getColorTitulo()));
                nombreEmpresa.setTextColor(Color.parseColor(Farcade.configuracionEmpresa.getColorTitulo()));
                mailEmpresa.setTextColor(Color.parseColor(Farcade.configuracionEmpresa.getColorTitulo()));
            }
            else{
                titulo.setTextColor(Color.parseColor("#ffffffff"));
                nombreEmpresa.setTextColor(Color.parseColor("#000000"));
                mailEmpresa.setTextColor(Color.parseColor("#ffffffff"));
            }
            if(Farcade.configuracionEmpresa.getColorLetras()!=null){
                textoButtonManual.setTextColor(Color.parseColor(Farcade.configuracionEmpresa.getColorLetras()));}
            else{
                textoButtonManual.setTextColor(Color.parseColor("#ffffffff"));
            }
            if(Farcade.configuracionEmpresa.getColorLetras()!=null){
                textoButtonEscaner.setTextColor(Color.parseColor(Farcade.configuracionEmpresa.getColorLetras()));}
            else{
                textoButtonEscaner.setTextColor(Color.parseColor("#ffffffff"));
            }
            if(Farcade.configuracionEmpresa.getEmails()!=null){
                if(!Farcade.configuracionEmpresa.getEmails().isEmpty()){
                mailEmpresa.setText(Farcade.configuracionEmpresa.getEmails().get(0).getEmail());
                }else{
                    mailEmpresa.setText("tecnologo2016@gmail.com");
                }
            }else{
                mailEmpresa.setText("tecnologo2016@gmail.com");
            }
            if(Farcade.configuracionEmpresa.getNombre()!=null){
                nombreEmpresa.setText(Farcade.configuracionEmpresa.getNombre());
            }else{
                nombreEmpresa.setText("LAC BUS");
            }
            if(Farcade.configuracionEmpresa.getIconoEmpresa()!=null){
                logo.setImageURI(Uri.parse(Farcade.configuracionEmpresa.getIconoEmpresa()));
            }else{
                logo.setImageResource(R.drawable.bondi_blanco);
            }

             //titulo.setBackgroundColor(R.drawable.abc_list_selector_background_transition_holo_dark);
        }else{
                //NO EXISTE CONFIGURACION
            pantalla.setBackgroundColor(Color.parseColor("#ffff4444"));
            pantalla2.setBackgroundColor(Color.parseColor("#ffff4444"));
            barraMenu.setBackgroundColor(Color.parseColor("#ffff4444"));
            titulo.setTextColor(Color.parseColor("#ffffffff"));
            textoButtonManual.setTextColor(Color.parseColor("#ffffffff"));
            textoButtonEscaner.setTextColor(Color.parseColor("#ffffffff"));
            nombreEmpresa.setTextColor(Color.parseColor("#000000"));
            mailEmpresa.setTextColor(Color.parseColor("#ffffffff"));
            logo.setImageResource(R.drawable.bondi_blanco);
            mailEmpresa.setText("tecnologo2016@gmail.com");
            nombreEmpresa.setText("LAC BUS");
        }
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
        /*if (id == R.id.action_settings) {
            return true;
        }*/


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
        }else if (id == R.id.datosEmpleado) {

         /*   Intent i = new Intent(MenuPrincipal.this,ProcesarPasajesVendidosEscaner.class);
            i.putExtra("codigo",codRecorrido);
            i.putExtra("codigoViaje",codViaje);
            startActivity(i);*/
        }/*
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
        //AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        ContextThemeWrapper ctw = new ContextThemeWrapper( this, R.style.DialogBlack);
        AlertDialog.Builder Dialog = new AlertDialog.Builder(ctw);
        Dialog.setTitle("Atencion");
        Dialog.setMessage("No Existe pasaje vendido");
        Dialog.setIcon(R.drawable.icono_alerta);;
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
        Dialog.setPositiveButton(R.string.ACEPTAR, listenerOk);
        // alertDialogBuilder.setNegativeButton(R.string.Cancelar, listenerCancelar);
        return Dialog.create();
    }
    private AlertDialog dialogPasajeUsado()
    {
        // Instanciamos un nuevo AlertDialog Builder y le asociamos titulo y mensaje
       // AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        ContextThemeWrapper ctw = new ContextThemeWrapper( this, R.style.DialogBlack);
        AlertDialog.Builder Dialog = new AlertDialog.Builder(ctw);

        Dialog.setTitle("Atencion");
        Dialog.setMessage("El pasaje  ya fue utilizado, Te quiere cagar!");
        Dialog.setIcon(R.drawable.icono_alerta);;
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
        Dialog.setPositiveButton(R.string.ACEPTAR, listenerOk);
        // alertDialogBuilder.setNegativeButton(R.string.Cancelar, listenerCancelar);
        return Dialog.create();
    }
    private AlertDialog dialogCodigoIncorrectoTipeo()
    {
        // Instanciamos un nuevo AlertDialog Builder y le asociamos titulo y mensaje
       // AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        ContextThemeWrapper ctw = new ContextThemeWrapper( this, R.style.DialogBlack);
        AlertDialog.Builder Dialog = new AlertDialog.Builder(ctw);
        Dialog.setTitle("Atencion");

        Dialog.setMessage("Debe ingresar un codigo valido, unicamente letras");
        Dialog.setIcon(R.drawable.icono_alerta);;

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
        Dialog.setPositiveButton(R.string.ACEPTAR, listenerOk);
        // alertDialogBuilder.setNegativeButton(R.string.Cancelar, listenerCancelar);
        return Dialog.create();
    }
    private AlertDialog crearDialogoConexion(DataPasajeConvertor pasaje)
    {
        // Instanciamos un nuevo AlertDialog Builder y le asociamos titulo y mensaje
       // AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        ContextThemeWrapper ctw = new ContextThemeWrapper( this, R.style.DialogBlack);
        AlertDialog.Builder Dialog = new AlertDialog.Builder(ctw);
        Dialog.setTitle("Pasaje Vendido");
        Dialog.setMessage("Numero de Pasaje:"+" "+pasaje.getId()+"\n"
                +"Monto:"+" "+/*pasaje.getMonto()+*/"\n"
                + "Con destino a"+" "+pasaje.getDestino().getNombre());
        Dialog.setIcon(R.drawable.icono_cash_black);;
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
        Dialog.setPositiveButton(R.string.ACEPTAR, listenerOk);
        // alertDialogBuilder.setNegativeButton(R.string.Cancelar, listenerCancelar);
        return Dialog.create();
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
