package com.softserveinc.todosoap.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;

public class XMLFileSaver {

	private static final Logger log = LoggerFactory.getLogger(XMLFileSaver.class);

	private XMLFileSaver() {
	}

	public static File stringToXMLFile(String xmlSource) {

		File xmlFile = new File(LocalTime.now().toString() + ".xml");
		try (FileWriter fileWriter = new FileWriter(xmlFile)) {
			fileWriter.write(xmlSource);
		} catch (IOException e) {
			log.error("Problems with saving fife - {}", e.getMessage());
		}
		return xmlFile;
	}
}
