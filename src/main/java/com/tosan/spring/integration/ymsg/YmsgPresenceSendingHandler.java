package com.tosan.spring.integration.ymsg;

import org.openymsg.network.Session;
import org.openymsg.network.Status;
import org.openymsg.network.YahooUser;
import org.openymsg.network.event.SessionFriendEvent;
import org.springframework.integration.Message;
import org.springframework.util.Assert;

import com.tosan.ymsg.YmsgConnection;

public class YmsgPresenceSendingHandler extends AbstractYmsgMessageHandler {

	public YmsgPresenceSendingHandler(YmsgConnection ymsgConnection) {
		super(ymsgConnection);
	}

	@Override
	protected void handleMessageInternal(Message<?> message) throws Exception {
		Assert.isTrue(this.initialized,
				this.getComponentName() + "#" + this.getComponentType()
						+ " must be initialized");
		Object payload = message.getPayload();
		Assert.isInstanceOf(SessionFriendEvent.class,payload,
				"'payload' must be of type " + SessionFriendEvent.class.getName()+ "'', was: " 
				+ payload.getClass().getName());
		Session session = ymsgConnection.getSession();
		YahooUser yahooUser = ((SessionFriendEvent)payload).getUser();
		boolean showBusyIcon = !yahooUser.getStatus().equals(Status.AVAILABLE);
		// TODO = Handle custumStatus attribute of YahooUser that have not a clear usage.
		if(yahooUser.getStatus().equals(Status.CUSTOM)){
			session.setStatus(yahooUser.getCustomStatusMessage(), showBusyIcon);
		} else {
			session.setStatus(yahooUser.getStatus());
		}
	}

}
