package br.com.financeiro.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.omnifaces.util.Messages;

import br.com.financeiro.dao.CirurgiaDao;
import br.com.financeiro.dao.EnderecoDao;
import br.com.financeiro.dao.MedicoDao;
import br.com.financeiro.dao.RiscoCirurgicoDao;
import br.com.financeiro.dao.UsuarioDao;
import br.com.financeiro.domain.Cirurgia;
import br.com.financeiro.domain.Endereco;
import br.com.financeiro.domain.Medico;
import br.com.financeiro.domain.RiscoCirurgico;

@ManagedBean(name = "avaRiscCirurgicoBean")
@ViewScoped
public class AvaRiscCirurgicoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private RiscoCirurgico riscoCirurgico;
	private RiscoCirurgicoDao riscoCirurgicoDao = new RiscoCirurgicoDao();
	private List<RiscoCirurgico> riscosCirurgicos;

	private EnderecoDao enderecoDao = new EnderecoDao();
	private List<Endereco> enderecos;

	private CirurgiaDao cirurgiaDao = new CirurgiaDao();
	private List<Cirurgia> cirurgias;

	private MedicoDao medicoDao = new MedicoDao();
	private List<Medico> medicos;

	private UsuarioDao usuarioDao = new UsuarioDao();

	private boolean exibir;

	public RiscoCirurgico getRiscoCirurgico() {
		return riscoCirurgico;
	}

	public void setRiscoCirurgico(RiscoCirurgico riscoCirurgico) {
		this.riscoCirurgico = riscoCirurgico;
	}

	public boolean isExibir() {
		return exibir;
	}

	public void setExibir(boolean exibir) {
		this.exibir = exibir;
	}

	public List<RiscoCirurgico> getRiscosCirurgicos() {
		return riscosCirurgicos;
	}

	public List<Endereco> getEnderecos() {
		return enderecos;
	}

	public List<Cirurgia> getCirurgias() {
		return cirurgias;
	}

	public List<Medico> getMedicos() {
		return medicos;
	}
	
	@PostConstruct
	public void novo() {
		riscoCirurgico = new RiscoCirurgico();
		exibir = false;
	}
	
	public RiscoCirurgico buscar() {
		try {
			RiscoCirurgico rc = riscoCirurgicoDao.buscar(riscoCirurgico.getId());
			
			if (rc == null) {
				novo();
				exibir = false;
				Messages.addFlashGlobalWarn("Nenhum risco cirúrgico encontrado com o código informado!");
			} else {
				exibir = true;
				riscoCirurgico = rc;
			}
			
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar buscar um risco cirúrgico!");
			erro.printStackTrace();
		}
		return riscoCirurgico;
	}
	
	public void novaBusca() {
		this.novo();
	}

}
