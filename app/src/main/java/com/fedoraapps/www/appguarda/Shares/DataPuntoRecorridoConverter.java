package com.fedoraapps.www.appguarda.Shares;

import java.util.List;

/**
 * Created by maxi on 06/07/16.
 */
public class DataPuntoRecorridoConverter {
    private String id;
    private String nombre;
    private String ubicacionMapa;
    private Boolean eliminado;
    private List<DataTelefono> telefonosContacto;
    private List<DataEmail> mailsDeContacto;
    private Boolean aceptaEncomiendas;
    private String tipo;

    public DataPuntoRecorridoConverter(){}

    public DataPuntoRecorridoConverter(String id, String nom, String uMap, List<DataTelefono> tels, List<DataEmail> mails, Boolean acEnc, Boolean elim,String tip) {
        setId(id);
        setNombre(nom);
        setUbicacionMapa(uMap);
        setEliminado(elim);
        setTelefonosContacto(tels);
        setMailsDeContacto(mails);
        setAceptaEncomiendas(acEnc);
        setTipo(tip);
    }

    public String toString(){
        return this.nombre;
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

    public void setUbicacionMapa(String val){
        this.ubicacionMapa = val;
    }

    public String getUbicacionMapa(){
        return this.ubicacionMapa;
    }

    public void setEliminado(Boolean val){
        this.eliminado = val;
    }

    public Boolean getEliminado(){
        return this.eliminado;
    }

    public void setTelefonosContacto(List<DataTelefono> val){
        this.telefonosContacto = val;
    }

    public List<DataTelefono> getTelefonosContacto(){
        return this.telefonosContacto;
    }

    public void setMailsDeContacto(List<DataEmail> val){
        this.mailsDeContacto = val;
    }

    public List<DataEmail> getMailsDeContacto(){
        return this.mailsDeContacto;
    }

    public void setAceptaEncomiendas(Boolean val){
        this.aceptaEncomiendas = val;
    }

    public Boolean getAceptaEncomiendas(){
        return this.aceptaEncomiendas;
    }

    public void setTipo(String val){
        this.tipo = val;
    }

    public String getTipo(){
        return this.tipo;
    }
}
