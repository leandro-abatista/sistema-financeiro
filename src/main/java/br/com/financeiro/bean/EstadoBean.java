package br.com.financeiro.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.financeiro.dao.EstadoDao;
import br.com.financeiro.domain.Estado;

@ManagedBean(name = "estadoBean")
@ViewScoped
public class EstadoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private EstadoDao estadoDao = new EstadoDao();
	private Estado estado;
	private List<Estado> estados;

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	
	public List<Estado> getEstados() {
		return estados;
	}

	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}

	public void novo() {
		estado = new Estado();
	}

	public void salvar() {
		try {
			estadoDao.merge(estado);

			novo();
			
			estados = estadoDao.listar();

			Messages.addGlobalInfo("Estado salvo com sucesso");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar o estado");
			erro.printStackTrace();
		}
	}

	@PostConstruct
	public void listar() {
		try {
			estados = estadoDao.listar();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os estados!");
			erro.printStackTrace();
		}
	}
	
	public void editar(ActionEvent evento) {
		estado = (Estado) evento.getComponent().getAttributes().get("estadoSelecionado");
	}

	public void excluir(ActionEvent evento) {
		try {
			estado = (Estado) evento.getComponent().getAttributes().get("estadoSelecionado");

			estadoDao.excluir(estado);

			estados = estadoDao.listar();

			Messages.addGlobalInfo("Estado removido com sucesso!");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar remover o estado");
			erro.printStackTrace();
		}
	}

}
