package mcmi.auto;

import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import javax.swing.JOptionPane;

import mcmi.Main;

public class AutoDownloader implements Runnable {
	
	private String url = "https://dl.dropboxusercontent.com/s/9sj223rfgt4ukuj/mods.zip?dl=0";
	private String filename = "mods.zip";
	
	private Thread thread;
	
	public boolean success = false;
	public boolean isDownloading = false;
	
	private boolean selfUpdate = false;
	
	public AutoDownloader(String url, boolean selfUpdate) {
		if (url == null || url.isEmpty()) {
			this.url = "https://dl.dropboxusercontent.com/s/9sj223rfgt4ukuj/mods.zip?dl=0";
		} else {
			this.url = url;
		}
		this.selfUpdate = selfUpdate;
		
		thread = new Thread(this);
	}
	
	public void startDownload() {
		isDownloading = true;
		thread.start();
	}

	@Override
	public void run() {
		System.out.println("[Auto Downloader] - Start downloading...");
		try{
		    URL download = new URL(url);
		    ReadableByteChannel rbc = Channels.newChannel(download.openStream());
		    
		    FileOutputStream fileOut = null;
		    if (selfUpdate) {
		    	filename = "mcModHelper.jar";
		    	System.out.println("[Auto Downloader] - output to " + System.getProperty("user.dir") + (Main.isWindows() ? "\\" : "/") + filename);
		    	fileOut = new FileOutputStream(System.getProperty("user.dir") + (Main.isWindows() ? "\\" : "/") + filename);
		    } else {
				System.out.println("[Auto Downloader] - output to " + Main.defualtMCLocation + (Main.isWindows() ? "\\" : "/") + filename);
		    	fileOut = new FileOutputStream(Main.defualtMCLocation + (Main.isWindows() ? "\\" : "/") + filename);
		    }
		    
		    
		    fileOut.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		    fileOut.flush();
		    fileOut.close();
		    rbc.close();
		    
		    System.out.println("[Auto Downloader] - Download competed!");
		    success = true;
		    
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Unable to download file to local system\n Error: " + e);
			e.printStackTrace(); 
		}
	}

}
