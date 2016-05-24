/**
 * 
 */
/**
 * @author silence
 *
 */
package GA;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;

public class musicMain {
	
	static String poem=""; // ���ɮפ����Ҧ��r��
	
	public static void main(String[] args) throws InterruptedException, IOException {
		GUI gui;
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("music.txt"), "utf8"));
		    /**
		     * Ū������text��
		     * �U���j��h�OŪ����g
		     * 
		     * **/
		do
	     { 
	    	 String data = br.readLine();
	    	 if(data == null) break;               
	    	 poem+=data+" ";     
	     } while(true);
		
		br.close(); //���� BufferedReader

		gui = new GUI();
	    gui.setVisible(true);
	    gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    gui.setTitle("GA");
	    	    	
	}	
}	

class threads extends spectralGene implements Runnable {

	private Chart ch ;//�ϥιϪ�
	private String Pattern = ""; //�s�񭵲�
	private int[] fitness; //�A����
	private HashMap<String,Integer> fit ;
	private List<Map.Entry<String, Integer>> infoIds;
	
	threads(String Pattern,int ITERA_CNT,double CPERCENT,double VPERCENT)
	{
		this.ITERA_CNT = ITERA_CNT;
		this.CPERCENT = CPERCENT;
		this.VPERCENT = VPERCENT;
		this.Pattern = Pattern;
		this.choosePattern = new ArrayList<String>();
		ch = new Chart();
		fitness = new int[ITERA_CNT+1];
		fit = new HashMap<String,Integer>();
	}
	
	@Override
	public void run() {

		String[] origin = Pattern.split(" ");
		int SELECT_CNT = origin.length; //��o��l���Ф��}�C����
		
		String[] arr = selection(SELECT_CNT,Pattern); //���L�k 
		
		fit.put( Pattern, fitness(ITERA_CNT) );//��J�����
		fitness[0] = fitness(ITERA_CNT);
		
		System.out.println("��"+0+"���A����="+fitness[0]);
		
		System.out.println("�i�J��t:");
		
		String[] pattern= crossover(arr,CPERCENT); // �`�N!!! �o�̬O�ϰ��ܼ�!! �Ψө��t�᪺����
	
		int k = 0; // ���N����count
		
		/**
		 *  �H�U�����N��X:
		 */
		do
		{	
			String[] x = crossover(arr,CPERCENT);
			
			System.arraycopy(x, 0, pattern, 0,x.length); //�����s##��t
			System.out.println("\n��t���᪺���G:");
			
			for(int i = 0; i < pattern.length; i++)	
				System.out.print(pattern[i]+"  "); 
			
			//�ܲ�
			pattern = variation(pattern,VPERCENT,origin);

			Pattern = ""; //��s�s������
			
			for(int i = 0; i < pattern.length; i++)
				Pattern+=pattern[i]+""; //���G���Ŧr��						
			
			System.out.println("\n\n--------------�H�W�@���N"+(k+1)+"��"+"---------------\n");
		
			arr = selection( SELECT_CNT,Pattern); //���L�k 
			
			fit.put(Pattern, fitness(ITERA_CNT));//�[�J�A����		
			fitness[k+1] = fitness(ITERA_CNT);
			
			if(fit.size()==1){
				break;
			}
			k++;
			
		} while (k < ITERA_CNT); // ���N�X��
		
		ch.putData(fitness);//�l��Ϫ�
		
		infoIds = new ArrayList<Map.Entry<String, Integer>>(fit.entrySet());
		Collections.sort(infoIds, new Comparator<Map.Entry<String, Integer>>() {  
		    public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {      
		        return (o2.getValue() - o1.getValue());
		    }
		}); 
		
		for (int i = 0; i < infoIds.size(); i++) {
		    String id = infoIds.get(i).toString();
		    choosePattern.add(id.substring(0, id.indexOf('=')));
		    System.out.println(choosePattern.get(i));	    
		}					
	}
}