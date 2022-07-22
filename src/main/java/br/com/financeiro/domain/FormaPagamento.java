package br.com.financeiro.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "formaPagamento")
public class FormaPagamento extends GenericDomain implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Column(nullable = false, length = 120)
	private String descricao;//Descrição do cartão
	
	@Column(nullable = false, length = 15)
	private String bandeira;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Usuario usuarioLogado;
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public String getBandeira() {
		return bandeira;
	}
	
	public void setBandeira(String bandeira) {
		this.bandeira = bandeira;
	}
	
	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}
	
	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}
}
