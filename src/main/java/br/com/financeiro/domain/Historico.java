package br.com.financeiro.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Historico extends GenericDomain implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP) // guarda data e hora
	private Date horario;

	@Column(nullable = true)
	private String observacoes;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Produto produto;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Usuario usuarioLogado;

	public Date getHorario() {
		return horario;
	}

	public void setHorario(Date horario) {
		this.horario = horario;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

}
