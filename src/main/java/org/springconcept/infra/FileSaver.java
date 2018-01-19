package org.springconcept.infra;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springconcept.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

//Anotação padrão que informa ao Spring que essa classe será gerenciada por ele e poderá ser injetada através do seu IoC em outros Components
@Component
public class FileSaver {

	@Autowired
	private HttpServletRequest request;

	public String write(String baseFolder, MultipartFile file) {
		try {
			String realPath = request.getServletContext().getRealPath("/" + baseFolder);

			FileUtils.getInstance().validaECriaDiretorio(realPath);
			
			String path = realPath + "/" + file.getOriginalFilename();
			file.transferTo(new File(path));

			return baseFolder + "/" + file.getOriginalFilename();
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
