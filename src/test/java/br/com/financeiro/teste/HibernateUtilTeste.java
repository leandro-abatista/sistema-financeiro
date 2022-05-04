package br.com.financeiro.teste;

import org.hibernate.Session;
import org.junit.Test;

import br.com.financeiro.util.HibernateUtil;

public class HibernateUtilTeste {

	@Test
	public void conectar() {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		sessao.close();
		HibernateUtil.getFabricaDeSessoes().close();
	}
}
