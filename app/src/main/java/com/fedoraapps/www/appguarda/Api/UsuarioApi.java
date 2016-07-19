package com.fedoraapps.www.appguarda.Api;

import com.fedoraapps.www.appguarda.Model.Usuario;
import com.fedoraapps.www.appguarda.Shares.DataUsuario;
import com.google.gson.JsonObject;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by andres on 14/5/16.
 */
public class UsuarioApi {
    private static UsuarioApiInterface usuarioService;
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    public static UsuarioApiInterface createService() {
        if (usuarioService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.1.188:8080")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();

            usuarioService = retrofit.create(UsuarioApiInterface.class);
        }

        return usuarioService;
    }

    public interface UsuarioApiInterface {

        @POST("/lcbsapi/rest/usuarios/loginusuario")
        Call<DataUsuario> getByUsuario(@Body JsonObject caca);
    }
}