package fr.thomas.orm.interfaces;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

/**
 * Interface de base, qui contient les definitions des methodes CRUD.
 *
 * @author tpeyr
 *
 * @param <T>
 */
public interface DAO<T> {

    /**
     * Methode qui sert a creer un objet dans la base de donnees.<br>
     * Par defaut, les champs identifiants doivent etre a null. Si les champs
     * identifiants sont renseignes, la requete sera quand meme tentee.
     *
     * @param object
     * @return L'objet qui est cree.
     * @throws Exception
     */
    T create(T object) throws Exception;

    /**
     * M�thode qui sert a modifier un objet dont le ou les champs identifiants
     * sont renseignes.<br>
     *
     * @param object
     * @return L'objet qui est modifie.
     * @throws Exception
     */
    T update(T object) throws Exception;

    /**
     * M�thode qui sert a supprimer un objet dans la base de donnees.<br>
     * Seul le ou les champs identifiants sont pris en compte dans la methode.
     *
     * @param object
     * @throws Exception
     */
    void delete(T object) throws Exception;

    /**
     * M�thode qui sert a supprimer un objet dans la base de donn�es, dont
     * l'identifiant est passe en parametres.<br>
     *
     * @param object
     * @throws Exception
     */
    void deleteById(Long id) throws Exception;

    /**
     * Fonction qui retourne tous les elements d'une table donnee
     *
     * @return
     * @throws SQLException
     * @throws SecurityException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws ParseException
     */
    List<T> findAll() throws SQLException, IllegalArgumentException, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException, SecurityException, ParseException;

    /**
     * Realise une requete preparee de type SELECT.
     *
     * @param query
     * @param fields
     * @return
     * @throws Exception
     */
    List<T> query(String query, List<?> fields) throws Exception;

    /**
     * Fonction qui retourne un objet dont l'identifiant est passe en parametres
     *
     * @param id
     * @return
     * @throws SQLException
     * @throws ParseException
     * @throws SecurityException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    T findById(Long id) throws SQLException, IllegalArgumentException, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException, SecurityException, ParseException;
}
