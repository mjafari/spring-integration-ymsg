/**
 * 
 */
package com.tosan.spring.integration.ymsg.config;

import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.integration.config.xml.AbstractOutboundChannelAdapterParser;
import org.w3c.dom.Element;

import com.tosan.spring.integration.ymsg.YmsgSendingMessageHandler;

/**
 * Parser for the 'ymsg:outbound-channel-adapter' element
 * @author mjafari
 * @see
 */
public class YmsgOutboundChannelAdapterParser extends
		AbstractOutboundChannelAdapterParser {

	@Override
	protected AbstractBeanDefinition parseConsumer(Element element,
			ParserContext parserContext) {
		BeanDefinitionBuilder builder = BeanDefinitionBuilder
				.genericBeanDefinition(YmsgSendingMessageHandler.class
						.getName());
		String connectionName = element.getAttribute("ymsg-connection");
		builder.addConstructorArgReference(connectionName);
		return builder.getBeanDefinition();
	}

}
