package org.fininfo.elbazar.service;

public class FormuleFideliteNotFoundException extends RuntimeException {
	
	 private static final long serialVersionUID = 1L;

	    public FormuleFideliteNotFoundException() {
	        super("Formule de fidélité inexistante");
	    }

}
