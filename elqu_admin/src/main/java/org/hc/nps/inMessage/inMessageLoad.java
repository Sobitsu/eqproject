package org.hc.nps.inMessage;

import java.io.FileInputStream;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.springframework.stereotype.Repository;
import org.xml.sax.SAXException;


@Repository
public class inMessageLoad 

{
	public void loadFileXML(FileInputStream fis,inMessageDB inMDB, JSInMessSession inMess,inMessageCheck inMCh, String filename) throws ParserConfigurationException, SAXException, IOException
	{		
		inMess.filename = filename;
		inMessagePars pars = new inMessagePars(inMDB,inMess,inMCh);
		if(fis != null){
			SAXParserFactory factory = SAXParserFactory.newInstance(); 
			SAXParser parser = factory.newSAXParser();
			parser.parse(fis, pars);
			}
	}
}
