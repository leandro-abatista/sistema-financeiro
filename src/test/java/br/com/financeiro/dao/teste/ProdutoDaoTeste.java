package br.com.financeiro.dao.teste;

import java.math.BigDecimal;

import org.junit.Test;

import br.com.financeiro.dao.FornecedorDao;
import br.com.financeiro.dao.ProdutoDao;
import br.com.financeiro.domain.Fornecedor;
import br.com.financeiro.domain.Produto;

public class ProdutoDaoTeste {
	
	private FornecedorDao fornecedorDao = new FornecedorDao();
	private ProdutoDao produtoDao = new ProdutoDao();
	
	@Test
	public void salvar(){
		Long id = 2L;
		Fornecedor fornecedor = fornecedorDao.buscar(id);
		
		Produto produto = new Produto();
		produto.setDescricao("Molho mix");
		produto.setFornecedor(fornecedor);
		produto.setPreco(new BigDecimal("200.03"));
		produto.setQuantidade(new Short("48"));
		
		produtoDao.salvar(produto);
		
		System.out.println("Produto salvo com sucesso");
	}
	
}
