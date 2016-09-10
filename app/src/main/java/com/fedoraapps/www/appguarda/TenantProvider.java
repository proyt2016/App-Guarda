package com.fedoraapps.www.appguarda;

/**
 * Created by maxi on 30/08/16.
 */
public class TenantProvider {

    static String ids = BuildConfig.API_KEY;

    public  static String GetTenant(){

        return ids;
    }
}
