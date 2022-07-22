package br.com.financeiro.bean;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@RequestScoped
@ManagedBean(name = "imagemBean")
public class ImagemBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManagedProperty("#{param.caminho}")
	private String caminho;
	private StreamedContent foto;

	public String getCaminho() {
		return caminho;
	}

	public void setCaminho(String caminho) {
		this.caminho = caminho;
	}

	public StreamedContent getFoto() {

		try {
			if (caminho == null || caminho.isEmpty()) {
				Path path = Paths.get("C:/ws-udemy/uploads/branco.png");
				// capturando os bites
				InputStream stream;
				stream = Files.newInputStream(path);

				foto = new DefaultStreamedContent(stream);
			} else {
				Path path = Paths.get(caminho);
				// capturando os bites
				InputStream stream = Files.newInputStream(path);
				foto = new DefaultStreamedContent(stream);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return foto;
	}

	public void setFoto(StreamedContent foto) {
		this.foto = foto;
	}

}
