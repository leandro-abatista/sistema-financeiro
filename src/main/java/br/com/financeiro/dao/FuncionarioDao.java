package br.com.financeiro.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import br.com.financeiro.domain.Funcionario;
import br.com.financeiro.util.HibernateUtil;

public class FuncionarioDao extends GenericDao<Funcionario> {

	@SuppressWarnings("unchecked")
	public List<Funcionario> listarOrdenadoFuncionario(){
		
		Session session = HibernateUtil.getFabricaDeSessoes().openSession();
		
		try {
			
			Criteria consulta = session.createCriteria(Funcionario.class);
			consulta.createAlias("pessoa", "p");
			consulta.addOrder(Order.asc("p.nome"));
			
			List<Funcionario> resultado = consulta.list();
			
			return resultado;
			
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			session.close();
		}
	}
}
