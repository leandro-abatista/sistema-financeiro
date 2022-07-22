package br.com.financeiro.util;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.omnifaces.util.Faces;

import br.com.financeiro.bean.AutenticacaoBean;
import br.com.financeiro.domain.Usuario;

public class AutenticacaoListener implements PhaseListener {

	private static final long serialVersionUID = 1L;

	@Override
	public void afterPhase(PhaseEvent event) {
		
		String paginaAtual = Faces.getViewId();
		boolean pagAutenticada = paginaAtual.contains("autenticacao.xhtml");
		
		if (!pagAutenticada) {
			AutenticacaoBean autenticacaoBean = Faces.getSessionAttribute("autenticacaoBean");

			if (autenticacaoBean == null) {
				Faces.navigate("/pages/autenticacao.xhtml");
				return;
			}
			
			Usuario	usuario = autenticacaoBean.getUsuario();//pega o usuário que está logado
			if (usuario == null) {
				Faces.navigate("/pages/autenticacao.xhtml");
				return;
			}
		}
		
	}

	@Override
	public void beforePhase(PhaseEvent event) {
		
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

	
}
