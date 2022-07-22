package br.com.financeiro.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Estado extends GenericDomain implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(nullable = false, length = 30)
	private String nome;

	@Column(nullable = false, length = 2)
	private String sigla;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	@Override
	public String toString() {
		return "Estado [nome=" + nome + ", sigla=" + sigla + "]";
	}
	
	

}
