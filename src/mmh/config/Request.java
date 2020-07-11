package mmh.config;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import javax.swing.JOptionPane;


public class Request {
	
	public static final String URL = "http://chwongbx.student.ust.hk/mc/config.json"; // https://adwonghk-mc.glitch.me/config.json";
	private String charset = "UTF-8";
	
	public boolean success = false;
	
	private String responseBody;
	
	public Request() {
		URLConnection connection = null;
		InputStream response = null;
		
		try {
			connection = new URL(URL).openConnection();
			connection.setRequestProperty("Accept-Charset", charset);
			connection.setConnectTimeout(10000);
			connection.setReadTimeout(10000);
			
			response = connection.getInputStream();
		} catch (MalformedURLException e) {
			JOptionPane.showMessageDialog(null, "Unable to download file to local system\n Error: " + e);
			e.printStackTrace();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Unable to download file to local system\n Error: " + e);
			e.printStackTrace();
		}
		
		String responseBody;
		
		try (Scanner scanner = new Scanner(response)) {
		    responseBody = scanner.useDelimiter("\\A").next();
		}
		
		this.responseBody = responseBody;
		
		success = true;
	}
	
	public String getResponseBody() {
		return responseBody;
	}

}
