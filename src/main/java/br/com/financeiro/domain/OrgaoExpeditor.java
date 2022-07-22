package br.com.financeiro.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class OrgaoExpeditor extends GenericDomain implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(length = 50, nullable = false)
	private String descricao;
	
	@Column(length = 5, nullable = false)
	private String sigla;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_estado", referencedColumnName = "id", nullable = false)
	private Estado estado;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	
	

}
