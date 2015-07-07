package org.hc.nps.message231;

import java.util.List;

import org.hc.jp.db.JDBCUtils;
import org.hc.jp.db.mappers.LongMapper;
import org.hc.jp.utils.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.xml.sax.SAXException;

@Repository
public class Mess231DB {
@Autowired
JdbcTemplate jdbcTemplate;
private static String SQLMATCHSTR = "SELECT b.ID FROM reestrhist a, reestrinfo b WHERE a.ID = b.COLLID and b.RegisterItemID = ? and a.EDDate = ? and a.EDNo = ? "; 

@Transactional(propagation = Propagation.REQUIRES_NEW)
public void saveDB(JSMess231InHead mess231, String filename) throws SAXException {
	Long id;
	id = saveHead(mess231,filename);
	saveDet(mess231,id);
	matchDet(mess231, id);
}
private void matchDet(JSMess231InHead mess231, Long m231id)
{
	for (JSMess231InInfo ree: mess231.CrdTransfRegisterInfo){
		List<Long> iDs = jdbcTemplate.query(SQLMATCHSTR, new LongMapper(),  new Object[] { ree.registeritemid, DateTimeUtils.formatDate(mess231.InitialED.eddate), mess231.InitialED.edno });
			for (Long id :  iDs)
				{
					String sql = "Update reestrinfoed321 a, reestrhisted231 b set a.ree230detid = ? where a.collid = b.id and b.id = ? and a.registeritemid = ?";
					jdbcTemplate.update(sql, new Object[] { id, m231id, ree.registeritemid});
					String status = "DONECONTROLCB";
					if (ree.statuscode == 1L) status = "DONECONTROLCB";
					if (ree.statuscode == 2L) status = "PAYDCB";
					if (ree.statuscode == 3L) status = "WAITMONEYCB";
					if (ree.statuscode == 4L) status = "REFUSECB";
					if (ree.statuscode == 5L) status = "CANTPAYCB";
					sql = "Update reestrinfo set status = ? where id = ?";
					jdbcTemplate.update(sql, new Object[] { status,id});
				}
	}
}

private Long saveHead(JSMess231InHead mess231, String filename) {

		StringBuilder sql = new StringBuilder();

		sql.append("INSERT INTO reestrhisted231("
				+ "BeginProcessingDate, "
				+ "EndProcessingDate, "
				+ "ClearingSystemCode, "
				+ "RegisterItemsQuantity, "
				+ "falename, "
				+ "RegisterMode, "
				+ "EDNo, "
				+ "EDDate, "
				+ "EDAuthor, "
				+ "InitialEDEDNo, "
				+ "InitialEDEDDate, "
				+ "InitialEDEDAuthor "
				+ ") "
				+ "VALUES ");
			sql.append("('")
				.append(DateTimeUtils.formatDate(mess231.beginprocessingdate)).append("' ")
				.append(", '").append(DateTimeUtils.formatDate(mess231.endprocessingdate)).append("' ")
				.append(", '").append(mess231.ClearingSystemCode).append("' ")
				.append(", '").append(mess231.registeritemsquantity).append("' ")
				.append(", '").append(filename).append("' ")
				.append(", '").append(mess231.RegisterMode).append("' ")
				.append(", '").append(mess231.edno).append("' ")
				.append(", '").append(DateTimeUtils.formatDate(mess231.eddate)).append("' ")
				.append(", '").append(mess231.edauthor).append("' ")
				.append(", '").append(mess231.InitialED.edno).append("' ")
				.append(", '").append(DateTimeUtils.formatDate(mess231.InitialED.eddate)).append("' ")
				.append(", '").append(mess231.InitialED.edauthor)
				.append("')");

			sql.append(";");
			Long id = JDBCUtils.insertAndGetId(jdbcTemplate,  sql.toString());
			return id;
}

private void saveDet(JSMess231InHead mess231, Long id) throws SAXException{
	
	if (mess231.CrdTransfRegisterInfo.size()>0) {
	StringBuilder sql = new StringBuilder();

	sql.append("INSERT INTO reestrinfoed321("
					+ "collid, "
					+ "RegisterItemID, "
					+ "bic, "
					+ "sum, "
					+ "dc, "
					+ "statuscode, "
//					+ "CtrlCode, "
					+ "EDNo, "
					+ "EDDate, "
					+ "EDAuthor) "
//					+ "Annotation) "
					+ "VALUES ");

	Boolean isNotFirst = false;
	try 
		{
			for(JSMess231InInfo minfo : mess231.CrdTransfRegisterInfo)
				{
					if(minfo == null)
						continue;
					if(isNotFirst)
						sql.append(", ");
						sql.append("('").
							 append(id).append("'")
							.append(", '").append(minfo.registeritemid).append("' ")
							.append(", '").append(minfo.bic).append("' ")
							.append(", '").append(minfo.sum).append("' ")
							.append(", '").append(minfo.dc).append("' ")
							.append(", '").append(minfo.statuscode).append("' ")
		//					.append(", '").append(minfo.ctrlcode).append("' ")
							.append(", '").append(minfo.EDRefID.edno).append("' ")
							.append(", '").append(DateTimeUtils.formatDate(minfo.EDRefID.eddate)).append("' ")
							.append(", '").append(minfo.EDRefID.edauthor)//.append("' ")
			//				.append(", '").append(minfo.Annotation)
							.append("')");
					isNotFirst = true;
				}
	} catch (Exception e) {
		throw new SAXException();
	}
	sql.append(";");
	jdbcTemplate.update(sql.toString());}
	}
}
