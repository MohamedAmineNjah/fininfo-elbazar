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

/**
 * A SousCategorie.
 */
@Entity
@Table(name = "sous_categorie")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "souscategorie")
public class SousCategorie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Column(name = "position", nullable = false)
    private Integer position;

    @Column(name = "etat")
    private Boolean etat;

    
    @Lob
    @Column(name = "image", nullable = true)
    private byte[] image;

    @Column(name = "image_content_type", nullable = true)
    private String imageContentType;

    @Column(name = "cree_le")
    private LocalDate creeLe;

    @Column(name = "cree_par")
    private String creePar;

    @Column(name = "modifie_le")
    private LocalDate modifieLe;

    @Column(name = "modifie_par")
    private String modifiePar;

    @OneToMany(mappedBy = "sousCategorie")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Produit> produits = new HashSet<>();

    @OneToMany(mappedBy = "idSousCategorie")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Stock> stocks = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "sousCategories", allowSetters = true)
    private Categorie categorie;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public SousCategorie nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public SousCategorie description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPosition() {
        return position;
    }

    public SousCategorie position(Integer position) {
        this.position = position;
        return this;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Boolean isEtat() {
        return etat;
    }

    public SousCategorie etat(Boolean etat) {
        this.etat = etat;
        return this;
    }

    public void setEtat(Boolean etat) {
        this.etat = etat;
    }

    public byte[] getImage() {
        return image;
    }

    public SousCategorie image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public SousCategorie imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public LocalDate getCreeLe() {
        return creeLe;
    }

    public SousCategorie creeLe(LocalDate creeLe) {
        this.creeLe = creeLe;
        return this;
    }

    public void setCreeLe(LocalDate creeLe) {
        this.creeLe = creeLe;
    }

    public String getCreePar() {
        return creePar;
    }

    public SousCategorie creePar(String creePar) {
        this.creePar = creePar;
        return this;
    }

    public void setCreePar(String creePar) {
        this.creePar = creePar;
    }

    public LocalDate getModifieLe() {
        return modifieLe;
    }

    public SousCategorie modifieLe(LocalDate modifieLe) {
        this.modifieLe = modifieLe;
        return this;
    }

    public void setModifieLe(LocalDate modifieLe) {
        this.modifieLe = modifieLe;
    }

    public String getModifiePar() {
        return modifiePar;
    }

    public SousCategorie modifiePar(String modifiePar) {
        this.modifiePar = modifiePar;
        return this;
    }

    public void setModifiePar(String modifiePar) {
        this.modifiePar = modifiePar;
    }

    public Set<Produit> getProduits() {
        return produits;
    }

    public SousCategorie produits(Set<Produit> produits) {
        this.produits = produits;
        return this;
    }

    public SousCategorie addProduit(Produit produit) {
        this.produits.add(produit);
        produit.setSousCategorie(this);
        return this;
    }

    public SousCategorie removeProduit(Produit produit) {
        this.produits.remove(produit);
        produit.setSousCategorie(null);
        return this;
    }

    public void setProduits(Set<Produit> produits) {
        this.produits = produits;
    }

    public Set<Stock> getStocks() {
        return stocks;
    }

    public SousCategorie stocks(Set<Stock> stocks) {
        this.stocks = stocks;
        return this;
    }

    public SousCategorie addStock(Stock stock) {
        this.stocks.add(stock);
        stock.setIdSousCategorie(this);
        return this;
    }

    public SousCategorie removeStock(Stock stock) {
        this.stocks.remove(stock);
        stock.setIdSousCategorie(null);
        return this;
    }

    public void setStocks(Set<Stock> stocks) {
        this.stocks = stocks;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public SousCategorie categorie(Categorie categorie) {
        this.categorie = categorie;
        return this;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SousCategorie)) {
            return false;
        }
        return id != null && id.equals(((SousCategorie) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SousCategorie{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", description='" + getDescription() + "'" +
            ", position=" + getPosition() +
            ", etat='" + isEtat() + "'" +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            ", creeLe='" + getCreeLe() + "'" +
            ", creePar='" + getCreePar() + "'" +
            ", modifieLe='" + getModifieLe() + "'" +
            ", modifiePar='" + getModifiePar() + "'" +
            "}";
    }
}
