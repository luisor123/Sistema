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
public class VentaDetalle {
    public Integer id = 0;
    public Integer venta_id;
    public Integer plato_id;
    public String nombreplato;//
    public double precioplato;//
    public Integer cantidad;
    public double precio;

    public VentaDetalle() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVenta_id() {
        return venta_id;
    }

    public void setVenta_id(Integer venta_id) {
        this.venta_id = venta_id;
    }

    public Integer getPlato_id() {
        return plato_id;
    }

    public void setPlato_id(Integer plato_id) {
        this.plato_id = plato_id;
    }

    public String getNombreplato() {
        return nombreplato;
    }

    public void setNombreplato(String nombreplato) {
        this.nombreplato = nombreplato;
    }

    public double getPrecioplato() {
        return precioplato;
    }

    public void setPrecioplato(double precioplato) {
        this.precioplato = precioplato;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
    
}
