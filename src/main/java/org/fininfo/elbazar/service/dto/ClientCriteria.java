package org.fininfo.elbazar.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import org.fininfo.elbazar.domain.enumeration.Civilite;
import org.fininfo.elbazar.domain.enumeration.RegMod;
import org.fininfo.elbazar.domain.enumeration.ProfileClient;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link org.fininfo.elbazar.domain.Client} entity. This class is used
 * in {@link org.fininfo.elbazar.web.rest.ClientResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /clients?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ClientCriteria implements Serializable, Criteria {
    /**
     * Class for filtering Civilite
     */
    public static class CiviliteFilter extends Filter<Civilite> {

        public CiviliteFilter() {
        }

        public CiviliteFilter(CiviliteFilter filter) {
            super(filter);
        }

        @Override
        public CiviliteFilter copy() {
            return new CiviliteFilter(this);
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
    /**
     * Class for filtering ProfileClient
     */
    public static class ProfileClientFilter extends Filter<ProfileClient> {

        public ProfileClientFilter() {
        }

        public ProfileClientFilter(ProfileClientFilter filter) {
            super(filter);
        }

        @Override
        public ProfileClientFilter copy() {
            return new ProfileClientFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private CiviliteFilter civilite;

    private StringFilter prenom;

    private StringFilter nom;

    private LocalDateFilter dateDeNaissance;

    private StringFilter email;

    private IntegerFilter mobile;

    private RegModFilter reglement;

    private BooleanFilter etat;

    private LocalDateFilter inscription;

    private LocalDateFilter derniereVisite;

    private DoubleFilter totalAchat;

    private ProfileClientFilter profile;

    private IntegerFilter pointsFidelite;

    private LocalDateFilter creeLe;

    private StringFilter creePar;

    private LocalDateFilter modifieLe;

    private StringFilter modifiePar;

    private LongFilter adresseId;

    private LongFilter commandeId;

    private LongFilter userId;

    public ClientCriteria() {
    }

    public ClientCriteria(ClientCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.civilite = other.civilite == null ? null : other.civilite.copy();
        this.prenom = other.prenom == null ? null : other.prenom.copy();
        this.nom = other.nom == null ? null : other.nom.copy();
        this.dateDeNaissance = other.dateDeNaissance == null ? null : other.dateDeNaissance.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.mobile = other.mobile == null ? null : other.mobile.copy();
        this.reglement = other.reglement == null ? null : other.reglement.copy();
        this.etat = other.etat == null ? null : other.etat.copy();
        this.inscription = other.inscription == null ? null : other.inscription.copy();
        this.derniereVisite = other.derniereVisite == null ? null : other.derniereVisite.copy();
        this.totalAchat = other.totalAchat == null ? null : other.totalAchat.copy();
        this.profile = other.profile == null ? null : other.profile.copy();
        this.pointsFidelite = other.pointsFidelite == null ? null : other.pointsFidelite.copy();
        this.creeLe = other.creeLe == null ? null : other.creeLe.copy();
        this.creePar = other.creePar == null ? null : other.creePar.copy();
        this.modifieLe = other.modifieLe == null ? null : other.modifieLe.copy();
        this.modifiePar = other.modifiePar == null ? null : other.modifiePar.copy();
        this.adresseId = other.adresseId == null ? null : other.adresseId.copy();
        this.commandeId = other.commandeId == null ? null : other.commandeId.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
    }

    @Override
    public ClientCriteria copy() {
        return new ClientCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public CiviliteFilter getCivilite() {
        return civilite;
    }

    public void setCivilite(CiviliteFilter civilite) {
        this.civilite = civilite;
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

    public LocalDateFilter getDateDeNaissance() {
        return dateDeNaissance;
    }

    public void setDateDeNaissance(LocalDateFilter dateDeNaissance) {
        this.dateDeNaissance = dateDeNaissance;
    }

    public StringFilter getEmail() {
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public IntegerFilter getMobile() {
        return mobile;
    }

    public void setMobile(IntegerFilter mobile) {
        this.mobile = mobile;
    }

    public RegModFilter getReglement() {
        return reglement;
    }

    public void setReglement(RegModFilter reglement) {
        this.reglement = reglement;
    }

    public BooleanFilter getEtat() {
        return etat;
    }

    public void setEtat(BooleanFilter etat) {
        this.etat = etat;
    }

    public LocalDateFilter getInscription() {
        return inscription;
    }

    public void setInscription(LocalDateFilter inscription) {
        this.inscription = inscription;
    }

    public LocalDateFilter getDerniereVisite() {
        return derniereVisite;
    }

    public void setDerniereVisite(LocalDateFilter derniereVisite) {
        this.derniereVisite = derniereVisite;
    }

    public DoubleFilter getTotalAchat() {
        return totalAchat;
    }

    public void setTotalAchat(DoubleFilter totalAchat) {
        this.totalAchat = totalAchat;
    }

    public ProfileClientFilter getProfile() {
        return profile;
    }

    public void setProfile(ProfileClientFilter profile) {
        this.profile = profile;
    }

    public IntegerFilter getPointsFidelite() {
        return pointsFidelite;
    }

    public void setPointsFidelite(IntegerFilter pointsFidelite) {
        this.pointsFidelite = pointsFidelite;
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

    public LongFilter getAdresseId() {
        return adresseId;
    }

    public void setAdresseId(LongFilter adresseId) {
        this.adresseId = adresseId;
    }

    public LongFilter getCommandeId() {
        return commandeId;
    }

    public void setCommandeId(LongFilter commandeId) {
        this.commandeId = commandeId;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ClientCriteria that = (ClientCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(civilite, that.civilite) &&
            Objects.equals(prenom, that.prenom) &&
            Objects.equals(nom, that.nom) &&
            Objects.equals(dateDeNaissance, that.dateDeNaissance) &&
            Objects.equals(email, that.email) &&
            Objects.equals(mobile, that.mobile) &&
            Objects.equals(reglement, that.reglement) &&
            Objects.equals(etat, that.etat) &&
            Objects.equals(inscription, that.inscription) &&
            Objects.equals(derniereVisite, that.derniereVisite) &&
            Objects.equals(totalAchat, that.totalAchat) &&
            Objects.equals(profile, that.profile) &&
            Objects.equals(pointsFidelite, that.pointsFidelite) &&
            Objects.equals(creeLe, that.creeLe) &&
            Objects.equals(creePar, that.creePar) &&
            Objects.equals(modifieLe, that.modifieLe) &&
            Objects.equals(modifiePar, that.modifiePar) &&
            Objects.equals(adresseId, that.adresseId) &&
            Objects.equals(commandeId, that.commandeId) &&
            Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        civilite,
        prenom,
        nom,
        dateDeNaissance,
        email,
        mobile,
        reglement,
        etat,
        inscription,
        derniereVisite,
        totalAchat,
        profile,
        pointsFidelite,
        creeLe,
        creePar,
        modifieLe,
        modifiePar,
        adresseId,
        commandeId,
        userId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClientCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (civilite != null ? "civilite=" + civilite + ", " : "") +
                (prenom != null ? "prenom=" + prenom + ", " : "") +
                (nom != null ? "nom=" + nom + ", " : "") +
                (dateDeNaissance != null ? "dateDeNaissance=" + dateDeNaissance + ", " : "") +
                (email != null ? "email=" + email + ", " : "") +
                (mobile != null ? "mobile=" + mobile + ", " : "") +
                (reglement != null ? "reglement=" + reglement + ", " : "") +
                (etat != null ? "etat=" + etat + ", " : "") +
                (inscription != null ? "inscription=" + inscription + ", " : "") +
                (derniereVisite != null ? "derniereVisite=" + derniereVisite + ", " : "") +
                (totalAchat != null ? "totalAchat=" + totalAchat + ", " : "") +
                (profile != null ? "profile=" + profile + ", " : "") +
                (pointsFidelite != null ? "pointsFidelite=" + pointsFidelite + ", " : "") +
                (creeLe != null ? "creeLe=" + creeLe + ", " : "") +
                (creePar != null ? "creePar=" + creePar + ", " : "") +
                (modifieLe != null ? "modifieLe=" + modifieLe + ", " : "") +
                (modifiePar != null ? "modifiePar=" + modifiePar + ", " : "") +
                (adresseId != null ? "adresseId=" + adresseId + ", " : "") +
                (commandeId != null ? "commandeId=" + commandeId + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
            "}";
    }

}
