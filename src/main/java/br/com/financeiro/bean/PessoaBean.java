package br.com.financeiro.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.financeiro.dao.EnderecoDao;
import br.com.financeiro.dao.OrgaoExpeditorDao;
import br.com.financeiro.dao.PessoaDao;
import br.com.financeiro.domain.Endereco;
import br.com.financeiro.domain.OrgaoExpeditor;
import br.com.financeiro.domain.Pessoa;

@ManagedBean(name = "pessoaBean")
@ViewScoped
public class PessoaBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Pessoa pessoa;
	private PessoaDao pessoaDao = new PessoaDao();
	private EnderecoDao enderecoDao = new EnderecoDao();
	private List<Pessoa> pessoas;
	private List<Endereco> enderecos;
	private OrgaoExpeditorDao orgaoExpeditorDao = new OrgaoExpeditorDao();
	private List<OrgaoExpeditor> orgaoExpeditores;
	
	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	public List<Endereco> getEnderecos() {
		return enderecos;
	}
	
	public List<OrgaoExpeditor> getOrgaoExpeditores() {
		return orgaoExpeditores;
	}

	public void novo() {
		try {
			pessoa = new Pessoa();
			enderecos = enderecoDao.listar("logradouro");
			orgaoExpeditores = orgaoExpeditorDao.listar("descricao");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar gerar um nova  pessoa!");
			erro.printStackTrace();
		}
	}

	public void salvar() {
		try {
			pessoaDao.merge(pessoa);
			novo();
			pessoas = pessoaDao.listar("id");
			Messages.addFlashGlobalInfo("Pessoa salvar com sucesso!");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar salvar uma nova pessoa!");
			erro.printStackTrace();
		}
	}

	@PostConstruct
	public void listar() {
		try {
			pessoas = pessoaDao.listar("id");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar listar as pessoas!");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		try {
			pessoa = (Pessoa) evento.getComponent().getAttributes().get("pessoaSelecionado");

			enderecos = enderecoDao.listar();
			orgaoExpeditores = orgaoExpeditorDao.listar();
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar atualizar a pessoa!");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {
		try {
			pessoa = (Pessoa) evento.getComponent().getAttributes().get("pessoaSelecionado");
			
			pessoaDao.excluir(pessoa);
			
			pessoas = pessoaDao.listar();
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar remover a pessoa!");
			erro.printStackTrace();
		}
	}
	
	public void visualizar(ActionEvent evento) {
		try {
			pessoa = (Pessoa) evento.getComponent().getAttributes().get("pessoaSelecionado");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar visualizar a pessoa!");
			erro.printStackTrace();
		}
	}
	

}
