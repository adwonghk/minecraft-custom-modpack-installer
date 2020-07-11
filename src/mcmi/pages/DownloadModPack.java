package mcmi.pages;

import java.awt.Color;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import mcmi.Main;
import mcmi.Screen;

public class DownloadModPack extends JPanel {
	private static final long serialVersionUID = 2989513334065503873L;
	
	private JLabel tip;
	private JButton link;

	public DownloadModPack(String modpackLink) {
		this.setBackground(Color.CYAN);
		this.setSize(Screen.PAGE_WIDTH, Screen.PAGE_HEIGHT);
		this.setLayout(null);
		tip = new JLabel("到以下網址下載由Ad精心準備的mods <3");
		tip.setFont(Main.font);
		tip.setBounds(10, 0, 300, 40);

		link = new JButton();
		link.setText("<HTML><FONT color=\"#000099\"><U>modpack</U></FONT></HTML>");
		link.setBounds(10, 40, 350, 20);
		link.setHorizontalAlignment(SwingConstants.LEFT);
		link.setBorderPainted(false);
		link.setOpaque(false);
		link.setBackground(Color.WHITE);
		link.setToolTipText(modpackLink);
		link.addActionListener(e -> {
			if (Desktop.isDesktopSupported()) {
				try {
					try {
						Desktop.getDesktop().browse(new URI(modpackLink));
					} catch (URISyntaxException e1) {
						e1.printStackTrace();
					}
				} catch (IOException e1) {

				}
			} else {

			}
		});

		add(tip);
		add(link);
	}

}
