package com.fedoraapps.www.appguarda.Shares;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by maxi on 10/07/16.
 */
public class DataPasajeConvertor {

    private String id;
    private DataViajeConvertor viaje;
    private Float precio;
    private DataPuntoRecorridoConverter origen;
    private DataPuntoRecorridoConverter destino;
    private String fechaCompra;
    private DataUsuario comprador;
    private String ciPersona;
    private DataEmpleado vendedor;
    private int codigoPasaje;
    private Boolean usado;
    private Boolean pago;
    private Boolean eliminado;



    public DataPasajeConvertor() {}

    //PARA TRAER LOS PASAJES

    public DataPasajeConvertor(String id, int codigoPasaje, DataViajeConvertor via, Float prec, DataPuntoRecorridoConverter orig,
                               DataPuntoRecorridoConverter des, Date fechCompra, DataUsuario comp, String ciPer,
                               DataEmpleado vend, Boolean usd, Boolean pg, Boolean elim) throws ParseException {
        this.id = id;
        this.codigoPasaje = codigoPasaje;
        this.viaje = via;
        this.precio = prec;
        this.origen = orig;
        this.destino = des;
        this.fechaCompra = this.getFechaCompra(fechCompra);
        this.comprador = comp;
        this.ciPersona = ciPer;
        this.vendedor = vend;
        this.usado = usd;
        this.pago = pg;
        this.eliminado = elim;
    }

    //PARA COMPRAR PASAJE

    public DataPasajeConvertor(DataViajeConvertor via, Float prec, DataPuntoRecorridoConverter orig,
                               DataPuntoRecorridoConverter des, String fechCompra, DataUsuario comp, String ciPer,
                               DataEmpleado vend, Boolean usd, Boolean pg, Boolean elim) {
        this.viaje = via;
        this.precio = prec;
        this.origen = orig;
        this.destino = des;
        this.fechaCompra = fechCompra;
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

    public void setViaje(DataViajeConvertor val){
        this.viaje = val;
    }

    public DataViajeConvertor getViaje(){
        return this.viaje;
    }

    public void setPrecio(Float val){
        this.precio = val;
    }

    public Float getPrecio(){
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

    public void setFechaCompra(String val){
        this.fechaCompra = val;
    }

    public Date genFechaCompra() throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.parse(this.fechaCompra);
    }

    public String getFechaCompra(Date fecha) throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(fecha);
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
