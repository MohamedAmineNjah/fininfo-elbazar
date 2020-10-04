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

import org.fininfo.elbazar.domain.enumeration.StatCmd;

import org.fininfo.elbazar.domain.enumeration.Origine;

import org.fininfo.elbazar.domain.enumeration.Devise;

import org.fininfo.elbazar.domain.enumeration.RegMod;

/**
 * A Commande.
 */
@Entity
@Table(name = "commande")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "commande")
public class Commande implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Pattern(regexp = "^[a-zA-Z0-9]{0,12}$")
    @Column(name = "reference", unique = true)
    private String reference;

    @Column(name = "date")
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut")
    private StatCmd statut;

    @Enumerated(EnumType.STRING)
    @Column(name = "origine")
    private Origine origine;

    @Column(name = "total_ht")
    private Double totalHT;

    @Column(name = "total_tva")
    private Double totalTVA;

    @Column(name = "total_remise")
    private Double totalRemise;

    @Column(name = "tot_ttc")
    private Double totTTC;

    @Enumerated(EnumType.STRING)
    @Column(name = "devise")
    private Devise devise;

    @Column(name = "points_fidelite")
    private Integer pointsFidelite;

    @Enumerated(EnumType.STRING)
    @Column(name = "reglement")
    private RegMod reglement;

    @Column(name = "date_livraison")
    private LocalDate dateLivraison;

    @Column(name = "date_creation")
    private LocalDate dateCreation;

    @Column(name = "date_annulation")
    private LocalDate dateAnnulation;

    @Column(name = "cree_le")
    private LocalDate creeLe;

    @Column(name = "cree_par")
    private String creePar;

    @Column(name = "modifie_le")
    private LocalDate modifieLe;

    @Column(name = "modifie_par")
    private String modifiePar;

    @Column(name = "prenom")
    private String prenom;

    @Column(name = "nom")
    private String nom;

    @Column(name = "adresse")
    private String adresse;

    @Column(name = "gouvernorat")
    private String gouvernorat;

    @Column(name = "ville")
    private String ville;

    @Column(name = "localite")
    private String localite;

    @Column(name = "code_postal")
    private Integer codePostal;

    @Column(name = "indication")
    private String indication;

    @Column(name = "telephone")
    private Integer telephone;

    @Column(name = "mobile")
    private Integer mobile;

    @Column(name = "frais_livraison")
    private Double fraisLivraison;

    @OneToMany(mappedBy = "refCommande")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<MouvementStock> mouvementStocks = new HashSet<>();

    @OneToMany(mappedBy = "refCommande")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<CommandeLignes> commandeLignes = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "commandes", allowSetters = true)
    private Client idClient;

    @ManyToOne
    @JsonIgnoreProperties(value = "commandes", allowSetters = true)
    private Adresse idAdresse;

    @ManyToOne
    @JsonIgnoreProperties(value = "commandes", allowSetters = true)
    private Zone zone;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public Commande reference(String reference) {
        this.reference = reference;
        return this;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public LocalDate getDate() {
        return date;
    }

    public Commande date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public StatCmd getStatut() {
        return statut;
    }

    public Commande statut(StatCmd statut) {
        this.statut = statut;
        return this;
    }

    public void setStatut(StatCmd statut) {
        this.statut = statut;
    }

    public Origine getOrigine() {
        return origine;
    }

    public Commande origine(Origine origine) {
        this.origine = origine;
        return this;
    }

    public void setOrigine(Origine origine) {
        this.origine = origine;
    }

    public Double getTotalHT() {
        return totalHT;
    }

    public Commande totalHT(Double totalHT) {
        this.totalHT = totalHT;
        return this;
    }

    public void setTotalHT(Double totalHT) {
        this.totalHT = totalHT;
    }

    public Double getTotalTVA() {
        return totalTVA;
    }

    public Commande totalTVA(Double totalTVA) {
        this.totalTVA = totalTVA;
        return this;
    }

    public void setTotalTVA(Double totalTVA) {
        this.totalTVA = totalTVA;
    }

    public Double getTotalRemise() {
        return totalRemise;
    }

    public Commande totalRemise(Double totalRemise) {
        this.totalRemise = totalRemise;
        return this;
    }

    public void setTotalRemise(Double totalRemise) {
        this.totalRemise = totalRemise;
    }

    public Double getTotTTC() {
        return totTTC;
    }

    public Commande totTTC(Double totTTC) {
        this.totTTC = totTTC;
        return this;
    }

    public void setTotTTC(Double totTTC) {
        this.totTTC = totTTC;
    }

    public Devise getDevise() {
        return devise;
    }

    public Commande devise(Devise devise) {
        this.devise = devise;
        return this;
    }

    public void setDevise(Devise devise) {
        this.devise = devise;
    }

    public Integer getPointsFidelite() {
        return pointsFidelite;
    }

    public Commande pointsFidelite(Integer pointsFidelite) {
        this.pointsFidelite = pointsFidelite;
        return this;
    }

    public void setPointsFidelite(Integer pointsFidelite) {
        this.pointsFidelite = pointsFidelite;
    }

    public RegMod getReglement() {
        return reglement;
    }

    public Commande reglement(RegMod reglement) {
        this.reglement = reglement;
        return this;
    }

    public void setReglement(RegMod reglement) {
        this.reglement = reglement;
    }

    public LocalDate getDateLivraison() {
        return dateLivraison;
    }

    public Commande dateLivraison(LocalDate dateLivraison) {
        this.dateLivraison = dateLivraison;
        return this;
    }

    public void setDateLivraison(LocalDate dateLivraison) {
        this.dateLivraison = dateLivraison;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public Commande dateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
        return this;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public LocalDate getDateAnnulation() {
        return dateAnnulation;
    }

    public Commande dateAnnulation(LocalDate dateAnnulation) {
        this.dateAnnulation = dateAnnulation;
        return this;
    }

    public void setDateAnnulation(LocalDate dateAnnulation) {
        this.dateAnnulation = dateAnnulation;
    }

    public LocalDate getCreeLe() {
        return creeLe;
    }

    public Commande creeLe(LocalDate creeLe) {
        this.creeLe = creeLe;
        return this;
    }

    public void setCreeLe(LocalDate creeLe) {
        this.creeLe = creeLe;
    }

    public String getCreePar() {
        return creePar;
    }

    public Commande creePar(String creePar) {
        this.creePar = creePar;
        return this;
    }

    public void setCreePar(String creePar) {
        this.creePar = creePar;
    }

    public LocalDate getModifieLe() {
        return modifieLe;
    }

    public Commande modifieLe(LocalDate modifieLe) {
        this.modifieLe = modifieLe;
        return this;
    }

    public void setModifieLe(LocalDate modifieLe) {
        this.modifieLe = modifieLe;
    }

    public String getModifiePar() {
        return modifiePar;
    }

    public Commande modifiePar(String modifiePar) {
        this.modifiePar = modifiePar;
        return this;
    }

    public void setModifiePar(String modifiePar) {
        this.modifiePar = modifiePar;
    }

    public String getPrenom() {
        return prenom;
    }

    public Commande prenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public Commande nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public Commande adresse(String adresse) {
        this.adresse = adresse;
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getGouvernorat() {
        return gouvernorat;
    }

    public Commande gouvernorat(String gouvernorat) {
        this.gouvernorat = gouvernorat;
        return this;
    }

    public void setGouvernorat(String gouvernorat) {
        this.gouvernorat = gouvernorat;
    }

    public String getVille() {
        return ville;
    }

    public Commande ville(String ville) {
        this.ville = ville;
        return this;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getLocalite() {
        return localite;
    }

    public Commande localite(String localite) {
        this.localite = localite;
        return this;
    }

    public void setLocalite(String localite) {
        this.localite = localite;
    }

    public Integer getCodePostal() {
        return codePostal;
    }

    public Commande codePostal(Integer codePostal) {
        this.codePostal = codePostal;
        return this;
    }

    public void setCodePostal(Integer codePostal) {
        this.codePostal = codePostal;
    }

    public String getIndication() {
        return indication;
    }

    public Commande indication(String indication) {
        this.indication = indication;
        return this;
    }

    public void setIndication(String indication) {
        this.indication = indication;
    }

    public Integer getTelephone() {
        return telephone;
    }

    public Commande telephone(Integer telephone) {
        this.telephone = telephone;
        return this;
    }

    public void setTelephone(Integer telephone) {
        this.telephone = telephone;
    }

    public Integer getMobile() {
        return mobile;
    }

    public Commande mobile(Integer mobile) {
        this.mobile = mobile;
        return this;
    }

    public void setMobile(Integer mobile) {
        this.mobile = mobile;
    }

    public Double getFraisLivraison() {
        return fraisLivraison;
    }

    public Commande fraisLivraison(Double fraisLivraison) {
        this.fraisLivraison = fraisLivraison;
        return this;
    }

    public void setFraisLivraison(Double fraisLivraison) {
        this.fraisLivraison = fraisLivraison;
    }

    public Set<MouvementStock> getMouvementStocks() {
        return mouvementStocks;
    }

    public Commande mouvementStocks(Set<MouvementStock> mouvementStocks) {
        this.mouvementStocks = mouvementStocks;
        return this;
    }

    public Commande addMouvementStock(MouvementStock mouvementStock) {
        this.mouvementStocks.add(mouvementStock);
        mouvementStock.setRefCommande(this);
        return this;
    }

    public Commande removeMouvementStock(MouvementStock mouvementStock) {
        this.mouvementStocks.remove(mouvementStock);
        mouvementStock.setRefCommande(null);
        return this;
    }

    public void setMouvementStocks(Set<MouvementStock> mouvementStocks) {
        this.mouvementStocks = mouvementStocks;
    }

    public Set<CommandeLignes> getCommandeLignes() {
        return commandeLignes;
    }

    public Commande commandeLignes(Set<CommandeLignes> commandeLignes) {
        this.commandeLignes = commandeLignes;
        return this;
    }

    public Commande addCommandeLignes(CommandeLignes commandeLignes) {
        this.commandeLignes.add(commandeLignes);
        commandeLignes.setRefCommande(this);
        return this;
    }

    public Commande removeCommandeLignes(CommandeLignes commandeLignes) {
        this.commandeLignes.remove(commandeLignes);
        commandeLignes.setRefCommande(null);
        return this;
    }

    public void setCommandeLignes(Set<CommandeLignes> commandeLignes) {
        this.commandeLignes = commandeLignes;
    }

    public Client getIdClient() {
        return idClient;
    }

    public Commande idClient(Client client) {
        this.idClient = client;
        return this;
    }

    public void setIdClient(Client client) {
        this.idClient = client;
    }

    public Adresse getIdAdresse() {
        return idAdresse;
    }

    public Commande idAdresse(Adresse adresse) {
        this.idAdresse = adresse;
        return this;
    }

    public void setIdAdresse(Adresse adresse) {
        this.idAdresse = adresse;
    }

    public Zone getZone() {
        return zone;
    }

    public Commande zone(Zone zone) {
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
        if (!(o instanceof Commande)) {
            return false;
        }
        return id != null && id.equals(((Commande) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Commande{" +
            "id=" + getId() +
            ", reference='" + getReference() + "'" +
            ", date='" + getDate() + "'" +
            ", statut='" + getStatut() + "'" +
            ", origine='" + getOrigine() + "'" +
            ", totalHT=" + getTotalHT() +
            ", totalTVA=" + getTotalTVA() +
            ", totalRemise=" + getTotalRemise() +
            ", totTTC=" + getTotTTC() +
            ", devise='" + getDevise() + "'" +
            ", pointsFidelite=" + getPointsFidelite() +
            ", reglement='" + getReglement() + "'" +
            ", dateLivraison='" + getDateLivraison() + "'" +
            ", dateCreation='" + getDateCreation() + "'" +
            ", dateAnnulation='" + getDateAnnulation() + "'" +
            ", creeLe='" + getCreeLe() + "'" +
            ", creePar='" + getCreePar() + "'" +
            ", modifieLe='" + getModifieLe() + "'" +
            ", modifiePar='" + getModifiePar() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", nom='" + getNom() + "'" +
            ", adresse='" + getAdresse() + "'" +
            ", gouvernorat='" + getGouvernorat() + "'" +
            ", ville='" + getVille() + "'" +
            ", localite='" + getLocalite() + "'" +
            ", codePostal=" + getCodePostal() +
            ", indication='" + getIndication() + "'" +
            ", telephone=" + getTelephone() +
            ", mobile=" + getMobile() +
            ", fraisLivraison=" + getFraisLivraison() +
            "}";
    }
}
