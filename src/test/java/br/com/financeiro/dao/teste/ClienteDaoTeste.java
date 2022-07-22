package br.com.financeiro.dao.teste;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Test;

import br.com.financeiro.dao.ClienteDao;
import br.com.financeiro.dao.PessoaDao;
import br.com.financeiro.dao.UsuarioDao;
import br.com.financeiro.domain.Cliente;
import br.com.financeiro.domain.Pessoa;
import br.com.financeiro.domain.Usuario;

public class ClienteDaoTeste {

	private ClienteDao clienteDao = new ClienteDao();
	private PessoaDao pessoaDao = new PessoaDao();

	@Test
	public void salvar() throws ParseException {
		Pessoa pessoa = pessoaDao.buscar(1L);

		System.out.println("Pessoa Encontrada");
		System.out.println("Nome: " + pessoa.getNome());
		System.out.println("CPF: " + pessoa.getCpf());

		Cliente cliente = new Cliente();
		cliente.setDataCadastro(new SimpleDateFormat("dd/MM/yyyy").parse("09/06/2015"));
		cliente.setLiberado(true);
		cliente.setPessoa(pessoa);
		
		clienteDao.salvar(cliente);

		System.out.println("Usuário salvo com sucesso.");
	}
}
