package br.com.financeiro.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

@WebFilter(filterName = "conexaoFilter", urlPatterns = "/")
public class FilterOpenSessionInView  implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// EXECUTADO UMA ÚNICA VEZ QUANDO O SERVIDOR SOBE NO AR
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// EXECUTADO EM TODA REQUISIÇÃO E RESPOSTA
		chain.doFilter(request, response);
		
	}

	@Override
	public void destroy() {
		//EXECUTADO QUANDO OS RECURSOS SÃO DESCARTADOS
		
	}

}
