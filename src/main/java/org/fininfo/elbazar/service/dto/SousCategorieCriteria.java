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
 * Criteria class for the {@link org.fininfo.elbazar.domain.SousCategorie} entity. This class is used
 * in {@link org.fininfo.elbazar.web.rest.SousCategorieResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /sous-categories?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SousCategorieCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nom;

    private StringFilter description;

    private IntegerFilter position;

    private BooleanFilter etat;

    private LocalDateFilter creeLe;

    private StringFilter creePar;

    private LocalDateFilter modifieLe;

    private StringFilter modifiePar;

    private LongFilter produitId;

    private LongFilter stockId;

    private LongFilter categorieId;

    public SousCategorieCriteria() {
    }

    public SousCategorieCriteria(SousCategorieCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nom = other.nom == null ? null : other.nom.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.position = other.position == null ? null : other.position.copy();
        this.etat = other.etat == null ? null : other.etat.copy();
        this.creeLe = other.creeLe == null ? null : other.creeLe.copy();
        this.creePar = other.creePar == null ? null : other.creePar.copy();
        this.modifieLe = other.modifieLe == null ? null : other.modifieLe.copy();
        this.modifiePar = other.modifiePar == null ? null : other.modifiePar.copy();
        this.produitId = other.produitId == null ? null : other.produitId.copy();
        this.stockId = other.stockId == null ? null : other.stockId.copy();
        this.categorieId = other.categorieId == null ? null : other.categorieId.copy();
    }

    @Override
    public SousCategorieCriteria copy() {
        return new SousCategorieCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNom() {
        return nom;
    }

    public void setNom(StringFilter nom) {
        this.nom = nom;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public IntegerFilter getPosition() {
        return position;
    }

    public void setPosition(IntegerFilter position) {
        this.position = position;
    }

    public BooleanFilter getEtat() {
        return etat;
    }

    public void setEtat(BooleanFilter etat) {
        this.etat = etat;
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

    public LongFilter getProduitId() {
        return produitId;
    }

    public void setProduitId(LongFilter produitId) {
        this.produitId = produitId;
    }

    public LongFilter getStockId() {
        return stockId;
    }

    public void setStockId(LongFilter stockId) {
        this.stockId = stockId;
    }

    public LongFilter getCategorieId() {
        return categorieId;
    }

    public void setCategorieId(LongFilter categorieId) {
        this.categorieId = categorieId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SousCategorieCriteria that = (SousCategorieCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nom, that.nom) &&
            Objects.equals(description, that.description) &&
            Objects.equals(position, that.position) &&
            Objects.equals(etat, that.etat) &&
            Objects.equals(creeLe, that.creeLe) &&
            Objects.equals(creePar, that.creePar) &&
            Objects.equals(modifieLe, that.modifieLe) &&
            Objects.equals(modifiePar, that.modifiePar) &&
            Objects.equals(produitId, that.produitId) &&
            Objects.equals(stockId, that.stockId) &&
            Objects.equals(categorieId, that.categorieId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nom,
        description,
        position,
        etat,
        creeLe,
        creePar,
        modifieLe,
        modifiePar,
        produitId,
        stockId,
        categorieId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SousCategorieCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nom != null ? "nom=" + nom + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (position != null ? "position=" + position + ", " : "") +
                (etat != null ? "etat=" + etat + ", " : "") +
                (creeLe != null ? "creeLe=" + creeLe + ", " : "") +
                (creePar != null ? "creePar=" + creePar + ", " : "") +
                (modifieLe != null ? "modifieLe=" + modifieLe + ", " : "") +
                (modifiePar != null ? "modifiePar=" + modifiePar + ", " : "") +
                (produitId != null ? "produitId=" + produitId + ", " : "") +
                (stockId != null ? "stockId=" + stockId + ", " : "") +
                (categorieId != null ? "categorieId=" + categorieId + ", " : "") +
            "}";
    }

}
