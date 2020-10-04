package org.fininfo.elbazar.web.rest.vm;

import org.fininfo.elbazar.domain.Client;
import org.fininfo.elbazar.domain.enumeration.Civilite;
import org.fininfo.elbazar.domain.enumeration.ProfileClient;
import org.fininfo.elbazar.domain.enumeration.RegMod;
import org.fininfo.elbazar.service.dto.UserDTO;

import java.time.LocalDate;

import javax.validation.constraints.Size;

/**
 * View Model extending the UserDTO, which is meant to be used in the user management UI.
 */
public class ManagedUserVM extends UserDTO {

    public static final int PASSWORD_MIN_LENGTH = 4;

    public static final int PASSWORD_MAX_LENGTH = 100;

    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
    private String password;

    // FININFO CODE : Client Attributes (Getters and Setters are below)  
	private Civilite civilite;
    private String prenom;
    private String nom;
    private LocalDate dateDeNaissance;
    private String email;
    private Integer mobile;
    private RegMod reglement;
	private ProfileClient profile;
	private LocalDate inscription;
	private LocalDate creeLe;
	private String creePar;
	private LocalDate modifieLe;
	private String modifiePar;
	private Boolean etat;


   
    // FININFO CODE : Adresse Attributes (Getters and Setters are below)  
    private Boolean principale;
	private String adresse;
    private String gouvernorat;
    private String ville;
    private String localite;
    private String indication;
	private Client client;

    public ManagedUserVM() {
        // Empty constructor needed for Jackson.
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ManagedUserVM{" + super.toString() + "} ";
    }
    
	public Civilite getCivilite() {
		return civilite;
	}

	public void setCivilite(Civilite civilite) {
		this.civilite = civilite;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public LocalDate getDateDeNaissance() {
		return dateDeNaissance;
	}

	public void setDateDeNaissance(LocalDate dateDeNaissance) {
		this.dateDeNaissance = dateDeNaissance;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getMobile() {
		return mobile;
	}

	public void setMobile(Integer mobile) {
		this.mobile = mobile;
	}

	public RegMod getReglement() {
		return reglement;
	}

	public void setReglement(RegMod reglement) {
		this.reglement = reglement;
	}

	public ProfileClient getProfile() {
		return profile;
	}

	public void setProfile(ProfileClient profile) {
		this.profile = profile;
	}
	
    public Boolean getPrincipale() {
		return principale;
	}

	public void setPrincipale(Boolean principale) {
		this.principale = principale;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getGouvernorat() {
		return gouvernorat;
	}

	public void setGouvernorat(String gouvernorat) {
		this.gouvernorat = gouvernorat;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public String getLocalite() {
		return localite;
	}

	public void setLocalite(String localite) {
		this.localite = localite;
	}
	
    public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public String getIndication() {
		return indication;
	}

	public void setIndication(String indication) {
		this.indication = indication;
	}

	public LocalDate getInscription() {
		return inscription;
	}

	public void setInscription(LocalDate inscription) {
		this.inscription = inscription;
	}

	public LocalDate getCreeLe() {
		return creeLe;
	}

	public void setCreeLe(LocalDate creeLe) {
		this.creeLe = creeLe;
	}

	public String getCreePar() {
		return creePar;
	}

	public void setCreePar(String creePar) {
		this.creePar = creePar;
	}

	public LocalDate getModifieLe() {
		return modifieLe;
	}

	public void setModifieLe(LocalDate modifieLe) {
		this.modifieLe = modifieLe;
	}

	public String getModifiePar() {
		return modifiePar;
	}

	public void setModifiePar(String modifiePar) {
		this.modifiePar = modifiePar;
	}

	public Boolean getEtat() {
		return etat;
	}

	public void setEtat(Boolean etat) {
		this.etat = etat;
	}

}
