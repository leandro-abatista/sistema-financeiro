package br.com.financeiro.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@Entity
public class Cirurgia extends GenericDomain {

	private static final long serialVersionUID = 1L;

	@Column(nullable = false, length = 150)
	private String descricao;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_usuario", referencedColumnName = "id", nullable = false)
	private Usuario usuario;
	
	@Temporal(TemporalType.TIMESTAMP)//essa anotação carrega hora e data
	private Date ent_ultimoAcesso;

	@Version
	@Column(name = "VERSAO", nullable = false)
	private Long versao;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Long getVersao() {
		return versao;
	}

	public void setVersao(Long versao) {
		this.versao = versao;
	}

}
