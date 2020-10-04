package org.fininfo.elbazar.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import org.fininfo.elbazar.domain.enumeration.StatCmd;
import org.fininfo.elbazar.domain.enumeration.Origine;
import org.fininfo.elbazar.domain.enumeration.Devise;
import org.fininfo.elbazar.domain.enumeration.RegMod;

/**
 * A DTO for the {@link org.fininfo.elbazar.domain.Commande} entity.
 */
public class CommandeDTO implements Serializable {
    
    private Long id;

    @Pattern(regexp = "^[a-zA-Z0-9]{0,12}$")
    private String reference;

    private LocalDate date;

    private StatCmd statut;

    private Origine origine;

    private Double totalHT;

    private Double totalTVA;

    private Double totalRemise;

    private Double totTTC;

    private Devise devise;

    private Integer pointsFidelite;

    private RegMod reglement;

    private LocalDate dateLivraison;

    private LocalDate dateCreation;

    private LocalDate dateAnnulation;

    private LocalDate creeLe;

    private String creePar;

    private LocalDate modifieLe;

    private String modifiePar;

    
    private String nomClient;
    
    private String prenomClient;

    private String prenom;

    private String nom;

    private String adresse;

    private String gouvernorat;

    private String ville;

    private String localite;

    private Integer codePostal;

    private String indication;

    private Integer telephone;

    private Integer mobile;

    private Double fraisLivraison;

    private Long idAdresseId;
    private Long idClientId;

    

    private Long zoneId;

    private String zoneNom;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public StatCmd getStatut() {
        return statut;
    }

    public void setStatut(StatCmd statut) {
        this.statut = statut;
    }

    public Origine getOrigine() {
        return origine;
    }

    public void setOrigine(Origine origine) {
        this.origine = origine;
    }

    public Double getTotalHT() {
        return totalHT;
    }

    public void setTotalHT(Double totalHT) {
        this.totalHT = totalHT;
    }

    public Double getTotalTVA() {
        return totalTVA;
    }

    public void setTotalTVA(Double totalTVA) {
        this.totalTVA = totalTVA;
    }

    public Double getTotalRemise() {
        return totalRemise;
    }

    public void setTotalRemise(Double totalRemise) {
        this.totalRemise = totalRemise;
    }

    public Double getTotTTC() {
        return totTTC;
    }

    public void setTotTTC(Double totTTC) {
        this.totTTC = totTTC;
    }

    public Devise getDevise() {
        return devise;
    }

    public void setDevise(Devise devise) {
        this.devise = devise;
    }

    public Integer getPointsFidelite() {
        return pointsFidelite;
    }

    public void setPointsFidelite(Integer pointsFidelite) {
        this.pointsFidelite = pointsFidelite;
    }

    public RegMod getReglement() {
        return reglement;
    }

    public void setReglement(RegMod reglement) {
        this.reglement = reglement;
    }

    public LocalDate getDateLivraison() {
        return dateLivraison;
    }

    public void setDateLivraison(LocalDate dateLivraison) {
        this.dateLivraison = dateLivraison;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public LocalDate getDateAnnulation() {
        return dateAnnulation;
    }

    public void setDateAnnulation(LocalDate dateAnnulation) {
        this.dateAnnulation = dateAnnulation;
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

    public Integer getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(Integer codePostal) {
        this.codePostal = codePostal;
    }

    public String getIndication() {
        return indication;
    }

    public void setIndication(String indication) {
        this.indication = indication;
    }

    public Integer getTelephone() {
        return telephone;
    }

    public void setTelephone(Integer telephone) {
        this.telephone = telephone;
    }

    public Integer getMobile() {
        return mobile;
    }

    public void setMobile(Integer mobile) {
        this.mobile = mobile;
    }

    public Double getFraisLivraison() {
        return fraisLivraison;
    }

    public void setFraisLivraison(Double fraisLivraison) {
        this.fraisLivraison = fraisLivraison;
    }

    public Long getIdClientId() {
        return idClientId;
    }

    public void setIdClientId(Long clientId) {
        this.idClientId = clientId;
    }

    public Long getIdAdresseId() {
        return idAdresseId;
    }

    public void setIdAdresseId(Long adresseId) {
        this.idAdresseId = adresseId;
    }

    public Long getZoneId() {
        return zoneId;
    }

    public void setZoneId(Long zoneId) {
        this.zoneId = zoneId;
    }

    public String getZoneNom() {
        return zoneNom;
    }

    public void setZoneNom(String zoneNom) {
        this.zoneNom = zoneNom;
    }
    
    

    public String getNomClient() {
		return nomClient;
	}

	public void setNomClient(String nomClient) {
		this.nomClient = nomClient;
	}

	public String getPrenomClient() {
		return prenomClient;
	}

	public void setPrenomClient(String prenomClient) {
		this.prenomClient = prenomClient;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CommandeDTO)) {
            return false;
        }

        return id != null && id.equals(((CommandeDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CommandeDTO{" +
            "id=" + getId() +
            ", reference='" + getReference() + "'" +
            ", date='" + getDate() + "'" +
            ", statut='" + getStatut() + "'" +
            ", origine='" + getOrigine() + "'" +
            ", totalHT=" + getTotalHT() +
            ", totalTVA=" + getTotalTVA() +
            ", totalRemise=" + getTotalRemise() +
            ", totTTC=" + getTotTTC() +
            ", devise='" + getDevise() + "'" +
            ", pointsFidelite=" + getPointsFidelite() +
            ", reglement='" + getReglement() + "'" +
            ", dateLivraison='" + getDateLivraison() + "'" +
            ", dateCreation='" + getDateCreation() + "'" +
            ", dateAnnulation='" + getDateAnnulation() + "'" +
            ", creeLe='" + getCreeLe() + "'" +
            ", creePar='" + getCreePar() + "'" +
            ", modifieLe='" + getModifieLe() + "'" +
            ", modifiePar='" + getModifiePar() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", nom='" + getNom() + "'" +
            ", adresse='" + getAdresse() + "'" +
            ", gouvernorat='" + getGouvernorat() + "'" +
            ", ville='" + getVille() + "'" +
            ", localite='" + getLocalite() + "'" +
            ", codePostal=" + getCodePostal() +
            ", indication='" + getIndication() + "'" +
            ", telephone=" + getTelephone() +
            ", mobile=" + getMobile() +
            ", fraisLivraison=" + getFraisLivraison() +
            ", idClientId=" + getIdClientId() +
            ", nomClient=" + getNomClient() +
            ", prenomClient=" + getPrenomClient() +
            ", idAdresseId=" + getIdAdresseId() +
            ", zoneId=" + getZoneId() +
            ", zoneNom='" + getZoneNom() + "'" +
            "}";
    }
}
