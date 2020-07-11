package mcmi.pages;

import java.awt.Color;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import mcmi.Main;
import mcmi.Screen;

public class InstallModPack extends JPanel {
	private static final long serialVersionUID = 7924515181475352425L;
	
	private JLabel tip;
	private JButton link;
	private JLabel tip2;

	public InstallModPack() {
		this.setBackground(Color.CYAN);
		this.setSize(Screen.PAGE_WIDTH, Screen.PAGE_HEIGHT);
		this.setLayout(null);
		tip = new JLabel("打開你minecraft所安裝的位置");
		tip.setFont(Main.font);
		tip.setBounds(10, 0, 300, 40);

		link = new JButton();
		link.setText("<HTML><FONT color=\"#000099\"><U>.minecraft (windows預設為%appdata%\\.minecraft)<br>(mac預設為~/Library/Application Support/minecraft)</U></FONT></HTML>");
		link.setBounds(10, 40, 450, 40);
		link.setHorizontalAlignment(SwingConstants.LEFT);
		link.setBorderPainted(false);
		link.setOpaque(false);
		link.setBackground(Color.WHITE);
		link.setToolTipText("");
		link.addActionListener(e -> {
			try {
				if (isWindows()) {
					Runtime.getRuntime().exec("Explorer.exe C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Roaming\\.minecraft" );
				} else {
					System.out.println("--Trying to open ~/Library/Application Support/minecraft");
					String path = "/Users/" + System.getProperty("user.name") + "/Library/Application Support/minecraft";
					Desktop.getDesktop().open(new File(path));
					if (!new File(path + "/mods").exists()) {
						new File(path + "/mods").mkdirs();
					}
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		
		tip2 = new JLabel("把下載好的mods.zip解壓縮並放在mods資料夾");
		tip2.setFont(Main.font);
		tip2.setBounds(10, 75, 400, 40);

		add(tip);
		add(link);
		add(tip2);
	}
	
	public static boolean isWindows() {
	    String os = System.getProperty("os.name").toLowerCase();
	    // windows
	    return (os.indexOf("win") >= 0);

	}

}
