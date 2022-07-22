package br.com.financeiro.dao.teste;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import br.com.financeiro.dao.FornecedorDao;
import br.com.financeiro.domain.Fornecedor;

public class FornecedorDaoTeste {

	private FornecedorDao fornecedorDao = new FornecedorDao();

	@Test
	public void salvar() {
		Fornecedor fornecedor = new Fornecedor();
		fornecedor.setRazaoSocial("Gutierrez Distribuidora de Bebidas Lactas ltda");
		fornecedor.setCnpj("22001005000156");

		fornecedorDao.salvar(fornecedor);
	}

	@Test
	@Ignore
	public void listar() {
		List<Fornecedor> resultado = fornecedorDao.listar();

		System.out.println("Total de Registros Encontrados: " + resultado.size());

		for (Fornecedor fornecedor : resultado) {
			System.out.println(fornecedor.getId() + " - " + fornecedor.getRazaoSocial());
		}
	}

	@Test
	@Ignore
	public void buscar() {
		Long codigo = 3L;

		Fornecedor fornecedor = fornecedorDao.buscar(codigo);

		if (fornecedor == null) {
			System.out.println("Nenhum registro encontrado");
		} else {
			System.out.println("Registro encontrado:");
			System.out.println(fornecedor.getId() + " - " + fornecedor.getRazaoSocial());
		}
	}

}
