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
		registerBeanDefinitionParser("ymsg-connection",
				new YmsgConnectionParser());

		// send/receive messages
		registerBeanDefinitionParser("inbound-channel-adapter",
				new YmsgInboundChannelAdapterParser());
		registerBeanDefinitionParser("outbound-channel-adapter",
				new YmsgOutboundChannelAdapterParser());
		
		// presence
		registerBeanDefinitionParser("presence-inbound-channel-adapter", new YmsgPresenceInboundChannelAdapterParser());
		registerBeanDefinitionParser("presence-outbound-channel-adapter", new YmsgPresenceOutboundChannelAdapterParser());

		registerBeanDefinitionParser("header-enricher", new YmsgHeaderEnricherParser());
	}

}
