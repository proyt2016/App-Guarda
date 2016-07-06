package com.fedoraapps.www.appguarda.Api;

import com.fedoraapps.www.appguarda.Model.Pasaje;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by maxi on 06/06/2016.
 */
public class PasajeApi {

    private static PasajeApiInterface pasajeService ;
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    public static PasajeApiInterface createService() {
        if (pasajeService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://574f74b25dd0e51100a9408c.mockapi.io")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();

            pasajeService = retrofit.create(PasajeApiInterface.class);
        }

        return pasajeService;
    }
    public interface PasajeApiInterface {
        @GET("/Pasaje")
        Call<List<Pasaje>> getAll();

        @POST("/Pasaje")
        Call<Pasaje> setPasaje(@Body Pasaje pasaje);
    }
}
