import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.SwingConstants;
import javax.swing.ImageIcon;

public class Wait extends JFrame {
	public Wait() {
		setTitle("\u4F11\u606F\u4E00\u4E0B");
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(dim.width/2-getWidth()/2-225, dim.height/2-getHeight()/2-150, 450, 300);
		getContentPane().setLayout(null);
		
		JButton button = new JButton("\u4E0B\u4E00\u984C");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Main.do_test[Main.i].start();
				dispose();
			}
		});
		button.setFont(new Font("微軟正黑體", Font.PLAIN, 16));
		button.setBounds(117, 176, 200, 50);
		getContentPane().add(button);
		
		JLabel label = new JLabel("\u6E96\u5099\u597D\u4E86\u8ACB\u6309\u4E0B\u6309\u9215");
		label.setFont(new Font("微軟正黑體", Font.PLAIN, 28));
		label.setBounds(90, 57, 255, 70);
		getContentPane().add(label);
	}

	public static void main() {
		// TODO Auto-generated method stub
		final Wait frame = new Wait();
		frame.setVisible(true);
		frame.setAlwaysOnTop(true);
	}
}
