package org.hc.nps.inMessage;


/**
*
* @author dale
*/

public class JSINMess {
	public String SCHEME;
	public String NET_AMOUNT;
	public String TRANSFER_BIC;
	public String DC;
	public String ID;
	public String Msgblock;
	public String filename;
	public String Status;

	
	@Override
	public String toString() 
	{
		return "JSINMess{" 
				+ "SCHEME=" + SCHEME 
				+ ", NET_AMOUNT=" + NET_AMOUNT 
				+ ", TRANSFER_BIC=" + TRANSFER_BIC 
				+ ", DC=" + DC 
				+ ", ID=" + ID  
				+ ", Msgblock=" + Msgblock  
				+ '}';
	}
}