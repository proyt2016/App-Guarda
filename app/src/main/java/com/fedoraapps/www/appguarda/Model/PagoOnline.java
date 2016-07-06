package com.fedoraapps.www.appguarda.Model;

/**
 * Created by maxi on 07/06/2016.
 */
public class PagoOnline {

    private int idPago;
    private int idViaje;
    private int idPrecio;
    private boolean pago;

    public PagoOnline(int idPago,int idViaje,int idPrecio, boolean pago){
        this.idPago = idPago;
        this.idViaje = idViaje;
        this.idPrecio = idPrecio;
        this.pago = pago;
    }

    public int getIdPago() {
        return idPago;
    }

    public void setIdPago(int idPago) {
        this.idPago = idPago;
    }

    public int getIdViaje() {
        return idViaje;
    }

    public void setIdViaje(int idViaje) {
        this.idViaje = idViaje;
    }

    public int getIdPrecio() {
        return idPrecio;
    }

    public void setIdPrecio(int idPrecio) {
        this.idPrecio = idPrecio;
    }

    public boolean isPago() {
        return pago;
    }

    public void setPago(boolean pago) {
        this.pago = pago;
    }
}
