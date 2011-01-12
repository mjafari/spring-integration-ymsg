/**
 * 
 */
package com.tosan.spring.integration.ymsg.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.integration.config.xml.IntegrationNamespaceUtils;
import org.w3c.dom.Element;

import com.tosan.spring.integration.ymsg.YmsgConnectionFactoryBean;
import com.tosan.ymsg.YmsgConfig;
import com.tosan.ymsg.YmsgConnection;

/**
 * Parser for 'ymsg:ymsg-connection' element.
 * @author mjafari
 * 
 */
public class YmsgConnectionParser extends AbstractSingleBeanDefinitionParser {

	private static String[] connectionFactoryAttributes = new String[] {
			"user", "password" };

	private static Logger logger = LoggerFactory
			.getLogger(YmsgConnection.class);

	@Override
	protected String getBeanClassName(Element element) {
		return YmsgConnectionFactoryBean.class.getName();
	}

	@Override
	protected boolean shouldGenerateIdAsFallback() {
		return true;
	}

	@Override
	protected void doParse(Element element, ParserContext parserContext,
			BeanDefinitionBuilder builder) {
		logger.debug("doParse + 1");
		BeanDefinitionBuilder ymsgConfigBuilder = BeanDefinitionBuilder
				.genericBeanDefinition(YmsgConfig.class);
		IntegrationNamespaceUtils.setValueIfAttributeDefined(ymsgConfigBuilder,
				element, "host");
		IntegrationNamespaceUtils.setValueIfAttributeDefined(ymsgConfigBuilder,
				element, "port");

		for (String attribute : connectionFactoryAttributes) {
			IntegrationNamespaceUtils.setValueIfAttributeDefined(builder,
					element, attribute);
		}
		IntegrationNamespaceUtils.setValueIfAttributeDefined(builder, element,
				"auto-startup");
		builder.addConstructorArgValue(ymsgConfigBuilder.getBeanDefinition());
		logger.debug("doParse + end");
	}
}
