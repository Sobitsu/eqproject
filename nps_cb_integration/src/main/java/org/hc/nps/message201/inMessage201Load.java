package org.hc.nps.message201;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.hc.jp.exceptions.ExceptionBuilder;
import org.hc.jp.files.FileManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.xml.sax.SAXException;


@Repository
public class inMessage201Load {
	@Autowired
	private FileManager fileManager;
	
	@Autowired
	private ExceptionBuilder exceptionBuilder;
		
	@Autowired
	private inMessage201DB inMDB;
	
	public void loadFileXML(File filexml,inMessage201DB inMDB, JSInMessSession201 inMess, inMessage201Check inMCh) throws ParserConfigurationException, SAXException, IOException
	{
		//JSInMessSession201 inMess = new JSInMessSession201();
		if(filexml != null){
			inMessage201Pars pars = new inMessage201Pars(inMDB,inMess,inMCh);
			inMess.filename = filexml.getName();
			SAXParserFactory factory = SAXParserFactory.newInstance(); 
			SAXParser parser = factory.newSAXParser();
			try{
				parser.parse(filexml, pars);
				}
			catch (Exception exception) {
					throw exception;
				}
		}
		
		
	/*	File FileXML = getFile(fileName); 
		if(FileXML == null)return;
		
		SAXParserFactory factory = SAXParserFactory.newInstance(); 
		SAXParser parser = factory.newSAXParser();
		try {
			parser.parse(FileXML, pars);}
		catch (Exception exception) {
			throw exception;
		}*/
	}
}
