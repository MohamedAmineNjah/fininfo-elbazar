package org.fininfo.elbazar.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.fininfo.elbazar.domain.enumeration.Devise;

import org.fininfo.elbazar.domain.enumeration.SourcePrd;

/**
 * A Produit.
 */
@Entity
@Table(name = "produit")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "produit")
public class Produit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Pattern(regexp = "^([\\S]{1,25}$)*")
    @Column(name = "reference", nullable = false, unique = true)
    private String reference;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "code_barre")
    private String codeBarre;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Column(name = "etat", nullable = false)
    private Boolean etat;

    @NotNull
    @Column(name = "marque", nullable = false)
    private String marque;

    @Column(name = "nature")
    private String nature;

    @NotNull
    @Column(name = "stock_minimum", nullable = false)
    private Double stockMinimum;

    @NotNull
    @Column(name = "quantite_vente_max", nullable = false)
    private Double quantiteVenteMax;

    @Column(name = "hors_stock")
    private Boolean horsStock;

    @Column(name = "type_service")
    private Boolean typeService;

    @Column(name = "date_premption")
    private LocalDate datePremption;

    @NotNull
    @Column(name = "prix_ht", nullable = false)
    private Double prixHT;

    @NotNull
    @Column(name = "taux_tva", nullable = false)
    private Double tauxTVA;

    @NotNull
    @Column(name = "prix_ttc", nullable = false)
    private Double prixTTC;

    @Enumerated(EnumType.STRING)
    @Column(name = "devise")
    private Devise devise;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "source_produit", nullable = false)
    private SourcePrd sourceProduit;

    @Pattern(regexp = "^[1-5]$")
    @Column(name = "rating")
    private String rating;

    @Column(name = "eligible_remise")
    private Boolean eligibleRemise;

    @Column(name = "remise")
    private Double remise;

    @Column(name = "debut_promo")
    private LocalDate debutPromo;

    @Column(name = "fin_promo")
    private LocalDate finPromo;

    
    @Lob
    @Column(name = "image", nullable = false)
    private byte[] image;

    @Column(name = "image_content_type", nullable = false)
    private String imageContentType;

    @Column(name = "cree_le")
    private LocalDate creeLe;

    @Column(name = "cree_par")
    private String creePar;

    @Column(name = "modifie_le")
    private LocalDate modifieLe;

    @Column(name = "modifie_par")
    private String modifiePar;

    @Column(name = "en_vedette")
    private Boolean enVedette;

    @Column(name = "image_url")
    private String imageUrl;

    @OneToMany(mappedBy = "refProduit")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Stock> stocks = new HashSet<>();

    @OneToMany(mappedBy = "refProduit")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<MouvementStock> mouvementStocks = new HashSet<>();

    @OneToMany(mappedBy = "refProduit")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<CommandeLignes> commandeLignes = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "produits", allowSetters = true)
    private Categorie categorie;

    @ManyToOne
    @JsonIgnoreProperties(value = "produits", allowSetters = true)
    private SousCategorie sousCategorie;

    @ManyToOne
    @JsonIgnoreProperties(value = "produits", allowSetters = true)
    private ProduitUnite unite;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public Produit reference(String reference) {
        this.reference = reference;
        return this;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getNom() {
        return nom;
    }

    public Produit nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCodeBarre() {
        return codeBarre;
    }

    public Produit codeBarre(String codeBarre) {
        this.codeBarre = codeBarre;
        return this;
    }

    public void setCodeBarre(String codeBarre) {
        this.codeBarre = codeBarre;
    }

    public String getDescription() {
        return description;
    }

    public Produit description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isEtat() {
        return etat;
    }

    public Produit etat(Boolean etat) {
        this.etat = etat;
        return this;
    }

    public void setEtat(Boolean etat) {
        this.etat = etat;
    }

    public String getMarque() {
        return marque;
    }

    public Produit marque(String marque) {
        this.marque = marque;
        return this;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getNature() {
        return nature;
    }

    public Produit nature(String nature) {
        this.nature = nature;
        return this;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public Double getStockMinimum() {
        return stockMinimum;
    }

    public Produit stockMinimum(Double stockMinimum) {
        this.stockMinimum = stockMinimum;
        return this;
    }

    public void setStockMinimum(Double stockMinimum) {
        this.stockMinimum = stockMinimum;
    }

    public Double getQuantiteVenteMax() {
        return quantiteVenteMax;
    }

    public Produit quantiteVenteMax(Double quantiteVenteMax) {
        this.quantiteVenteMax = quantiteVenteMax;
        return this;
    }

    public void setQuantiteVenteMax(Double quantiteVenteMax) {
        this.quantiteVenteMax = quantiteVenteMax;
    }

    public Boolean isHorsStock() {
        return horsStock;
    }

    public Produit horsStock(Boolean horsStock) {
        this.horsStock = horsStock;
        return this;
    }

    public void setHorsStock(Boolean horsStock) {
        this.horsStock = horsStock;
    }

    public Boolean isTypeService() {
        return typeService;
    }

    public Produit typeService(Boolean typeService) {
        this.typeService = typeService;
        return this;
    }

    public void setTypeService(Boolean typeService) {
        this.typeService = typeService;
    }

    public LocalDate getDatePremption() {
        return datePremption;
    }

    public Produit datePremption(LocalDate datePremption) {
        this.datePremption = datePremption;
        return this;
    }

    public void setDatePremption(LocalDate datePremption) {
        this.datePremption = datePremption;
    }

    public Double getPrixHT() {
        return prixHT;
    }

    public Produit prixHT(Double prixHT) {
        this.prixHT = prixHT;
        return this;
    }

    public void setPrixHT(Double prixHT) {
        this.prixHT = prixHT;
    }

    public Double getTauxTVA() {
        return tauxTVA;
    }

    public Produit tauxTVA(Double tauxTVA) {
        this.tauxTVA = tauxTVA;
        return this;
    }

    public void setTauxTVA(Double tauxTVA) {
        this.tauxTVA = tauxTVA;
    }

    public Double getPrixTTC() {
        return prixTTC;
    }

    public Produit prixTTC(Double prixTTC) {
        this.prixTTC = prixTTC;
        return this;
    }

    public void setPrixTTC(Double prixTTC) {
        this.prixTTC = prixTTC;
    }

    public Devise getDevise() {
        return devise;
    }

    public Produit devise(Devise devise) {
        this.devise = devise;
        return this;
    }

    public void setDevise(Devise devise) {
        this.devise = devise;
    }

    public SourcePrd getSourceProduit() {
        return sourceProduit;
    }

    public Produit sourceProduit(SourcePrd sourceProduit) {
        this.sourceProduit = sourceProduit;
        return this;
    }

    public void setSourceProduit(SourcePrd sourceProduit) {
        this.sourceProduit = sourceProduit;
    }

    public String getRating() {
        return rating;
    }

    public Produit rating(String rating) {
        this.rating = rating;
        return this;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public Boolean isEligibleRemise() {
        return eligibleRemise;
    }

    public Produit eligibleRemise(Boolean eligibleRemise) {
        this.eligibleRemise = eligibleRemise;
        return this;
    }

    public void setEligibleRemise(Boolean eligibleRemise) {
        this.eligibleRemise = eligibleRemise;
    }

    public Double getRemise() {
        return remise;
    }

    public Produit remise(Double remise) {
        this.remise = remise;
        return this;
    }

    public void setRemise(Double remise) {
        this.remise = remise;
    }

    public LocalDate getDebutPromo() {
        return debutPromo;
    }

    public Produit debutPromo(LocalDate debutPromo) {
        this.debutPromo = debutPromo;
        return this;
    }

    public void setDebutPromo(LocalDate debutPromo) {
        this.debutPromo = debutPromo;
    }

    public LocalDate getFinPromo() {
        return finPromo;
    }

    public Produit finPromo(LocalDate finPromo) {
        this.finPromo = finPromo;
        return this;
    }

    public void setFinPromo(LocalDate finPromo) {
        this.finPromo = finPromo;
    }

    public byte[] getImage() {
        return image;
    }

    public Produit image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public Produit imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public LocalDate getCreeLe() {
        return creeLe;
    }

    public Produit creeLe(LocalDate creeLe) {
        this.creeLe = creeLe;
        return this;
    }

    public void setCreeLe(LocalDate creeLe) {
        this.creeLe = creeLe;
    }

    public String getCreePar() {
        return creePar;
    }

    public Produit creePar(String creePar) {
        this.creePar = creePar;
        return this;
    }

    public void setCreePar(String creePar) {
        this.creePar = creePar;
    }

    public LocalDate getModifieLe() {
        return modifieLe;
    }

    public Produit modifieLe(LocalDate modifieLe) {
        this.modifieLe = modifieLe;
        return this;
    }

    public void setModifieLe(LocalDate modifieLe) {
        this.modifieLe = modifieLe;
    }

    public String getModifiePar() {
        return modifiePar;
    }

    public Produit modifiePar(String modifiePar) {
        this.modifiePar = modifiePar;
        return this;
    }

    public void setModifiePar(String modifiePar) {
        this.modifiePar = modifiePar;
    }

    public Boolean isEnVedette() {
        return enVedette;
    }

    public Produit enVedette(Boolean enVedette) {
        this.enVedette = enVedette;
        return this;
    }

    public void setEnVedette(Boolean enVedette) {
        this.enVedette = enVedette;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Produit imageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Set<Stock> getStocks() {
        return stocks;
    }

    public Produit stocks(Set<Stock> stocks) {
        this.stocks = stocks;
        return this;
    }

    public Produit addStock(Stock stock) {
        this.stocks.add(stock);
        stock.setRefProduit(this);
        return this;
    }

    public Produit removeStock(Stock stock) {
        this.stocks.remove(stock);
        stock.setRefProduit(null);
        return this;
    }

    public void setStocks(Set<Stock> stocks) {
        this.stocks = stocks;
    }

    public Set<MouvementStock> getMouvementStocks() {
        return mouvementStocks;
    }

    public Produit mouvementStocks(Set<MouvementStock> mouvementStocks) {
        this.mouvementStocks = mouvementStocks;
        return this;
    }

    public Produit addMouvementStock(MouvementStock mouvementStock) {
        this.mouvementStocks.add(mouvementStock);
        mouvementStock.setRefProduit(this);
        return this;
    }

    public Produit removeMouvementStock(MouvementStock mouvementStock) {
        this.mouvementStocks.remove(mouvementStock);
        mouvementStock.setRefProduit(null);
        return this;
    }

    public void setMouvementStocks(Set<MouvementStock> mouvementStocks) {
        this.mouvementStocks = mouvementStocks;
    }

    public Set<CommandeLignes> getCommandeLignes() {
        return commandeLignes;
    }

    public Produit commandeLignes(Set<CommandeLignes> commandeLignes) {
        this.commandeLignes = commandeLignes;
        return this;
    }

    public Produit addCommandeLignes(CommandeLignes commandeLignes) {
        this.commandeLignes.add(commandeLignes);
        commandeLignes.setRefProduit(this);
        return this;
    }

    public Produit removeCommandeLignes(CommandeLignes commandeLignes) {
        this.commandeLignes.remove(commandeLignes);
        commandeLignes.setRefProduit(null);
        return this;
    }

    public void setCommandeLignes(Set<CommandeLignes> commandeLignes) {
        this.commandeLignes = commandeLignes;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public Produit categorie(Categorie categorie) {
        this.categorie = categorie;
        return this;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public SousCategorie getSousCategorie() {
        return sousCategorie;
    }

    public Produit sousCategorie(SousCategorie sousCategorie) {
        this.sousCategorie = sousCategorie;
        return this;
    }

    public void setSousCategorie(SousCategorie sousCategorie) {
        this.sousCategorie = sousCategorie;
    }

    public ProduitUnite getUnite() {
        return unite;
    }

    public Produit unite(ProduitUnite produitUnite) {
        this.unite = produitUnite;
        return this;
    }

    public void setUnite(ProduitUnite produitUnite) {
        this.unite = produitUnite;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Produit)) {
            return false;
        }
        return id != null && id.equals(((Produit) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Produit{" +
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
            ", imageContentType='" + getImageContentType() + "'" +
            ", creeLe='" + getCreeLe() + "'" +
            ", creePar='" + getCreePar() + "'" +
            ", modifieLe='" + getModifieLe() + "'" +
            ", modifiePar='" + getModifiePar() + "'" +
            ", enVedette='" + isEnVedette() + "'" +
            ", imageUrl='" + getImageUrl() + "'" +
            "}";
    }
}
