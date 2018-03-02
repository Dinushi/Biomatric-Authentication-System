/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biomatric;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dinushi Ranagalage
 */
public class DbConnect {
        public Connection conn;
        public DbConnect(){
        try {

            this.conn=DriverManager.getConnection("jdbc:derby://localhost:1527/KeyStroke","root","root");
            System.out.println("database_connected");
        } catch (SQLException ex) {
            Logger.getLogger(DbConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
