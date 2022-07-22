package br.com.financeiro.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import br.com.financeiro.domain.Cliente;
import br.com.financeiro.util.HibernateUtil;

public class ClienteDao extends GenericDao<Cliente> {

	@SuppressWarnings("unchecked")
	public List<Cliente> listarOrdenadoCliente(){
		
		Session session = HibernateUtil.getFabricaDeSessoes().openSession();
		
		try {
			
			Criteria consulta = session.createCriteria(Cliente.class);
			consulta.createAlias("pessoa", "p");
			consulta.addOrder(Order.asc("p.nome"));
			
			List<Cliente> resultado = consulta.list();
			
			return resultado;
			
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			session.close();
		}
	}
}
