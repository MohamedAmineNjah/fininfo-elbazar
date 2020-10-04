package org.fininfo.elbazar.service.dto.common;

import java.time.LocalDate;

import javax.persistence.Lob;


public interface ProduitBySousCatDTO {

    Long getId();
    String getReference();
    String getNom();
    String getCode_barre();
    String getDescription();
    Boolean getEtat();
    String getMarque();
    String getNature();
    Double getStock_minimum();
    Double getQuantite_vente_max();
    Boolean getHors_stock();
	Boolean gettypeService();
	LocalDate getDate_Premption();
    Double getPrix_ht();
    Double getTaux_tva();
    Double getPrix_ttc();
    String getDevise();
    String getSource_produit();
    String getRating();
	Boolean geteligible_Remise();
    Double getRemise();
    LocalDate getDebut_promo();
    LocalDate getFin_promo();
    @Lob
    byte[] getImage();
    String getimage_url();
	String getimage_Content_Type();
	LocalDate getcree_Le();
    String getcree_Par();
    LocalDate getmodifie_Le();
    String getmodifie_Par();
    Boolean geten_Vedette();
    Long getcategorie_Id();
    String getcategorie_Nom();
    Long getsous_Categorie_Id();
    String getsous_Categorie_Nom();
    Long getunite_Id();
    String getunite_Code();

}