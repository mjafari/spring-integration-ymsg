package com.tosan.spring.integration.ymsg.config;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * This class parses the schema for YMSG support. This NamespaceHandler subclass
 * and it's associated parsers imitate
 * {@link org.springframework.integration.xmpp.config.XmppNamespaceHandler}
 * @author mjafari
 * 
 */
public class YmsgNamespaceHandler extends NamespaceHandlerSupport {

	@Override
	public void init() {
		// TODO = In comparison whith XMPP support some element like
		// presence-inbound-channel-adapter, presence-outbound-channel-adapter
		// and header-enricher are not implemented.
		// connection
		registerBeanDefinitionParser("ymsg-connection",
				new YmsgConnectionParser());

		// send/receive messages
		registerBeanDefinitionParser("inbound-channel-adapter",
				new YmsgInboundChannelAdapterParser());
		registerBeanDefinitionParser("outbound-channel-adapter",
				new YmsgOutboundChannelAdapterParser());
	}

}
