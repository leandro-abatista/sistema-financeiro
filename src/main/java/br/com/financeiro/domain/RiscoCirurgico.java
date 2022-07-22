package br.com.financeiro.domain;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@Entity
public class RiscoCirurgico extends GenericDomain {

	private static final long serialVersionUID = 1L;

	@Column(nullable = false, length = 150)
	private String nome;
	@Column(nullable = false)
	private Integer idade;
	@Column(nullable = false, length = 15)
	private String sexo;
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataAvaliacao;
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataNascimento;
	@Column(length = 255)
	private String observacao;
	@Column(nullable = true)
	private String[] antecedentesPessoais;

	@Column(nullable = true, length = 100)
	private String medicamentoEmUso;

	@Column(nullable = true, length = 20)
	private String estadoGeral;
	@Column(nullable = true, length = 20)
	private String peleMucosas;
	@Column(nullable = true, length = 40)
	private String ar;
	@Column(nullable = true, length = 40)
	private String acv;
	@Column(nullable = true, length = 10)
	private String ta;

	@Column(nullable = true, length = 100)
	private String exameHematologico;

	@Column(nullable = true, length = 30)
	private String raioXTorax;
	@Column(nullable = true, length = 30)
	private String ecoCardiograma;
	@Column(nullable = true, length = 50)
	private String eletrocardiograma;
	@Column(nullable = true, length = 50)
	private String indiceGoldman;

	@Column(nullable = true, length = 40)
	private String tabelaAsa;
	@Column(nullable = true, length = 40)
	private String tabelaNyha;
	@Column(nullable = true, length = 40)
	private String criteriosLee;
	@Column(nullable = true, length = 10)
	private String riscoCirurgicoGrau;
	@Column(nullable = true, length = 20)
	private String paciente;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_endereco", referencedColumnName = "id", nullable = false)
	private Endereco endereço;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_usuario", referencedColumnName = "id", nullable = false)
	private Usuario usuario;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_cirurgia", referencedColumnName = "id", nullable = false)
	private Cirurgia cirurgia;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_medico", referencedColumnName = "id", nullable = false)
	private Medico medico;

	@Temporal(TemporalType.TIMESTAMP) // essa anotação carrega hora e data
	private Date ultimoAcesso;

	@Version
	@Column(name = "VERSAO", nullable = false)
	private Long versao;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getIdade() {
//		Calendar dateOfBirth = new GregorianCalendar();
//		dateOfBirth.setTime(dataN);
//		//cria um objeto calendar com a data atual
//		Calendar today = Calendar.getInstance();
//		//obtém a idade baseado no ano
//		Integer age = today.get(Calendar.YEAR) - dateOfBirth.get(Calendar.YEAR);
//		dateOfBirth.add(Calendar.YEAR, age);
//		//se a data de hoje é antes da data de nascimento, então dimninue 1
//		if (today.before(dateOfBirth)) {
//			age--;
//		}
		
		return idade;
	}

	public void setIdade(Integer idade) {
		this.idade = idade;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public Date getDataAvaliacao() {
		return dataAvaliacao;
	}

	public void setDataAvaliacao(Date dataAvaliacao) {
		this.dataAvaliacao = dataAvaliacao;
	}
	
	public Date getDataNascimento() {
		return dataNascimento;
	}
	
	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public String[] getAntecedentesPessoais() {
		return antecedentesPessoais;
	}

	public void setAntecedentesPessoais(String[] antecedentesPessoais) {
		this.antecedentesPessoais = antecedentesPessoais;
	}

	public String getMedicamentoEmUso() {
		return medicamentoEmUso;
	}

	public void setMedicamentoEmUso(String medicamentoEmUso) {
		this.medicamentoEmUso = medicamentoEmUso;
	}

	public String getEstadoGeral() {
		return estadoGeral;
	}

	public void setEstadoGeral(String estadoGeral) {
		this.estadoGeral = estadoGeral;
	}

	public String getPeleMucosas() {
		return peleMucosas;
	}

	public void setPeleMucosas(String peleMucosas) {
		this.peleMucosas = peleMucosas;
	}

	public String getAr() {
		return ar;
	}

	public void setAr(String ar) {
		this.ar = ar;
	}

	public String getAcv() {
		return acv;
	}

	public void setAcv(String acv) {
		this.acv = acv;
	}

	public String getTa() {
		return ta;
	}

	public void setTa(String ta) {
		this.ta = ta;
	}

	public String getExameHematologico() {
		return exameHematologico;
	}

	public void setExameHematologico(String exameHematologico) {
		this.exameHematologico = exameHematologico;
	}

	public String getRaioXTorax() {
		return raioXTorax;
	}

	public void setRaioXTorax(String raioXTorax) {
		this.raioXTorax = raioXTorax;
	}

	public String getEcoCardiograma() {
		return ecoCardiograma;
	}

	public void setEcoCardiograma(String ecoCardiograma) {
		this.ecoCardiograma = ecoCardiograma;
	}

	public String getEletrocardiograma() {
		return eletrocardiograma;
	}

	public void setEletrocardiograma(String eletrocardiograma) {
		this.eletrocardiograma = eletrocardiograma;
	}

	public String getIndiceGoldman() {
		return indiceGoldman;
	}

	public void setIndiceGoldman(String indiceGoldman) {
		this.indiceGoldman = indiceGoldman;
	}

	public String getTabelaAsa() {
		return tabelaAsa;
	}

	public void setTabelaAsa(String tabelaAsa) {
		this.tabelaAsa = tabelaAsa;
	}

	public String getTabelaNyha() {
		return tabelaNyha;
	}

	public void setTabelaNyha(String tabelaNyha) {
		this.tabelaNyha = tabelaNyha;
	}

	public String getCriteriosLee() {
		return criteriosLee;
	}

	public void setCriteriosLee(String criteriosLee) {
		this.criteriosLee = criteriosLee;
	}

	public String getRiscoCirurgicoGrau() {
		return riscoCirurgicoGrau;
	}

	public void setRiscoCirurgicoGrau(String riscoCirurgicoGrau) {
		this.riscoCirurgicoGrau = riscoCirurgicoGrau;
	}

	public String getPaciente() {
		return paciente;
	}

	public void setPaciente(String paciente) {
		this.paciente = paciente;
	}

	public Endereco getEndereço() {
		return endereço;
	}

	public void setEndereço(Endereco endereço) {
		this.endereço = endereço;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Cirurgia getCirurgia() {
		return cirurgia;
	}

	public void setCirurgia(Cirurgia cirurgia) {
		this.cirurgia = cirurgia;
	}

	public Medico getMedico() {
		return medico;
	}

	public void setMedico(Medico medico) {
		this.medico = medico;
	}

	public Date getUltimoAcesso() {
		return ultimoAcesso;
	}

	public void setUltimoAcesso(Date ultimoAcesso) {
		this.ultimoAcesso = ultimoAcesso;
	}

	public Long getVersao() {
		return versao;
	}

	public void setVersao(Long versao) {
		this.versao = versao;
	}

}
