package br.com.financeiro.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.financeiro.dao.FornecedorDao;
import br.com.financeiro.dao.UsuarioDao;
import br.com.financeiro.domain.Fornecedor;
import br.com.financeiro.domain.Usuario;

@ManagedBean(name = "fornecedorBean")
@ViewScoped
public class FornecedorBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private FornecedorDao fornecedorDao = new FornecedorDao();
	private Fornecedor fornecedor;
	private List<Fornecedor> fornecedores;
	private List<Fornecedor> fornecedoresFiltrados;
	private Usuario usuario;
	private UsuarioDao usuarioDao = new UsuarioDao();
	
	@ManagedProperty(value = "#{autenticacaoBean}")
	private AutenticacaoBean autenticacaoBean;

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public List<Fornecedor> getFornecedores() {
		return fornecedores;
	}

	public void setFornecedores(List<Fornecedor> fornecedores) {
		this.fornecedores = fornecedores;
	}

	public void novo() {
		fornecedor = new Fornecedor();
	}

	public List<Fornecedor> getFornecedoresFiltrados() {
		return fornecedoresFiltrados;
	}

	public void setFornecedoresFiltrados(List<Fornecedor> fornecedoresFiltrados) {
		this.fornecedoresFiltrados = fornecedoresFiltrados;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public AutenticacaoBean getAutenticacaoBean() {
		return autenticacaoBean;
	}

	public void setAutenticacaoBean(AutenticacaoBean autenticacaoBean) {
		this.autenticacaoBean = autenticacaoBean;
	}

	public void salvar() {
		try {
			Usuario usuario = usuarioDao.buscar(autenticacaoBean.getUsuarioLogado().getId());
			fornecedor.setUsuario(usuario);
			fornecedorDao.merge(fornecedor);

			novo();

			fornecedores = fornecedorDao.listar();

			Messages.addGlobalInfo("Fornecedor salvo com sucesso");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar o fornecedor");
			erro.printStackTrace();
		}
	}

	@PostConstruct
	public void listar() {
		try {
			fornecedores = fornecedorDao.listar();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os fornecedores!");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		fornecedor = (Fornecedor) evento.getComponent().getAttributes().get("fornecedorSelecionado");
	}

	public void excluir(ActionEvent evento) {
		try {
			fornecedor = (Fornecedor) evento.getComponent().getAttributes().get("fornecedorSelecionado");

			fornecedorDao.excluir(fornecedor);

			fornecedores = fornecedorDao.listar();

			Messages.addGlobalInfo("Fornecedor removido com sucesso!");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar remover o fornecedor");
			erro.printStackTrace();
		}
	}

	public void pesquisar(String descricao) {
		fornecedoresFiltrados = fornecedorDao.buscarPorDescricao(descricao);
	}

}
