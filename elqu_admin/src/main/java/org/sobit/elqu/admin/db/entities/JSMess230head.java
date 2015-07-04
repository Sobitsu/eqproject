package org.sobit.elqu.admin.db.entities;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class JSMess230head {
	public Long id;
	public String EDNo;
	public Date EDDate;
	public String EDAuthor;
	public Date BeginProcessingDate;
	public Date EndProcessingDate;
	public String ClearingSystemCode;
	public Long RegisterItemsQuantity;
	public Long RegisterCreditSum;
	public Long RegisterDebetSum;
	public String Status;
	public List<JSMess230det> RegisterItemsInfo = new LinkedList<JSMess230det>();

	@Override
	public String toString() 
	{
		return "JSINMess{" 
				+ "EDNo=" + EDNo 
				+ ", EDAuthor=" + EDAuthor 
				+ ", ClearingSystemCode=" + ClearingSystemCode 
				+ ", RegisterItemsQuantity=" + RegisterItemsQuantity 
				+ ", RegisterCreditSum=" + RegisterCreditSum
				+ ", RegisterDebetSum=" + RegisterDebetSum
				+ ", Status=" + Status
				+ '}';
	}
	
}
