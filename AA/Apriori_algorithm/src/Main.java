import java.util.LinkedList;

import Apriori_algorithm.Apriori;

public class Main {

	private final static int minSupport = 2;   // �̤p���
	
	public static void main(String[] args) {
				
		//   �ǤJ��ƶ��X&�̤p����� ,�I�s Aprior_Algorithm �C		
		Apriori apr = new Apriori ( 
				new Usingxls<Object>().dataLinkedList(),minSupport);	
		
		String ans = apr.run().toString();
		 

		new GUI( ans , new Usingxls().fileToBeRead );
		
	}

}
