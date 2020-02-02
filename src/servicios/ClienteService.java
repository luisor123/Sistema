/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;
import entidades.Cliente;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;
/**
 *
 * @author javie
 */
public class ClienteService {
    
    private Connection conection = null;
    private Statement s = null;
    private ResultSet rs = null;
    
    public ClienteService(Connection conection) {
        this.conection = conection;
    }
    
    public boolean ingresarCliente(Cliente cliente){
        boolean bandera = false;
        try {
            s = conection.createStatement();
            int z = s.executeUpdate("INSERT INTO cliente (nombres, dni, direccion, ruc, telefono) "
                    + "values ('"+cliente.nombres+"', '"+cliente.dni+"','"+cliente.direccion
                    +"','"+cliente.ruc+"','"+cliente.telefono+"')");
            if (z == 1) {
                bandera = true;
                JOptionPane.showMessageDialog(null, "El cliente: "+cliente.nombres+
                        ", se ha ingresado correctamente.", "Exito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error al ingresar el cliente.", "Error", JOptionPane.ERROR);
            }
        } catch (Exception e) {
            System.out.println("Error de conexion: "+e.getMessage());
            JOptionPane.showMessageDialog(null, "Error: "+e.getMessage(), "Error", JOptionPane.ERROR);
        }
        return bandera;
    }
    
    public boolean actualizarCliente(Cliente cliente) {
        boolean bandera = false;
        try {
            s = conection.createStatement();
            int z = s.executeUpdate("UPDATE cliente SET nombres = '"+cliente.nombres
                    +"', dni = '"+cliente.dni+"',direccion = '"+cliente.direccion
                    +"', ruc = '"+cliente.ruc+"', telefono = '"+cliente.telefono+
                    "' WHERE id = "+cliente.id);
            if (z == 1) {
                bandera = true;
                JOptionPane.showMessageDialog(null, "El cliente: "+cliente.nombres+
                        ", se ha actualizado correctamente.", "Exito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error al actualizar cliente.", "Error", JOptionPane.ERROR);
            }
        } catch (Exception e) {
            System.out.println("Error de conexion: "+e.getMessage());
            JOptionPane.showMessageDialog(null, "Error: "+e.getMessage(), "Error", JOptionPane.ERROR);
        }
        return bandera;
    }
    
    public boolean eliminarCliente(Cliente cliente) {
        boolean bandera = false;
        try {
            s = conection.createStatement();
            int z = s.executeUpdate("DELETE FROM cliente WHERE id = "+cliente.id);
            if (z == 1) {
                bandera = true;
                JOptionPane.showMessageDialog(null, "El cliente: "+cliente.nombres+
                        ", se ha eliminado correctamente.", "Exito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error al eliminar el cliente.", "Error", JOptionPane.ERROR);
            }
        } catch (Exception e) {
            System.out.println("Error de conexion: "+e.getMessage());
            JOptionPane.showMessageDialog(null, "Error: "+e.getMessage(), "Error", JOptionPane.ERROR);
        }
        return bandera;
    }
    
    public ArrayList<Cliente> listarClientes() {
        ArrayList<Cliente> lista = new ArrayList<Cliente>();
        try {
            s = conection.createStatement();
            rs = s.executeQuery("SELECT * FROM cliente");
            Cliente cliente = new Cliente();
            while (rs.next()) {
                cliente.setId(rs.getInt("id"));
                cliente.setNombres(rs.getString("nombres"));
                cliente.setDni(rs.getString("dni"));
                cliente.setDireccion(rs.getString("direccion"));
                cliente.setRuc(rs.getString("ruc"));
                cliente.setTelefono(rs.getString("telefono"));
                lista.add(cliente);
                cliente = new Cliente();
            }
        } catch (Exception e) {
            System.out.println("Error de conexion: "+e.getMessage());
            JOptionPane.showMessageDialog(null, "Error: "+e.getMessage(), "Error", JOptionPane.ERROR);
        }
        return lista;
    }
    
    public Cliente buscarCliente(int id) {
        Cliente cliente = null;
        try {
            s = conection.createStatement();
            rs = s.executeQuery("SELECT * FROM cliente WHERE id = "+id);
            while (rs.next()) {
                cliente = new Cliente();
                cliente.setId(rs.getInt("id"));
                cliente.setNombres(rs.getString("nombres"));
                cliente.setDni(rs.getString("dni"));
                cliente.setDireccion(rs.getString("direccion"));
                cliente.setRuc(rs.getString("ruc"));
                cliente.setTelefono(rs.getString("telefono"));
            }
        } catch (Exception e) {
            System.out.println("error: "+e.getMessage());
            JOptionPane.showMessageDialog(null, "Error: "+e.getMessage(), "Error", JOptionPane.ERROR);
        }
        return cliente;
    }
    public ArrayList<Cliente> buscarClientes(String nombres) {
        ArrayList<Cliente> lista = new ArrayList<Cliente>();
        try {
            s = conection.createStatement();
            rs = s.executeQuery("SELECT name FROM cliente WHERE nombres = '"+nombres+"'");
            Cliente cliente = new Cliente();
            while (rs.next()) {
                cliente.setId(rs.getInt(1));
                cliente.setNombres(rs.getString(2));
                cliente.setDni(rs.getString(3));
                cliente.setDireccion(rs.getString(4));
                cliente.setRuc(rs.getString(5));
                cliente.setTelefono(rs.getString(6));
                lista.add(cliente);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: "+e.getMessage(), "Error", JOptionPane.ERROR);
        }
        return lista;
    }
    
}
