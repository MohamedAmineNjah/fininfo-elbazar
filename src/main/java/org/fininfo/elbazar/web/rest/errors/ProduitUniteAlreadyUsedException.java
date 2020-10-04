package org.fininfo.elbazar.web.rest.errors;

public class ProduitUniteAlreadyUsedException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    public ProduitUniteAlreadyUsedException() {
        super(ErrorConstants.LOGIN_ALREADY_USED_TYPE, "Produit Unite code already used!", "userManagement", "produituniteexists");
    }
}
