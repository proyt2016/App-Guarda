package com.fedoraapps.www.appguarda.Api;

import com.fedoraapps.www.appguarda.Model.PagoOnline;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by maxi on 07/06/2016.
 */
public class PagosOnlineApi {

    private static PagosOnlineApiInterface pagoOnlineService ;
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    public static PagosOnlineApiInterface createService() {
        if (pagoOnlineService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://574f74b25dd0e51100a9408c.mockapi.io/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();

            pagoOnlineService = retrofit.create(PagosOnlineApiInterface.class);
        }

        return pagoOnlineService;
    }
    public interface PagosOnlineApiInterface {
        @GET("/PagosOnline")
        Call<List<PagoOnline>> getAll();


    }
}
