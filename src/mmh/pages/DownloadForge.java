package mmh.pages;

import java.awt.Color;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import mmh.Main;
import mmh.Screen;

public class DownloadForge extends JPanel {
	private static final long serialVersionUID = -8324611571879837373L;
	
	private JLabel tip;
	private JLabel tip2;
	private JButton link;

	public DownloadForge(String mcVersion, String forgeVersion, String forgeLink) {
		this.setBackground(Color.CYAN);
		this.setSize(Screen.PAGE_WIDTH, Screen.PAGE_HEIGHT);
		this.setLayout(null);
		tip = new JLabel("到以下網址下載" + forgeVersion + "版本的Forge Installer");
		tip.setFont(Main.font);
		tip.setBounds(10, 0, 400, 40);

		link = new JButton();
		link.setText("<HTML><FONT color=\"#000099\"><U>forge download link for for Minecraft Forge</U></FONT></HTML>");
		link.setBounds(10, 40, 450, 20);
		link.setHorizontalAlignment(SwingConstants.LEFT);
		link.setBorderPainted(false);
		link.setOpaque(false);
		link.setBackground(Color.WHITE);
		link.setToolTipText(forgeLink);
		link.addActionListener(e -> {
			if (Desktop.isDesktopSupported()) {
				try {
					try {
						Desktop.getDesktop().browse(new URI(forgeLink));
					} catch (URISyntaxException e1) {
						e1.printStackTrace();
					}
				} catch (IOException e1) {

				}
			} else {

			}
		});
		
		tip2 = new JLabel("開啟" + "forge-" + mcVersion + "-" + forgeVersion + "-installer.jar，Install Client即可");
		tip2.setFont(Main.font);
		tip2.setBounds(10, 60, 400, 40);

		add(tip);
		add(link);
		add(tip2);
	}

}
