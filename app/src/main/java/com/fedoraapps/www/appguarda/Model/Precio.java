package com.fedoraapps.www.appguarda.Model;

/**
 * Created by maxi on 07/06/2016.
 */
public class Precio {

    private int id;
    private int idViaje;
    private int monto;

    public Precio(int id, int idViaje, int monto) {
        this.id = id;
        this.idViaje = idViaje;
        this.monto = monto;
    }

   /* public String toString() {
        return this.id + " " + this.idViaje + " " + this.monto;
    }*/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdViaje() {
        return idViaje;
    }

    public void setIdViaje(int idViaje) {
        this.idViaje = idViaje;
    }

    public int getMonto() {
        return monto;
    }

    public void setMonto(int monto) {
        this.monto = monto;
    }
}
