package com.softserveinc.todosoap.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;

public class ObjectToXMLConverter {

	private ObjectToXMLConverter() {
	}

	public static String objectToXMLConvert(Object object) throws JAXBException {

		StringWriter stringWriter = new StringWriter();

		JAXBContext context = JAXBContext.newInstance(object.getClass());
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.marshal(object, stringWriter);

		return stringWriter.toString();
	}
}
