package br.com.financeiro.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Pessoa extends GenericDomain {

	private static final long serialVersionUID = 1L;

	@Column(length = 120, nullable = false)
	private String nome;

	@Column(length = 20, nullable = false)
	private String cpf;

	@Column(length = 12, nullable = false)
	private String rg;

	@Column(length = 16, nullable = false)
	private String telefone;

	@Column(length = 16, nullable = false)
	private String celular;

	@Column(length = 120, nullable = false)
	private String email;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_endereco", referencedColumnName = "id", nullable = false)
	private Endereco endereço;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_orgaoExpeditor", referencedColumnName = "id", nullable = true)
	private OrgaoExpeditor orgaoExpeditor;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Endereco getEndereço() {
		return endereço;
	}

	public void setEndereço(Endereco endereço) {
		this.endereço = endereço;
	}
	
	public OrgaoExpeditor getOrgaoExpeditor() {
		return orgaoExpeditor;
	}

	public void setOrgaoExpeditor(OrgaoExpeditor orgaoExpeditor) {
		this.orgaoExpeditor = orgaoExpeditor;
	}

	@Override
	public String toString() {
		return "Pessoa [nome=" + nome + ", cpf=" + cpf + ", rg=" + rg + ", telefone=" + telefone + ", celular="
				+ celular + ", email=" + email + ", endereço=" + endereço + "]";
	}

	
}
