package mcmi.pages;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.*;

import components.ModernStyleUI;
import mcmi.Constant;
import mcmi.Main;
import mcmi.Screen;

public class DownloadForge extends JPanel {
	private static final long serialVersionUID = -8324611571879837373L;
	
	private JLabel tip;
	private JLabel tip2;
	private JButton link;
	private JLabel tip3;

	public DownloadForge(String mcVersion, String forgeVersion, String forgeLink) {
		this.setBackground(Constant.secondaryColor);
		this.setSize(Screen.PAGE_WIDTH, Screen.PAGE_HEIGHT);
		this.setLayout(null);
		tip = new JLabel("到以下網址下載" + forgeVersion + "版本的Forge Installer");
		tip.setFont(Constant.body);
		tip.setForeground(Constant.textColor);
		tip.setBounds(10, 0, 400, 40);

		link = new JButton();
		link.setText("<HTML><U>forge download link for for Minecraft Forge</HTML>");
		link.setUI(new ModernStyleUI());
		link.setForeground(Constant.linkColor);
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
		
		tip2 = new JLabel("開啟" + "forge-" + mcVersion + "-" + forgeVersion + "-installer.jar，再按下Install Client即可");
		tip2.setFont(Constant.body);
		tip2.setForeground(Constant.textColor);
		tip2.setBounds(10, 60, 400, 40);


		// TODO Didn't work as expected, need to be fixed
		/*tip3 = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/forge.png"))));
		tip3.setBounds(10, 90, 312, 224);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 90, 300, 100);
		scrollPane.setViewportView(tip3);*/

		add(tip);
		add(link);
		add(tip2);
		//add(scrollPane);
	}

}
