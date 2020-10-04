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
 * Criteria class for the {@link org.fininfo.elbazar.domain.CommandeLignes} entity. This class is used
 * in {@link org.fininfo.elbazar.web.rest.CommandeLignesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /commande-lignes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CommandeLignesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private DoubleFilter quantite;

    private DoubleFilter prixHT;

    private DoubleFilter remise;

    private DoubleFilter tva;

    private DoubleFilter prixTTC;

    private LocalDateFilter creeLe;

    private StringFilter creePar;

    private LocalDateFilter modifieLe;

    private StringFilter modifiePar;

    private LongFilter refCommandeId;

    private LongFilter refProduitId;

    public CommandeLignesCriteria() {
    }

    public CommandeLignesCriteria(CommandeLignesCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.quantite = other.quantite == null ? null : other.quantite.copy();
        this.prixHT = other.prixHT == null ? null : other.prixHT.copy();
        this.remise = other.remise == null ? null : other.remise.copy();
        this.tva = other.tva == null ? null : other.tva.copy();
        this.prixTTC = other.prixTTC == null ? null : other.prixTTC.copy();
        this.creeLe = other.creeLe == null ? null : other.creeLe.copy();
        this.creePar = other.creePar == null ? null : other.creePar.copy();
        this.modifieLe = other.modifieLe == null ? null : other.modifieLe.copy();
        this.modifiePar = other.modifiePar == null ? null : other.modifiePar.copy();
        this.refCommandeId = other.refCommandeId == null ? null : other.refCommandeId.copy();
        this.refProduitId = other.refProduitId == null ? null : other.refProduitId.copy();
    }

    @Override
    public CommandeLignesCriteria copy() {
        return new CommandeLignesCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public DoubleFilter getQuantite() {
        return quantite;
    }

    public void setQuantite(DoubleFilter quantite) {
        this.quantite = quantite;
    }

    public DoubleFilter getPrixHT() {
        return prixHT;
    }

    public void setPrixHT(DoubleFilter prixHT) {
        this.prixHT = prixHT;
    }

    public DoubleFilter getRemise() {
        return remise;
    }

    public void setRemise(DoubleFilter remise) {
        this.remise = remise;
    }

    public DoubleFilter getTva() {
        return tva;
    }

    public void setTva(DoubleFilter tva) {
        this.tva = tva;
    }

    public DoubleFilter getPrixTTC() {
        return prixTTC;
    }

    public void setPrixTTC(DoubleFilter prixTTC) {
        this.prixTTC = prixTTC;
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

    public LongFilter getRefCommandeId() {
        return refCommandeId;
    }

    public void setRefCommandeId(LongFilter refCommandeId) {
        this.refCommandeId = refCommandeId;
    }

    public LongFilter getRefProduitId() {
        return refProduitId;
    }

    public void setRefProduitId(LongFilter refProduitId) {
        this.refProduitId = refProduitId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CommandeLignesCriteria that = (CommandeLignesCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(quantite, that.quantite) &&
            Objects.equals(prixHT, that.prixHT) &&
            Objects.equals(remise, that.remise) &&
            Objects.equals(tva, that.tva) &&
            Objects.equals(prixTTC, that.prixTTC) &&
            Objects.equals(creeLe, that.creeLe) &&
            Objects.equals(creePar, that.creePar) &&
            Objects.equals(modifieLe, that.modifieLe) &&
            Objects.equals(modifiePar, that.modifiePar) &&
            Objects.equals(refCommandeId, that.refCommandeId) &&
            Objects.equals(refProduitId, that.refProduitId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        quantite,
        prixHT,
        remise,
        tva,
        prixTTC,
        creeLe,
        creePar,
        modifieLe,
        modifiePar,
        refCommandeId,
        refProduitId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CommandeLignesCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (quantite != null ? "quantite=" + quantite + ", " : "") +
                (prixHT != null ? "prixHT=" + prixHT + ", " : "") +
                (remise != null ? "remise=" + remise + ", " : "") +
                (tva != null ? "tva=" + tva + ", " : "") +
                (prixTTC != null ? "prixTTC=" + prixTTC + ", " : "") +
                (creeLe != null ? "creeLe=" + creeLe + ", " : "") +
                (creePar != null ? "creePar=" + creePar + ", " : "") +
                (modifieLe != null ? "modifieLe=" + modifieLe + ", " : "") +
                (modifiePar != null ? "modifiePar=" + modifiePar + ", " : "") +
                (refCommandeId != null ? "refCommandeId=" + refCommandeId + ", " : "") +
                (refProduitId != null ? "refProduitId=" + refProduitId + ", " : "") +
            "}";
    }

}
