/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;

import entidades.TipoDocumento;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author javie
 */
public class TipoDocumentoService {
    private Connection conection = null;
    private Statement s = null;
    private ResultSet rs = null;
    
    public TipoDocumentoService(Connection conection) {
        this.conection = conection;
    }
    
    public ArrayList<TipoDocumento> listarTiposdocumento() {
        ArrayList<TipoDocumento> lista = new ArrayList<TipoDocumento>();
        try {
            s = conection.createStatement();
            rs = s.executeQuery("SELECT * FROM tipodocumento");
            TipoDocumento cliente = new TipoDocumento();
            while (rs.next()) {
                cliente.setId(rs.getInt("id"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setDescripcion(rs.getString("descripcion"));
                lista.add(cliente);
                cliente = new TipoDocumento();
            }
        } catch (Exception e) {
            System.out.println("Error de conexion: "+e.getMessage());
            JOptionPane.showMessageDialog(null, "Error: "+e.getMessage(), "Error", JOptionPane.ERROR);
        }
        return lista;
    }
}
