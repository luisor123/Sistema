/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;

import entidades.Venta;
import entidades.VentaDetalle;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author javie
 */
public class VentaDetalleService {
    private Connection conection = null;
    private Statement s = null;
    private ResultSet rs = null;
    
    public VentaDetalleService(Connection conection) {
        this.conection = conection;
    }
    
    public boolean ingresarVentaDetalle(VentaDetalle detalle){
        boolean bandera = false;
        try {
            s = conection.createStatement();
            int z = s.executeUpdate("INSERT INTO ventadetalle (venta_id, plato_id, cantidad, precio) "
                    + "values ("+detalle.venta_id+", "+detalle.plato_id+","+detalle.cantidad
                    +","+detalle.precio+")");
            if (z == 1) {
                bandera = true;
                JOptionPane.showMessageDialog(null, "El pedido se ha ingresado correctamente.", "Exito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error al ingresar venta detalle.", "Error", 
                        JOptionPane.ERROR);
            }
        } catch (Exception e) {
            System.out.println("Error ingresar ventadetalle: "+e.getMessage());
            JOptionPane.showMessageDialog(null, "Error: "+e.getMessage(), "Error", JOptionPane.ERROR);
        }
        return bandera;
    }
    
    public boolean actualizarVentaDetalle(VentaDetalle detalle) {
        boolean bandera = false;
        try {
            s = conection.createStatement();
            int z = s.executeUpdate("UPDATE ventadetalle SET venta_id = "+detalle.venta_id
                    +", plato_id = "+detalle.plato_id+", cantidad = "+detalle.cantidad
                    +", precio = "+detalle.precio+" WHERE id = "+detalle.id);
            if (z == 1) {
                bandera = true;
            } else {
                JOptionPane.showMessageDialog(null, "Error al actualizar venta detalle.", "Error", 
                        JOptionPane.ERROR);
            }
        } catch (Exception e) {
            System.out.println("Error de conexion: "+e.getMessage());
            JOptionPane.showMessageDialog(null, "Error: "+e.getMessage(), "Error", JOptionPane.ERROR);
        }
        return bandera;
    }
    
    public boolean eliminarVentaDetalle(VentaDetalle detalle) {
        boolean bandera = false;
        try {
            s = conection.createStatement();
            int z = s.executeUpdate("DELETE FROM ventadetalle WHERE id = "+detalle.id);
            if (z == 1) {
                bandera = true;
                JOptionPane.showMessageDialog(null, "La venta detalle, se ha eliminado correctamente.", 
                        "Exito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error al eliminar venta detalle.", "Error", JOptionPane.ERROR);
            }
        } catch (Exception e) {
            System.out.println("Error de conexion: "+e.getMessage());
            JOptionPane.showMessageDialog(null, "Error: "+e.getMessage(), "Error", JOptionPane.ERROR);
        }
        return bandera;
    }
    
    public ArrayList<VentaDetalle> listarVentaDetalles(Venta venta) {
        ArrayList<VentaDetalle> lista = new ArrayList<VentaDetalle>();
        try {
            s = conection.createStatement();
            // inner join con el plato
            rs = s.executeQuery("SELECT ventadetalle.id, venta_id, plato_id, cantidad, ventadetalle.precio, "
                    + "plato.nombre as nombreplato, plato.precio as precioplato FROM ventadetalle "
                    + "inner join plato on ventadetalle.plato_id = plato.id WHERE ventadetalle.venta_id = "+venta.id);
            VentaDetalle cliente = new VentaDetalle();
            while (rs.next()) {
                cliente.setId(rs.getInt("id"));
                cliente.setVenta_id(rs.getInt("venta_id"));
                cliente.setPlato_id(rs.getInt("plato_id"));
                cliente.setCantidad(rs.getInt("cantidad"));
                cliente.setPrecio(rs.getDouble("precio")); // precioplato * cantidad
                cliente.setNombreplato(rs.getString("nombreplato"));//
                cliente.setPrecioplato(rs.getDouble("precioplato"));//
                lista.add(cliente);
                cliente = new VentaDetalle();
            }
        } catch (Exception e) {
            System.out.println("Error: "+e.getMessage());
            JOptionPane.showMessageDialog(null, "Error: "+e.getMessage(), "Error", JOptionPane.ERROR);
        }
        return lista;
    }
}
