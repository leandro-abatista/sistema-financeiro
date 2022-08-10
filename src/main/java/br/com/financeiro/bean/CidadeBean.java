package br.com.financeiro.bean;

import java.io.Serializable;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;
import org.primefaces.component.datatable.DataTable;

import br.com.financeiro.dao.CidadeDao;
import br.com.financeiro.dao.EstadoDao;
import br.com.financeiro.domain.Cidade;
import br.com.financeiro.domain.Estado;
import br.com.financeiro.util.HibernateUtil;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;

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
	
	public void imprimir() {
		try {
			DataTable tabela = (DataTable) Faces.getViewRoot().findComponent("formListagem:tabela");
			
			Map<String, Object> filtros = tabela.getFilters();
			
			String proDescricao = (String) filtros.get("descricao");
			String forDescricao = (String) filtros.get("fornecedor.descricao");
			
			//caminho do relatório
			String caminho = Faces.getRealPath("/reports/rel_cidade.jasper");
			
//			Map<String, Object> parametros = new HashMap<>();
//			
//			if (proDescricao == null) {
//				parametros.put("PRODUTO_DESCRICAO", "%%");
//			} else {
//				parametros.put("PRODUTO_DESCRICAO", "%" + proDescricao + "%");
//			}
//			
//			if (forDescricao == null) {
//				parametros.put("FORNECEDOR_DESCRICAO", "%%");
//			} else {
//				parametros.put("FORNECEDOR_DESCRICAO", "%" + forDescricao + "%");
//			}
			
			Connection conexao = HibernateUtil.getConexao();
			
			//blioteca do jasperreports
			JasperPrint relatorio = JasperFillManager.fillReport(caminho,null, conexao);
			
			JasperPrintManager.printReport(relatorio, true);
			
			System.out.println("Relatório gerado");
			
		} catch (JRException | RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar carregar o relatório!");
			erro.printStackTrace();
		}
	}
	

}

