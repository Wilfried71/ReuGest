package fr.reugest.models;

import fr.thomas.orm.annotations.Column;
import fr.thomas.orm.annotations.PrimaryKey;
import fr.thomas.orm.annotations.Table;

/**
 * Function of {@link Utilisateur} in the society.
 * @author tpeyr
 */
@Table(name = "fonction")
public class Fonction {

    @PrimaryKey
    @Column(name = "id")
    private Long id;

    @Column(name = "libelle")
    private String libelle;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Fonction(Long id, String libelle) {
        super();
        this.id = id;
        this.libelle = libelle;
    }

    public Fonction() {
        super();
    }

    /**
     * Used to display name in comboboxes
     */
    @Override
    public String toString() {
        return this.libelle;
    }
}
