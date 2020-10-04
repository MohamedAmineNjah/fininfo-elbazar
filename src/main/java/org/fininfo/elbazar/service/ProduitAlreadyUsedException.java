package org.fininfo.elbazar.service;



public class ProduitAlreadyUsedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ProduitAlreadyUsedException() {
        super("Product Reference already used!");
    }

}
