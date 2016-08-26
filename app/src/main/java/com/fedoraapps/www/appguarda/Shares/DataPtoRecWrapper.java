package com.fedoraapps.www.appguarda.Shares;

/**
 * Created by maxi on 15/08/16.
 */
public class DataPtoRecWrapper {
    private DataPuntoRecorridoConverter punto;
    private Boolean checked;

    public DataPtoRecWrapper(DataPuntoRecorridoConverter pto){
        this.punto = pto;
        this.checked = false;
    }

    public String toString(){
        return this.punto.getNombre();
    }

    public void setPunto(DataPuntoRecorridoConverter pto){
        this.punto = pto;
    }

    public DataPuntoRecorridoConverter getPunto(){
        return this.punto;
    }

    public void setChecked(Boolean ck){
        this.checked = ck;
    }

    public Boolean getChecked(){
        return this.checked;
    }
}
