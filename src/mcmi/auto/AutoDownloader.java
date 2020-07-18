package mcmi.auto;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import javax.swing.*;

import mcmi.Main;

public class AutoDownloader implements Runnable {
	
	private String url = "https://dl.dropboxusercontent.com/s/9sj223rfgt4ukuj/mods.zip?dl=0";
	private String filename = "mods.zip";
	
	private Thread thread;
	
	public boolean success = false;
	public boolean isDownloading = false;
	
	private boolean selfUpdate = false;

	private JProgressBar jProgressBar;
	
	public AutoDownloader(String url, boolean selfUpdate, JProgressBar jProgressBar) {
		if (url == null || url.isEmpty()) {
			this.url = "https://dl.dropboxusercontent.com/s/9sj223rfgt4ukuj/mods.zip?dl=0";
		} else {
			this.url = url;
		}
		this.selfUpdate = selfUpdate;
		this.jProgressBar = jProgressBar;

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
		    //ReadableByteChannel rbc = Channels.newChannel(download.openStream());
			HttpURLConnection httpConnection = (HttpURLConnection) (download.openConnection());
			long completeFileSize = httpConnection.getContentLength();
		    
		    FileOutputStream fileOut = null;
		    if (selfUpdate) {
		    	filename = "mcModHelper.jar";
		    	System.out.println("[Auto Downloader] - output to " + System.getProperty("user.dir") + (Main.isWindows() ? "\\" : "/") + filename);
		    	fileOut = new FileOutputStream(System.getProperty("user.dir") + (Main.isWindows() ? "\\" : "/") + filename);
		    } else {
				System.out.println("[Auto Downloader] - output to " + Main.defualtMCLocation + (Main.isWindows() ? "\\" : "/") + filename);
		    	fileOut = new FileOutputStream(Main.defualtMCLocation + (Main.isWindows() ? "\\" : "/") + filename);
		    }

			BufferedInputStream in = new BufferedInputStream(httpConnection.getInputStream());
			BufferedOutputStream bout = new BufferedOutputStream(fileOut, 1024);

			byte[] data = new byte[1024];

			long downloadedFileSize = 0;
			int x = 0;
			double lastDownloadedMB = 0;
			double completeFileSizeMB = BigDecimal.valueOf((double) completeFileSize / 1024.0d / 1024.0d).setScale(3, RoundingMode.HALF_UP).doubleValue();

			System.out.println("[Auto Downloader] - file size: " + completeFileSizeMB + "MB");
			while ((x = in.read(data, 0, 1024)) >= 0) {
				double downloadedMB = BigDecimal.valueOf((double) downloadedFileSize / 1024.0d / 1024.0d).setScale(3, RoundingMode.HALF_UP).doubleValue();
				if (downloadedMB - lastDownloadedMB > 0){
					System.out.print("\r");
					System.out.print("[Auto Downloader] - process: " + downloadedMB + "MB/" + completeFileSizeMB + "MB");
					lastDownloadedMB = downloadedMB;
				}

				downloadedFileSize += x;

				// calculate progress
				final int currentProgress = (int) ((((double)downloadedFileSize) / ((double)completeFileSize)) * 100000d);

				// update progress bar
				if (jProgressBar != null) {
					SwingUtilities.invokeLater(() -> jProgressBar.setValue(currentProgress));
				}

				bout.write(data, 0, x);
			}
			System.out.println();


		    //fileOut.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		    //fileOut.flush();
		    fileOut.close();
			in.close();
		    //rbc.close();
		    
		    System.out.println("[Auto Downloader] - Download competed!");
		    success = true;
		    
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, "無法下載檔案，請向Ad查詢\n Error: " + e);
			e.printStackTrace(); 
		}
	}

}
