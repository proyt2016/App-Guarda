package com.fedoraapps.www.appguarda.Model;

/**
 * Created by andres on 14/5/16.
 */
public class Usuario {

    private Integer id;
    private String usuario;
    private String nombre;
    private String clave;

    /**
     * @return The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return The usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * @param usuario The usuario
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * @return The nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre The nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return The clave
     */
    public String getClave() {
        return clave;
    }

    /**
     * @param clave The clave
     */
    public void setClave(String clave) {
        this.clave = clave;
    }

}