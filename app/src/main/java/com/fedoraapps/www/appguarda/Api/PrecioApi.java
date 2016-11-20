package com.fedoraapps.www.appguarda.Api;


import com.fedoraapps.www.appguarda.TenantProvider;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by maxi on 07/06/2016.
 */
public class PrecioApi {
    private static PrecioApiInterface precioService;
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    public static PrecioApiInterface createService() {
        if (precioService == null) {
            TenantProvider tenantConfig = new TenantProvider();
            String apiUrl = tenantConfig.GetApiUrl();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(apiUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();

            precioService = retrofit.create(PrecioApiInterface.class);
        }

        return precioService;
    }

    public interface PrecioApiInterface {

        @GET("/lcbsapi/rest/viajes/getpreciodepasaje/{codigoOrigen}/{codigoDestino}/{codigoRecorrido}")
        Call<Float> getPrecio(@Path("codigoOrigen") final String codigoOrigen, @Path("codigoDestino") final String codigoDestino, @Path("codigoRecorrido") final String codigoRecorrido);
    }
}
