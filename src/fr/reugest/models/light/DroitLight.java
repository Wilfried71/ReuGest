package fr.reugest.models.light;

import fr.thomas.orm.annotations.Column;
import fr.thomas.orm.annotations.PrimaryKey;
import fr.thomas.orm.annotations.Table;

/**
 * Light entity of {@link fr.reugest.models.Droit}.
 *
 * @author tpeyr
 */
@Table(name = "droit")
public class DroitLight {

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

    public DroitLight(Long id, String libelle) {
        super();
        this.id = id;
        this.libelle = libelle;
    }

    public DroitLight() {
        super();
    }

}
