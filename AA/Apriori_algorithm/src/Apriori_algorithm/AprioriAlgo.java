package Apriori_algorithm;

import java.util.LinkedList;

interface AprioriAlgo {
	
	/**
	 * 
	 * @param <E>
	 * @param <E>
	 * @param itemsets   // �Y�N�z�諸���X
	 * @param originset // ��l���X
	 * @return Map     // �p��n�Ӽƪ����XMap
	 */
	<E> Object countItemFrequent(LinkedList<E> itemsets , LinkedList<E> originsets);
	
	
	/**
	 * 
	 * @param <E>
	 * @param itemsets // �Y�N�z�諸���X
	 * @return Map	  // �p��n�Ӽƪ����XMap
	 */
	<E>Object countItemFrequent( LinkedList<E> itemsets);
	
	
	/**
	 * 
	 * @param min_support	//�̤p�����
	 * @param map			//�Ӽƪ����XMap
	 * @return LinkedList	//�g�L�̤p����׿�X�����X
	 */
	
	Object chooseByminSpport(int min_support,Object map);
	
	
	/**
	 * 
	 * @param <E>
	 * @param itemsets		//one is next-data
	 * @param originet		//the other is original-data
	 * @return LinkedList	//combine both of them
	 */
	<E> Object generateList(LinkedList<E> itemsets ,LinkedList<E> originsets );
}
