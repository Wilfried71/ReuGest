package fr.reugest.models.light;

import fr.thomas.orm.annotations.Column;
import fr.thomas.orm.annotations.PrimaryKey;
import fr.thomas.orm.annotations.Table;

/**
 *
 * @author tpeyr
 *
 */
@Table(name = "affectation")
public class AffectationLight {

    @PrimaryKey
    @Column(name = "salle")
    private Long idSalle;
    
    @PrimaryKey
    @Column(name = "equipement")
    private Long idEquipement;

    

    public AffectationLight(Long equipement, Long salle) {
        super();
        this.idEquipement = equipement;
        this.idSalle = salle;
    }

    public AffectationLight() {
        super();
    }

    public Long getEquipement() {
        return idEquipement;
    }

    public void setEquipement(Long equipement) {
        this.idEquipement = equipement;
    }

    public Long getSalle() {
        return idSalle;
    }

    public void setSalle(Long salle) {
        this.idSalle = salle;
    }

}
