package br.com.financeiro.teste;

import java.util.List;

import org.hibernate.Session;
import org.junit.Ignore;
import org.junit.Test;

import br.com.financeiro.dao.EstadoDao;
import br.com.financeiro.domain.Estado;
import br.com.financeiro.util.HibernateUtil;

public class HibernateUtilTeste {

	@Test
	public void conectar() {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		sessao.close();
		HibernateUtil.getFabricaDeSessoes().close();
	}

	@Test
	@Ignore
	public void merge() {
		Estado estado = new Estado();
		estado.setNome("Paraná");
		estado.setSigla("PA");

		Estado estado2 = new Estado();
		estado2.setNome("Distrito Federal");
		estado2.setSigla("DF");

		EstadoDao estadoDAO = new EstadoDao();
		estadoDAO.merge(estado);
		estadoDAO.merge(estado2);
	}

	@Test
	@Ignore
	public void listar() {
		EstadoDao estadoDAO = new EstadoDao();
		List<Estado> resultado = estadoDAO.listar();

		System.out.println("Total de Registros Encontrados: " + resultado.size());

		for (Estado estado : resultado) {
			System.out.println(estado.getId() + " - " + estado.getSigla() + " - " + estado.getNome());
		}
	}

	@Test
	@Ignore
	public void buscar() {
		Long codigo = 3L;

		EstadoDao estadoDAO = new EstadoDao();
		Estado estado = estadoDAO.buscar(codigo);

		if (estado == null) {
			System.out.println("Nenhum registro encontrado");
		} else {
			System.out.println("Registro encontrado:");
			System.out.println(estado.getId() + " - " + estado.getSigla() + " - " + estado.getNome());
		}
	}

	@Test
	@Ignore
	public void excluir() {
		Long id = 4L;
		EstadoDao estadoDAO = new EstadoDao();
		Estado estado = estadoDAO.buscar(id);

		if (estado == null) {
			System.out.println("Nenhum registro encontrado");
		} else {
			estadoDAO.excluir(estado);
			System.out.println("Registro removido:");
			System.out.println(estado.getId() + " - " + estado.getSigla() + " - " + estado.getNome());
		}
	}

	@Test
	@Ignore
	public void editar() {
		Long codigo = 3L;
		EstadoDao estadoDAO = new EstadoDao();
		Estado estado = estadoDAO.buscar(codigo);

		if (estado == null) {
			System.out.println("Nenhum registro encontrado");
		} else {
			System.out.println("Registro editado - Antes:");
			System.out.println(estado.getId() + " - " + estado.getSigla() + " - " + estado.getNome());

			estado.setNome("Goiás");
			estado.setSigla("GO");
			estadoDAO.editar(estado);

			System.out.println("Registro editado - Depois:");
			System.out.println(estado.getId() + " - " + estado.getSigla() + " - " + estado.getNome());
		}
	}
}
