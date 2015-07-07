package org.hc.nps.message231;

import java.io.File;
import java.io.FileInputStream;
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
public class Mess231Reader {
	private static final Logger LOG = LoggerFactory.getLogger(Mess231Reader.class);

	@Autowired
	private Mess231DB messDB;

	@Autowired
	private FileManager filemanager;
	
	@Value(value = "${repcb231.path}")
	private String dirname; 

	@Scheduled(fixedDelay = 60000L)
	public void doIt() throws Exception {
		File dir = filemanager.getPathDir(dirname);
		File dirarc = filemanager.getPathDir(dirname+"/arc");
		File dirtmp = filemanager.getPathDir(dirname+"/tmp");
		File[] files = filemanager.getListFiles(dir);
		filemanager.removeAllFiles(dirname+"/tmp");
		JSMess231InHead parsemess;
		FileInputStream fis = null;
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
					Mess231Parser parser = new Mess231Parser();
					fis = new FileInputStream(f);
					parsemess = parser.parse(fis);
					if (parsemess.InitialED.edno.isEmpty()) throw new SAXException();
					fis.close();
					messDB.saveDB(parsemess, f.getName());
					FileUtils.moveFileToDirectory(f, dirarc, true);
					LOG.info(LogUtil.fileimportlog("ED231", parsemess.eddate , hashsum, f.getName()));
				} catch (ParserConfigurationException exception) {
					fis.close();
					LOG.error(LogUtil.fileimporterror("ED231", null, hashsum, f.getName()));
				} catch (SAXException exception) {
					fis.close();
					LOG.error("FATAL Error. Incorrect file format " + f.getName());
				} catch (IOException exception) {
					fis.close();
					LOG.error("FATAL Error. File system error ");
				}
		}
		}
	}
