package org.fininfo.elbazar.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import org.fininfo.elbazar.domain.enumeration.TypeMvt;
import org.fininfo.elbazar.service.dto.CommandeCriteria.StatCmdFilter;

import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link org.fininfo.elbazar.domain.MouvementStock} entity. This class is used
 * in {@link org.fininfo.elbazar.web.rest.MouvementStockResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /mouvement-stocks?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MouvementStockCriteria implements Serializable, Criteria {
    /**
     * Class for filtering TypeMvt
     */
    public static class TypeMvtFilter extends Filter<TypeMvt> {

        public TypeMvtFilter() {
        }

        public TypeMvtFilter(TypeMvtFilter filter) {
            super(filter);
        }

        @Override
        public TypeMvtFilter copy() {
            return new TypeMvtFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private TypeMvtFilter type;

    private LocalDateFilter date;

    private IntegerFilter sens;

    private DoubleFilter quantite;

    private LocalDateFilter creeLe;

    private StringFilter creePar;

    private LocalDateFilter modifieLe;

    private StringFilter modifiePar;

    private StringFilter reference;

    private LongFilter refProduitId;

    private StringFilter refProduitReference;

    private StringFilter nomProduit;

    private LongFilter refCommandeId;

    private StringFilter refCommandeReference;

    private StatCmdFilter refCommandeStatut;

    public MouvementStockCriteria() {
    }

    public MouvementStockCriteria(MouvementStockCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.date = other.date == null ? null : other.date.copy();
        this.sens = other.sens == null ? null : other.sens.copy();
        this.quantite = other.quantite == null ? null : other.quantite.copy();
        this.creeLe = other.creeLe == null ? null : other.creeLe.copy();
        this.creePar = other.creePar == null ? null : other.creePar.copy();
        this.modifieLe = other.modifieLe == null ? null : other.modifieLe.copy();
        this.modifiePar = other.modifiePar == null ? null : other.modifiePar.copy();
        this.reference = other.reference == null ? null : other.reference.copy();
        this.refProduitId = other.refProduitId == null ? null : other.refProduitId.copy();
        this.refProduitReference = other.refProduitReference == null ? null : other.refProduitReference.copy();
        this.nomProduit = other.nomProduit == null ? null : other.nomProduit.copy();
        this.refCommandeId = other.refCommandeId == null ? null : other.refCommandeId.copy();
        this.refCommandeReference = other.refCommandeReference == null ? null : other.refCommandeReference.copy();
        this.refCommandeStatut = other.refCommandeStatut == null ? null : other.refCommandeStatut.copy();
    }

    @Override
    public MouvementStockCriteria copy() {
        return new MouvementStockCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public TypeMvtFilter getType() {
        return type;
    }

    public void setType(TypeMvtFilter type) {
        this.type = type;
    }

    public LocalDateFilter getDate() {
        return date;
    }

    public void setDate(LocalDateFilter date) {
        this.date = date;
    }

    public IntegerFilter getSens() {
        return sens;
    }

    public void setSens(IntegerFilter sens) {
        this.sens = sens;
    }

    public DoubleFilter getQuantite() {
        return quantite;
    }

    public void setQuantite(DoubleFilter quantite) {
        this.quantite = quantite;
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

    public StringFilter getReference() {
        return reference;
    }

    public void setReference(StringFilter reference) {
        this.reference = reference;
    }

    public StringFilter getRefProduitReference() {
        return refProduitReference;
    }

    public void setRefProduitReference(StringFilter refProduitReference) {
        this.refProduitReference = refProduitReference;
    }

    public StringFilter getNomProduit() {
        return nomProduit;
    }

    public void setNomProduit(StringFilter nomProduit) {
        this.nomProduit = nomProduit;
    }

    public LongFilter getRefProduitId() {
        return refProduitId;
    }

    public void setRefProduitId(LongFilter refProduitId) {
        this.refProduitId = refProduitId;
    }

    public LongFilter getRefCommandeId() {
        return refCommandeId;
    }

    public void setRefCommandeId(LongFilter refCommandeId) {
        this.refCommandeId = refCommandeId;
    }


    public StringFilter getRefCommandeReference() {
        return refCommandeReference;
    }

    public void setRefCommandeReference(StringFilter refCommandeReference) {
        this.refCommandeReference = refCommandeReference;
    }

    public StatCmdFilter getRefCommandeStatut() {
        return refCommandeStatut;
    }

    public void setRefCommandeStatut(StatCmdFilter refCommandeStatut) {
        this.refCommandeStatut = refCommandeStatut;
    }
    

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MouvementStockCriteria that = (MouvementStockCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(type, that.type) &&
            Objects.equals(date, that.date) &&
            Objects.equals(sens, that.sens) &&
            Objects.equals(quantite, that.quantite) &&
            Objects.equals(creeLe, that.creeLe) &&
            Objects.equals(creePar, that.creePar) &&
            Objects.equals(modifieLe, that.modifieLe) &&
            Objects.equals(modifiePar, that.modifiePar) &&
            Objects.equals(reference, that.reference) &&
            Objects.equals(refProduitId, that.refProduitId) &&
            Objects.equals(refProduitReference, that.refProduitReference) &&
            Objects.equals(nomProduit, that.nomProduit) &&
            Objects.equals(refProduitReference, that.refProduitReference) &&
            Objects.equals(refCommandeReference, that.refCommandeReference) &&
            Objects.equals(refCommandeStatut, that.refCommandeStatut);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        type,
        date,
        sens,
        quantite,
        creeLe,
        creePar,
        modifieLe,
        modifiePar,
        reference,
        refProduitId,
        refProduitReference,
        nomProduit,
        refCommandeId,
        refCommandeReference,
        refCommandeStatut
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MouvementStockCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (type != null ? "type=" + type + ", " : "") +
                (date != null ? "date=" + date + ", " : "") +
                (sens != null ? "sens=" + sens + ", " : "") +
                (quantite != null ? "quantite=" + quantite + ", " : "") +
                (creeLe != null ? "creeLe=" + creeLe + ", " : "") +
                (creePar != null ? "creePar=" + creePar + ", " : "") +
                (modifieLe != null ? "modifieLe=" + modifieLe + ", " : "") +
                (modifiePar != null ? "modifiePar=" + modifiePar + ", " : "") +
                (reference != null ? "reference=" + reference + ", " : "") +
                (refProduitId != null ? "refProduitId=" + refProduitId + ", " : "") +
                (refProduitReference != null ? "refProduitReference=" + refProduitReference + ", " : "") +
                (nomProduit != null ? "nomProduit=" + nomProduit + ", " : "") +
                (refCommandeId != null ? "refCommandeId=" + refCommandeId + ", " : "") +
                (refCommandeReference != null ? "refCommandeReference=" + refCommandeReference + ", " : "") +
                (refCommandeStatut != null ? "refCommandeId=" + refCommandeStatut.toString() + ", " : "") +
            "}";
    }


}
