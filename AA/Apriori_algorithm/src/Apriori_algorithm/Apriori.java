package Apriori_algorithm;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Apriori implements AprioriAlgo {
		
	private static int minSupport;     // 最小支持度
	private static LinkedList<Object> ITEMSET;    // 存取最初原始資料
	
	public Object run()
	{
		Map ItemMap = new HashMap();
		Map origin_ItemsetMap = countItemFrequent(ITEMSET); //計算母體集合數
		ItemMap = origin_ItemsetMap;
		
		LinkedList originLinkedList = chooseByminSpport(0,origin_ItemsetMap); 
		//  第一步的set分類
		
		LinkedList selectedItemset = originLinkedList;
		LinkedList Output = null ;
	
	/**
	 * 迴圈進行阿帕利演算法的步驟
	 * 
	 * 
	 * 	
	 */
		while (!selectedItemset.isEmpty())  // 直到選出的集合全為空
		{	
			Output = selectedItemset;   // 結果list
			selectedItemset = chooseByminSpport(minSupport,ItemMap);
			
			if(selectedItemset==null) break;
			
			LinkedList combinedItenset = generateList(selectedItemset,originLinkedList);
			ItemMap  = countItemFrequent(combinedItenset,ITEMSET);			
			
		}
		//輸出結果List
		System.out.println("輸出結果集合 :\n"+Output);
		
		return Output;
	}
	
/**
 * - 建構子
 * @param ITEMSET :所有資料集合
 * @param minSupport :最小支持度
 */
	public Apriori(LinkedList<Object> ITEMSET,int minSupport)
	{		
		this.ITEMSET = ITEMSET;
		this.minSupport = minSupport;	
	}

	
/**
 * - 以下為interface方法
 * 
 */	
	public  Map countItemFrequent( LinkedList itemsets , LinkedList originsets)
	{
		Map<Object,Integer> itemSet = new HashMap<Object,Integer>();

		int common_Innerlen = 2; 
		//兩個合併開始
		
			
		for(int i = 0; i < itemsets.size() ; i++)//合併過的 [ .... ]
		{
			LinkedList itemset = (LinkedList) itemsets.get(i) ; 	//  內部list : [ [],[],[].... ]
		
			int count = 0;  			
			//  計算累積數
			
			for(int k = 0 ; k < originsets.size() ; k++) // 原始陣列  [  ....  ]
			{
				LinkedList originset = (LinkedList) originsets.get(k); //  內部list : [ [],[],[].... ]
				
				int c = 0;
				// 判斷是否完全相同 === 數量一致
				
				for(int j = 0; j<itemset.size() ; j++) //[ [ 1 ,2 ] ]
				{
					
					if(itemset.get(j) instanceof LinkedList) //若遇到集合物件 [... ,[ "[1,2]" ,1], ...]
						//  =>超過2個合併的情況
					{
						common_Innerlen = ((LinkedList) itemset.get(j)).size() + 1; // 正常該有的長度
					
						for ( int l = 0 ; l < ((LinkedList) itemset.get(j)).size() ; l++ ) // 將list中的list取出
						{
							Object s = ((LinkedList) itemset.get(j)).get(l);
							if(!itemset.contains(s)) 
								itemset.add(s);
						}
						itemset.remove(j);  //	將list中之list刪除
					}

					if( originset.contains( itemset.get(j) ) )	c++;				
				}
				if( c == itemset.size()) //代表此originList含有這項
					count++;
			}

			if(itemset.size() == common_Innerlen)
					itemSet.put(itemset , count);
		}
		
		System.out.println("....丟入Map囉!");
		Set set = itemSet.keySet();
        for(Object o : set) {
            System.out.print("key: ");
            System.out.print(o);
            System.out.print(" value: ");
            System.out.println(itemSet.get(o));
        }
        return itemSet;
	}
	
	public <String> Map countItemFrequent( LinkedList<String> itemsets )
	{
		Map<String,Integer> itemSet = new HashMap<String,Integer>();
		

		for(int i = 0; i<((LinkedList) itemsets).size() ; i++){
			
			LinkedList itemset = (LinkedList) ((LinkedList) itemsets).get(i);
			LinkedList lis = new LinkedList(); // 暫存
			
			for(int j = 0;j<itemset.size();j++){

				lis.add(itemset.get(j));
				
				if(itemSet.containsKey(lis.get(j)))
				{
					 Integer integer = (Integer) itemSet.get(lis.get(j));
		             int count = integer.intValue();
		             count += 1; //計算重複個數
		             itemSet.put((String) lis.get(j), new Integer(count));
				}
				else
					itemSet.put((String) lis.get(j),new Integer(1));		
			}
		}

		System.out.println("\n\n"+"....丟入Map囉!");
		
		Set set = itemSet.keySet();
        for(Object o : set) {
            System.out.print("key: ");
            System.out.print(o);
            System.out.print(" value: ");
            System.out.println(itemSet.get(o));
        }	
        return itemSet;
	}		
		
	public LinkedList<Object> chooseByminSpport(int min_support,Object map){

		Iterator<Entry<Object, Integer>> it = ((Map) map).entrySet().iterator(); 
		LinkedList attributeArray = new LinkedList() ;
			
		while(it.hasNext())
		{
		    Entry<Object, Integer> entry = it.next();  
		    	
		    if (entry.getValue() < min_support)		
		    	it.remove();  // delete attribute that < minSupport	    	
		    else
		    	attributeArray.add(entry.getKey());
		 }
				
		if(attributeArray.isEmpty())
			return null;
		
		System.out.println("-----------------------------------\n"
				+ "根據support篩選 :"
				+ "\n"+attributeArray);
		
		 return attributeArray;
	   }      
	
	public LinkedList<LinkedList<?>> generateList(LinkedList itemsets ,LinkedList originsets )
	{
		LinkedList list = new LinkedList();
		
		for(int i =0;i<itemsets.size();i++)
		{			
			for(int j = i+1;j<originsets.size();j++)
			{
				LinkedList templist = new LinkedList();
				templist.add(itemsets.get(i));
				templist.add(originsets.get(j));
				list.add(templist);
			}
		}
		System.out.println("合併兩項 :");
		System.out.println(list);
		return list;
	}
}