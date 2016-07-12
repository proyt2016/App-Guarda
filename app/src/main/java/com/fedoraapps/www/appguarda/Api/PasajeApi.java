package com.fedoraapps.www.appguarda.Api;

import com.fedoraapps.www.appguarda.Model.Pasaje;
import com.fedoraapps.www.appguarda.Shares.DataPasaje;
import com.google.gson.JsonObject;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by maxi on 06/06/2016.
 */
public class PasajeApi {

    private static PasajeApiInterface pasajeService ;
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    public static PasajeApiInterface createService() {
        if (pasajeService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.1.43:8080")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();

            pasajeService = retrofit.create(PasajeApiInterface.class);
        }

        return pasajeService;
    }
    public interface PasajeApiInterface {

        @POST("/lcbsapi/rest/viajes/comprarpasaje/")
        Call<DataPasaje> venderPasaje(@Body DataPasaje   pasaje);

        @GET("/Pasaje")
        Call<List<Pasaje>> getAll();

        @POST("/Pasaje")
        Call<Pasaje> setPasaje(@Body Pasaje pasaje);
    }
}
