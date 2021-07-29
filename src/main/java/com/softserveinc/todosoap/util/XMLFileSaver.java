package com.softserveinc.todosoap.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class XMLFileSaver {

	private XMLFileSaver() {
	}

	public static File stringToXMLFile(String xmlSource) throws IOException {

		String fileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
		File xmlFile = new File("tempfiles/" + fileName + ".xml");
		try (FileWriter fileWriter = new FileWriter(xmlFile)) {
			fileWriter.write(xmlSource);
		}

		return xmlFile;
	}
}
