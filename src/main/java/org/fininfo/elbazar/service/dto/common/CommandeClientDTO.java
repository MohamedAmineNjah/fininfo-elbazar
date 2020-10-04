package org.fininfo.elbazar.service.dto.common;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonRawValue;

public interface CommandeClientDTO {

    
    Long getId();
    
    String getReference();

    Date getDate_creation();

    Date getDate_annulation();

    String getStatut();

    String getOrigine();

    String getReglement();

    Long getPoints_fidelite();

    Double getTotal_ht();

    Double getTotal_tva();

    Double getTotal_remise();

    Double getTot_ttc();

    String getDevise();

    Long getId_client_id();

    Date getDate_livraison();

    @JsonRawValue    
    String getCommande_lignes();
    
}
