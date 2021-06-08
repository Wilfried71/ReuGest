/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.reugest.models;

/**
 *
 * @author tpeyr
 */
public class Settings {
    
    private int id;
    private String key,value;

    public Settings(int id, String key, String value) {
        this.id = id;
        this.key = key;
        this.value = value;
    }

    public Settings() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
        
}
