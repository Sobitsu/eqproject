package org.hc.nps.message231;
/**
 * @author dale
 * */

import java.util.Date;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = { "edno", "eddate", "edauthor"}, name = "EDRefID")
public class JSMess231InInfoDet {
	@XmlAttribute(name="EDNo")
	public String edno;
	@XmlAttribute(name="EDDate")
	public Date eddate;
	@XmlAttribute(name="EDAuthor")
	public String edauthor;
	@Override
	public String toString() 
	{
		return "JSMess230InInfoDet{" 
				+ "EDNo" + edno 
				+ ", EDDate=" + eddate 
				+ ", EDAuthor=" + edauthor 
				+ '}';
	}

}
