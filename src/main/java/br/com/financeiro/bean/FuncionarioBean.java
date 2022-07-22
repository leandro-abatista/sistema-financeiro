package br.com.financeiro.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.financeiro.dao.FuncionarioDao;
import br.com.financeiro.dao.PessoaDao;
import br.com.financeiro.domain.Funcionario;
import br.com.financeiro.domain.Pessoa;

@ManagedBean(name = "funcionarioBean")
@ViewScoped
public class FuncionarioBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private Pessoa pessoa;
	private PessoaDao pessoaDao = new PessoaDao();
	private FuncionarioDao funcionarioDao = new FuncionarioDao();
	private List<Funcionario> funcionarios;
	private Funcionario funcionario;
	private List<Pessoa> pessoas;

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}

	 public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	@PostConstruct
	public void listar() {
		try {
			funcionarios = funcionarioDao.listar();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os Funcionários!");
			erro.printStackTrace();
		}
	}

	public void novo() {
		try {
			funcionario = new Funcionario();

			pessoas = pessoaDao.listar("nome");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar criar um novo Funcionário!");
			erro.printStackTrace();
		}
	}

	public void salvar() {
		try {
			funcionarioDao.merge(funcionario);

			novo();

			funcionarios = funcionarioDao.listar("id");

			Messages.addGlobalInfo("Funcionário salvo com sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar o Funcionário!");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		try {
			funcionario = (Funcionario) evento.getComponent().getAttributes().get("funcionarioSelecionado");

			pessoas = pessoaDao.listar("id");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar selecionar um funcionário!");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {
		try {
			funcionario = (Funcionario) evento.getComponent().getAttributes().get("funcionarioSelecionado");

			funcionarioDao.excluir(funcionario);

			funcionarios = funcionarioDao.listar("id");

			Messages.addGlobalInfo("Funcionário removido com sucesso!");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar remover o funcionário!");
			erro.printStackTrace();
		}
	}

}
