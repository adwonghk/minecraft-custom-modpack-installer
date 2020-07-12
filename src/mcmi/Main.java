package mcmi;

import java.awt.*;

import javax.swing.*;

import mcmi.config.ConfigLoader;

public class Main {

	public static final Version version = new Version("1.3");
	// "/Users/" + System.getProperty("user.name") + "/Library/Application Support/minecraft";
	public static String defualtMCLocation = "C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Roaming\\.minecraft";
	
	public Main() {
		JFrame frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/logo.png")));
		frame.setSize(450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setTitle("Minecraft 安裝模組小幫手 - by A:D v" + version.get());
		frame.setContentPane(new Screen(new ConfigLoader()));
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		System.out.println("Starting minecraft mod helper...");
		
		if (!isWindows()) {
			defualtMCLocation = "/Users/" + System.getProperty("user.name") + "/Library/Application Support/minecraft";
		}
		System.out.println("Working Directory = " + System.getProperty("user.dir"));
		
		new Main();
	}
	
	public static boolean isWindows() {
	    String os = System.getProperty("os.name").toLowerCase();
	    // windows
	    return (os.indexOf("win") >= 0);

	}

}
