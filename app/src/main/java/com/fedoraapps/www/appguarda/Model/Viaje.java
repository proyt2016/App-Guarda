package com.fedoraapps.www.appguarda.Model;

/**
 * Created by maxi on 04/06/2016.
 */
public class Viaje {

    private int id;
    private String nombre;
    private int nroCoche;
    private String horaSalida;

    public Viaje(int id, String nombre,int nroCoche,String horaSalida){
        this.id = id;
        this.nombre = nombre;
        this.nroCoche = nroCoche;
        this.horaSalida = horaSalida;
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

    public int getNroCoche() {
        return nroCoche;
    }

    public void setNroCoche(int nroCoche) {
        this.nroCoche = nroCoche;
    }

    public String getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(String horaSalida) {
        this.horaSalida = horaSalida;
    }
}
