package org.springconcept.utils;

import java.io.File;

public class FileUtils {

	//Implementação do padrão de projeto Singleton
	private static FileUtils instance;

	public static synchronized FileUtils getInstance() {
		if (instance == null) {
			instance = new FileUtils();
		}

		return instance;
	}

	public void validaECriaDiretorio(String path) {
		if (path.contains("/")) {
			path = path.replace("/", "\\");
		}

		if (!new File(path).exists()) {
			(new File(path)).mkdirs();
		}
	}

}
