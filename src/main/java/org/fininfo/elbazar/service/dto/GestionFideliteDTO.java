package org.fininfo.elbazar.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import org.fininfo.elbazar.domain.enumeration.Devise;

/**
 * A DTO for the {@link org.fininfo.elbazar.domain.GestionFidelite} entity.
 */
public class GestionFideliteDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String nom;

    @NotNull
    private Integer points;

    @NotNull
    private Double valeur;

    @NotNull
    private Integer silverMin;

    @NotNull
    private Integer silverMax;

    @NotNull
    private Integer goldMin;

    @NotNull
    private Integer goldMax;

    @NotNull
    private Integer platiniumMin;

    @NotNull
    private Integer platiniumMax;

    private Devise devise;

    private Boolean etat;

    private LocalDate creeLe;

    private String creePar;

    private LocalDate modifieLe;

    private String modifiePar;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Double getValeur() {
        return valeur;
    }

    public void setValeur(Double valeur) {
        this.valeur = valeur;
    }

    public Integer getSilverMin() {
        return silverMin;
    }

    public void setSilverMin(Integer silverMin) {
        this.silverMin = silverMin;
    }

    public Integer getSilverMax() {
        return silverMax;
    }

    public void setSilverMax(Integer silverMax) {
        this.silverMax = silverMax;
    }

    public Integer getGoldMin() {
        return goldMin;
    }

    public void setGoldMin(Integer goldMin) {
        this.goldMin = goldMin;
    }

    public Integer getGoldMax() {
        return goldMax;
    }

    public void setGoldMax(Integer goldMax) {
        this.goldMax = goldMax;
    }

    public Integer getPlatiniumMin() {
        return platiniumMin;
    }

    public void setPlatiniumMin(Integer platiniumMin) {
        this.platiniumMin = platiniumMin;
    }

    public Integer getPlatiniumMax() {
        return platiniumMax;
    }

    public void setPlatiniumMax(Integer platiniumMax) {
        this.platiniumMax = platiniumMax;
    }

    public Devise getDevise() {
        return devise;
    }

    public void setDevise(Devise devise) {
        this.devise = devise;
    }

    public Boolean isEtat() {
        return etat;
    }

    public void setEtat(Boolean etat) {
        this.etat = etat;
    }

    public LocalDate getCreeLe() {
        return creeLe;
    }

    public void setCreeLe(LocalDate creeLe) {
        this.creeLe = creeLe;
    }

    public String getCreePar() {
        return creePar;
    }

    public void setCreePar(String creePar) {
        this.creePar = creePar;
    }

    public LocalDate getModifieLe() {
        return modifieLe;
    }

    public void setModifieLe(LocalDate modifieLe) {
        this.modifieLe = modifieLe;
    }

    public String getModifiePar() {
        return modifiePar;
    }

    public void setModifiePar(String modifiePar) {
        this.modifiePar = modifiePar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GestionFideliteDTO)) {
            return false;
        }

        return id != null && id.equals(((GestionFideliteDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GestionFideliteDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", points=" + getPoints() +
            ", valeur=" + getValeur() +
            ", silverMin=" + getSilverMin() +
            ", silverMax=" + getSilverMax() +
            ", goldMin=" + getGoldMin() +
            ", goldMax=" + getGoldMax() +
            ", platiniumMin=" + getPlatiniumMin() +
            ", platiniumMax=" + getPlatiniumMax() +
            ", devise='" + getDevise() + "'" +
            ", etat='" + isEtat() + "'" +
            ", creeLe='" + getCreeLe() + "'" +
            ", creePar='" + getCreePar() + "'" +
            ", modifieLe='" + getModifieLe() + "'" +
            ", modifiePar='" + getModifiePar() + "'" +
            "}";
    }
}
