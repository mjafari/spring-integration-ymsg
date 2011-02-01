/**
 * This class configures an {@link YmsgConnection} object. 
 * This object is used for all scenarios to talk to a Ymsg server.
 * @see YmsgConnection
 */
package com.tosan.spring.integration.ymsg;

import java.io.IOException;

import org.openymsg.network.FailedLoginException;
import org.openymsg.network.LoginRefusedException;
import org.openymsg.network.YahooException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.context.SmartLifecycle;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.tosan.ymsg.GenericYahooException;
import com.tosan.ymsg.YmsgConfig;
import com.tosan.ymsg.YmsgConnection;

/**
 * @author mjafari
 * 
 */
public class YmsgConnectionFactoryBean extends
		AbstractFactoryBean<YmsgConnection> implements SmartLifecycle {

	private final YmsgConfig connectionConfiguration;

	private volatile String user;

	private volatile String password;

	private volatile YmsgConnection connection;

	private volatile boolean autoStartup;

	private volatile int phase = Integer.MIN_VALUE;

	private volatile boolean started;

	public YmsgConnectionFactoryBean() {
		super();
		this.connectionConfiguration = null;
	}

	public YmsgConnectionFactoryBean(YmsgConfig connectionConfiguration) {
		Assert.notNull(connectionConfiguration,
				"'connectionConfiguration' must not be null");
		this.connectionConfiguration = connectionConfiguration;
	}

	@Override
	protected YmsgConnection createInstance() throws Exception {
		connection = new YmsgConnection(this.connectionConfiguration);
		return connection;
	}

	@Override
	public Class<? extends YmsgConnection> getObjectType() {
		return YmsgConnection.class;
	}

	@Override
	public int getPhase() {
		return this.phase;
	}

	@Override
	public boolean isAutoStartup() {
		return this.autoStartup;
	}

	@Override
	public boolean isRunning() {
		return this.started;
	}

	public void setAutoStartup(boolean autoStartup) {
		this.autoStartup = autoStartup;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUser(String user) {
		this.user = user;
	}

	@Override
	public void start() {
		try {
			connection.connect();
			if (StringUtils.hasText(user)) {
				connection.getSession().login(user, password);
				this.started = true;
			}
			else {
				throw new GenericYahooException("yahoo user must have text");
			}

		}
		catch (FailedLoginException e) {
			throw new BeanInitializationException(
					"failed to login to yahoo - failed login", e);
		}
		catch (LoginRefusedException e) {
			throw new BeanInitializationException(
					"failed to login to yahoo - refused login", e);
		}
		catch (IllegalStateException e) {
			throw new BeanInitializationException("failed to login to yahoo", e);
		}
		catch (IOException e) {
			throw new BeanInitializationException(
					"failed to login to yahoo - probably network problem", e);
		}
		catch (YahooException e) {
			throw new BeanInitializationException("failed to connect to yahoo",
					e);
		}
	}

	@Override
	public void stop() {
		if (this.isRunning()) {
			try {
				this.connection.disconnect();
				this.started = false;
			}
			catch (YahooException e) {
				logger.error(e);
			}
		}
	}

	@Override
	public void stop(Runnable callback) {
		callback.run();
	}

}
