package org.hc.nps.message201;

import org.springframework.stereotype.Repository;

/*
 * проверки для входящего  файла 201 */
@Repository
public class inMessage201Check {
	@SuppressWarnings("unused")
	public String Check_dat_length_nolit(String ch_data,int leng){
		String log = "";
		 try {
		        Long l1 = new Long(ch_data);
		        if (ch_data.length()> leng){log = log + "Слишком большое число "+ch_data;} 
		   } catch (NumberFormatException e) {
		        log = log + "Не верный формат числа "+ch_data;
		    }
		 return log;
	}
	///если is_null = 0 , поле не может быть пустым
	public String Check_dat_length(String ch_data,int leng,String is_null){
		String log = "";
		if (ch_data != null){ 
		if (ch_data.length()> leng){log = log + "Слишком большое число "+ch_data;}}
		else if (is_null.contains("0") == true){log = log + "Поле не может быть пустым ";}
		return log;
	}
	@SuppressWarnings("unused")
	public String Check_time(String ch_data,String is_null)
	{
		String log = "";
		String tmp_str = "";
		if (ch_data != null){
			if (ch_data.length() == 8)
				if (ch_data.indexOf(":") == 2)
					if (ch_data.lastIndexOf(":")== 5 ){
						tmp_str = ch_data.replace(":", "");
						//System.out.println("Check_time "+tmp_str);
						try	{
							long ln = new Long(tmp_str);
						}
						catch (NumberFormatException e) {
					        log = log + "Не верный формат времени "+ch_data;
					    }
					}
					else log = log + "Не верный формат времени "+ch_data;
				else log = log + "Не верный формат времени "+ch_data;
			else log = log + "Не верный формат времени "+ch_data;
		}
		else if (is_null.contains("0") == true){log = log + "Поле не может быть пустым ";}
	return log;	
	}
	
	@SuppressWarnings("unused")
	public String Check_date(String ch_data,String is_null)
	{
		String log = "";
		String tmp_str = "";
		if (ch_data != null){
			if (ch_data.length() == 10)
				if (ch_data.indexOf("-") == 4)
					if (ch_data.lastIndexOf("-")== 7 ){
						tmp_str = ch_data.replace("-", "");
						//System.out.println("Check_time "+tmp_str);
						try	{
							long ln = new Long(tmp_str);
						}
						catch (NumberFormatException e) {
					        log = log + "Не верный формат даты "+ch_data;
					    }
					}
					else log = log + "Не верный формат даты "+ch_data;
				else log = log + "Не верный формат даты "+ch_data;
			else log = log + "Не верный формат даты "+ch_data;
		}
		else if (is_null.contains("0") == true){log = log + "Поле не может быть пустым ";}
	return log;	
	}
	
	public String Check_mess(JSMESS201 mess){
		String log = "";
		inMessage201Check ch = new inMessage201Check();
		log = log + ch.Check_dat_length_nolit(mess.EDNo, 9);
		log = log + ch.Check_date(mess.EDDate, "0");
		log = log + ch.Check_dat_length_nolit(mess.EDAuthor, 10);
		log = log + ch.Check_dat_length_nolit(mess.EDReceiver, 10);
		log = log + ch.Check_dat_length_nolit(mess.CtrlCode, 4);
		log = log + ch.Check_time(mess.CtrlTime, "0");
		log = log + ch.Check_dat_length(mess.Annotation, 150,"0");
		log = log + ch.Check_dat_length(mess.MsgID, 150,"1");
		log = log + ch.Check_dat_length_nolit(mess.EDNo1, 9);
		log = log + ch.Check_date(mess.EDDate1, "1");//EDDate1
		log = log + ch.Check_dat_length_nolit(mess.EDAuthor1, 10);
			
		return log;
	}
}
