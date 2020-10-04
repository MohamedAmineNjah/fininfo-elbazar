package org.fininfo.elbazar.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import javax.persistence.Lob;
import org.fininfo.elbazar.domain.enumeration.Devise;
import org.fininfo.elbazar.domain.enumeration.SourcePrd;

/**
 * A DTO for the {@link org.fininfo.elbazar.domain.Produit} entity.
 */
public class ProduitDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Pattern(regexp = "^([\\S]{1,25}$)*")
    private String reference;

    @NotNull
    private String nom;

    private String codeBarre;

    @NotNull
    private String description;

    @NotNull
    private Boolean etat;

    @NotNull
    private String marque;

    private String nature;

    @NotNull
    private Double stockMinimum;

    @NotNull
    private Double quantiteVenteMax;

    private Boolean horsStock;

    private Boolean typeService;

    private LocalDate datePremption;

    @NotNull
    private Double prixHT;

    @NotNull
    private Double tauxTVA;

    @NotNull
    private Double prixTTC;

    private Devise devise;

    @NotNull
    private SourcePrd sourceProduit;

    @Pattern(regexp = "^[1-5]$")
    private String rating;

    private Boolean eligibleRemise;

    private Double remise;

    private LocalDate debutPromo;

    private LocalDate finPromo;

    
    @Lob
    private byte[] image;

    private String imageContentType;
    private LocalDate creeLe;

    private String creePar;

    private LocalDate modifieLe;

    private String modifiePar;

    private Boolean enVedette;

    private String imageUrl;


    private Long categorieId;

    private String categorieNom;

    private Long sousCategorieId;

    private String sousCategorieNom;

    private Long uniteId;

    private String uniteCode;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCodeBarre() {
        return codeBarre;
    }

    public void setCodeBarre(String codeBarre) {
        this.codeBarre = codeBarre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isEtat() {
        return etat;
    }

    public void setEtat(Boolean etat) {
        this.etat = etat;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public Double getStockMinimum() {
        return stockMinimum;
    }

    public void setStockMinimum(Double stockMinimum) {
        this.stockMinimum = stockMinimum;
    }

    public Double getQuantiteVenteMax() {
        return quantiteVenteMax;
    }

    public void setQuantiteVenteMax(Double quantiteVenteMax) {
        this.quantiteVenteMax = quantiteVenteMax;
    }

    public Boolean isHorsStock() {
        return horsStock;
    }

    public void setHorsStock(Boolean horsStock) {
        this.horsStock = horsStock;
    }

    public Boolean isTypeService() {
        return typeService;
    }

    public void setTypeService(Boolean typeService) {
        this.typeService = typeService;
    }

    public LocalDate getDatePremption() {
        return datePremption;
    }

    public void setDatePremption(LocalDate datePremption) {
        this.datePremption = datePremption;
    }

    public Double getPrixHT() {
        return prixHT;
    }

    public void setPrixHT(Double prixHT) {
        this.prixHT = prixHT;
    }

    public Double getTauxTVA() {
        return tauxTVA;
    }

    public void setTauxTVA(Double tauxTVA) {
        this.tauxTVA = tauxTVA;
    }

    public Double getPrixTTC() {
        return prixTTC;
    }

    public void setPrixTTC(Double prixTTC) {
        this.prixTTC = prixTTC;
    }

    public Devise getDevise() {
        return devise;
    }

    public void setDevise(Devise devise) {
        this.devise = devise;
    }

    public SourcePrd getSourceProduit() {
        return sourceProduit;
    }

    public void setSourceProduit(SourcePrd sourceProduit) {
        this.sourceProduit = sourceProduit;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public Boolean isEligibleRemise() {
        return eligibleRemise;
    }

    public void setEligibleRemise(Boolean eligibleRemise) {
        this.eligibleRemise = eligibleRemise;
    }

    public Double getRemise() {
        return remise;
    }

    public void setRemise(Double remise) {
        this.remise = remise;
    }

    public LocalDate getDebutPromo() {
        return debutPromo;
    }

    public void setDebutPromo(LocalDate debutPromo) {
        this.debutPromo = debutPromo;
    }

    public LocalDate getFinPromo() {
        return finPromo;
    }

    public void setFinPromo(LocalDate finPromo) {
        this.finPromo = finPromo;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public LocalDate getCreeLe() {
        return creeLe;
    }

    public void setCreeLe(LocalDate creeLe) {
        this.creeLe = creeLe;
    }

    public String getCreePar() {
        return creePar;
    }

    public void setCreePar(String creePar) {
        this.creePar = creePar;
    }

    public LocalDate getModifieLe() {
        return modifieLe;
    }

    public void setModifieLe(LocalDate modifieLe) {
        this.modifieLe = modifieLe;
    }

    public String getModifiePar() {
        return modifiePar;
    }

    public void setModifiePar(String modifiePar) {
        this.modifiePar = modifiePar;
    }

    public Boolean isEnVedette() {
        return enVedette;
    }

    public void setEnVedette(Boolean enVedette) {
        this.enVedette = enVedette;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getCategorieId() {
        return categorieId;
    }

    public void setCategorieId(Long categorieId) {
        this.categorieId = categorieId;
    }

    public String getCategorieNom() {
        return categorieNom;
    }

    public void setCategorieNom(String categorieNom) {
        this.categorieNom = categorieNom;
    }

    public Long getSousCategorieId() {
        return sousCategorieId;
    }

    public void setSousCategorieId(Long sousCategorieId) {
        this.sousCategorieId = sousCategorieId;
    }

    public String getSousCategorieNom() {
        return sousCategorieNom;
    }

    public void setSousCategorieNom(String sousCategorieNom) {
        this.sousCategorieNom = sousCategorieNom;
    }

    public Long getUniteId() {
        return uniteId;
    }

    public void setUniteId(Long produitUniteId) {
        this.uniteId = produitUniteId;
    }

    public String getUniteCode() {
        return uniteCode;
    }

    public void setUniteCode(String produitUniteCode) {
        this.uniteCode = produitUniteCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProduitDTO)) {
            return false;
        }

        return id != null && id.equals(((ProduitDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProduitDTO{" +
            "id=" + getId() +
            ", reference='" + getReference() + "'" +
            ", nom='" + getNom() + "'" +
            ", codeBarre='" + getCodeBarre() + "'" +
            ", description='" + getDescription() + "'" +
            ", etat='" + isEtat() + "'" +
            ", marque='" + getMarque() + "'" +
            ", nature='" + getNature() + "'" +
            ", stockMinimum=" + getStockMinimum() +
            ", quantiteVenteMax=" + getQuantiteVenteMax() +
            ", horsStock='" + isHorsStock() + "'" +
            ", typeService='" + isTypeService() + "'" +
            ", datePremption='" + getDatePremption() + "'" +
            ", prixHT=" + getPrixHT() +
            ", tauxTVA=" + getTauxTVA() +
            ", prixTTC=" + getPrixTTC() +
            ", devise='" + getDevise() + "'" +
            ", sourceProduit='" + getSourceProduit() + "'" +
            ", rating='" + getRating() + "'" +
            ", eligibleRemise='" + isEligibleRemise() + "'" +
            ", remise=" + getRemise() +
            ", debutPromo='" + getDebutPromo() + "'" +
            ", finPromo='" + getFinPromo() + "'" +
            ", image='" + getImage() + "'" +
            ", creeLe='" + getCreeLe() + "'" +
            ", creePar='" + getCreePar() + "'" +
            ", modifieLe='" + getModifieLe() + "'" +
            ", modifiePar='" + getModifiePar() + "'" +
            ", enVedette='" + isEnVedette() + "'" +
            ", imageUrl='" + getImageUrl() + "'" +
            ", categorieId=" + getCategorieId() +
            ", categorieNom='" + getCategorieNom() + "'" +
            ", sousCategorieId=" + getSousCategorieId() +
            ", sousCategorieNom='" + getSousCategorieNom() + "'" +
            ", uniteId=" + getUniteId() +
            ", uniteCode='" + getUniteCode() + "'" +
            "}";
    }
}
