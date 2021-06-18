package fr.reugest.models.light;

import fr.thomas.orm.annotations.Column;
import fr.thomas.orm.annotations.PrimaryKey;
import fr.thomas.orm.annotations.Table;

/**
 * Light entity of {@link fr.reugest.models.Fonction}.
 * @author tpeyr
 */
@Table(name = "fonction")
public class FonctionLight {

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

    public FonctionLight(Long id, String libelle) {
        super();
        this.id = id;
        this.libelle = libelle;
    }

    public FonctionLight() {
        super();
    }
}
