package fr.reugest.models.light;

import java.util.Date;

import fr.thomas.orm.annotations.Column;
import fr.thomas.orm.annotations.PrimaryKey;
import fr.thomas.orm.annotations.Table;

/**
 *
 * @author tpeyr
 *
 */
@Table(name = "reunion")
public class ReunionLight {

    @PrimaryKey
    @Column(name = "id")
    private Long id;

    @Column(name = "debut")
    private Date debut;

    @Column(name = "fin")
    private Date fin;

    @Column(name = "salle")
    private Long idSalle;
    
    @Column(name = "isValid")
    private Boolean isValid;
    
    @Column(name = "motif")
    private String motif;

    public ReunionLight(Long id, Date debut, Date fin, Long idSalle, Boolean isValid, String motif) {
        this.id = id;
        this.debut = debut;
        this.fin = fin;
        this.idSalle = idSalle;
        this.isValid = isValid;
        this.motif = motif;
    }

    
    
    public ReunionLight() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDebut() {
        return debut;
    }

    public void setDebut(Date debut) {
        this.debut = debut;
    }

    public Date getFin() {
        return fin;
    }

    public void setFin(Date fin) {
        this.fin = fin;
    }

    public Long getIdSalle() {
        return idSalle;
    }

    public void setIdSalle(Long idSalle) {
        this.idSalle = idSalle;
    }

    public Boolean getIsValid() {
        return isValid;
    }

    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

}
