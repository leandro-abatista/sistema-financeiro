package br.com.financeiro.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.financeiro.dao.CirurgiaDao;
import br.com.financeiro.domain.Cirurgia;

@ManagedBean(name = "cirurgiaBean")
@ViewScoped
public class CirurgiaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Cirurgia cirurgia;
	private CirurgiaDao cirurgiaDao = new CirurgiaDao();
	private List<Cirurgia> cirurgias;

	@ManagedProperty(value = "#{autenticacaoBean}")
	private AutenticacaoBean autenticacaoBean;

	public Cirurgia getCirurgia() {
		return cirurgia;
	}

	public void setCirurgia(Cirurgia cirurgia) {
		this.cirurgia = cirurgia;
	}

	public List<Cirurgia> getCirurgias() {
		return cirurgias;
	}

	public void setCirurgias(List<Cirurgia> cirurgias) {
		this.cirurgias = cirurgias;
	}

	public AutenticacaoBean getAutenticacaoBean() {
		return autenticacaoBean;
	}

	public void setAutenticacaoBean(AutenticacaoBean autenticacaoBean) {
		this.autenticacaoBean = autenticacaoBean;
	}

	public void novo() {
		cirurgia = new Cirurgia();
	}

	public void salvar() {
		try {
			cirurgiaDao.salvar(cirurgia);
			novo();
			cirurgias = cirurgiaDao.listar("descricao");
			Messages.addFlashGlobalInfo("Cirurgia sava com sucesso!");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Erro ao tentar salvar uma nova cirurgia!");
			erro.printStackTrace();
		}
	}

	@PostConstruct
	public void listar() {
		try {
			cirurgias = cirurgiaDao.listar("descricao");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Erro ao tentar listar as cirurgias!");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {
		try {
			cirurgia = (Cirurgia) evento.getComponent().getAttributes().get("objetoSelecionado");
			cirurgiaDao.excluir(cirurgia);
			cirurgias = cirurgiaDao.listar("descricao");
			Messages.addFlashGlobalInfo("Cirurgia excluída com sucesso!");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Erro ao tentar excluir uma cirurgia!");
		}
	}

}
