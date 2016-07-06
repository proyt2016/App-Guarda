package com.fedoraapps.www.appguarda.Api;

import com.fedoraapps.www.appguarda.Model.PuntosDeRecorrido;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by maxi on 06/06/2016.
 */
public class PuntosRecorridoApi {

    private static PuntosRecorridoApiInterface recorridosService;
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    public static PuntosRecorridoApiInterface createService() {
        if (recorridosService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://574f74b25dd0e51100a9408c.mockapi.io")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();

            recorridosService = retrofit.create(PuntosRecorridoApiInterface.class);
        }

        return recorridosService;
    }

    public interface PuntosRecorridoApiInterface {
        @GET("/PuntosDeRecorrido")
        Call<List<PuntosDeRecorrido>> getAll();
    }
}
