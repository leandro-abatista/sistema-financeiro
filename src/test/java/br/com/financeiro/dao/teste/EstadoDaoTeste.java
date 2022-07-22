package br.com.financeiro.dao.teste;

import org.junit.Test;

import br.com.financeiro.dao.EstadoDao;
import br.com.financeiro.domain.Estado;

public class EstadoDaoTeste {
	
	private EstadoDao dao = new EstadoDao();

	@Test
	public void salvar() {
		Estado estado = new Estado();
		estado.setNome("Paraíba");
		estado.setSigla("PB");
		
		dao.salvar(estado);
		System.out.println("Estado salvo com sucesso!" + estado);
	}
}
