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
@Table(name = "concerner")
public class Concerner {

	@PrimaryKey
	@Column(name = "utilisateur")
	@ForeignKey(field = "id", table = "utilisateur")
	private Utilisateur utilisateur;
	
	@PrimaryKey
	@Column(name = "reunion")
	@ForeignKey(field = "id", table = "reunion")
	private Reunion reunion;

	protected Concerner(Utilisateur utilisateur, Reunion reunion) {
		super();
		this.utilisateur = utilisateur;
		this.reunion = reunion;
	}

	protected Concerner() {
		super();
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public Reunion getReunion() {
		return reunion;
	}

	public void setReunion(Reunion reunion) {
		this.reunion = reunion;
	}
	
	
}
