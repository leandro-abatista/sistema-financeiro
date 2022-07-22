package br.com.financeiro.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import br.com.financeiro.dao.CirurgiaDao;
import br.com.financeiro.dao.EnderecoDao;
import br.com.financeiro.dao.MedicoDao;
import br.com.financeiro.dao.RiscoCirurgicoDao;
import br.com.financeiro.dao.UsuarioDao;
import br.com.financeiro.domain.Cirurgia;
import br.com.financeiro.domain.Endereco;
import br.com.financeiro.domain.Medico;
import br.com.financeiro.domain.RiscoCirurgico;

@ManagedBean(name = "rcBean")
@ViewScoped
public class RiscoCirurgicoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private RiscoCirurgico riscoCirurgico;
	private RiscoCirurgicoDao riscoCirurgicoDao = new RiscoCirurgicoDao();
	private List<RiscoCirurgico> riscosCirurgicos;

	private EnderecoDao enderecoDao = new EnderecoDao();
	private List<Endereco> enderecos;

	private CirurgiaDao cirurgiaDao = new CirurgiaDao();
	private List<Cirurgia> cirurgias;

	private MedicoDao medicoDao = new MedicoDao();
	private List<Medico> medicos;

	private UsuarioDao usuarioDao = new UsuarioDao();

	@ManagedProperty(value = "#{autenticacaoBean}")
	private AutenticacaoBean autenticacaoBean;

	public RiscoCirurgico getRiscoCirurgico() {
		return riscoCirurgico;
	}

	public void setRiscoCirurgico(RiscoCirurgico riscoCirurgico) {
		this.riscoCirurgico = riscoCirurgico;
	}

	public AutenticacaoBean getAutenticacaoBean() {
		return autenticacaoBean;
	}

	public void setAutenticacaoBean(AutenticacaoBean autenticacaoBean) {
		this.autenticacaoBean = autenticacaoBean;
	}

	public List<RiscoCirurgico> getRiscosCirurgicos() {
		return riscosCirurgicos;
	}

	public List<Endereco> getEnderecos() {
		return enderecos;
	}

	public List<Cirurgia> getCirurgias() {
		return cirurgias;
	}

	public List<Medico> getMedicos() {
		return medicos;
	}
	
	public void novo() {
		riscoCirurgico = new RiscoCirurgico();
		enderecos = enderecoDao.listar();
		cirurgias = cirurgiaDao.listar();
		medicos = medicoDao.listar();
	}

}
