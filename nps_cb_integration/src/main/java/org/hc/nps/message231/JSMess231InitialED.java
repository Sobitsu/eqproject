package org.hc.nps.message231;

import java.util.Date;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = { "edno", "eddate", "edauthor"}, name = "InitialED")

public class JSMess231InitialED {
	@XmlAttribute(name="EDNo")
	public String edno;
	@XmlAttribute(name="EDDate")
	public Date eddate;
	@XmlAttribute(name="EDAuthor")
	public String edauthor;
	@Override
	public String toString() 
	{
		return "JSMess231InitialED{" 
				+ "EDNo" + edno 
				+ ", EDDate=" + eddate 
				+ ", EDAuthor=" + edauthor 
				+ '}';
	}
}
