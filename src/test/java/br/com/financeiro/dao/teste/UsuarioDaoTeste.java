package br.com.financeiro.dao.teste;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.Ignore;
import org.junit.Test;

import br.com.financeiro.dao.PessoaDao;
import br.com.financeiro.dao.UsuarioDao;
import br.com.financeiro.domain.Pessoa;
import br.com.financeiro.domain.Usuario;

public class UsuarioDaoTeste {

	private UsuarioDao usuarioDao = new UsuarioDao();
	private PessoaDao pessoaDao = new PessoaDao();

	@Test
	//@Ignore
	public void salvar() {
		Pessoa pessoa = pessoaDao.buscar(3L);

		System.out.println("Pessoa Encontrada");
		System.out.println("Nome: " + pessoa.getNome());
		System.out.println("CPF: " + pessoa.getCpf());

		Usuario usuario = new Usuario();
		usuario.setAtivo(true);
		usuario.setPessoa(pessoa);
		usuario.setSenhaSemCriptografia("12345678");
		SimpleHash hash = new SimpleHash("md5", usuario.getSenhaSemCriptografia());
		usuario.setSenha(hash.toHex());
		usuario.setTipo('B');
		//q1w2e356
		usuarioDao.salvar(usuario);

		System.out.println("Usuário salvo com sucesso.");
	}
	
	@Test
	@Ignore
	public void autenticar() {
		String cpf = "078.887.545-55";
		String senha = "12345678";
		
		UsuarioDao usuarioDao = new UsuarioDao();
		Usuario resultado = usuarioDao.autenticar(cpf, senha);
		
		System.out.println("Usuario autenticado:: " + resultado);
	}
}
