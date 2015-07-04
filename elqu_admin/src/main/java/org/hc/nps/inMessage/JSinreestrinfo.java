package org.hc.nps.inMessage;

import java.math.BigInteger;
import java.util.Date;

import org.hc.jp.utils.DateTimeUtils;

/**
*
* @author dale
*/



public class JSinreestrinfo {
	public BigInteger CollId;
	public String Bic;
	public Long Sum;
	public String DC;
	public String Msgblock;
	public String ClearingSystemCode;
	public String SessionID;
	public Date SessionDate;
	public String UniId;
	public String fileName;
	public String Status;
	@Override
	public String toString() 
	{
		return "JSinreestrinfo{" + "CollId=" 
								+ CollId + ", Bic=" 
								+ Bic + ", Sum=" 
								+ Sum + ", DC=" 
								+ DC + ", Msgblock=" 
								+ Msgblock + ", ClearingSystemCode=" 
								+ ClearingSystemCode + ", SessionID=" 
								+ SessionID + ", UniId=" 
								+ UniId + ", SessionDate=" 
								+  DateTimeUtils.formatDate(SessionDate)+ '}';
	}
}