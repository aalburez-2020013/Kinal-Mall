
package org.alvaroalburez.bean;

public class Cliente {
    private int codigoCliente;
    private String nombresCliente;
    private String apellidosCliente;
    private String telefonoCliente;
    private String direccionCliente;
    private String email;
    private int codigoLocales;
    private int codigoAdministracion;
    private int codigoTipoCliente;

    public Cliente() {
    }

    public Cliente(int codigoCliente, 
                   String nombresCliente, 
                   String apellidosCliente, 
                   String telefonoCliente, 
                   String direccionCliente, 
                   String email, 
                   int codigoLocales, 
                   int codigoAdministracion, 
                   int codigoTipoCliente) {
        this.codigoCliente = codigoCliente;
        this.nombresCliente = nombresCliente;
        this.apellidosCliente = apellidosCliente;
        this.telefonoCliente = telefonoCliente;
        this.direccionCliente = direccionCliente;
        this.email = email;
        this.codigoLocales = codigoLocales;
        this.codigoAdministracion = codigoAdministracion;
        this.codigoTipoCliente = codigoTipoCliente;
    }

    public int getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(int codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public String getNombresCliente() {
        return nombresCliente;
    }

    public void setNombresCliente(String nombresCliente) {
        this.nombresCliente = nombresCliente;
    }

    public String getApellidosCliente() {
        return apellidosCliente;
    }

    public void setApellidosCliente(String apellidosCliente) {
        this.apellidosCliente = apellidosCliente;
    }

    public String getTelefonoCliente() {
        return telefonoCliente;
    }

    public void setTelefonoCliente(String telefonoCliente) {
        this.telefonoCliente = telefonoCliente;
    }

    public String getDireccionCliente() {
        return direccionCliente;
    }

    public void setDireccionCliente(String direccionCliente) {
        this.direccionCliente = direccionCliente;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCodigoLocales() {
        return codigoLocales;
    }

    public void setCodigoLocales(int codigoLocales) {
        this.codigoLocales = codigoLocales;
    }

    public int getCodigoAdministracion() {
        return codigoAdministracion;
    }

    public void setCodigoAdministracion(int codigoAdministracion) {
        this.codigoAdministracion = codigoAdministracion;
    }

    public int getCodigoTipoCliente() {
        return codigoTipoCliente;
    }

    public void setCodigoTipoCliente(int codigoTipoCliente) {
        this.codigoTipoCliente = codigoTipoCliente;
    }
}
