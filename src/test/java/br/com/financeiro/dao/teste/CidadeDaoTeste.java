package br.com.financeiro.dao.teste;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import br.com.financeiro.dao.CidadeDao;
import br.com.financeiro.dao.EstadoDao;
import br.com.financeiro.domain.Cidade;
import br.com.financeiro.domain.Estado;

public class CidadeDaoTeste {
	
	private EstadoDao estadoDao = new EstadoDao();
	private CidadeDao cidadeDao = new CidadeDao();

	@Test
	@Ignore
	public void salvar() {
		Long codigoEstado = 2L;

		Estado estado = estadoDao.buscar(codigoEstado);

		Cidade cidade = new Cidade();
		cidade.setNome("Catingueira");
		cidade.setEstado(estado);

		cidadeDao.salvar(cidade);
	}

	@Test
	@Ignore
	public void listar() {
		List<Cidade> resultado = cidadeDao.listar();

		for (Cidade cidade : resultado) {
			System.out.println("Código da Cidade: " + cidade.getId());
			System.out.println("Nome da Cidade: " + cidade.getNome());
			System.out.println("Código do Estado: " + cidade.getEstado().getId());
			System.out.println("Sigla do Estado: " + cidade.getEstado().getSigla());
			System.out.println("Nome do Estado: " + cidade.getEstado().getNome());
			System.out.println();
		}
	}

	@Test
	@Ignore
	public void buscar() {
		Long codigo = 7L;

		Cidade cidade = cidadeDao.buscar(codigo);

		System.out.println("Código da Cidade: " + cidade.getId());
		System.out.println("Nome da Cidade: " + cidade.getNome());
		System.out.println("Código do Estado: " + cidade.getEstado().getId());
		System.out.println("Sigla do Estado: " + cidade.getEstado().getSigla());
		System.out.println("Nome do Estado: " + cidade.getEstado().getNome());
	}

	@Test
	@Ignore
	public void excluir() {
		Long codigo = 7L;

		Cidade cidade = cidadeDao.buscar(codigo);

		cidadeDao.excluir(cidade);

		System.out.println("Cidade Removida");
		System.out.println("Código da Cidade: " + cidade.getId());
		System.out.println("Nome da Cidade: " + cidade.getNome());
		System.out.println("Código do Estado: " + cidade.getEstado().getId());
		System.out.println("Sigla do Estado: " + cidade.getEstado().getSigla());
		System.out.println("Nome do Estado: " + cidade.getEstado().getNome());
	}

	@Test
	@Ignore
	public void editar() {
		Long codigoCidade = 6L;
		Long codigoEstado = 11L;

		Estado estado = estadoDao.buscar(codigoEstado);

		System.out.println("Código do Estado: " + estado.getId());
		System.out.println("Sigla do Estado: " + estado.getSigla());
		System.out.println("Nome do Estado: " + estado.getNome());

		Cidade cidade = cidadeDao.buscar(codigoCidade);

		System.out.println("Cidade A Ser Editada");
		System.out.println("Código da Cidade: " + cidade.getId());
		System.out.println("Nome da Cidade: " + cidade.getNome());
		System.out.println("Código do Estado: " + cidade.getEstado().getId());
		System.out.println("Sigla do Estado: " + cidade.getEstado().getSigla());
		System.out.println("Nome do Estado: " + cidade.getEstado().getNome());

		cidade.setNome("Guarapuava");
		cidade.setEstado(estado);

		cidadeDao.editar(cidade);

		System.out.println("Cidade Editada");
		System.out.println("Código da Cidade: " + cidade.getId());
		System.out.println("Nome da Cidade: " + cidade.getNome());
		System.out.println("Código do Estado: " + cidade.getEstado().getId());
		System.out.println("Sigla do Estado: " + cidade.getEstado().getSigla());
		System.out.println("Nome do Estado: " + cidade.getEstado().getNome());
	}
}
