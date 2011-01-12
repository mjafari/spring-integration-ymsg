package com.tosan.spring.integration.ymsg;

import org.openymsg.network.YahooException;
import org.openymsg.network.event.SessionAdapter;
import org.openymsg.network.event.SessionEvent;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.util.Assert;

import com.tosan.ymsg.YmsgConnection;

public class YmsgListeningMessageEndpoint extends AbstractYmsgMessageEndpoint {

	private final YMSGListener listener = new YMSGListener();

	private MessagingTemplate messagingTemplate = new MessagingTemplate();

	private volatile boolean extractPayload = true;

	public YmsgListeningMessageEndpoint() {
		super();
	}

	public YmsgListeningMessageEndpoint(YmsgConnection ymsgConnection) {
		super(ymsgConnection);
	}

	/**
	 * @param requestChannel the channel on which the inbound message should be
	 * sent
	 */
	public void setRequestChannel(MessageChannel requestChannel) {
		this.messagingTemplate.setDefaultChannel(requestChannel);
	}

	/**
	 * Specify whether the text message body should be extracted when mapping to
	 * a Spring Integration Message payload. Otherwise, the full XMPP Message
	 * will be passed within the payload. This value is <em>true</em> by
	 * default.
	 */
	public void setExtractPayload(boolean extractPayload) {
		this.extractPayload = extractPayload;
	}

	@Override
	protected void onInit() throws Exception {
		super.onInit();
		this.messagingTemplate.afterPropertiesSet();
	}

	@Override
	protected void doStart() {
		Assert.isTrue(this.initialized,
				this.getComponentName() + " [" + this.getComponentType()
						+ "] must be initialized");
		try {
			this.ymsgConnection.getSession().addSessionListener(this.listener);
		}
		catch (YahooException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	protected void doStop() {
		if (this.ymsgConnection != null) {
			try {
				this.ymsgConnection.getSession().removeSessionListener(
						this.listener);
			}
			catch (YahooException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private class YMSGListener extends SessionAdapter {

		@Override
		public void messageReceived(SessionEvent event) {
			Object payload = (extractPayload ? event.getMessage() : event);
			// TODO = xmpp adds some headers like TYPE and CHAT. Verify if it is
			// usefull for ymsg
			MessageBuilder<?> messageBuilder = MessageBuilder
					.withPayload(payload);
			messagingTemplate.send(messageBuilder.build());
		}

	}

}
