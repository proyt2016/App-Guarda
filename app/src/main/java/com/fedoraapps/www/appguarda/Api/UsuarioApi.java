package com.fedoraapps.www.appguarda.Api;

import com.fedoraapps.www.appguarda.Model.Usuario;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
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
                    .baseUrl("http://574f74b25dd0e51100a9408c.mockapi.io")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();

            usuarioService = retrofit.create(UsuarioApiInterface.class);
        }

        return usuarioService;
    }

    public interface UsuarioApiInterface {
        @GET("/usuarios/{id}")
        Call<Usuario> getByUsuario(@Path("id") int id);
    }
}