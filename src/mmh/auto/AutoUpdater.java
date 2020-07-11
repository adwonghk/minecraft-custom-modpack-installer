package mmh.auto;

import mmh.Main;

import java.io.IOException;

import javax.swing.*;

public class AutoUpdater implements Runnable {
	
	private AutoDownloader downloader;
	private String url = "https://dl.dropboxusercontent.com//s/8gki91za4iwc6a2/mcModHelper.jar?dl=0";
	
	private Thread thread;
	private Timer timer;

	public AutoUpdater(String url) {
		if (url != null) {
			this.url = url;
		} else {
			return;
		}
		
		downloader = new AutoDownloader(this.url, true);	
		
		timer = new Timer(100, e -> {
			if (downloader.success) {
				System.out.println("[Auto Updater] - Auto downloaded the newest version of mc mod helper!");
				timer.stop();
				// start the new version
				try {
					System.out.println("[Auto Updater] - Exexcuting the newer version: " + "java -jar " + System.getProperty("user.dir") +  (Main.isWindows() ? "\\" : "/") + "mcModHelper.jar");
					Runtime.getRuntime().exec("java -jar " + System.getProperty("user.dir") +  (Main.isWindows() ? "\\" : "/") + "mcModHelper.jar");
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "Please restart this program!\n Error: " + e);
					e1.printStackTrace();
				}
				
				// exit the current program
				System.exit(0);
			}
		});
	}
	
	public void startUpdate() {
		thread = new Thread(this);
		thread.start();
		timer.start();
	}

	@Override
	public void run() {
		downloader.startDownload();
	}

}
