package com.fedoraapps.www.appguarda.Shares;

/**
 * Created by maxi on 14/08/16.
 */
public class DataParada extends DataPuntoRecorridoConverter{

    public DataParada() {}

    public DataParada(String id, String nom, String uMap, Boolean elim) {
        setId(id);
        setNombre(nom);
        setUbicacionMapa(uMap);
        setEliminado(elim);
    }



    public String getTipo(){
        return "Parada";
    }

    public void setTipo(String val){}
}
