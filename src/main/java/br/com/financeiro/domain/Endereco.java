package br.com.financeiro.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Endereco extends GenericDomain {

	private static final long serialVersionUID = 1L;

	@Column(length = 10, nullable = false)
	private String cep;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_cidade", referencedColumnName = "id", nullable = false)
	private Cidade cidade;
	
	@Column(length = 50, nullable = false)
	private String bairro;

	@Column(length = 100, nullable = false)
	private String logradouro;

	@Column(nullable = true)
	private Short numero;

	@Column(length = 100, nullable = true)
	private String complemento;

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public Short getNumero() {
		return numero;
	}

	public void setNumero(Short numero) {
		this.numero = numero;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

}
