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
	
	static String poem=""; // 為檔案中的所有字串
	
	public static void main(String[] args) throws InterruptedException, IOException {
		GUI gui;
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("music.txt"), "utf8"));
		    /**
		     * 讀取音符text檔
		     * 下面迴圈則是讀完整篇
		     * 
		     * **/
		do
	     { 
	    	 String data = br.readLine();
	    	 if(data == null) break;               
	    	 poem+=data+" ";     
	     } while(true);
		
		br.close(); //關閉 BufferedReader

		gui = new GUI();
	    gui.setVisible(true);
	    gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    gui.setTitle("GA");
	    	    	
	}	
}	

class threads extends spectralGene implements Runnable {

	private Chart ch ;//使用圖表
	private String Pattern = ""; //存放音符
	private int[] fitness; //適應值
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
		int SELECT_CNT = origin.length; //獲得原始樂譜之陣列長度
		
		String[] arr = selection(SELECT_CNT,Pattern); //輪盤法 
		
		fit.put( Pattern, fitness(ITERA_CNT) );//放入母體值
		fitness[0] = fitness(ITERA_CNT);
		
		System.out.println("第"+0+"次適應值="+fitness[0]);
		
		System.out.println("進入交配:");
		
		String[] pattern= crossover(arr,CPERCENT); // 注意!!! 這裡是區域變數!! 用來放交配後的母體
	
		int k = 0; // 迭代次數count
		
		/**
		 *  以下為迭代輸出:
		 */
		do
		{	
			String[] x = crossover(arr,CPERCENT);
			
			System.arraycopy(x, 0, pattern, 0,x.length); //母體更新##交配
			System.out.println("\n交配完後的結果:");
			
			for(int i = 0; i < pattern.length; i++)	
				System.out.print(pattern[i]+"  "); 
			
			//變異
			pattern = variation(pattern,VPERCENT,origin);

			Pattern = ""; //更新新的樂譜
			
			for(int i = 0; i < pattern.length; i++)
				Pattern+=pattern[i]+""; //結果音符字串						
			
			System.out.println("\n\n--------------以上共迭代"+(k+1)+"次"+"---------------\n");
		
			arr = selection( SELECT_CNT,Pattern); //輪盤法 
			
			fit.put(Pattern, fitness(ITERA_CNT));//加入適應值		
			fitness[k+1] = fitness(ITERA_CNT);
			
			if(fit.size()==1){
				break;
			}
			k++;
			
		} while (k < ITERA_CNT); // 迭代幾次
		
		ch.putData(fitness);//召喚圖表
		
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