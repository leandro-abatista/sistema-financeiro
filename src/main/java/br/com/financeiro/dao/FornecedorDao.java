package br.com.financeiro.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.financeiro.domain.Fornecedor;
import br.com.financeiro.util.HibernateUtil;

public class FornecedorDao extends GenericDao<Fornecedor> {

	private Session session = HibernateUtil.getFabricaDeSessoes().openSession();

	@SuppressWarnings("unchecked")
	public List<Fornecedor> buscarPorDescricao(String razaoSocial){
		
		Criteria resultado = session.createCriteria(Fornecedor.class);
		if (razaoSocial != null && !razaoSocial.isEmpty()) {
			resultado.add(Restrictions.ilike("razaoSocial", razaoSocial));
		}
		
		List<Fornecedor> lista = resultado.list();
		
		return lista;
	}

	/*
	 * @SuppressWarnings("unchecked")
	public List<Fornecedor> pesquisarPorNome(String descricao) {

		//session.createQuery("select * from Fornecedor where descricao like %" + descricao + "%");
		String hql = "from Fornecedor f where f.descricao like :descricao";
		
		return session.createQuery(hql).setParameter("descricao", descricao).list();
	}
	 * 
	 * 
	public List<Restaurante> busca(Restaurante restaurante) { 
		String hql = "from Restaurante r where r.nome like :nome"
	+ " and r.endereco like :endereco"
				+ " and r.tipoDeComida.nome like :tipoDeComida"; return session.createQuery(hql) 
						.setParameter("nome", restaurante.getNome()) 
						.setParameter("endereco", restaurante.getEndereco()) 
						.setParameter("tipoDeComida", restaurante.getTipoDeComida().getNome()) .list() }*/

}
