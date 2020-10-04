package org.fininfo.elbazar.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;

import org.fininfo.elbazar.domain.enumeration.Devise;

/**
 * A GestionFidelite.
 */
@Entity
@Table(name = "gestion_fidelite")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "gestionfidelite")
public class GestionFidelite implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nom", nullable = false, unique = true)
    private String nom;

    @NotNull
    @Column(name = "points", nullable = false)
    private Integer points;

    @NotNull
    @Column(name = "valeur", nullable = false)
    private Double valeur;

    @NotNull
    @Column(name = "silver_min", nullable = false)
    private Integer silverMin;

    @NotNull
    @Column(name = "silver_max", nullable = false)
    private Integer silverMax;

    @NotNull
    @Column(name = "gold_min", nullable = false)
    private Integer goldMin;

    @NotNull
    @Column(name = "gold_max", nullable = false)
    private Integer goldMax;

    @NotNull
    @Column(name = "platinium_min", nullable = false)
    private Integer platiniumMin;

    @NotNull
    @Column(name = "platinium_max", nullable = false)
    private Integer platiniumMax;

    @Enumerated(EnumType.STRING)
    @Column(name = "devise")
    private Devise devise;

    @Column(name = "etat")
    private Boolean etat;

    @Column(name = "cree_le")
    private LocalDate creeLe;

    @Column(name = "cree_par")
    private String creePar;

    @Column(name = "modifie_le")
    private LocalDate modifieLe;

    @Column(name = "modifie_par")
    private String modifiePar;

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

    public GestionFidelite nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Integer getPoints() {
        return points;
    }

    public GestionFidelite points(Integer points) {
        this.points = points;
        return this;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Double getValeur() {
        return valeur;
    }

    public GestionFidelite valeur(Double valeur) {
        this.valeur = valeur;
        return this;
    }

    public void setValeur(Double valeur) {
        this.valeur = valeur;
    }

    public Integer getSilverMin() {
        return silverMin;
    }

    public GestionFidelite silverMin(Integer silverMin) {
        this.silverMin = silverMin;
        return this;
    }

    public void setSilverMin(Integer silverMin) {
        this.silverMin = silverMin;
    }

    public Integer getSilverMax() {
        return silverMax;
    }

    public GestionFidelite silverMax(Integer silverMax) {
        this.silverMax = silverMax;
        return this;
    }

    public void setSilverMax(Integer silverMax) {
        this.silverMax = silverMax;
    }

    public Integer getGoldMin() {
        return goldMin;
    }

    public GestionFidelite goldMin(Integer goldMin) {
        this.goldMin = goldMin;
        return this;
    }

    public void setGoldMin(Integer goldMin) {
        this.goldMin = goldMin;
    }

    public Integer getGoldMax() {
        return goldMax;
    }

    public GestionFidelite goldMax(Integer goldMax) {
        this.goldMax = goldMax;
        return this;
    }

    public void setGoldMax(Integer goldMax) {
        this.goldMax = goldMax;
    }

    public Integer getPlatiniumMin() {
        return platiniumMin;
    }

    public GestionFidelite platiniumMin(Integer platiniumMin) {
        this.platiniumMin = platiniumMin;
        return this;
    }

    public void setPlatiniumMin(Integer platiniumMin) {
        this.platiniumMin = platiniumMin;
    }

    public Integer getPlatiniumMax() {
        return platiniumMax;
    }

    public GestionFidelite platiniumMax(Integer platiniumMax) {
        this.platiniumMax = platiniumMax;
        return this;
    }

    public void setPlatiniumMax(Integer platiniumMax) {
        this.platiniumMax = platiniumMax;
    }

    public Devise getDevise() {
        return devise;
    }

    public GestionFidelite devise(Devise devise) {
        this.devise = devise;
        return this;
    }

    public void setDevise(Devise devise) {
        this.devise = devise;
    }

    public Boolean isEtat() {
        return etat;
    }

    public GestionFidelite etat(Boolean etat) {
        this.etat = etat;
        return this;
    }

    public void setEtat(Boolean etat) {
        this.etat = etat;
    }

    public LocalDate getCreeLe() {
        return creeLe;
    }

    public GestionFidelite creeLe(LocalDate creeLe) {
        this.creeLe = creeLe;
        return this;
    }

    public void setCreeLe(LocalDate creeLe) {
        this.creeLe = creeLe;
    }

    public String getCreePar() {
        return creePar;
    }

    public GestionFidelite creePar(String creePar) {
        this.creePar = creePar;
        return this;
    }

    public void setCreePar(String creePar) {
        this.creePar = creePar;
    }

    public LocalDate getModifieLe() {
        return modifieLe;
    }

    public GestionFidelite modifieLe(LocalDate modifieLe) {
        this.modifieLe = modifieLe;
        return this;
    }

    public void setModifieLe(LocalDate modifieLe) {
        this.modifieLe = modifieLe;
    }

    public String getModifiePar() {
        return modifiePar;
    }

    public GestionFidelite modifiePar(String modifiePar) {
        this.modifiePar = modifiePar;
        return this;
    }

    public void setModifiePar(String modifiePar) {
        this.modifiePar = modifiePar;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GestionFidelite)) {
            return false;
        }
        return id != null && id.equals(((GestionFidelite) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GestionFidelite{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", points=" + getPoints() +
            ", valeur=" + getValeur() +
            ", silverMin=" + getSilverMin() +
            ", silverMax=" + getSilverMax() +
            ", goldMin=" + getGoldMin() +
            ", goldMax=" + getGoldMax() +
            ", platiniumMin=" + getPlatiniumMin() +
            ", platiniumMax=" + getPlatiniumMax() +
            ", devise='" + getDevise() + "'" +
            ", etat='" + isEtat() + "'" +
            ", creeLe='" + getCreeLe() + "'" +
            ", creePar='" + getCreePar() + "'" +
            ", modifieLe='" + getModifieLe() + "'" +
            ", modifiePar='" + getModifiePar() + "'" +
            "}";
    }
}
