package com.fedoraapps.www.appguarda.Shares;

import com.fedoraapps.www.appguarda.Model.PuntosDeRecorrido;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maxi on 11/07/16.
 */
public class DataRecorridoConvertor {
    private String id;

    private String nombre;
    private List<DataPuntoRecorridoConverter> puntosDeRecorrido;
    private List<DataGrupoHorario> horarios;
    private String idOrigen;
    private String idDestino;
    private List<DataPrecio> precios;
    private Boolean eliminado;



    public DataRecorridoConvertor() {}

    public DataRecorridoConvertor(String id, String nom, List<DataPuntoRecorridoConverter> punRec, List<DataGrupoHorario> hor, List<DataPrecio> prec, Boolean elim) {
        this.id = id;
        this.nombre = nom;
        this.puntosDeRecorrido = punRec;
        this.horarios = hor;
        this.precios = prec;
        this.eliminado = elim;
    }



    public void setId(String val){
        this.id = val;
    }

    public String getId(){
        return this.id;
    }

    public void setNombre(String val){
        this.nombre = val;
    }

    public String getNombre(){
        return this.nombre;
    }

    public void setPuntosDeRecorrido(List<DataPuntoRecorridoConverter> val){
        this.puntosDeRecorrido = val;
    }

    public List<DataPuntoRecorridoConverter> getPuntosDeRecorrido(){
        return this.puntosDeRecorrido;
    }

    public void setHorarios(List<DataGrupoHorario> val){
        this.horarios = val;
    }

    public List<DataGrupoHorario> getHorarios(){
        return this.horarios;
    }

    public void setPrecios(List<DataPrecio> val){
        this.precios = val;
    }

    public List<DataPrecio> getPrecios(){
        return this.precios;
    }

    public void setEliminado(Boolean val){
        this.eliminado = val;
    }

    public Boolean getEliminado(){
        return this.eliminado;
    }

    public String getIdOrigen(){
        return idOrigen;
    }

    public void setIdOrigen(String val){
        this.idOrigen = val;
    }

    public String getIdDestino(){
        return idDestino;
    }

    public void setIdDestino(String val){
        this.idDestino = val;
    }
}
