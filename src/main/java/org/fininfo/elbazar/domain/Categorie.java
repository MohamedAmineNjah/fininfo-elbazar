package org.fininfo.elbazar.domain;

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
 * A Categorie.
 */
@Entity
@Table(name = "categorie")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "categorie")
public class Categorie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nom", nullable = false, unique = true)
    private String nom;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Column(name = "position", nullable = false, unique = true)
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

    @OneToMany(mappedBy = "categorie")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<SousCategorie> sousCategories = new HashSet<>();

    @OneToMany(mappedBy = "categorie")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Produit> produits = new HashSet<>();

    @OneToMany(mappedBy = "idCategorie")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Stock> stocks = new HashSet<>();

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

    public Categorie nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public Categorie description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPosition() {
        return position;
    }

    public Categorie position(Integer position) {
        this.position = position;
        return this;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Boolean isEtat() {
        return etat;
    }

    public Categorie etat(Boolean etat) {
        this.etat = etat;
        return this;
    }

    public void setEtat(Boolean etat) {
        this.etat = etat;
    }

    public byte[] getImage() {
        return image;
    }

    public Categorie image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public Categorie imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public LocalDate getCreeLe() {
        return creeLe;
    }

    public Categorie creeLe(LocalDate creeLe) {
        this.creeLe = creeLe;
        return this;
    }

    public void setCreeLe(LocalDate creeLe) {
        this.creeLe = creeLe;
    }

    public String getCreePar() {
        return creePar;
    }

    public Categorie creePar(String creePar) {
        this.creePar = creePar;
        return this;
    }

    public void setCreePar(String creePar) {
        this.creePar = creePar;
    }

    public LocalDate getModifieLe() {
        return modifieLe;
    }

    public Categorie modifieLe(LocalDate modifieLe) {
        this.modifieLe = modifieLe;
        return this;
    }

    public void setModifieLe(LocalDate modifieLe) {
        this.modifieLe = modifieLe;
    }

    public String getModifiePar() {
        return modifiePar;
    }

    public Categorie modifiePar(String modifiePar) {
        this.modifiePar = modifiePar;
        return this;
    }

    public void setModifiePar(String modifiePar) {
        this.modifiePar = modifiePar;
    }

    public Set<SousCategorie> getSousCategories() {
        return sousCategories;
    }

    public Categorie sousCategories(Set<SousCategorie> sousCategories) {
        this.sousCategories = sousCategories;
        return this;
    }

    public Categorie addSousCategorie(SousCategorie sousCategorie) {
        this.sousCategories.add(sousCategorie);
        sousCategorie.setCategorie(this);
        return this;
    }

    public Categorie removeSousCategorie(SousCategorie sousCategorie) {
        this.sousCategories.remove(sousCategorie);
        sousCategorie.setCategorie(null);
        return this;
    }

    public void setSousCategories(Set<SousCategorie> sousCategories) {
        this.sousCategories = sousCategories;
    }

    public Set<Produit> getProduits() {
        return produits;
    }

    public Categorie produits(Set<Produit> produits) {
        this.produits = produits;
        return this;
    }

    public Categorie addProduit(Produit produit) {
        this.produits.add(produit);
        produit.setCategorie(this);
        return this;
    }

    public Categorie removeProduit(Produit produit) {
        this.produits.remove(produit);
        produit.setCategorie(null);
        return this;
    }

    public void setProduits(Set<Produit> produits) {
        this.produits = produits;
    }

    public Set<Stock> getStocks() {
        return stocks;
    }

    public Categorie stocks(Set<Stock> stocks) {
        this.stocks = stocks;
        return this;
    }

    public Categorie addStock(Stock stock) {
        this.stocks.add(stock);
        stock.setIdCategorie(this);
        return this;
    }

    public Categorie removeStock(Stock stock) {
        this.stocks.remove(stock);
        stock.setIdCategorie(null);
        return this;
    }

    public void setStocks(Set<Stock> stocks) {
        this.stocks = stocks;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Categorie)) {
            return false;
        }
        return id != null && id.equals(((Categorie) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Categorie{" +
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
