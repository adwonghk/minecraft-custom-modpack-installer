package mcmi.pages;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

import mcmi.Main;
import mcmi.Screen;

public class Welcome extends JPanel {
	private static final long serialVersionUID = 7994513795896060482L;
	
	private JLabel welcome;
	private JLabel welcome2;
	
	public Welcome() {
		this.setBackground(Color.YELLOW);
		this.setSize(Screen.PAGE_WIDTH, Screen.PAGE_HEIGHT);
		this.setLayout(null);
		welcome = new JLabel("歡迎使用Minecraft安裝模組小幫手");
		welcome.setFont(Main.font);
		welcome.setBounds(10, 0, 400, 40);
		add(welcome);
		
		welcome2 = new JLabel("本程式由Ad開發 <3");
		welcome2.setFont(Main.font);
		welcome2.setBounds(10, 30, 400, 40);
		add(welcome2);
	}

}
