package br.com.financeiro.dao;

import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import br.com.financeiro.domain.RiscoCirurgico;
import br.com.financeiro.util.HibernateUtil;

public class RiscoCirurgicoDao extends GenericDao<RiscoCirurgico> {
	
	@SuppressWarnings("unchecked")
	public Collection<RiscoCirurgico> findByFilter(RiscoCirurgico riscoCirurgico) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(this.getClass(), "riscoCirurgico");
			
			if (riscoCirurgico.getId() != null) {
				consulta.add(Restrictions.idEq("id"));
			}
			if (StringUtils.isNotBlank(riscoCirurgico.getNome())) {
				consulta.add(Restrictions.like("nome", riscoCirurgico.getNome(), MatchMode.ANYWHERE));
			}
			if (StringUtils.isNotBlank(riscoCirurgico.getCpf())) {
				consulta.add(Restrictions.like("cpf", riscoCirurgico.getCpf(), MatchMode.ANYWHERE));
			}
			
			return consulta.list();
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
		
	}

}
