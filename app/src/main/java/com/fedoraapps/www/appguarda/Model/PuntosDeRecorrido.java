package com.fedoraapps.www.appguarda.Model;

/**
 * Created by maxi on 06/06/2016.
 */
public class PuntosDeRecorrido {

    private int id;
    private String nombre;
    private int cantAsientos;

    public PuntosDeRecorrido(int id, String nombre,int cantAsientos){
        this.id = id;
        this.nombre = nombre;
        this.cantAsientos = cantAsientos;
    }

    public String toString(){
        return this.nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantAsientos() {
        return cantAsientos;
    }

    public void setCantAsientos(int cantAsientos) {
        this.cantAsientos = cantAsientos;
    }
}
