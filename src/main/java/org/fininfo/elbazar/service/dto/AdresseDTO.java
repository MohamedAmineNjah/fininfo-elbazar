package org.fininfo.elbazar.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link org.fininfo.elbazar.domain.Adresse} entity.
 */
public class AdresseDTO implements Serializable {
    
    private Long id;

    private Boolean principale;

    @NotNull
    private String prenom;

    @NotNull
    private String nom;

    @NotNull
    private String adresse;

    @NotNull
    private String gouvernorat;

    @NotNull
    private String ville;

    @NotNull
    private String localite;

    private String indication;

    private Integer telephone;

    @NotNull
    private Integer mobile;

    private LocalDate creeLe;

    private String creePar;

    private LocalDate modifieLe;

    private String modifiePar;


    private Long clientId;

    private Long zoneId;

    private String zoneNom;

    private Long codePostalId;

    private String codePostalCodePostal;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isPrincipale() {
        return principale;
    }

    public void setPrincipale(Boolean principale) {
        this.principale = principale;
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

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
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

    public Long getCodePostalId() {
        return codePostalId;
    }

    public void setCodePostalId(Long affectationZoneId) {
        this.codePostalId = affectationZoneId;
    }

    public String getCodePostalCodePostal() {
        return codePostalCodePostal;
    }

    public void setCodePostalCodePostal(String affectationZoneCodePostal) {
        this.codePostalCodePostal = affectationZoneCodePostal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdresseDTO)) {
            return false;
        }

        return id != null && id.equals(((AdresseDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdresseDTO{" +
            "id=" + getId() +
            ", principale='" + isPrincipale() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", nom='" + getNom() + "'" +
            ", adresse='" + getAdresse() + "'" +
            ", gouvernorat='" + getGouvernorat() + "'" +
            ", ville='" + getVille() + "'" +
            ", localite='" + getLocalite() + "'" +
            ", indication='" + getIndication() + "'" +
            ", telephone=" + getTelephone() +
            ", mobile=" + getMobile() +
            ", creeLe='" + getCreeLe() + "'" +
            ", creePar='" + getCreePar() + "'" +
            ", modifieLe='" + getModifieLe() + "'" +
            ", modifiePar='" + getModifiePar() + "'" +
            ", clientId=" + getClientId() +
            ", zoneId=" + getZoneId() +
            ", zoneNom='" + getZoneNom() + "'" +
            ", codePostalId=" + getCodePostalId() +
            ", codePostalCodePostal='" + getCodePostalCodePostal() + "'" +
            "}";
    }
}
