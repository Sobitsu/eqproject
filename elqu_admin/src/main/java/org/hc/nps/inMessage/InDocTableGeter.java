package org.hc.nps.inMessage;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hc.jp.db.JDBCUtils;
import org.hc.jp.db.PaginateHelper;
import org.hc.jp.db.PaginateParams;
import org.hc.jp.db.entities.User;
import org.hc.jp.db.mappers.LongMapper;
import org.hc.jp.exceptions.ExceptionBuilder;
import org.hc.nps.exceptions.mvc.js.JSReportRow;
import org.hc.nps.exceptions.mvc.js.JSReportTable;
import org.hc.jp.js.JSOrderBy;
import org.hc.jp.users.UserRolesUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
*
* @author Kat
*/
@Repository
public class InDocTableGeter {
@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private ExceptionBuilder exceptionBuilder;

	private final String SQL_SELECT_IN_MESSAGES ="SELECT "+ 
													"a.id, "+
													"a.BIC, "+
													"IF(a.DC = '1','true','false') as dc, "+
													"a.sum, "+
													"a.status, "+
													"b.falename, "+
													"ed.filename as file_201, "+
													"h.falename as file_231, "+
													"b.BeginProcessingDate "+
													"FROM "+
													"reestrinfo a "+
													"INNER JOIN reestrhist b on a.CollId = b.id "+
													"LEFT JOIN ed201hist ed on b.EDNo = ed.EDRefID_EDNo and b.EDDate = ed.EDRefID_EDDate and b.EDAuthor  = ed.EDRefID_EDAuthor "+
													"LEFT JOIN reestrinfoed321 i on a.id  = i.ree230detid "+
													"LEFT JOIN reestrhisted231 h on i.CollId = h.id"
													+ " where 1=1"; 
			
			
			
			
			//"SELECT a.id, a.BIC"+
				//							",a.DC,a.sum,a.status,b.falename"+ 
					//						",(select(ed.filename) from ed201hist ed where b.EDNo = ed.EDRefID_EDNo and b.EDDate = ed.EDRefID_EDDate and b.EDAuthor  = ed.EDRefID_EDAuthor) as file_201"+
											//",(select(i.filename) from inreestrhist h, inreestrinfo i where h.id = b.inreeID and i.CollId = h.id group by i.filename) as in_file"+
						//					",(select(h.falename) from reestrhisted231 h,reestrinfoed321 i where i.ree230detid = a.id and i.CollId = h.id  ) as file_231"+
							//				",b.BeginProcessingDate FROM reestrinfo a, reestrhist b where a.CollId = b.id";
	
	
	
	
	private final String SQL_COUNT_IN_MESSAGES = "SELECT "+ 
													"count(a.id)"+
													"FROM "+
													"reestrinfo a "+
													"INNER JOIN reestrhist b on a.CollId = b.id "+
													"LEFT JOIN ed201hist ed on b.EDNo = ed.EDRefID_EDNo and b.EDDate = ed.EDRefID_EDDate and b.EDAuthor  = ed.EDRefID_EDAuthor "+
													"LEFT JOIN reestrinfoed321 i on a.id  = i.ree230detid "+
													"LEFT JOIN reestrhisted231 h on i.CollId = h.id"
													+ " where 1=1";  
			
			//"SELECT count(a.id), FROM  reestrinfo a, reestrhist b where a.CollId = b.id";

	public JSReportTable getInMessageTable(JSReportTable filter, User user)
	{
		
		if(user == null)
			throw exceptionBuilder.noUserException();
		if(!UserRolesUtils.isSuperAdmin(user))
			throw exceptionBuilder.noUserAccessException();
		
		if(filter == null)
			filter = new JSReportTable();
		if(filter.orderBy == null)
			filter.orderBy = new JSOrderBy("id", JSOrderBy.DESC);
		
		JSReportTable result = new JSReportTable();
		result.totalItems = 0L;
		result.items = new JSReportRow[0];
		
		PaginateParams paginateParams = PaginateHelper.getPaginateParamsFromFilter(filter);
		result.currentPage = paginateParams.currentPage;
		result.itemsOnPage = paginateParams.itemsOnPage;
		
		String sqlCondition = createFilterCondition(filter);
		
		StringBuilder sql = new StringBuilder(SQL_SELECT_IN_MESSAGES);
		sql.append(sqlCondition);
		String orderByCondition = createOrderByCondition(filter.orderBy);
		if(orderByCondition != null)
			sql.append(orderByCondition);
		
		sql.append(" LIMIT ").append(paginateParams.rowCount).append(" OFFSET ").append(paginateParams.offset);
		
		List<JSReportRow> list = jdbcTemplate.query(sql.toString(), new JSInDocRowMapper(""));
		
		
		if(list != null)
			if(!list.isEmpty())
				result.items = list.toArray(new JSReportRow[0]);
		
		sql = new StringBuilder(SQL_COUNT_IN_MESSAGES);
		sql.append(sqlCondition);
		Long count = JDBCUtils.queryForObject(jdbcTemplate, sql.toString(), new LongMapper());
		if(count != null)
			result.totalItems = count;
		
		result.orderBy = filter.orderBy;
		
		return result;
	}
	
	private String createFilterCondition(JSReportTable filter) {
		StringBuilder sqlstr = new StringBuilder();
		if (!(filter.id == null))  sqlstr.append(" and a.id = '"+filter.id+"'");
		if (!(filter.status == null) && (filter.status.length()!=0))  
					sqlstr.append(" and a.status = '"+filter.status+"'");
		if (!(filter.nspkFileName == null)&& (filter.nspkFileName.length()!=0))  
				sqlstr.append(" and in_file = '"+filter.nspkFileName+"'");
		if (!(filter.sendFileName == null)&& (filter.sendFileName.length()!=0))  
				sqlstr.append(" and b.falename = '"+filter.sendFileName+"'");
		if (!(filter.reject201FileName == null)&& (filter.reject201FileName.length()!=0))  
				sqlstr.append(" and ed.filename = '"+filter.reject201FileName+"'");
		if (!(filter.reject231FileName == null)&& (filter.reject231FileName.length()!=0))  
				sqlstr.append(" and h.falename = '"+filter.reject231FileName+"'");
		return sqlstr.toString();
	}

	private String createOrderByCondition(JSOrderBy orderBy)
	{
		if(orderBy == null)
			return null;
		if(StringUtils.isBlank(orderBy.field))
			return null;
		
		return " ORDER BY " + fieldmapper(orderBy.field) + " " + (orderBy.direction == null ? "asc" : orderBy.direction);
	}
	private String fieldmapper(String field){
		String fmap = "id";
			
		switch(field )	
		{	case "id" : fmap = "id";break;
			case "bik" : fmap =  "bic";break;
			case "status" : fmap = "Status";break;
			case "sum" : fmap = "sum";break;
			case "nspkFileName" : fmap = "in_file";break;
			case "sendFileName" : fmap = "falename";break;
			case "reject201FileName" : fmap = "file_201";break;
			case "reject231FileName" : fmap = "file_231";break;
			case "date" : fmap = "BeginProcessingDate";break;
			
		}
	
		return fmap;
	}
}
