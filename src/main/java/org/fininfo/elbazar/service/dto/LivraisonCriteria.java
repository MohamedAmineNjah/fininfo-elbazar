package org.fininfo.elbazar.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
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
 * Criteria class for the {@link org.fininfo.elbazar.domain.Livraison} entity. This class is used
 * in {@link org.fininfo.elbazar.web.rest.LivraisonResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /livraisons?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class LivraisonCriteria implements Serializable, Criteria {
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

    private ProfileClientFilter categorieClient;

    private DoubleFilter intervalValeur;

    private DoubleFilter frais;

    private IntegerFilter date;

    private LocalDateFilter creeLe;

    private StringFilter creePar;

    private LocalDateFilter modifieLe;

    private StringFilter modifiePar;

    private DoubleFilter valeurMin;

    private DoubleFilter valeurMax;

    private LocalDateFilter dateLivraison;

    private LongFilter zoneId;

    public LivraisonCriteria() {
    }

    public LivraisonCriteria(LivraisonCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.categorieClient = other.categorieClient == null ? null : other.categorieClient.copy();
        this.intervalValeur = other.intervalValeur == null ? null : other.intervalValeur.copy();
        this.frais = other.frais == null ? null : other.frais.copy();
        this.date = other.date == null ? null : other.date.copy();
        this.creeLe = other.creeLe == null ? null : other.creeLe.copy();
        this.creePar = other.creePar == null ? null : other.creePar.copy();
        this.modifieLe = other.modifieLe == null ? null : other.modifieLe.copy();
        this.modifiePar = other.modifiePar == null ? null : other.modifiePar.copy();
        this.valeurMin = other.valeurMin == null ? null : other.valeurMin.copy();
        this.valeurMax = other.valeurMax == null ? null : other.valeurMax.copy();
        this.dateLivraison = other.dateLivraison == null ? null : other.dateLivraison.copy();
        this.zoneId = other.zoneId == null ? null : other.zoneId.copy();
    }

    @Override
    public LivraisonCriteria copy() {
        return new LivraisonCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public ProfileClientFilter getCategorieClient() {
        return categorieClient;
    }

    public void setCategorieClient(ProfileClientFilter categorieClient) {
        this.categorieClient = categorieClient;
    }

    public DoubleFilter getIntervalValeur() {
        return intervalValeur;
    }

    public void setIntervalValeur(DoubleFilter intervalValeur) {
        this.intervalValeur = intervalValeur;
    }

    public DoubleFilter getFrais() {
        return frais;
    }

    public void setFrais(DoubleFilter frais) {
        this.frais = frais;
    }

    public IntegerFilter getDate() {
        return date;
    }

    public void setDate(IntegerFilter date) {
        this.date = date;
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

    public DoubleFilter getValeurMin() {
        return valeurMin;
    }

    public void setValeurMin(DoubleFilter valeurMin) {
        this.valeurMin = valeurMin;
    }

    public DoubleFilter getValeurMax() {
        return valeurMax;
    }

    public void setValeurMax(DoubleFilter valeurMax) {
        this.valeurMax = valeurMax;
    }

    public LocalDateFilter getDateLivraison() {
        return dateLivraison;
    }

    public void setDateLivraison(LocalDateFilter dateLivraison) {
        this.dateLivraison = dateLivraison;
    }

    public LongFilter getZoneId() {
        return zoneId;
    }

    public void setZoneId(LongFilter zoneId) {
        this.zoneId = zoneId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final LivraisonCriteria that = (LivraisonCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(categorieClient, that.categorieClient) &&
            Objects.equals(intervalValeur, that.intervalValeur) &&
            Objects.equals(frais, that.frais) &&
            Objects.equals(date, that.date) &&
            Objects.equals(creeLe, that.creeLe) &&
            Objects.equals(creePar, that.creePar) &&
            Objects.equals(modifieLe, that.modifieLe) &&
            Objects.equals(modifiePar, that.modifiePar) &&
            Objects.equals(valeurMin, that.valeurMin) &&
            Objects.equals(valeurMax, that.valeurMax) &&
            Objects.equals(dateLivraison, that.dateLivraison) &&
            Objects.equals(zoneId, that.zoneId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        categorieClient,
        intervalValeur,
        frais,
        date,
        creeLe,
        creePar,
        modifieLe,
        modifiePar,
        valeurMin,
        valeurMax,
        dateLivraison,
        zoneId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LivraisonCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (categorieClient != null ? "categorieClient=" + categorieClient + ", " : "") +
                (intervalValeur != null ? "intervalValeur=" + intervalValeur + ", " : "") +
                (frais != null ? "frais=" + frais + ", " : "") +
                (date != null ? "date=" + date + ", " : "") +
                (creeLe != null ? "creeLe=" + creeLe + ", " : "") +
                (creePar != null ? "creePar=" + creePar + ", " : "") +
                (modifieLe != null ? "modifieLe=" + modifieLe + ", " : "") +
                (modifiePar != null ? "modifiePar=" + modifiePar + ", " : "") +
                (valeurMin != null ? "valeurMin=" + valeurMin + ", " : "") +
                (valeurMax != null ? "valeurMax=" + valeurMax + ", " : "") +
                (dateLivraison != null ? "dateLivraison=" + dateLivraison + ", " : "") +
                (zoneId != null ? "zoneId=" + zoneId + ", " : "") +
            "}";
    }

}
