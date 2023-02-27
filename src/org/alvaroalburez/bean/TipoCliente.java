/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.alvaroalburez.bean;


public class TipoCliente {
    
    private int codigoTipoCliente;
    private String descripcion;
    
    
    
    public TipoCliente(){
        
    }
    
    
    public TipoCliente(int codigoTipoCliente, String descripcion){
        
        this.codigoTipoCliente = codigoTipoCliente;
        this.descripcion = descripcion;
    }

    public int getCodigoTipoCliente() {
        return codigoTipoCliente;
    }

    public void setCodigoTipoCliente(int codigoTipoCliente) {
        this.codigoTipoCliente = codigoTipoCliente;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
