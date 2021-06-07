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

    public ReunionLight(Long id, Date debut, Date fin, Long salle) {
        super();
        this.id = id;
        this.debut = debut;
        this.fin = fin;
        this.idSalle = salle;
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

    public Long getSalle() {
        return idSalle;
    }

    public void setSalle(Long salle) {
        this.idSalle = salle;
    }

}
