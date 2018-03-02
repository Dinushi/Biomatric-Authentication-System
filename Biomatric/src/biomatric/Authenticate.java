/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biomatric;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Pasidu Chinthiya
 */
public class Authenticate {
    
        public int checkPassword(String username, String password){
        DbConnect dbCon = new DbConnect();
            Statement stm;
            String saved_password="";
            try {
                stm = dbCon.conn.createStatement();
                ResultSet rs = stm.executeQuery("SELECT PASSWORD FROM USER_DATA WHERE USER_NAME='"+username+"'");
                
                while (rs.next()){
                    saved_password=rs.getString(1);
                    
                    //this.user_id = rs.getInt("USER_ID")+1;
                    //System.out.println("savd pasword"+saved_password);
                }
            }catch (SQLException ex) {
                Logger.getLogger(KeyListenener.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (password.equals(saved_password)){
                return 1;
            }
            else{
                return 2;
            }
    }
    public boolean checkKeyStrokeData( KeyListenener keylistner,String username){
        double avg_keyHoldTime=keylistner.avg_keyHoldTime;
        double avg_interKeyTime=keylistner.avg_interKeyTime;
        double avg_keyPressTokeyPressTime=keylistner.avg_keyPressTokeyPressTime;
        
        double mean_keyHoldTime=0;
        double mean_interKeyTime=0;
        double mean_keyPressTokeyPressTime=0;
        
        double  error_rate_keyHoldTime=0;
        double error_rate_interKeyTime=0;
        double error_rate_keyPressTokeyPressTime=0;
        
        DbConnect dbCon = new DbConnect();
        Statement stm;
         try {
                stm = dbCon.conn.createStatement();
                ResultSet rs = stm.executeQuery("SELECT MEAN_KEYHOLDTIME,MEAN_INTERKEYTIME,MEAN_KEYPRESSTOKEYPRESSTIME,ERROR_RATE_KEYHOLDTIME,ERROR_RATE_INTERKEYTIME,ERROR_RATE_KEYPRESSTOKEYPRESSTIME FROM USER_DATA WHERE USER_NAME='"+username+"'");
                
                while (rs.next()){
                    mean_keyHoldTime=rs.getDouble("MEAN_KEYHOLDTIME");
                    mean_interKeyTime=rs.getDouble("MEAN_INTERKEYTIME");
                    mean_keyPressTokeyPressTime=rs.getDouble("MEAN_KEYPRESSTOKEYPRESSTIME");
                    
                    error_rate_keyHoldTime=rs.getDouble("ERROR_RATE_KEYHOLDTIME");
                    error_rate_interKeyTime=rs.getDouble("ERROR_RATE_INTERKEYTIME");
                    error_rate_keyPressTokeyPressTime=rs.getDouble("ERROR_RATE_KEYPRESSTOKEYPRESSTIME");
                
                }
                
                
            }catch (SQLException ex) {
                Logger.getLogger(KeyListenener.class.getName()).log(Level.SEVERE, null, ex);
            }
     
         
         double rangeHold1=mean_keyHoldTime-error_rate_keyHoldTime*2;
         double rangeHold2=mean_keyHoldTime+error_rate_keyHoldTime*2;
         double rangeInter1=mean_interKeyTime-error_rate_interKeyTime*1.7;
         double rangeInter2=mean_interKeyTime+error_rate_interKeyTime*1.7;
         double rangePressToPress1=mean_keyPressTokeyPressTime-error_rate_keyPressTokeyPressTime*1.7;
         double rangePressToPress2=mean_keyPressTokeyPressTime+error_rate_keyPressTokeyPressTime*1.7;
        
        return avg_keyHoldTime>=rangeHold1 && avg_keyHoldTime<=rangeHold2 && avg_interKeyTime>=rangeInter1 && avg_interKeyTime<=rangeInter2 && avg_keyPressTokeyPressTime>=rangePressToPress1 && avg_keyPressTokeyPressTime<=rangePressToPress2;
    }
        
    
}
