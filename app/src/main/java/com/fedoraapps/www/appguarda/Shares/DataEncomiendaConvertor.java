package com.fedoraapps.www.appguarda.Shares;

import java.util.Date;
import java.util.List;

/**
 * Created by maxi on 10/07/16.
 */
public class DataEncomiendaConvertor {


    private String id;
    private DataPuntoRecorridoConverter origen;
    private DataPuntoRecorridoConverter destino;
    private DataUsuario emisor;
    private String ciEmisor;
    private DataTelefono telEmisor;
    private DataUsuario receptor;
    private String ciReceptor;
    private DataTelefono telReceptor;
    private String direccionReceptor;
    private DataReglaCobroEncomienda reglaCobro;
    private float monto;
    private boolean pagaReceptor;
    private DataViajeConvertor viajeAsignado;
    private List<DataHistorialEstadosEncomienda> estados;
    private DataEstadosEncomienda estadoActual;

    private Date fechaIngreso;

    private Date fechaEntrega;
    private boolean retiraEnSucursal;
    private boolean eliminada;



    public DataEncomiendaConvertor() {}

    public DataEncomiendaConvertor(String id, DataPuntoRecorridoConverter orig, DataPuntoRecorridoConverter dest, DataUsuario emi, String ciEm, DataTelefono telEm, DataUsuario rec, String ciRec, DataTelefono telRec, String dirRec, DataReglaCobroEncomienda regCob, float mont, boolean pagaRec, DataViajeConvertor viajeAs, List<DataHistorialEstadosEncomienda> estds, DataEstadosEncomienda estAc, Date fecIng, Date fecEn, boolean retiraSuc, boolean elim) {
        this.id = id;
        this.origen = orig;
        this.destino = dest;
        this.emisor = emi;
        this.ciEmisor = ciEm;
        this.telEmisor = telEm;
        this.receptor = rec;
        this.ciReceptor = ciRec;
        this.telReceptor = telEm;
        this.direccionReceptor = dirRec;
        this.reglaCobro = regCob;
        this.monto = mont;
        this.pagaReceptor = pagaRec;
        this.viajeAsignado = viajeAs;
        this.estados = estds;
        this.estadoActual = estAc;
        this.fechaIngreso = fecIng;
        this.fechaEntrega = fecEn;
        this.retiraEnSucursal = retiraSuc;
        this.eliminada = elim;
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

    public void setEmisor(DataUsuario val){
        this.emisor = val;
    }

    public DataUsuario getEmisor(){
        return this.emisor;
    }

    public void setCiEmisor(String val){
        this.ciEmisor = val;
    }

    public String getCiEmisor(){
        return this.ciEmisor;
    }

    public void setTelEmisor(DataTelefono val){
        this.telEmisor = val;
    }

    public DataTelefono getTelEmisor(){
        return this.telEmisor;
    }

    public void setReceptor(DataUsuario val){
        this.receptor = val;
    }

    public DataUsuario getReceptor(){
        return this.receptor;
    }

    public void setCiReceptor(String val){
        this.ciReceptor = val;
    }

    public String getCiReceptor(){
        return this.ciReceptor;
    }

    public void setTelReceptor(DataTelefono val){
        this.telReceptor = val;
    }

    public DataTelefono getTelReceptor(){
        return this.telReceptor;
    }

    public void setDireccionReceptor(String val){
        this.direccionReceptor = val;
    }

    public String getDireccionReceptor(){
        return this.direccionReceptor;
    }

    public void setReglaCobro(DataReglaCobroEncomienda val){
        this.reglaCobro = val;
    }

    public DataReglaCobroEncomienda getReglaCobro(){
        return this.reglaCobro;
    }

    public void setMonto(float val){
        this.monto = val;
    }

    public float getMonto(){
        return this.monto;
    }

    public void setPagaReceptor(boolean val){
        this.pagaReceptor = val;
    }

    public boolean getPagaReceptor(){
        return this.pagaReceptor;
    }

    public void setViajeAsignado(DataViajeConvertor val){
        this.viajeAsignado = val;
    }

    public DataViajeConvertor getViajeAsignado(){
        return this.viajeAsignado;
    }

    public void setEstados(List<DataHistorialEstadosEncomienda> val){
        this.estados = val;
    }

    public List<DataHistorialEstadosEncomienda> getEstados(){
        return this.estados;
    }

    public void setEstadoActual(DataEstadosEncomienda val){
        this.estadoActual = val;
    }

    public DataEstadosEncomienda getEstadoActual(){
        return this.estadoActual;
    }

    public void setFechaIngreso(Date val){
        this.fechaIngreso = val;
    }

    public Date getFechaIngreso(){
        return this.fechaIngreso;
    }

    public void setFechaEntrega(Date val){
        this.fechaEntrega = val;
    }

    public Date getFechaEntrega(){
        return this.fechaEntrega;
    }

    public void setRetiraEnSucursal(boolean val){
        this.retiraEnSucursal = val;
    }

    public boolean getRetiraEnSucursal(){
        return this.retiraEnSucursal;
    }

    public void setEliminada(boolean val){
        this.eliminada = val;
    }

    public boolean getEliminada(){
        return this.eliminada;
    }
}
