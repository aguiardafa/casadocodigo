package br.com.casadocodigo.lojavirtual.infra;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileSaver {

	@Autowired
	private HttpServletRequest request;

	public String write(String baseFolder, MultipartFile file) {
		try {
			String realPath = request.getServletContext().getRealPath("/" + baseFolder);
			String pathAbsoluto = realPath + "/" + file.getOriginalFilename();
			String pathRelativo = baseFolder + "/" + file.getOriginalFilename();
			
			file.transferTo(new File(pathAbsoluto));
			
			return pathRelativo;
		} catch (IllegalStateException | IOException e) {
			throw new RuntimeException(e);
		}
	}
}
