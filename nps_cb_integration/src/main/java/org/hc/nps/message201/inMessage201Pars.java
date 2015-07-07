package org.hc.nps.message201;


import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class inMessage201Pars extends DefaultHandler {
	
	private JSMESS201 doc = null; 
	private String thisElement = ""; 
	private JSInMessSession201 inMess;
	private inMessage201DB inMDB;
	private inMessage201Check inMCh;
	
	public inMessage201Pars(inMessage201DB inMDB2, JSInMessSession201 inMess2, inMessage201Check inMCh2){
		inMDB = inMDB2;
		inMess = inMess2;
		inMCh = inMCh2;
		doc = new JSMESS201();
	}
	
	@Override 
	public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
	System.out.println("Start" + qName);
	thisElement = qName;
	if (qName.equals("ED201")){ 
		doc =  new JSMESS201();
		doc.EDNo = atts.getValue("EDNo");
		doc.EDDate = atts.getValue("EDDate");
		doc.EDAuthor = atts.getValue("EDAuthor");
		doc.EDReceiver = atts.getValue("EDReceiver");
		doc.CtrlCode = atts.getValue("CtrlCode");
		doc.CtrlTime = atts.getValue("CtrlTime");
		}
	if (qName.equals("EDRefID")){ 
		doc.EDNo1 = atts.getValue("EDNo");
		doc.EDDate1 = atts.getValue("EDDate");
		doc.EDAuthor1 = atts.getValue("EDAuthor");
		}
	} 
	 
	@Override 
	public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
		String log = "";
		thisElement = ""; 
		System.out.println("END" + qName);
		if (qName.equals("ED201")) {
		
	//	inMessage201Check check = new inMessage201Check();
		log = inMCh.Check_mess(doc);
		if (log.length() == 0 ){
		try {
			inMDB.addinMess(doc, inMess);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	/*	try {
			inMDB.saveDB(inMess);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		}
		}
	} 
	 
	@Override 
	public void characters(char[] ch, int start, int length) throws SAXException {
		System.out.println("characters " + thisElement);
		if (thisElement.equals("Annotation")){
			System.out.println("characters " + new String(ch,start,length));
			doc.Annotation = new String(ch,start,length);
		}
		if (thisElement.equals("ErrorDiagnostic")){
			//System.out.println("doc.ErrorDiagnostic "+new String(ch,start,length));
			if (doc.ErrorDiagnostic == null ){
				doc.ErrorDiagnostic =  new String(ch,start,length);
			}else{
			doc.ErrorDiagnostic = doc.ErrorDiagnostic + new String(ch,start,length);
			}
		}
		
		if (thisElement.equals("MsgID")){
			doc.MsgID = new String(ch,start,length);
		}
	}
}