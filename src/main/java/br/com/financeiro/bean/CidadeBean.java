package br.com.financeiro.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.financeiro.dao.CidadeDao;
import br.com.financeiro.dao.EstadoDao;
import br.com.financeiro.domain.Cidade;
import br.com.financeiro.domain.Estado;

@ManagedBean(name = "cidadeBean")
@ViewScoped
public class CidadeBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private CidadeDao cidadeDao = new CidadeDao();
	private EstadoDao estadoDao = new EstadoDao();
	private Cidade cidade;
	private Estado estado;
	private List<Estado> estados;
	private List<Cidade> cidades;

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

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

	public List<Cidade> getCidades() {
		return cidades;
	}

	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
	}

	public void novo() {
		cidade = new Cidade();
		estados = estadoDao.listar("nome");
	}

	public void salvar() {
		try {
			cidadeDao.merge(cidade);

			novo();
			
			cidades = cidadeDao.listar();

			Messages.addGlobalInfo("Cidade salva com sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar uma nova cidade!");
			erro.printStackTrace();
		}
	}

	@PostConstruct
	public void listar() {
		try {
			cidades = cidadeDao.listar();
			
			estados = estadoDao.listar();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar as cidades!");
			erro.printStackTrace();
		}
	}
	
	public void editar(ActionEvent evento) {
		cidade = (Cidade) evento.getComponent().getAttributes().get("cidadeSelecionada");
		
		estados = estadoDao.listar();
	}

	public void excluir(ActionEvent evento) {
		try {
			cidade = (Cidade) evento.getComponent().getAttributes().get("cidadeSelecionada");

			cidadeDao.excluir(cidade);

			cidades = cidadeDao.listar();

			Messages.addGlobalInfo("Cidade removida com sucesso!");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar remover a cidade");
			erro.printStackTrace();
		}
	}
	

}

