package mcmi.config;

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

	public boolean isRequesting  = false;
	public boolean success = false;
	public boolean error = false;
	
	private String responseBody;
	
	public Request() {
		URLConnection connection = null;
		InputStream response = null;
		
		try {
			isRequesting = true;

			connection = new URL(URL).openConnection();
			connection.setRequestProperty("Accept-Charset", charset);
			connection.setConnectTimeout(10000);
			connection.setReadTimeout(10000);
			
			response = connection.getInputStream();



			String responseBody;

			try (Scanner scanner = new Scanner(response)) {
				responseBody = scanner.useDelimiter("\\A").next();
			}

			this.responseBody = responseBody;

			success = true;

		} catch (MalformedURLException e) {
			error = true;
			JOptionPane.showMessageDialog(null, "請關掉再重新開啟本筐式，若仍然有問題請向Ad查詢\n Error: " + e);
			e.printStackTrace();
		} catch (IOException e) {
			error = true;
			JOptionPane.showMessageDialog(null, "請關掉再重新開啟本筐式，若仍然有問題請向Ad查詢\n Error: " + e);
			e.printStackTrace();
		}
	}
	
	public String getResponseBody() {
		return responseBody;
	}

}
