package com.fedoraapps.www.appguarda.Shares;

import java.util.Date;
import java.util.List;

/**
 * Created by maxi on 13/07/16.
 */
public abstract class DataPersona {
    protected String id;
    protected String nombrePila;
    protected String apellido;
    protected DataEmail email;
    protected String clave;
    protected List<DataTelefono> telefonosContacto;
    protected Date fechaNacimiento;
    protected boolean eliminado;


    public void setId(String val){
        this.id = val;
    }

    public void setClave(String c){
        this.clave = c;
    }

    public String getClave(){
        return this.clave;
    }

    public String getId(){
        return this.id;
    }

    public void setNombrePila(String val){
        this.nombrePila = val;
    }

    public String getNombrePila(){
        return this.nombrePila;
    }

    public void setApellido(String val){
        this.apellido = val;
    }

    public String getApellido(){
        return this.apellido;
    }

    public void setEmail(DataEmail val){
        this.email = val;
    }

    public DataEmail getEmail(){
        return this.email;
    }

    public void setTelefonosContacto(List<DataTelefono> val){
        this.telefonosContacto = val;
    }

    public List<DataTelefono> getTelefonosContacto(){
        return this.telefonosContacto;
    }

    public void setFechaNacimiento(Date val){
        this.fechaNacimiento = val;
    }

    public Date getFechaNacimiento(){
        return this.fechaNacimiento;
    }


    public void setEliminado(Boolean val){
        this.eliminado = val;
    }

    public Boolean getEliminado(){
        return this.eliminado;
    }
}
