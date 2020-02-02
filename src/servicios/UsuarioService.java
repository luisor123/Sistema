/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;

import entidades.Usuario;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author javie
 */
public class UsuarioService {
    private Connection conection = null;
    private Statement s = null;
    private ResultSet rs = null;
    
    public UsuarioService(Connection conection) {
        this.conection = conection;
    }
    
    public Usuario login(String correo, String password) {
        Usuario usuario = null;
        try {
            s = conection.createStatement();
            rs = s.executeQuery("SELECT * FROM usuario WHERE correo = '"+correo+
                    "' and password = '"+password+"'");
            while (rs.next()) {
                usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNombres(rs.getString("nombres"));
                usuario.setDni(rs.getString("dni"));
                usuario.setDireccion(rs.getString("direccion"));
                usuario.setCorreo(rs.getString("correo"));
                usuario.setTelefono(rs.getString("telefono"));
                usuario.setPassword(rs.getString("password"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: "+e.getMessage(), "Error", JOptionPane.ERROR);
        }
        return usuario;
    }
    
    public boolean ingresarUsuario(Usuario usuario){
        boolean bandera = false;
        try {
            s = conection.createStatement();
            int z = s.executeUpdate("INSERT INTO usuario (nombres, dni, direccion, correo, telefono, password) "
                    + "values ('"+usuario.nombres+"', '"+usuario.dni+"','"+usuario.direccion
                    +"','"+usuario.correo+"','"+usuario.telefono+"','"+usuario.password+"')");
            if (z == 1) {
                bandera = true;
                JOptionPane.showMessageDialog(null, "El usuario: "+usuario.nombres+
                        ", se ha ingresado correctamente.", "Exito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error al ingresar el usuario.", "Error", JOptionPane.ERROR);
            }
        } catch (Exception e) {
            System.out.println("Error: "+e.getMessage());
            JOptionPane.showMessageDialog(null, "Error: "+e.getMessage(), "Error", JOptionPane.ERROR);
        }
        return bandera;
    }
    
    public boolean actualizarUsuario(Usuario usuario) {
        boolean bandera = false;
        try {
            s = conection.createStatement();
            int z = s.executeUpdate("UPDATE usuario SET nombres = '"+usuario.nombres
                    +"', dni = '"+usuario.dni+"',direccion = '"+usuario.direccion
                    +"', correo = '"+usuario.correo+"', telefono = '"+usuario.telefono+
                    "', password = '"+usuario.password+"' WHERE id = "+usuario.id);
            if (z == 1) {
                bandera = true;
                JOptionPane.showMessageDialog(null, "El usuario: "+usuario.nombres+
                        ", se ha actualizado correctamente.", "Exito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error al actualizar usuario.", "Error", JOptionPane.ERROR);
            }
        } catch (Exception e) {
            System.out.println("Error de conexion: "+e.getMessage());
            JOptionPane.showMessageDialog(null, "Error: "+e.getMessage(), "Error", JOptionPane.ERROR);
        }
        return bandera;
    }
    
    public boolean eliminarUsuario(Usuario usuario) {
        boolean bandera = false;
        try {
            s = conection.createStatement();
            int z = s.executeUpdate("DELETE FROM usuario WHERE id = "+usuario.id);
            if (z == 1) {
                bandera = true;
                JOptionPane.showMessageDialog(null, "El usuario: "+usuario.nombres+
                        ", se ha eliminado correctamente.", "Exito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error al eliminar el usuario.", "Error", JOptionPane.ERROR);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: "+e.getMessage(), "Error", JOptionPane.ERROR);
        }
        return bandera;
    }
    
    public ArrayList<Usuario> listarUsuarios() {
        ArrayList<Usuario> lista = new ArrayList<Usuario>();
        try {
            s = conection.createStatement();
            rs = s.executeQuery("SELECT * FROM usuario");
            Usuario usuario = new Usuario();
            while (rs.next()) {
                usuario.setId(rs.getInt("id"));
                usuario.setNombres(rs.getString("nombres"));
                usuario.setDni(rs.getString("dni"));
                usuario.setDireccion(rs.getString("direccion"));
                usuario.setCorreo(rs.getString("correo"));
                usuario.setTelefono(rs.getString("telefono"));
                usuario.setPassword(rs.getString("password"));
                lista.add(usuario);
                usuario = new Usuario();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: "+e.getMessage(), "Error", JOptionPane.ERROR);
        }
        return lista;
    }
    
    public ArrayList<Usuario> buscarUsuarios(String nombres) {
        ArrayList<Usuario> lista = new ArrayList<Usuario>();
        try {
            s = conection.createStatement();
            rs = s.executeQuery("SELECT name FROM usuario WHERE nombres = '"+nombres+"'");
            Usuario usuario = new Usuario();
            while (rs.next()) {
                usuario.setId(rs.getInt("id"));
                usuario.setNombres(rs.getString("nombres"));
                usuario.setDni(rs.getString("dni"));
                usuario.setDireccion(rs.getString("direccion"));
                usuario.setCorreo(rs.getString("correo"));
                usuario.setTelefono(rs.getString("telefono"));
                usuario.setPassword(rs.getString("password"));
                lista.add(usuario);
                usuario = new Usuario();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: "+e.getMessage(), "Error", JOptionPane.ERROR);
        }
        return lista;
    }
}
