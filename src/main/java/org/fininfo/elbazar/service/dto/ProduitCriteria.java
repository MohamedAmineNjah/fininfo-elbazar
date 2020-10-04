package org.fininfo.elbazar.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import org.fininfo.elbazar.domain.enumeration.Devise;
import org.fininfo.elbazar.domain.enumeration.SourcePrd;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link org.fininfo.elbazar.domain.Produit} entity. This class is used
 * in {@link org.fininfo.elbazar.web.rest.ProduitResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /produits?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProduitCriteria implements Serializable, Criteria {
    /**
     * Class for filtering Devise
     */
    public static class DeviseFilter extends Filter<Devise> {

        public DeviseFilter() {
        }

        public DeviseFilter(DeviseFilter filter) {
            super(filter);
        }

        @Override
        public DeviseFilter copy() {
            return new DeviseFilter(this);
        }

    }
    /**
     * Class for filtering SourcePrd
     */
    public static class SourcePrdFilter extends Filter<SourcePrd> {

        public SourcePrdFilter() {
        }

        public SourcePrdFilter(SourcePrdFilter filter) {
            super(filter);
        }

        @Override
        public SourcePrdFilter copy() {
            return new SourcePrdFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter reference;

    private StringFilter nom;

    private StringFilter codeBarre;

    private StringFilter description;

    private BooleanFilter etat;

    private StringFilter marque;

    private StringFilter nature;

    private DoubleFilter stockMinimum;

    private DoubleFilter quantiteVenteMax;

    private BooleanFilter horsStock;

    private BooleanFilter typeService;

    private LocalDateFilter datePremption;

    private DoubleFilter prixHT;

    private DoubleFilter tauxTVA;

    private DoubleFilter prixTTC;

    private DeviseFilter devise;

    private SourcePrdFilter sourceProduit;

    private StringFilter rating;

    private BooleanFilter eligibleRemise;

    private DoubleFilter remise;

    private LocalDateFilter debutPromo;

    private LocalDateFilter finPromo;

    private LocalDateFilter creeLe;

    private StringFilter creePar;

    private LocalDateFilter modifieLe;

    private StringFilter modifiePar;

    private BooleanFilter enVedette;

    private StringFilter imageUrl;

    private LongFilter stockId;

    private LongFilter mouvementStockId;

    private LongFilter commandeLignesId;

    private LongFilter categorieId;

    private StringFilter categorieNom;

    private LongFilter sousCategorieId;

    private StringFilter sousCategorieNom;

    private LongFilter uniteId;

    private StringFilter uniteCode;

    public ProduitCriteria() {
    }

    public ProduitCriteria(ProduitCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.reference = other.reference == null ? null : other.reference.copy();
        this.nom = other.nom == null ? null : other.nom.copy();
        this.codeBarre = other.codeBarre == null ? null : other.codeBarre.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.etat = other.etat == null ? null : other.etat.copy();
        this.marque = other.marque == null ? null : other.marque.copy();
        this.nature = other.nature == null ? null : other.nature.copy();
        this.stockMinimum = other.stockMinimum == null ? null : other.stockMinimum.copy();
        this.quantiteVenteMax = other.quantiteVenteMax == null ? null : other.quantiteVenteMax.copy();
        this.horsStock = other.horsStock == null ? null : other.horsStock.copy();
        this.typeService = other.typeService == null ? null : other.typeService.copy();
        this.datePremption = other.datePremption == null ? null : other.datePremption.copy();
        this.prixHT = other.prixHT == null ? null : other.prixHT.copy();
        this.tauxTVA = other.tauxTVA == null ? null : other.tauxTVA.copy();
        this.prixTTC = other.prixTTC == null ? null : other.prixTTC.copy();
        this.devise = other.devise == null ? null : other.devise.copy();
        this.sourceProduit = other.sourceProduit == null ? null : other.sourceProduit.copy();
        this.rating = other.rating == null ? null : other.rating.copy();
        this.eligibleRemise = other.eligibleRemise == null ? null : other.eligibleRemise.copy();
        this.remise = other.remise == null ? null : other.remise.copy();
        this.debutPromo = other.debutPromo == null ? null : other.debutPromo.copy();
        this.finPromo = other.finPromo == null ? null : other.finPromo.copy();
        this.creeLe = other.creeLe == null ? null : other.creeLe.copy();
        this.creePar = other.creePar == null ? null : other.creePar.copy();
        this.modifieLe = other.modifieLe == null ? null : other.modifieLe.copy();
        this.modifiePar = other.modifiePar == null ? null : other.modifiePar.copy();
        this.enVedette = other.enVedette == null ? null : other.enVedette.copy();
        this.imageUrl = other.imageUrl == null ? null : other.imageUrl.copy();
        this.stockId = other.stockId == null ? null : other.stockId.copy();
        this.mouvementStockId = other.mouvementStockId == null ? null : other.mouvementStockId.copy();
        this.commandeLignesId = other.commandeLignesId == null ? null : other.commandeLignesId.copy();
        this.categorieId = other.categorieId == null ? null : other.categorieId.copy();
        this.categorieNom = other.categorieNom == null ? null : other.categorieNom.copy();
        this.sousCategorieId = other.sousCategorieId == null ? null : other.sousCategorieId.copy();
        this.sousCategorieNom = other.sousCategorieNom == null ? null : other.sousCategorieNom.copy();
        this.uniteId = other.uniteId == null ? null : other.uniteId.copy();
        this.uniteCode = other.uniteCode == null ? null : other.uniteCode.copy();
    }

    @Override
    public ProduitCriteria copy() {
        return new ProduitCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getReference() {
        return reference;
    }

    public void setReference(StringFilter reference) {
        this.reference = reference;
    }

    public StringFilter getNom() {
        return nom;
    }

    public void setNom(StringFilter nom) {
        this.nom = nom;
    }

    public StringFilter getCodeBarre() {
        return codeBarre;
    }

    public void setCodeBarre(StringFilter codeBarre) {
        this.codeBarre = codeBarre;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public BooleanFilter getEtat() {
        return etat;
    }

    public void setEtat(BooleanFilter etat) {
        this.etat = etat;
    }

    public StringFilter getMarque() {
        return marque;
    }

    public void setMarque(StringFilter marque) {
        this.marque = marque;
    }

    public StringFilter getNature() {
        return nature;
    }

    public void setNature(StringFilter nature) {
        this.nature = nature;
    }

    public DoubleFilter getStockMinimum() {
        return stockMinimum;
    }

    public void setStockMinimum(DoubleFilter stockMinimum) {
        this.stockMinimum = stockMinimum;
    }

    public DoubleFilter getQuantiteVenteMax() {
        return quantiteVenteMax;
    }

    public void setQuantiteVenteMax(DoubleFilter quantiteVenteMax) {
        this.quantiteVenteMax = quantiteVenteMax;
    }

    public BooleanFilter getHorsStock() {
        return horsStock;
    }

    public void setHorsStock(BooleanFilter horsStock) {
        this.horsStock = horsStock;
    }

    public BooleanFilter getTypeService() {
        return typeService;
    }

    public void setTypeService(BooleanFilter typeService) {
        this.typeService = typeService;
    }

    public LocalDateFilter getDatePremption() {
        return datePremption;
    }

    public void setDatePremption(LocalDateFilter datePremption) {
        this.datePremption = datePremption;
    }

    public DoubleFilter getPrixHT() {
        return prixHT;
    }

    public void setPrixHT(DoubleFilter prixHT) {
        this.prixHT = prixHT;
    }

    public DoubleFilter getTauxTVA() {
        return tauxTVA;
    }

    public void setTauxTVA(DoubleFilter tauxTVA) {
        this.tauxTVA = tauxTVA;
    }

    public DoubleFilter getPrixTTC() {
        return prixTTC;
    }

    public void setPrixTTC(DoubleFilter prixTTC) {
        this.prixTTC = prixTTC;
    }

    public DeviseFilter getDevise() {
        return devise;
    }

    public void setDevise(DeviseFilter devise) {
        this.devise = devise;
    }

    public SourcePrdFilter getSourceProduit() {
        return sourceProduit;
    }

    public void setSourceProduit(SourcePrdFilter sourceProduit) {
        this.sourceProduit = sourceProduit;
    }

    public StringFilter getRating() {
        return rating;
    }

    public void setRating(StringFilter rating) {
        this.rating = rating;
    }

    public BooleanFilter getEligibleRemise() {
        return eligibleRemise;
    }

    public void setEligibleRemise(BooleanFilter eligibleRemise) {
        this.eligibleRemise = eligibleRemise;
    }

    public DoubleFilter getRemise() {
        return remise;
    }

    public void setRemise(DoubleFilter remise) {
        this.remise = remise;
    }

    public LocalDateFilter getDebutPromo() {
        return debutPromo;
    }

    public void setDebutPromo(LocalDateFilter debutPromo) {
        this.debutPromo = debutPromo;
    }

    public LocalDateFilter getFinPromo() {
        return finPromo;
    }

    public void setFinPromo(LocalDateFilter finPromo) {
        this.finPromo = finPromo;
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

    public BooleanFilter getEnVedette() {
        return enVedette;
    }

    public void setEnVedette(BooleanFilter enVedette) {
        this.enVedette = enVedette;
    }

    public StringFilter getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(StringFilter imageUrl) {
        this.imageUrl = imageUrl;
    }

    public LongFilter getStockId() {
        return stockId;
    }

    public void setStockId(LongFilter stockId) {
        this.stockId = stockId;
    }

    public LongFilter getMouvementStockId() {
        return mouvementStockId;
    }

    public void setMouvementStockId(LongFilter mouvementStockId) {
        this.mouvementStockId = mouvementStockId;
    }

    public LongFilter getCommandeLignesId() {
        return commandeLignesId;
    }

    public void setCommandeLignesId(LongFilter commandeLignesId) {
        this.commandeLignesId = commandeLignesId;
    }

    public LongFilter getCategorieId() {
        return categorieId;
    }

    public void setCategorieId(LongFilter categorieId) {
        this.categorieId = categorieId;
    }

    public StringFilter getCategorieNom() {
        return categorieNom;
    }

    public void setCategorieNom(StringFilter categorieNom) {
        this.categorieNom = categorieNom;
    }

    public LongFilter getSousCategorieId() {
        return sousCategorieId;
    }

    public void setSousCategorieId(LongFilter sousCategorieId) {
        this.sousCategorieId = sousCategorieId;
    }

    public StringFilter getSousCategorieNom() {
        return sousCategorieNom;
    }

    public void setSousCategorieNom(StringFilter sousCategorieNom) {
        this.sousCategorieNom = sousCategorieNom;
    }

    public LongFilter getUniteId() {
        return uniteId;
    }

    public void setUniteId(LongFilter uniteId) {
        this.uniteId = uniteId;
    }

    public StringFilter getUniteCode() {
        return uniteCode;
    }

    public void setUniteCode(StringFilter uniteCode) {
        this.uniteCode = uniteCode;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProduitCriteria that = (ProduitCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(reference, that.reference) &&
            Objects.equals(nom, that.nom) &&
            Objects.equals(codeBarre, that.codeBarre) &&
            Objects.equals(description, that.description) &&
            Objects.equals(etat, that.etat) &&
            Objects.equals(marque, that.marque) &&
            Objects.equals(nature, that.nature) &&
            Objects.equals(stockMinimum, that.stockMinimum) &&
            Objects.equals(quantiteVenteMax, that.quantiteVenteMax) &&
            Objects.equals(horsStock, that.horsStock) &&
            Objects.equals(typeService, that.typeService) &&
            Objects.equals(datePremption, that.datePremption) &&
            Objects.equals(prixHT, that.prixHT) &&
            Objects.equals(tauxTVA, that.tauxTVA) &&
            Objects.equals(prixTTC, that.prixTTC) &&
            Objects.equals(devise, that.devise) &&
            Objects.equals(sourceProduit, that.sourceProduit) &&
            Objects.equals(rating, that.rating) &&
            Objects.equals(eligibleRemise, that.eligibleRemise) &&
            Objects.equals(remise, that.remise) &&
            Objects.equals(debutPromo, that.debutPromo) &&
            Objects.equals(finPromo, that.finPromo) &&
            Objects.equals(creeLe, that.creeLe) &&
            Objects.equals(creePar, that.creePar) &&
            Objects.equals(modifieLe, that.modifieLe) &&
            Objects.equals(modifiePar, that.modifiePar) &&
            Objects.equals(enVedette, that.enVedette) &&
            Objects.equals(imageUrl, that.imageUrl) &&
            Objects.equals(stockId, that.stockId) &&
            Objects.equals(mouvementStockId, that.mouvementStockId) &&
            Objects.equals(commandeLignesId, that.commandeLignesId) &&
            Objects.equals(categorieId, that.categorieId) &&
            Objects.equals(categorieNom, that.categorieNom) &&
            Objects.equals(sousCategorieId, that.sousCategorieId) &&
            Objects.equals(sousCategorieNom, that.sousCategorieNom) &&
            Objects.equals(uniteId, that.uniteId) &&
            Objects.equals(uniteCode, that.uniteCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        reference,
        nom,
        codeBarre,
        description,
        etat,
        marque,
        nature,
        stockMinimum,
        quantiteVenteMax,
        horsStock,
        typeService,
        datePremption,
        prixHT,
        tauxTVA,
        prixTTC,
        devise,
        sourceProduit,
        rating,
        eligibleRemise,
        remise,
        debutPromo,
        finPromo,
        creeLe,
        creePar,
        modifieLe,
        modifiePar,
        enVedette,
        imageUrl,
        stockId,
        mouvementStockId,
        commandeLignesId,
        categorieId,
        categorieNom,
        sousCategorieId,
        sousCategorieNom,
        uniteId,
        uniteCode
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProduitCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (reference != null ? "reference=" + reference + ", " : "") +
                (nom != null ? "nom=" + nom + ", " : "") +
                (codeBarre != null ? "codeBarre=" + codeBarre + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (etat != null ? "etat=" + etat + ", " : "") +
                (marque != null ? "marque=" + marque + ", " : "") +
                (nature != null ? "nature=" + nature + ", " : "") +
                (stockMinimum != null ? "stockMinimum=" + stockMinimum + ", " : "") +
                (quantiteVenteMax != null ? "quantiteVenteMax=" + quantiteVenteMax + ", " : "") +
                (horsStock != null ? "horsStock=" + horsStock + ", " : "") +
                (typeService != null ? "typeService=" + typeService + ", " : "") +
                (datePremption != null ? "datePremption=" + datePremption + ", " : "") +
                (prixHT != null ? "prixHT=" + prixHT + ", " : "") +
                (tauxTVA != null ? "tauxTVA=" + tauxTVA + ", " : "") +
                (prixTTC != null ? "prixTTC=" + prixTTC + ", " : "") +
                (devise != null ? "devise=" + devise + ", " : "") +
                (sourceProduit != null ? "sourceProduit=" + sourceProduit + ", " : "") +
                (rating != null ? "rating=" + rating + ", " : "") +
                (eligibleRemise != null ? "eligibleRemise=" + eligibleRemise + ", " : "") +
                (remise != null ? "remise=" + remise + ", " : "") +
                (debutPromo != null ? "debutPromo=" + debutPromo + ", " : "") +
                (finPromo != null ? "finPromo=" + finPromo + ", " : "") +
                (creeLe != null ? "creeLe=" + creeLe + ", " : "") +
                (creePar != null ? "creePar=" + creePar + ", " : "") +
                (modifieLe != null ? "modifieLe=" + modifieLe + ", " : "") +
                (modifiePar != null ? "modifiePar=" + modifiePar + ", " : "") +
                (enVedette != null ? "enVedette=" + enVedette + ", " : "") +
                (imageUrl != null ? "imageUrl=" + imageUrl + ", " : "") +
                (stockId != null ? "stockId=" + stockId + ", " : "") +
                (mouvementStockId != null ? "mouvementStockId=" + mouvementStockId + ", " : "") +
                (commandeLignesId != null ? "commandeLignesId=" + commandeLignesId + ", " : "") +
                (categorieId != null ? "categorieId=" + categorieId + ", " : "") +
                (categorieNom != null ? "categorieNom=" + categorieNom + ", " : "") +
                (sousCategorieId != null ? "sousCategorieId=" + sousCategorieId + ", " : "") +
                (sousCategorieNom != null ? "sousCategorieId=" + sousCategorieNom + ", " : "") +
                (uniteId != null ? "uniteId=" + uniteId + ", " : "") +
                (uniteCode != null ? "uniteId=" + uniteCode + ", " : "") +
            "}";
    }

}
