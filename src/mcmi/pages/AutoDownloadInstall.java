package mcmi.pages;

import java.awt.Color;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import com.sun.org.apache.bcel.internal.Const;
import components.ModernStyleUI;
import components.RoundedBorder;
import mcmi.Constant;
import mcmi.Main;
import mcmi.Screen;
import mcmi.auto.AutoDownloader;
import mcmi.auto.AutoUnzipper;

public class AutoDownloadInstall extends JPanel {
	private static final long serialVersionUID = -8324611571879837373L;
	
	private JLabel hint;
	private JButton btn;
	private JTextArea console;
	private StringBuilder messages = new StringBuilder("");
	
	private AutoDownloader downloader;
	private Timer timer;
	private String loadingText = "...";
	
	private enum states {onReady, downloadMods, removeOldMods, unzipMods, removeModsZip, onFinish};
	private states currentState = states.onReady;
	
	public AutoDownloadInstall(Screen screen, String modpackURL) {
		this.setBackground(Constant.secondaryColor);
		this.setSize(Screen.PAGE_WIDTH, Screen.PAGE_HEIGHT);
		this.setLayout(null);
		
		downloader = new AutoDownloader(modpackURL, false);
		
		hint = new JLabel("一鍵全自動下載mod pack並安裝 (請先安裝Forge!!!)");
		hint.setFont(Constant.body);
		hint.setForeground(Constant.textColor);
		hint.setBounds(10, 0, 400, 40);
		add(hint);
		
		btn = new JButton();
		btn.setText("按我按我 <3");
		btn.setBounds(10, 40, 100, 20);
		btn.setHorizontalAlignment(SwingConstants.CENTER);
		btn.setBorder(new RoundedBorder(5, Constant.btnBorderColor, 1));
		btn.setBackground(Constant.secondaryColor);
		btn.setFont(Constant.body);
		btn.setUI(new ModernStyleUI());
		btn.setFocusPainted(false);
		btn.setForeground(Constant.textColor);
		btn.addActionListener(e -> {
			btn.setEnabled(false);
			screen.setEnableAllBtns(false);
			timer.start();
		});
		add(btn);
		
		console = new JTextArea();
		console.setEditable(false);
		console.setFont(Constant.body);
		console.setBackground(Constant.mainColor);
		console.setForeground(Constant.textColor);

		console.setBounds(10, 70, 400, 40);
		JScrollPane jScrollPane = new JScrollPane(console);
		jScrollPane.setBackground(Constant.mainColor);
		jScrollPane.setForeground(Constant.textColor);
		jScrollPane.setBounds(10, 70, 400, 110);
		add(jScrollPane);
		
		if (currentState == states.onReady) {
			currentState = states.downloadMods;
		}
		
		timer = new Timer(100, e -> {
			if (currentState == states.downloadMods) {
				downloadMods();
			} else if (currentState == states.removeOldMods) {
				removeOldMods();
			} else if (currentState == states.unzipMods) {
				unzipMods();
			} else if (currentState == states.removeModsZip) {
				System.out.println("[Info] - Start to remove mods.zip\n");
				File file = new File(Main.defualtMCLocation +  (Main.isWindows() ? "\\" : "/") + "mods.zip");
				if (file.delete()) {
					System.out.println("[Info] - Removed mods.zip");
					messages.append("Deleted mods.zip!\n");
				} else {
					System.out.println("[Info] - Failed at removing mods.zip");
					messages.append("Failed at deleting mods.zip! Please ask Ad to solve this problem...\n");
				}
				currentState = states.onFinish;
			} else if (currentState == states.onFinish) {
				timer.stop();
				screen.setEnableAllBtns(true);
				btn.setEnabled(true);
			}
			console.setText(messages.toString());
		});
		
	}

	private void downloadMods() {
		messages.replace(0, messages.length(), "Downloading mods" + loadingText);
		loadingText = loadingText.equals("...") ? ".." : loadingText.equals("..") ? "." : "...";
		
		if (!downloader.isDownloading) downloader.startDownload();
		
		if (downloader.success) {
			messages.append("\nDownload completed!\n");
			timer.stop();
			currentState = states.removeOldMods;
			timer.start();
		}
	}

	private void removeOldMods() {
		System.out.println("----------");
		messages.append("Start to delete old mods...\n");
		System.out.println("[Info] - Start to removing old mods");
		
		File modsFolder = new File(Main.defualtMCLocation +  (Main.isWindows() ? "\\" : "/") +"mods");
		if (!modsFolder.exists()) {
			modsFolder.mkdir();
		} else {
			int numFiles = 0;
			for(File file: modsFolder.listFiles()) {
			    if (!file.isDirectory()) {
			    	numFiles++;
			    	messages.append("Deleting files: " + file.getName() + "\n");
					System.out.println("[Info] - Deleting files: " + file.getName());
			    	file.delete();
			    }
			}
		    messages.append("Deleted " + numFiles  + " files\n");
			System.out.println("[Info] - Deleted " + numFiles  + " files");
			System.out.println("----------");
		}

		timer.stop();
		currentState = states.unzipMods;
		timer.start();
	}

	private void unzipMods() {
		System.out.println("----------");
		System.out.println("[Info] - Start to unzip mods");
		System.out.println("[Info] - Unzipping " + Main.defualtMCLocation +  (Main.isWindows() ? "\\" : "/") + "mods.zip");
		
		try {
			messages.append("Unzipping " + Main.defualtMCLocation + " mods.zip...");
			AutoUnzipper unzipper = new AutoUnzipper(Main.defualtMCLocation +  (Main.isWindows() ? "\\" : "/") + "mods.zip", Main.defualtMCLocation +  (Main.isWindows() ? "\\" : "/") + "mods");
			String[] filesName = unzipper.filesName.split(" ");
			for (String n : filesName) {
				messages.append("Unzipped files: " + n + "\n");
			}
			
			messages.append("Unzipped successfully!\n");
			messages.append("Unzipped " + filesName.length + " files!\n");
			System.out.println("[Info] - Unzipped " + filesName.length + " files!");
			timer.stop();
			currentState = states.removeModsZip;
			timer.start();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		System.out.println("----------");
	}

}
