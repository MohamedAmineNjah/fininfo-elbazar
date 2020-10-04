package org.fininfo.elbazar.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import org.fininfo.elbazar.domain.enumeration.Devise;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link org.fininfo.elbazar.domain.GestionFidelite} entity. This class is used
 * in {@link org.fininfo.elbazar.web.rest.GestionFideliteResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /gestion-fidelites?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class GestionFideliteCriteria implements Serializable, Criteria {
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

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nom;

    private IntegerFilter points;

    private DoubleFilter valeur;

    private IntegerFilter silverMin;

    private IntegerFilter silverMax;

    private IntegerFilter goldMin;

    private IntegerFilter goldMax;

    private IntegerFilter platiniumMin;

    private IntegerFilter platiniumMax;

    private DeviseFilter devise;

    private BooleanFilter etat;

    private LocalDateFilter creeLe;

    private StringFilter creePar;

    private LocalDateFilter modifieLe;

    private StringFilter modifiePar;

    public GestionFideliteCriteria() {
    }

    public GestionFideliteCriteria(GestionFideliteCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nom = other.nom == null ? null : other.nom.copy();
        this.points = other.points == null ? null : other.points.copy();
        this.valeur = other.valeur == null ? null : other.valeur.copy();
        this.silverMin = other.silverMin == null ? null : other.silverMin.copy();
        this.silverMax = other.silverMax == null ? null : other.silverMax.copy();
        this.goldMin = other.goldMin == null ? null : other.goldMin.copy();
        this.goldMax = other.goldMax == null ? null : other.goldMax.copy();
        this.platiniumMin = other.platiniumMin == null ? null : other.platiniumMin.copy();
        this.platiniumMax = other.platiniumMax == null ? null : other.platiniumMax.copy();
        this.devise = other.devise == null ? null : other.devise.copy();
        this.etat = other.etat == null ? null : other.etat.copy();
        this.creeLe = other.creeLe == null ? null : other.creeLe.copy();
        this.creePar = other.creePar == null ? null : other.creePar.copy();
        this.modifieLe = other.modifieLe == null ? null : other.modifieLe.copy();
        this.modifiePar = other.modifiePar == null ? null : other.modifiePar.copy();
    }

    @Override
    public GestionFideliteCriteria copy() {
        return new GestionFideliteCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNom() {
        return nom;
    }

    public void setNom(StringFilter nom) {
        this.nom = nom;
    }

    public IntegerFilter getPoints() {
        return points;
    }

    public void setPoints(IntegerFilter points) {
        this.points = points;
    }

    public DoubleFilter getValeur() {
        return valeur;
    }

    public void setValeur(DoubleFilter valeur) {
        this.valeur = valeur;
    }

    public IntegerFilter getSilverMin() {
        return silverMin;
    }

    public void setSilverMin(IntegerFilter silverMin) {
        this.silverMin = silverMin;
    }

    public IntegerFilter getSilverMax() {
        return silverMax;
    }

    public void setSilverMax(IntegerFilter silverMax) {
        this.silverMax = silverMax;
    }

    public IntegerFilter getGoldMin() {
        return goldMin;
    }

    public void setGoldMin(IntegerFilter goldMin) {
        this.goldMin = goldMin;
    }

    public IntegerFilter getGoldMax() {
        return goldMax;
    }

    public void setGoldMax(IntegerFilter goldMax) {
        this.goldMax = goldMax;
    }

    public IntegerFilter getPlatiniumMin() {
        return platiniumMin;
    }

    public void setPlatiniumMin(IntegerFilter platiniumMin) {
        this.platiniumMin = platiniumMin;
    }

    public IntegerFilter getPlatiniumMax() {
        return platiniumMax;
    }

    public void setPlatiniumMax(IntegerFilter platiniumMax) {
        this.platiniumMax = platiniumMax;
    }

    public DeviseFilter getDevise() {
        return devise;
    }

    public void setDevise(DeviseFilter devise) {
        this.devise = devise;
    }

    public BooleanFilter getEtat() {
        return etat;
    }

    public void setEtat(BooleanFilter etat) {
        this.etat = etat;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final GestionFideliteCriteria that = (GestionFideliteCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nom, that.nom) &&
            Objects.equals(points, that.points) &&
            Objects.equals(valeur, that.valeur) &&
            Objects.equals(silverMin, that.silverMin) &&
            Objects.equals(silverMax, that.silverMax) &&
            Objects.equals(goldMin, that.goldMin) &&
            Objects.equals(goldMax, that.goldMax) &&
            Objects.equals(platiniumMin, that.platiniumMin) &&
            Objects.equals(platiniumMax, that.platiniumMax) &&
            Objects.equals(devise, that.devise) &&
            Objects.equals(etat, that.etat) &&
            Objects.equals(creeLe, that.creeLe) &&
            Objects.equals(creePar, that.creePar) &&
            Objects.equals(modifieLe, that.modifieLe) &&
            Objects.equals(modifiePar, that.modifiePar);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nom,
        points,
        valeur,
        silverMin,
        silverMax,
        goldMin,
        goldMax,
        platiniumMin,
        platiniumMax,
        devise,
        etat,
        creeLe,
        creePar,
        modifieLe,
        modifiePar
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GestionFideliteCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nom != null ? "nom=" + nom + ", " : "") +
                (points != null ? "points=" + points + ", " : "") +
                (valeur != null ? "valeur=" + valeur + ", " : "") +
                (silverMin != null ? "silverMin=" + silverMin + ", " : "") +
                (silverMax != null ? "silverMax=" + silverMax + ", " : "") +
                (goldMin != null ? "goldMin=" + goldMin + ", " : "") +
                (goldMax != null ? "goldMax=" + goldMax + ", " : "") +
                (platiniumMin != null ? "platiniumMin=" + platiniumMin + ", " : "") +
                (platiniumMax != null ? "platiniumMax=" + platiniumMax + ", " : "") +
                (devise != null ? "devise=" + devise + ", " : "") +
                (etat != null ? "etat=" + etat + ", " : "") +
                (creeLe != null ? "creeLe=" + creeLe + ", " : "") +
                (creePar != null ? "creePar=" + creePar + ", " : "") +
                (modifieLe != null ? "modifieLe=" + modifieLe + ", " : "") +
                (modifiePar != null ? "modifiePar=" + modifiePar + ", " : "") +
            "}";
    }

}
