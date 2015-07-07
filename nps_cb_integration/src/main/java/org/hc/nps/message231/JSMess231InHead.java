package org.hc.nps.message231;
/**
 * @author dale
 * */

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.hc.nps.utils.ShortDateFormatter;

@XmlType(	propOrder = { 	
		"xmlns",
		"edno", 
		"eddate", 
		"edauthor",
		"registeritemsquantity",
		"endprocessingdate",
		"beginprocessingdate",
		"ClearingSystemCode",
		"RegisterMode",
		"CrdTransfRegisterInfo",
		"InitialED"
	}
)
@XmlRootElement(name="ED231")
public class JSMess231InHead {
	@XmlAttribute(name="xmlns")
	public String xmlns;
	@XmlAttribute(name="EDNo")
	public Long edno;
	@XmlAttribute(name="EDDate")
	@XmlJavaTypeAdapter(ShortDateFormatter.class)
	public Date eddate;
	@XmlAttribute(name="EDAuthor")
	public String edauthor; 
	@XmlAttribute(name="RegisterItemsQuantity")
	public Long registeritemsquantity;
	@XmlJavaTypeAdapter(ShortDateFormatter.class)
	@XmlAttribute(name="EndProcessingDate")
	public Date endprocessingdate;
	@XmlJavaTypeAdapter(ShortDateFormatter.class)
	@XmlAttribute(name="BeginProcessingDate")
	public Date beginprocessingdate;
	@XmlAttribute(name="ClearingSystemCode")
	public String ClearingSystemCode;
	@XmlAttribute(name="RegisterMode")
	public Long RegisterMode;
	public List<JSMess231InInfo> CrdTransfRegisterInfo;
	public JSMess231InitialED InitialED;
	
	@Override
	public String toString() 
	{
		return "JSMess230InHead{" 
				+ "EDNo=" + edno 
				+ ", eddate=" + eddate 
				+ ", edauthor=" + edauthor 
				+ ", registeritemsquantity=" + registeritemsquantity 
				+ ", endprocessingdate=" + endprocessingdate  
				+ ", beginprocessingdate=" + beginprocessingdate  
				+ ", ClearingSystemCode=" + ClearingSystemCode  
				+ ", RegisterMode=" + RegisterMode  
				+ InitialED.toString()+"}";
	}
}
