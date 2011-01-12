package com.tosan.ymsg;

import java.io.IOException;
import java.util.Collection;

import org.openymsg.network.DirectConnectionHandler;
import org.openymsg.network.Session;
import org.openymsg.network.SessionState;
import org.openymsg.network.YahooException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

/**
 * Creates a connection to a YMSG server.
 * 
 * @author mjafari
 */
public class YmsgConnection extends AbstractTestNGSpringContextTests{
	public static Logger logger = LoggerFactory.getLogger(YmsgConnection.class);

	private Session session;

	private Collection<String> servers;

	private int port;

	@Autowired
	public void setPort(int port) {
		this.port = port;
	}

	@Autowired
	public void setServers(Collection<String> servers) {
		this.servers = servers;
	}

	@Autowired
	public YmsgConnection(YmsgConfig config) {

		// Setting default values for port and servers
		this.servers = YmsgDefaults.getServers();
		this.port = YmsgDefaults.PORT;
		if (config.getServers() != null && !config.getServers().isEmpty()) {
			servers = config.getServers();
		}
		if (config.getPort() != null) {
			this.port = config.getPort();
		}
	}

	private boolean isConnected() {
		return session != null
				&& ( session.getSessionStatus() == SessionState.LOGGED_ON || session.getSessionStatus() == SessionState.CONNECTED);
	}
	
	public void connect() throws YahooException {
		DirectConnectionHandler dch;
		if (!isConnected()) {
			for (String currentHost : this.servers) {
				dch = new DirectConnectionHandler(currentHost, this.port);
				logger.debug("creating new session to ymsg server: "
						+ currentHost + ":" + this.port);
				try {
					session = new Session(dch);
					break;
				}
				catch (Exception e) {
					logger.warn("Connecting to " + currentHost + ":" + this.port + " failed.");
				}
			}
		}
	}

	public void disconnect() throws YahooException {
		if (isConnected()) {
			try {
				session.logout();
			}
			catch (IllegalStateException e) {
				throw new GenericYahooException("error in logout.", e);
			}
			catch (IOException e) {
				throw new GenericYahooException("error in logout.", e);
			}
		}
	}

	public Session getSession() throws YahooException {
		connect();
		return session;
	}

}
