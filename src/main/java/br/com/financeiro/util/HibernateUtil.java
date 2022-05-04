package br.com.financeiro.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;


public class HibernateUtil {

	//cria a fábrica de sess]ao
    private static final SessionFactory fabricaDeSessoes = criarFabricaDeSessoes();

    private static SessionFactory criarFabricaDeSessoes() {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
        	
        	
        	Configuration configuracao = new Configuration().configure();
        	//configuration.configure();
        	
        	ServiceRegistry registro = new StandardServiceRegistryBuilder()
        			.applySettings(configuracao.getProperties()).build();
        	
        	SessionFactory fabricaDeSessao = configuracao.buildSessionFactory(registro);
        	
        	return fabricaDeSessao;
        			
        	/*
            return new Configuration().configure().buildSessionFactory(
			    new StandardServiceRegistryBuilder().build() );*/
        }
        catch (Throwable ex) {
            // Mensagem de erro de conex�o
            System.err.println("Erro ao tentar a fábrica de sessão\nNão foi possível criar conexão com o banco de dados." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getFabricaDeSessoes() {
        return fabricaDeSessoes;
    }

}
