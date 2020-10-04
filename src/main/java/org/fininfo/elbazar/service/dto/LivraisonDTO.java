package org.fininfo.elbazar.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import org.fininfo.elbazar.domain.enumeration.ProfileClient;

/**
 * A DTO for the {@link org.fininfo.elbazar.domain.Livraison} entity.
 */
public class LivraisonDTO implements Serializable {
    
    private Long id;

    private ProfileClient categorieClient;

    private Double intervalValeur;

    private Double frais;

    private Integer date;

    private LocalDate creeLe;

    private String creePar;

    private LocalDate modifieLe;

    private String modifiePar;

    @NotNull
    private Double valeurMin;

    @NotNull
    private Double valeurMax;

    private LocalDate dateLivraison;


    private Long zoneId;

    private String zoneNom;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProfileClient getCategorieClient() {
        return categorieClient;
    }

    public void setCategorieClient(ProfileClient categorieClient) {
        this.categorieClient = categorieClient;
    }

    public Double getIntervalValeur() {
        return intervalValeur;
    }

    public void setIntervalValeur(Double intervalValeur) {
        this.intervalValeur = intervalValeur;
    }

    public Double getFrais() {
        return frais;
    }

    public void setFrais(Double frais) {
        this.frais = frais;
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
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

    public Double getValeurMin() {
        return valeurMin;
    }

    public void setValeurMin(Double valeurMin) {
        this.valeurMin = valeurMin;
    }

    public Double getValeurMax() {
        return valeurMax;
    }

    public void setValeurMax(Double valeurMax) {
        this.valeurMax = valeurMax;
    }

    public LocalDate getDateLivraison() {
        return dateLivraison;
    }

    public void setDateLivraison(LocalDate dateLivraison) {
        this.dateLivraison = dateLivraison;
    }

    public Long getZoneId() {
        return zoneId;
    }

    public void setZoneId(Long zoneId) {
        this.zoneId = zoneId;
    }

    public String getZoneNom() {
        return zoneNom;
    }

    public void setZoneNom(String zoneNom) {
        this.zoneNom = zoneNom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LivraisonDTO)) {
            return false;
        }

        return id != null && id.equals(((LivraisonDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LivraisonDTO{" +
            "id=" + getId() +
            ", categorieClient='" + getCategorieClient() + "'" +
            ", intervalValeur=" + getIntervalValeur() +
            ", frais=" + getFrais() +
            ", date=" + getDate() +
            ", creeLe='" + getCreeLe() + "'" +
            ", creePar='" + getCreePar() + "'" +
            ", modifieLe='" + getModifieLe() + "'" +
            ", modifiePar='" + getModifiePar() + "'" +
            ", valeurMin=" + getValeurMin() +
            ", valeurMax=" + getValeurMax() +
            ", dateLivraison='" + getDateLivraison() + "'" +
            ", zoneId=" + getZoneId() +
            ", zoneNom='" + getZoneNom() + "'" +
            "}";
    }
}
