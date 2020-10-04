package org.fininfo.elbazar.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link org.fininfo.elbazar.domain.InfoSociete} entity.
 */
public class InfoSocieteDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String nomSociete;

    @NotNull
    private String adresse;

    @NotNull
    private Integer tel1;

    @NotNull
    private Integer tel2;

    @NotNull
    private Integer tel3;

    private String email1;

    private String email2;

    private LocalDate creeLe;

    private String creePar;

    private LocalDate modifieLe;

    private String modifiePar;

    private String facebook;

    private String youtube;

    private String instagram;

    private String twitter;

    private String tiktok;

    private String matriculeFiscal;

    @NotNull
    private Double valeurMinPanier;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomSociete() {
        return nomSociete;
    }

    public void setNomSociete(String nomSociete) {
        this.nomSociete = nomSociete;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Integer getTel1() {
        return tel1;
    }

    public void setTel1(Integer tel1) {
        this.tel1 = tel1;
    }

    public Integer getTel2() {
        return tel2;
    }

    public void setTel2(Integer tel2) {
        this.tel2 = tel2;
    }

    public Integer getTel3() {
        return tel3;
    }

    public void setTel3(Integer tel3) {
        this.tel3 = tel3;
    }

    public String getEmail1() {
        return email1;
    }

    public void setEmail1(String email1) {
        this.email1 = email1;
    }

    public String getEmail2() {
        return email2;
    }

    public void setEmail2(String email2) {
        this.email2 = email2;
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

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getYoutube() {
        return youtube;
    }

    public void setYoutube(String youtube) {
        this.youtube = youtube;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getTiktok() {
        return tiktok;
    }

    public void setTiktok(String tiktok) {
        this.tiktok = tiktok;
    }

    public String getMatriculeFiscal() {
        return matriculeFiscal;
    }

    public void setMatriculeFiscal(String matriculeFiscal) {
        this.matriculeFiscal = matriculeFiscal;
    }

    public Double getValeurMinPanier() {
        return valeurMinPanier;
    }

    public void setValeurMinPanier(Double valeurMinPanier) {
        this.valeurMinPanier = valeurMinPanier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InfoSocieteDTO)) {
            return false;
        }

        return id != null && id.equals(((InfoSocieteDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InfoSocieteDTO{" +
            "id=" + getId() +
            ", nomSociete='" + getNomSociete() + "'" +
            ", adresse='" + getAdresse() + "'" +
            ", tel1=" + getTel1() +
            ", tel2=" + getTel2() +
            ", tel3=" + getTel3() +
            ", email1='" + getEmail1() + "'" +
            ", email2='" + getEmail2() + "'" +
            ", creeLe='" + getCreeLe() + "'" +
            ", creePar='" + getCreePar() + "'" +
            ", modifieLe='" + getModifieLe() + "'" +
            ", modifiePar='" + getModifiePar() + "'" +
            ", facebook='" + getFacebook() + "'" +
            ", youtube='" + getYoutube() + "'" +
            ", instagram='" + getInstagram() + "'" +
            ", twitter='" + getTwitter() + "'" +
            ", tiktok='" + getTiktok() + "'" +
            ", matriculeFiscal='" + getMatriculeFiscal() + "'" +
            ", valeurMinPanier=" + getValeurMinPanier() +
            "}";
    }
}
