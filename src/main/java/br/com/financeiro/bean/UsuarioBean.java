package br.com.financeiro.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.omnifaces.util.Messages;

import br.com.financeiro.dao.PessoaDao;
import br.com.financeiro.dao.UsuarioDao;
import br.com.financeiro.domain.Pessoa;
import br.com.financeiro.domain.Usuario;

@ManagedBean(name = "usuarioBean")
@ViewScoped
public class UsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private Pessoa pessoa;
	private PessoaDao pessoaDao = new PessoaDao();
	private UsuarioDao usuarioDao = new UsuarioDao();
	private List<Pessoa> pessoas;
	private Usuario usuario;
	private List<Usuario> usuarios;

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	@PostConstruct
	public void listar() {
		try {
			usuarios = usuarioDao.listar();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os usuários!");
			erro.printStackTrace();
		}
	}

	public void novo() {
		try {
			usuario = new Usuario();

			pessoas = pessoaDao.listar("nome");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar criar um novo usuário!");
			erro.printStackTrace();
		}
	}

	public void salvar() {
		try {
			
			SimpleHash hash = new SimpleHash("md5", usuario.getSenha());
			usuario.setSenha(hash.toHex());
			
			usuarioDao.merge(usuario);

			novo();

			usuarios = usuarioDao.listar();

			Messages.addGlobalInfo("Usuário salvo com sucesso");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar um novo usuário!");
			erro.printStackTrace();
		}
	}
	
	public void editar(ActionEvent evento) {
		try {
			usuario = (Usuario) evento.getComponent().getAttributes().get("usuarioSelecionado");

			pessoas = pessoaDao.listar("id");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar selecionar um usuário!");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {
		try {
			usuario = (Usuario) evento.getComponent().getAttributes().get("usuarioSelecionado");

			usuarioDao.excluir(usuario);

			usuarios = usuarioDao.listar();

			Messages.addGlobalInfo("Usuário removido com sucesso!");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar remover o usuário!");
			erro.printStackTrace();
		}
	}

}
