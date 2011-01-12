package com.tosan.spring.integration.ymsg;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.integration.endpoint.AbstractEndpoint;
import org.springframework.util.Assert;

import com.tosan.ymsg.YmsgConnection;

public abstract class AbstractYmsgMessageEndpoint extends AbstractEndpoint {

	protected volatile YmsgConnection ymsgConnection;

	protected volatile boolean initialized;

	public AbstractYmsgMessageEndpoint() {
	}

	public AbstractYmsgMessageEndpoint(YmsgConnection ymsgConnection) {
		Assert.notNull(ymsgConnection, "'ymsgConnection' must no be null");
		this.ymsgConnection = ymsgConnection;
	}

	protected void onInit() throws Exception {
		BeanFactory beanFactory = this.getBeanFactory();
		if (this.ymsgConnection == null && beanFactory != null) {
			this.ymsgConnection = beanFactory.getBean(
					YmsgContextUtil.YMSG_CONNECTION_BEAN_NAME,
					YmsgConnection.class);
		}
		Assert.notNull(
				this.ymsgConnection,
				"Failed to resolve YmsgConnection. YmsgConnection must either be set expicitly "
						+ "via the 'ymsg-connection' attribute or implicitly by registering a bean with the name 'ymsgConnection' and of type "
						+ "'dev.mj.ymsg.YMSGConnection' in the Application Context.");
		this.initialized = true;
	}

}
