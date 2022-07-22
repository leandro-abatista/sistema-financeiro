package br.com.financeiro.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ItemVenda extends GenericDomain implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(nullable = false)
	private Short quantidade;

	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal precoParcial;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_produto", referencedColumnName = "id", nullable = false)
	private Produto produto;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_venda", referencedColumnName = "id", nullable = false)
	private Venda venda;

	public Short getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Short quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getPrecoParcial() {
		return precoParcial;
	}

	public void setPrecoParcial(BigDecimal precoParcial) {
		this.precoParcial = precoParcial;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Venda getVenda() {
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}

}
