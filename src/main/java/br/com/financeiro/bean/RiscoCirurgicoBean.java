package br.com.financeiro.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.omnifaces.util.Messages;

import br.com.financeiro.dao.CirurgiaDao;
import br.com.financeiro.dao.EnderecoDao;
import br.com.financeiro.dao.MedicoDao;
import br.com.financeiro.dao.RiscoCirurgicoDao;
import br.com.financeiro.dao.UsuarioDao;
import br.com.financeiro.domain.Cirurgia;
import br.com.financeiro.domain.Endereco;
import br.com.financeiro.domain.Medico;
import br.com.financeiro.domain.RiscoCirurgico;
import br.com.financeiro.domain.Usuario;

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
	
	/**
	 * Método para criar um novo objeto ARC
	 */
	public void novo() {
		try {
			riscoCirurgico = new RiscoCirurgico();
			enderecos = enderecoDao.listar();
			cirurgias = cirurgiaDao.listar();
			medicos = medicoDao.listar();
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Erro ao tentar gerar uma nova avaliação de risco cirúrgico!");
			erro.printStackTrace();
		}
	}
	
	/**
	 * Método para inserir um novo registro no banco de dados
	 */
	public void salvar() {
		try {
			Usuario usuario = usuarioDao.buscar(autenticacaoBean.getUsuarioLogado().getId());
			riscoCirurgico.setUsuario(usuario);
			riscoCirurgico.setDataAvaliacao(new Date());
			riscoCirurgicoDao.merge(riscoCirurgico);
			novo();
			Messages.addGlobalInfo("Avaliação de Risco Cirúrgico salvo com sucesso!");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Erro ao tentar salvar uma nova avaliação de risco cirúrgico!");
			erro.printStackTrace();
		}
	}

}
