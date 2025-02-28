package com.monoSpringAereo.monoSpringAereo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "prenotazioni")
public class Prenotazione {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String codiceVolo;
	private String nomeAereo;
	private Double prezzoBiglietto;
	private Integer nAdulti;
	private Integer nBambini;

	@ManyToOne
	@JoinColumn(name = "volo_id")
	private Volo volo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodiceVolo() {
		return codiceVolo;
	}

	public void setCodiceVolo(String codiceVolo) {
		this.codiceVolo = codiceVolo;
	}

	public String getNomeAereo() {
		return nomeAereo;
	}

	public void setNomeAereo(String nomeAereo) {
		this.nomeAereo = nomeAereo;
	}

	public Double getPrezzoBiglietto() {
		return prezzoBiglietto;
	}

	public void setPrezzoBiglietto(Double prezzoBiglietto) {
		this.prezzoBiglietto = prezzoBiglietto;
	}

	public Integer getNAdulti() {
		return nAdulti;
	}

	public void setNAdulti(Integer nAdulti) {
		this.nAdulti = nAdulti;
	}

	public Integer getNBambini() {
		return nBambini;
	}

	public void setNBambini(Integer nBambini) {
		this.nBambini = nBambini;
	}

	public Volo getVolo() {
		return volo;
	}

	public void setVolo(Volo volo) {
		this.volo = volo;
		if (volo != null) {
			this.codiceVolo = volo.getCodiceVolo();
		}
	}

}