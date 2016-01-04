import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridBagLayout;

import javax.swing.JButton;

import java.awt.GridBagConstraints;
import java.awt.GridLayout;

import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.SwingConstants;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class End extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					End frame = new End();
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
	public End() {
		setTitle("´úÅçµ²§ô");
		getToolkit();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(dim.width/2-getWidth()/2-225, dim.height/2-getHeight()/2-150, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
				
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnExit.setBounds(10, 210, 140, 42);
		contentPane.add(btnExit);
		
		ImageIcon icon = new ImageIcon("exfile\\usr\\End.jpg");
		Image temp = icon.getImage().getScaledInstance(434, 262, icon.getImage().SCALE_DEFAULT); 
		icon = new ImageIcon(temp);
		JLabel back = new JLabel("");
		back.setBounds(0, 0, 434, 262);
		back.setIcon(icon);
		back.setVisible(true);
		contentPane.add(back);
		
	}
}
