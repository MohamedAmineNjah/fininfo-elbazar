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
 * Criteria class for the {@link org.fininfo.elbazar.domain.AffectationZone} entity. This class is used
 * in {@link org.fininfo.elbazar.web.rest.AffectationZoneResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /affectation-zones?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AffectationZoneCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter gouvernorat;

    private StringFilter ville;

    private StringFilter localite;

    private IntegerFilter codePostal;

    private LocalDateFilter modifieLe;

    private StringFilter modifiePar;

    private IntegerFilter idVille;

    private LongFilter adresseId;

    private LongFilter zoneId;

    private StringFilter zoneNom;

    public AffectationZoneCriteria() {
    }

    public AffectationZoneCriteria(AffectationZoneCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.gouvernorat = other.gouvernorat == null ? null : other.gouvernorat.copy();
        this.ville = other.ville == null ? null : other.ville.copy();
        this.localite = other.localite == null ? null : other.localite.copy();
        this.codePostal = other.codePostal == null ? null : other.codePostal.copy();
        this.modifieLe = other.modifieLe == null ? null : other.modifieLe.copy();
        this.modifiePar = other.modifiePar == null ? null : other.modifiePar.copy();
        this.idVille = other.idVille == null ? null : other.idVille.copy();
        this.adresseId = other.adresseId == null ? null : other.adresseId.copy();
        this.zoneId = other.zoneId == null ? null : other.zoneId.copy();
        this.zoneNom = other.zoneNom == null ? null : other.zoneNom.copy();
    }

    @Override
    public AffectationZoneCriteria copy() {
        return new AffectationZoneCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
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

    public IntegerFilter getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(IntegerFilter codePostal) {
        this.codePostal = codePostal;
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

    public IntegerFilter getIdVille() {
        return idVille;
    }

    public void setIdVille(IntegerFilter idVille) {
        this.idVille = idVille;
    }

    public LongFilter getAdresseId() {
        return adresseId;
    }

    public void setAdresseId(LongFilter adresseId) {
        this.adresseId = adresseId;
    }

    public LongFilter getZoneId() {
        return zoneId;
    }

    public void setZoneId(LongFilter zoneId) {
        this.zoneId = zoneId;
    }

    public StringFilter getZoneNom() {
        return zoneNom;
    }

    public void setZoneNom(StringFilter zoneNom) {
        this.zoneNom = zoneNom;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AffectationZoneCriteria that = (AffectationZoneCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(gouvernorat, that.gouvernorat) &&
            Objects.equals(ville, that.ville) &&
            Objects.equals(localite, that.localite) &&
            Objects.equals(codePostal, that.codePostal) &&
            Objects.equals(modifieLe, that.modifieLe) &&
            Objects.equals(modifiePar, that.modifiePar) &&
            Objects.equals(idVille, that.idVille) &&
            Objects.equals(adresseId, that.adresseId) &&
            Objects.equals(zoneId, that.zoneId) &&
            Objects.equals(zoneNom, that.zoneNom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        gouvernorat,
        ville,
        localite,
        codePostal,
        modifieLe,
        modifiePar,
        idVille,
        adresseId,
        zoneId,
        zoneNom
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AffectationZoneCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (gouvernorat != null ? "gouvernorat=" + gouvernorat + ", " : "") +
                (ville != null ? "ville=" + ville + ", " : "") +
                (localite != null ? "localite=" + localite + ", " : "") +
                (codePostal != null ? "codePostal=" + codePostal + ", " : "") +
                (modifieLe != null ? "modifieLe=" + modifieLe + ", " : "") +
                (modifiePar != null ? "modifiePar=" + modifiePar + ", " : "") +
                (idVille != null ? "idVille=" + idVille + ", " : "") +
                (adresseId != null ? "adresseId=" + adresseId + ", " : "") +
                (zoneId != null ? "zoneId=" + zoneId + ", " : "") +
                (zoneNom != null ? "zoneId=" + zoneNom + ", " : "") +
            "}";
    }

}
