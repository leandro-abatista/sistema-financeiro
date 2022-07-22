package br.com.financeiro.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.financeiro.domain.Estoque;
import br.com.financeiro.domain.Produto;
import br.com.financeiro.util.HibernateUtil;

public class EstoqueDao extends GenericDao<Estoque> {

	private Session session = HibernateUtil.getFabricaDeSessoes().openSession();
	private Transaction transaction = null;

	public void salvar(Produto produto) {
		session = HibernateUtil.getFabricaDeSessoes().openSession();

		try {
			transaction = session.beginTransaction();
			
			if (produto.getId() != null) {
				session.update(produto);
			}
			
			transaction.commit();
		} catch (RuntimeException erro) {
			if (transaction != null) {
				transaction.rollback();
			}
			throw erro;
		} finally {
			session.close();
		}
	}
}
