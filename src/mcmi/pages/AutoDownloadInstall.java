package mcmi.pages;

import java.io.File;
import java.io.IOException;

import javax.swing.*;

import audio.WavPlayer;
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
	private JProgressBar progressBar;
	private JTextArea console;
	private StringBuilder messages = new StringBuilder("");
	
	private AutoDownloader downloader;
	private Timer timer;
	private String loadingText = "...";
	
	private enum states {onReady, downloadMods, removeOldMods, unzipMods, removeModsZip, onFinish};
	private states currentState = states.onFinish;
	
	public AutoDownloadInstall(Screen screen, String modpackURL) {
		this.setBackground(Constant.secondaryColor);
		this.setSize(Screen.PAGE_WIDTH, Screen.PAGE_HEIGHT);
		this.setLayout(null);

		progressBar = new JProgressBar();
		progressBar.setMaximum(100000);
		progressBar.setStringPainted(true);
		progressBar.setBounds(120, 40, 290, 20);
		add(progressBar);

		downloader = new AutoDownloader(modpackURL, false, progressBar);
		
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
			progressBar.setValue(0);
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
				removeModZip();
			} else if (currentState == states.onFinish) {
				onFinish(screen);
			}
			console.setText(messages.toString());
		});
		
	}

	private void onFinish(Screen screen) {
		messages.append("完成安裝!\n");

		// play a sound
		new WavPlayer("src/ding.wav");

		timer.stop();
		screen.setEnableAllBtns(true);
		btn.setEnabled(true);
	}

	private void removeModZip() {
		System.out.println("[Info] - Start to remove mods.zip\n");
		File file = new File(Main.defualtMCLocation +  (Main.isWindows() ? "\\" : "/") + "mods.zip");
		if (file.delete()) {
			System.out.println("[Info] - Removed mods.zip");
			messages.append("刪除 mods.zip!\n");
		} else {
			System.out.println("[Info] - Failed at removing mods.zip");
			messages.append("錯誤!無法刪除 mods.zip!請向Ad查詢...\n");
		}
		currentState = states.onFinish;
	}

	private void downloadMods() {
		messages.replace(0, messages.length(), "正在下載模組包" + loadingText);
		loadingText = loadingText.equals("...") ? ".." : loadingText.equals("..") ? "." : "...";
		
		if (!downloader.isDownloading) downloader.startDownload();
		
		if (downloader.success) {
			messages.append("\n下載完成!\n");
			timer.stop();
			currentState = states.removeOldMods;
			timer.start();
		}
	}

	private void removeOldMods() {
		System.out.println("----------");
		messages.append("開始刪除舊模組...\n");
		System.out.println("[Info] - Start to removing old mods");
		
		File modsFolder = new File(Main.defualtMCLocation +  (Main.isWindows() ? "\\" : "/") +"mods");
		if (!modsFolder.exists()) {
			modsFolder.mkdir();
		} else {
			int numFiles = 0;
			for(File file: modsFolder.listFiles()) {
			    if (!file.isDirectory()) {
			    	numFiles++;
			    	messages.append("刪除模組: " + file.getName() + "\n");
					System.out.println("[Info] - Deleting files: " + file.getName());
			    	file.delete();
			    }
			}
		    messages.append("刪除了 " + numFiles  + " 個蕭模組\n");
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
			messages.append("正在解壓縮在 " + Main.defualtMCLocation + " 的mods.zip...\n");
			AutoUnzipper unzipper = new AutoUnzipper(Main.defualtMCLocation +  (Main.isWindows() ? "\\" : "/") + "mods.zip", Main.defualtMCLocation +  (Main.isWindows() ? "\\" : "/") + "mods");
			String[] filesName = unzipper.filesName.split(" ");
			for (String n : filesName) {
				messages.append("解壓縮: " + n + "\n");
			}
			
			messages.append("成功解壓縮!\n");
			messages.append("解壓縮了 " + filesName.length + " 個模組!\n");
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
