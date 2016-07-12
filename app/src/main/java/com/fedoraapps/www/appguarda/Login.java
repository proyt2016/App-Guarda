package com.fedoraapps.www.appguarda;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fedoraapps.www.appguarda.Api.UsuarioApi;
import com.fedoraapps.www.appguarda.Model.Usuario;
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

        final ProgressDialog progressDialog = new ProgressDialog(Login.this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Autenticando...");
        progressDialog.show();

        String user = _userText.getText().toString();
        String password = _passwordText.getText().toString();

        /*int userId = 0;
        if(user.equals("admin")){
            userId = 1;
        }else if(user.equals("empleado")){
            userId = 2;
        }else{
            onLoginFailed();
            progressDialog.hide();
            return;
        }*/
        JsonObject caca = new JsonObject();
        caca.addProperty("usuario",_userText.getText().toString());
        caca.addProperty("clave",_passwordText.getText().toString());

        Call<Boolean> call = UsuarioApi.createService().getByUsuario(caca);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.isSuccessful()){

                    boolean log = response.body();

                    if (log == true){
                        new android.os.Handler().postDelayed(
                                new Runnable() {
                                    public void run() {
                                        // On complete call either onLoginSuccess or onLoginFailed
                                        onLoginSuccess();
                                        progressDialog.dismiss();
                                    }
                                }, 3000);
                    }else{
                        System.out.println("onResponse password distintos");
                        onLoginFailed();
                        progressDialog.dismiss();
                    }
                }else{
                    onLoginFailed();
                    System.out.println("onResponse no isSuccessful");
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                System.out.println("onFailure");
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
        Toast.makeText(getBaseContext(), "Fallo el inicio de sesión", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _userText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty()) {
            _userText.setError("ingrese su usuario");
            valid = false;
        } else {
            _userText.setError(null);
        }

        if (password.isEmpty()) {
            _passwordText.setError("ingrese su clave");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }

}
