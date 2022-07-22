package br.com.financeiro.report.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.stereotype.Component;

import br.com.financeiro.util.DateUtils;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.oasis.JROdsExporter;
import net.sf.jasperreports.engine.util.JRLoader;

@Component
public class ReportUtil implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static final String UNDERLINE = "_";
	private static final String FOLDER_RELATORIOS = "/reports";
	private static final String SUBREPORT_DIRETORIO = "SUBREPORT_DIRETORIO";
	private static final String EXTENSION_ODS = "ods";
	private static final String EXTENSION_XLS = "xls";
	private static final String EXTENSION_HTML = "html";
	private static final String EXTENSION_PDF = "pdf";
	private String SEPARATOR =  File.separator;
	private static final int RELATORIO_PDF = 1;
	private static final int RELATORIO_EXCEL = 2;
	private static final int RELATORIO_HTML = 3;
	private static final int RELATORIO_PLANILHA_OPEN_OFFICE = 4;
	private static final String PONTO = ".";
	private StreamedContent arquivoRetornoWeb = null;
	private String caminhoArquivoRelatorio = null;
	private JRExporter tipoArquivoExportado = null;
	private String extensaoArquivoExportado = "";
	private File arquivoGerado = null;
	private String caminhoSubreport_dir = null;
	
	/**
	 * Arquivo genérico
	 * @return
	 */
	public StreamedContent geraRelatorio(List<?> listaDataBeanCollectionReport,
			HashMap parametrosRelatorio, String nomeRelatorioJasper,
			String nomeRelatorioSaida, int tipoRelatorio) throws Exception {
		
		//cria a lista de collectionDataSource de beans carregam os dados do relatório
		JRBeanCollectionDataSource collectionDataSource = new JRBeanCollectionDataSource(listaDataBeanCollectionReport);
		
		//Fornece o caminho físico até a pasta onde contém os relatórios compilados .jasper
		FacesContext contexto = FacesContext.getCurrentInstance();
		contexto.responseComplete();
		ServletContext servletContext = (ServletContext) contexto.getExternalContext().getContext();
		
		//caminho da pasta de relatórios
		String caminhoRelatorio = servletContext.getRealPath(FOLDER_RELATORIOS);
		
		//exemplo  do código abaixo: c:aplicacao/report/rel_consulta_produto.jasper
		File file = new File(caminhoRelatorio + SEPARATOR + nomeRelatorioJasper + PONTO + "jasper");
		
		if (caminhoRelatorio == null || (caminhoRelatorio != null && caminhoRelatorio.isEmpty()
				|| !file.exists())) {
			
			caminhoRelatorio = this.getClass().getResource(FOLDER_RELATORIOS).getPath();
			SEPARATOR = "";
		}
		
		//caminho para imagens
		parametrosRelatorio.put("PARAMETROS_IMAGEM_RELATORIO", caminhoRelatorio);
		
		//caminho completo até o relatório compilado indicado
		String caminhoArquivojasper = caminhoRelatorio + SEPARATOR + nomeRelatorioJasper + PONTO + "jasper";
		
		//faz o carregamento do relatório indicado
		JasperReport relatorioJasper = (JasperReport) JRLoader.loadObjectFromFile(caminhoArquivojasper);
		
		//seta parametros subreport_diretorio como caminho físico para o sub-reports
		caminhoSubreport_dir = caminhoRelatorio + SEPARATOR;
		parametrosRelatorio.put(SUBREPORT_DIRETORIO, caminhoSubreport_dir);
		
		//carrega o arquivo compilado para a memória
		JasperPrint impressaoJasper = JasperFillManager.fillReport(relatorioJasper, parametrosRelatorio, collectionDataSource);
		
		
		switch (tipoRelatorio) {
		case RELATORIO_PDF:
			tipoArquivoExportado = new JRPdfExporter();
			extensaoArquivoExportado = EXTENSION_PDF;
			break;
		case RELATORIO_EXCEL:
			tipoArquivoExportado = new JRXlsExporter();
			extensaoArquivoExportado = EXTENSION_XLS;
			break;
		case RELATORIO_PLANILHA_OPEN_OFFICE:
			tipoArquivoExportado = new JROdsExporter();
			extensaoArquivoExportado = EXTENSION_ODS;
			break;

		default:
			tipoArquivoExportado = new JRPdfExporter();
			extensaoArquivoExportado = EXTENSION_PDF;
			break;
		}
		
		nomeRelatorioSaida += UNDERLINE + DateUtils.getDateAtualReportName();
		
		//caminho relatorio exportado
		caminhoArquivoRelatorio = caminhoRelatorio + SEPARATOR + nomeRelatorioSaida + PONTO + extensaoArquivoExportado;
		
		//cria novo file exportado
		arquivoGerado = new File(caminhoArquivoRelatorio);
		
		//preparando a impressão do relatório
		tipoArquivoExportado.setParameter(JRExporterParameter.JASPER_PRINT, impressaoJasper);
		
		//nome do arquivo a ser impresso/exportado
		tipoArquivoExportado.setParameter(JRExporterParameter.OUTPUT_FILE, arquivoGerado);
		
		//executa a exportação
		tipoArquivoExportado.exportReport();
		
		//remove o arquivo do servidor após ser feito o dwnload pelo o usuário
		arquivoGerado.deleteOnExit();
		
		//cria o inputStream para ser usado pelo primefaces
		InputStream conteudorelatorio = new FileInputStream(arquivoGerado);
		
		//faz o retorno para a aplicação
		arquivoRetornoWeb = new DefaultStreamedContent(conteudorelatorio, "application/" + extensaoArquivoExportado,
				nomeRelatorioSaida + PONTO + extensaoArquivoExportado);
		
		return arquivoRetornoWeb;
		
	}
	
	
	
}
