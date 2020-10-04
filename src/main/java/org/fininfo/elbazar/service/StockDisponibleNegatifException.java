package org.fininfo.elbazar.service;



public class StockDisponibleNegatifException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public StockDisponibleNegatifException() {
        super("Stock disponible négatif non autorisé !");
    }

}
