package mcmi;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.LineBorder;

import components.ModernStyleUI;
import components.RoundedBorder;
import mcmi.auto.AutoUpdater;
import mcmi.config.ConfigLoader;
import mcmi.pages.*;

public class Screen extends JPanel {
	private static final long serialVersionUID = -2486623228027275624L;
	
	public static final int PAGE_WIDTH = 450;
	public static final int PAGE_HEIGHT = 200;
	
	private int stageIndex = 0;
	
	private JProgressBar progressBar;
	
	private ArrayList<JPanel> stages = new ArrayList<JPanel>();
	private Welcome welcome;
	private AutoDownloadInstall autoDownloadInstall;

	
	private JButton btnNext, btnLast;
	private JLabel lbCheckUpdate;
	private String loadingText = "...";
	
	private Timer timer;
	
	private AutoUpdater autoUpdater;
	
	public Screen(ConfigLoader config) {
		config.load();
		
		this.setSize(450, 300);
		this.setLayout(null);
		this.setBackground(Constant.mainColor);

		welcome = new Welcome();
		stages.add(welcome);


		
		this.add(stages.get(stageIndex));
		
		progressBar = new JProgressBar(0, stages.size() - 1);
		progressBar.setValue(stageIndex);
		progressBar.setStringPainted(true);
		progressBar.setBounds(120, 215, 200, 20);
		this.add(progressBar);
		
		btnLast = new JButton("上一頁");
		btnLast.setBounds(10, 215, 100, 20);

		btnLast.setBorder(new RoundedBorder(5, Constant.btnBorderColor, 1));
		btnLast.setBackground(Constant.secondaryColor);
		btnLast.setFont(Constant.body);
		btnLast.setUI(new ModernStyleUI());
		btnLast.setFocusPainted(false);
		btnLast.setForeground(Constant.textColor);
		
		btnNext = new JButton("下一頁");
		btnNext.setBorder(new RoundedBorder(5, Constant.btnBorderColor, 1));
		btnNext.setBackground(Constant.secondaryColor);
		btnNext.setFont(Constant.body);
		btnNext.setUI(new ModernStyleUI());
		btnNext.setFocusPainted(false);
		btnNext.setForeground(Constant.textColor);

		btnNext.setBounds(330, 215, 100, 20);
		btnNext.setEnabled(false);
		
		this.add(btnNext);
		this.add(btnLast);

		btnNext.addActionListener(e -> {
			if (stageIndex == 0) {
				removeAllPages();
				if (welcome.btnGrp.getSelection() == null) {
					JOptionPane.showMessageDialog(null, "請先選擇自動或是自定安裝!");
					return;
				} else if (welcome.rbChoice1.isSelected()) {
					System.out.println("[Info] - User choose option 1 - auto download and install");
					addAutoPages(config);
				} else if (welcome.rbChoice2.isSelected()) {
					System.out.println("[Info] - User choose option 2 - custom download and install");
					addCustomPages(config);
				}
			}


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
		lbCheckUpdate.setFont(Constant.body);
		lbCheckUpdate.setForeground(Constant.textColor);
		lbCheckUpdate.setBounds(10, 240, 400, 20);
		this.add(lbCheckUpdate);
		
		timer = new Timer(100, e -> {
			loadingText = loadingText.equals("...") ? ".." : loadingText.equals("..") ? "." : "...";
			lbCheckUpdate.setText("Fetching data " + loadingText);
			checkUpdate(config);

			repaint();
		});
		timer.start();
		
	}

	private void addCustomPages(ConfigLoader config) {
		stages.add(1, new DownloadForge(config.parser.mc_version, config.parser.forge_version, config.parser.forge_link));
		stages.add(new DownloadModPack(config.parser.modpack_link));
		stages.add(new InstallModPack());
		stages.add(new DownloadServer(config.parser.server_pack_link));
		stages.add(new Finish());

		progressBar.setMaximum(stages.size() - 1);
	}

	private void addAutoPages(ConfigLoader config) {
		stages.add(1, new DownloadForge(config.parser.mc_version, config.parser.forge_version, config.parser.forge_link));
		autoDownloadInstall =  new AutoDownloadInstall(this, config.parser.modpack_link);
		stages.add(2, autoDownloadInstall);
		stages.add(new Finish());
		progressBar.setMaximum(stages.size() - 1);
	}

	private void removeAllPages() {
		stages.removeIf(page -> (page != welcome));
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
			progressBar.setMaximum(1);
		}
	}

}
