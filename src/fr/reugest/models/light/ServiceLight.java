package fr.reugest.models.light;

import fr.thomas.orm.annotations.Column;
import fr.thomas.orm.annotations.PrimaryKey;
import fr.thomas.orm.annotations.Table;

@Table(name = "Service")
public class ServiceLight {
	
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

	public ServiceLight(Long id, String libelle) {
		super();
		this.id = id;
		this.libelle = libelle;
	}

	public ServiceLight() {
		super();
	}
	
	
}
