package org.hc.nps.message201;

import java.util.Date;
import java.math.BigInteger;

import org.hc.jp.utils.DateTimeUtils;

public class JSinMessage201 {
	public BigInteger CollId;
	public Long EDNo;
	public Date EDDate ;
	public String EDAuthor;
	public String EDReceiver;
	public String CtrlTime;
	public String CtrlCode;
	public String  Annotation;
	public String ErrorDiagnostic;
	public String MsgID;
	public Long EDRefID_EDNo;
	public Date EDRefID_EDDate;
	public String EDRefID_EDAuthor; 
	
	@Override
	public String toString() 
	{
		return "JSinMessage201{" + "CollId="+ CollId 
								+ ", EDNo = "+EDNo
								+ ", EDDate="+ EDDate 
								+ ", EDAuthor="+ EDAuthor 
								+ ", EDReceiver="+ EDReceiver 
								+ ", CtrlTime="	+ CtrlTime 
								+ ", CtrlCode="+ CtrlCode 
								+ ", Annotation="+ Annotation 
								+ ", ErrorDiagnostic="+ ErrorDiagnostic
								+ ", MsgID="+ MsgID
								+ ", EDRefID_EDNo="+ EDRefID_EDNo
								+ ", EDRefID_EDAuthor="+ EDRefID_EDAuthor
								+ ", EDRefID_EDDate="	+  DateTimeUtils.formatDate(EDRefID_EDDate)
								+ '}';
	}
}
