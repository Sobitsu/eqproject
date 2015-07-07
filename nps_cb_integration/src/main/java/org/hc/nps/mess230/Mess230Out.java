package org.hc.nps.mess230;

/**
 * @author dale
 *
 */
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.commons.io.FileUtils;
import org.hc.nps.calendar.CalendarUtils;
import org.hc.nps.db.entities.JSMess230det;
import org.hc.nps.db.entities.JSMess230head;
import org.hc.nps.db.mappers.JSMess230detRowMapper;
import org.hc.nps.db.mappers.JSMess230headRowMapper;
import org.hc.jp.exceptions.ExceptionBuilder;
import org.hc.nps.files.FileCheckSum;
import org.hc.jp.files.FileManager;
import org.hc.nps.files.FileNames;
import org.hc.jp.utils.DateTimeUtils;
import org.hc.nps.utils.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Repository
public class Mess230Out {
	private static final Logger LOG = LoggerFactory.getLogger(Mess230Out.class);

	@Value(value = "${fornspc.path}")
	private String dirname; 

	
	private final String SQL_SELECT_REESTRLIST = "SELECT id, Status, BeginProcessingDate, EndProcessingDate,  "
															+ "	ClearingSystemCode,  RegisterItemsQuantity,  RegisterDebetSum,  "
															+ "RegisterCreditSum,  EDNo,  EDDate,  EDAuthor"
															+" FROM reestrhist WHERE status = 'CREATE'";
	private final String SQL_SELECT_REESTRDETAIL = "SELECT CollId, Status, RegisterItemID, Sum, DC, BIC FROM reestrinfo WHERE CollId = ?";

	@Autowired
	private FileManager filemanager;
	
	@Autowired
	private FileNames filenames;
	
	@Autowired
	private CalendarUtils cu;
	
	@Autowired
	private ExceptionBuilder exceptionBuilder;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Value(value = "${xml.encoding}")
	private String encoding; 
	
	@Scheduled(fixedDelay = 60000L)
	public void doIt() throws Exception {
		List<JSMess230out> curree = getCurReestr();
		for(JSMess230out ree : curree)
		{
			createXMLOut(ree);
		}
	}
	private List<JSMess230outdetail> getReeDetail(Long id){
		List<JSMess230outdetail> result = new LinkedList<JSMess230outdetail>();
		List<JSMess230det> reestrs = jdbcTemplate.query(SQL_SELECT_REESTRDETAIL, new JSMess230detRowMapper(), new Object[] {id});
		for (JSMess230det ps : reestrs) {
			JSMess230outdetail rdetail = new JSMess230outdetail();
			rdetail.BIC = ps.Bic;
			rdetail.RegisterItemID = ps.RegisterItemID;
			rdetail.DC = ps.DC;
			rdetail.Sum = ps.Sum;
			result.add(rdetail);
		}		
		return result;
	}
	public List<JSMess230out> getReeBysql(String sql){
		List<JSMess230out> result = new LinkedList<JSMess230out>();
		List<JSMess230head> reestrs = jdbcTemplate.query(sql, new JSMess230headRowMapper());
		for (JSMess230head ps : reestrs) {
			JSMess230out rdetail = new JSMess230out();
			rdetail.BeginProcessingDate = ps.BeginProcessingDate;
			rdetail.ClearingSystemCode = "0"+ps.ClearingSystemCode;
			rdetail.EDNo = ps.EDNo;
			rdetail.EDAuthor = ps.EDAuthor;
			rdetail.EDDate = ps.EDDate;
			rdetail.EndProcessingDate = ps.EndProcessingDate;
			rdetail.RegisterCreditSum = ps.RegisterCreditSum;
			rdetail.RegisterDebetSum = ps.RegisterDebetSum;
			rdetail.RegisterItemsQuantity = ps.RegisterItemsQuantity;
			rdetail.RegisterItemsInfo = getReeDetail(ps.id);
			result.add(rdetail);
			}		
	return result;
	}
	private List<JSMess230out> getCurReestr(){
	
		return getReeBysql(SQL_SELECT_REESTRLIST);
	}

	public static byte[] stringToBytes(String str) {
		 char[] buffer = str.toCharArray();
		 byte[] b = new byte[buffer.length];
		 for (int i = 0; i < b.length; i++) {
		  b[i] = (byte) buffer[i];
		 }
		 return b;
		}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)//Делаем в транзакции
	public void createXMLOut(JSMess230out outRee) throws ED230Exception{
		String filename = filenames.getfilename("ED230",outRee.ClearingSystemCode.replace("0", ""), outRee.EDDate);
		File file;
		String checksum;
		File dir = filemanager.getPathDir(dirname);
		File dirtmp = filemanager.getPathDir(dirname+"/tmp");
		FileOutputStream fis;
		String header = "<?xml version=\"1.0\" encoding=\""+encoding+"\"?>";
		byte[] b = stringToBytes(header);
		try {
			   JAXBContext context = JAXBContext.newInstance(JSMess230out.class);
			   Marshaller marshaller = context.createMarshaller();
			   marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
			   marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			   file = filemanager.getFile(dirtmp.getName(),filename);
			   fis = new FileOutputStream(file);
			   fis.write(b);
			   marshaller.marshal(outRee, fis);
			   fis.close();
			   checksum = FileCheckSum.md5checkSum(file);
			   StringBuilder sqlUpdate = new StringBuilder();
			   sqlUpdate.append("UPDATE reestrhist set Status = 'EXPORTED', falename = ?  where EDNo = ? and EDDate = ?");
			   sqlUpdate.append(";");
			   jdbcTemplate.update(sqlUpdate.toString(), new Object[] { filename, outRee.EDNo, DateTimeUtils.formatDate(outRee.EDDate)});
			   LOG.info(LogUtil.fileexportlog("E230", outRee.EDDate, checksum,filename));
			   cu.setfilescounter(outRee.ClearingSystemCode.replace("0", ""), outRee.EDDate);
			   FileUtils.moveFileToDirectory(file, dir, true);
		} catch (JAXBException exception) {LOG.error("Ошибка при выгрузке сообщения ED230", exception);
			throw new ED230Exception("FATAL ERROR!!! Ошибка при формировании xml ED230");
			  }
		catch (IOException exception){LOG.error("FATAL Error. File system error", exception);
			throw new ED230Exception("FATAL ERROR!!! Ошибка при выгрузке ED230");
		  }
	}
}
