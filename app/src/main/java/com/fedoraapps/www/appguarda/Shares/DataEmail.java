package com.fedoraapps.www.appguarda.Shares;

/**
 * Created by maxi on 11/07/16.
 */
public class DataEmail {
    private String descripcion;
    private String email;


    public DataEmail(){}

    public DataEmail(String desc, String em) {
        this.descripcion = desc;
        this.email = em;
    }

    public void setDescripcion(String val){
        this.descripcion = val;
    }

    public String getDescripcion(){
        return this.descripcion;
    }

    public void setEmail(String val){
        this.email = val;
    }

    public String getEmail(){
        return this.email;
    }
}
