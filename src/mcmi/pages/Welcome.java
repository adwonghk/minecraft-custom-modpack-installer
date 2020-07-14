package mcmi.pages;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import com.sun.org.apache.bcel.internal.Const;
import components.ModernStyleUI;
import components.RoundedBorder;
import mcmi.Constant;
import mcmi.Main;
import mcmi.Screen;

public class Welcome extends JPanel {
	private static final long serialVersionUID = 7994513795896060482L;
	
	private JLabel welcome;
	private JLabel welcome2;

	public JRadioButton rbChoice1;
	public JRadioButton rbChoice2;
	public ButtonGroup btnGrp = new ButtonGroup();

	private JTextArea path;
	private JButton btnChooseFolder;
	private JFileChooser chooser;
	
	public Welcome() {
		this.setBackground(Constant.secondaryColor);
		this.setSize(Screen.PAGE_WIDTH, Screen.PAGE_HEIGHT);
		this.setLayout(null);
		this.setFocusable(true);

		welcome = new JLabel("歡迎使用Minecraft安裝模組小幫手");
		welcome.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/logo.png"))));
		welcome.setIconTextGap(10);
		welcome.setFont(Constant.body);
		welcome.setForeground(Constant.textColor);
		welcome.setBounds(10, 0, 400, 40);
		add(welcome);
		
		welcome2 = new JLabel("本程式由Ad開發 <3");
		welcome2.setFont(Constant.body);
		welcome2.setForeground(Constant.textColor);
		welcome2.setBounds(10, 30, 400, 40);
		add(welcome2);

		rbChoice1 = new JRadioButton("自行安裝Forge + 自動安裝modpack(建議使用)");
		rbChoice1.setFont(Constant.body);
		rbChoice1.setForeground(Constant.textColor);
		rbChoice1.setBackground(Constant.secondaryColor);
		rbChoice1.addActionListener(e -> {

		});
		btnGrp.add(rbChoice1);
		rbChoice1.setBounds(10, 70, 400, 40);
		add(rbChoice1);

		rbChoice2 = new JRadioButton("完全自定義下載");
		rbChoice2.setFont(Constant.body);
		rbChoice2.setForeground(Constant.textColor);
		rbChoice2.setBackground(Constant.secondaryColor);
		rbChoice2.addActionListener(e -> {

		});
		btnGrp.add(rbChoice2);
		rbChoice2.setBounds(10, 100, 400, 40);
		add(rbChoice2);

		path = new JTextArea();
		path.setText(Main.defualtMCLocation);
		path.setEditable(false);
		path.setFont(Constant.body);
		path.setBackground(Constant.mainColor);
		path.setForeground(Constant.textColor);
		path.setBounds(10, 140, 300, 22);
		add(path);

		btnChooseFolder = new JButton("...");
		btnChooseFolder.addActionListener(e -> {
			chooser = new JFileChooser();
			chooser.setCurrentDirectory(new java.io.File("."));
			chooser.setDialogTitle("Choose Your Minecraft Location");
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			//
			// disable the "All files" option.
			//
			chooser.setAcceptAllFileFilterUsed(false);
			chooser.setFileHidingEnabled(false);
			//
			if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
				System.out.println("[Info] - getCurrentDirectory(): " + chooser.getCurrentDirectory());
				System.out.println("[Info] - getSelectedFile() : " + chooser.getSelectedFile());
				Main.defualtMCLocation = chooser.getSelectedFile().toPath().toString();
				path.setText(Main.defualtMCLocation);
			}
			else {
				System.out.println("[Info] - No Folder Selection");
			}
		});
		btnChooseFolder.setBounds(320, 140, 40, 20);
		btnChooseFolder.setBorder(new RoundedBorder(5, Constant.btnBorderColor, 1));
		btnChooseFolder.setBackground(Constant.secondaryColor);
		btnChooseFolder.setFont(Constant.body);
		btnChooseFolder.setUI(new ModernStyleUI());
		btnChooseFolder.setFocusPainted(false);
		btnChooseFolder.setForeground(Constant.textColor);
		add(btnChooseFolder);
	}

}
