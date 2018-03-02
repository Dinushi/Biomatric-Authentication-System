/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biomatric;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JTextField;

/**
 *
 * @author Dinushi Ranagalage
 */
public class keyListner {
    public static void action() {

        JFrame frame = new JFrame("Key Listener");
        frame.setSize(600,400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Container contentPane = frame.getContentPane();
        KeyListener listener = new KeyListener() {
            
        long start_time,end_time;
         @Override
        public void keyPressed(KeyEvent event) {
            printEventInfo("Key Pressed", event);
            start_time=System.currentTimeMillis();
            System.out.println(start_time);
        }
        @Override
        public void keyReleased(KeyEvent event) {
            printEventInfo("Key Released", event);
            end_time=System.currentTimeMillis();
            System.out.println(end_time);
            System.out.println(end_time - start_time);
        }

        @Override
        public void keyTyped(KeyEvent event) {
            printEventInfo("Key Typed", event);
        }

        private void printEventInfo(String str, KeyEvent e) {
            System.out.println(str);
            int code = e.getKeyCode();

            System.out.println("   Code: " + KeyEvent.getKeyText(code));
            System.out.println("   Char: " + e.getKeyChar());
            int mods = e.getModifiersEx();
            System.out.println("    Mods: "+ KeyEvent.getModifiersExText(mods));
            System.out.println("    Location: "+ keyboardLocation(e.getKeyLocation()));
            System.out.println("    Action? " + e.isActionKey());
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
        };
        
        JTextField textField = new JTextField();
        textField.addKeyListener(listener);
        contentPane.add(textField, BorderLayout.NORTH);
        //frame.pack();
        frame.setVisible(true);
       }
    }
    
