package com.fedoraapps.www.appguarda.Shares;

/**
 * Created by maxi on 06/07/16.
 */
public class DataPrecio {
    private String id;
    private DataPuntoRecorridoConverter origen;
    private DataPuntoRecorridoConverter destino;
    private float monto;

    public DataPrecio() {

    }

    public DataPrecio(DataPuntoRecorridoConverter orig, DataPuntoRecorridoConverter dest, float monto) {
        this.origen = orig;
        this.destino = dest;
        this.monto = monto;
    }

    public void setId(String val){
        this.id = val;
    }

    public String getId(){
        return this.id;
    }

    public void setOrigen(DataPuntoRecorridoConverter val){
        this.origen = val;
    }

    public DataPuntoRecorridoConverter getOrigen(){
        return this.origen;
    }

    public void setDestino(DataPuntoRecorridoConverter val){
        this.destino = val;
    }

    public DataPuntoRecorridoConverter getDestino(){
        return this.destino;
    }

    public void setMonto(float val){
        this.monto = val;
    }

    public float getMonto(){
        return this.monto;
    }


}
