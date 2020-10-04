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
 * A AffectationZone.
 */
@Entity
@Table(name = "affectation_zone")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "affectationzone")
public class AffectationZone implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "gouvernorat", nullable = false)
    private String gouvernorat;

    @NotNull
    @Column(name = "ville", nullable = false)
    private String ville;

    @NotNull
    @Column(name = "localite", nullable = false)
    private String localite;

    @NotNull
    @Column(name = "code_postal", nullable = false)
    private Integer codePostal;

    @Column(name = "modifie_le")
    private LocalDate modifieLe;

    @Column(name = "modifie_par")
    private String modifiePar;

    @Column(name = "id_ville")
    private Integer idVille;

    @OneToMany(mappedBy = "codePostal")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Adresse> adresses = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "affectationZones", allowSetters = true)
    private Zone zone;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGouvernorat() {
        return gouvernorat;
    }

    public AffectationZone gouvernorat(String gouvernorat) {
        this.gouvernorat = gouvernorat;
        return this;
    }

    public void setGouvernorat(String gouvernorat) {
        this.gouvernorat = gouvernorat;
    }

    public String getVille() {
        return ville;
    }

    public AffectationZone ville(String ville) {
        this.ville = ville;
        return this;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getLocalite() {
        return localite;
    }

    public AffectationZone localite(String localite) {
        this.localite = localite;
        return this;
    }

    public void setLocalite(String localite) {
        this.localite = localite;
    }

    public Integer getCodePostal() {
        return codePostal;
    }

    public AffectationZone codePostal(Integer codePostal) {
        this.codePostal = codePostal;
        return this;
    }

    public void setCodePostal(Integer codePostal) {
        this.codePostal = codePostal;
    }

    public LocalDate getModifieLe() {
        return modifieLe;
    }

    public AffectationZone modifieLe(LocalDate modifieLe) {
        this.modifieLe = modifieLe;
        return this;
    }

    public void setModifieLe(LocalDate modifieLe) {
        this.modifieLe = modifieLe;
    }

    public String getModifiePar() {
        return modifiePar;
    }

    public AffectationZone modifiePar(String modifiePar) {
        this.modifiePar = modifiePar;
        return this;
    }

    public void setModifiePar(String modifiePar) {
        this.modifiePar = modifiePar;
    }

    public Integer getIdVille() {
        return idVille;
    }

    public AffectationZone idVille(Integer idVille) {
        this.idVille = idVille;
        return this;
    }

    public void setIdVille(Integer idVille) {
        this.idVille = idVille;
    }

    public Set<Adresse> getAdresses() {
        return adresses;
    }

    public AffectationZone adresses(Set<Adresse> adresses) {
        this.adresses = adresses;
        return this;
    }

    public AffectationZone addAdresse(Adresse adresse) {
        this.adresses.add(adresse);
        adresse.setCodePostal(this);
        return this;
    }

    public AffectationZone removeAdresse(Adresse adresse) {
        this.adresses.remove(adresse);
        adresse.setCodePostal(null);
        return this;
    }

    public void setAdresses(Set<Adresse> adresses) {
        this.adresses = adresses;
    }

    public Zone getZone() {
        return zone;
    }

    public AffectationZone zone(Zone zone) {
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
        if (!(o instanceof AffectationZone)) {
            return false;
        }
        return id != null && id.equals(((AffectationZone) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AffectationZone{" +
            "id=" + getId() +
            ", gouvernorat='" + getGouvernorat() + "'" +
            ", ville='" + getVille() + "'" +
            ", localite='" + getLocalite() + "'" +
            ", codePostal=" + getCodePostal() +
            ", modifieLe='" + getModifieLe() + "'" +
            ", modifiePar='" + getModifiePar() + "'" +
            ", idVille=" + getIdVille() +
            "}";
    }
}
