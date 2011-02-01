/**
 * 
 */
package com.tosan.spring.integration.ymsg.config;

import org.springframework.integration.config.xml.HeaderEnricherParserSupport;

import com.tosan.spring.integration.ymsg.YmsgHeaders;

/**
 * @author mjafari
 *
 */
public class YmsgHeaderEnricherParser extends HeaderEnricherParserSupport {
	
	public YmsgHeaderEnricherParser() {
		this.addElementToHeaderMapping("chat-to", YmsgHeaders.CHAT_TO);
		this.addElementToHeaderMapping("chat-thread-id", YmsgHeaders.CHAT_THREAD_ID);
	}
}
