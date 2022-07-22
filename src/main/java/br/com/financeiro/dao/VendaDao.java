package br.com.financeiro.dao;

import java.util.List;

import javax.persistence.Query;

import org.apache.commons.collections.functors.IfClosure;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import br.com.financeiro.domain.ItemVenda;
import br.com.financeiro.domain.Produto;
import br.com.financeiro.domain.Venda;
import br.com.financeiro.util.HibernateUtil;

public class VendaDao extends GenericDao<Venda> {
	
	private Session session = null;
	private Transaction transacao = null;

	public void salvar(Venda venda, List<ItemVenda> itensVenda){
		session = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			transacao = session.beginTransaction();
			session.save(venda);
			
			for (int posicao = 0; posicao < itensVenda.size(); posicao++) {

				ItemVenda itemVenda = itensVenda.get(posicao);
				itemVenda.setVenda(venda);

				session.save(itemVenda);

				Produto produto = itemVenda.getProduto();

				int qtde = produto.getQuantidade() - itemVenda.getQuantidade();

				if (qtde >= 0) {

					produto.setQuantidade(new Short((qtde + "")));
					// realiza a atualização do produto
					session.update(produto);
				} else {
					throw new RuntimeException("Quantidade insufuciente em estoque!");
				}
			}
			
			transacao.commit();
		} catch (RuntimeException erro) {
			if (transacao != null) {
				transacao.rollback();
			}
			throw erro;
		} finally {
			session.close();
		}
	}
	
	public Venda buscarPorId(Long id) {
		session = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = session.createCriteria(Venda.class);
			consulta.add(Restrictions.eq("id", id));
			Venda resultado = (Venda) consulta.uniqueResult();
			return resultado;

		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			session.close();
		}
	}
}
