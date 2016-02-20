package org.hc.wm.exceptions.mvc.js;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 *
 * @author Ctac
 */
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class JSReportRow 
{
	public Long id;		//идентификатор
	public String edNo;
	public String fileName;			//Файл
	public String curRegistryDate;	//Дата текущего реестра
	public String psCode;			//Код ПС
	public String status;		//Статус
	public Integer recordCount;	//Количество записей
	public String debitSum;		//Сумма дебета
	public String creditSum;	//сумма кредита
	public String esCreateDate; // дата
	public String esControlCode; //код результата проверки для 201
	public String description;
	public String messageId;
	public String esParentId;
	public String esControlTime;
	public String registryRecordCount;
	public String registryBeginDate;
	public String registryEndDate;
	public String rejectTextPath;
	public String rejectFileName;
	public String bik;
	public String sum;
	public String nspkFileName;
	public String sendFileName;
	public String reject201FileName;
	public String reject231FileName;
	public String date;
	public String systemCode;
	public String registryType;
	public String docPath;
	public Boolean isDebet;
	public String idEd230;

}
