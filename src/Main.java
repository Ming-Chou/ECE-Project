import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.awt.Window.Type;


public class Main extends JFrame {

	private JPanel contentPane;
	static String school_name;
	static String student_name;
	static String student_sex;
	static String student_ans;
	static String[] quit_ans;
	static int quit_start_to = 1;
    static long quit_long;
    static int quittint;
    static int sign = 1;
    static int i = 0;
    static Thread[] do_test;
    static int[] quit_start_to_a;
    static int[] quittint_a;
    static long[] quit_long_a;
    static String[] student_ans_a;
    static long[] student_taketime_a;

	/**
	 * Launch the application.
	 */
    public static void output_reslut() throws IOException {
    	
    	FileWriter fw = new FileWriter("exfile\\test result\\" + Main.school_name + "_" + Main.student_name + " test result.csv");
    	String Save = "Name,School,Student Sex\r\n";		//儲存資料字串
    	Save = Save + "　" + Main.student_name + " , " + Main.school_name + " , " + Main.student_sex + " \r\n";
    	Save = Save + "\r\n";
    	
    	Save = Save + "Question,";
    	for(int c=0; c<Main.student_ans_a.length; c++)
    	{
    		Save = Save + (c+1) + ",";
    	}
    	Save = Save + "Total\r\n";
    	
    	Save = Save + "Student Ans";
    	for(int c=0; c<Main.student_ans_a.length; c++)
    	{
    		Save = Save + ", " + Main.student_ans_a[c];
    	}
    	Save = Save + "\r\n";
    	
    	int right = 0;
    	Save = Save + "Score,";
    	for(int c=0; c<Main.student_ans_a.length; c++)
    	{
    		if(Main.student_ans_a[c].compareTo(Main.quit_ans[c])==0)
    		{
    			right++;
    			Save = Save + "1,";
    		}
    		else
    		{
    			Save = Save + "0,";
    		}
    	}
    	Save = Save + right + "/[" + Main.student_ans_a.length + "]\r\n";
    	
    	long time = 0;
    	Save = Save + "Use Time,";
    	for(int c=0; c<Main.student_ans_a.length; c++)
    	{
			Save = Save + ((float)Main.student_taketime_a[c]/1000) + "s,";
			time = time + Main.student_taketime_a[c];
    	}
    	Save = Save + ((float)time/1000) + "s\r\n";
    	
    	fw.write(Save);
		fw.close();
		End.main();
    }
    
	public static void main() {
		
		try {
			Main frame = new Main();
			frame.setVisible(true);
			
			final Random ran = new Random();
			
			File[] file = new File[150];
			int quit_amount = 1;
			for (int i=0; i < 50; i++)
            {
				file[i] = new File("exfile\\q" + (i+1) + ".wav");  
                if (!(file[i].exists()))
                {
                	quit_amount = i;
                    break;
                }
            }
			
			final String[] quit = new String[quit_amount];
			for (int i=0; i < quit_amount; i++)
            {
				quit[i] = "exfile\\q" + (i+1) + ".wav";
            }
			
            int[] quit_show = new int[quit_amount];
            for (int i = 0; i < quit_amount; i++)
            {
                quit_show[i] = 0;
            }
			
			quit_ans = new String[quit_amount];
            int c = 0;
            File ans = new File("exfile\\ans.txt");
            if (ans.exists())
            {
            	Scanner anssc = new Scanner(ans);
                while(anssc.hasNext())
                {
                    quit_ans[c] = anssc.nextLine();
                    c++;
                }
                anssc.close();
            }
            else
            {
            	final JFrame er=new JFrame("找不到解答檔案！程式將自動關閉");
				er.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				er.setLocationRelativeTo(null); 
				er.setSize(500,100);
				er.setVisible(true);
				er.setResizable(false);
				JButton BEnd2 = new JButton("確定");
				BEnd2.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						er.dispose();
						System.exit(0);
					}
				});
				er.getContentPane().add(BEnd2);
				try {
					Thread.sleep(Integer.MAX_VALUE); 
				} catch (InterruptedException e) {
				}
            }
            
            student_ans_a = new String[quit_amount];
            student_taketime_a = new long[quit_amount];
            
            Main.i = 0;
            do_test = new Thread[quit_amount];
            quit_start_to_a = new int[quit_amount];
            quittint_a = new int[quit_amount];
            quit_long_a = new long[quit_amount];
            while(Main.quit_start_to<=quit_amount)
            {
    			Main.quittint = ran.nextInt(quit_amount);
                while(quit_show[Main.quittint]==1)
                {
                	Main.quittint = ran.nextInt(quit_amount);
                }
                
                Main.quit_start_to_a[Main.quit_start_to-1] = Main.quit_start_to;
                Main.quittint_a[Main.quit_start_to-1] = Main.quittint+1;
                Main.quit_long_a[Main.quit_start_to-1] = file[Main.quittint].length();
                
				do_test[Main.quit_start_to-1] = new Thread(new Runnable(){
					public void run() {
						Test.main();
					}
				});
								
                quit_show[Main.quittint] = 1;
                Main.quit_start_to++;          	
            }
            
            do_test[0].start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * Create the frame.
	 */
	public Main() {
		setBackground(Color.WHITE);
		setUndecorated(true);
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1024, 768);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		
	}

}
