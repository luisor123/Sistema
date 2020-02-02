/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

/**
 *
 * @author javie
 */
public class Venta {
    public Integer id = 0;
    public Integer cliente_id;
    public Integer usuario_id;
    public Integer tipodocumento_id;
    public String numdocumento;
    public Integer numpedido;
    public Double preciototal;
    public String fecha;
    public String fechaventa;

    public Venta() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCliente_id() {
        return cliente_id;
    }

    public void setCliente_id(Integer cliente_id) {
        this.cliente_id = cliente_id;
    }

    public Integer getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(Integer usuario_id) {
        this.usuario_id = usuario_id;
    }

    public Integer getTipodocumento_id() {
        return tipodocumento_id;
    }

    public void setTipodocumento_id(Integer tipodocumento_id) {
        this.tipodocumento_id = tipodocumento_id;
    }

    public String getNumdocumento() {
        return numdocumento;
    }

    public void setNumdocumento(String numdocumento) {
        this.numdocumento = numdocumento;
    }

    public Integer getNumpedido() {
        return numpedido;
    }

    public void setNumpedido(Integer numpedido) {
        this.numpedido = numpedido;
    }

    public Double getPreciototal() {
        return preciototal;
    }

    public void setPreciototal(Double preciototal) {
        this.preciototal = preciototal;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getFechaventa() {
        return fechaventa;
    }

    public void setFechaventa(String fechaventa) {
        this.fechaventa = fechaventa;
    }    
    
}
