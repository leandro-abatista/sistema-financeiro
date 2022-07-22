package br.com.financeiro.dao;


import javax.persistence.Query;

import org.hibernate.Session;

import br.com.financeiro.domain.ItemVenda;
import br.com.financeiro.util.HibernateUtil;

public class ItemVendaDao extends GenericDao<ItemVenda> {
	
	private Session session = HibernateUtil.getFabricaDeSessoes().openSession();

	public ItemVenda buscarPorIdVenda(Long id){
		Query query  = (Query) session.createQuery("select id_produto from itemvenda where id_venda = " + id);
		query.setParameter("id", id);
		ItemVenda itemVenda = (ItemVenda) query.getSingleResult();
		return itemVenda;
	}
}
