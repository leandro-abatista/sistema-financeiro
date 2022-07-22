package br.com.financeiro.dao.teste;

import org.junit.Test;

import br.com.financeiro.dao.CidadeDao;
import br.com.financeiro.dao.EnderecoDao;
import br.com.financeiro.domain.Cidade;
import br.com.financeiro.domain.Endereco;

public class EnderecoDaoTeste {

	private EnderecoDao enderecoDao = new EnderecoDao();
	private CidadeDao cidadeDao = new CidadeDao();

	@Test
	public void salvar() {

		Endereco endereco = new Endereco();
		endereco.setLogradouro("Av rio branco");
		endereco.setNumero(new Short("197"));
		endereco.setBairro("Centro");
		
		Cidade cidade = cidadeDao.buscar(1L);
		endereco.setCidade(cidade);
		endereco.setCep("88015201");
		endereco.setComplemento("Empresa");
		
		enderecoDao.salvar(endereco);

		System.out.println("Pessoa salva com sucesso.");
	}
}
