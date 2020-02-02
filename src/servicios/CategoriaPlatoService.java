/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;

import entidades.CategoriaPlato;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author javie
 */
public class CategoriaPlatoService {
    private Connection conection = null;
    private Statement s = null;
    private ResultSet rs = null;
    
    public CategoriaPlatoService(Connection conection) {
        this.conection = conection;
    }
    
    public boolean ingresarCategoriaPlato(CategoriaPlato categoria){
        boolean bandera = false;
        try {
            s = conection.createStatement();
            int z = s.executeUpdate("INSERT INTO categoriaplato (nombre, descripcion) "
                    + "values ('"+categoria.nombre+"', '"+categoria.descripcion+"')");
            if (z == 1) {
                bandera = true;
                JOptionPane.showMessageDialog(null, "La categoria: "+categoria.nombre+
                        ", se ha ingresado correctamente.", "Exito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error al ingresar el categoria.", "Error", JOptionPane.ERROR);
            }
        } catch (Exception e) {
            System.out.println("Error de conexion: "+e.getMessage());
            JOptionPane.showMessageDialog(null, "Error: "+e.getMessage(), "Error", JOptionPane.ERROR);
        }
        return bandera;
    }
    
    public boolean actualizarCategoriaPlato(CategoriaPlato categoria) {
        boolean bandera = false;
        try {
            s = conection.createStatement();
            int z = s.executeUpdate("UPDATE categoriaplato SET nombre = '"+categoria.nombre
                    +"', descripcion = '"+categoria.descripcion+"' WHERE id = "+categoria.id);
            if (z == 1) {
                bandera = true;
                JOptionPane.showMessageDialog(null, "La categoría: "+categoria.nombre+
                        ", se ha actualizado correctamente.", "Exito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error al actualizar categoría.", "Error", JOptionPane.ERROR);
            }
        } catch (Exception e) {
            System.out.println("Error de conexion: "+e.getMessage());
            JOptionPane.showMessageDialog(null, "Error: "+e.getMessage(), "Error", JOptionPane.ERROR);
        }
        return bandera;
    }
    
    public boolean eliminarCategoria(CategoriaPlato categoria) {
        boolean bandera = false;
        try {
            s = conection.createStatement();
            int z = s.executeUpdate("DELETE FROM categoriaplato WHERE id = "+categoria.id);
            if (z == 1) {
                bandera = true;
                JOptionPane.showMessageDialog(null, "La categoría: "+categoria.nombre+
                        ", se ha eliminado correctamente.", "Exito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error al eliminar la categoría.", "Error", JOptionPane.ERROR);
            }
        } catch (Exception e) {
            System.out.println("Error de conexion: "+e.getMessage());
            JOptionPane.showMessageDialog(null, "Error: "+e.getMessage(), "Error", JOptionPane.ERROR);
        }
        return bandera;
    }
    
    public ArrayList<CategoriaPlato> listarCategoriasPlato() {
        ArrayList<CategoriaPlato> lista = new ArrayList<CategoriaPlato>();
        try {
            s = conection.createStatement();
            rs = s.executeQuery("SELECT * FROM categoriaplato");
            CategoriaPlato categoria = new CategoriaPlato();
            while (rs.next()) {
                categoria.setId(rs.getInt("id"));
                categoria.setNombre(rs.getString("nombre"));
                categoria.setDescripcion(rs.getString("descripcion"));
                lista.add(categoria);
                categoria = new CategoriaPlato();
            }
        } catch (Exception e) {
            System.out.println("Error de conexion: "+e.getMessage());
            JOptionPane.showMessageDialog(null, "Error: "+e.getMessage(), "Error", JOptionPane.ERROR);
        }
        return lista;
    }
    
    public ArrayList<CategoriaPlato> buscarCategoriaPlato(String nombre) {
        ArrayList<CategoriaPlato> lista = new ArrayList<CategoriaPlato>();
        try {
            s = conection.createStatement();
            rs = s.executeQuery("SELECT name FROM categoriaplato WHERE nombre = '"+nombre+"'");
            CategoriaPlato categoria = new CategoriaPlato();
            while (rs.next()) {
                categoria.setId(rs.getInt("id"));
                categoria.setNombre(rs.getString("nombre"));
                categoria.setDescripcion(rs.getString("descripcion"));
                lista.add(categoria);
                categoria = new CategoriaPlato();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: "+e.getMessage(), "Error", JOptionPane.ERROR);
        }
        return lista;
    }
}
