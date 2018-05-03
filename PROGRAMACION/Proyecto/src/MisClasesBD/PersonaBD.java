/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MisClasesBD;

import MisClases.Persona;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author Aitor Alday
 */
public class PersonaBD {
    
    private static GenericoBD gbd;
    
    public static void insertarPersona(Persona p, Integer tipo){
        try{
            gbd = new GenericoBD();
            PreparedStatement sentencia = gbd.abrirConexion().prepareStatement("insert into Persona values (?,?,?)");
            sentencia.setInt(1, p.getIdPersona());
            sentencia.setString(2, p.getNombre());
            sentencia.setInt(3, p.getTipo());
            
            gbd.cerrarConexion();
        }
        catch(Exception e){
            
        }
    }
    
    public static void actualizarPersona(Persona p){
         try{
            gbd = new GenericoBD();
            PreparedStatement sentencia = gbd.abrirConexion().prepareStatement("update persona set id_persona=?, nombre=?, tipo=?");
            sentencia.setInt(1, p.getIdPersona());
            sentencia.setString(2, p.getNombre());
            sentencia.setInt(3, p.getTipo());
            
            gbd.cerrarConexion();
        }
        catch(Exception e){
            
        }
    }
    
    public static void borrarPersona(Persona p) {
        try {
            gbd = new GenericoBD();
            PreparedStatement sentencia = gbd.abrirConexion().prepareStatement("delete from persona where id_persona=?");
            sentencia.setInt(1, p.getIdPersona());
            sentencia.executeUpdate();
            
            gbd.cerrarConexion();
        }
        catch (Exception e) {
            
        }
    }
}
