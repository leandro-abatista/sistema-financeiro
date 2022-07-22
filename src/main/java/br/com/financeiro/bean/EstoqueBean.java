package br.com.financeiro.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.omnifaces.util.Messages;

import br.com.financeiro.dao.EstoqueDao;
import br.com.financeiro.dao.FornecedorDao;
import br.com.financeiro.dao.HistoricoDao;
import br.com.financeiro.dao.ProdutoDao;
import br.com.financeiro.dao.UsuarioDao;
import br.com.financeiro.domain.Estoque;
import br.com.financeiro.domain.Fornecedor;
import br.com.financeiro.domain.Historico;
import br.com.financeiro.domain.Produto;
import br.com.financeiro.domain.Usuario;

@ManagedBean(name = "estoqueBean")
@ViewScoped
public class EstoqueBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Produto produto;
	private Usuario usuario;
	private Estoque estoque;
	private List<Fornecedor> fornecedores;
	private Fornecedor fornecedor;
	private FornecedorDao fornecedorDao = new FornecedorDao();
	private ProdutoDao produtoDao = new ProdutoDao();
	private UsuarioDao usuarioDao = new UsuarioDao();
	private EstoqueDao estoqueDao = new EstoqueDao();

	@ManagedProperty(value = "#{autenticacaoBean}")
	private AutenticacaoBean autenticacaoBean;

	private boolean exibir;

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Estoque getEstoque() {
		return estoque;
	}

	public void setEstoque(Estoque estoque) {
		this.estoque = estoque;
	}

	public AutenticacaoBean getAutenticacaoBean() {
		return autenticacaoBean;
	}

	public void setAutenticacaoBean(AutenticacaoBean autenticacaoBean) {
		this.autenticacaoBean = autenticacaoBean;
	}

	public boolean isExibir() {
		return exibir;
	}

	public void setExibir(boolean exibir) {
		this.exibir = exibir;
	}

	public List<Fornecedor> getFornecedores() {
		return fornecedores;
	}

	public void setFornecedores(List<Fornecedor> fornecedores) {
		this.fornecedores = fornecedores;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	@PostConstruct
	public void novo() {
		estoque = new Estoque();
		produto = new Produto();
		fornecedores = fornecedorDao.listar();
		exibir = false;
	}

	public Produto buscar() {
		try {
			Produto resultado = produtoDao.buscar(produto.getId());

			if (resultado == null) {
				novo();
				exibir = false;
				Messages.addFlashGlobalWarn("Nenhum produto encontrado com o código informado!");
			} else {
				exibir = true;
				produto = resultado;
			}
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar buscar um produto!");
			erro.printStackTrace();
		}
		return produto;
	}

	public void salvar() {
		try {

			estoque.setHorario(new Date());
			estoque.setProduto(produto);
			Usuario usuario = usuarioDao.buscar(autenticacaoBean.getUsuarioLogado().getId());
			estoque.setUsuarioLogado(usuario);

			estoqueDao.merge(estoque);

			Messages.addGlobalInfo("Atualização de estoque salvo com sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar atualizar o estoque!");
			erro.printStackTrace();
		}
	}

	public void limparCampo() {
		estoque = new Estoque();
	}

	public void novaBusca() {
		novo();
	}

}
