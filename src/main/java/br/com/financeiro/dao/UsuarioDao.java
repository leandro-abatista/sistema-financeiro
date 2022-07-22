package br.com.financeiro.dao;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.omnifaces.util.Messages;

import br.com.financeiro.domain.Usuario;
import br.com.financeiro.util.HibernateUtil;

public class UsuarioDao extends GenericDao<Usuario> {

	private Session session = HibernateUtil.getFabricaDeSessoes().openSession();

	public Usuario autenticar(String cpf, String senha) {

		try {
			// usa a classe de usuário como critério
			Criteria criteria = session.createCriteria(Usuario.class);
			criteria.createAlias("pessoa", "p");
			criteria.add(Restrictions.eq("p.cpf", cpf));

			SimpleHash hash = new SimpleHash("md5", senha);
			criteria.add(Restrictions.eq("senha", hash.toHex()));
			Usuario resultado = (Usuario) criteria.uniqueResult();
			return resultado;
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar autenticar usuário!");
			erro.printStackTrace();
		} 
		return null;
	}

}
