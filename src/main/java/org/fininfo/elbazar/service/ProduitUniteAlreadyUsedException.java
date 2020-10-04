package org.fininfo.elbazar.service;

public class ProduitUniteAlreadyUsedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ProduitUniteAlreadyUsedException() {
        super("Code unité de produit déjà utilisé !");
    }

}