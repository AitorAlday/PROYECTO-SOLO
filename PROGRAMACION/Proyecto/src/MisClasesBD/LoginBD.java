/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MisClasesBD;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import proyecto.Proyecto;

/**
 *
 * @author Aitor Alday
 */
public class LoginBD {
    
    private static GenericoBD gbd;
    
    public static String logearUsuario(String nombre, String contrasenya) {
        //Utilizamos unuario y nombre para logear a un usuario.
        try {
            gbd = new GenericoBD();
            nombre = nombre.toLowerCase();
            contrasenya = contrasenya.toLowerCase();
            String a = "No se ha encontrado el usuario";
            PreparedStatement sentencia = gbd.abrirConexion().prepareStatement("select t.tipo from Login l, Trabajador t where t.dni=l.dni and lower(l.usuario)=? and lower(contraseña)=?");
            sentencia.setString(1, nombre);
            sentencia.setString(2, contrasenya);
            ResultSet resultado = sentencia.executeQuery();
            if (resultado.next()) {
                a = resultado.getString("tipo");   
            }
            gbd.cerrarConexion();
            return a;
        } 
        catch (Exception e) {
            Proyecto.toDLogin("Problemas en logearUsuario, en LoginBD: " + e.getMessage());
            return "Error";
        }
    }
    
    public static String obtenerNombre(String nombre, String contrasenya) throws Exception {
        //Para saludar debidamente el trabajador, utilizamos una select con una join para saber como se llama el Trabajador. Utilizamos su usuario y contraseña
        try {
            gbd = new GenericoBD();
            nombre = nombre.toLowerCase();
            contrasenya = contrasenya.toLowerCase();
            String a = "No se ha encontrado el usuario";
            PreparedStatement sentencia = gbd.abrirConexion().prepareStatement("select t.nombre from Login l, Trabajador t where t.dni=l.dni and lower(l.usuario)=? and lower(contraseña)=?");
            sentencia.setString(1, nombre);
            sentencia.setString(2, contrasenya);
            ResultSet resultado = sentencia.executeQuery();
            if (resultado.next()) {
                a = resultado.getString("nombre");
            }
            gbd.cerrarConexion();
            return a;
        } 
        catch (Exception e) {
            Proyecto.toDLogin("Problemas en obtenerNombre, en LoginBD: " + e.getMessage());
            return "Error";
        }
    }
    
    public static void crearLogin (String nombre, String apeuno, String apedos,String dni) {
        //Cada vez que creamos a un trabajador, creamos un login para este. Se compone de su primera letra del nombre y su apellido.
        //En caso de que el usuario exista tambien usaremos su segundo apellido
        try {
            String cadena = nombre.charAt(0)+apeuno;
            gbd = new GenericoBD();
            Statement sentencia = gbd.abrirConexion().createStatement();
            ResultSet resultado = sentencia.executeQuery("select * from Login");
            while (resultado.next()) {
                if (cadena.equalsIgnoreCase(resultado.getString("usuario")))
                    cadena = cadena+apedos;
            }
            PreparedStatement ps = gbd.abrirConexion().prepareStatement("insert into login values (?,?,?)");
            ps.setString(1, dni);
            ps.setString(2, cadena);
            ps.setString(3, cadena);
            ps.executeUpdate();
            Proyecto.toVAdministracion("Login generado.\n Usuario :"+cadena+"\nContraseña: "+cadena);
            gbd.cerrarConexion();
        } 
        catch (Exception e) {
            Proyecto.toVAdministracion("Problemas en crearLogin, en LoginBD: " + e.getMessage());
        }
    }
    
    public static void borrarLogin(String dni) {
        try {
            gbd = new GenericoBD();
            PreparedStatement ps = gbd.abrirConexion().prepareStatement("delete from login where dni=?");
            ps.setString(1, dni);
            ps.executeUpdate();
            gbd.cerrarConexion();
        } 
        catch (Exception e) {
            Proyecto.toVAdministracion("Problemas en borrarLogin, en LoginBD: " + e.getMessage());
        }
    }
    
    public static void modificarLogin(String usu, String pas, String dni) {
        try {
            gbd = new GenericoBD();
            PreparedStatement ps = gbd.abrirConexion().prepareStatement("update login set usuario=?, contraseña=? where dni=?");
            ps.setString(1, usu);
            ps.setString(2, pas);
            ps.setString(3, dni);
            ps.executeUpdate();
            gbd.cerrarConexion();
            Proyecto.toVAdministracion("Login actualizado");
        } 
        catch (Exception e) {
            Proyecto.toVAdministracion("Problemas en modificarLogin, en LoginBD: " + e.getMessage());
        }
    }
}
