package org.fininfo.elbazar.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import org.fininfo.elbazar.domain.enumeration.Civilite;
import org.fininfo.elbazar.domain.enumeration.RegMod;
import org.fininfo.elbazar.domain.enumeration.ProfileClient;

/**
 * A DTO for the {@link org.fininfo.elbazar.domain.Client} entity.
 */
public class ClientDTO implements Serializable {
    
    private Long id;

    @NotNull
    private Civilite civilite;

    @NotNull
    private String prenom;

    @NotNull
    private String nom;

    @NotNull
    private LocalDate dateDeNaissance;

    @NotNull
    @Pattern(regexp = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$")
    private String email;

    @NotNull
    private Integer mobile;

    @NotNull
    private RegMod reglement;

    private Boolean etat;

    private LocalDate inscription;

    private LocalDate derniereVisite;

    private Double totalAchat;

    @NotNull
    private ProfileClient profile;

    private Integer pointsFidelite;

    private LocalDate creeLe;

    private String creePar;

    private LocalDate modifieLe;

    private String modifiePar;


    private Long userId;

    private String userLogin;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Boolean isEtat() {
        return etat;
    }

    public void setEtat(Boolean etat) {
        this.etat = etat;
    }

    public LocalDate getInscription() {
        return inscription;
    }

    public void setInscription(LocalDate inscription) {
        this.inscription = inscription;
    }

    public LocalDate getDerniereVisite() {
        return derniereVisite;
    }

    public void setDerniereVisite(LocalDate derniereVisite) {
        this.derniereVisite = derniereVisite;
    }

    public Double getTotalAchat() {
        return totalAchat;
    }

    public void setTotalAchat(Double totalAchat) {
        this.totalAchat = totalAchat;
    }

    public ProfileClient getProfile() {
        return profile;
    }

    public void setProfile(ProfileClient profile) {
        this.profile = profile;
    }

    public Integer getPointsFidelite() {
        return pointsFidelite;
    }

    public void setPointsFidelite(Integer pointsFidelite) {
        this.pointsFidelite = pointsFidelite;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClientDTO)) {
            return false;
        }

        return id != null && id.equals(((ClientDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClientDTO{" +
            "id=" + getId() +
            ", civilite='" + getCivilite() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", nom='" + getNom() + "'" +
            ", dateDeNaissance='" + getDateDeNaissance() + "'" +
            ", email='" + getEmail() + "'" +
            ", mobile=" + getMobile() +
            ", reglement='" + getReglement() + "'" +
            ", etat='" + isEtat() + "'" +
            ", inscription='" + getInscription() + "'" +
            ", derniereVisite='" + getDerniereVisite() + "'" +
            ", totalAchat=" + getTotalAchat() +
            ", profile='" + getProfile() + "'" +
            ", pointsFidelite=" + getPointsFidelite() +
            ", creeLe='" + getCreeLe() + "'" +
            ", creePar='" + getCreePar() + "'" +
            ", modifieLe='" + getModifieLe() + "'" +
            ", modifiePar='" + getModifiePar() + "'" +
            ", userId=" + getUserId() +
            ", userLogin='" + getUserLogin() + "'" +
            "}";
    }
}
