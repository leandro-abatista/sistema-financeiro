package br.com.financeiro.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.financeiro.dao.EnderecoDao;
import br.com.financeiro.dao.EspecialidadeDao;
import br.com.financeiro.dao.MedicoDao;
import br.com.financeiro.dao.OrgaoExpeditorDao;
import br.com.financeiro.dao.UsuarioDao;
import br.com.financeiro.domain.Endereco;
import br.com.financeiro.domain.Especialidade;
import br.com.financeiro.domain.Medico;
import br.com.financeiro.domain.OrgaoExpeditor;
import br.com.financeiro.domain.Usuario;

@ManagedBean(name = "medicoBean")
@ViewScoped
public class MedicoBean implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	private MedicoDao medicoDao = new MedicoDao();
	private Medico medico;
	private List<Medico> medicos;
	private EnderecoDao enderecoDao = new EnderecoDao();
	private List<Endereco> enderecos;
	private EspecialidadeDao especialidadeDao = new EspecialidadeDao();
	private List<Especialidade> especialidades;
	private OrgaoExpeditorDao orgaoExpeditorDao = new OrgaoExpeditorDao();
	private List<OrgaoExpeditor> orgaoExpeditores;
	private UsuarioDao usuarioDao = new UsuarioDao();
	
	@ManagedProperty(value = "#{autenticacaoBean}")
	private AutenticacaoBean autenticacaoBean;

	public Medico getMedico() {
		return medico;
	}

	public void setMedico(Medico medico) {
		this.medico = medico;
	}

	public List<Medico> getMedicos() {
		return medicos;
	}

	public List<Endereco> getEnderecos() {
		return enderecos;
	}

	public List<Especialidade> getEspecialidades() {
		return especialidades;
	}

	public List<OrgaoExpeditor> getOrgaoExpeditores() {
		return orgaoExpeditores;
	}
	
	public AutenticacaoBean getAutenticacaoBean() {
		return autenticacaoBean;
	}

	public void setAutenticacaoBean(AutenticacaoBean autenticacaoBean) {
		this.autenticacaoBean = autenticacaoBean;
	}

	public void novo() {
		try {
			medico = new Medico();
			orgaoExpeditores = orgaoExpeditorDao.listar("descricao");
			enderecos = enderecoDao.listar("logradouro");
			especialidades = especialidadeDao.listar("descricao");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Erro ao tentar gerar um novo médico!");
			erro.printStackTrace();
		}
	}
	
	public void salvar() {
		try {
			Usuario usuario = usuarioDao.buscar(autenticacaoBean.getUsuarioLogado().getId());
			medico.setUsuarioLogado(usuario);
			medico.setDataCadastro(new Date());
			medicoDao.merge(medico);
			novo();
			medicos = medicoDao.listar("nome");
			Messages.addFlashGlobalInfo("Médico salvo com sucesso!");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Erro ao tentar salvar um novo médico!");
			erro.printStackTrace();
		}
	}
	
	@PostConstruct
	public void listar() {
		try {
			medicos = medicoDao.listar("id");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Erro ao tentar listar os médicos!");
			erro.printStackTrace();
		}
	}
	
	public void editar(ActionEvent evento) {
		try {
			medico = (Medico) evento.getComponent().getAttributes().get("objetoSelecionado");
			orgaoExpeditores = orgaoExpeditorDao.listar("descricao");
			enderecos = enderecoDao.listar("logradouro");
			especialidades = especialidadeDao.listar("descricao");
			Messages.addFlashGlobalInfo("Dados atualizados com sucesso!");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Erro ao tentar atualizar os dados do médico!");
			erro.printStackTrace();
		}
	}
	
	public void excluir(ActionEvent evento) {
		try {
			medico = (Medico) evento.getComponent().getAttributes().get("objetoSelecionado");
			medicoDao.excluir(medico);
			medicos = medicoDao.listar("id");
			Messages.addFlashGlobalInfo("Médico excluído com sucesso!");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Erro ao tentar excluir o médico!");
			erro.printStackTrace();
		}
	}

}
