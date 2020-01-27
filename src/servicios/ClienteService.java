/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;
import entidades.Cliente;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
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
    
    public void ingresarCliente(Cliente cliente){
        try {
            s = conection.createStatement();
            int z = s.executeUpdate("INSERT INTO cliente (nombres, dni, direccion, ruc, telefono) "
                    + "values ('"+cliente.nombres+"', '"+cliente.dni+"','"+cliente.direccion+
                    "','"+cliente.telefono+"')");
            if (z == 1) {
                JOptionPane.showMessageDialog(null, "El cliente: "+cliente.nombres+
                        ", se ha ingresado correctamente.", "Exito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error al ingresar el cliente.", "Error", JOptionPane.ERROR);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error de conexión: "+e.getMessage(), "Error", JOptionPane.ERROR);
        }
    }
    
    public void buscarCliente(String nombre) {
        try {
            s = conection.createStatement();
            rs = s.executeQuery("SELECT name FROM cliente WHERE nombre = '"+nombre+"'");
        } catch (Exception e) {
            
        }
    }
    
}
