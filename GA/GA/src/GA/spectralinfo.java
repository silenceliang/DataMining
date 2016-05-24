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

//�����ݩ�
 interface spectral 
 {
	 
			
	 String[] selection (int times,String mother); //���L�k
	 
	 int fitness(int len);
		
	 String[] crossover (String[] sample,double percent); //��t
		
	 String[] variation(String[] a,double percent,String[] origin); //�ܲ�
	
 }
 
 class spectralGene implements spectral
 {
	 int ITERA_CNT = 0;	//���N����(�ۭq)
	 double CPERCENT = 0; //��t�v(�ۭq)
	 double VPERCENT = 0; //�ܲ����v(�ۭq)
	private int SIZE = 0;
	private Map<String,Integer> map; //�s (���Ŧr��,�X�{����)
	private ArrayList<Integer> Pos;//�s��m
	private Random ran;
	protected ArrayList<String> choosePattern;

	spectralGene(){	
		
		ran	= new Random();
		map = new HashMap<>();
		Pos = new ArrayList<Integer>();
	}
	
	@Override
	
	public String[] selection(int times, String mother) { //mother�}�C��i���L�k��  times ��
			
		divide(mother); // �N���鵥�q���� => �r���imap

		int index = -1;
		String[] result = new String[times];
		String[] sample = new String[SIZE];
		
		for(String key :map.keySet()){
			for(int j=1 ;j<=map.get(key) ;j++){
				index++;
				sample[index] = key;
			}
		}
		System.out.println("�H�U�����L"+times+"��,��X�Ӫ�:");
		
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
		String[][] item2Darray = null; //��P�˪��ת��G���}�C
		boolean flag = false;
		int len = 0;
		
		do{ // �H�����v <= ��t���v ,then ��t
			
			if(flag)
			{
				int first=0,last=0; 
				
				while(item2Darray==null){						
					len = sample[ran.nextInt(sample.length)].length(); //�Y�q����
					item2Darray = catchSample(sample,len);
				}
				first = ran.nextInt(len);
				last = ran.nextInt(len);
				
				if(first > last){
					int temp = last;
					last =first;
					first = temp;
				}
				//System.out.println("\n��"+first+"��"+last+"���r���d��Ӵ�");
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
		
		do{ // �H�����v <= �ܲ����v ,then �ܲ�	
			if(flag){
				int index = ran.nextInt(mother.length);
				mother[index] = origin[ran.nextInt(origin.length)]+" ";
				System.out.println();
				System.out.print(" ���ܲ��F"+index+"����m,");
			}

			flag=true;	
			
		}while( Math.random() <= percent && flag);
		
		return mother;
	}

	
	/**���Υ��}�C���T�w���פl�}�C**///�Qselection�ҥ�,��selection�Ĥ@�B
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
				 * ���bpattern
				 * 
				 * **/		
				if(sample[i].matches("[a-zA-Z0-9\\s]*\\[[a-zA-Z]*\\][a-zA-Z0-9\\s]*"))
		    		return null;

				else{
					arr.add(sample[i]);
					Pos.add(i);
				}
			}

		if(Pos.size()==1) //�Ӽƥu���@�ӵL�k��t
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
			
		System.out.println("\n��l����:");
		for(String i:mother)
			System.out.print(i+"  ");
		System.out.println();
		
		/**
		 * �H�U���]�t 'all' pattern rules
		 * **/
		
		int cnt = sample.length;
		int A =ran.nextInt(cnt); //�r���m
		int B =ran.nextInt(cnt); //�r���m
		while(A==B) //����ۥ�
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
		
		System.out.println("\n��t�L�᪺����:");
		for(String i:mother)
			System.out.print(i+"  ");
		
		return mother;	
	}

	@Override
	public int fitness(int len) {
		int fit = 1;
		for(String key :map.keySet()){
			System.out.println(key+","+map.get(key)+"��");
			fit *= map.get(key);
		}	
		fit/=len;
		return fit;
	}
 }