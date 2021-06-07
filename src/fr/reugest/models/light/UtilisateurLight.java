package fr.reugest.models.light;

import fr.thomas.orm.annotations.Column;
import fr.thomas.orm.annotations.PrimaryKey;
import fr.thomas.orm.annotations.Table;

@Table(name = "utilisateur")
public class UtilisateurLight {

    @PrimaryKey
    @Column(name = "id")
    private Long id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "prenom")
    private String prenom;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "droit")
    private Long idDroit;

    @Column(name = "service")
    private Long idService;

    @Column(name = "fonction")
    private Long idFonction;
    
    @Column(name = "isDeleted")
    private Boolean isDeleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getIdDroit() {
        return idDroit;
    }

    public void setIdDroit(Long idDroit) {
        this.idDroit = idDroit;
    }

    public Long getIdService() {
        return idService;
    }

    public void setIdService(Long idService) {
        this.idService = idService;
    }

    public Long getIdFonction() {
        return idFonction;
    }

    public void setIdFonction(Long idFonction) {
        this.idFonction = idFonction;
    }
    
    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public UtilisateurLight() {
        super();
    }

    public UtilisateurLight(Long id, String nom, String prenom, String email, String password, Long idDroit,
            Long idService, Long idFonction, Boolean isDeleted) {
        super();
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.idDroit = idDroit;
        this.idService = idService;
        this.idFonction = idFonction;
        this.isDeleted = isDeleted;
    }
}
