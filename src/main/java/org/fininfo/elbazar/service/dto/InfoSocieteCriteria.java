package org.fininfo.elbazar.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link org.fininfo.elbazar.domain.InfoSociete} entity. This class is used
 * in {@link org.fininfo.elbazar.web.rest.InfoSocieteResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /info-societes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class InfoSocieteCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nomSociete;

    private StringFilter adresse;

    private IntegerFilter tel1;

    private IntegerFilter tel2;

    private IntegerFilter tel3;

    private StringFilter email1;

    private StringFilter email2;

    private LocalDateFilter creeLe;

    private StringFilter creePar;

    private LocalDateFilter modifieLe;

    private StringFilter modifiePar;

    private StringFilter facebook;

    private StringFilter youtube;

    private StringFilter instagram;

    private StringFilter twitter;

    private StringFilter tiktok;

    private StringFilter matriculeFiscal;

    private DoubleFilter valeurMinPanier;

    public InfoSocieteCriteria() {
    }

    public InfoSocieteCriteria(InfoSocieteCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nomSociete = other.nomSociete == null ? null : other.nomSociete.copy();
        this.adresse = other.adresse == null ? null : other.adresse.copy();
        this.tel1 = other.tel1 == null ? null : other.tel1.copy();
        this.tel2 = other.tel2 == null ? null : other.tel2.copy();
        this.tel3 = other.tel3 == null ? null : other.tel3.copy();
        this.email1 = other.email1 == null ? null : other.email1.copy();
        this.email2 = other.email2 == null ? null : other.email2.copy();
        this.creeLe = other.creeLe == null ? null : other.creeLe.copy();
        this.creePar = other.creePar == null ? null : other.creePar.copy();
        this.modifieLe = other.modifieLe == null ? null : other.modifieLe.copy();
        this.modifiePar = other.modifiePar == null ? null : other.modifiePar.copy();
        this.facebook = other.facebook == null ? null : other.facebook.copy();
        this.youtube = other.youtube == null ? null : other.youtube.copy();
        this.instagram = other.instagram == null ? null : other.instagram.copy();
        this.twitter = other.twitter == null ? null : other.twitter.copy();
        this.tiktok = other.tiktok == null ? null : other.tiktok.copy();
        this.matriculeFiscal = other.matriculeFiscal == null ? null : other.matriculeFiscal.copy();
        this.valeurMinPanier = other.valeurMinPanier == null ? null : other.valeurMinPanier.copy();
    }

    @Override
    public InfoSocieteCriteria copy() {
        return new InfoSocieteCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNomSociete() {
        return nomSociete;
    }

    public void setNomSociete(StringFilter nomSociete) {
        this.nomSociete = nomSociete;
    }

    public StringFilter getAdresse() {
        return adresse;
    }

    public void setAdresse(StringFilter adresse) {
        this.adresse = adresse;
    }

    public IntegerFilter getTel1() {
        return tel1;
    }

    public void setTel1(IntegerFilter tel1) {
        this.tel1 = tel1;
    }

    public IntegerFilter getTel2() {
        return tel2;
    }

    public void setTel2(IntegerFilter tel2) {
        this.tel2 = tel2;
    }

    public IntegerFilter getTel3() {
        return tel3;
    }

    public void setTel3(IntegerFilter tel3) {
        this.tel3 = tel3;
    }

    public StringFilter getEmail1() {
        return email1;
    }

    public void setEmail1(StringFilter email1) {
        this.email1 = email1;
    }

    public StringFilter getEmail2() {
        return email2;
    }

    public void setEmail2(StringFilter email2) {
        this.email2 = email2;
    }

    public LocalDateFilter getCreeLe() {
        return creeLe;
    }

    public void setCreeLe(LocalDateFilter creeLe) {
        this.creeLe = creeLe;
    }

    public StringFilter getCreePar() {
        return creePar;
    }

    public void setCreePar(StringFilter creePar) {
        this.creePar = creePar;
    }

    public LocalDateFilter getModifieLe() {
        return modifieLe;
    }

    public void setModifieLe(LocalDateFilter modifieLe) {
        this.modifieLe = modifieLe;
    }

    public StringFilter getModifiePar() {
        return modifiePar;
    }

    public void setModifiePar(StringFilter modifiePar) {
        this.modifiePar = modifiePar;
    }

    public StringFilter getFacebook() {
        return facebook;
    }

    public void setFacebook(StringFilter facebook) {
        this.facebook = facebook;
    }

    public StringFilter getYoutube() {
        return youtube;
    }

    public void setYoutube(StringFilter youtube) {
        this.youtube = youtube;
    }

    public StringFilter getInstagram() {
        return instagram;
    }

    public void setInstagram(StringFilter instagram) {
        this.instagram = instagram;
    }

    public StringFilter getTwitter() {
        return twitter;
    }

    public void setTwitter(StringFilter twitter) {
        this.twitter = twitter;
    }

    public StringFilter getTiktok() {
        return tiktok;
    }

    public void setTiktok(StringFilter tiktok) {
        this.tiktok = tiktok;
    }

    public StringFilter getMatriculeFiscal() {
        return matriculeFiscal;
    }

    public void setMatriculeFiscal(StringFilter matriculeFiscal) {
        this.matriculeFiscal = matriculeFiscal;
    }

    public DoubleFilter getValeurMinPanier() {
        return valeurMinPanier;
    }

    public void setValeurMinPanier(DoubleFilter valeurMinPanier) {
        this.valeurMinPanier = valeurMinPanier;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final InfoSocieteCriteria that = (InfoSocieteCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nomSociete, that.nomSociete) &&
            Objects.equals(adresse, that.adresse) &&
            Objects.equals(tel1, that.tel1) &&
            Objects.equals(tel2, that.tel2) &&
            Objects.equals(tel3, that.tel3) &&
            Objects.equals(email1, that.email1) &&
            Objects.equals(email2, that.email2) &&
            Objects.equals(creeLe, that.creeLe) &&
            Objects.equals(creePar, that.creePar) &&
            Objects.equals(modifieLe, that.modifieLe) &&
            Objects.equals(modifiePar, that.modifiePar) &&
            Objects.equals(facebook, that.facebook) &&
            Objects.equals(youtube, that.youtube) &&
            Objects.equals(instagram, that.instagram) &&
            Objects.equals(twitter, that.twitter) &&
            Objects.equals(tiktok, that.tiktok) &&
            Objects.equals(matriculeFiscal, that.matriculeFiscal) &&
            Objects.equals(valeurMinPanier, that.valeurMinPanier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nomSociete,
        adresse,
        tel1,
        tel2,
        tel3,
        email1,
        email2,
        creeLe,
        creePar,
        modifieLe,
        modifiePar,
        facebook,
        youtube,
        instagram,
        twitter,
        tiktok,
        matriculeFiscal,
        valeurMinPanier
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InfoSocieteCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nomSociete != null ? "nomSociete=" + nomSociete + ", " : "") +
                (adresse != null ? "adresse=" + adresse + ", " : "") +
                (tel1 != null ? "tel1=" + tel1 + ", " : "") +
                (tel2 != null ? "tel2=" + tel2 + ", " : "") +
                (tel3 != null ? "tel3=" + tel3 + ", " : "") +
                (email1 != null ? "email1=" + email1 + ", " : "") +
                (email2 != null ? "email2=" + email2 + ", " : "") +
                (creeLe != null ? "creeLe=" + creeLe + ", " : "") +
                (creePar != null ? "creePar=" + creePar + ", " : "") +
                (modifieLe != null ? "modifieLe=" + modifieLe + ", " : "") +
                (modifiePar != null ? "modifiePar=" + modifiePar + ", " : "") +
                (facebook != null ? "facebook=" + facebook + ", " : "") +
                (youtube != null ? "youtube=" + youtube + ", " : "") +
                (instagram != null ? "instagram=" + instagram + ", " : "") +
                (twitter != null ? "twitter=" + twitter + ", " : "") +
                (tiktok != null ? "tiktok=" + tiktok + ", " : "") +
                (matriculeFiscal != null ? "matriculeFiscal=" + matriculeFiscal + ", " : "") +
                (valeurMinPanier != null ? "valeurMinPanier=" + valeurMinPanier + ", " : "") +
            "}";
    }

}
