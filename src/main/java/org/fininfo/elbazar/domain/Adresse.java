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
 * A Adresse.
 */
@Entity
@Table(name = "adresse")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "adresse")
public class Adresse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "principale")
    private Boolean principale;

    @NotNull
    @Column(name = "prenom", nullable = false)
    private String prenom;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @NotNull
    @Column(name = "adresse", nullable = false)
    private String adresse;

    @NotNull
    @Column(name = "gouvernorat", nullable = false)
    private String gouvernorat;

    @NotNull
    @Column(name = "ville", nullable = false)
    private String ville;

    @NotNull
    @Column(name = "localite", nullable = false)
    private String localite;

    @Column(name = "indication")
    private String indication;

    @Column(name = "telephone")
    private Integer telephone;

    @NotNull
    @Column(name = "mobile", nullable = false)
    private Integer mobile;

    @Column(name = "cree_le")
    private LocalDate creeLe;

    @Column(name = "cree_par")
    private String creePar;

    @Column(name = "modifie_le")
    private LocalDate modifieLe;

    @Column(name = "modifie_par")
    private String modifiePar;

    @OneToMany(mappedBy = "idAdresse")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Commande> commandes = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "adresses", allowSetters = true)
    private Client client;

    @ManyToOne
    @JsonIgnoreProperties(value = "adresses", allowSetters = true)
    private Zone zone;

    @ManyToOne
    @JsonIgnoreProperties(value = "adresses", allowSetters = true)
    private AffectationZone codePostal;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isPrincipale() {
        return principale;
    }

    public Adresse principale(Boolean principale) {
        this.principale = principale;
        return this;
    }

    public void setPrincipale(Boolean principale) {
        this.principale = principale;
    }

    public String getPrenom() {
        return prenom;
    }

    public Adresse prenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public Adresse nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public Adresse adresse(String adresse) {
        this.adresse = adresse;
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getGouvernorat() {
        return gouvernorat;
    }

    public Adresse gouvernorat(String gouvernorat) {
        this.gouvernorat = gouvernorat;
        return this;
    }

    public void setGouvernorat(String gouvernorat) {
        this.gouvernorat = gouvernorat;
    }

    public String getVille() {
        return ville;
    }

    public Adresse ville(String ville) {
        this.ville = ville;
        return this;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getLocalite() {
        return localite;
    }

    public Adresse localite(String localite) {
        this.localite = localite;
        return this;
    }

    public void setLocalite(String localite) {
        this.localite = localite;
    }

    public String getIndication() {
        return indication;
    }

    public Adresse indication(String indication) {
        this.indication = indication;
        return this;
    }

    public void setIndication(String indication) {
        this.indication = indication;
    }

    public Integer getTelephone() {
        return telephone;
    }

    public Adresse telephone(Integer telephone) {
        this.telephone = telephone;
        return this;
    }

    public void setTelephone(Integer telephone) {
        this.telephone = telephone;
    }

    public Integer getMobile() {
        return mobile;
    }

    public Adresse mobile(Integer mobile) {
        this.mobile = mobile;
        return this;
    }

    public void setMobile(Integer mobile) {
        this.mobile = mobile;
    }

    public LocalDate getCreeLe() {
        return creeLe;
    }

    public Adresse creeLe(LocalDate creeLe) {
        this.creeLe = creeLe;
        return this;
    }

    public void setCreeLe(LocalDate creeLe) {
        this.creeLe = creeLe;
    }

    public String getCreePar() {
        return creePar;
    }

    public Adresse creePar(String creePar) {
        this.creePar = creePar;
        return this;
    }

    public void setCreePar(String creePar) {
        this.creePar = creePar;
    }

    public LocalDate getModifieLe() {
        return modifieLe;
    }

    public Adresse modifieLe(LocalDate modifieLe) {
        this.modifieLe = modifieLe;
        return this;
    }

    public void setModifieLe(LocalDate modifieLe) {
        this.modifieLe = modifieLe;
    }

    public String getModifiePar() {
        return modifiePar;
    }

    public Adresse modifiePar(String modifiePar) {
        this.modifiePar = modifiePar;
        return this;
    }

    public void setModifiePar(String modifiePar) {
        this.modifiePar = modifiePar;
    }

    public Set<Commande> getCommandes() {
        return commandes;
    }

    public Adresse commandes(Set<Commande> commandes) {
        this.commandes = commandes;
        return this;
    }

    public Adresse addCommande(Commande commande) {
        this.commandes.add(commande);
        commande.setIdAdresse(this);
        return this;
    }

    public Adresse removeCommande(Commande commande) {
        this.commandes.remove(commande);
        commande.setIdAdresse(null);
        return this;
    }

    public void setCommandes(Set<Commande> commandes) {
        this.commandes = commandes;
    }

    public Client getClient() {
        return client;
    }

    public Adresse client(Client client) {
        this.client = client;
        return this;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Zone getZone() {
        return zone;
    }

    public Adresse zone(Zone zone) {
        this.zone = zone;
        return this;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    public AffectationZone getCodePostal() {
        return codePostal;
    }

    public Adresse codePostal(AffectationZone affectationZone) {
        this.codePostal = affectationZone;
        return this;
    }

    public void setCodePostal(AffectationZone affectationZone) {
        this.codePostal = affectationZone;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Adresse)) {
            return false;
        }
        return id != null && id.equals(((Adresse) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Adresse{" +
            "id=" + getId() +
            ", principale='" + isPrincipale() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", nom='" + getNom() + "'" +
            ", adresse='" + getAdresse() + "'" +
            ", gouvernorat='" + getGouvernorat() + "'" +
            ", ville='" + getVille() + "'" +
            ", localite='" + getLocalite() + "'" +
            ", indication='" + getIndication() + "'" +
            ", telephone=" + getTelephone() +
            ", mobile=" + getMobile() +
            ", creeLe='" + getCreeLe() + "'" +
            ", creePar='" + getCreePar() + "'" +
            ", modifieLe='" + getModifieLe() + "'" +
            ", modifiePar='" + getModifiePar() + "'" +
            "}";
    }
}
