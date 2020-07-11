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

public class DownloadServer extends JPanel {
	private static final long serialVersionUID = 2100186923123627455L;
	
	private JLabel tip;
	private JButton link;
	private JLabel tip2;

	public DownloadServer(String serverLink) {
		this.setBackground(Color.CYAN);
		this.setSize(Screen.PAGE_WIDTH, Screen.PAGE_HEIGHT);
		this.setLayout(null);
		tip = new JLabel("如有需要先下載");
		tip.setFont(Main.font);
		tip.setBounds(10, 0, 400, 40);

		link = new JButton();
		link.setText("<HTML><FONT color=\"#000099\"><U>server</U></FONT></HTML>");
		link.setBounds(10, 40, 350, 20);
		link.setHorizontalAlignment(SwingConstants.LEFT);
		link.setBorderPainted(false);
		link.setOpaque(false);
		link.setBackground(Color.WHITE);
		link.setToolTipText(serverLink);
		link.addActionListener(e -> {
			if (Desktop.isDesktopSupported()) {
				try {
					try {
						Desktop.getDesktop().browse(new URI(serverLink));
					} catch (URISyntaxException e1) {
						e1.printStackTrace();
					}
				} catch (IOException e1) {

				}
			} else {

			}
		});
		
		tip2 = new JLabel("下載後解壓縮，點擊start.bat開Server");
		tip2.setFont(Main.font);
		tip2.setBounds(10, 60, 400, 40);

		add(tip);
		add(link);
		add(tip2);
	}

}
