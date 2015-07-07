package org.hc.nps.inMessage;


import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import org.hc.jp.utils.DateTimeUtils;

/**
*
* @author dale
*/

public class JSinreestrhist {
	public Date BeginProcessingDate;
	public Date EndProcessingDate;
	public String ClearingSystemCode;
	public String Status;
	public Long ItemCount;
	public Long SumDt;
	public Long SumKt;

	public List<JSinreestrinfo> reestrinfo = new LinkedList<JSinreestrinfo>();
	
	@Override
	public String toString() 
	{
		return "JSinreestrhist{" 
	+ "BeginProcessingDate=" + DateTimeUtils.formatDate(BeginProcessingDate) 
	+ ", EndProcessingDate=" + DateTimeUtils.formatDate(EndProcessingDate) 
	+ ", ClearingSystemCode=" + ClearingSystemCode 
	+ ", Status=" + Status 
	+  '}';
	}
}


