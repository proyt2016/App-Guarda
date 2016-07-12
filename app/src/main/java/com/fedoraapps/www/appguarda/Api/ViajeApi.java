package com.fedoraapps.www.appguarda.Api;

import com.fedoraapps.www.appguarda.Shares.DataViaje;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by maxi on 04/06/2016.
 */
public class ViajeApi {
    private static ViajeApiInterface viajeService;
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    public static ViajeApiInterface createService() {
        if (viajeService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.1.188:8080")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();

            viajeService = retrofit.create(ViajeApiInterface.class);
        }

        return viajeService;
    }

    public interface ViajeApiInterface {

        @GET("/lcbsapi/rest/viajes/getviajes/1/99")
        Call<List<DataViaje>> getAll();

        @GET("/lcbsapi/rest/viajes/getviaje/{idViaje}")
        Call<DataViaje> getViaje(@Path("idViaje") String idViaje);
        //@GET("/terminales/{id}")
        //Call<Viaje> getById(@Path("id") int id);

//        @GET("/terminales/{id}?search={search}")
//        Call<List<Terminal>> getSearch(@Path("id") int id, @Path("search") String search);
    }
}
