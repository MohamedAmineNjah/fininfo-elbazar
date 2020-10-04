package org.fininfo.elbazar.service.dto.common;

import javax.validation.constraints.NotNull;

public class CommandeLignesHeaderDTO {

	@NotNull
	private Long id;

	@NotNull
	private Double quantite;

	@NotNull
	private Double prixHT;

	@NotNull
	private Double remise;

	@NotNull
	private Double tva;

	@NotNull
	private Double prixTTC;

	@NotNull
	private Long refCommandeId;

	@NotNull
	private Long refProduitId;

	@NotNull
	private String refProduitReference;

	@NotNull
	private String nomProduit;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getQuantite() {
		return quantite;
	}

	public void setQuantite(Double quantite) {
		this.quantite = quantite;
	}

	public Double getPrixHT() {
		return prixHT;
	}

	public void setPrixHT(Double prixHT) {
		this.prixHT = prixHT;
	}

	public Double getRemise() {
		return remise;
	}

	public void setRemise(Double remise) {
		this.remise = remise;
	}

	public Double getTva() {
		return tva;
	}

	public void setTva(Double tva) {
		this.tva = tva;
	}

	public Double getPrixTTC() {
		return prixTTC;
	}

	public void setPrixTTC(Double prixTTC) {
		this.prixTTC = prixTTC;
	}

	public Long getRefCommandeId() {
		return refCommandeId;
	}

	public void setRefCommandeId(Long refCommandeId) {
		this.refCommandeId = refCommandeId;
	}



	public Long getRefProduitId() {
		return refProduitId;
	}

	public void setRefProduitId(Long refProduitId) {
		this.refProduitId = refProduitId;
	}

	public String getRefProduitReference() {
		return refProduitReference;
	}

	public void setRefProduitReference(String refProduitReference) {
		this.refProduitReference = refProduitReference;
	}

	public String getNomProduit() {
		return nomProduit;
	}

	public void setNomProduit(String nomProduit) {
		this.nomProduit = nomProduit;
	}

}
