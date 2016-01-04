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
			final JFrame er = new JFrame("找不到解答檔案！程式將自動關閉");
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
			label.setFont(new Font("標楷體", Font.BOLD, 18));
			label.setForeground(new Color(0, 0, 0));
			label.setBounds(473, 544, 92, 32);
			contentPane.add(label);
			
			textField = new JTextField();
			textField.setFont(new Font("標楷體", Font.PLAIN, 18));
			textField.setBounds(565, 546, 179, 29);
			contentPane.add(textField);
			textField.setColumns(10);
			
			JButton btnStart = new JButton("Start");
			btnStart.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					Main.student_name = textField.getText();
					Main.school_name = school.getText();
					if(sex_b.isSelected())
					{
						Main.student_sex = "男";
					}
					if(sex_g.isSelected())
					{
						Main.student_sex = "女";
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
			btnStart.setBounds(802, 598, 87, 23);
			contentPane.add(btnStart);
			
			school = new JTextField();
			school.setFont(new Font("標楷體", Font.PLAIN, 18));
			school.setBounds(565, 491, 324, 32);
			contentPane.add(school);
			school.setColumns(10);
			
			JLabel label_1 = new JLabel("\u5B78\u6821 : ");
			label_1.setForeground(new Color(0, 0, 0));
			label_1.setFont(new Font("標楷體", Font.BOLD, 18));
			label_1.setBounds(473, 491, 92, 32);
			contentPane.add(label_1);
			
			JLabel label_2 = new JLabel("\u6027\u5225 : ");
			label_2.setForeground(new Color(0, 0, 0));
			label_2.setFont(new Font("標楷體", Font.BOLD, 18));
			label_2.setBounds(473, 594, 92, 32);
			contentPane.add(label_2);
			
			sex_b = new JRadioButton("\u7537");
			sex_b.setBounds(565, 601, 75, 23);
			contentPane.add(sex_b);
			
			sex_g = new JRadioButton("\u5973");
			sex_g.setBounds(669, 601, 75, 23);
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
			panel.setBackground(new Color(100, 149, 237));
			panel.setBounds(461, 478, 447, 159);
			contentPane.add(panel);
			contentPane.add(back);
			contentPane.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{back, label, textField, btnStart, school, label_1, label_2, sex_b, sex_g}));
		}
	}
}
