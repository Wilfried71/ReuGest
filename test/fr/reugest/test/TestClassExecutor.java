/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.reugest.test;

import fr.reugest.models.UtilisateurTest;

/**
 *
 * @author tpeyr
 */
public class TestClassExecutor {
    
    public static void main(String[] args) {
        System.out.println("Tests execution :");
        System.out.println("-- UtilisateurTest --");
        UtilisateurTest userTest = new UtilisateurTest();
        userTest.run();
    }
}
