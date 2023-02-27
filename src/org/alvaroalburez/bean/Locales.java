
package org.alvaroalburez.bean;


public class Locales {
    
    private int CodigoLocales;
    private double SaldoFavor;
    private double SaldoContra;
    private int MesesPendientes;
    private boolean Disponibilidad;
    private double ValorLocal;
    private double ValorAdministracion;
    
    
    
    
    
    
    public Locales(){
        
    }

    public Locales(int CodigoLocales, double SaldoFavor, double SaldoContra, int MesesPendientes, boolean Disponibilidad, double ValorLocal, double ValorAdministracion) {
        this.CodigoLocales = CodigoLocales;
        this.SaldoFavor = SaldoFavor;
        this.SaldoContra = SaldoContra;
        this.MesesPendientes = MesesPendientes;
        this.Disponibilidad = Disponibilidad;
        this.ValorLocal = ValorLocal;
        this.ValorAdministracion = ValorAdministracion;
    }

    public int getCodigoLocales() {
        return CodigoLocales;
    }

    public void setCodigoLocales(int CodigoLocales) {
        this.CodigoLocales = CodigoLocales;
    }

    public double getSaldoFavor() {
        return SaldoFavor;
    }

    public void setSaldoFavor(double SaldoFavor) {
        this.SaldoFavor = SaldoFavor;
    }

    public double getSaldoContra() {
        return SaldoContra;
    }

    public void setSaldoContra(double SaldoContra) {
        this.SaldoContra = SaldoContra;
    }

    public int getMesesPendientes() {
        return MesesPendientes;
    }

    public void setMesesPendientes(int MesesPendientes) {
        this.MesesPendientes = MesesPendientes;
    }

    public boolean getDisponibilidad() {
        return Disponibilidad;
    }

    public void setDisponibilidad(boolean Disponibilidad) {
        this.Disponibilidad = Disponibilidad;
    }

    public double getValorLocal() {
        return ValorLocal;
    }

    public void setValorLocal(double ValorLocal) {
        this.ValorLocal = ValorLocal;
    }

    public double getValorAdministracion() {
        return ValorAdministracion;
    }

    public void setValorAdministracion(double ValorAdministracion) {
        this.ValorAdministracion = ValorAdministracion;
    }
    
    
    
    
    
    
    
}