import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;


public class MakeSure extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MakeSure frame = new MakeSure();
					frame.setVisible(true);
					frame.setAlwaysOnTop(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MakeSure() {
		setTitle("警告！");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(dim.width/2-getWidth()/2-225, dim.height/2-getHeight()/2-150, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("確定要離開嗎？");
		label.setFont(new Font("標楷體", Font.BOLD, 30));
		label.setBounds(84, 32, 369, 106);
		contentPane.add(label);
		
		JButton yes = new JButton("是");
		yes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		yes.setBounds(31, 179, 128, 56);
		contentPane.add(yes);
		
		JButton no = new JButton("否");
		no.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		no.setBounds(272, 179, 128, 56);
		contentPane.add(no);
		
		JLabel label_1 = new JLabel("");
		label_1.setIcon(new ImageIcon(MakeSure.class.getResource("/javax/swing/plaf/metal/icons/Warn.gif")));
		label_1.setBounds(29, 52, 56, 64);
		contentPane.add(label_1);
	}
}
