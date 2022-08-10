package br.com.financeiro.bean;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import br.com.financeiro.dao.FornecedorDao;
import br.com.financeiro.dao.ProdutoDao;
import br.com.financeiro.dao.UsuarioDao;
import br.com.financeiro.domain.Fornecedor;
import br.com.financeiro.domain.Produto;
import br.com.financeiro.domain.Usuario;
import br.com.financeiro.util.HibernateUtil;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;

@ManagedBean(name = "produtoBean")
@ViewScoped
public class ProdutoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private ProdutoDao produtoDao = new ProdutoDao();
	private FornecedorDao fornecedorDao = new FornecedorDao();
	private Produto produto;
	private List<Produto> produtos;
	private List<Fornecedor> fornecedores;
	
	private UsuarioDao usuarioDao = new UsuarioDao();
	
	@ManagedProperty(value = "#{autenticacaoBean}")
	private AutenticacaoBean autenticacaoBean;


	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public List<Fornecedor> getFornecedor() {
		return fornecedores;
	}

	public void setFornecedores(List<Fornecedor> fornecedores) {
		this.fornecedores = fornecedores;
	}
	
	public AutenticacaoBean getAutenticacaoBean() {
		return autenticacaoBean;
	}

	public void setAutenticacaoBean(AutenticacaoBean autenticacaoBean) {
		this.autenticacaoBean = autenticacaoBean;
	}

	@PostConstruct
	public void listar() {
		try {
			produtos = produtoDao.listar();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os produtos!");
			erro.printStackTrace();
		}
	}

	public void novo() {
		try {
			produto = new Produto();
			fornecedores = fornecedorDao.listar();
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar gerar um novo produto");
			erro.printStackTrace();
		}
	}
	
	public void salvar() {
		try {

			if (produto.getCaminho() == null) {
				Messages.addGlobalInfo("O campo 'Imagem' é obrigatório");
				return;
			}

			/*
			 * if (produto.getId() != null && produto.getDescricao() != null) {
			 * Messages.addFlashGlobalInfo("Já existe este produto no banco de dados!");
			 * return; } else { }
			 * 
			 * }
			 */
			Usuario usuario = usuarioDao.buscar(autenticacaoBean.getUsuarioLogado().getId());
			produto.setUsuario(usuario);
			produto.setDataHora(LocalDateTime.now());
			Produto produtoRetorno = produtoDao.merge(produto);

			Path origem = Paths.get(produto.getCaminho());
			Path destino = Paths.get("C:/ws-udemy/uploads/" + produtoRetorno.getId() + ".jpg");

			Files.copy(origem, destino, StandardCopyOption.REPLACE_EXISTING);

			novo();

			produtos = produtoDao.listar("id");// lista de fora ordenada

			Messages.addGlobalInfo("Produto salvo com sucesso!");
		} catch (RuntimeException | IOException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar salvar o produto!");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		try {
			produto = (Produto) evento.getComponent().getAttributes().get("produtoSelecionado");
			
			produto.setCaminho("C:/ws-udemy/uploads/" + produto.getId() + ".jpg");

			fornecedores = fornecedorDao.listar();
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar selecionar um produto!");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {
		try {
			produto = (Produto) evento.getComponent().getAttributes().get("produtoSelecionado");

			produtoDao.excluir(produto);
			
			Path arquivoImagem = Paths.get("C:/ws-udemy/uploads/" + produto.getId() + ".jpg");
			//se existir imagem no produto
			Files.deleteIfExists(arquivoImagem);

			produtos = produtoDao.listar();

			Messages.addGlobalInfo("Produto removido com sucesso!");
		} catch (RuntimeException | IOException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar remover o produto!");
			erro.printStackTrace();
		}
	}
	
	public void upload(FileUploadEvent evento) {
		try {
			//pega  o arquivo temporário
			UploadedFile arquivoUpload = evento.getFile();
			//cria um arquivo temporário
			Path arquivoTemporario = Files.createTempFile(null, null);
			
			//origem e destino do arquivo
			Files.copy(arquivoUpload.getInputstream(), arquivoTemporario, StandardCopyOption.REPLACE_EXISTING);
			
			//seta o arquivo temporário e converte o arquivo para string
			produto.setCaminho(arquivoTemporario.toString());
			//Messages.addFlashGlobalInfo(produto.getCaminho());
			//System.out.println(produto.getCaminho());
			Messages.addGlobalInfo("Imagem carregada com sucesso!");
			
		} catch (IOException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar carregar a imagem!");
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
			String caminho = Faces.getRealPath("/reports/rel_produto.jasper");
			
			Map<String, Object> parametros = new HashMap<>();
			
			if (proDescricao == null) {
				parametros.put("PRODUTO_DESCRICAO", "%%");
			} else {
				parametros.put("PRODUTO_DESCRICAO", "%" + proDescricao + "%");
			}
			
			if (forDescricao == null) {
				parametros.put("FORNECEDOR_DESCRICAO", "%%");
			} else {
				parametros.put("FORNECEDOR_DESCRICAO", "%" + forDescricao + "%");
			}
			
			Connection conexao = HibernateUtil.getConexao();
			
			//JasperReport report = JasperCompileManager.compileReport(caminho);
			
			//blioteca do jasperreports
			JasperPrint relatorio = JasperFillManager.fillReport(caminho, parametros, conexao);
			
			JasperPrintManager.printReport(relatorio, true);
			
			//JasperExportManager.exportReportToPdf(relatorio);
			System.out.println("Relatório gerado");
			
		} catch (JRException | RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar carregar o relatório!");
			erro.printStackTrace();
		}
	}
	
	public void getArquivoReport() {
		try {
			
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar carregar o relatório!");
			erro.printStackTrace();
		}
	}
	
	public String redirecionaProdutos() {
		return "produtosPrincipal.xhtml?faces-redirect=true";
	}
}
