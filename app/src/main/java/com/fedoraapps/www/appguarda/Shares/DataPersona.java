package com.fedoraapps.www.appguarda.Shares;

import java.util.Date;
import java.util.List;

/**
 * Created by maxi on 13/07/16.
 */
public class DataPersona {
    private String id;
    private String nombrePila;
    private String apellido;
    private DataEmail email;
    private List<DataTelefono> telefonosContacto;
    private Date fechaNacimiento;
    private boolean eliminado;

    public void setId(String val){
        this.id = val;
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
