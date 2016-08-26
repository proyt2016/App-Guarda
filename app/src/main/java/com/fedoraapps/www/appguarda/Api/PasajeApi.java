package com.fedoraapps.www.appguarda.Api;

import com.fedoraapps.www.appguarda.Shares.DataPasajeConvertor;

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
                    .baseUrl("http://192.168.1.191:8080")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();

            pasajeService = retrofit.create(PasajeApiInterface.class);
        }

        return pasajeService;
    }
    public interface PasajeApiInterface {

        @GET("/lcbsapi/rest/viajes/getpasajes/1/999999898")
        Call<List<DataPasajeConvertor>> getAll();

        @POST("/lcbsapi/rest/viajes/comprarpasaje")
        Call<DataPasajeConvertor> venderPasaje(@Body DataPasajeConvertor pasaje);

        @GET("/lcbsapi/rest/viajes/getpasajeporcodigo/{codigoPasaje}")
        Call<DataPasajeConvertor> getPasajePorCodigo(@Path("codigoPasaje") int codigoPasaje);

        @POST("/lcbsapi/rest/viajes/procesarpasaje/{idPasaje}")
        Call<Void> procesarPasaje(@Path("idPasaje") String idPasaje);

    }
}
