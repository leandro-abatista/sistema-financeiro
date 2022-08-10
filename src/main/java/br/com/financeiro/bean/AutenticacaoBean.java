package br.com.financeiro.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import br.com.financeiro.dao.UsuarioDao;
import br.com.financeiro.domain.Pessoa;
import br.com.financeiro.domain.Usuario;

@ManagedBean(name = "autenticacaoBean")
@SessionScoped
public class AutenticacaoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Usuario usuario;
	private Usuario usuarioLogado;

	private UsuarioDao usuarioDao = new UsuarioDao();

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@PostConstruct
	public void iniciar() {
		usuario = new Usuario();
		usuario.setPessoa(new Pessoa());
	}

	public void autenticar() {
		try {

			usuarioLogado = usuarioDao.autenticar(usuario.getPessoa().getCpf(), usuario.getSenha());

			if (usuarioLogado == null) {
				Messages.addFlashGlobalError("CPF e/ou Senha incorretos!");
				return;
			}
			
			Faces.redirect("./pages/principal.jsf");
			Messages.addGlobalInfo("Usuário logado com sucesso!");
		} catch (IOException | RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar autenticar o usuário!");
			erro.printStackTrace();
		}
	}
	
	public void logout() {
		try {
			Faces.redirect("./pages/autenticacao.jsf");
			Messages.addGlobalInfo("Logout realizado com sucesso!");
		} catch (IOException | RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar realizar logout do sistema!");
			erro.printStackTrace();
		}
	}
	
	public boolean temPermissao(List<String> permissoes) {
		
		for (String permissao : permissoes) {
			if (usuarioLogado.getTipo() == (permissao.charAt(0))) {
				return true;
			}
		}
		return false;
	}
}
