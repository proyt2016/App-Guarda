package com.fedoraapps.www.appguarda.Shares;

import java.util.Date;

/**
 * Created by maxi on 10/07/16.
 */
public class DataPasaje {

    private String id;
    private DataViaje viaje;
    private DataPrecio precio;
    private DataPuntoRecorridoConverter origen;
    private DataPuntoRecorridoConverter destino;
    private Date fechaCompra;
    private DataUsuario comprador;
    private String ciPersona;
    private DataEmpleado vendedor;
    private int codigoPasaje;
    private Boolean usado;
    private Boolean pago;
    private Boolean eliminado;


    public DataPasaje(String id, DataViaje via, DataPrecio prec, DataPuntoRecorridoConverter orig, DataPuntoRecorridoConverter des, Date fecVen,int codigoPasaje , DataUsuario comp,
                      String ciPer, DataEmpleado vend, Boolean usd, Boolean pg, Boolean elim) {
        this.id = id;
        this.codigoPasaje = codigoPasaje;
        this.viaje = via;
        this.precio = prec;
        this.origen = orig;
        this.destino = des;
        this.fechaCompra = fecVen;
        this.comprador = comp;
        this.ciPersona = ciPer;
        this.vendedor = vend;
        this.usado = usd;
        this.pago = pg;
        this.eliminado = elim;
    }




    public DataPasaje() {}

    //PARA VENDER LOS PASAJES

    public DataPasaje(DataViaje via, DataPrecio prec, DataPuntoRecorridoConverter orig,
                      DataPuntoRecorridoConverter des, Date fecVen, DataUsuario comp, String ciPer,
                      DataEmpleado vend, Boolean usd, Boolean pg, Boolean elim) {

        this.viaje = via;
        this.precio = prec;
        this.origen = orig;
        this.destino = des;
        this.fechaCompra = fecVen;
        this.comprador = comp;
        this.ciPersona = ciPer;
        this.vendedor = vend;
        this.usado = usd;
        this.pago = pg;
        this.eliminado = elim;
    }

    public void setCodigoPasaje(int cod){
        this.codigoPasaje = cod;
    }

    public int getCodigoPasaje(){
        return this.codigoPasaje;
    }

    public void setId(String val){
        this.id = val;
    }

    public String getId(){
        return this.id;
    }

    public void setViaje(DataViaje val){
        this.viaje = val;
    }

    public DataViaje getViaje(){
        return this.viaje;
    }

    public void setPrecio(DataPrecio val){
        this.precio = val;
    }

    public DataPrecio getPrecio(){
        return this.precio;
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

    public void setFechaCompra(Date val){
        this.fechaCompra = val;
    }

    public Date getFechaCompra(){
        return this.fechaCompra;
    }

    public void setComprador(DataUsuario val){
        this.comprador = val;
    }

    public DataUsuario getComprador(){
        return this.comprador;
    }

    public void setCiPersona(String val){
        this.ciPersona = val;
    }

    public String getCiPersona(){
        return this.ciPersona;
    }

    public void setVendedor(DataEmpleado val){
        this.vendedor = val;
    }

    public DataEmpleado getVendedor(){
        return this.vendedor;
    }

    public void setUsado(Boolean val){
        this.usado = val;
    }

    public Boolean getUsado(){
        return this.usado;
    }

    public void setPago(Boolean val){
        this.pago = val;
    }

    public Boolean getPago(){
        return this.pago;
    }

    public void setEliminado(Boolean val){
        this.eliminado = val;
    }

    public Boolean getEliminado(){
        return this.eliminado;
    }
}
