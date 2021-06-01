package fr.reugest.models.light;

import fr.thomas.orm.annotations.Column;
import fr.thomas.orm.annotations.PrimaryKey;
import fr.thomas.orm.annotations.Table;

/**
 * 
 * @author tpeyr
 *
 */
@Table(name = "concerner")
public class ConcernerLight {

	@PrimaryKey
	@Column(name = "utilisateur")
	private Long utilisateur;
	
	@PrimaryKey
	@Column(name = "reunion")
	private Long reunion;

	

	protected ConcernerLight(Long utilisateur, Long reunion) {
		super();
		this.utilisateur = utilisateur;
		this.reunion = reunion;
	}



	protected ConcernerLight() {
		super();
	}



	public Long getUtilisateur() {
		return utilisateur;
	}



	public void setUtilisateur(Long utilisateur) {
		this.utilisateur = utilisateur;
	}



	public Long getReunion() {
		return reunion;
	}



	public void setReunion(Long reunion) {
		this.reunion = reunion;
	}

	
	
}
