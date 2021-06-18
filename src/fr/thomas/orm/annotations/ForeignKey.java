package fr.thomas.orm.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Represent a foreign key constraint
 *
 * @author tpeyr
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ForeignKey {

    /**
     * Name of the foreign table in the database.
     * @return the name of the table.
     */
    String table();

    /**
     * Name of the primary key field in the foreign table in database.
     * @return the name of the field.
     */
    String field();
}
