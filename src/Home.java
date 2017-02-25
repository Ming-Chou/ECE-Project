import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JRadioButton;

import org.eclipse.wb.swing.FocusTraversalOnArray;

import java.awt.Component;
import java.awt.SystemColor;


public class Home extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField school;
	private JRadioButton sex_b;
	private JRadioButton sex_g;
	private JTextField textField_1;
	private JCheckBox need_wait_chk;
	private JCheckBox need_ran_chk;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Home frame = new Home();
					frame.setVisible(true);
					Image icon = Toolkit.getDefaultToolkit().getImage("exfile\\usr\\icon.gif");
					frame.setIconImage(icon);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Home() {
		File ans = new File("exfile\\ans.txt");
		if (!(ans.exists()))
        {
			final JFrame er = new JFrame("找不到解答檔案！程式將自動關閉！");
			er.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			er.setLocationRelativeTo(null);
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			er.setBounds(dim.width/2-getWidth()/2-250, dim.height/2-getHeight()/2-50, 500, 100);
			er.setVisible(true);
			er.setResizable(false);
			Image icon = Toolkit.getDefaultToolkit().getImage("exfile\\usr\\icon.gif");
			er.setIconImage(icon);
			JButton BEnd2 = new JButton("確定");
			BEnd2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					er.dispose();
					System.exit(0);
				}
			});
			er.getContentPane().add(BEnd2);
        }
		else
		{
			setTitle("小測驗");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			setBounds(dim.width/2-getWidth()/2-512, dim.height/2-getHeight()/2-384, 1024, 768);
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(contentPane);
			contentPane.setLayout(null);
					
			JLabel label = new JLabel("姓名 : ");
			label.setBounds(473, 575, 92, 32);
			label.setFont(new Font("標楷體", Font.BOLD, 18));
			label.setForeground(new Color(0, 0, 0));
			contentPane.add(label);
			
			JLabel label_3 = new JLabel("\u73ED\u7D1A : ");
			label_3.setBounds(473, 533, 92, 32);
			label_3.setForeground(Color.BLACK);
			label_3.setFont(new Font("標楷體", Font.BOLD, 18));
			contentPane.add(label_3);
			
			textField = new JTextField();
			textField.setBounds(565, 577, 179, 29);
			textField.setFont(new Font("標楷體", Font.PLAIN, 18));
			contentPane.add(textField);
			textField.setColumns(10);
			
			textField_1 = new JTextField();
			textField_1.setBounds(565, 535, 179, 29);
			textField_1.setFont(new Font("標楷體", Font.PLAIN, 18));
			textField_1.setColumns(10);
			contentPane.add(textField_1);
						
			school = new JTextField();
			school.setBounds(565, 491, 324, 32);
			school.setFont(new Font("標楷體", Font.PLAIN, 18));
			contentPane.add(school);
			school.setColumns(10);
			
			JLabel label_1 = new JLabel("\u5B78\u6821 : ");
			label_1.setBounds(473, 491, 92, 32);
			label_1.setForeground(new Color(0, 0, 0));
			label_1.setFont(new Font("標楷體", Font.BOLD, 18));
			contentPane.add(label_1);
			
			JLabel label_2 = new JLabel("\u6027\u5225 : ");
			label_2.setBounds(473, 617, 92, 32);
			label_2.setForeground(new Color(0, 0, 0));
			label_2.setFont(new Font("標楷體", Font.BOLD, 18));
			contentPane.add(label_2);
			
			sex_b = new JRadioButton("\u7537");
			sex_b.setBounds(565, 624, 75, 23);
			contentPane.add(sex_b);
			
			sex_g = new JRadioButton("\u5973");
			sex_g.setBounds(669, 624, 75, 23);
			contentPane.add(sex_g);
			
			ButtonGroup sex = new ButtonGroup();
			sex.add(sex_b);
			sex.add(sex_g);
			
			ImageIcon icon = new ImageIcon("exfile\\usr\\home-1.jpg");
			Image temp = icon.getImage().getScaledInstance(1008, 730, icon.getImage().SCALE_DEFAULT); 
			icon = new ImageIcon(temp);
			JLabel back = new JLabel("");
			back.setBounds(0, 0, 1008, 730);
			back.setIcon(icon);
			back.setVisible(true);
			
			JPanel panel = new JPanel();
			panel.setBounds(460, 465, 499, 208);
			panel.setBackground(new Color(100, 149, 237));
			contentPane.add(panel);
			panel.setLayout(null);
			
			need_ran_chk = new JCheckBox("是否隨機出題");
			need_ran_chk.setBounds(342, 179, 150, 23);
			panel.add(need_ran_chk);
			
			need_wait_chk = new JCheckBox("題目間是否需要等待");
			need_wait_chk.setBounds(342, 148, 150, 23);
			panel.add(need_wait_chk);
			
			JButton btnStart = new JButton("Start");
			btnStart.setBounds(342, 116, 87, 23);
			panel.add(btnStart);
			btnStart.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					Main.student_name = textField.getText();
					Main.student_class = textField_1.getText();
					Main.school_name = school.getText();
					if(sex_b.isSelected())
					{
						Main.student_sex = "男";
					}
					if(sex_g.isSelected())
					{
						Main.student_sex = "女";
					}
					if(need_wait_chk.isSelected())
					{
						Main.need_wait = true;
					}
					if(need_ran_chk.isSelected())
					{
						Main.ran_quit = true;
					}
					new Thread(new Runnable(){
	        			public void run() {
	        				Main.main();
	        			}
	        		}).start();
					dispose();				
				}
			});
			btnStart.setFont(new Font("Tahoma", Font.PLAIN, 18));
			contentPane.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{back, label, textField, btnStart, school, label_1, label_2, sex_b, sex_g}));
			contentPane.add(back);
		}
	}
}
