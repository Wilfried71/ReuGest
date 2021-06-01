package fr.thomas.orm;

/**
 * Configuration de la connexion a la base de donnees.<br>
 * Les variables doivent etre definies au lancement de l'application.
 *
 * @author tpeyr
 *
 */
public class ORMConfig {

    /**
     * Adresse IP ou nom de domaine du serveur SGBD
     */
    public static String server;

    /**
     * Port d'acces au serveur SGBD
     */
    public static String port;

    /**
     * Nom d'utilisateur utilise pour se connecter au serveur SGBD.
     */
    public static String username;

    /**
     * Mot de passe utilise pour se connecter au serveur SGBD
     */
    public static String password;

    /**
     * Nom de la base de donnees.
     */
    public static String database;

    /**
     * Timezone du serveur SGBD (a une influence sur les dates)
     */
    public static String serverTimeZone;
}
