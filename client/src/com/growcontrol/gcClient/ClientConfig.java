package com.growcontrol.gcClient;

import com.growcontrol.gcCommon.pxnConfig.pxnConfig;
import com.growcontrol.gcCommon.pxnLogger.pxnLogger;


public class ClientConfig {

	// path to config files
	protected String configsPath = null;
	// config loader
	protected pxnConfig config = null;
	// instance of this
	protected static ClientConfig clientConfig = null;


	public static ClientConfig get() {
		if(clientConfig == null)
			clientConfig = new ClientConfig(null);
		return clientConfig;
	}
	protected static ClientConfig get(String dirPath) {
		if(clientConfig == null)
			clientConfig = new ClientConfig(dirPath);
		return clientConfig;
	}


	// load config.yml
	protected ClientConfig(String dirPath) {
		if(dirPath != null)
			configsPath = dirPath;
		try {
			config = pxnConfig.loadFile(configsPath, "config.yml");
		} catch (Exception e) {
			pxnLogger.get().exception(e);
		}
	}


	// version
	public String Version() {
		if(config == null) return null;
		return config.getString("Version");
	}


	// log level
	public String LogLevel() {
		if(config == null) return null;
		return config.getString("Log Level");
	}


}
