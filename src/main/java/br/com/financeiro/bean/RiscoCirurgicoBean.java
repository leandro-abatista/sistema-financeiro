package br.com.financeiro.bean;

import java.io.Serializable;
import java.sql.Connection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.ws.rs.POST;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;
import org.primefaces.component.datatable.DataTable;

import br.com.financeiro.dao.CirurgiaDao;
import br.com.financeiro.dao.EnderecoDao;
import br.com.financeiro.dao.MedicoDao;
import br.com.financeiro.dao.RiscoCirurgicoDao;
import br.com.financeiro.dao.UsuarioDao;
import br.com.financeiro.domain.Cirurgia;
import br.com.financeiro.domain.Endereco;
import br.com.financeiro.domain.Medico;
import br.com.financeiro.domain.RiscoCirurgico;
import br.com.financeiro.domain.Usuario;
import br.com.financeiro.util.HibernateUtil;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;

@ManagedBean(name = "rcBean")
@ViewScoped
public class RiscoCirurgicoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private RiscoCirurgico riscoCirurgico;
	private RiscoCirurgicoDao riscoCirurgicoDao = new RiscoCirurgicoDao();
	private List<RiscoCirurgico> riscosCirurgicos;
	private String[] selectAntPessoais;

	private EnderecoDao enderecoDao = new EnderecoDao();
	private List<Endereco> enderecos;

	private CirurgiaDao cirurgiaDao = new CirurgiaDao();
	private List<Cirurgia> cirurgias;

	private MedicoDao medicoDao = new MedicoDao();
	private List<Medico> medicos;

	private UsuarioDao usuarioDao = new UsuarioDao();
	
	private boolean exibir;

	@ManagedProperty(value = "#{autenticacaoBean}")
	private AutenticacaoBean autenticacaoBean;

	public String[] getSelectAntPessoais() {
		return selectAntPessoais;
	}
	
	public void setSelectAntPessoais(String[] selectAntPessoais) {
		this.selectAntPessoais = selectAntPessoais;
	}
	
	public RiscoCirurgico getRiscoCirurgico() {
		return riscoCirurgico;
	}

	public void setRiscoCirurgico(RiscoCirurgico riscoCirurgico) {
		this.riscoCirurgico = riscoCirurgico;
	}

	public AutenticacaoBean getAutenticacaoBean() {
		return autenticacaoBean;
	}

	public void setAutenticacaoBean(AutenticacaoBean autenticacaoBean) {
		this.autenticacaoBean = autenticacaoBean;
	}

	public List<RiscoCirurgico> getRiscosCirurgicos() {
		return riscosCirurgicos;
	}

	public List<Endereco> getEnderecos() {
		return enderecos;
	}

	public List<Cirurgia> getCirurgias() {
		return cirurgias;
	}

	public List<Medico> getMedicos() {
		return medicos;
	}
	
	public boolean isExibir() {
		return exibir;
	}

	public void setExibir(boolean exibir) {
		this.exibir = exibir;
	}

	/**
	 * Método para criar um novo objeto ARC
	 */
	public void novo() {
		try {
			riscoCirurgico = new RiscoCirurgico();
			enderecos = enderecoDao.listar();
			cirurgias = cirurgiaDao.listar();
			medicos = medicoDao.listar();
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Erro ao tentar gerar uma nova avaliação de risco cirúrgico!");
			erro.printStackTrace();
		}
	}
	
	/**
	 * Método para inserir um novo registro no banco de dados
	 */
	public void salvar() {
		try {
			Usuario usuario = usuarioDao.buscar(autenticacaoBean.getUsuarioLogado().getId());
			riscoCirurgico.setUsuario(usuario);
			riscoCirurgico.setDataAvaliacao(new Date());
			riscoCirurgicoDao.merge(riscoCirurgico);
			novo();
			Messages.addGlobalInfo("Avaliação de Risco Cirúrgico salvo com sucesso!");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Erro ao tentar salvar uma nova avaliação de risco cirúrgico!");
			erro.printStackTrace();
		}
	}
	
	@PostConstruct
	public void listarRC() {
		try {
			riscosCirurgicos = riscoCirurgicoDao.listar();
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Erro ao tentar listar os riscos cirúrgicos!");
			erro.printStackTrace();
		}
	}
	
	public void visualizar(ActionEvent evento) {
		try {
			riscoCirurgico = (RiscoCirurgico) evento.getComponent().getAttributes().get("objetoSelecionado");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Erro ao tentar carregar as informações do risco cirúrgico selecionado!");
			erro.printStackTrace();
		}
	}
	
	public void imprimir() {
		try {
			DataTable tabela = (DataTable) Faces.getViewRoot().findComponent("formBuscar:tabela");
			
			Map<String, Object> filtros = tabela.getFilters();
			
			Long codigo = (Long) filtros.get("codigo");
			String nome = (String) filtros.get("nome");
			
			//caminho do relatório
			String caminho = Faces.getRealPath("/reports/rel_risco_cirurgico.jasper");
			
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
			
			Messages.addFlashGlobalInfo("Relatório gerado com sucesso!");
			
			System.out.println("Relatório gerado");
			
		} catch (JRException | RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar carregar o relatório!");
			erro.printStackTrace();
		}
	}
	
	

}
