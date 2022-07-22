package br.com.financeiro.domain;

import java.time.LocalDateTime;
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
public class Medico extends GenericDomain {

	private static final long serialVersionUID = 1L;

	@Column(nullable = false, length = 50)
	private String nome;

	@Column(nullable = false, length = 100)
	private String sobreNome;

	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date dataNascimento;

	@Column(nullable = false, length = 20)
	private String crm;

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

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataCadastro;

	@Column(nullable = false)
	private Boolean ativo;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_endereco", referencedColumnName = "id", nullable = false)
	private Endereco endereço;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_especialidade", referencedColumnName = "id", nullable = false)
	private Especialidade especialidade;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_orgaoExpeditor", referencedColumnName = "id", nullable = true)
	private OrgaoExpeditor orgaoExpeditor;

	@ManyToOne
	@JoinColumn(nullable = true)
	private Usuario usuarioLogado;
	
	@Version
	@Column(name = "VERSAO", nullable = false)
	private Long versao;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSobreNome() {
		return sobreNome;
	}

	public void setSobreNome(String sobreNome) {
		this.sobreNome = sobreNome;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getCrm() {
		return crm;
	}

	public void setCrm(String crm) {
		this.crm = crm;
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

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Long getVersao() {
		return versao;
	}

	public void setVersao(Long versao) {
		this.versao = versao;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public Endereco getEndereço() {
		return endereço;
	}

	public void setEndereço(Endereco endereço) {
		this.endereço = endereço;
	}

	public Especialidade getEspecialidade() {
		return especialidade;
	}

	public void setEspecialidade(Especialidade especialidade) {
		this.especialidade = especialidade;
	}

	public OrgaoExpeditor getOrgaoExpeditor() {
		return orgaoExpeditor;
	}

	public void setOrgaoExpeditor(OrgaoExpeditor orgaoExpeditor) {
		this.orgaoExpeditor = orgaoExpeditor;
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

}
