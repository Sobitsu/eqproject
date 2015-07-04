package org.sobit.elqu.admin.exceptions.mvc.js;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hc.jp.js.JSOrderBy;

/**
 *
 * @author Ctac
 */
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class JSReportTable 
{
	public String type;
	public Long id;		//идентификатор
	public String nspkFileName;
	public String sendFileName;
	public String reject201FileName;
	public String reject231FileName;
	public String status;		//Статус
	
	public Integer itemsOnPage;
	public Long totalItems;
	public Integer currentPage;
	
	public JSOrderBy orderBy;
	
	public JSReportRow[] items;
	
	
	public String toString(){
		return "type  = "+ type +"itemcount" + items.length;
	}
}
