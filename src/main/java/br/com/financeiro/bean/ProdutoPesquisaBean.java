package br.com.financeiro.bean;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.omnifaces.util.Messages;

import br.com.financeiro.dao.HistoricoDao;
import br.com.financeiro.dao.ProdutoDao;
import br.com.financeiro.dao.UsuarioDao;
import br.com.financeiro.domain.Historico;
import br.com.financeiro.domain.Produto;
import br.com.financeiro.domain.Usuario;

@ManagedBean(name = "produtoPesquisaBean")
@ViewScoped
public class ProdutoPesquisaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Produto produto;
	private Historico historico;
	private Usuario usuario;
	private ProdutoDao produtoDao = new ProdutoDao();
	private HistoricoDao historicoDao = new HistoricoDao();
	private UsuarioDao usuarioDao = new UsuarioDao();
	
	@ManagedProperty(value = "#{autenticacaoBean}")
	private AutenticacaoBean autenticacaoBean;

	private boolean exibir;
	
	public AutenticacaoBean getAutenticacaoBean() {
		return autenticacaoBean;
	}
	
	public void setAutenticacaoBean(AutenticacaoBean autenticacaoBean) {
		this.autenticacaoBean = autenticacaoBean;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public boolean isExibir() {
		return exibir;
	}

	public void setExibir(boolean exibir) {
		this.exibir = exibir;
	}
	
	public Historico getHistorico() {
		return historico;
	}
	
	public void setHistorico(Historico historico) {
		this.historico = historico;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@PostConstruct
	public void novo() {
		historico = new Historico();
		produto = new Produto();
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
			
			historico.setHorario(new Date());
			historico.setProduto(produto);
			Usuario usuario = usuarioDao.buscar(autenticacaoBean.getUsuarioLogado().getId());
			historico.setUsuarioLogado(usuario);
			
			historicoDao.salvar(historico);
			
			Messages.addGlobalInfo("Histórico salvo com sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar o histórico!");
			erro.printStackTrace();
		}
	}
	
	public void limparCampo() {
		historico = new Historico();
	}
	
	public void novaBusca() {
		novo();
	}
	
}
