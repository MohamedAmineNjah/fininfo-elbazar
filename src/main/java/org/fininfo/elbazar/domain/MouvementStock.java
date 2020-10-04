package org.fininfo.elbazar.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;

import org.fininfo.elbazar.domain.enumeration.TypeMvt;

/**
 * A MouvementStock.
 */
@Entity
@Table(name = "mouvement_stock")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "mouvementstock")
public class MouvementStock implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private TypeMvt type;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "sens")
    private Integer sens;

    @Column(name = "quantite")
    private Double quantite;

    @Column(name = "cree_le")
    private LocalDate creeLe;

    @Column(name = "cree_par")
    private String creePar;

    @Column(name = "modifie_le")
    private LocalDate modifieLe;

    @Column(name = "modifie_par")
    private String modifiePar;

    @Column(name = "reference")
    private String reference;

    @ManyToOne
    @JsonIgnoreProperties(value = "mouvementStocks", allowSetters = true)
    private Produit refProduit;

    @ManyToOne
    @JsonIgnoreProperties(value = "mouvementStocks", allowSetters = true)
    private Commande refCommande;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TypeMvt getType() {
        return type;
    }

    public MouvementStock type(TypeMvt type) {
        this.type = type;
        return this;
    }

    public void setType(TypeMvt type) {
        this.type = type;
    }

    public LocalDate getDate() {
        return date;
    }

    public MouvementStock date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getSens() {
        return sens;
    }

    public MouvementStock sens(Integer sens) {
        this.sens = sens;
        return this;
    }

    public void setSens(Integer sens) {
        this.sens = sens;
    }

    public Double getQuantite() {
        return quantite;
    }

    public MouvementStock quantite(Double quantite) {
        this.quantite = quantite;
        return this;
    }

    public void setQuantite(Double quantite) {
        this.quantite = quantite;
    }

    public LocalDate getCreeLe() {
        return creeLe;
    }

    public MouvementStock creeLe(LocalDate creeLe) {
        this.creeLe = creeLe;
        return this;
    }

    public void setCreeLe(LocalDate creeLe) {
        this.creeLe = creeLe;
    }

    public String getCreePar() {
        return creePar;
    }

    public MouvementStock creePar(String creePar) {
        this.creePar = creePar;
        return this;
    }

    public void setCreePar(String creePar) {
        this.creePar = creePar;
    }

    public LocalDate getModifieLe() {
        return modifieLe;
    }

    public MouvementStock modifieLe(LocalDate modifieLe) {
        this.modifieLe = modifieLe;
        return this;
    }

    public void setModifieLe(LocalDate modifieLe) {
        this.modifieLe = modifieLe;
    }

    public String getModifiePar() {
        return modifiePar;
    }

    public MouvementStock modifiePar(String modifiePar) {
        this.modifiePar = modifiePar;
        return this;
    }

    public void setModifiePar(String modifiePar) {
        this.modifiePar = modifiePar;
    }

    public String getReference() {
        return reference;
    }

    public MouvementStock reference(String reference) {
        this.reference = reference;
        return this;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Produit getRefProduit() {
        return refProduit;
    }

    public MouvementStock refProduit(Produit produit) {
        this.refProduit = produit;
        return this;
    }

    public void setRefProduit(Produit produit) {
        this.refProduit = produit;
    }

    public Commande getRefCommande() {
        return refCommande;
    }

    public MouvementStock refCommande(Commande commande) {
        this.refCommande = commande;
        return this;
    }

    public void setRefCommande(Commande commande) {
        this.refCommande = commande;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MouvementStock)) {
            return false;
        }
        return id != null && id.equals(((MouvementStock) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MouvementStock{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", date='" + getDate() + "'" +
            ", sens=" + getSens() +
            ", quantite=" + getQuantite() +
            ", creeLe='" + getCreeLe() + "'" +
            ", creePar='" + getCreePar() + "'" +
            ", modifieLe='" + getModifieLe() + "'" +
            ", modifiePar='" + getModifiePar() + "'" +
            ", reference='" + getReference() + "'" +
            "}";
    }
}
