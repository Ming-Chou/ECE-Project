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


public class Test extends JFrame {
	
	private JPanel contentPane;
	static int quit_num;
	static String quit;
	static String apic;
	static String bpic;
	static String cpic;
	static long quit_long;
	static int quittint;
	
	static long startTime;
	static long endTime;
	
	/**
	 * Launch the application.
	 */
	public static void main() {
		
			try {
				Test.quit_num = Main.quit_start_to_a[Main.i];  
				Test.apic = "exfile\\q"+ (Main.quittint_a[Main.i]) + "a.jpg";
				Test.bpic = "exfile\\q"+ (Main.quittint_a[Main.i]) + "b.jpg";
				Test.cpic = "exfile\\q"+ (Main.quittint_a[Main.i]) + "c.jpg";
				Test.quit = "exfile\\q" + (Main.quittint_a[Main.i]) + ".wav";
				Test.quit_long = Main.quit_long_a[Main.i];
				Test.quittint = Main.quittint_a[Main.i];
				
				File afile = new File("exfile\\q"+ (Main.quittint_a[Main.i]) + "a.jpg");
				if(!(afile.exists()))
				{
					Test.apic = "exfile\\usr\\1.jpg";
				}
				File bfile = new File("exfile\\q"+ (Main.quittint_a[Main.i]) + "b.jpg");
				if(!(bfile.exists()))
				{
					Test.bpic = "exfile\\usr\\2.jpg";
				}
				File cfile = new File("exfile\\q"+ (Main.quittint_a[Main.i]) + "c.jpg");
				if(!(cfile.exists()))
				{
					Test.cpic = "exfile\\usr\\3.jpg";
				}
				
				
				final Test frame = new Test();
				frame.setVisible(true);
				
				JButton button_A = new JButton("");
				button_A.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						Main.student_ans_a[Test.quittint-1] = "A";
						Test.endTime = System.currentTimeMillis();
						Main.student_taketime_a[Test.quittint-1] = Test.endTime - Test.startTime; 
						Main.i++;
						if(Main.i<Main.do_test.length)
						{
							Main.do_test[Main.i].start();
						}
						else
						{
							try {
								Main.output_reslut();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						frame.dispose();
					}
				});
				button_A.setBounds(10, 339, 310, 310);
				ImageIcon icon_A = new ImageIcon(Test.apic);
				Image temp_A = icon_A.getImage().getScaledInstance(310, 310, icon_A.getImage().SCALE_DEFAULT); 
				icon_A = new ImageIcon(temp_A);
				button_A.setIcon(icon_A);
				button_A.setVisible(false);
				frame.getContentPane().add(button_A);
				
				JButton button_B = new JButton("");
				button_B.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						Main.student_ans_a[Test.quittint-1] = "B";
						endTime = System.currentTimeMillis();
						Main.student_taketime_a[Test.quittint-1] = Test.endTime - Test.startTime;
						Main.i++;
						if(Main.i<Main.do_test.length)
						{
							Main.do_test[Main.i].start();
						}
						else
						{
							try {
								Main.output_reslut();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						frame.dispose();
					}
				});
				button_B.setBounds(349, 339, 310, 310);
				ImageIcon icon_B = new ImageIcon(Test.bpic);
				Image temp_B = icon_B.getImage().getScaledInstance(310, 310, icon_B.getImage().SCALE_DEFAULT); 
				icon_B = new ImageIcon(temp_B);
				button_B.setIcon(icon_B);
				button_B.setVisible(false);
				frame.getContentPane().add(button_B);
				
				JButton button_C = new JButton("");
				button_C.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						Main.student_ans_a[Test.quittint-1] = "C";
						endTime = System.currentTimeMillis();
						Main.student_taketime_a[Test.quittint-1] = Test.endTime - Test.startTime;
						Main.i++;
						if(Main.i<Main.do_test.length)
						{
							Main.do_test[Main.i].start();
						}
						else
						{
							try {
								Main.output_reslut();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						frame.dispose();
					}
				});
				button_C.setBounds(688, 339, 310, 310);
				ImageIcon icon_C = new ImageIcon(Test.cpic);
				Image temp_C = icon_C.getImage().getScaledInstance(310, 310, icon_C.getImage().SCALE_DEFAULT); 
				icon_C = new ImageIcon(temp_C);
				button_C.setIcon(icon_C);
				button_C.setVisible(false);
				frame.getContentPane().add(button_C);
				
				File quitfile = new File(Test.quit);
				try {
					AudioInputStream quitaudio = AudioSystem.getAudioInputStream(quitfile);
					DataLine.Info quitinfo = new DataLine.Info(Clip.class, quitaudio.getFormat());
					Clip quitplayer = (Clip)AudioSystem.getLine(quitinfo);
					quitplayer.open(quitaudio);
					quitplayer.start();
				} catch (UnsupportedAudioFileException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (LineUnavailableException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				try {
					Thread.sleep((int)(Test.quit_long/210)); 
				} catch (InterruptedException e) {
				}
				button_A.setVisible(true);
				button_B.setVisible(true);
				button_C.setVisible(true);
				
				startTime = System.currentTimeMillis();
				
				Thread.sleep(Integer.MAX_VALUE); 		
										
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

	/**
	 * Create the frame.
	 */
	public Test() {
		setTitle("第  "+quit_num+"  題");
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(dim.width/2-getWidth()/2-512, dim.height/2-getHeight()/2-384, 1024, 768);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
			
		JLabel label = new JLabel("第      題");
		label.setFont(new Font("標楷體", Font.BOLD, 28));
		label.setBounds(42, 51, 253, 46);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel(""+quit_num);
		label_1.setFont(new Font("標楷體", Font.PLAIN, 28));
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setBounds(59, 52, 126, 46);
		contentPane.add(label_1);
	}
}
