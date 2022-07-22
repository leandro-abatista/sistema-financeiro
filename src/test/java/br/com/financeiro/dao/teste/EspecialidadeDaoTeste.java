package br.com.financeiro.dao.teste;

import org.junit.Ignore;
import org.junit.Test;

import br.com.financeiro.dao.EspecialidadeDao;
import br.com.financeiro.domain.Especialidade;

public class EspecialidadeDaoTeste {

	private EspecialidadeDao dao = new EspecialidadeDao();
	
	@Test
	@Ignore
	public void salvar() {
		
		Especialidade especialidade = new Especialidade();
		especialidade.setDescricao("CARDIOLOGIA");
		
		dao.merge(especialidade);
		
		System.out.println("Especialidade sava com sucesso!");
	}
}
