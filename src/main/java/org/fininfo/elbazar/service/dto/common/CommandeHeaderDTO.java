package org.fininfo.elbazar.service.dto.common;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class CommandeHeaderDTO {

	@Pattern(regexp = "^[a-zA-Z0-9]{0,12}$")
	private String reference;

	@NotNull
	private String prenom;

	@NotNull
	private String nom;

	@NotNull
	private Integer clientMobile;

	private Integer telephone;

	@NotNull
	private Integer mobile;
	
	@NotNull
	private String Adresse;

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Integer getClientMobile() {
		return clientMobile;
	}

	public void setClientMobile(Integer clientMobile) {
		this.clientMobile = clientMobile;
	}

	public Integer getTelephone() {
		return telephone;
	}

	public void setTelephone(Integer telephone) {
		this.telephone = telephone;
	}

	public Integer getMobile() {
		return mobile;
	}

	public void setMobile(Integer mobile) {
		this.mobile = mobile;
	}

	public String getAdresse() {
		return Adresse;
	}

	public void setAdresse(String adresse) {
		Adresse = adresse;
	}

}
