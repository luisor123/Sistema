/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modulos.sistema.menuprincipal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author javie
 */
public class Conexion {
    
    private Connection conection = null;
    private ResultSet rs = null;
    private Statement s = null;
    
    public Conexion() {
        
    }
    
    public Connection iniciarConexion() {
        if (conection != null) {
            return null;
        }
        String url="jdbc:postgresql://localhost:5432/postgres";
        String contraseña ="123";
        try {
            Class.forName("org.postgresql.Driver");
            conection = DriverManager.getConnection(url, "postgres",contraseña);
            if (conection !=null) {
                System.out.println("Conexion exitosa");
            }
        } catch (Exception e) {
            System.out.println("Error de conexion: "+e.getMessage());
        }
        return conection;
    }
}
