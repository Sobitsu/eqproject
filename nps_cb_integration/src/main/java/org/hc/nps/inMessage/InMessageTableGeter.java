package org.hc.nps.inMessage;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hc.jp.db.JDBCUtils;
import org.hc.jp.db.PaginateHelper;
import org.hc.jp.db.PaginateParams;
import org.hc.jp.db.entities.User;
import org.hc.jp.db.mappers.LongMapper;
import org.hc.jp.exceptions.ExceptionBuilder;
import org.hc.jp.users.UserRolesUtils;
import org.hc.nps.exceptions.mvc.js.JSReportRow;
import org.hc.nps.exceptions.mvc.js.JSReportTable;
import org.hc.jp.files.FileManager;
import org.hc.jp.js.JSOrderBy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Ctac
 */
@Repository
public class InMessageTableGeter 
{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private ExceptionBuilder exceptionBuilder;

	private final String SQL_SELECT_IN_MESSAGES = "SELECT DISTINCT "+ 
					" a.id, b.filename, a.EndProcessingDate, a.ClearingSystemCode, a.Status, a.itemCount, a.SumDt, a.SumKt ,c.id as id_230"+
					" FROM inreestrhist a"+
					" LEFT JOIN inreestrinfo b on b.collid = a.id and b.filename != 'create'"+
					" LEFT JOIN reestrhist c on a.id = c.inreeID";
	private final String SQL_COUNT_IN_MESSAGES = "SELECT count(id) FROM  inreestrhist";

	@Value(value = "${fromnspc.path}")
	private String dirnamedata; 
	@Autowired
	private FileManager filemanager;

	public JSReportTable getInMessageTable(JSReportTable filter, User user)
	{
		
		String dirname = filemanager.getPathDir("").getName()+"/"+filemanager.getPathDir(dirnamedata).getName()+"/arc";
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
		
		String sqlCondition = "";
		
		StringBuilder sql = new StringBuilder(SQL_SELECT_IN_MESSAGES);
		sql.append(sqlCondition);
		String orderByCondition = createOrderByCondition(filter.orderBy);
		if(orderByCondition != null)
			sql.append(orderByCondition);
		
		sql.append(" LIMIT ").append(paginateParams.rowCount).append(" OFFSET ").append(paginateParams.offset);
		
		List<JSReportRow> list = jdbcTemplate.query(sql.toString(), new JSInMessageRowMapper(dirname));
		
		
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
		{	case "fileName" : fmap = "filename";break;
			case "datetimeReestr" : fmap =  "EndProcessingDate";break;
			case "creditSum" : fmap = "SumKt";break;
			case "debitSum" : fmap = "SumDt";break;
			case "recordCount" : fmap = "itemCount";break;
			case "psCode" : fmap = "ClearingSystemCode";break;
			case "status" : fmap = "Status";break;
			case "curRegistryDate" : fmap = "EndProcessingDate";break;
			case "edNo" : fmap = "id_230"; break;
		}
		
		return fmap;
	}
}
