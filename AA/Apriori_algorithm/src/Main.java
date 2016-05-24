import java.util.LinkedList;

import Apriori_algorithm.Apriori;

public class Main {

	private final static int minSupport = 2;   // 最小支持
	
	public static void main(String[] args) {
				
		//   傳入資料集合&最小支持度 ,呼叫 Aprior_Algorithm 。		
		Apriori apr = new Apriori ( 
				new Usingxls<Object>().dataLinkedList(),minSupport);	
		
		String ans = apr.run().toString();
		 

		new GUI( ans , new Usingxls().fileToBeRead );
		
	}

}
