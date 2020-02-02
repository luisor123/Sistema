/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;

import entidades.Venta;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author javie
 */
public class VentaService {
    private Connection conection = null;
    private Statement s = null;
    private ResultSet rs = null;
    
    public VentaService(Connection conection) {
        this.conection = conection;
    }
    
    public boolean ingresarPedido(Venta venta){ // ingresar pedido
        boolean bandera = false;
        try {
            s = conection.createStatement();
            int z = s.executeUpdate("INSERT INTO venta (cliente_id, usuario_id, numpedido, preciototal, fecha) "
                    + "values ("+venta.cliente_id+", "+venta.usuario_id+", "+venta.numpedido
                    +", "+venta.preciototal+",'"+venta.fecha+"')");
            if (z == 1) {
                bandera = true;
                //JOptionPane.showMessageDialog(null, "El pedido N°: "+venta.numpedido+
                //        ", se ha ingresado correctamente.", "Exito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error al ingresar el pedido.", "Error", JOptionPane.ERROR);
            }
        } catch (Exception e) {
            System.out.println("Error de conexion: "+e.getMessage());
            JOptionPane.showMessageDialog(null, "Error: "+e.getMessage(), "Error", JOptionPane.ERROR);
        }
        return bandera;
    }
    
    public boolean actualizarVenta(Venta venta) {
        boolean bandera = false;
        try {
            s = conection.createStatement();
            int z = s.executeUpdate("UPDATE venta SET cliente_id = "+venta.cliente_id
                    +", usuario_id = "+venta.usuario_id+", tipodocumento_id = "+venta.tipodocumento_id
                    +", numdocumento = "+venta.numdocumento+", numpedido = "+venta.numpedido
                    +", preciototal = "+venta.preciototal+", fecha = '"+venta.fecha+
                    "' WHERE id = "+venta.id);
            if (z == 1) {
                bandera = true;
                JOptionPane.showMessageDialog(null, "La venta N°: "+venta.numdocumento+
                        ", se ha ingresado correctamente.", "Exito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error al ingresar venta.", "Error", JOptionPane.ERROR);
            }
        } catch (Exception e) {
            System.out.println("Error de conexion: "+e.getMessage());
            JOptionPane.showMessageDialog(null, "Error: "+e.getMessage(), "Error", JOptionPane.ERROR);
        }
        return bandera;
    }
    
    public boolean eliminarVenta(Venta venta) {
        boolean bandera = false;
        try {
            s = conection.createStatement();
            int z = s.executeUpdate("DELETE FROM venta WHERE id = "+venta.id);
            if (z == 1) {
                bandera = true;
                JOptionPane.showMessageDialog(null, "La venta N°: "+venta.numdocumento+
                        ", se ha eliminado correctamente.", "Exito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error al eliminar venta.", "Error", JOptionPane.ERROR);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: "+e.getMessage(), "Error", JOptionPane.ERROR);
        }
        return bandera;
    }
    
    public int generarCodigoPedido() {
        int codigo = 0;
        try {
            s = conection.createStatement();
            rs = s.executeQuery("SELECT count(*) as cantidad FROM venta");
            while (rs.next()) {
                codigo = rs.getInt("cantidad") +1;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "Error: "+e.getMessage(), "Error", JOptionPane.ERROR);
        }
        
        return codigo;
    }
    
    public Venta mostrarVenta(int numpedido) {
        Venta venta = null;
        try {
            s = conection.createStatement();
            rs = s.executeQuery("SELECT * FROM venta WHERE numpedido = "+numpedido+"");
            while (rs.next()) {
                venta = new Venta();
                venta.setId(rs.getInt("id"));
                venta.setCliente_id(rs.getInt("cliente_id"));
                venta.setUsuario_id(rs.getInt("usuario_id"));
                venta.setTipodocumento_id(rs.getInt("tipodocumento_id"));
                venta.setNumpedido(rs.getInt("numpedido"));
                venta.setNumdocumento(rs.getInt("numdocumento"));
                venta.setPreciototal(rs.getDouble("preciototal"));
                venta.setFecha(rs.getString("fecha"));
            }
        } catch (Exception e) {
            System.out.println("Error: "+e.getMessage());
            JOptionPane.showMessageDialog(null, "Error: "+e.getMessage(), "Error", JOptionPane.ERROR);
        }
        return venta;
    }

}
