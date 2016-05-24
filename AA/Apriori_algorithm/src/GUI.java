import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

class GUI extends JFrame{
		
	private JPanel panel;
	private JPanel pane2;
	private JTextField text;
	private JTextField outputxt;
	private JLabel lab;
	private JLabel outputlab;
	private JButton btn;
	private String output;

	GUI()
	{
		
	}
	
	GUI(String output,String name)
	{	 
		this.output = output;
		
		panel = new JPanel();
		pane2 = new JPanel();
		
		btn = new JButton("run");
		
		lab = new JLabel("ÀÉ®×¦WºÙ : ");
		outputlab = new JLabel("Output : ");
		
		text = new JTextField(name); 
		outputxt = new JTextField(); 
		
		text.setEditable(false);
		text.setBackground(new Color(250,250,250));
		
		outputxt.setEditable(false);
		outputxt.setBackground(new Color(250,250,250));
		
		panel.setLayout(new BorderLayout(10, 10));
		panel.add(lab, BorderLayout.WEST);
		panel.add(text, BorderLayout.CENTER);
		panel.add(btn,BorderLayout.EAST);
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		pane2.setLayout(new BorderLayout(10, 10));
		pane2.add(outputlab, BorderLayout.WEST);
		pane2.add(outputxt, BorderLayout.CENTER);
		pane2.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); 
		
		this.add(panel,BorderLayout.NORTH);
		this.add(pane2,BorderLayout.SOUTH);
		
		this.setTitle("Apriori");
		this.setSize(400, 300);
		this.setVisible(true);	 
		

		 btn.addActionListener(
			      new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						outputxt.setText("");
						outputxt.setText(output);
					}
			      }
			    );
	}
}