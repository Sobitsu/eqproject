package org.hc.nps.message201;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.FileUtils;
import org.hc.nps.files.FileCheckSum;
import org.hc.jp.files.FileManager;
import org.hc.nps.utils.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;
import org.xml.sax.SAXException;

@Repository
public class inMessage201Reader {
	private static final Logger LOG = LoggerFactory.getLogger("event_info");

	@Autowired
	private inMessage201DB inMDB;

	@Autowired
	private FileManager filemanager;
	
	@Autowired
	private inMessage201Check inMCh;
	
	@Autowired
	private inMessage201Load iml;
	
	@Value(value = "${repcb201.path}")
	private String dirname; 
	
	@Scheduled(fixedDelay = 60000L)
	public void doIt() throws Exception{
		
		File dir = filemanager.getPathDir(dirname);
		File dirarc = filemanager.getPathDir(dirname+"/arc");
		File dirtmp = filemanager.getPathDir(dirname+"/tmp");
		File[] files = filemanager.getListFiles(dir);
		filemanager.removeAllFiles(dirname+"/tmp");
		JSInMessSession201 inMess = new JSInMessSession201();
		for (File f:files)
		{
			try {
				FileUtils.moveToDirectory(f, dirtmp, true);
			} catch (IOException exception) {
				LOG.error("FATAL Error. File system error", exception);
			}
		}
		files = filemanager.getListFiles(dirtmp);
		for (File f:files)
		{
		String hashsum= FileCheckSum.md5checkSum(f);
		try {
			iml.loadFileXML(f, inMDB, inMess, inMCh);
			FileUtils.copyFileToDirectory(f, dirarc, true);
			LOG.info(LogUtil.fileimportlog("ED201", null, hashsum, f.getName()));
		} catch (ParserConfigurationException exception) {
			LOG.error(LogUtil.fileimporterror("ED201", null, hashsum, f.getName()));
		} catch (SAXException exception) {
			LOG.error("FATAL Error. Incorrect file format " + f.getName());
		} catch (IOException exception) {
			LOG.error("FATAL Error. File system error ");
		}
		
		}
		try {
			inMDB.saveDB(inMess);
			filemanager.removeAllFiles(dirname+"/tmp");
		} catch (Exception e) {
			filemanager.removeAllFiles(dirname+"/tmp");
			LOG.error("FATAL Error save to DB", e);
		}
		
	}
}
