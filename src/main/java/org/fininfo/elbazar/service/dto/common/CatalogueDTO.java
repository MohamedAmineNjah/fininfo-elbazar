package org.fininfo.elbazar.service.dto.common;

import com.fasterxml.jackson.annotation.JsonRawValue;

public interface CatalogueDTO {

    Long getId();
    
    String getNom();

    String getDescription();

    Long getPosition();

    Boolean getEtat(); 

    @JsonRawValue    
    String getSous_categorie();
}