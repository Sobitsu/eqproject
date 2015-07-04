	package org.hc.nps.reports;

import org.apache.commons.lang.StringUtils;
import org.hc.jp.db.entities.User;
import org.hc.nps.exceptions.mvc.js.JSReportTable;
import org.hc.nps.inMessage.InDocTableGeter;
import org.hc.nps.inMessage.InMessageTableGeter;
//import org.hc.nps.mess230.Mess230TableGeter;
//import org.hc.nps.message201.InMessageTableGeter201;
//import org.hc.nps.message231.InMessage231TableGeter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Ctac
 */
@Service
public class ReportsTableGetter
{
	@Autowired
	private InMessageTableGeter inMessageTableGeter;
//	@Autowired
//	private InMessageTableGeter201 inMessageTableGeter201;
	@Autowired
	private InDocTableGeter inMessageTableGeterDoc;
	
//	@Autowired
//	private InMessage231TableGeter inMessage231TableGeterDoc;
	
//	@Autowired
//	private Mess230TableGeter inMessageTableGeter230;
	
	public JSReportTable getDataForTable(JSReportTable filter, User user)
	{
		if(filter == null)
			return new JSReportTable();
		if(StringUtils.isEmpty(filter.type))
			return new JSReportTable();
		//System.out.println("filter "+ filter.type);
	//	System.out.println("filter " + filter.orderBy.field);
		if(filter.type.equals("filesNspk"))
			return inMessageTableGeter.getInMessageTable(filter, user);
		if(filter.type.equals("responseEd201"))
//			return inMessageTableGeter201.getInMessageTable(filter, user);
		if(filter.type.equals("files230"))  
//			return inMessageTableGeter230.getInMessageTable(filter, user);
		if(filter.type.equals("docs"))  
			return inMessageTableGeterDoc.getInMessageTable(filter, user);
		if(filter.type.equals("responseEd231"))  
//			return inMessage231TableGeterDoc.getInMessageTable(filter, user);
		return new JSReportTable();
		return filter;
	}
}
