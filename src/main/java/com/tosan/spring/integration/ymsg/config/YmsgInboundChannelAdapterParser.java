/**
 * 
 */
package com.tosan.spring.integration.ymsg.config;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.integration.config.xml.IntegrationNamespaceUtils;
import org.w3c.dom.Element;

import com.tosan.spring.integration.ymsg.YmsgListeningMessageEndpoint;

/**
 * Parser for the 'ymsg:inbound-channel-adapter' element.
 * @author mjafari
 * 
 */
public class YmsgInboundChannelAdapterParser extends
		AbstractSingleBeanDefinitionParser {
	@Override
	protected String getBeanClassName(Element element) {
		return YmsgListeningMessageEndpoint.class.getName();
	}

	@Override
	protected boolean shouldGenerateId() {
		return false;
	}

	@Override
	protected boolean shouldGenerateIdAsFallback() {
		return true;
	}

	@Override
	protected void doParse(Element element, ParserContext parserContext,
			BeanDefinitionBuilder builder) {
		String connectionName = element.getAttribute("ymsg-connection");
		builder.addConstructorArgReference(connectionName);
		IntegrationNamespaceUtils.setReferenceIfAttributeDefined(builder,
				element, "channel", "requestChannel");
		IntegrationNamespaceUtils.setValueIfAttributeDefined(builder, element,
				"extract-payload");
		IntegrationNamespaceUtils.setValueIfAttributeDefined(builder, element,
				"auto-startup");
	}
}
