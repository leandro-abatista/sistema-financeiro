package br.com.financeiro.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.financeiro.dao.CidadeDao;
import br.com.financeiro.dao.EnderecoDao;
import br.com.financeiro.domain.Cidade;
import br.com.financeiro.domain.Endereco;

@ManagedBean(name = "enderecoBean")
@ViewScoped
public class EnderecoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private CidadeDao cidadeDao = new CidadeDao();
	private EnderecoDao enderecoDao = new EnderecoDao();
	private Cidade cidade;
	private Endereco endereco;
	private List<Cidade> cidades;
	private List<Endereco> enderecos;

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public List<Cidade> getCidades() {
		return cidades;
	}

	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
	}
	
	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public List<Endereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	public void novo() {
		endereco = new Endereco();
		enderecos = enderecoDao.listar();
	}

	public void salvar() {
		try {
			enderecoDao.merge(endereco);

			novo();
			
			enderecos = enderecoDao.listar();

			Messages.addGlobalInfo("Endereço salvo com sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar uma novo endereço!");
			erro.printStackTrace();
		}
	}

	@PostConstruct
	public void listar() {
		try {
			
			enderecos = enderecoDao.listar();
			cidades = cidadeDao.listar();
			
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar as cidades!");
			erro.printStackTrace();
		}
	}
	
	public void editar(ActionEvent evento) {
		endereco = (Endereco) evento.getComponent().getAttributes().get("enderecoSelecionado");
		
		cidades = cidadeDao.listar();
	}

	public void excluir(ActionEvent evento) {
		try {
			endereco = (Endereco) evento.getComponent().getAttributes().get("enderecoSelecionado");

			enderecoDao.excluir(endereco);

			enderecos = enderecoDao.listar();

			Messages.addGlobalInfo("Endereço removido com sucesso!");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar remover o endereço!");
			erro.printStackTrace();
		}
	}
	

}

