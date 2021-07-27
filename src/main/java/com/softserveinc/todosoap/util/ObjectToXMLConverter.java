package com.softserveinc.todosoap.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;

public class ObjectToXMLConverter {

	private static final Logger log = LoggerFactory.getLogger(ObjectToXMLConverter.class);

	private ObjectToXMLConverter() {
	}

	public static String objectToXMLConvert(Object object) {

		StringWriter stringWriter = new StringWriter();
		try {
			JAXBContext context = JAXBContext.newInstance(object.getClass());
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(object, stringWriter);
		} catch (JAXBException e) {
			log.error("Problems with marshaling - {}", e.getMessage());
		}

		return stringWriter.toString();
	}
}
