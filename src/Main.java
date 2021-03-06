import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
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
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.awt.Window.Type;

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

import javax.swing.JCheckBox;

import org.eclipse.wb.swing.FocusTraversalOnArray;

import java.awt.Component;
import java.awt.SystemColor;


public class Main extends JFrame {

	private JPanel contentPane;
	static String school_name;
	static String student_class;
	static String student_name;
	static String student_sex;
	static String student_ans;
	static String[] quit_ans;
	static int[] quit_show;
	static int quit_amount = 1;
	static int quit_start_to = 1;
    static int quittint;
    static int sign = 1;
    static int i;
    static Thread[] do_test;
    static int[] quit_start_to_a;
    static int[] quittint_a;
    static long[] quit_long_a;
    static String[] student_ans_a;
    static long[] student_taketime_a;
    static String[] quit;
    static boolean need_wait;
    static boolean ran_quit;

	/**
	 * Launch the application.
	 */
    public static void output_reslut() throws IOException {
    	
    	FileWriter fw = new FileWriter("exfile\\test result\\" + Main.school_name + "_" + Main.student_name + " test result.csv");
    	String Save = "Name,School,Class,Student Sex\r\n";		//儲存資料字串
    	Save = Save + "　" + Main.student_name + " , " + Main.school_name + " , " + Main.student_class + " , " + Main.student_sex + " \r\n";
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
   			Save = Save + "," + Main.student_ans_a[c];
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
    }
    
    public static void load() throws IOException
    {
    	char data[] = new char[1000];
    	FileReader fr = new FileReader("exfile\\test result\\" + Main.school_name + "_" + Main.student_name + " test result.csv"); //存檔路徑
		int num = fr.read(data);
		String Load_sig = new String(data,0,num);		//儲存資料字串
		String Load[] = Load_sig.split("\r\n");
		
		String[] quit_num = Load[3].split(",");
		String[] student_ans = Load[4].split(",");
		//String[] score = Load[5].split(",");
		String[] use_time = Load[6].split(",");
		
		if(quit_num.length-2 != Main.quit_amount)
		{
			final JFrame er = new JFrame("題目數量不相等！程式將自動關閉！");
			er.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			er.setLocationRelativeTo(null);
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			er.setBounds(dim.width/2-250, dim.height/2-50, 500, 100);
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
		
		int done_num = 0;
		for(int c=0; c<Main.student_ans_a.length; c++)
		{
			Main.student_ans_a[c] = student_ans[c+1];
			if(student_ans[c+1].compareTo(" ")==0)
			{
				Main.quit_show[c] = 0;
			}
			else
			{
				Main.quit_show[c] = 1;
				done_num++;
			}
		}
		Main.quit_start_to = done_num+1;
		Main.i = done_num;
		
		if(done_num==Main.quit_amount)
		{
			End.main();
			fr.close();
			return;
		}
		
		String[] temp;
		for(int c=0; c<Main.student_taketime_a.length; c++)
		{
			temp = use_time[c+1].split("s");
			temp = temp[0].split("\\.");
			if(temp[1].length()==2)
			{
				Main.student_taketime_a[c] = Long.parseLong(temp[0]+temp[1]+"0");
			}
			else
			{
				Main.student_taketime_a[c] = Long.parseLong(temp[0]+temp[1]);
			}
		}
		
		fr.close();
    }
    
	public static void main() {
		
		try {
			Main frame = new Main();
			frame.setVisible(true);
			
			final Random ran = new Random();
			
			File[] file = new File[500];
			for (int i=0; i < file.length; i++)
            {
				file[i] = new File("exfile\\q" + (i+1) + ".wav");  
                if (!(file[i].exists()))
                {
                	Main.quit_amount = i;
                    break;
                }
                if (i==file.length-1 && (file[i].exists()))
                {
                	Main.quit_amount = i+1;
                }
            }
			
			quit = new String[quit_amount];
			for (int i=0; i < Main.quit_amount; i++)
            {
				quit[i] = "exfile\\q" + (i+1) + ".wav";
            }
			
            quit_show = new int[Main.quit_amount];
            for (int i = 0; i < Main.quit_amount; i++)
            {
                quit_show[i] = 0;
            }
			
			quit_ans = new String[Main.quit_amount];
            int c = 0;
            File ans = new File("exfile\\ans.txt");
            int temp_c = 0;
            if (ans.exists())
            {
            	Scanner anssc = new Scanner(ans);
                while(anssc.hasNext())
                {
                    quit_ans[c] = " " + anssc.nextLine();
                    c++;
                    temp_c++;
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
            if(temp_c!=Main.quit_amount)
            {
            	final JFrame er = new JFrame("答檔數量與題目數量不相符！程式將自動關閉！");
    			er.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    			er.setLocationRelativeTo(null);
    			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
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
            
            student_ans_a = new String[Main.quit_amount];
            student_taketime_a = new long[Main.quit_amount];
            for(int c1=0; c1<student_ans_a.length; c1++)
            {
            	student_ans_a[c1] = " ";
            }
                        
            Main.i = 0;
            do_test = new Thread[Main.quit_amount];
            quit_start_to_a = new int[Main.quit_amount];
            quittint_a = new int[Main.quit_amount];
            quit_long_a = new long[Main.quit_amount];
            
            File old_file = new File("exfile\\test result\\" + Main.school_name + "_" + Main.student_name + " test result.csv");
            if(old_file.exists())
            {
            	load();
            }
            
            if(ran_quit==true)
    		{
    			while(Main.quit_start_to<=Main.quit_amount)
    	        {
    	 			Main.quittint = ran.nextInt(Main.quit_amount);
    	             
    				while(quit_show[Main.quittint]==1)
    	            {
    	             	Main.quittint = ran.nextInt(Main.quit_amount);
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
    		}
    		else
    		{
    			Main.quittint = 0;
    			while(Main.quit_start_to<=Main.quit_amount)
    			{
    				while(quit_show[Main.quittint]==1)
    				{
    					Main.quittint++;
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
    		}
            
            do_test[Main.i].start();
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
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		JButton btnStart = new JButton("Warning　　　緊急結束程式　　　Warning");
		btnStart.setBackground(Color.RED);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		btnStart.setBounds(0, 0, dim.width, 30);
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Main.output_reslut();
					MakeSure.main(null);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			}
		});
		contentPane.add(btnStart);
	}
}
