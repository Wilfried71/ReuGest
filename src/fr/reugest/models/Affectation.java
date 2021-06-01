package fr.reugest.models;

import fr.thomas.orm.annotations.Column;
import fr.thomas.orm.annotations.ForeignKey;
import fr.thomas.orm.annotations.PrimaryKey;
import fr.thomas.orm.annotations.Table;

/**
 * 
 * @author tpeyr
 *
 */
@Table(name = "affectation")
public class Affectation {

	@PrimaryKey
	@Column(name = "equipement")
	@ForeignKey(field = "id", table = "equipement")
	private Equipement equipement;
	
	@PrimaryKey
	@Column(name = "salle")
	@ForeignKey(field = "id", table = "salle")
	private Salle salle;

	protected Affectation(Equipement equipement, Salle salle) {
		super();
		this.equipement = equipement;
		this.salle = salle;
	}

	protected Affectation() {
		super();
	}

	public Equipement getEquipement() {
		return equipement;
	}

	public void setEquipement(Equipement equipement) {
		this.equipement = equipement;
	}

	public Salle getSalle() {
		return salle;
	}

	public void setSalle(Salle salle) {
		this.salle = salle;
	}
	
	
}
