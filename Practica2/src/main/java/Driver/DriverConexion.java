/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Driver;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

/**
 *
 * @author alumne
 */
public class DriverConexion {
    
    private static final String URL = "jdbc:derby://localhost:1527/pr2";
    private static final String dbUser = "pr2";
    private static final String dbPassword = "pr2";
    private static final String DRIVER = "org.apache.derby.jdbc.ClientDriver";
    
    static {
        try {
            Class.forName(DRIVER);
        }
        catch (ClassNotFoundException e) {
            System.out.println("Error al cargar controlador");
        }
    }
    
    public Connection DBConexion(Connection conexion) {
        try {
            conexion = DriverManager.getConnection(URL, dbUser, dbPassword);
        } catch (SQLException e) {
            System.out.println("Error en la conexion de la DB");
            System.err.println(e.getMessage());
        }
        return conexion;
    }    
}
