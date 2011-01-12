package com.tosan.spring.integration.ymsg;

import org.openymsg.network.Session;
import org.springframework.integration.Message;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.tosan.ymsg.YmsgConnection;

public class YmsgSendingMessageHandler extends AbstractYmsgMessageHandler {

	public YmsgSendingMessageHandler(YmsgConnection ymsgConnection) {
		super(ymsgConnection);
	}

	@Override
	protected void handleMessageInternal(Message<?> message) throws Exception {
		Assert.isTrue(this.initialized,
				this.getComponentName() + "#" + this.getComponentType()
						+ " must be initialized");
		Object messageBody = message.getPayload();
		String chatTo = (String) message.getHeaders().get(YmsgHeaders.CHAT_TO);
		Assert.state(StringUtils.hasText(chatTo), "The '" + YmsgHeaders.CHAT_TO
				+ "' header must not be null");
		Assert.isInstanceOf(
				String.class,
				messageBody,
				"Only payload of type String is suported. You "
						+ "can apply transformer prior to sending message to this handler");
		// String threadId = (String)
		// message.getHeaders().get(YmsgHeaders.CHAT_THREAD_ID);
		Session session = ymsgConnection.getSession();
		String destination = message.getHeaders()
				.get(YmsgHeaders.CHAT_TO, String.class).split("@")[0];
		session.sendMessage(destination, (String) message.getPayload());
	}

}
