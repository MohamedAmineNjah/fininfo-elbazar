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
 * Criteria class for the {@link org.fininfo.elbazar.domain.Adresse} entity. This class is used
 * in {@link org.fininfo.elbazar.web.rest.AdresseResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /adresses?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AdresseCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BooleanFilter principale;

    private StringFilter prenom;

    private StringFilter nom;

    private StringFilter adresse;

    private StringFilter gouvernorat;

    private StringFilter ville;

    private StringFilter localite;

    private StringFilter indication;

    private IntegerFilter telephone;

    private IntegerFilter mobile;

    private LocalDateFilter creeLe;

    private StringFilter creePar;

    private LocalDateFilter modifieLe;

    private StringFilter modifiePar;

    private LongFilter commandeId;

    private LongFilter clientId;

    private LongFilter zoneId;

    private LongFilter codePostalId;

    public AdresseCriteria() {
    }

    public AdresseCriteria(AdresseCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.principale = other.principale == null ? null : other.principale.copy();
        this.prenom = other.prenom == null ? null : other.prenom.copy();
        this.nom = other.nom == null ? null : other.nom.copy();
        this.adresse = other.adresse == null ? null : other.adresse.copy();
        this.gouvernorat = other.gouvernorat == null ? null : other.gouvernorat.copy();
        this.ville = other.ville == null ? null : other.ville.copy();
        this.localite = other.localite == null ? null : other.localite.copy();
        this.indication = other.indication == null ? null : other.indication.copy();
        this.telephone = other.telephone == null ? null : other.telephone.copy();
        this.mobile = other.mobile == null ? null : other.mobile.copy();
        this.creeLe = other.creeLe == null ? null : other.creeLe.copy();
        this.creePar = other.creePar == null ? null : other.creePar.copy();
        this.modifieLe = other.modifieLe == null ? null : other.modifieLe.copy();
        this.modifiePar = other.modifiePar == null ? null : other.modifiePar.copy();
        this.commandeId = other.commandeId == null ? null : other.commandeId.copy();
        this.clientId = other.clientId == null ? null : other.clientId.copy();
        this.zoneId = other.zoneId == null ? null : other.zoneId.copy();
        this.codePostalId = other.codePostalId == null ? null : other.codePostalId.copy();
    }

    @Override
    public AdresseCriteria copy() {
        return new AdresseCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public BooleanFilter getPrincipale() {
        return principale;
    }

    public void setPrincipale(BooleanFilter principale) {
        this.principale = principale;
    }

    public StringFilter getPrenom() {
        return prenom;
    }

    public void setPrenom(StringFilter prenom) {
        this.prenom = prenom;
    }

    public StringFilter getNom() {
        return nom;
    }

    public void setNom(StringFilter nom) {
        this.nom = nom;
    }

    public StringFilter getAdresse() {
        return adresse;
    }

    public void setAdresse(StringFilter adresse) {
        this.adresse = adresse;
    }

    public StringFilter getGouvernorat() {
        return gouvernorat;
    }

    public void setGouvernorat(StringFilter gouvernorat) {
        this.gouvernorat = gouvernorat;
    }

    public StringFilter getVille() {
        return ville;
    }

    public void setVille(StringFilter ville) {
        this.ville = ville;
    }

    public StringFilter getLocalite() {
        return localite;
    }

    public void setLocalite(StringFilter localite) {
        this.localite = localite;
    }

    public StringFilter getIndication() {
        return indication;
    }

    public void setIndication(StringFilter indication) {
        this.indication = indication;
    }

    public IntegerFilter getTelephone() {
        return telephone;
    }

    public void setTelephone(IntegerFilter telephone) {
        this.telephone = telephone;
    }

    public IntegerFilter getMobile() {
        return mobile;
    }

    public void setMobile(IntegerFilter mobile) {
        this.mobile = mobile;
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

    public LongFilter getCommandeId() {
        return commandeId;
    }

    public void setCommandeId(LongFilter commandeId) {
        this.commandeId = commandeId;
    }

    public LongFilter getClientId() {
        return clientId;
    }

    public void setClientId(LongFilter clientId) {
        this.clientId = clientId;
    }

    public LongFilter getZoneId() {
        return zoneId;
    }

    public void setZoneId(LongFilter zoneId) {
        this.zoneId = zoneId;
    }

    public LongFilter getCodePostalId() {
        return codePostalId;
    }

    public void setCodePostalId(LongFilter codePostalId) {
        this.codePostalId = codePostalId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AdresseCriteria that = (AdresseCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(principale, that.principale) &&
            Objects.equals(prenom, that.prenom) &&
            Objects.equals(nom, that.nom) &&
            Objects.equals(adresse, that.adresse) &&
            Objects.equals(gouvernorat, that.gouvernorat) &&
            Objects.equals(ville, that.ville) &&
            Objects.equals(localite, that.localite) &&
            Objects.equals(indication, that.indication) &&
            Objects.equals(telephone, that.telephone) &&
            Objects.equals(mobile, that.mobile) &&
            Objects.equals(creeLe, that.creeLe) &&
            Objects.equals(creePar, that.creePar) &&
            Objects.equals(modifieLe, that.modifieLe) &&
            Objects.equals(modifiePar, that.modifiePar) &&
            Objects.equals(commandeId, that.commandeId) &&
            Objects.equals(clientId, that.clientId) &&
            Objects.equals(zoneId, that.zoneId) &&
            Objects.equals(codePostalId, that.codePostalId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        principale,
        prenom,
        nom,
        adresse,
        gouvernorat,
        ville,
        localite,
        indication,
        telephone,
        mobile,
        creeLe,
        creePar,
        modifieLe,
        modifiePar,
        commandeId,
        clientId,
        zoneId,
        codePostalId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdresseCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (principale != null ? "principale=" + principale + ", " : "") +
                (prenom != null ? "prenom=" + prenom + ", " : "") +
                (nom != null ? "nom=" + nom + ", " : "") +
                (adresse != null ? "adresse=" + adresse + ", " : "") +
                (gouvernorat != null ? "gouvernorat=" + gouvernorat + ", " : "") +
                (ville != null ? "ville=" + ville + ", " : "") +
                (localite != null ? "localite=" + localite + ", " : "") +
                (indication != null ? "indication=" + indication + ", " : "") +
                (telephone != null ? "telephone=" + telephone + ", " : "") +
                (mobile != null ? "mobile=" + mobile + ", " : "") +
                (creeLe != null ? "creeLe=" + creeLe + ", " : "") +
                (creePar != null ? "creePar=" + creePar + ", " : "") +
                (modifieLe != null ? "modifieLe=" + modifieLe + ", " : "") +
                (modifiePar != null ? "modifiePar=" + modifiePar + ", " : "") +
                (commandeId != null ? "commandeId=" + commandeId + ", " : "") +
                (clientId != null ? "clientId=" + clientId + ", " : "") +
                (zoneId != null ? "zoneId=" + zoneId + ", " : "") +
                (codePostalId != null ? "codePostalId=" + codePostalId + ", " : "") +
            "}";
    }

}
