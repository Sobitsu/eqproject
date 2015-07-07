package org.hc.nps.calendar;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = { "year", "method", "datesInfo"})
@XmlRootElement(name="Calendar")
public class JSCalendarImport {
	@XmlAttribute(name="Method")
	public String method;
	@XmlAttribute(name="Year")
	public Long year;
	
	public List<JSCalendarImportDetail> datesInfo;
}
