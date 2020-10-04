package org.fininfo.elbazar.service;



public class SousCategorieAlreadyUsedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public SousCategorieAlreadyUsedException() {
        super("Sous Catégorie Reference already used!");
    }

}
