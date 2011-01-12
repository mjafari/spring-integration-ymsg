package com.tosan.ymsg;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.PatternSyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * Configuration to use while establishing the connection to the server. It is a
 * light copy of {@link org.jivesoftware.smack.ConnectionConfiguration}
 * 
 * @author mjafari
 */
public class YmsgConfig {
	public static Logger logger = LoggerFactory.getLogger(YmsgConfig.class);

	private Collection<String> servers;

	private Integer port;

	public void setServer(String server) {
		Assert.hasText(server,
				"server must be a host name or a comma seperated list of them");
		try {
			String[] servers = server.split(",");
			this.servers = new ArrayList<String>(servers.length);
			for (String host : servers) {
				host = host.trim();
				if (!host.isEmpty()) {
					this.servers.add(host);
				}
			}
		}
		catch (PatternSyntaxException e) {
			logger.warn("server attribute value is wrong: " + e);
			throw new IllegalArgumentException(e);
		}
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public Collection<String> getServers() {
		return servers;
	}

}
