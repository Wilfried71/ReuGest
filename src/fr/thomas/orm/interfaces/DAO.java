package fr.thomas.orm.interfaces;

import fr.thomas.orm.annotations.PrimaryKey;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

/**
 * Interface de base, qui contient les definitions des methodes CRUD.
 *
 * @author tpeyr
 *
 * @param <T> Type of the entity the will be linked to the database.
 */
public interface DAO<T> {

    /**
     * Create an entity in the database.<br>
     * The {@link PrimaryKey} must not be given, except for multi-pk entities.
     * 
     * @param object object that will be created.
     * @return Created object
     * @throws Exception because some exceptions can occure during transaction.
     */
    T create(T object) throws Exception;

    /**
     * Update an entity in the database.<br>
     *
     * @param object entity that will be updated.
     * @return Updated entity.
     * @throws Exception because some exceptions can occure during transaction.
     */
    T update(T object) throws Exception;

    /**
     * Delete an entity in the database.<br>
     * Only {@link PrimaryKey} field is used in this method.
     *
     * @param object object that will be deleted
     * @throws Exception because some exceptions can occure during transaction.
     */
    void delete(T object) throws Exception;

    /**
     * Delete an object by its {@link PrimaryKey}.<br>
     * If there are more than one {@link PrimaryKey}, use the first one.
     *
     * @param id id of the object that will be deleted.
     * @throws Exception because some exceptions can occure during transaction.
     */
    void deleteById(Long id) throws Exception;

    /**
     *
     * @return all entities of a table.
     * @throws SQLException because some exceptions can occure during transaction.
     * @throws SecurityException because some exceptions can occure during transaction.
     * @throws NoSuchMethodException because some exceptions can occure during transaction.
     * @throws InvocationTargetException because some exceptions can occure during transaction.
     * @throws InstantiationException because some exceptions can occure during transaction.
     * @throws IllegalAccessException because some exceptions can occure during transaction.
     * @throws IllegalArgumentException because some exceptions can occure during transaction.
     * @throws ParseException because some exceptions can occure during transaction.
     */
    List<T> findAll() throws SQLException, IllegalArgumentException, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException, SecurityException, ParseException;

    /**
     * Realize a SELECT SQL request as a prepared statement.
     *
     * @param query executed query.
     * @param fields List of values for prepared statement.
     * @return the result of the query.
     * @throws Exception because some exceptions can occure during transaction.
     */
    List<T> query(String query, List<?> fields) throws Exception;

    /**
     * @param id Id of the entity
     * @return the entity with this id, or null if entity is not found/
     * @throws SQLException because some exceptions can occure during transaction.
     * @throws ParseException because some exceptions can occure during transaction.
     * @throws SecurityException because some exceptions can occure during transaction.
     * @throws NoSuchMethodException because some exceptions can occure during transaction.
     * @throws InvocationTargetException because some exceptions can occure during transaction.
     * @throws InstantiationException because some exceptions can occure during transaction.
     * @throws IllegalAccessException because some exceptions can occure during transaction.
     * @throws IllegalArgumentException because some exceptions can occure during transaction.
     */
    T findById(Long id) throws SQLException, IllegalArgumentException, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException, SecurityException, ParseException;
}
