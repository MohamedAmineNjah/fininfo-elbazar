package org.fininfo.elbazar.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A InfoSociete.
 */
@Entity
@Table(name = "info_societe")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "infosociete")
public class InfoSociete implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nom_societe", nullable = false)
    private String nomSociete;

    @NotNull
    @Column(name = "adresse", nullable = false)
    private String adresse;

    @NotNull
    @Column(name = "tel_1", nullable = false)
    private Integer tel1;

    @NotNull
    @Column(name = "tel_2", nullable = false)
    private Integer tel2;

    @NotNull
    @Column(name = "tel_3", nullable = false)
    private Integer tel3;

    @Column(name = "email_1")
    private String email1;

    @Column(name = "email_2")
    private String email2;

    @Column(name = "cree_le")
    private LocalDate creeLe;

    @Column(name = "cree_par")
    private String creePar;

    @Column(name = "modifie_le")
    private LocalDate modifieLe;

    @Column(name = "modifie_par")
    private String modifiePar;

    @Column(name = "facebook")
    private String facebook;

    @Column(name = "youtube")
    private String youtube;

    @Column(name = "instagram")
    private String instagram;

    @Column(name = "twitter")
    private String twitter;

    @Column(name = "tiktok")
    private String tiktok;

    @Column(name = "matricule_fiscal")
    private String matriculeFiscal;

    @NotNull
    @Column(name = "valeur_min_panier", nullable = false)
    private Double valeurMinPanier;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomSociete() {
        return nomSociete;
    }

    public InfoSociete nomSociete(String nomSociete) {
        this.nomSociete = nomSociete;
        return this;
    }

    public void setNomSociete(String nomSociete) {
        this.nomSociete = nomSociete;
    }

    public String getAdresse() {
        return adresse;
    }

    public InfoSociete adresse(String adresse) {
        this.adresse = adresse;
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Integer getTel1() {
        return tel1;
    }

    public InfoSociete tel1(Integer tel1) {
        this.tel1 = tel1;
        return this;
    }

    public void setTel1(Integer tel1) {
        this.tel1 = tel1;
    }

    public Integer getTel2() {
        return tel2;
    }

    public InfoSociete tel2(Integer tel2) {
        this.tel2 = tel2;
        return this;
    }

    public void setTel2(Integer tel2) {
        this.tel2 = tel2;
    }

    public Integer getTel3() {
        return tel3;
    }

    public InfoSociete tel3(Integer tel3) {
        this.tel3 = tel3;
        return this;
    }

    public void setTel3(Integer tel3) {
        this.tel3 = tel3;
    }

    public String getEmail1() {
        return email1;
    }

    public InfoSociete email1(String email1) {
        this.email1 = email1;
        return this;
    }

    public void setEmail1(String email1) {
        this.email1 = email1;
    }

    public String getEmail2() {
        return email2;
    }

    public InfoSociete email2(String email2) {
        this.email2 = email2;
        return this;
    }

    public void setEmail2(String email2) {
        this.email2 = email2;
    }

    public LocalDate getCreeLe() {
        return creeLe;
    }

    public InfoSociete creeLe(LocalDate creeLe) {
        this.creeLe = creeLe;
        return this;
    }

    public void setCreeLe(LocalDate creeLe) {
        this.creeLe = creeLe;
    }

    public String getCreePar() {
        return creePar;
    }

    public InfoSociete creePar(String creePar) {
        this.creePar = creePar;
        return this;
    }

    public void setCreePar(String creePar) {
        this.creePar = creePar;
    }

    public LocalDate getModifieLe() {
        return modifieLe;
    }

    public InfoSociete modifieLe(LocalDate modifieLe) {
        this.modifieLe = modifieLe;
        return this;
    }

    public void setModifieLe(LocalDate modifieLe) {
        this.modifieLe = modifieLe;
    }

    public String getModifiePar() {
        return modifiePar;
    }

    public InfoSociete modifiePar(String modifiePar) {
        this.modifiePar = modifiePar;
        return this;
    }

    public void setModifiePar(String modifiePar) {
        this.modifiePar = modifiePar;
    }

    public String getFacebook() {
        return facebook;
    }

    public InfoSociete facebook(String facebook) {
        this.facebook = facebook;
        return this;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getYoutube() {
        return youtube;
    }

    public InfoSociete youtube(String youtube) {
        this.youtube = youtube;
        return this;
    }

    public void setYoutube(String youtube) {
        this.youtube = youtube;
    }

    public String getInstagram() {
        return instagram;
    }

    public InfoSociete instagram(String instagram) {
        this.instagram = instagram;
        return this;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getTwitter() {
        return twitter;
    }

    public InfoSociete twitter(String twitter) {
        this.twitter = twitter;
        return this;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getTiktok() {
        return tiktok;
    }

    public InfoSociete tiktok(String tiktok) {
        this.tiktok = tiktok;
        return this;
    }

    public void setTiktok(String tiktok) {
        this.tiktok = tiktok;
    }

    public String getMatriculeFiscal() {
        return matriculeFiscal;
    }

    public InfoSociete matriculeFiscal(String matriculeFiscal) {
        this.matriculeFiscal = matriculeFiscal;
        return this;
    }

    public void setMatriculeFiscal(String matriculeFiscal) {
        this.matriculeFiscal = matriculeFiscal;
    }

    public Double getValeurMinPanier() {
        return valeurMinPanier;
    }

    public InfoSociete valeurMinPanier(Double valeurMinPanier) {
        this.valeurMinPanier = valeurMinPanier;
        return this;
    }

    public void setValeurMinPanier(Double valeurMinPanier) {
        this.valeurMinPanier = valeurMinPanier;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InfoSociete)) {
            return false;
        }
        return id != null && id.equals(((InfoSociete) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InfoSociete{" +
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
