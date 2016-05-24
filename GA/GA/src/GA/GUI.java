package GA;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
class GUI extends JFrame {

	private JPanel contentPane;
	JTextField textField;
	JTextField textField_1;
	JTextField textField_2;
	JTextField textField_3;
	JTextField textField_4;
	JTextField textField_5;
	JTextArea text;
	JButton btn;
	JButton btn1;
	/**
	 * Launch t
	}

	/**
	 * Create the frame.
	 */
	
	boolean locksleep=false;
	
	public GUI() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 423, 232);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.WEST);
		panel.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lblNewLabel = new JLabel("迭代次數：");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel);
		
		textField = new JTextField("50");
		panel.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("交配率：");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_1);
		
		textField_1 = new JTextField("0.5");
		panel.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("變異率：");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_2);
		
		textField_2 = new JTextField("0.5");
		panel.add(textField_2);
		textField_2.setColumns(10);
		
		btn = new JButton("Run");
		btn.setBackground(Color.GRAY);
		panel.add(btn);
		
		btn1 = new JButton("Cancel");
		btn1.setBackground(Color.GRAY);
		panel.add(btn1);
		
		JPanel panel_1 = new JPanel();
		panel.setLayout(new GridLayout(0, 2, 0, 0));
		contentPane.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new GridLayout(0, 1, 0, 0));
		
		text = new JTextArea("",9,9);
		text.setEditable(false);
		text.setLineWrap(true);
		JScrollPane jsp = new JScrollPane(text);
		text.setEnabled(false);
		panel_1.add(jsp);
		
		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2, BorderLayout.NORTH);
		panel_2.setLayout(new GridLayout(0, 1, 0, 0));
		
		
		textField_3 = new JTextField();
		textField_3.setHorizontalAlignment(SwingConstants.CENTER);
		textField_3.setEditable(false);
		panel_2.add(textField_3);
		textField_3.setColumns(35);
		
		textField_4 = new JTextField();
		textField_4.setEditable(false);
		panel_2.add(textField_4);
		textField_4.setColumns(9);
		
		textField_5 = new JTextField();
		textField_5.setEditable(false);
		panel_2.add(textField_5);
		textField_5.setColumns(9);
		Lis lis = new Lis();
	    Mus mus = new Mus();
		
	    btn.addActionListener(lis);
	    btn1.addActionListener(lis);
	    
	    textField_4.addMouseListener(mus);
	    textField_5.addMouseListener(mus);
	    textField_3.addMouseListener(mus);
	    
	}
	    
	   
	private class Mus implements MouseListener {
			
		void callthread (JTextField obj){
			
			new Thread(new Runnable(){  //呼叫原聲
				@SuppressWarnings("static-access")
				@Override
				public void run() {
					
					System.out.println("原聲前");	
					new generate_sound().player.play( new musicMain().poem); //原聲
					Thread.interrupted();
					}}).start();
				System.out.println("原聲後");	
					
			try 
					{
						Thread.sleep(200);		
					} 				
			catch (InterruptedException e1)
					{			
						e1.printStackTrace();
					}			
					
			
			new Thread(new Runnable(){ //和聲部
				@Override	
				public void run() {	
					System.out.println("fucking");	
					new generate_sound().player.play((obj).getText()); //改過的
						}}).start();	
				System.out.println("machine~~~~~~~~~~~~~~~~~~~~~~~~~~~~");	
		}
    	
		@Override
		public void mouseClicked(MouseEvent e) {
		
			if(e.getSource()==textField_3){
				callthread(textField_3);
			}
			if(e.getSource()==textField_4){
				callthread(textField_4);
			}
			if(e.getSource()==textField_5){
				callthread(textField_5);
			}
		}
		@Override
		public void mousePressed(MouseEvent e) {}
		@Override
		public void mouseReleased(MouseEvent e) {}
		@Override
		public void mouseEntered(MouseEvent e) {}
		@Override
		public void mouseExited(MouseEvent e) {}
		}

	private class Lis implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
				// TODO Auto-generated method stub
				if (arg0.getSource() == btn){
					run();

				}
				if (arg0.getSource() == btn1){
					System.exit(0);
				}
			}
			
		}
	public void run(){
		int ITERA_CNT = Integer.parseInt(textField.getText());
		double CPERCENT =  Double.parseDouble(textField_1.getText());
		double VPERCENT = Double.parseDouble(textField_2.getText());
		
			
		@SuppressWarnings("static-access")
		threads th = new threads(new musicMain().poem,ITERA_CNT,CPERCENT,VPERCENT);
		th.run();
		
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		textField_3.setText(th.choosePattern.get(1));
		textField_4.setText(th.choosePattern.get(2));
		textField_5.setText(th.choosePattern.get(3));
		text.setText("");
		
		for(int i =0;i<th.choosePattern.size();i++){
			text.append("第"+i+"次迭代\n"+th.choosePattern.get(i)+"\n");
		}	
	}	
}