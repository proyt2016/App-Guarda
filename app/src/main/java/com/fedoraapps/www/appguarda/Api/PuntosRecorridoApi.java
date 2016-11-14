package com.fedoraapps.www.appguarda.Api;

import com.fedoraapps.www.appguarda.AddHeaderInterceptor;
import com.fedoraapps.www.appguarda.Shares.DataRecorridoConvertor;
import com.fedoraapps.www.appguarda.TenantProvider;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by maxi on 06/06/2016.
 */
public class PuntosRecorridoApi {

    private static PuntosRecorridoApiInterface recorridosService;
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    public static PuntosRecorridoApiInterface createService() {
        if (recorridosService == null) {
            TenantProvider tenantConfig = new TenantProvider();
            String apiUrl = tenantConfig.GetApiUrl();

            httpClient.addInterceptor(new AddHeaderInterceptor());
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(apiUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();

            recorridosService = retrofit.create(PuntosRecorridoApiInterface.class);
        }

        return recorridosService;
    }

    public interface PuntosRecorridoApiInterface {
        @GET("/lcbsapi/rest/viajes/listarrecorridos/1/999999998")
        Call<List<DataRecorridoConvertor>> getAll();

        @GET("/lcbsapi/rest/viajes/getrecorrido/{idRecorrido}")
        Call<DataRecorridoConvertor> getRecorrido(@Path("idRecorrido") final String filtro);



    }
}
