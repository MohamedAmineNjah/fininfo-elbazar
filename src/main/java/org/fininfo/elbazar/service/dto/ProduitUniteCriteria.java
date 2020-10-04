package org.fininfo.elbazar.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link org.fininfo.elbazar.domain.ProduitUnite} entity. This class is used
 * in {@link org.fininfo.elbazar.web.rest.ProduitUniteResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /produit-unites?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProduitUniteCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter code;

    private StringFilter nom;

    private LongFilter produitId;

    public ProduitUniteCriteria() {
    }

    public ProduitUniteCriteria(ProduitUniteCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.code = other.code == null ? null : other.code.copy();
        this.nom = other.nom == null ? null : other.nom.copy();
        this.produitId = other.produitId == null ? null : other.produitId.copy();
    }

    @Override
    public ProduitUniteCriteria copy() {
        return new ProduitUniteCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCode() {
        return code;
    }

    public void setCode(StringFilter code) {
        this.code = code;
    }

    public StringFilter getNom() {
        return nom;
    }

    public void setNom(StringFilter nom) {
        this.nom = nom;
    }

    public LongFilter getProduitId() {
        return produitId;
    }

    public void setProduitId(LongFilter produitId) {
        this.produitId = produitId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProduitUniteCriteria that = (ProduitUniteCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(code, that.code) &&
            Objects.equals(nom, that.nom) &&
            Objects.equals(produitId, that.produitId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        code,
        nom,
        produitId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProduitUniteCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (code != null ? "code=" + code + ", " : "") +
                (nom != null ? "nom=" + nom + ", " : "") +
                (produitId != null ? "produitId=" + produitId + ", " : "") +
            "}";
    }

}
