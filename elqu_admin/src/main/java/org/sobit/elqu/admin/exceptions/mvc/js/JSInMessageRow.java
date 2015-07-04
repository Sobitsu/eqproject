package org.sobit.elqu.admin.exceptions.mvc.js;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 *
 * @author Ctac
 */
@Deprecated
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class JSInMessageRow
{
	public Long id;
	public String edNum;
	public String inFile;
	public String datetimeReestr;
	public String ps;
	public String status;
	public String countRecords;
	public String sumDebet;
	public String sumCredit;

	@Override
	public String toString() {
		return "JSInMessageRow{" + "id=" + id + ", edNum=" + edNum + ", inFile=" + inFile + ", datetimeReestr=" + datetimeReestr + ", ps=" + ps + ", status=" + status + ", countRecords=" + countRecords + ", sumDebet=" + sumDebet + ", sumCredit=" + sumCredit + '}';
	}
}
