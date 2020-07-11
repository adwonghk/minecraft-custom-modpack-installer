package mcmi.config;

import java.util.HashMap;
import java.util.Map;

public class ConfigLoader implements Runnable {
	
	private Map<String, String> defaultConfig;
	
	private Request request;
	
	public Parser parser;
	
	public boolean success = false;
	
	
	public ConfigLoader() {
		initDefaultConfig();
	}
	
	private void initDefaultConfig() {
		defaultConfig = new HashMap<String, String>();
		defaultConfig.put("version", "1.0");
		defaultConfig.put("modpack_version", "1.0");
		defaultConfig.put("mc_version", "1.5.2");
		defaultConfig.put("forge_version", "31.2.0");
		defaultConfig.put("forge_link", "ttps://files.minecraftforge.net/");
		defaultConfig.put("modpack_link", "https://www.dropbox.com/s/9sj223rfgt4ukuj/mods.zip?dl=0");
		defaultConfig.put("server_pack_link", "https://www.dropbox.com/s/ouot5ithvhzcl6w/server%201.15.2.zip?dl=0");
	}
	
	public synchronized void load() {
		Thread thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run() {
		System.out.println("Requesting config from " + Request.URL + "...");
		request = new Request();
		parser = new Parser(request.getResponseBody());
		if (request.success) {
			System.out.println("Request successful!");
			success = true;
		} else {
			System.out.println("Request failed! Will use defualt config instead.");
			success = false;
		}
	}

}
