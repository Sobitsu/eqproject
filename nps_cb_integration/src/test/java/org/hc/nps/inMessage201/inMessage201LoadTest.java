package org.hc.nps.inMessage201;


//import org.hc.nps.message201.inMessage201Reader;
import org.hc.nps.message201.inMessage201Reader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-web-context.xml" })
public class inMessage201LoadTest {
	@Autowired
	private inMessage201Reader ML;
	
	@Test
	public void LOADFile() throws Exception{
		ML.doIt(); //.loadFileXML("ED201_.xml");
	}
}
