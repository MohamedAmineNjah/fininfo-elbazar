package org.fininfo.elbazar.service.dto.common;

public interface CommandeLignesHeaderProjection {

       
    Long	 getId() ;
    
    Double	 getQuantite() ;
    
    Double	 getPrix_HT() ;
    
    Double	 getRemise() ;
    
    Double	 getTva() ;
    
    Double	 getPrix_TTC() ;
    
    Long	 getRef_Commande_Id() ;
        
    Long	 getRef_Produit_Id() ;
    
    String	 getReference() ;
    
    String	 getnom();
    
}


