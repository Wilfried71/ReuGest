/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.reugest.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *  assertEquals() ne marche pas
 * @author tpeyr
 */
public class UtilisateurTest {
    
    public UtilisateurTest() {
    }
    
    public void run() {
        testId();
        testNom();
        testPrenom();
        testEmail();
        testPassword();
        testDroit();
        testFonction();
        testService();
        testIsDeleted();
        testToString();
    }

    @Test
    public void testId() {
        Utilisateur instance = new Utilisateur();
        assertNull(instance.getId());
        instance.setId(123L);
        assertTrue(instance.getId().equals(123L));
        System.out.println("testId works !");
    }


    @Test
    public void testNom() {
        Utilisateur instance = new Utilisateur();
        assertNull(instance.getNom());
        instance.setNom("Toto");
        assertTrue(instance.getNom().equals("Toto"));
        //assertEquals(instance.getNom(), "Toto");
        System.out.println("testNom works !");
    }


    @Test
    public void testPrenom() {
        Utilisateur instance = new Utilisateur();
        assertNull(instance.getPrenom());
        instance.setPrenom("Titi");
        assertTrue(instance.getPrenom().equals("Titi"));
        //assertEquals(instance.getPrenom(), "Titi");
        System.out.println("testPrenom works !");
    }

    @Test
    public void testEmail() {
        Utilisateur instance = new Utilisateur();
        assertNull(instance.getEmail());
        instance.setEmail("monemail@gmail.com");
        assertTrue(instance.getEmail().equals("monemail@gmail.com"));
        //assertEquals(instance.getEmail(), "monemail@gmail.com");
        System.out.println("testEmail works !");
    }


    @Test
    public void testPassword() {
        Utilisateur instance = new Utilisateur();
        assertNull(instance.getPassword());
        instance.setPassword("M0tD3Passe123!");
        assertTrue(instance.getPassword().equals("M0tD3Passe123!"));
        //assertEquals(instance.getPassword(), "M0tD3Passe123!");
        System.out.println("testPassword works !");
    }

    @Test
    public void testDroit() {
        Utilisateur instance = new Utilisateur();
        Droit droit = new Droit(1L,"Administrateur");
        assertNull(instance.getDroit());        
        instance.setDroit(droit);
        assertTrue(instance.getDroit().equals(droit));
        //assertEquals(instance.getDroit(), droit);
        System.out.println("testDroit works !");
    }

    @Test
    public void testService() {
        Utilisateur instance = new Utilisateur();
        Service service = new Service(1L,"Informatique");
        assertNull(instance.getService());
        instance.setService(service);
        assertTrue(instance.getService().equals(service));
        //assertEquals(instance.getService(), service);
        System.out.println("testService works !");
    }

    @Test
    public void testFonction() {
        Utilisateur instance = new Utilisateur();
        Fonction fonction = new Fonction(1L,"DRH");
        assertNull(instance.getFonction());
        instance.setFonction(fonction);
        assertTrue(instance.getFonction().equals(fonction));
        //assertEquals(instance.getFonction(), fonction);
        System.out.println("testFonction works !");
    }
    
    @Test
    public void testIsDeleted() {
        Utilisateur instance = new Utilisateur();
        assertNull(instance.getIsDeleted());
        instance.setIsDeleted(false);
        assertTrue(instance.getIsDeleted().equals(false));
       // assertEquals(instance.getIsDeleted(), false);
        System.out.println("testIsDeleted works !");
    }

    @Test
    public void testToString() {
        Utilisateur instance = new Utilisateur(1L,"OUILDOL","Yves","ol@ol.fr","123",null,null,null,false);
        assertTrue(instance.toString().equals("OUILDOL Yves"));
        //assertEquals(instance.toString(), " ");
        System.out.println("testToString works !");
    }
    
}
