package com.tosan.spring.integration.ymsg;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.integration.handler.AbstractMessageHandler;
import org.springframework.util.Assert;

import com.tosan.ymsg.YmsgConnection;

public abstract class AbstractYmsgMessageHandler extends AbstractMessageHandler {

	protected volatile YmsgConnection ymsgConnection;

	protected volatile boolean initialized;

	public AbstractYmsgMessageHandler() {
	}

	public AbstractYmsgMessageHandler(YmsgConnection ymsgConnection) {
		Assert.notNull(ymsgConnection, "'ymsgConnection' must no be null");
		this.ymsgConnection = ymsgConnection;
	}

	protected void onInit() throws Exception {
		BeanFactory bf = this.getBeanFactory();
		if (ymsgConnection == null && bf != null) {
			ymsgConnection = bf.getBean(
					YmsgContextUtil.YMSG_CONNECTION_BEAN_NAME,
					YmsgConnection.class);
		}
		Assert.notNull(
				ymsgConnection,
				"Failed to resolve YMSGConnection. YMSGConnection must either be set expicitly "
						+ "via 'ymsg-connection' attribute or implicitly by registering a bean with the name 'ymsgConnection' and of type "
						+ "'org.jivesoftware.smack.XMPPConnection' in the Application Context");
		this.initialized = true;
	}

}
