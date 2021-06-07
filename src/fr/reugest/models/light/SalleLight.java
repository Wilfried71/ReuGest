package fr.reugest.models.light;

import fr.thomas.orm.annotations.Column;
import fr.thomas.orm.annotations.PrimaryKey;
import fr.thomas.orm.annotations.Table;

/**
 *
 * @author tpeyr
 *
 */
@Table(name = "salle")
public class SalleLight {

    @PrimaryKey
    @Column(name = "id")
    private Long id;

    @Column(name = "libelle")
    private String libelle;

    @Column(name = "places")
    private Long places;

    public SalleLight(Long id, String libelle, Long places) {
        super();
        this.id = id;
        this.libelle = libelle;
        this.places = places;
    }

    public SalleLight() {
        super();
    }

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

    public Long getPlaces() {
        return places;
    }

    public void setPlaces(Long places) {
        this.places = places;
    }

}
