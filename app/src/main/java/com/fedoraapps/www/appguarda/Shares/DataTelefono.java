package com.fedoraapps.www.appguarda.Shares;

/**
 * Created by maxi on 11/07/16.
 */
public class DataTelefono {

    private String descripcion;
    private String telefono;



    public DataTelefono() {}

    public DataTelefono(String tel){
        this.descripcion = "Contacto";
        this.telefono = tel;
    }

    public DataTelefono(String desc, String tel) {
        this.descripcion = desc;
        this.telefono = tel;
    }

    public void setDescripcion(String val){
        this.descripcion = val;
    }

    public String getDescripcion(){
        return this.descripcion;
    }

    public void setTelefono(String val){
        this.telefono = val;
    }

    public String getTelefono(){
        return this.telefono;
    }
}
