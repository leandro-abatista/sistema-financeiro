package br.com.financeiro.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.financeiro.dao.ClienteDao;
import br.com.financeiro.dao.PessoaDao;
import br.com.financeiro.domain.Cliente;
import br.com.financeiro.domain.Pessoa;

@ManagedBean(name = "clienteBean")
@ViewScoped
public class ClienteBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private Pessoa pessoa;
	private PessoaDao pessoaDao = new PessoaDao();
	private ClienteDao clienteDao = new ClienteDao();
	private List<Pessoa> pessoas;
	private Cliente cliente;
	private List<Cliente> clientes;

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

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	@PostConstruct
	public void listar() {
		try {
			clientes = clienteDao.listarOrdenadoCliente();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os clientes");
			erro.printStackTrace();
		}
	}

	public void novo() {
		try {
			cliente = new Cliente();

			pessoas = pessoaDao.listar("nome");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar criar um novo cliente");
			erro.printStackTrace();
		}
	}

	public void salvar() {
		try {
			clienteDao.merge(cliente);

			cliente = new Cliente();
			
			clientes = clienteDao.listar("dataCadastro");

			pessoas = pessoaDao.listar("nome");
			
			Messages.addGlobalInfo("Cliente salvo com sucesso");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar o cliente");
			erro.printStackTrace();
		}
	}
	
	public void editar(ActionEvent evento) {
		try {
			cliente = (Cliente) evento.getComponent().getAttributes().get("clienteSelecionado");

			pessoas = pessoaDao.listar("id");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar selecionar um cliente!");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {
		try {
			cliente = (Cliente) evento.getComponent().getAttributes().get("clienteSelecionado");

			clienteDao.excluir(cliente);

			clientes = clienteDao.listar();

			Messages.addGlobalInfo("Cliente removido com sucesso!");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar remover o cliente!");
			erro.printStackTrace();
		}
	}
	
	public String redirecionaClientes() {
       // return "cliente.xhtml";
        //ou
        return "clientesPrincipal.xhtml?faces-redirect=true";
    }

}
