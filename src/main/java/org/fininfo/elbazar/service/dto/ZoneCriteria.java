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
 * Criteria class for the {@link org.fininfo.elbazar.domain.Zone} entity. This class is used
 * in {@link org.fininfo.elbazar.web.rest.ZoneResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /zones?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ZoneCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter codeZone;

    private StringFilter nom;

    private LocalDateFilter creeLe;

    private StringFilter creePar;

    private LocalDateFilter modifieLe;

    private StringFilter modifiePar;

    private LongFilter adresseId;

    private LongFilter livraisonId;

    private LongFilter affectationZoneId;

    private LongFilter commandeId;

    public ZoneCriteria() {
    }

    public ZoneCriteria(ZoneCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.codeZone = other.codeZone == null ? null : other.codeZone.copy();
        this.nom = other.nom == null ? null : other.nom.copy();
        this.creeLe = other.creeLe == null ? null : other.creeLe.copy();
        this.creePar = other.creePar == null ? null : other.creePar.copy();
        this.modifieLe = other.modifieLe == null ? null : other.modifieLe.copy();
        this.modifiePar = other.modifiePar == null ? null : other.modifiePar.copy();
        this.adresseId = other.adresseId == null ? null : other.adresseId.copy();
        this.livraisonId = other.livraisonId == null ? null : other.livraisonId.copy();
        this.affectationZoneId = other.affectationZoneId == null ? null : other.affectationZoneId.copy();
        this.commandeId = other.commandeId == null ? null : other.commandeId.copy();
    }

    @Override
    public ZoneCriteria copy() {
        return new ZoneCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCodeZone() {
        return codeZone;
    }

    public void setCodeZone(StringFilter codeZone) {
        this.codeZone = codeZone;
    }

    public StringFilter getNom() {
        return nom;
    }

    public void setNom(StringFilter nom) {
        this.nom = nom;
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

    public LongFilter getAdresseId() {
        return adresseId;
    }

    public void setAdresseId(LongFilter adresseId) {
        this.adresseId = adresseId;
    }

    public LongFilter getLivraisonId() {
        return livraisonId;
    }

    public void setLivraisonId(LongFilter livraisonId) {
        this.livraisonId = livraisonId;
    }

    public LongFilter getAffectationZoneId() {
        return affectationZoneId;
    }

    public void setAffectationZoneId(LongFilter affectationZoneId) {
        this.affectationZoneId = affectationZoneId;
    }

    public LongFilter getCommandeId() {
        return commandeId;
    }

    public void setCommandeId(LongFilter commandeId) {
        this.commandeId = commandeId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ZoneCriteria that = (ZoneCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(codeZone, that.codeZone) &&
            Objects.equals(nom, that.nom) &&
            Objects.equals(creeLe, that.creeLe) &&
            Objects.equals(creePar, that.creePar) &&
            Objects.equals(modifieLe, that.modifieLe) &&
            Objects.equals(modifiePar, that.modifiePar) &&
            Objects.equals(adresseId, that.adresseId) &&
            Objects.equals(livraisonId, that.livraisonId) &&
            Objects.equals(affectationZoneId, that.affectationZoneId) &&
            Objects.equals(commandeId, that.commandeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        codeZone,
        nom,
        creeLe,
        creePar,
        modifieLe,
        modifiePar,
        adresseId,
        livraisonId,
        affectationZoneId,
        commandeId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ZoneCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (codeZone != null ? "codeZone=" + codeZone + ", " : "") +
                (nom != null ? "nom=" + nom + ", " : "") +
                (creeLe != null ? "creeLe=" + creeLe + ", " : "") +
                (creePar != null ? "creePar=" + creePar + ", " : "") +
                (modifieLe != null ? "modifieLe=" + modifieLe + ", " : "") +
                (modifiePar != null ? "modifiePar=" + modifiePar + ", " : "") +
                (adresseId != null ? "adresseId=" + adresseId + ", " : "") +
                (livraisonId != null ? "livraisonId=" + livraisonId + ", " : "") +
                (affectationZoneId != null ? "affectationZoneId=" + affectationZoneId + ", " : "") +
                (commandeId != null ? "commandeId=" + commandeId + ", " : "") +
            "}";
    }

}
