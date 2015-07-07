

/**
 * @author dale
 *
 */
package org.hc.nps.mess230;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.hc.nps.utils.ShortDateFormatter;
 
@XmlType(	propOrder = { 	"EDNo", 
							"EDDate", "EDAuthor",
							"RegisterItemsQuantity","RegisterCreditSum","EndProcessingDate",
							"RegisterDebetSum", "BeginProcessingDate",
							"RegisterItemsInfo","ClearingSystemCode"
						}
		)
@XmlRootElement(name="ED230")
public class JSMess230out {
	public List<JSMess230outdetail> RegisterItemsInfo = new LinkedList<JSMess230outdetail>();
	@XmlAttribute(name="EDNo")
	public String EDNo;
	@XmlAttribute(name="EDDate")
	@XmlJavaTypeAdapter(ShortDateFormatter.class)
	public Date EDDate;
	@XmlAttribute(name="EDAuthor")
	public String EDAuthor;
	@XmlAttribute(name="ClearingSystemCode")
	public String ClearingSystemCode;
	@XmlAttribute(name="RegisterItemsQuantity")
	public Long RegisterItemsQuantity;
	@XmlAttribute(name="RegisterCreditSum")
	public Long RegisterCreditSum;
	@XmlAttribute(name="RegisterDebetSum")
	public Long RegisterDebetSum;
	@XmlJavaTypeAdapter(ShortDateFormatter.class)
	@XmlAttribute(name="BeginProcessingDate")
	public Date BeginProcessingDate;
	@XmlAttribute(name="EndProcessingDate")
	@XmlJavaTypeAdapter(ShortDateFormatter.class)
	public Date EndProcessingDate;
}
