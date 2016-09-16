package com.fedoraapps.www.appguarda;

import com.fedoraapps.www.appguarda.Shares.DataConfiguracionEmpresa;
import com.fedoraapps.www.appguarda.Shares.DataEmpleado;
import com.fedoraapps.www.appguarda.Shares.DataPuntoRecorridoConverter;
import com.fedoraapps.www.appguarda.Shares.DataTerminal;
import com.fedoraapps.www.appguarda.Shares.DataViajeConvertor;

/**
 * Created by maxi on 06/06/2016.
 */
public class Farcade {

    static DataPuntoRecorridoConverter DestinoSeleccionado = new DataPuntoRecorridoConverter();
    static DataViajeConvertor recorridoSeleccionado = new DataViajeConvertor();
    static DataConfiguracionEmpresa configuracionEmpresa = new DataConfiguracionEmpresa();
    static DataEmpleado empleado = new DataEmpleado();
    static DataTerminal terminalSeleccionada = new DataTerminal();


    public void setTerminalSeleccionada(DataTerminal emp){
        this.terminalSeleccionada = emp;
    }

    public DataTerminal getTerminalSeleccionada(){
        return this.terminalSeleccionada;
    }

    public void setEmpleado(DataEmpleado emp){
        this.empleado = emp;
    }

    public DataEmpleado getEmpleado(){
        return this.empleado;
    }

    public  void SaveConfiguracionEmpresa(DataConfiguracionEmpresa empresa){
        this.configuracionEmpresa = empresa;
    }

    public   DataConfiguracionEmpresa genConfiguracionEmpresa(){
       return this.configuracionEmpresa;
    }

    public  void setRecorridoSeleccionado(DataViajeConvertor reco){
        this.recorridoSeleccionado = reco;
    }

    public  DataViajeConvertor getRecorridoSeleccionado(){
        return recorridoSeleccionado;
    }


    public  DataPuntoRecorridoConverter getDestinoSeleccionado(){
      return this.DestinoSeleccionado;
    }

    public  void setDestinoSeleccionado(DataPuntoRecorridoConverter data){
        this.DestinoSeleccionado = data;
    }




}
