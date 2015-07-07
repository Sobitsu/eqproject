package org.hc.nps.message231;

import java.io.FileInputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class Mess231Parser {
	public JSMess231InHead parse(FileInputStream fis) throws Exception {
	        JAXBContext context = JAXBContext.newInstance(JSMess231InHead.class);
	        Marshaller marshaller =  context.createMarshaller();
	        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,Boolean.TRUE);
	        Unmarshaller unmarshaller = context.createUnmarshaller();
	        JSMess231InHead groupCopy = (JSMess231InHead) unmarshaller.unmarshal(fis);
	        return groupCopy;
	}
}
