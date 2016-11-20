package com.fedoraapps.www.appguarda;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.fedoraapps.www.appguarda.Api.EmpleadoApi;
import com.fedoraapps.www.appguarda.Api.EmpresaApi;
import com.fedoraapps.www.appguarda.Shares.DataConfiguracionEmpresa;
import com.fedoraapps.www.appguarda.Shares.DataEmpleado;
import com.google.gson.JsonObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by maxi on 04/06/2016.
 */
public class Login extends AppCompatActivity {

    private ScrollView pantalla;
    private ImageView logoEmpres;
    private LinearLayout layoutInternoLogin;
    Farcade farcade = new Farcade();
    ProgressDialog progressDialog;
    DataConfiguracionEmpresa d = new DataConfiguracionEmpresa();

    private static final String TAG = "Login";

    @Bind(R.id.input_user)

    EditText _userText;
    @Bind(R.id.input_password)

    EditText _passwordText;
    @Bind(R.id.btn_login)Button _loginButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_login);
        ButterKnife.bind(this);

        DataConfiguracionEmpresa conf = farcade.configuracionEmpresa;

        pantalla = (ScrollView) findViewById(R.id.loginfondo);
        logoEmpres = (ImageView)findViewById(R.id.logoEmpresa);
        layoutInternoLogin = (LinearLayout)findViewById(R.id.layoutinternologin);

        logoEmpres.setImageResource(R.drawable.bondi_blanco);



        Call<DataConfiguracionEmpresa> call3 = EmpresaApi.createService().getConfiguracionEmpresa();
        call3.enqueue(new Callback<DataConfiguracionEmpresa>() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onResponse(Call<DataConfiguracionEmpresa> call, Response<DataConfiguracionEmpresa> response) {
                if(response.isSuccessful()) {
                   d = response.body();
                    if(Farcade.configuracionEmpresa.getId()!=null){
                        if(Farcade.configuracionEmpresa.getIconoEmpresa()!=null){

                            logoEmpres.setImageResource(R.drawable.bondi_blanco);}

                        if(Farcade.configuracionEmpresa.getColorFondosDePantalla()!=null){
                            pantalla.setBackgroundColor((Color.parseColor(d.getColorFondosDePantalla())));
                        //    pantalla.setForegroundTintList(ColorStateList.valueOf(Color.parseColor(d.getColorFondosDePantalla())));
                            layoutInternoLogin.setBackgroundColor(Color.parseColor(d.getColorFondosDePantalla()));
                         //   layoutInternoLogin.setForegroundTintList(ColorStateList.valueOf(Color.parseColor(d.getColorFondosDePantalla())));
                            }
                        else{
                            pantalla.setBackgroundColor(Color.parseColor("#0b7bff"));
                           // pantalla.setForegroundTintList(ColorStateList.valueOf(Color.parseColor("#ffff4444")));
                            layoutInternoLogin.setBackgroundColor(Color.parseColor("#0b7bff"));
                           // layoutInternoLogin.setForegroundTintList(ColorStateList.valueOf(Color.parseColor("#ffff4444")));

                        }
                        if(Farcade.configuracionEmpresa.getColorLetras()!=null){
                            _userText.setTextColor(ColorStateList.valueOf(Color.parseColor(d.getColorLetras())));
                            _passwordText.setTextColor(ColorStateList.valueOf(Color.parseColor(d.getColorLetras())));
                            _loginButton.setTextColor(ColorStateList.valueOf(Color.parseColor(d.getColorLetras())));}
                        else{
                            _userText.setTextColor(ColorStateList.valueOf(Color.parseColor("#ffffffff")));
                            _passwordText.setTextColor(ColorStateList.valueOf(Color.parseColor("#ffffffff")));
                            _loginButton.setTextColor(ColorStateList.valueOf(Color.parseColor("#ffffffff")));
                        }
                        if(Farcade.configuracionEmpresa.getColorBoton()!=null){
                            _loginButton.setBackgroundColor(Color.parseColor(d.getColorBoton()));}
                        else{
                            _loginButton.setBackgroundColor(Color.parseColor("#5a595b"));
                        }
                    }
                }else{
                    //NO EXISTE CONFIGURACION EMPRESA O RESPONSE NULL SE CONF APP POR DEFECTO
                        logoEmpres.setImageResource(R.drawable.bondi_blanco);
                        pantalla.setBackgroundColor(Color.parseColor("#0b7bff"));
                      //  pantalla.setForegroundTintList(ColorStateList.valueOf(Color.parseColor("#ffff4444")));
                        layoutInternoLogin.setBackgroundColor(Color.parseColor("#ffff4444"));
                       // layoutInternoLogin.setForegroundTintList(ColorStateList.valueOf(Color.parseColor("#ffff4444")));
                        _userText.setTextColor(ColorStateList.valueOf(Color.parseColor("#ffffffff")));
                        _passwordText.setTextColor(ColorStateList.valueOf(Color.parseColor("#ffffffff")));
                        _loginButton.setBackgroundColor(Color.parseColor("#5a595b"));

                    Toast.makeText(Login.this,"NO EXISTE CONFIGURACION",Toast.LENGTH_LONG).show();
                }
            }
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onFailure(Call<DataConfiguracionEmpresa> call, Throwable t) {
                Toast.makeText(Login.this,"FALLO EL WEB-SERVICE",Toast.LENGTH_LONG).show();
                logoEmpres.setImageResource(R.drawable.bondi_blanco);
                pantalla.setBackgroundColor(Color.parseColor("#0b7bff"));
          //      pantalla.setForegroundTintList(ColorStateList.valueOf(Color.parseColor("#ffff4444")));
                layoutInternoLogin.setBackgroundColor(Color.parseColor("#0b7bff"));
//                layoutInternoLogin.setForegroundTintList(ColorStateList.valueOf(Color.parseColor("#ffff4444")));
                _userText.setTextColor(ColorStateList.valueOf(Color.parseColor("#ffffffff")));
                _passwordText.setTextColor(ColorStateList.valueOf(Color.parseColor("#ffffffff")));
                _loginButton.setBackgroundColor(Color.parseColor("#5a595b"));

            }
        });

       _loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }


        _loginButton.setEnabled(false);
        if(Farcade.configuracionEmpresa.getId()!=null && Farcade.configuracionEmpresa.getColorFondosDePantalla()!=null) {

            progressDialog = new ProgressDialog(Login.this, R.style.DialogBlack);

            progressDialog.setIndeterminate(true);


            progressDialog.setMessage("Autenticando...");
            progressDialog.show();
        }else{

            progressDialog = new ProgressDialog(Login.this,R.style.DialogBlack);

            progressDialog.setIndeterminate(true);

            progressDialog.setMessage("Autenticando...");
            progressDialog.show();

        }

        String user = _userText.getText().toString();
        String password = _passwordText.getText().toString();

        JsonObject empleado = new JsonObject();
        empleado.addProperty("usuario",_userText.getText().toString());
        empleado.addProperty("clave",_passwordText.getText().toString());

        Call<DataEmpleado> call = EmpleadoApi.createService().login(empleado);
        call.enqueue(new Callback<DataEmpleado>() {
            @Override
            public void onResponse(Call<DataEmpleado> call, Response<DataEmpleado> response) {
                if(response.isSuccessful()) {

                    final DataEmpleado empleado = response.body();

                    if (empleado!=null) {


                                new android.os.Handler().postDelayed(
                                        new Runnable() {
                                            public void run() {
                                                // On complete call either onLoginSuccess or onLoginFailed
                                                //GUARDO EL EMPLEADO EN MEMORIA
                                                Farcade.empleado = empleado;
                                                onLoginSuccess();
                                                progressDialog.dismiss();
                                            }
                                        }, 3000);

                        /*} else {
                            if(empleado.getEmail().getEmail() != _userText.getText().toString()){
                                System.out.println("Usuario ingresado incorrecto");
                                Toast.makeText(Login.this,"Usuario ingresado incorrecto",Toast.LENGTH_LONG).show();
                                onLoginFailed();
                                progressDialog.dismiss();
                            }else {
                                System.out.println("Contrasenia ingresada incorrecta");
                                Toast.makeText(Login.this,"Contrasenia ingresada incorrecta",Toast.LENGTH_LONG).show();
                            }
                        }*/
                    }else{

                        new android.os.Handler().postDelayed(
                                new Runnable() {
                                    public void run() {
                                        // On complete call either onLoginSuccess or onLoginFailed
                                        //GUARDO EL EMPLEADO EN MEMORIA
                                        Farcade.empleado = null;
                                        onLoginFailed();
                                        progressDialog.dismiss();
                                        System.out.println("No existe Usuario Registrado");
                                        Toast.makeText(Login.this,"Usuario y/o contraseña incorrectos",Toast.LENGTH_LONG).show();
                                    }
                                }, 3000);

                    }
                }else{
                    Toast.makeText(Login.this,"Fallo el servicio, contactar con LACBUS",Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();}
            }
            @Override
            public void onFailure(Call<DataEmpleado> call, Throwable t) {
                System.out.println("Fallo el Servicio, contactar LacBus");
                onLoginFailed();
                progressDialog.dismiss();
            }
        });
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
       // Toast.makeText(getBaseContext(), "Error en Usuario/Contrasenia", Toast.LENGTH_LONG).show();
        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _userText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty()) {
            _userText.setError("Ingrese su usuario");
            valid = false;
        } else {
            _userText.setError(null);
        }

        if (password.isEmpty()) {
            _passwordText.setError("Ingrese su clave");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }

}
