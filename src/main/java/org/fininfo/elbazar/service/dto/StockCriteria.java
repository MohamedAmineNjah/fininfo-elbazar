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
 * Criteria class for the {@link org.fininfo.elbazar.domain.Stock} entity. This class is used
 * in {@link org.fininfo.elbazar.web.rest.StockResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /stocks?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class StockCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private DoubleFilter stockReserve;

    private DoubleFilter stockCommande;

    private DoubleFilter stockPhysique;

    private DoubleFilter stockDisponible;

    private DoubleFilter stockMinimum;

    private LocalDateFilter derniereEntre;

    private LocalDateFilter derniereSortie;

    private BooleanFilter alerteStock;

    private LocalDateFilter creeLe;

    private StringFilter creePar;

    private LocalDateFilter modifieLe;

    private StringFilter modifiePar;

    private LongFilter refProduitId;

    private StringFilter refProduitReference;

    private StringFilter nomProduit;

    private LongFilter idCategorieId;

    private LongFilter idSousCategorieId;

    public StockCriteria() {
    }

    public StockCriteria(StockCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.stockReserve = other.stockReserve == null ? null : other.stockReserve.copy();
        this.stockCommande = other.stockCommande == null ? null : other.stockCommande.copy();
        this.stockPhysique = other.stockPhysique == null ? null : other.stockPhysique.copy();
        this.stockDisponible = other.stockDisponible == null ? null : other.stockDisponible.copy();
        this.stockMinimum = other.stockMinimum == null ? null : other.stockMinimum.copy();
        this.derniereEntre = other.derniereEntre == null ? null : other.derniereEntre.copy();
        this.derniereSortie = other.derniereSortie == null ? null : other.derniereSortie.copy();
        this.alerteStock = other.alerteStock == null ? null : other.alerteStock.copy();
        this.creeLe = other.creeLe == null ? null : other.creeLe.copy();
        this.creePar = other.creePar == null ? null : other.creePar.copy();
        this.modifieLe = other.modifieLe == null ? null : other.modifieLe.copy();
        this.modifiePar = other.modifiePar == null ? null : other.modifiePar.copy();
        this.refProduitId = other.refProduitId == null ? null : other.refProduitId.copy();
        this.refProduitReference = other.refProduitReference == null ? null : other.refProduitReference.copy();
        this.nomProduit = other.nomProduit == null ? null : other.nomProduit.copy();
        this.idCategorieId = other.idCategorieId == null ? null : other.idCategorieId.copy();
        this.idSousCategorieId = other.idSousCategorieId == null ? null : other.idSousCategorieId.copy();
    }

    @Override
    public StockCriteria copy() {
        return new StockCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public DoubleFilter getStockReserve() {
        return stockReserve;
    }

    public void setStockReserve(DoubleFilter stockReserve) {
        this.stockReserve = stockReserve;
    }

    public DoubleFilter getStockCommande() {
        return stockCommande;
    }

    public void setStockCommande(DoubleFilter stockCommande) {
        this.stockCommande = stockCommande;
    }

    public DoubleFilter getStockPhysique() {
        return stockPhysique;
    }

    public void setStockPhysique(DoubleFilter stockPhysique) {
        this.stockPhysique = stockPhysique;
    }

    public DoubleFilter getStockDisponible() {
        return stockDisponible;
    }

    public void setStockDisponible(DoubleFilter stockDisponible) {
        this.stockDisponible = stockDisponible;
    }

    public DoubleFilter getStockMinimum() {
        return stockMinimum;
    }

    public void setStockMinimum(DoubleFilter stockMinimum) {
        this.stockMinimum = stockMinimum;
    }

    public LocalDateFilter getDerniereEntre() {
        return derniereEntre;
    }

    public void setDerniereEntre(LocalDateFilter derniereEntre) {
        this.derniereEntre = derniereEntre;
    }

    public LocalDateFilter getDerniereSortie() {
        return derniereSortie;
    }

    public void setDerniereSortie(LocalDateFilter derniereSortie) {
        this.derniereSortie = derniereSortie;
    }

    public BooleanFilter getAlerteStock() {
        return alerteStock;
    }

    public void setAlerteStock(BooleanFilter alerteStock) {
        this.alerteStock = alerteStock;
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

    public StringFilter getRefProduitReference() {
        return refProduitReference;
    }

    public void setRefProduitReference(StringFilter refProduitReference) {
        this.refProduitReference = refProduitReference;
    }

    public LongFilter getIdCategorieId() {
        return idCategorieId;
    }

    public void setIdCategorieId(LongFilter idCategorieId) {
        this.idCategorieId = idCategorieId;
    }

    public LongFilter getIdSousCategorieId() {
        return idSousCategorieId;
    }

    public void setIdSousCategorieId(LongFilter idSousCategorieId) {
        this.idSousCategorieId = idSousCategorieId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final StockCriteria that = (StockCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(stockReserve, that.stockReserve) &&
            Objects.equals(stockCommande, that.stockCommande) &&
            Objects.equals(stockPhysique, that.stockPhysique) &&
            Objects.equals(stockDisponible, that.stockDisponible) &&
            Objects.equals(stockMinimum, that.stockMinimum) &&
            Objects.equals(derniereEntre, that.derniereEntre) &&
            Objects.equals(derniereSortie, that.derniereSortie) &&
            Objects.equals(alerteStock, that.alerteStock) &&
            Objects.equals(creeLe, that.creeLe) &&
            Objects.equals(creePar, that.creePar) &&
            Objects.equals(modifieLe, that.modifieLe) &&
            Objects.equals(modifiePar, that.modifiePar) &&
            Objects.equals(refProduitId, that.refProduitId) &&
            Objects.equals(refProduitReference, that.refProduitReference) &&
            Objects.equals(nomProduit, that.nomProduit) &&
            Objects.equals(idCategorieId, that.idCategorieId) &&
            Objects.equals(idSousCategorieId, that.idSousCategorieId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        stockReserve,
        stockCommande,
        stockPhysique,
        stockDisponible,
        stockMinimum,
        derniereEntre,
        derniereSortie,
        alerteStock,
        creeLe,
        creePar,
        modifieLe,
        modifiePar,
        refProduitId,
        nomProduit,
        refProduitReference,
        idCategorieId,
        idSousCategorieId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StockCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (stockReserve != null ? "stockReserve=" + stockReserve + ", " : "") +
                (stockCommande != null ? "stockCommande=" + stockCommande + ", " : "") +
                (stockPhysique != null ? "stockPhysique=" + stockPhysique + ", " : "") +
                (stockDisponible != null ? "stockDisponible=" + stockDisponible + ", " : "") +
                (stockMinimum != null ? "stockMinimum=" + stockMinimum + ", " : "") +
                (derniereEntre != null ? "derniereEntre=" + derniereEntre + ", " : "") +
                (derniereSortie != null ? "derniereSortie=" + derniereSortie + ", " : "") +
                (alerteStock != null ? "alerteStock=" + alerteStock + ", " : "") +
                (creeLe != null ? "creeLe=" + creeLe + ", " : "") +
                (creePar != null ? "creePar=" + creePar + ", " : "") +
                (modifieLe != null ? "modifieLe=" + modifieLe + ", " : "") +
                (modifiePar != null ? "modifiePar=" + modifiePar + ", " : "") +
                (refProduitId != null ? "refProduitId=" + refProduitId + ", " : "") +
                (refProduitReference != null ? "refProduitReference=" + refProduitReference + ", " : "") +
                (nomProduit != null ? "nomProduit=" + nomProduit + ", " : "") +
                (idCategorieId != null ? "idCategorieId=" + idCategorieId + ", " : "") +
                (idSousCategorieId != null ? "idSousCategorieId=" + idSousCategorieId + ", " : "") +
            "}";
    }

}
