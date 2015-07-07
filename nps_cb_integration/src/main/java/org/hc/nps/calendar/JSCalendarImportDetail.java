package org.hc.nps.calendar;

import java.util.Date;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.hc.nps.utils.ShortDateFormatter;

@XmlType(propOrder = { "date", "isWork"}, name = "datesinfo")
public class JSCalendarImportDetail {
	@XmlJavaTypeAdapter(ShortDateFormatter.class)
	@XmlAttribute(name="Date")
	public Date date;
	@XmlAttribute(name="isWork")
	public String isWork;

}
