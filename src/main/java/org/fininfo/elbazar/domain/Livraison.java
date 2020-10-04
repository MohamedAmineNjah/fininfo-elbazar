package org.fininfo.elbazar.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;

import org.fininfo.elbazar.domain.enumeration.ProfileClient;

/**
 * A Livraison.
 */
@Entity
@Table(name = "livraison")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "livraison")
public class Livraison implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "categorie_client")
    private ProfileClient categorieClient;

    @Column(name = "interval_valeur")
    private Double intervalValeur;

    @Column(name = "frais")
    private Double frais;

    @Column(name = "date")
    private Integer date;

    @Column(name = "cree_le")
    private LocalDate creeLe;

    @Column(name = "cree_par")
    private String creePar;

    @Column(name = "modifie_le")
    private LocalDate modifieLe;

    @Column(name = "modifie_par")
    private String modifiePar;

    @NotNull
    @Column(name = "valeur_min", nullable = false)
    private Double valeurMin;

    @NotNull
    @Column(name = "valeur_max", nullable = false)
    private Double valeurMax;

    @Column(name = "date_livraison")
    private LocalDate dateLivraison;

    @ManyToOne
    @JsonIgnoreProperties(value = "livraisons", allowSetters = true)
    private Zone zone;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProfileClient getCategorieClient() {
        return categorieClient;
    }

    public Livraison categorieClient(ProfileClient categorieClient) {
        this.categorieClient = categorieClient;
        return this;
    }

    public void setCategorieClient(ProfileClient categorieClient) {
        this.categorieClient = categorieClient;
    }

    public Double getIntervalValeur() {
        return intervalValeur;
    }

    public Livraison intervalValeur(Double intervalValeur) {
        this.intervalValeur = intervalValeur;
        return this;
    }

    public void setIntervalValeur(Double intervalValeur) {
        this.intervalValeur = intervalValeur;
    }

    public Double getFrais() {
        return frais;
    }

    public Livraison frais(Double frais) {
        this.frais = frais;
        return this;
    }

    public void setFrais(Double frais) {
        this.frais = frais;
    }

    public Integer getDate() {
        return date;
    }

    public Livraison date(Integer date) {
        this.date = date;
        return this;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public LocalDate getCreeLe() {
        return creeLe;
    }

    public Livraison creeLe(LocalDate creeLe) {
        this.creeLe = creeLe;
        return this;
    }

    public void setCreeLe(LocalDate creeLe) {
        this.creeLe = creeLe;
    }

    public String getCreePar() {
        return creePar;
    }

    public Livraison creePar(String creePar) {
        this.creePar = creePar;
        return this;
    }

    public void setCreePar(String creePar) {
        this.creePar = creePar;
    }

    public LocalDate getModifieLe() {
        return modifieLe;
    }

    public Livraison modifieLe(LocalDate modifieLe) {
        this.modifieLe = modifieLe;
        return this;
    }

    public void setModifieLe(LocalDate modifieLe) {
        this.modifieLe = modifieLe;
    }

    public String getModifiePar() {
        return modifiePar;
    }

    public Livraison modifiePar(String modifiePar) {
        this.modifiePar = modifiePar;
        return this;
    }

    public void setModifiePar(String modifiePar) {
        this.modifiePar = modifiePar;
    }

    public Double getValeurMin() {
        return valeurMin;
    }

    public Livraison valeurMin(Double valeurMin) {
        this.valeurMin = valeurMin;
        return this;
    }

    public void setValeurMin(Double valeurMin) {
        this.valeurMin = valeurMin;
    }

    public Double getValeurMax() {
        return valeurMax;
    }

    public Livraison valeurMax(Double valeurMax) {
        this.valeurMax = valeurMax;
        return this;
    }

    public void setValeurMax(Double valeurMax) {
        this.valeurMax = valeurMax;
    }

    public LocalDate getDateLivraison() {
        return dateLivraison;
    }

    public Livraison dateLivraison(LocalDate dateLivraison) {
        this.dateLivraison = dateLivraison;
        return this;
    }

    public void setDateLivraison(LocalDate dateLivraison) {
        this.dateLivraison = dateLivraison;
    }

    public Zone getZone() {
        return zone;
    }

    public Livraison zone(Zone zone) {
        this.zone = zone;
        return this;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Livraison)) {
            return false;
        }
        return id != null && id.equals(((Livraison) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Livraison{" +
            "id=" + getId() +
            ", categorieClient='" + getCategorieClient() + "'" +
            ", intervalValeur=" + getIntervalValeur() +
            ", frais=" + getFrais() +
            ", date=" + getDate() +
            ", creeLe='" + getCreeLe() + "'" +
            ", creePar='" + getCreePar() + "'" +
            ", modifieLe='" + getModifieLe() + "'" +
            ", modifiePar='" + getModifiePar() + "'" +
            ", valeurMin=" + getValeurMin() +
            ", valeurMax=" + getValeurMax() +
            ", dateLivraison='" + getDateLivraison() + "'" +
            "}";
    }
}
