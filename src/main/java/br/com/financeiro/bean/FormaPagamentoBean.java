package br.com.financeiro.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.financeiro.dao.FormaPagamentoDao;
import br.com.financeiro.dao.UsuarioDao;
import br.com.financeiro.domain.FormaPagamento;
import br.com.financeiro.domain.Usuario;

@ViewScoped
@ManagedBean(name = "pagamentoBean")
public class FormaPagamentoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private FormaPagamento pagamento;
	private FormaPagamentoDao pagamentoDao = new FormaPagamentoDao();
	private List<FormaPagamento> pagamentos;

	@ManagedProperty(value = "#{autenticacaoBean}")
	private AutenticacaoBean autenticacaoBean;
	private UsuarioDao usuarioDao = new UsuarioDao();

	public FormaPagamento getPagamento() {
		return pagamento;
	}

	public void setPagamento(FormaPagamento pagamento) {
		this.pagamento = pagamento;
	}

	public FormaPagamentoDao getPagamentoDao() {
		return pagamentoDao;
	}

	public void setPagamentoDao(FormaPagamentoDao pagamentoDao) {
		this.pagamentoDao = pagamentoDao;
	}

	public List<FormaPagamento> getPagamentos() {
		return pagamentos;
	}

	public void setPagamentos(List<FormaPagamento> pagamentos) {
		this.pagamentos = pagamentos;
	}

	public void novo() {
		pagamento = new FormaPagamento();
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
			pagamento.setUsuarioLogado(usuario);
			pagamentoDao.merge(pagamento);
			
			novo();
			
			pagamentos = pagamentoDao.listar("id");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Erro ao tentar salvar uma nova forma de pagamento!");
			erro.printStackTrace();
		}
	}

	@PostConstruct
	public void listar() {
		try {
			pagamentos = pagamentoDao.listar();
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Erro ao tentar listar as formas de pagamento!");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		pagamento = (FormaPagamento) evento.getComponent().getAttributes().get("fpSelecionado");
	}

	public void excluir(ActionEvent evento) {
		try {
			pagamento = (FormaPagamento) evento.getComponent().getAttributes().get("fpSelecionado");

			pagamentoDao.excluir(pagamento);

			Messages.addGlobalInfo("Forma de pagamento removida com sucesso!");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar remover a forma de pagamento!");
			erro.printStackTrace();
		}
	}
}
