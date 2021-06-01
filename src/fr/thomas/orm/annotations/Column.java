package fr.thomas.orm.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation qui indique que le champ est relie a une colonne en base.
 *
 * @author tpeyr
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Column {

    /**
     * Nom du champ dans la base de donnees.
     */
    String name();
}
