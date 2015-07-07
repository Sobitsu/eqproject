package org.hc.nps.message201;

/**
*
* @author kat
*/

public class JSMESS201 {
		public String EDNo;
		public String EDDate;
		public String EDAuthor;
		public String EDReceiver;
		public String CtrlCode;
		public String CtrlTime;
		public String Annotation;
		public String ErrorDiagnostic;
		public String MsgID;
		public String EDNo1;
		public String EDDate1;
		public String EDAuthor1;
		public String FileName;
		
		@Override
		public String toString() 
		{
			return "JSMESS201{" 
					+ "EDNo=" + EDNo 
					+ ", EDDate=" + EDDate 
					+ ", EDAuthor=" + EDAuthor 
					+ ", EDReceiver=" + EDReceiver 
					+ ", CtrlCode=" + CtrlCode  
					+ ", CtrlTime=" + CtrlTime
					+ ", Annotation=" + Annotation
					+ ", ErrorDiagnostic=" + ErrorDiagnostic
					+ ", MsgID=" + MsgID
					+ ", EDNo1=" + EDNo1
					+ ", EDDate1=" + EDDate1
					+ ", EDAuthor1=" + EDAuthor1
					+ ", FileName=" + FileName
					+ '}';
		}
}
