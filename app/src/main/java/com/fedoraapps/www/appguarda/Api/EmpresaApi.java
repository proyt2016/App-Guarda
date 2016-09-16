package com.fedoraapps.www.appguarda.Api;

import com.fedoraapps.www.appguarda.AddHeaderInterceptor;
import com.fedoraapps.www.appguarda.Shares.DataConfiguracionEmpresa;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by maxi on 05/09/16.
 */
public class EmpresaApi {

    private static EmpresaApiInterface empresaService;
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    public static EmpresaApiInterface createService() {
        if (empresaService == null) {
            //AGREGO ID EN EL HEADER DEL REQUEST
            httpClient.addInterceptor(new AddHeaderInterceptor());
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.43.158:8080")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();

            empresaService = retrofit.create(EmpresaApiInterface.class);
        }

        return empresaService;
    }

    public interface EmpresaApiInterface {

        @GET("/lcbsapi/rest/empresa/getconfirguacionempresa/")
        Call<DataConfiguracionEmpresa> getConfiguracionEmpresa();
    }
}
