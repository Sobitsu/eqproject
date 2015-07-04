package org.hc.nps.inMessage;


import org.xml.sax.helpers.DefaultHandler; 
import org.xml.sax.*; 

public class inMessagePars extends DefaultHandler {
	private inMessageCheck inMCh;
	private JSINMess doc;
	private inMessageDB inMDB;
	private JSInMessSession inMess;
	public inMessagePars(inMessageDB inMDB2, JSInMessSession inMess2, inMessageCheck inMCh2) {
			inMDB = inMDB2;
			inMess = inMess2;
			inMCh = inMCh2;
		}

	@Override 
	public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
		String name_ell = "";
		if (qName.equals("record")){ 
			doc = new JSINMess();
			}

		try
		{
			if (qName.equals("field")){
				name_ell =  atts.getValue("name");
				}
			if (name_ell.equals("SCHEME")){
				doc.SCHEME = atts.getValue("value");
				}	
			if (name_ell.equals("NET_AMOUNT")){
				doc.NET_AMOUNT = atts.getValue("value").replace(".", "");
				}
			if (name_ell.equals("TRANSFER_BIC")){
				doc.TRANSFER_BIC = atts.getValue("value");
				}
			if (name_ell.equals("DC")){
				String tmp = atts.getValue("value");
				if (tmp.contentEquals("D")) doc.DC = "1";
				if (tmp.contentEquals("C")) doc.DC = "2";
				}
			if (name_ell.equals("ID")){
				doc.ID = atts.getValue("value");
				}
			}
		catch (Exception exception) {
			throw new SAXException(exception);
		}
	}

	 
	@Override 
	public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
		if (qName.equals("record")){
			try {
					inMCh.Check_mess(doc);
					doc.Status = "CREATE";
					doc.filename = inMess.filename;
					inMDB.addinMess(doc,inMess);
				} catch (Exception exception) {
					throw new SAXException(exception);
				}
			}
		}
	@Override 
	public void characters(char[] ch, int start, int length) throws SAXException {
	}
}
