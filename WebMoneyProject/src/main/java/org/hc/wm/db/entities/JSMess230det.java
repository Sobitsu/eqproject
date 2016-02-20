package org.hc.wm.db.entities;

public class JSMess230det {
		public Long	CollId;
		public Long	RegisterItemID;
		public String	Bic;
		public Long	Sum;
		public String DC;
		public	String	Status;
		
		@Override
		public String toString() 
		{
			return "JSINMess{" 
					+ "CollId=" + CollId 
					+ ", RegisterItemID=" + RegisterItemID 
					+ ", Bic=" + Bic 
					+ ", Sum=" + Sum 
					+ ", Status=" + Status  
					+ '}';
		}

};