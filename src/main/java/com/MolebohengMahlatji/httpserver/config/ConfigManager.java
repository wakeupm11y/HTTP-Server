package com.MolebohengMahlatji.httpserver.config;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.MolebohengMahlatji.httpserver.util.Json;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

public class ConfigManager {
	// singleton instance, we only need one manager
	private static ConfigManager myConfigManager;
	private static Config myCurrentConfig;

	private ConfigManager() {

	}

	public static ConfigManager getInstance() {
		if (myConfigManager == null)
			myConfigManager = new ConfigManager();
		return myConfigManager;
	}

	/**
	 * Loads configuration file specified by the file path
	 */

	public void loadConfigFile(String filePath) {
		FileReader fileReader = null;
		try {
			fileReader = new FileReader(filePath);
		} catch (FileNotFoundException e) {
			throw new HttpConfigurationException(e);
		}
		StringBuffer sb = new StringBuffer();
		int i;
		try {
			while ((i = fileReader.read()) != -1) {
				sb.append((char) i);
			}
		} catch (IOException e) {
			throw new HttpConfigurationException(e);
		}

		JsonNode conf = null;

		try {
			conf = Json.parse(sb.toString());
		} catch (IOException e) {
			throw new HttpConfigurationException("Error parsing configuration file.", e);
		}

		try {
			myCurrentConfig = Json.fromJson(conf, Config.class);
		} catch (JsonProcessingException e) {
			throw new HttpConfigurationException("Error parsing configuration file, internal", e);
		}
	}

	/**
	 * Returns the current loaded configuration
	 *
	 *
	 */
	public Config getCurrentConfig() {
		if (myCurrentConfig == null)
			throw new HttpConfigurationException("No Current Configuration set.");
		return myCurrentConfig;
	}
}
