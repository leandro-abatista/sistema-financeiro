package br.com.financeiro.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.financeiro.domain.Produto;
import br.com.financeiro.util.HibernateUtil;

public class ProdutoDao extends GenericDao<Produto> {

	private Session session = HibernateUtil.getFabricaDeSessoes().openSession();
	
	public Produto findProdutoByDescricao(String descricao) {
		
		Criteria consulta = session.createCriteria(Produto.class);
		return (Produto) consulta.add(Restrictions.eq("descricao", descricao)).uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<Produto> listarOrdenadoProduto(){
		
		session = HibernateUtil.getFabricaDeSessoes().openSession();
		
		try {
			
			Criteria consulta = session.createCriteria(Produto.class);
			consulta.createAlias("produto", "p");
			consulta.addOrder(Order.asc("p.descricao"));
			
			List<Produto> resultado = consulta.list();
			
			return resultado;
			
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			session.close();
		}
	}
}
