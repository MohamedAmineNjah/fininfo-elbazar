package org.fininfo.elbazar.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import org.fininfo.elbazar.domain.enumeration.StatCmd;
import org.fininfo.elbazar.domain.enumeration.Origine;
import org.fininfo.elbazar.domain.enumeration.Devise;
import org.fininfo.elbazar.domain.enumeration.RegMod;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link org.fininfo.elbazar.domain.Commande} entity. This class is used
 * in {@link org.fininfo.elbazar.web.rest.CommandeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /commandes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CommandeCriteria implements Serializable, Criteria {
    /**
     * Class for filtering StatCmd
     */
    public static class StatCmdFilter extends Filter<StatCmd> {

        public StatCmdFilter() {
        }

        public StatCmdFilter(StatCmdFilter filter) {
            super(filter);
        }

        @Override
        public StatCmdFilter copy() {
            return new StatCmdFilter(this);
        }

    }
    /**
     * Class for filtering Origine
     */
    public static class OrigineFilter extends Filter<Origine> {

        public OrigineFilter() {
        }

        public OrigineFilter(OrigineFilter filter) {
            super(filter);
        }

        @Override
        public OrigineFilter copy() {
            return new OrigineFilter(this);
        }

    }
    /**
     * Class for filtering Devise
     */
    public static class DeviseFilter extends Filter<Devise> {

        public DeviseFilter() {
        }

        public DeviseFilter(DeviseFilter filter) {
            super(filter);
        }

        @Override
        public DeviseFilter copy() {
            return new DeviseFilter(this);
        }

    }
    /**
     * Class for filtering RegMod
     */
    public static class RegModFilter extends Filter<RegMod> {

        public RegModFilter() {
        }

        public RegModFilter(RegModFilter filter) {
            super(filter);
        }

        @Override
        public RegModFilter copy() {
            return new RegModFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter reference;

    private LocalDateFilter date;

    private StatCmdFilter statut;

    private OrigineFilter origine;

    private DoubleFilter totalHT;

    private DoubleFilter totalTVA;

    private DoubleFilter totalRemise;

    private DoubleFilter totTTC;

    private DeviseFilter devise;

    private IntegerFilter pointsFidelite;

    private RegModFilter reglement;

    private LocalDateFilter dateLivraison;

    private LocalDateFilter dateCreation;

    private LocalDateFilter dateAnnulation;

    private LocalDateFilter creeLe;

    private StringFilter creePar;

    private LocalDateFilter modifieLe;

    private StringFilter modifiePar;

    private StringFilter prenom;

    private StringFilter nom;

    private StringFilter adresse;

    private StringFilter gouvernorat;

    private StringFilter ville;

    private StringFilter localite;

    private IntegerFilter codePostal;

    private StringFilter indication;

    private IntegerFilter telephone;

    private IntegerFilter mobile;

    private DoubleFilter fraisLivraison;

    private LongFilter mouvementStockId;

    private LongFilter commandeLignesId;

    private LongFilter idClientId;

    private StringFilter nomClient;

    private LongFilter idAdresseId;

    private LongFilter zoneId;

    private StringFilter zoneNom;

    public CommandeCriteria() {
    }

    public CommandeCriteria(CommandeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.reference = other.reference == null ? null : other.reference.copy();
        this.date = other.date == null ? null : other.date.copy();
        this.statut = other.statut == null ? null : other.statut.copy();
        this.origine = other.origine == null ? null : other.origine.copy();
        this.totalHT = other.totalHT == null ? null : other.totalHT.copy();
        this.totalTVA = other.totalTVA == null ? null : other.totalTVA.copy();
        this.totalRemise = other.totalRemise == null ? null : other.totalRemise.copy();
        this.totTTC = other.totTTC == null ? null : other.totTTC.copy();
        this.devise = other.devise == null ? null : other.devise.copy();
        this.pointsFidelite = other.pointsFidelite == null ? null : other.pointsFidelite.copy();
        this.reglement = other.reglement == null ? null : other.reglement.copy();
        this.dateLivraison = other.dateLivraison == null ? null : other.dateLivraison.copy();
        this.dateCreation = other.dateCreation == null ? null : other.dateCreation.copy();
        this.dateAnnulation = other.dateAnnulation == null ? null : other.dateAnnulation.copy();
        this.creeLe = other.creeLe == null ? null : other.creeLe.copy();
        this.creePar = other.creePar == null ? null : other.creePar.copy();
        this.modifieLe = other.modifieLe == null ? null : other.modifieLe.copy();
        this.modifiePar = other.modifiePar == null ? null : other.modifiePar.copy();
        this.prenom = other.prenom == null ? null : other.prenom.copy();
        this.nom = other.nom == null ? null : other.nom.copy();
        this.adresse = other.adresse == null ? null : other.adresse.copy();
        this.gouvernorat = other.gouvernorat == null ? null : other.gouvernorat.copy();
        this.ville = other.ville == null ? null : other.ville.copy();
        this.localite = other.localite == null ? null : other.localite.copy();
        this.codePostal = other.codePostal == null ? null : other.codePostal.copy();
        this.indication = other.indication == null ? null : other.indication.copy();
        this.telephone = other.telephone == null ? null : other.telephone.copy();
        this.mobile = other.mobile == null ? null : other.mobile.copy();
        this.fraisLivraison = other.fraisLivraison == null ? null : other.fraisLivraison.copy();
        this.mouvementStockId = other.mouvementStockId == null ? null : other.mouvementStockId.copy();
        this.commandeLignesId = other.commandeLignesId == null ? null : other.commandeLignesId.copy();
        this.idClientId = other.idClientId == null ? null : other.idClientId.copy();
        this.idAdresseId = other.idAdresseId == null ? null : other.idAdresseId.copy();
        this.zoneId = other.zoneId == null ? null : other.zoneId.copy();
    }

    @Override
    public CommandeCriteria copy() {
        return new CommandeCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getReference() {
        return reference;
    }

    public void setReference(StringFilter reference) {
        this.reference = reference;
    }

    public LocalDateFilter getDate() {
        return date;
    }

    public void setDate(LocalDateFilter date) {
        this.date = date;
    }

    public StatCmdFilter getStatut() {
        return statut;
    }

    public void setStatut(StatCmdFilter statut) {
        this.statut = statut;
    }

    public OrigineFilter getOrigine() {
        return origine;
    }

    public void setOrigine(OrigineFilter origine) {
        this.origine = origine;
    }

    public DoubleFilter getTotalHT() {
        return totalHT;
    }

    public void setTotalHT(DoubleFilter totalHT) {
        this.totalHT = totalHT;
    }

    public DoubleFilter getTotalTVA() {
        return totalTVA;
    }

    public void setTotalTVA(DoubleFilter totalTVA) {
        this.totalTVA = totalTVA;
    }

    public DoubleFilter getTotalRemise() {
        return totalRemise;
    }

    public void setTotalRemise(DoubleFilter totalRemise) {
        this.totalRemise = totalRemise;
    }

    public DoubleFilter getTotTTC() {
        return totTTC;
    }

    public void setTotTTC(DoubleFilter totTTC) {
        this.totTTC = totTTC;
    }

    public DeviseFilter getDevise() {
        return devise;
    }

    public void setDevise(DeviseFilter devise) {
        this.devise = devise;
    }

    public IntegerFilter getPointsFidelite() {
        return pointsFidelite;
    }

    public void setPointsFidelite(IntegerFilter pointsFidelite) {
        this.pointsFidelite = pointsFidelite;
    }

    public RegModFilter getReglement() {
        return reglement;
    }

    public void setReglement(RegModFilter reglement) {
        this.reglement = reglement;
    }

    public LocalDateFilter getDateLivraison() {
        return dateLivraison;
    }

    public void setDateLivraison(LocalDateFilter dateLivraison) {
        this.dateLivraison = dateLivraison;
    }

    public LocalDateFilter getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateFilter dateCreation) {
        this.dateCreation = dateCreation;
    }

    public LocalDateFilter getDateAnnulation() {
        return dateAnnulation;
    }

    public void setDateAnnulation(LocalDateFilter dateAnnulation) {
        this.dateAnnulation = dateAnnulation;
    }

    public LocalDateFilter getCreeLe() {
        return creeLe;
    }

    public void setCreeLe(LocalDateFilter creeLe) {
        this.creeLe = creeLe;
    }

    public StringFilter getCreePar() {
        return creePar;
    }

    public void setCreePar(StringFilter creePar) {
        this.creePar = creePar;
    }

    public LocalDateFilter getModifieLe() {
        return modifieLe;
    }

    public void setModifieLe(LocalDateFilter modifieLe) {
        this.modifieLe = modifieLe;
    }

    public StringFilter getModifiePar() {
        return modifiePar;
    }

    public void setModifiePar(StringFilter modifiePar) {
        this.modifiePar = modifiePar;
    }

    public StringFilter getPrenom() {
        return prenom;
    }

    public void setPrenom(StringFilter prenom) {
        this.prenom = prenom;
    }

    public StringFilter getNom() {
        return nom;
    }

    public void setNom(StringFilter nom) {
        this.nom = nom;
    }

    public StringFilter getAdresse() {
        return adresse;
    }

    public void setAdresse(StringFilter adresse) {
        this.adresse = adresse;
    }

    public StringFilter getGouvernorat() {
        return gouvernorat;
    }

    public void setGouvernorat(StringFilter gouvernorat) {
        this.gouvernorat = gouvernorat;
    }

    public StringFilter getVille() {
        return ville;
    }

    public void setVille(StringFilter ville) {
        this.ville = ville;
    }

    public StringFilter getLocalite() {
        return localite;
    }

    public void setLocalite(StringFilter localite) {
        this.localite = localite;
    }

    public IntegerFilter getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(IntegerFilter codePostal) {
        this.codePostal = codePostal;
    }

    public StringFilter getIndication() {
        return indication;
    }

    public void setIndication(StringFilter indication) {
        this.indication = indication;
    }

    public IntegerFilter getTelephone() {
        return telephone;
    }

    public void setTelephone(IntegerFilter telephone) {
        this.telephone = telephone;
    }

    public IntegerFilter getMobile() {
        return mobile;
    }

    public void setMobile(IntegerFilter mobile) {
        this.mobile = mobile;
    }

    public DoubleFilter getFraisLivraison() {
        return fraisLivraison;
    }

    public void setFraisLivraison(DoubleFilter fraisLivraison) {
        this.fraisLivraison = fraisLivraison;
    }

    public LongFilter getMouvementStockId() {
        return mouvementStockId;
    }

    public void setMouvementStockId(LongFilter mouvementStockId) {
        this.mouvementStockId = mouvementStockId;
    }

    public LongFilter getCommandeLignesId() {
        return commandeLignesId;
    }

    public void setCommandeLignesId(LongFilter commandeLignesId) {
        this.commandeLignesId = commandeLignesId;
    }

    public LongFilter getIdClientId() {
        return idClientId;
    }

    public void setIdClientId(LongFilter idClientId) {
        this.idClientId = idClientId;
    }

    public LongFilter getIdAdresseId() {
        return idAdresseId;
    }

    public void setIdAdresseId(LongFilter idAdresseId) {
        this.idAdresseId = idAdresseId;
    }

    public LongFilter getZoneId() {
        return zoneId;
    }

    public void setZoneId(LongFilter zoneId) {
        this.zoneId = zoneId;
    }
    public StringFilter getNomClient() {
        return nomClient;
    }

    public void setNomClient(StringFilter nomClient) {
        this.nomClient = nomClient;
    }

    public StringFilter getZoneNom() {
        return zoneNom;
    }

    public void setZoneNom(StringFilter zoneNom) {
        this.zoneNom = zoneNom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CommandeCriteria that = (CommandeCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(reference, that.reference) &&
            Objects.equals(date, that.date) &&
            Objects.equals(statut, that.statut) &&
            Objects.equals(origine, that.origine) &&
            Objects.equals(totalHT, that.totalHT) &&
            Objects.equals(totalTVA, that.totalTVA) &&
            Objects.equals(totalRemise, that.totalRemise) &&
            Objects.equals(totTTC, that.totTTC) &&
            Objects.equals(devise, that.devise) &&
            Objects.equals(pointsFidelite, that.pointsFidelite) &&
            Objects.equals(reglement, that.reglement) &&
            Objects.equals(dateLivraison, that.dateLivraison) &&
            Objects.equals(dateCreation, that.dateCreation) &&
            Objects.equals(dateAnnulation, that.dateAnnulation) &&
            Objects.equals(creeLe, that.creeLe) &&
            Objects.equals(creePar, that.creePar) &&
            Objects.equals(modifieLe, that.modifieLe) &&
            Objects.equals(modifiePar, that.modifiePar) &&
            Objects.equals(prenom, that.prenom) &&
            Objects.equals(nom, that.nom) &&
            Objects.equals(adresse, that.adresse) &&
            Objects.equals(gouvernorat, that.gouvernorat) &&
            Objects.equals(ville, that.ville) &&
            Objects.equals(localite, that.localite) &&
            Objects.equals(codePostal, that.codePostal) &&
            Objects.equals(indication, that.indication) &&
            Objects.equals(telephone, that.telephone) &&
            Objects.equals(mobile, that.mobile) &&
            Objects.equals(fraisLivraison, that.fraisLivraison) &&
            Objects.equals(mouvementStockId, that.mouvementStockId) &&
            Objects.equals(commandeLignesId, that.commandeLignesId) &&
            Objects.equals(idClientId, that.idClientId) &&
            Objects.equals(idAdresseId, that.idAdresseId) &&
            Objects.equals(zoneId, that.zoneId) &&
            Objects.equals(nomClient, that.nomClient) &&
            Objects.equals(zoneNom, that.zoneNom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        reference,
        date,
        statut,
        origine,
        totalHT,
        totalTVA,
        totalRemise,
        totTTC,
        devise,
        pointsFidelite,
        reglement,
        dateLivraison,
        dateCreation,
        dateAnnulation,
        creeLe,
        creePar,
        modifieLe,
        modifiePar,
        prenom,
        nom,
        adresse,
        gouvernorat,
        ville,
        localite,
        codePostal,
        indication,
        telephone,
        mobile,
        fraisLivraison,
        mouvementStockId,
        commandeLignesId,
        idClientId,
        idAdresseId,
        zoneId,
        nomClient,
        zoneNom
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CommandeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (reference != null ? "reference=" + reference + ", " : "") +
                (date != null ? "date=" + date + ", " : "") +
                (statut != null ? "statut=" + statut + ", " : "") +
                (origine != null ? "origine=" + origine + ", " : "") +
                (totalHT != null ? "totalHT=" + totalHT + ", " : "") +
                (totalTVA != null ? "totalTVA=" + totalTVA + ", " : "") +
                (totalRemise != null ? "totalRemise=" + totalRemise + ", " : "") +
                (totTTC != null ? "totTTC=" + totTTC + ", " : "") +
                (devise != null ? "devise=" + devise + ", " : "") +
                (pointsFidelite != null ? "pointsFidelite=" + pointsFidelite + ", " : "") +
                (reglement != null ? "reglement=" + reglement + ", " : "") +
                (dateLivraison != null ? "dateLivraison=" + dateLivraison + ", " : "") +
                (dateCreation != null ? "dateCreation=" + dateCreation + ", " : "") +
                (dateAnnulation != null ? "dateAnnulation=" + dateAnnulation + ", " : "") +
                (creeLe != null ? "creeLe=" + creeLe + ", " : "") +
                (creePar != null ? "creePar=" + creePar + ", " : "") +
                (modifieLe != null ? "modifieLe=" + modifieLe + ", " : "") +
                (modifiePar != null ? "modifiePar=" + modifiePar + ", " : "") +
                (prenom != null ? "prenom=" + prenom + ", " : "") +
                (nom != null ? "nom=" + nom + ", " : "") +
                (adresse != null ? "adresse=" + adresse + ", " : "") +
                (gouvernorat != null ? "gouvernorat=" + gouvernorat + ", " : "") +
                (ville != null ? "ville=" + ville + ", " : "") +
                (localite != null ? "localite=" + localite + ", " : "") +
                (codePostal != null ? "codePostal=" + codePostal + ", " : "") +
                (indication != null ? "indication=" + indication + ", " : "") +
                (telephone != null ? "telephone=" + telephone + ", " : "") +
                (mobile != null ? "mobile=" + mobile + ", " : "") +
                (fraisLivraison != null ? "fraisLivraison=" + fraisLivraison + ", " : "") +
                (mouvementStockId != null ? "mouvementStockId=" + mouvementStockId + ", " : "") +
                (commandeLignesId != null ? "commandeLignesId=" + commandeLignesId + ", " : "") +
                (idClientId != null ? "idClientId=" + idClientId + ", " : "") +
                (idAdresseId != null ? "idAdresseId=" + idAdresseId + ", " : "") +
                (zoneId != null ? "zoneId=" + zoneId + ", " : "") +
                (zoneNom != null ? "zoneNom=" + zoneNom + ", " : "") +
                (nomClient != null ? "zoneId=" + nomClient + ", " : "") +
            "}";
    }

}
