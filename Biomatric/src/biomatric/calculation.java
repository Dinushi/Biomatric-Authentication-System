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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Pasidu Chinthiya
 */
public class calculation {
        List<Long> keyHoldTimes;
        List<Long> interKeyTimes;
        List<Long> keyPressTokeyPressTimes;
        
        double mean_keyHoldTime;
        double mean_interKeyTime;
        double mean_keyPressTokeyPressTime;
        
        double dev_keyHoldTime;//hold the standard deviation value for error rate
        double dev_interKeyTime;
        double dev_keyPressTokeyPressTime;
       
        DbConnect dbCon ;
        
        public boolean checkTheUserNameSequence(List<String> keySequence,String name){
            //System.out.println("username "+name);
            //System.out.println(keySequence);
            if(keySequence.size()!= name.length()){
                System.out.println("size has not been equaled");
                return false;
            }else{
                String[] name_ary=name.split("");
                for(int i=0;i<keySequence.size();i++){
                    //System.out.println(keySequence.get(i)+"   "+name_ary[i]);
                    //System.out.println(keySequence.get(i).getClass()+"----   "+name_ary[i].getClass());
                    if (!keySequence.get(i).equals(name_ary[i])){//be creful about equals
                        System.out.println("letters has not been equal");
                        return false;
                    }
                }
            }
           return true; 
        }
         public double findKeyHoldTimes( List<Long> keyPressedTimes,List<Long> keyReleasedTimes){
            this.keyHoldTimes= new ArrayList<Long>();
            for(int i=0;i<keyPressedTimes.size();i++){
                keyHoldTimes.add((keyReleasedTimes.get(i)-keyPressedTimes.get(i)));   
            } 
            System.out.println(keyHoldTimes);
            long total=0;
            for(int i=0;i<keyHoldTimes.size();i++){
                total+=keyHoldTimes.get(i);  
            }
            double avg_time=total/keyHoldTimes.size();
            System.out.println("avg_timeHold"+avg_time);
            return avg_time;
 
        }
        public double keyPressTokeyPressTime(List<Long> keyPressedTimes,List<Long> keyReleasedTimes){
            this.keyPressTokeyPressTimes= new ArrayList<Long>();
            for(int i=1;i<keyPressedTimes.size();i++){
                keyPressTokeyPressTimes.add((keyPressedTimes.get(i)-keyPressedTimes.get(i-1)));   
            } 
            double total=0;
            for(int i=0;i<keyPressTokeyPressTimes.size();i++){
                total+= keyPressTokeyPressTimes.get(i);
            }
            double avg_time=total/keyPressTokeyPressTimes.size();
            System.out.println("avg_timepress_press"+avg_time);
            return avg_time;  
        }
    public double InterKeyTime(List<Long> keyPressedTimes,List<Long> keyReleasedTimes){
            this.interKeyTimes= new ArrayList<Long>();
            for(int i=1;i<keyPressedTimes.size();i++){
                interKeyTimes.add((keyPressedTimes.get(i)-keyReleasedTimes.get(i-1)));   
            }
            double total=0;
            for(int i=0;i<interKeyTimes.size();i++){
                total+= interKeyTimes.get(i);
            }
            double avg_time=total/interKeyTimes.size();
            System.out.println("avg_timeInterKey"+avg_time);
            return avg_time;  
    }
    
    public void calculate_mean_values(List<KeyListenener> listners){
        double total_keyHoldTime=0.0;
        double total_interKeyTime=0.0;
        double total_keyPressTokeyPressTime=0.0;
        for(int i=0;i<listners.size();i++){
                total_keyHoldTime+= listners.get(i).avg_keyHoldTime;
                total_interKeyTime+=listners.get(i).avg_interKeyTime;
                total_keyPressTokeyPressTime+=listners.get(i).avg_keyPressTokeyPressTime;
        }
        this.mean_keyHoldTime=total_keyHoldTime/listners.size();
        this.mean_interKeyTime=total_interKeyTime/listners.size();
        this.mean_keyPressTokeyPressTime=total_keyPressTokeyPressTime/listners.size();
        System.out.println("mean hold"+this.mean_keyHoldTime);
        System.out.println("mean press"+this.mean_interKeyTime);
        System.out.println("mean inter"+this.mean_keyPressTokeyPressTime);
    }
    public void calculateErrorRate(List<KeyListenener> listners){
        double diff_keyHoldTime=0.0;
        double diff_interKeyTime=0.0;
        double diff_keyPressTokeyPressTime=0.0;
        
        for(int i=0;i<listners.size();i++){
                diff_keyHoldTime+= Math.pow(listners.get(i).avg_keyHoldTime-this.mean_keyHoldTime,2);
                diff_interKeyTime+=Math.pow(listners.get(i).avg_interKeyTime-this.mean_interKeyTime,2);
                diff_keyPressTokeyPressTime+=Math.pow(listners.get(i).avg_keyPressTokeyPressTime-this.mean_keyPressTokeyPressTime,2);
                
        }
        
        this.dev_keyHoldTime=Math.sqrt(diff_keyHoldTime/listners.size());
        this.dev_interKeyTime=Math.sqrt(diff_interKeyTime/listners.size());
        this.dev_keyPressTokeyPressTime=Math.sqrt(diff_keyPressTokeyPressTime/listners.size());
        System.out.println("error hold"+this.dev_keyHoldTime);
        System.out.println("error press"+this.dev_interKeyTime);
        System.out.println("error inter"+this.dev_keyPressTokeyPressTime);
    }
    
    public void updateDb(String username,String password,String email){
         DbConnect dbCon = new DbConnect();
         int user_id=0;
            Statement stm;
            try {
                stm = dbCon.conn.createStatement();
                ResultSet rs = stm.executeQuery("SELECT MAX(USER_ID)FROM USER_DATA");
                while (rs.next()){
                    user_id=rs.getInt(1)+1;
                    //this.user_id = rs.getInt("USER_ID")+1;
                    System.out.println("new user_id "+user_id);
                }
                String template = "INSERT INTO USER_DATA (USER_ID,USER_NAME,PASSWORD,EMAIL,MEAN_KEYHOLDTIME,MEAN_INTERKEYTIME,MEAN_KEYPRESSTOKEYPRESSTIME,ERROR_RATE_KEYHOLDTIME,ERROR_RATE_INTERKEYTIME,ERROR_RATE_KEYPRESSTOKEYPRESSTIME) values (?,?,?,?,?,?,?,?,?,?)";
                PreparedStatement stmt = dbCon.conn.prepareStatement(template);
                stmt.setInt(1,user_id);
                stmt.setString(2,username);
                stmt.setString(3,password);
                stmt.setString(4,email);
                stmt.setDouble(5, this.mean_keyHoldTime);
                stmt.setDouble(6, this.mean_interKeyTime);
                stmt.setDouble(7, this.mean_keyPressTokeyPressTime);
                stmt.setDouble(8, dev_keyHoldTime);
                stmt.setDouble(9, dev_interKeyTime);
                stmt.setDouble(10, dev_keyPressTokeyPressTime);

                stmt.executeUpdate();
            }catch (SQLException ex) {
                Logger.getLogger(KeyListenener.class.getName()).log(Level.SEVERE, null, ex);
            }
    
    }
}
