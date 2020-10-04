package org.fininfo.elbazar.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A CommandeLignes.
 */
@Entity
@Table(name = "commande_lignes")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "commandelignes")
public class CommandeLignes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "quantite")
    private Double quantite;

    @Column(name = "prix_ht")
    private Double prixHT;

    @Column(name = "remise")
    private Double remise;

    @Column(name = "tva")
    private Double tva;

    @Column(name = "prix_ttc")
    private Double prixTTC;

    @Column(name = "cree_le")
    private LocalDate creeLe;

    @Column(name = "cree_par")
    private String creePar;

    @Column(name = "modifie_le")
    private LocalDate modifieLe;

    @Column(name = "modifie_par")
    private String modifiePar;

    @ManyToOne
    @JsonIgnoreProperties(value = "commandeLignes", allowSetters = true)
    private Commande refCommande;

    @ManyToOne
    @JsonIgnoreProperties(value = "commandeLignes", allowSetters = true)
    private Produit refProduit;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getQuantite() {
        return quantite;
    }

    public CommandeLignes quantite(Double quantite) {
        this.quantite = quantite;
        return this;
    }

    public void setQuantite(Double quantite) {
        this.quantite = quantite;
    }

    public Double getPrixHT() {
        return prixHT;
    }

    public CommandeLignes prixHT(Double prixHT) {
        this.prixHT = prixHT;
        return this;
    }

    public void setPrixHT(Double prixHT) {
        this.prixHT = prixHT;
    }

    public Double getRemise() {
        return remise;
    }

    public CommandeLignes remise(Double remise) {
        this.remise = remise;
        return this;
    }

    public void setRemise(Double remise) {
        this.remise = remise;
    }

    public Double getTva() {
        return tva;
    }

    public CommandeLignes tva(Double tva) {
        this.tva = tva;
        return this;
    }

    public void setTva(Double tva) {
        this.tva = tva;
    }

    public Double getPrixTTC() {
        return prixTTC;
    }

    public CommandeLignes prixTTC(Double prixTTC) {
        this.prixTTC = prixTTC;
        return this;
    }

    public void setPrixTTC(Double prixTTC) {
        this.prixTTC = prixTTC;
    }

    public LocalDate getCreeLe() {
        return creeLe;
    }

    public CommandeLignes creeLe(LocalDate creeLe) {
        this.creeLe = creeLe;
        return this;
    }

    public void setCreeLe(LocalDate creeLe) {
        this.creeLe = creeLe;
    }

    public String getCreePar() {
        return creePar;
    }

    public CommandeLignes creePar(String creePar) {
        this.creePar = creePar;
        return this;
    }

    public void setCreePar(String creePar) {
        this.creePar = creePar;
    }

    public LocalDate getModifieLe() {
        return modifieLe;
    }

    public CommandeLignes modifieLe(LocalDate modifieLe) {
        this.modifieLe = modifieLe;
        return this;
    }

    public void setModifieLe(LocalDate modifieLe) {
        this.modifieLe = modifieLe;
    }

    public String getModifiePar() {
        return modifiePar;
    }

    public CommandeLignes modifiePar(String modifiePar) {
        this.modifiePar = modifiePar;
        return this;
    }

    public void setModifiePar(String modifiePar) {
        this.modifiePar = modifiePar;
    }

    public Commande getRefCommande() {
        return refCommande;
    }

    public CommandeLignes refCommande(Commande commande) {
        this.refCommande = commande;
        return this;
    }

    public void setRefCommande(Commande commande) {
        this.refCommande = commande;
    }

    public Produit getRefProduit() {
        return refProduit;
    }

    public CommandeLignes refProduit(Produit produit) {
        this.refProduit = produit;
        return this;
    }

    public void setRefProduit(Produit produit) {
        this.refProduit = produit;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CommandeLignes)) {
            return false;
        }
        return id != null && id.equals(((CommandeLignes) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CommandeLignes{" +
            "id=" + getId() +
            ", quantite=" + getQuantite() +
            ", prixHT=" + getPrixHT() +
            ", remise=" + getRemise() +
            ", tva=" + getTva() +
            ", prixTTC=" + getPrixTTC() +
            ", creeLe='" + getCreeLe() + "'" +
            ", creePar='" + getCreePar() + "'" +
            ", modifieLe='" + getModifieLe() + "'" +
            ", modifiePar='" + getModifiePar() + "'" +
            "}";
    }
}
