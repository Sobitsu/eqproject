package org.hc.nps.message231;
/**
 * @author dale
 * */

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = { "dc", "bic", "sum","registeritemid","statuscode",/*"ctrlcode",*/"EDRefID"/*,"Annotation"*/}, name = "CrdTransfRegisterInfo")
public class JSMess231InInfo {
	@XmlAttribute(name="DC")
	public String dc;
	@XmlAttribute(name="BIC")
	public String bic;
	@XmlAttribute(name="Sum")
	public Long sum;
	@XmlAttribute(name="RegisterItemID")
	public Long registeritemid;
	@XmlAttribute(name="StatusCode")
	public Long statuscode;
//	@XmlAttribute(name="CtrlCode")
//00	public Long ctrlcode;
	public JSMess231InInfoDet EDRefID;
//	public String Annotation;

	@Override
	public String toString() 
	{
		return "JSMess230InInfo{" 
				+ "dc=" + dc 
				+ ", bic=" + bic 
				+ ", sum=" + sum 
				+ ", registeritemid=" + registeritemid 
				+ ", statuscode=" + statuscode  
//				+ ", ctrlcode=" + ctrlcode  
				+ ", EDRefID:" +EDRefID.toString()
//				+ ", Annotation=" + Annotation  
				+ '}';
	}
}
