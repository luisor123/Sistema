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
    
    public void iniciarConexion() {
        if (conection != null) {
            return;
        }
        
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String password = "1234";
        try {
            Class.forName("org.postgresql.Driver");
            conection = DriverManager.getConnection(url, "postgres", password);
            if (conection != null) {
                System.out.println("Conectando a Base de datos");
            }
        } catch (Exception e) {
            System.out.println("Problemas de Conexion");
        }
    }
}
