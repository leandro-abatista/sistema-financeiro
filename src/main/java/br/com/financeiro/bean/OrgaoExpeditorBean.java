package br.com.financeiro.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.financeiro.dao.EstadoDao;
import br.com.financeiro.dao.OrgaoExpeditorDao;
import br.com.financeiro.domain.Estado;
import br.com.financeiro.domain.OrgaoExpeditor;

@ViewScoped
@ManagedBean(name = "OEBean")
public class OrgaoExpeditorBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private OrgaoExpeditor orgaoExpeditor;
	private OrgaoExpeditorDao expeditorDao = new OrgaoExpeditorDao();
	private EstadoDao estadoDao = new EstadoDao();
	private List<OrgaoExpeditor> listaOE;
	private Estado estado;
	private List<Estado> estados;

	public OrgaoExpeditor getOrgaoExpeditor() {
		return orgaoExpeditor;
	}

	public void setOrgaoExpeditor(OrgaoExpeditor orgaoExpeditor) {
		this.orgaoExpeditor = orgaoExpeditor;
	}

	public List<OrgaoExpeditor> getListaOE() {
		return listaOE;
	}

	public void setListaOE(List<OrgaoExpeditor> listaOE) {
		this.listaOE = listaOE;
	}
	
	public List<Estado> getEstados() {
		return estados;
	}
	
	public void novo() {
		orgaoExpeditor = new OrgaoExpeditor();
		estados = estadoDao.listar("nome");
	}
	
	public void salvar() {
		try {
			expeditorDao.merge(orgaoExpeditor);
			novo();
			estados = estadoDao.listar("nome");
			listaOE = expeditorDao.listar();
			Messages.addGlobalInfo("Cliente salvo com sucesso");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar o orgão expeditor!");
			erro.printStackTrace();
		}
	}
	
	@PostConstruct
	public void listar() {
		try {
			listaOE = expeditorDao.listar();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os orgãos expeditores!");
			erro.printStackTrace();
		}
	}
	
	public void editar(ActionEvent evento) {
		orgaoExpeditor = (OrgaoExpeditor) evento.getComponent().getAttributes().get("oeSelecionado");
		estados = estadoDao.listar("nome");
	}

	public void excluir(ActionEvent evento) {
		try {
			orgaoExpeditor = (OrgaoExpeditor) evento.getComponent().getAttributes().get("oeSelecionado");

			expeditorDao.excluir(orgaoExpeditor);

			listaOE = expeditorDao.listar();

			Messages.addGlobalInfo("Orgão expeditor removido com sucesso!");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar remover o orgão expeditor!");
			erro.printStackTrace();
		}
	}
}
