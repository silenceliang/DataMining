/**
 * 
 */
/**
 * @author silence
 *
 */
package GA;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

//音符屬性
 interface spectral 
 {
	 
			
	 String[] selection (int times,String mother); //輪盤法
	 
	 int fitness(int len);
		
	 String[] crossover (String[] sample,double percent); //交配
		
	 String[] variation(String[] a,double percent,String[] origin); //變異
	
 }
 
 class spectralGene implements spectral
 {
	 int ITERA_CNT = 0;	//迭代次數(自訂)
	 double CPERCENT = 0; //交配率(自訂)
	 double VPERCENT = 0; //變異機率(自訂)
	private int SIZE = 0;
	private Map<String,Integer> map; //存 (音符字串,出現次數)
	private ArrayList<Integer> Pos;//存位置
	private Random ran;
	protected ArrayList<String> choosePattern;

	spectralGene(){	
		
		ran	= new Random();
		map = new HashMap<>();
		Pos = new ArrayList<Integer>();
	}
	
	@Override
	
	public String[] selection(int times, String mother) { //mother陣列丟進輪盤法轉  times 次
			
		divide(mother); // 將母體等量分割 => 字串丟進map

		int index = -1;
		String[] result = new String[times];
		String[] sample = new String[SIZE];
		
		for(String key :map.keySet()){
			for(int j=1 ;j<=map.get(key) ;j++){
				index++;
				sample[index] = key;
			}
		}
		System.out.println("以下為輪盤"+times+"次,選出來的:");
		
		for(int i=0;i<times;i++){		
			int Ran_num = ran.nextInt(index);
			result[i]=sample[Ran_num];
			System.out.print(result[i]+",");
		}		
		
		return result;
	}
		
	@Override
	
	public String[] crossover(String[] sample,double percent) {
		System.out.println("fuck");
		SIZE = 0;
		map.clear();
		String[][] item2Darray = null; //放同樣長度的二維陣列
		boolean flag = false;
		int len = 0;
		
		do{ // 隨機機率 <= 交配機率 ,then 交配
			
			if(flag)
			{
				int first=0,last=0; 
				
				while(item2Darray==null){						
					len = sample[ran.nextInt(sample.length)].length(); //某段長度
					item2Darray = catchSample(sample,len);
				}
				first = ran.nextInt(len);
				last = ran.nextInt(len);
				
				if(first > last){
					int temp = last;
					last =first;
					first = temp;
				}
				//System.out.println("\n取"+first+"到"+last+"的字元範圍來換");
				sample = inner_crossover(first,last,item2Darray,sample);
				
				Pos.clear();
				item2Darray = null;
			}
			
			flag=true;	
			
		}while( Math.random() <= percent && flag);
		

		return sample;
	}
	
	@Override
	public String[] variation(String[] mother,double percent,String[] origin) {

		boolean flag = false;
		
		do{ // 隨機機率 <= 變異機率 ,then 變異	
			if(flag){
				int index = ran.nextInt(mother.length);
				mother[index] = origin[ran.nextInt(origin.length)]+" ";
				System.out.println();
				System.out.print(" 我變異了"+index+"的位置,");
			}

			flag=true;	
			
		}while( Math.random() <= percent && flag);
		
		return mother;
	}

	
	/**分割母陣列成固定長度子陣列**///被selection所用,為selection第一步
	private void divide(String m) {
		
		int count=0;
		String[] origin = m.split(" ");
		for(int i = 0;i<origin.length;i++){
			origin[i]+=" ";
			if(map.containsKey(origin[i])){
				count=map.get(origin[i]);
				count++;
				SIZE++;
				map.replace(origin[i], count);
			}
			else{
				count=1;
				SIZE++;
				map.put(origin[i], count);
			}
		}
		
	}
	
	private String[][] catchSample(String[] sample,int len){

		ArrayList<String>arr=new ArrayList<String>();
		
		String[][] array2D;
		
		for (int i=0;i<sample.length;i++)		
			if( sample[i].length()==len ){
				
				/**
				 * 防呆pattern
				 * 
				 * **/		
				if(sample[i].matches("[a-zA-Z0-9\\s]*\\[[a-zA-Z]*\\][a-zA-Z0-9\\s]*"))
		    		return null;

				else{
					arr.add(sample[i]);
					Pos.add(i);
				}
			}

		if(Pos.size()==1) //個數只有一個無法交配
			return null;
		
		array2D = new String[arr.size()][len];
		
		for(int i=0;i<arr.size();i++){
			for(int j=0;j<len;j++){
				array2D[i][j]=arr.get(i).split("")[j];
			}
		}
		
		arr.clear();
		return array2D;	
	}
 
	private String[] inner_crossover(int start,int end,String[][] sample,String[] mother ){
			
		System.out.println("\n初始母體:");
		for(String i:mother)
			System.out.print(i+"  ");
		System.out.println();
		
		/**
		 * 以下未包含 'all' pattern rules
		 * **/
		
		int cnt = sample.length;
		int A =ran.nextInt(cnt); //字串位置
		int B =ran.nextInt(cnt); //字串位置
		while(A==B) //防止自交
			 B =ran.nextInt(cnt);
		
		String[] temp = sample[A];
		System.arraycopy(sample[B],start, sample[A], start, end-start);
		System.arraycopy(temp,start,sample[B] , start, end-start);
		
		String te="";
		String ta="";
		for(int i=0;i<sample[A].length;i++){
			te+=sample[A][i];
			ta+=sample[B][i];
		}
		
		
		mother[Pos.get(A)]=te;
		mother[Pos.get(B)]=ta;
		
		System.out.println("\n交配過後的母體:");
		for(String i:mother)
			System.out.print(i+"  ");
		
		return mother;	
	}

	@Override
	public int fitness(int len) {
		int fit = 1;
		for(String key :map.keySet()){
			System.out.println(key+","+map.get(key)+"次");
			fit *= map.get(key);
		}	
		fit/=len;
		return fit;
	}
 }