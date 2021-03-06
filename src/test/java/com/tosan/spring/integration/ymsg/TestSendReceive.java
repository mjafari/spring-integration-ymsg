package com.tosan.spring.integration.ymsg;

import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.Message;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.core.PollableChannel;
import org.springframework.integration.message.GenericMessage;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


/**
 * 
 * 
 * @author mjafari
 */

@Test
@ContextConfiguration(locations = {"classpath:META-INF/spring/send-receive-test.xml"})
public class TestSendReceive extends AbstractTestNGSpringContextTests {
	@Autowired
	@Qualifier(value = "sendChannel")
	private MessageChannel sendChannel;
	
	@Autowired
	@Qualifier(value = "receiveChannel")
	private PollableChannel receiveChannel;
	
	@Resource(name = "messageText")
	private String messageText;
	
	@Resource(name = "receiverYahooId")
	private String receiverYahooId;
	
	@Resource(name = "timeoutMilisecond")
	private Integer timeoutMilisecond;
	
	@BeforeClass
	protected void createConnections() throws Exception {
		Logger logger = LoggerFactory.getLogger(TestSendReceive.class);
	}

	@AfterClass
	protected void disconnect() throws Exception {
		// TODO disconnect;
	}

	/**
	 * This test method first sends a message to "sendChannel" that will be sent through ymsg:outbound-channel-adapter to receiverYahooId.
	 * Simultanously defined ymsg:inbound-channel-adapter is putting received messages to receiveChannel (a 1 capacity que channel).
	 * if receivedMessage (polled from receiveChannel) is equal to messageTesxt, test is passed.
	 */
	@Test
	protected void sendAndReceive() {

		Message<String> sentMessage = MessageBuilder
				.fromMessage(new GenericMessage<String>(messageText))
				.setHeader(YmsgHeaders.CHAT_TO, receiverYahooId).build();

		sendChannel.send(sentMessage);
		
		Message<String> recevedMessage = (Message<String>) receiveChannel
				.receive(timeoutMilisecond);
		Assert.assertEquals(messageText, recevedMessage.getPayload());
	}
}
