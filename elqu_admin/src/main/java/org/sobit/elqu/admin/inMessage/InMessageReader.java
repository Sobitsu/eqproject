package org.sobit.elqu.admin.inMessage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.FileUtils;
//import org.hc.nps.calendar.CalendarUpdateReader;
//import org.hc.nps.calendar.CalendarUtils;
import org.hc.jp.files.FileManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sobit.elqu.admin.biks.BiksDBFLoader;
import org.sobit.elqu.admin.files.FileCheckSum;
import org.sobit.elqu.admin.utils.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;
import org.xml.sax.SAXException;

@Repository
public class InMessageReader {
	private static final Logger LOG = LoggerFactory.getLogger(InMessageReader.class);

	@Autowired
	private inMessageDB inMDB;

	@Autowired
	private FileManager filemanager;
	
	@Autowired
	private inMessageCheck inMCh;
	
	@Autowired
	private inMessageLoad iml;
	
//	@Autowired
//	private CalendarUtils cu;
	@Autowired
	private BiksDBFLoader biksDBFLoader;
	private static String BNKSEEKNAME = "BNKSEEK.DBF";
	private static String CALUPDATENAME = "calendar.xml";
	@Value(value = "${fromnspc.path}")
	private String dirname; 
//	@Autowired
//	private CalendarUpdateReader cur;
	//@Value(value = "${scheduledelay}")
	@Scheduled(fixedDelay = 60000L)
	public void doIt() throws Exception {
		Date todate =  java.util.Calendar.getInstance().getTime();
		File dir = filemanager.getPathDir(dirname);
		File dirarc = filemanager.getPathDir(dirname+"/arc");
		File dirtmp = filemanager.getPathDir(dirname+"/tmp");
		File[] files = filemanager.getListFiles(dir);
		filemanager.removeAllFiles(dirname+"/tmp");
		filemanager.removeAllFiles(filemanager.getPathDir().getName());
		
		JSInMessSession inMess = new JSInMessSession();
		for (File f:files)
		{
			try {
				FileUtils.moveToDirectory(f, dirtmp, true);
			} catch (IOException exception) {
				LOG.error("FATAL Error. File system error ", exception);
			}
		}
		files = filemanager.getListFiles(dirtmp);
		for (File f:files)
			{	
			String hashsum= FileCheckSum.md5checkSum(f);	
			try {
				if (f.getName().contentEquals(BNKSEEKNAME)) {
					File fbnk = filemanager.getFile(BNKSEEKNAME);
					if (fbnk != null) FileUtils.deleteQuietly(fbnk);
					FileUtils.moveFileToDirectory(f, filemanager.getPathDir(), true);
					biksDBFLoader.loadBiks();
					LOG.info(LogUtil.fileimportlog(BNKSEEKNAME, null, hashsum, BNKSEEKNAME));
					continue;
				}
				if (f.getName().contentEquals(CALUPDATENAME)){
					FileUtils.moveFileToDirectory(f, filemanager.getPathDir(), true);
//					cur.loadCalendar();
					LOG.info(LogUtil.fileimportlog(CALUPDATENAME, null, hashsum, CALUPDATENAME));
					continue;
				}
				else {
					//	if (!cu.isDateWork(todate)) 
					//		{
					//			FileUtils.moveToDirectory(f, dir, true);
					//			continue;
					//		};
						FileInputStream fis = new FileInputStream(f);
						inMCh.all_bic = "~";
						iml.loadFileXML(fis, inMDB, inMess, inMCh,f.getName());
						fis.close();
						FileUtils.copyFileToDirectory(f, dirarc, true);
						LOG.info(LogUtil.fileimportlog("INFOBACK", null, hashsum, f.getName()));
					}
				} catch (ParserConfigurationException exception) {
					LOG.error(LogUtil.fileimporterror("INFOBACK", null, hashsum, f.getName() + exception.getMessage()));
					FileUtils.moveFileToDirectory(f, filemanager.getPathDir(dirname+"/err"), true);
				} catch (SAXException exception) {
					LOG.error("FATAL Error. Incorrect file format " + f.getName() + exception.getMessage());
					FileUtils.moveFileToDirectory(f, filemanager.getPathDir(dirname+"/err"), true);
				} catch (IOException exception) {
					LOG.error("FATAL Error. Incorrect file format " + f.getName() + exception.getMessage());
					FileUtils.moveFileToDirectory(f, filemanager.getPathDir(dirname+"/err"), true);
				}
		}
		try {
			if (inMess.inmessges.size() != 0){
			inMDB.saveDB(inMess);
			filemanager.removeAllFiles(dirname+"/tmp");}
		} catch (Exception e) {
//			LOG.error("FATAL Error save to DB" + e.getMessage(), e);
			filemanager.removeAllFiles(dirname+"/tmp");
			LOG.error("FATAL Error save to DB", e);
		}
	}
}
