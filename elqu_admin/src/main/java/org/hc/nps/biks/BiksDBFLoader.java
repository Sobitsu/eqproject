package org.hc.nps.biks;

import java.io.File;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.hc.nps.Constants;
import org.hc.jp.dbf.DBFReader;
import org.hc.jp.exceptions.ExceptionBuilder;
import org.hc.jp.files.FileManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ctac
 */
@Repository
public class BiksDBFLoader implements Constants
{
	private static final Logger LOG = LoggerFactory.getLogger(BiksDBFLoader.class);
	
	@Autowired
	private FileManager fileManager;
	
	@Autowired
	private ExceptionBuilder exceptionBuilder;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	public void loadBiks()
	{
		loadBiks(FILE_NAME_BIKS);
	}
	
	public void loadBiks(String fileName)
	{
		File file = getFile(fileName);
		if(file == null)
			return;
		
		DBFReader dbfReader = new DBFReader();
		List<JSBik> biks = null;
		try
		{
			dbfReader.init(file);
			biks = readAllRekvizits(dbfReader);
		}
		catch(Exception e)
		{
			LOG.error("Cannot read dbf file [" + fileName + ", " + file.getAbsolutePath() + "]. Reason[" + e.getClass() + "]: " + e.getMessage(), e);
			throw exceptionBuilder.internalException(e, "Cannot init dbf reader.");
		}
		
		if(biks == null)
			return;
		if(biks.isEmpty())
			return;
		
		insertBiksIntoDB(biks);
	}
	
	private File getFile(String fileName)
	{
		return fileManager.getFile(fileName);
	}
	
	
	private List<JSBik> readAllRekvizits(DBFReader dbfReader) throws Exception
	{
		Map<String, Integer> columnMap = new HashMap<String, Integer>();
		
		List<JSBik> biks = new LinkedList<JSBik>();
		
		int i;
		for (i=0; i < dbfReader.getFieldCount(); i++) 
		{
			String columnName = dbfReader.getField(i).getName();
			//System.out.print(columnName + "  ");
			
			if("NAMEP".equals(columnName))
				columnMap.put(columnName, i);
			else if("NAMEN".equals(columnName))
				columnMap.put(columnName, i);
			else if("NEWNUM".equals(columnName))
				columnMap.put(columnName, i);
			else if("KSNP".equals(columnName))
				columnMap.put(columnName, i);
			else if("REGN".equals(columnName))
				columnMap.put(columnName, i);
			else if("OKPO".equals(columnName))
				columnMap.put(columnName, i);
		}
		//System.out.print("\n");
        
		for(i = 0; dbfReader.hasNextRecord(); i++)
		{
			Object aobj[] = dbfReader.nextRecord(Charset.forName("CP866"));
			
			JSBik bik = new JSBik();
			bik.bik = String.valueOf(aobj[columnMap.get("NEWNUM")]);
			bik.corrAcc = String.valueOf(aobj[columnMap.get("KSNP")]);
			bik.fullName = String.valueOf(aobj[columnMap.get("NAMEP")]);
			bik.name = String.valueOf(aobj[columnMap.get("NAMEN")]);
			bik.regNum = String.valueOf(aobj[columnMap.get("REGN")]);
			bik.okpo = String.valueOf(aobj[columnMap.get("OKPO")]);
			
			//System.out.println(rekvizit);
			
			biks.add(bik);
		}

		System.out.println("biks Total Count: " + i);
		
		return biks;
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	private void insertBiksIntoDB(List<JSBik> biks)
	{
		jdbcTemplate.update("DELETE FROM biks");
		
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO biks(bik, corr_acc, name, full_name, reg_num, okpo) VALUES ");
		
		boolean isNotFirst = false;
		for(JSBik bik : biks)
		{
			if(bik == null)
				continue;
			
			if(isNotFirst)
				sql.append(", ");
			
			sql.append("('").append(bik.bik).append("'")
					.append(", '").append(bik.corrAcc).append("' ")
					.append(", '").append(bik.name).append("' ")
					.append(", '").append(bik.fullName).append("' ")
					.append(", '").append(bik.regNum).append("' ")
					.append(", '").append(bik.okpo).append("')");
			
			isNotFirst = true;
		}
		
		sql.append(";");
		jdbcTemplate.update(sql.toString());
	}
}
