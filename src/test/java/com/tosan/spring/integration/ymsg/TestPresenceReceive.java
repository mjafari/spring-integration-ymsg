package com.tosan.spring.integration.ymsg;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


/**
 * 
 * 
 * @author mjafari
 */

@Test
@ContextConfiguration(locations = {"classpath:META-INF/spring/receive-presence-test.xml"})
public class TestPresenceReceive extends AbstractTestNGSpringContextTests {
	
	@Resource(name = "messageText")
	private String messageText;
	
	@Resource(name = "receiverYahooId")
	private String receiverYahooId;
	
	@Resource(name = "timeoutMilisecond")
	private Integer timeoutMilisecond;
	
	@BeforeClass
	protected void createConnections() throws Exception {
		Logger vc = LoggerFactory.getLogger(TestPresenceReceive.class);
	}

	@AfterClass
	protected void disconnect() throws Exception {
		// TODO disconnect;
	}

	/**
	 * This test method first loads ymsg:presense-inbound-channel-adapter (define in contex file receive-presence-test.xml) that starts automatically. 
	 * Then it make a 60 second delay before finishing execution. In this delay you can change status of each friend of ${yahoo.receiver.username} and see its result on console.
	 */
	@Test
	protected void receiveDuringDurarion() {
		try {
			// Keep program running for a while
			Thread.sleep(60000);
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
