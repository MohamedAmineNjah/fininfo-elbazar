package org.fininfo.elbazar.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link org.fininfo.elbazar.domain.ProduitUnite} entity.
 */
public class ProduitUniteDTO implements Serializable {
    
    private Long id;

    @Pattern(regexp = "^([\\S]{1,10}$)*")
    private String code;

    private String nom;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProduitUniteDTO)) {
            return false;
        }

        return id != null && id.equals(((ProduitUniteDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProduitUniteDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", nom='" + getNom() + "'" +
            "}";
    }
}
