package org.fininfo.elbazar.web.rest.errors;

public class StockDisponibleNegatifException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    public StockDisponibleNegatifException() {
        super(ErrorConstants.LOGIN_ALREADY_USED_TYPE, "Stock disponible négatif non autorisé !", "userManagement", "stockdisponegatif");
    }
}
