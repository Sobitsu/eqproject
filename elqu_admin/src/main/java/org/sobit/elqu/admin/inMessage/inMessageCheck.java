package org.sobit.elqu.admin.inMessage;

import org.sobit.elqu.admin.biks.BiksUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/*
 * проверки для входящего первоначального файла */

@Repository
public class inMessageCheck {
	public String  all_bic = "~";
	
	@Autowired
	private BiksUtil BU;
	
		@SuppressWarnings("unused")
		public  void Check_mess(JSINMess mess) throws INMesException{
		String log = "";
		
		
		if (mess.TRANSFER_BIC.length() != 9) {
			log = log + "не корректна длина БИК " + mess.TRANSFER_BIC;
		} 
		
		 try {
		        Long l1 = new Long(mess.NET_AMOUNT);
		   } catch (NumberFormatException e) {
		        log = log + "Не верный формат числа "+mess.NET_AMOUNT;
		    }  
		 if (BU.isexistbik(mess.TRANSFER_BIC) != true) {
			 log = log + "не найден БИК в справочнике " + mess.TRANSFER_BIC;
		 }
		 if (all_bic.indexOf(mess.TRANSFER_BIC, 0) > 0) {
			 log = log + " дублирование БИК в файле ";
		 }
		 else {all_bic = all_bic + mess.TRANSFER_BIC + "~";
		 }
		if (log.length()!= 0) throw new INMesException(log);
	}
}
