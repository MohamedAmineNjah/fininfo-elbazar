package org.fininfo.elbazar.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A ProduitUnite.
 */
@Entity
@Table(name = "produit_unite")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "produitunite")
public class ProduitUnite implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Pattern(regexp = "^([\\S]{1,10}$)*")
    @Column(name = "code", unique = true)
    private String code;

    @Column(name = "nom")
    private String nom;

    @OneToMany(mappedBy = "unite")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Produit> produits = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public ProduitUnite code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNom() {
        return nom;
    }

    public ProduitUnite nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Set<Produit> getProduits() {
        return produits;
    }

    public ProduitUnite produits(Set<Produit> produits) {
        this.produits = produits;
        return this;
    }

    public ProduitUnite addProduit(Produit produit) {
        this.produits.add(produit);
        produit.setUnite(this);
        return this;
    }

    public ProduitUnite removeProduit(Produit produit) {
        this.produits.remove(produit);
        produit.setUnite(null);
        return this;
    }

    public void setProduits(Set<Produit> produits) {
        this.produits = produits;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProduitUnite)) {
            return false;
        }
        return id != null && id.equals(((ProduitUnite) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProduitUnite{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", nom='" + getNom() + "'" +
            "}";
    }
}
