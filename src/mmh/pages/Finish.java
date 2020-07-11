package mmh.pages;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

import mmh.Main;
import mmh.Screen;

public class Finish extends JPanel {
	private static final long serialVersionUID = 9202767316546599584L;
	
	private JLabel finish;
	private JLabel hint;
	private JLabel hint2;
	
	public Finish() {
		this.setBackground(Color.YELLOW);
		this.setSize(Screen.PAGE_WIDTH, Screen.PAGE_HEIGHT);
		this.setLayout(null);
		
		finish = new JLabel("大功告成!");
		finish.setFont(Main.font);
		finish.setBounds(10, 0, 400, 40);
		add(finish);
		
		hint = new JLabel("若你想自訂下載及安裝，請前往下一頁。");
		hint.setFont(Main.font);
		hint.setBounds(10, 30, 400, 40);
		add(hint);
		
		hint2 = new JLabel("否則可以關掉本程式。射射。");
		hint2.setFont(Main.font);
		hint2.setBounds(10, 60, 400, 40);
		add(hint2);
	}

}
