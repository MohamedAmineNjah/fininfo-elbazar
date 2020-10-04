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
 * A Zone.
 */
@Entity
@Table(name = "zone")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "zone")
public class Zone implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Pattern(regexp = "^([\\S]{1,25}$)*")
    @Column(name = "code_zone", unique = true)
    private String codeZone;

    @Column(name = "nom")
    private String nom;

    @Column(name = "cree_le")
    private LocalDate creeLe;

    @Column(name = "cree_par")
    private String creePar;

    @Column(name = "modifie_le")
    private LocalDate modifieLe;

    @Column(name = "modifie_par")
    private String modifiePar;

    @OneToMany(mappedBy = "zone")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Adresse> adresses = new HashSet<>();

    @OneToMany(mappedBy = "zone")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Livraison> livraisons = new HashSet<>();

    @OneToMany(mappedBy = "zone")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<AffectationZone> affectationZones = new HashSet<>();

    @OneToMany(mappedBy = "zone")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Commande> commandes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeZone() {
        return codeZone;
    }

    public Zone codeZone(String codeZone) {
        this.codeZone = codeZone;
        return this;
    }

    public void setCodeZone(String codeZone) {
        this.codeZone = codeZone;
    }

    public String getNom() {
        return nom;
    }

    public Zone nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public LocalDate getCreeLe() {
        return creeLe;
    }

    public Zone creeLe(LocalDate creeLe) {
        this.creeLe = creeLe;
        return this;
    }

    public void setCreeLe(LocalDate creeLe) {
        this.creeLe = creeLe;
    }

    public String getCreePar() {
        return creePar;
    }

    public Zone creePar(String creePar) {
        this.creePar = creePar;
        return this;
    }

    public void setCreePar(String creePar) {
        this.creePar = creePar;
    }

    public LocalDate getModifieLe() {
        return modifieLe;
    }

    public Zone modifieLe(LocalDate modifieLe) {
        this.modifieLe = modifieLe;
        return this;
    }

    public void setModifieLe(LocalDate modifieLe) {
        this.modifieLe = modifieLe;
    }

    public String getModifiePar() {
        return modifiePar;
    }

    public Zone modifiePar(String modifiePar) {
        this.modifiePar = modifiePar;
        return this;
    }

    public void setModifiePar(String modifiePar) {
        this.modifiePar = modifiePar;
    }

    public Set<Adresse> getAdresses() {
        return adresses;
    }

    public Zone adresses(Set<Adresse> adresses) {
        this.adresses = adresses;
        return this;
    }

    public Zone addAdresse(Adresse adresse) {
        this.adresses.add(adresse);
        adresse.setZone(this);
        return this;
    }

    public Zone removeAdresse(Adresse adresse) {
        this.adresses.remove(adresse);
        adresse.setZone(null);
        return this;
    }

    public void setAdresses(Set<Adresse> adresses) {
        this.adresses = adresses;
    }

    public Set<Livraison> getLivraisons() {
        return livraisons;
    }

    public Zone livraisons(Set<Livraison> livraisons) {
        this.livraisons = livraisons;
        return this;
    }

    public Zone addLivraison(Livraison livraison) {
        this.livraisons.add(livraison);
        livraison.setZone(this);
        return this;
    }

    public Zone removeLivraison(Livraison livraison) {
        this.livraisons.remove(livraison);
        livraison.setZone(null);
        return this;
    }

    public void setLivraisons(Set<Livraison> livraisons) {
        this.livraisons = livraisons;
    }

    public Set<AffectationZone> getAffectationZones() {
        return affectationZones;
    }

    public Zone affectationZones(Set<AffectationZone> affectationZones) {
        this.affectationZones = affectationZones;
        return this;
    }

    public Zone addAffectationZone(AffectationZone affectationZone) {
        this.affectationZones.add(affectationZone);
        affectationZone.setZone(this);
        return this;
    }

    public Zone removeAffectationZone(AffectationZone affectationZone) {
        this.affectationZones.remove(affectationZone);
        affectationZone.setZone(null);
        return this;
    }

    public void setAffectationZones(Set<AffectationZone> affectationZones) {
        this.affectationZones = affectationZones;
    }

    public Set<Commande> getCommandes() {
        return commandes;
    }

    public Zone commandes(Set<Commande> commandes) {
        this.commandes = commandes;
        return this;
    }

    public Zone addCommande(Commande commande) {
        this.commandes.add(commande);
        commande.setZone(this);
        return this;
    }

    public Zone removeCommande(Commande commande) {
        this.commandes.remove(commande);
        commande.setZone(null);
        return this;
    }

    public void setCommandes(Set<Commande> commandes) {
        this.commandes = commandes;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Zone)) {
            return false;
        }
        return id != null && id.equals(((Zone) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Zone{" +
            "id=" + getId() +
            ", codeZone='" + getCodeZone() + "'" +
            ", nom='" + getNom() + "'" +
            ", creeLe='" + getCreeLe() + "'" +
            ", creePar='" + getCreePar() + "'" +
            ", modifieLe='" + getModifieLe() + "'" +
            ", modifiePar='" + getModifiePar() + "'" +
            "}";
    }
}
