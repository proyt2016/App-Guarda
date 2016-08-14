package com.fedoraapps.www.appguarda;

import com.fedoraapps.www.appguarda.Shares.DataPuntoRecorridoConverter;
import com.fedoraapps.www.appguarda.Shares.DataViajeConvertor;

/**
 * Created by maxi on 06/06/2016.
 */
public class Farcade {

    static public DataPuntoRecorridoConverter DestinoSeleccionado = new DataPuntoRecorridoConverter();
    static DataViajeConvertor recorridoSeleccionado = new DataViajeConvertor();


    public void setRecorridoSeleccionado(DataViajeConvertor reco){
        this.recorridoSeleccionado = reco;
    }

    public DataViajeConvertor getRecorridoSeleccionado(){
        return this.recorridoSeleccionado;
    }


    public DataPuntoRecorridoConverter getDestinoSeleccionado(){
      return this.DestinoSeleccionado;
    }

    public  void setDestinoSeleccionado(DataPuntoRecorridoConverter data){
        this.DestinoSeleccionado = data;
    }




}
