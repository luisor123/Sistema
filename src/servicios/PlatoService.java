/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;

import entidades.Plato;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author javie
 */
public class PlatoService {
    private Connection conection = null;
    private Statement s = null;
    private ResultSet rs = null;
    
    public PlatoService(Connection conection) {
        this.conection = conection;
    }
    
    public boolean ingresarPlato(Plato plato){
        boolean bandera = false;
        try {
            s = conection.createStatement();
            int z = s.executeUpdate("INSERT INTO plato (nombre, precio, categoria_id, descripcion) "
                    + "values ('"+plato.nombre+"', "+plato.precio+", "+plato.categoria_id
                    +", '"+plato.descripcion+"')");
            if (z == 1) {
                bandera = true;
                JOptionPane.showMessageDialog(null, "El plato: "+plato.nombre+
                        ", se ha ingresado correctamente.", "Exito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error al ingresar el plato.", "Error", JOptionPane.ERROR);
            }
        } catch (Exception e) {
            System.out.println("Error de conexion: "+e.getMessage());
            JOptionPane.showMessageDialog(null, "Error: "+e.getMessage(), "Error", JOptionPane.ERROR);
        }
        return bandera;
    }
    
    public boolean actualizarPlato(Plato plato) {
        boolean bandera = false;
        try {
            s = conection.createStatement();
            int z = s.executeUpdate("UPDATE plato SET nombre = '"+plato.nombre
                    +"', precio = "+plato.precio+", categoria_id = "+plato.categoria_id
                    +", descripcion = '"+plato.descripcion+"' WHERE id = "+plato.id);
            if (z == 1) {
                bandera = true;
                JOptionPane.showMessageDialog(null, "El plato: "+plato.nombre+
                        ", se ha actualizado correctamente.", "Exito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error al actualizar plato.", "Error", JOptionPane.ERROR);
            }
        } catch (Exception e) {
            System.out.println("Error de conexion: "+e.getMessage());
            JOptionPane.showMessageDialog(null, "Error: "+e.getMessage(), "Error", JOptionPane.ERROR);
        }
        return bandera;
    }
    
    public boolean eliminarPlato(Plato plato) {
        boolean bandera = false;
        try {
            s = conection.createStatement();
            int z = s.executeUpdate("DELETE FROM plato WHERE id = "+plato.id);
            if (z == 1) {
                bandera = true;
                JOptionPane.showMessageDialog(null, "El plato: "+plato.nombre+
                        ", se ha eliminado correctamente.", "Exito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error al eliminar el plato.", "Error", JOptionPane.ERROR);
            }
        } catch (Exception e) {
            System.out.println("Error de conexion: "+e.getMessage());
            JOptionPane.showMessageDialog(null, "Error: "+e.getMessage(), "Error", JOptionPane.ERROR);
        }
        return bandera;
    }
    
    public ArrayList<Plato> listarPlatos() {
        ArrayList<Plato> lista = new ArrayList<Plato>();
        try {
            s = conection.createStatement();
            rs = s.executeQuery("SELECT plato.id, plato.nombre, plato.precio, plato.categoria_id,"
                    + "plato.descripcion, categoriaplato.nombre as nombrecategoria FROM plato "
                    + "inner join categoriaplato on categoriaplato.id = plato.categoria_id");
            Plato plato = new Plato();
            while (rs.next()) {
                plato.setId(rs.getInt("id"));
                plato.setNombre(rs.getString("nombre"));
                plato.setPrecio(rs.getDouble("precio"));
                plato.setCategoria_id(rs.getInt("categoria_id"));
                plato.setNombrecategoria(rs.getString("nombrecategoria"));
                plato.setDescripcion(rs.getString("descripcion"));
                lista.add(plato);
                plato = new Plato();
            }
        } catch (Exception e) {
            System.out.println("Error de conexion: "+e.getMessage());
            JOptionPane.showMessageDialog(null, "Error: "+e.getMessage(), "Error", JOptionPane.ERROR);
        }
        return lista;
    }
    
    public ArrayList<Plato> buscarPlatos(String nombres) {
        ArrayList<Plato> lista = new ArrayList<Plato>();
        try {
            s = conection.createStatement();
            rs = s.executeQuery("SELECT name FROM plato WHERE nombres = '"+nombres+"'");
            Plato plato = new Plato();
            while (rs.next()) {
                plato.setId(rs.getInt("id"));
                plato.setNombre(rs.getString("nombre"));
                plato.setPrecio(rs.getDouble("precio"));
                plato.setCategoria_id(rs.getInt("categoria_id"));
                plato.setDescripcion(rs.getString("descripcion"));
                lista.add(plato);
                plato = new Plato();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: "+e.getMessage(), "Error", JOptionPane.ERROR);
        }
        return lista;
    }
}
