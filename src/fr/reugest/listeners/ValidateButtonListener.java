/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.reugest.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author jeansauron
 */
public class ValidateButtonListener<T> implements ActionListener {
    
    final Class<T> type;

    public ValidateButtonListener(Class<T> type) {
        this.type = type;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        System.out.println(type.toString()); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
