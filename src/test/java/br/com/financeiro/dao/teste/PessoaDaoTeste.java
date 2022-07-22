package br.com.financeiro.dao.teste;

import org.junit.Test;

import br.com.financeiro.dao.EnderecoDao;
import br.com.financeiro.dao.PessoaDao;
import br.com.financeiro.domain.Endereco;
import br.com.financeiro.domain.Pessoa;

public class PessoaDaoTeste {

	private PessoaDao pessoaDao = new PessoaDao();
	private EnderecoDao enderecoDao = new EnderecoDao();

	@Test
	public void salvar() {

		Pessoa pessoa = new Pessoa();
		pessoa.setNome("melissa amorim");
		pessoa.setCpf("666.666.666-66");
		pessoa.setCelular("83 9997 6602");
		pessoa.setEmail("melissa@teste.com");
		pessoa.setRg("66666666");
		pessoa.setTelefone("83 99997 6602");
		
		Endereco endereco = enderecoDao.buscar(1L);
		pessoa.setEndereço(endereco);
		
		pessoaDao.salvar(pessoa);

		System.out.println("Pessoa salva com sucesso.");
	}
}
