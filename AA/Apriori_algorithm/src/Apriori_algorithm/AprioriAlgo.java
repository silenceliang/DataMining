package Apriori_algorithm;

import java.util.LinkedList;

interface AprioriAlgo {
	
	/**
	 * 
	 * @param <E>
	 * @param <E>
	 * @param itemsets   // 即將篩選的集合
	 * @param originset // 原始集合
	 * @return Map     // 計算好個數的集合Map
	 */
	<E> Object countItemFrequent(LinkedList<E> itemsets , LinkedList<E> originsets);
	
	
	/**
	 * 
	 * @param <E>
	 * @param itemsets // 即將篩選的集合
	 * @return Map	  // 計算好個數的集合Map
	 */
	<E>Object countItemFrequent( LinkedList<E> itemsets);
	
	
	/**
	 * 
	 * @param min_support	//最小支持度
	 * @param map			//個數的集合Map
	 * @return LinkedList	//經過最小支持度選出的集合
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
