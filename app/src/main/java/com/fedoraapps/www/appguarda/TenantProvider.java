package com.fedoraapps.www.appguarda;

/**
 * Created by maxi on 30/08/16.
 */
public class TenantProvider {

    static String ids = BuildConfig.API_KEY;
    static String api_url = BuildConfig.API_URL;
    static String name = BuildConfig.APP_NAME;

    public  static String GetTenant(){
        return ids;
    }

    public static  String GetApiUrl(){
        return api_url;
    }

    public static String GetNameApp(){ return name; }
}
