package org.fininfo.elbazar.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link org.fininfo.elbazar.domain.AffectationZone} entity.
 */
public class AffectationZoneDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String gouvernorat;

    @NotNull
    private String ville;

    @NotNull
    private String localite;

    @NotNull
    private Integer codePostal;

    private LocalDate modifieLe;

    private String modifiePar;

    private Integer idVille;


    private Long zoneId;

    private String zoneNom;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGouvernorat() {
        return gouvernorat;
    }

    public void setGouvernorat(String gouvernorat) {
        this.gouvernorat = gouvernorat;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getLocalite() {
        return localite;
    }

    public void setLocalite(String localite) {
        this.localite = localite;
    }

    public Integer getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(Integer codePostal) {
        this.codePostal = codePostal;
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

    public Integer getIdVille() {
        return idVille;
    }

    public void setIdVille(Integer idVille) {
        this.idVille = idVille;
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
        if (!(o instanceof AffectationZoneDTO)) {
            return false;
        }

        return id != null && id.equals(((AffectationZoneDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AffectationZoneDTO{" +
            "id=" + getId() +
            ", gouvernorat='" + getGouvernorat() + "'" +
            ", ville='" + getVille() + "'" +
            ", localite='" + getLocalite() + "'" +
            ", codePostal=" + getCodePostal() +
            ", modifieLe='" + getModifieLe() + "'" +
            ", modifiePar='" + getModifiePar() + "'" +
            ", idVille=" + getIdVille() +
            ", zoneId=" + getZoneId() +
            ", zoneNom='" + getZoneNom() + "'" +
            "}";
    }
}
