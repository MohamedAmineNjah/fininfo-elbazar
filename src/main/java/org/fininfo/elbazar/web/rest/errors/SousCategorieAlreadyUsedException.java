package org.fininfo.elbazar.web.rest.errors;

public class SousCategorieAlreadyUsedException extends BadRequestAlertException{
    private static final long serialVersionUID = 1L;

    public SousCategorieAlreadyUsedException() {
        super(ErrorConstants.LOGIN_ALREADY_USED_TYPE, "Sous Catégorie déjà utilisée !", "userManagement", "souscategorieexists");
    }
}


