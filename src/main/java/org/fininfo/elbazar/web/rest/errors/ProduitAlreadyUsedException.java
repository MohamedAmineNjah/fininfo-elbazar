package org.fininfo.elbazar.web.rest.errors;

public class ProduitAlreadyUsedException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    public ProduitAlreadyUsedException() {
        super(ErrorConstants.LOGIN_ALREADY_USED_TYPE, "Produit reference already used!", "userManagement", "produitexists");
    }
}
