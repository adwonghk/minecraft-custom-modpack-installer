package mcmi.pages;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

import mcmi.Constant;
import mcmi.Main;
import mcmi.Screen;

public class Finish extends JPanel {
	private static final long serialVersionUID = 9202767316546599584L;
	
	private JLabel finish;
	private JLabel hint;
	private JLabel hint2;
	
	public Finish() {
		this.setBackground(Constant.secondaryColor);
		this.setSize(Screen.PAGE_WIDTH, Screen.PAGE_HEIGHT);
		this.setLayout(null);
		
		finish = new JLabel("大功告成! ");
		finish.setFont(Constant.body);
		finish.setForeground(Constant.textColor);
		finish.setBounds(10, 0, 400, 40);
		add(finish);
		
		hint = new JLabel("感謝使用本軟件，如有任何問題，請聯繫Ad (adwonghk@gmail.com)");
		hint.setFont(Constant.body);
		hint.setForeground(Constant.textColor);
		hint.setBounds(10, 30, 400, 40);
		add(hint);
		
		hint2 = new JLabel("否則可以關掉本程式。射射。");
		hint2.setFont(Constant.body);
		hint2.setForeground(Constant.textColor);
		hint2.setBounds(10, 60, 400, 40);
		//add(hint2);
	}

}
