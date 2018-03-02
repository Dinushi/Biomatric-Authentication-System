/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biomatric;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
 * @author Dinushi Ranagalage
 */

public class KeyListenener implements KeyListener{
        //long [] keyPressedTimes=new long[20];//get the size from username length
        double avg_keyHoldTime;
        double avg_interKeyTime;
        double avg_keyPressTokeyPressTime;
        
        List<Long> keyPressedTimes;
        List<Long> keyReleasedTimes;
        
        //List<Long> keyTimes;
        //List<Long> InterKeyTime;
        //List<Long> keysPressed;
        
        List<String> keySequence;//holds the pressed keys orderly
        //long [] keyReleasedTimes=new long[20];
        long start_time,end_time;
        calculation calc;
        
        public KeyListenener(){
            this.keyPressedTimes=new ArrayList<Long>();
            this.keyReleasedTimes=new ArrayList<Long>();
            //this.keyTimes=new ArrayList<Long>();
            //this.InterKeyTime=new ArrayList<Long>();
            //List<String> keysPressed=new ArrayList<String>();
            this.keySequence = new ArrayList<String>();
            calc =new calculation();
        }
        @Override
        public void keyPressed(KeyEvent event) {
            //getEventInfo("Key Pressed", event);
            start_time=System.currentTimeMillis();
            keyPressedTimes.add(start_time);
            System.out.println(start_time);
            System.out.println("keyPressed times"+this.keyPressedTimes);
        }
        @Override
        public void keyReleased(KeyEvent event) {
            //getEventInfo("Key Released", event);
            end_time=System.currentTimeMillis();
            keyReleasedTimes.add(end_time);
            System.out.println(end_time);
            System.out.println("keyReleased times"+this.keyReleasedTimes);
        }
       
        @Override
        public void keyTyped(KeyEvent event) {
            //getEventInfo("Key Typed", event);
            this.keySequence.add(String.valueOf(event.getKeyChar()));//typed key is added to the list
        }

        private void getEventInfo(String str, KeyEvent e) {
            //System.out.println(str);
            int code = e.getKeyCode();

            //System.out.println("   Code: " + KeyEvent.getKeyText(code));
            //System.out.println("   Char: " + e.getKeyChar());
            int mods = e.getModifiersEx();
            //System.out.println("    Mods: "+ KeyEvent.getModifiersExText(mods));
            //System.out.println("    Location: "+ keyboardLocation(e.getKeyLocation()));
            //System.out.println("    Action? " + e.isActionKey());
        }

        private String keyboardLocation(int keybrd) {

            switch (keybrd) {
                case KeyEvent.KEY_LOCATION_RIGHT:
                    return "Right";
                case KeyEvent.KEY_LOCATION_LEFT:
                    return "Left";
                case KeyEvent.KEY_LOCATION_NUMPAD:
                    return "NumPad";
                case KeyEvent.KEY_LOCATION_STANDARD:
                    return "Standard";
                case KeyEvent.KEY_LOCATION_UNKNOWN:
                default:
                    return "Unknown";

            }

        }
        public boolean checkTheUserNameSequence(String name){
            return calc.checkTheUserNameSequence(keySequence,name);
        }
        public void findAvgKeyHoldtime(){
            this.avg_keyHoldTime=calc.findKeyHoldTimes(keyPressedTimes, keyReleasedTimes);
        }
        public void findAvgInterKeyTime(){
            this.avg_interKeyTime=calc.InterKeyTime(keyPressedTimes, keyReleasedTimes);
        }
        public void findAvgkeyPressTokeyPressTime(){
            this.avg_keyPressTokeyPressTime=calc.keyPressTokeyPressTime(keyPressedTimes, keyReleasedTimes);
        }
    
}
