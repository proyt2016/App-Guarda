package com.fedoraapps.www.appguarda.Api;

import com.fedoraapps.www.appguarda.Model.Viaje;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by maxi on 04/06/2016.
 */
public class ViajeApi {
    private static ViajeApiInterface viajeService;
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    public static ViajeApiInterface createService() {
        if (viajeService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://574f74b25dd0e51100a9408c.mockapi.io")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();

            viajeService = retrofit.create(ViajeApiInterface.class);
        }

        return viajeService;
    }

    public interface ViajeApiInterface {
        @GET("/viajes")
        Call<List<Viaje>> getAll();

        //@GET("/terminales/{id}")
        //Call<Viaje> getById(@Path("id") int id);

//        @GET("/terminales/{id}?search={search}")
//        Call<List<Terminal>> getSearch(@Path("id") int id, @Path("search") String search);
    }
}
