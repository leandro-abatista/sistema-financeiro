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

}
