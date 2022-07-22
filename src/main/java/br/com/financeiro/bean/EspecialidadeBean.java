package br.com.financeiro.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.omnifaces.util.Messages;

import br.com.financeiro.dao.EspecialidadeDao;
import br.com.financeiro.domain.Especialidade;

@ManagedBean(name = "especBean")
@ViewScoped
public class EspecialidadeBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private EspecialidadeDao especialidadeDao = new EspecialidadeDao();
	private Especialidade especialidade;
	private List<Especialidade> especialidades;
	
	@ManagedProperty(value = "#{autenticacaoBean}")
	private AutenticacaoBean autenticacaoBean;

	public Especialidade getEspecialidade() {
		return especialidade;
	}

	public void setEspecialidade(Especialidade especialidade) {
		this.especialidade = especialidade;
	}

	public List<Especialidade> getEspecialidades() {
		return especialidades;
	}

	public void novo() {
		especialidade = new Especialidade();
	}
	
	public AutenticacaoBean getAutenticacaoBean() {
		return autenticacaoBean;
	}

	public void setAutenticacaoBean(AutenticacaoBean autenticacaoBean) {
		this.autenticacaoBean = autenticacaoBean;
	}

	public void salvar() {

		try {
			especialidadeDao.merge(especialidade);
			novo();
			//especialidades = especialidadeDao.listar();
			Messages.addFlashGlobalInfo("Especialidade salva com sucesso!");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Erro ao tentar salvar uma nova especialidade!");
			erro.printStackTrace();
		}
	}
}
