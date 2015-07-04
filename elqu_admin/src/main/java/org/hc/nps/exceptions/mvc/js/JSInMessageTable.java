package org.hc.nps.exceptions.mvc.js;

import java.util.Arrays;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hc.jp.js.JSOrderBy;

/**
 *
 * @author Ctac
 */
@Deprecated
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class JSInMessageTable
{
	public Integer itemsOnPage;
	public Long totalItems;
	public Integer currentPage;
	
	public JSOrderBy orderBy;
	
	public JSInMessageRow[] items;

	@Override
	public String toString() {
		return "JSInMessageTable{" + "itemsOnPage=" + itemsOnPage + ", totalItems=" + totalItems + ", currentPage=" + currentPage + ", orderBy=" + orderBy + ", items=" + Arrays.toString(items) + '}';
	}
}
