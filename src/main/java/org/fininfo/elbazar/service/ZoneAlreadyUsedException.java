package org.fininfo.elbazar.service;

public class ZoneAlreadyUsedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ZoneAlreadyUsedException() {
        super("Code de zone déjà utilisé !");
    }

}
