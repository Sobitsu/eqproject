package org.hc.nps.inMessage;

import org.hc.nps.biks.BiksDBFLoader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-web-context.xml" })

public class scandir {
	@Autowired
	private InMessageReader imr;
	
	@Autowired
	private BiksDBFLoader bnlloader;

	@Test
	public void scan() throws Exception
	{
		
		bnlloader.loadBiks();
		imr.doIt();
	}
}
