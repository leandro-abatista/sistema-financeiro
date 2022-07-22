package br.com.financeiro.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Especialidade extends GenericDomain {

	private static final long serialVersionUID = 1L;

	@Column(nullable = false, length = 100)
	private String descricao;

	@ManyToOne
	@JoinColumn(nullable = true)
	private Usuario usuarioLogado;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

}
