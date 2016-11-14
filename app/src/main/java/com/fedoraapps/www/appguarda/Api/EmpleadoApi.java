package com.fedoraapps.www.appguarda.Api;

import com.fedoraapps.www.appguarda.AddHeaderInterceptor;
import com.fedoraapps.www.appguarda.Shares.DataEmpleado;
import com.fedoraapps.www.appguarda.TenantProvider;
import com.google.gson.JsonObject;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by andres on 14/5/16.
 */
public class EmpleadoApi {
    private static UsuarioApiInterface usuarioService;
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    public static UsuarioApiInterface createService() {
        if (usuarioService == null) {
            TenantProvider tenantConfig = new TenantProvider();
            String apiUrl = tenantConfig.GetApiUrl();

            httpClient.addInterceptor(new AddHeaderInterceptor());
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(apiUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();

            usuarioService = retrofit.create(UsuarioApiInterface.class);
        }

        return usuarioService;
    }

    public interface UsuarioApiInterface {

        @POST("/lcbsapi/rest/usuarios/loginempleado")
        Call<DataEmpleado> login(@Body JsonObject caca);
    }
}