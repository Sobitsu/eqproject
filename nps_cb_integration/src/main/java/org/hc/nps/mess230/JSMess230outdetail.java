package org.hc.nps.mess230;

/**
 * @author dale
 *
 */
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = { "DC", "BIC", "Sum","RegisterItemID"}, name = "RegisterItemsInfo")

public class JSMess230outdetail {
	@XmlAttribute(name="RegisterItemID")
	public Long RegisterItemID;
	@XmlAttribute(name="BIC")
	public String	BIC;
	@XmlAttribute(name="Sum")
	public Long	Sum;
	@XmlAttribute(name="DC")
	public String DC;
}
