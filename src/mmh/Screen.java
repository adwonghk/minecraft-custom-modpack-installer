package mmh;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.Timer;

import mmh.auto.AutoUpdater;
import mmh.config.ConfigLoader;
import mmh.pages.*;

public class Screen extends JPanel {
	private static final long serialVersionUID = -2486623228027275624L;
	
	public static final int PAGE_WIDTH = 450;
	public static final int PAGE_HEIGHT = 200;
	
	private int stageIndex = 0;
	
	private JProgressBar progressBar;
	
	private ArrayList<JPanel> stages = new ArrayList<JPanel>();
	
	private JButton btnNext, btnLast;
	private JLabel lbCheckUpdate;
	private String loadingText = "...";
	
	private Timer timer;
	
	private AutoUpdater autoUpdater;
	
	public Screen(ConfigLoader config) {
		config.load();	
		
		this.setSize(450, 300);
		this.setLayout(null);
		
		stages.add(new Welcome());

		stages.add(new Finish());
		
		this.add(stages.get(stageIndex));
		
		progressBar = new JProgressBar(0, stages.size() - 1);
		progressBar.setValue(stageIndex);
		progressBar.setStringPainted(true);
		progressBar.setBounds(120, 205, 195, 20);
		this.add(progressBar);
		
		btnLast = new JButton("上一頁");
		btnLast.setBounds(10, 205, 100, 20);
		
		btnNext = new JButton("下一頁");
		btnNext.setBounds(325, 205, 100, 20);
		btnNext.setEnabled(false);
		
		this.add(btnNext);
		this.add(btnLast);

		btnNext.addActionListener(e -> {
			if (stageIndex == stages.size() - 1) return;
			
			this.remove(stages.get(stageIndex));
			
			stageIndex++;
			progressBar.setValue(stageIndex);
			
			this.add(stages.get(stageIndex));
			repaint();
		});
		
		btnLast.addActionListener(e -> {
			if (stageIndex == 0) return;
			
			this.remove(stages.get(stageIndex));
			
			stageIndex--;
			progressBar.setValue(stageIndex);
			
			this.add(stages.get(stageIndex));
			repaint();
		});
		
		lbCheckUpdate = new JLabel("Checking update...");
		lbCheckUpdate.setBounds(10, 230, 400, 20);
		this.add(lbCheckUpdate);
		
		timer = new Timer(100, e -> { 
			loadingText = loadingText.equals("...") ? ".." : loadingText.equals("..") ? "." : "...";
			lbCheckUpdate.setText("Fetching data " + loadingText);
			checkUpdate(config);
			repaint();
		});
		timer.start();
		
	}
	
	public void setEnableAllBtns(boolean enable) {
		btnLast.setEnabled(enable);
		btnNext.setEnabled(enable);
	}

	private void checkUpdate(ConfigLoader config) {
		if (config.success) {
			System.out.println("----------");
			String versionHint = "";
			if (Main.version.compareTo(new Version(config.parser.version)) >= 0) {
				// current version is the newest
				System.out.println("[Info] - current version is the newest");
				versionHint = "Current version is the newest!";
				
		    	lbCheckUpdate.setText("Fetched successfully! " + versionHint);
		    	btnNext.setEnabled(true);
		    	
				stages.add(1, new AutoDownloadInstall(this, config.parser.modpack_link));
		    	
		    	timer.stop();
				
			} else if (Main.version.compareTo(new Version(config.parser.version)) == -1) {
				// current version needs an update
				System.out.println("[Info] - current version needs an update");
				versionHint = "Current version needs an update! Please wait...";
				
		    	lbCheckUpdate.setText("Fetched successfully! " + versionHint);
		    	autoUpdater = new AutoUpdater(config.parser.update_link);
		    	autoUpdater.startUpdate();
		    	timer.stop();
			}
			
			stages.add(1, new DownloadForge(config.parser.mc_version, config.parser.forge_version, config.parser.forge_link));
			stages.add(new DownloadModPack(config.parser.modpack_link));
			stages.add(new InstallModPack());
			stages.add(new DownloadServer(config.parser.server_pack_link));
			
		}
		progressBar.setMaximum(stages.size() - 1);
	}

}
