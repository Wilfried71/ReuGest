/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.reugest.models;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author tpeyr
 */
public class UtilisateurTest {
    
    public UtilisateurTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getId method, of class Utilisateur.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        Utilisateur instance = new Utilisateur();
        // Test 
        assertEquals(instance.getId(), null);
    }

    /**
     * Test of setId method, of class Utilisateur.
     */
    @Test
    public void testSetId() {
        System.out.println("setId");
        Long id = null;
        Utilisateur instance = new Utilisateur();
        instance.setId(id);
    }

    /**
     * Test of getNom method, of class Utilisateur.
     */
    @Test
    public void testGetNom() {
        System.out.println("getNom");
        Utilisateur instance = new Utilisateur();
        String expResult = "";
        String result = instance.getNom();
        assertEquals(expResult, result);
    }

    /**
     * Test of setNom method, of class Utilisateur.
     */
    @Test
    public void testSetNom() {
        System.out.println("setNom");
        String nom = "";
        Utilisateur instance = new Utilisateur();
        instance.setNom(nom);
    }

    /**
     * Test of getPrenom method, of class Utilisateur.
     */
    @Test
    public void testGetPrenom() {
        System.out.println("getPrenom");
        Utilisateur instance = new Utilisateur();
        String expResult = "";
        String result = instance.getPrenom();
        assertEquals(expResult, result);
    }

    /**
     * Test of setPrenom method, of class Utilisateur.
     */
    @Test
    public void testSetPrenom() {
        System.out.println("setPrenom");
        String prenom = "";
        Utilisateur instance = new Utilisateur();
        instance.setPrenom(prenom);
    }

    /**
     * Test of getEmail method, of class Utilisateur.
     */
    @Test
    public void testGetEmail() {
        System.out.println("getEmail");
        Utilisateur instance = new Utilisateur();
        String expResult = "";
        String result = instance.getEmail();
        assertEquals(expResult, result);
    }

    /**
     * Test of setEmail method, of class Utilisateur.
     */
    @Test
    public void testSetEmail() {
        System.out.println("setEmail");
        String email = "";
        Utilisateur instance = new Utilisateur();
        instance.setEmail(email);
    }

    /**
     * Test of getPassword method, of class Utilisateur.
     */
    @Test
    public void testGetPassword() {
        System.out.println("getPassword");
        Utilisateur instance = new Utilisateur();
        String expResult = "";
        String result = instance.getPassword();
        assertEquals(expResult, result);
    }

    /**
     * Test of setPassword method, of class Utilisateur.
     */
    @Test
    public void testSetPassword() {
        System.out.println("setPassword");
        String password = "";
        Utilisateur instance = new Utilisateur();
        instance.setPassword(password);
    }

    /**
     * Test of getDroit method, of class Utilisateur.
     */
    @Test
    public void testGetDroit() {
        System.out.println("getDroit");
        Utilisateur instance = new Utilisateur();
        Droit expResult = null;
        Droit result = instance.getDroit();
        assertEquals(expResult, result);
    }

    /**
     * Test of setDroit method, of class Utilisateur.
     */
    @Test
    public void testSetDroit() {
        System.out.println("setDroit");
        Droit droit = null;
        Utilisateur instance = new Utilisateur();
        instance.setDroit(droit);
    }

    /**
     * Test of getService method, of class Utilisateur.
     */
    @Test
    public void testGetService() {
        System.out.println("getService");
        Utilisateur instance = new Utilisateur();
        Service expResult = null;
        Service result = instance.getService();
        assertEquals(expResult, result);
    }

    /**
     * Test of setService method, of class Utilisateur.
     */
    @Test
    public void testSetService() {
        System.out.println("setService");
        Service service = null;
        Utilisateur instance = new Utilisateur();
        instance.setService(service);
    }

    /**
     * Test of getFonction method, of class Utilisateur.
     */
    @Test
    public void testGetFonction() {
        System.out.println("getFonction");
        Utilisateur instance = new Utilisateur();
        Fonction expResult = null;
        Fonction result = instance.getFonction();
        assertEquals(expResult, result);
    }

    /**
     * Test of setFonction method, of class Utilisateur.
     */
    @Test
    public void testSetFonction() {
        System.out.println("setFonction");
        Fonction fonction = null;
        Utilisateur instance = new Utilisateur();
        instance.setFonction(fonction);
    }

    /**
     * Test of getIsDeleted method, of class Utilisateur.
     */
    @Test
    public void testGetIsDeleted() {
        System.out.println("getIsDeleted");
        Utilisateur instance = new Utilisateur();
        Boolean expResult = null;
        Boolean result = instance.getIsDeleted();
        assertEquals(expResult, result);
    }

    /**
     * Test of setIsDeleted method, of class Utilisateur.
     */
    @Test
    public void testSetIsDeleted() {
        System.out.println("setIsDeleted");
        Boolean isDeleted = null;
        Utilisateur instance = new Utilisateur();
        instance.setIsDeleted(isDeleted);
    }

    /**
     * Test of toString method, of class Utilisateur.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Utilisateur instance = new Utilisateur();
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
    }
    
}
