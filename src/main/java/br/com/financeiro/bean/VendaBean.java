package br.com.financeiro.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.financeiro.dao.ClienteDao;
import br.com.financeiro.dao.FormaPagamentoDao;
import br.com.financeiro.dao.FuncionarioDao;
import br.com.financeiro.dao.ProdutoDao;
import br.com.financeiro.dao.UsuarioDao;
import br.com.financeiro.dao.VendaDao;
import br.com.financeiro.domain.Cliente;
import br.com.financeiro.domain.FormaPagamento;
import br.com.financeiro.domain.Funcionario;
import br.com.financeiro.domain.ItemVenda;
import br.com.financeiro.domain.Produto;
import br.com.financeiro.domain.Usuario;
import br.com.financeiro.domain.Venda;

@ManagedBean(name = "vendaBean")
@ViewScoped
public class VendaBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private VendaDao vendaDao = new VendaDao();
	private ProdutoDao produtoDao = new ProdutoDao();
	private ClienteDao clienteDao = new ClienteDao();
	private FuncionarioDao funcionarioDao = new FuncionarioDao();
	private Venda venda;
	private List<Produto> produtos;
	private List<ItemVenda> itensVenda;
	private List<Cliente> clientes;
	private List<Funcionario> funcionarios;
	private Funcionario funcionario;
	private UsuarioDao usuarioDao = new UsuarioDao();
	@ManagedProperty(value = "#{autenticacaoBean}")
	private AutenticacaoBean autenticacaoBean;
	
	private List<FormaPagamento> pagamentos;
	private FormaPagamentoDao pagamentoDao = new FormaPagamentoDao();

	public Venda getVenda() {
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public List<ItemVenda> getItensVenda() {
		return itensVenda;
	}

	public void setItensVenda(List<ItemVenda> itensVenda) {
		this.itensVenda = itensVenda;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public AutenticacaoBean getAutenticacaoBean() {
		return autenticacaoBean;
	}

	public void setAutenticacaoBean(AutenticacaoBean autenticacaoBean) {
		this.autenticacaoBean = autenticacaoBean;
	}

	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}
	
	public List<FormaPagamento> getPagamentos() {
		return pagamentos;
	}

	@PostConstruct
	public void novo() {
		try {
			venda = new Venda();
			venda.setPrecoTotal(new BigDecimal("0.00"));

			produtos = produtoDao.listar("descricao");
			
			pagamentos = pagamentoDao.listar("id");

			itensVenda = new ArrayList<>();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar carregar a tela de vendas");
			erro.printStackTrace();
		}
	}

	public void adicionar(ActionEvent evento) {
		Produto produto = (Produto) evento.getComponent().getAttributes().get("produtoSelecionado");

		int achou = -1;
		for (int posicao = 0; posicao < itensVenda.size(); posicao++) {
			if (itensVenda.get(posicao).getProduto().equals(produto)) {
				achou = posicao;
			}
		}

		if (achou < 0) {
			ItemVenda itemVenda = new ItemVenda();
			itemVenda.setPrecoParcial(produto.getPreco());
			itemVenda.setProduto(produto);
			itemVenda.setQuantidade(new Short("1"));

			itensVenda.add(itemVenda);
		} else {
			ItemVenda itemVenda = itensVenda.get(achou);
			itemVenda.setQuantidade(new Short(itemVenda.getQuantidade() + 1 + ""));
			itemVenda.setPrecoParcial(produto.getPreco().multiply(new BigDecimal(itemVenda.getQuantidade())));
		}

		calcular();
	}

	public void remover(ActionEvent evento) {
		ItemVenda itemVenda = (ItemVenda) evento.getComponent().getAttributes().get("itemSelecionado");

		int achou = -1;
		for (int posicao = 0; posicao < itensVenda.size(); posicao++) {
			if (itensVenda.get(posicao).getProduto().equals(itemVenda.getProduto())) {
				achou = posicao;
			}
		}

		if (achou > -1) {
			itensVenda.remove(achou);
		}

		calcular();
	}

	public void calcular() {
		venda.setPrecoTotal(new BigDecimal("0.00"));

		for (int posicao = 0; posicao < itensVenda.size(); posicao++) {
			ItemVenda itemVenda = itensVenda.get(posicao);
			venda.setPrecoTotal(venda.getPrecoTotal().add(itemVenda.getPrecoParcial()));
		}
	}

	public void finalizar() {
		try {
			venda.setHorario(new Date());
			venda.setCliente(null);
			Usuario usuario = usuarioDao.buscar(autenticacaoBean.getUsuarioLogado().getId());
			venda.setUsuario(usuario);
			venda.setFuncionario(funcionario);

			funcionarios = funcionarioDao.listarOrdenadoFuncionario();

			clientes = clienteDao.listarOrdenadoCliente();
			
			pagamentos = pagamentoDao.listar("id");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar finalizar a venda!");
			erro.printStackTrace();
		}
	}

	public void salvar() {
		try {
			if (venda.getPrecoTotal().signum() == 0) {
				Messages.addGlobalError("Informe pelo menos um item para a venda!");
				return;
			}

			vendaDao.salvar(venda, itensVenda);

			venda = new Venda();
			venda.setPrecoTotal(new BigDecimal("0.00"));

			produtos = produtoDao.listar("descricao");
			
			pagamentos = pagamentoDao.listar("id");
			
			itensVenda = new ArrayList<>();

			Messages.addGlobalInfo("Venda realizada com sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar a venda!");
			erro.printStackTrace();
		}
	}
	
	public void getRelatorio() {
		
	}
}
