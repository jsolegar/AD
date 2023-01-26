/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Driver;

import Application.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DriverUser {
    public Boolean CheckUserPass(String user, String password) {
        Connection con = null;
        try {
            String query = "SELECT * FROM usuarios WHERE id_usuario = ?";
            DriverConexion dr = new DriverConexion();
            con = dr.DBConexion(con);
            PreparedStatement statement;
            statement = con.prepareStatement(query);
            statement.setString(1, user);
            
            ResultSet rs = statement.executeQuery();
            
            if (rs.next()) { //Si existe usuario
                String passwordCheck = rs.getString("password");
                if (password.equals(passwordCheck)) return true;
                else return false;
            }
            else return false;          
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
        finally {
            try {
                if (con != null) {
                    con.close();
                }
            }
            catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
    }
    
    public void NewUser(User usuario) {
        Connection con = null;
        try {
            String query = "INSERT INTO usuarios (ID_USUARIO, PASSWORD) VALUES (?,?)";
            DriverConexion dr = new DriverConexion();
            con = dr.DBConexion(con);
            PreparedStatement statement;
            statement = con.prepareStatement(query);
            statement.setString(1, usuario.user);
            statement.setString(2, usuario.password);
            statement.executeUpdate();
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        finally {
            try {
                if (con != null) {
                    con.close();
                }
            }
            catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
    }
    public Boolean CheckUser(String usuario) {
        Connection con = null;
        try {
            String query = "SELECT * FROM usuarios WHERE id_usuario = ?";
            DriverConexion dr = new DriverConexion();
            con = dr.DBConexion(con);
            PreparedStatement statement;
            statement = con.prepareStatement(query);
            statement.setString(1, usuario);
            
            ResultSet rs = statement.executeQuery();
            
            return rs.next(); //Si existe usuario
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
        finally {
            try {
                if (con != null) {
                    con.close();
                }
            }
            catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
    }
}
