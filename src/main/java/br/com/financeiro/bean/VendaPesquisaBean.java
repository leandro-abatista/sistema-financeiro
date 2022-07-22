package br.com.financeiro.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.omnifaces.util.Messages;

import br.com.financeiro.dao.ItemVendaDao;
import br.com.financeiro.dao.VendaDao;
import br.com.financeiro.domain.ItemVenda;
import br.com.financeiro.domain.Produto;
import br.com.financeiro.domain.Venda;

@ManagedBean(name = "vendaPesquisaBean")
@ViewScoped
public class VendaPesquisaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Venda venda;
	private VendaDao vendaDao = new VendaDao();
	private boolean exibir;
	private ItemVendaDao itemVendaDao = new ItemVendaDao();
	private List<ItemVenda> itensvendas;
	private ItemVenda itemVenda;
	private List<Produto> produtos;
	private List<Venda> vendas;
	private Produto produto;

	public Venda getVenda() {
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}

	public boolean isExibir() {
		return exibir;
	}

	public void setExibir(boolean exibir) {
		this.exibir = exibir;
	}
	
	public List<ItemVenda> getItensvendas() {
		return itensvendas;
	}
	
	public List<Produto> getProdutos() {
		return produtos;
	}
	
	public List<Venda> getVendas() {
		return vendas;
	}
	
	public Produto getProduto() {
		return produto;
	}
	
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
	public ItemVenda getItemVenda() {
		return itemVenda;
	}

	public void setItemVenda(ItemVenda itemVenda) {
		this.itemVenda = itemVenda;
	}

	@PostConstruct
	public void novo() {
		//historico = new Historico();
		venda = new Venda();
		exibir = false;
	}

	public Venda buscar() {
		try {
			Venda resultado = vendaDao.buscarPorId(venda.getId());

			if (resultado == null) {
				novo();
				exibir = false;
				Messages.addFlashGlobalError("Nenhuma venda encontrada com o código informado!");
			} else {
				exibir = true;
				venda = resultado;
			}
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar buscar uma venda!");
			erro.printStackTrace();
		}
		return venda;
	}
	
	public void limparCampo() {
		//historico = new Historico();
	}
	
	public void novaBusca() {
		this.novo();
	}

}
