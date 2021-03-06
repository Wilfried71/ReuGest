package fr.reugest.models;

import java.util.Date;

import fr.thomas.orm.annotations.Column;
import fr.thomas.orm.annotations.ForeignKey;
import fr.thomas.orm.annotations.PrimaryKey;
import fr.thomas.orm.annotations.Table;

/**
 *
 * @author tpeyr
 *
 */
@Table(name = "reunion")
public class Reunion {

    @PrimaryKey
    @Column(name = "id")
    private Long id;

    @Column(name = "debut")
    private Date debut;

    @Column(name = "fin")
    private Date fin;

    @ForeignKey(field = "id", table = "salle")
    @Column(name = "salle")
    private Salle salle;
    
    @Column(name = "isValid")
    private Boolean isValid;
    
    @Column(name = "motif")
    private String motif;

    public Reunion(Long id, Date debut, Date fin, Salle salle, Boolean isValid, String motif) {
        this.id = id;
        this.debut = debut;
        this.fin = fin;
        this.salle = salle;
        this.isValid = isValid;
        this.motif = motif;
    }

    public Reunion() {
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

    public Salle getSalle() {
        return salle;
    }

    public void setSalle(Salle salle) {
        this.salle = salle;
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
