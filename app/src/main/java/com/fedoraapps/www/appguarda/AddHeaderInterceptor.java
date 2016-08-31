package com.fedoraapps.www.appguarda;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by maxi on 30/08/16.
 */
public class AddHeaderInterceptor implements Interceptor{

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {

        Request.Builder builder = chain.request().newBuilder();
        builder.addHeader("lcbs-tenant", TenantProvider.GetTenant());

        return chain.proceed(builder.build());
    }
}
