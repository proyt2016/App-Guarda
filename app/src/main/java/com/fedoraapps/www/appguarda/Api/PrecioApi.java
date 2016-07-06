package com.fedoraapps.www.appguarda.Api;

import com.fedoraapps.www.appguarda.Model.Precio;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by maxi on 07/06/2016.
 */
public class PrecioApi {
    private static PrecioApiInterface precioService;
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    public static PrecioApiInterface createService() {
        if (precioService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://574f74b25dd0e51100a9408c.mockapi.io")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();

            precioService = retrofit.create(PrecioApiInterface.class);
        }

        return precioService;
    }

    public interface PrecioApiInterface {
        @GET("/Precio")
        Call<List<Precio>> getAll();
    }
}
