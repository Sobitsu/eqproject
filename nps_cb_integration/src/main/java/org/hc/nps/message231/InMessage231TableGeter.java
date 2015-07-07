package org.hc.nps.message231;

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
import org.hc.jp.files.FileManager;
import org.hc.jp.js.JSOrderBy;
import org.hc.jp.users.UserRolesUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
*
* @author Kat
*/
@Repository
public class InMessage231TableGeter {
	
//private static final Logger LOG = LoggerFactory.getLogger(InMessageTableGeter.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private ExceptionBuilder exceptionBuilder;

	private final String SQL_SELECT_IN_MESSAGES = "select EDNo,falename,EDDate,BeginProcessingDate,EndProcessingDate,ClearingSystemCode,RegisterItemsQuantity,RegisterMode from reestrhisted231";
	private final String SQL_COUNT_IN_MESSAGES = "SELECT count(id) FROM  reestrhisted231";
	
	@Value(value = "${repcb231.path}")
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
		System.out.print("orderByCondition "+ orderByCondition);
		if(orderByCondition != null)
			sql.append(orderByCondition);
		
		sql.append(" LIMIT ").append(paginateParams.rowCount).append(" OFFSET ").append(paginateParams.offset);
	//	System.out.println("++++++++++++sql.toString() "+sql.toString());
		List<JSReportRow> list = jdbcTemplate.query(sql.toString(), new JSInMessage231RowMapper(dirname));
		
		
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
		{	case "edNo" : fmap = "edNo";break;
			case "fileName" : fmap =  "fileName";break;
			case "esCreateDate" : fmap = "EDDate";break;
			case "registryBeginDate" : fmap = "BeginProcessingDate";break;
			case "registryEndDate" : fmap = "EndProcessingDate";break;
			case "systemCode" : fmap = "ClearingSystemCode";break;
			case "registryRecordCount" : fmap = "RegisterItemsQuantity";break;
			case "registryType" : fmap = "RegisterMode";break;
			}
		
		return fmap;
	}
}
