package Apriori_algorithm;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Apriori implements AprioriAlgo {
		
	private static int minSupport;     // �̤p�����
	private static LinkedList<Object> ITEMSET;    // �s���̪��l���
	
	public Object run()
	{
		Map ItemMap = new HashMap();
		Map origin_ItemsetMap = countItemFrequent(ITEMSET); //�p����鶰�X��
		ItemMap = origin_ItemsetMap;
		
		LinkedList originLinkedList = chooseByminSpport(0,origin_ItemsetMap); 
		//  �Ĥ@�B��set����
		
		LinkedList selectedItemset = originLinkedList;
		LinkedList Output = null ;
	
	/**
	 * �j��i������Q�t��k���B�J
	 * 
	 * 
	 * 	
	 */
		while (!selectedItemset.isEmpty())  // �����X�����X������
		{	
			Output = selectedItemset;   // ���Glist
			selectedItemset = chooseByminSpport(minSupport,ItemMap);
			
			if(selectedItemset==null) break;
			
			LinkedList combinedItenset = generateList(selectedItemset,originLinkedList);
			ItemMap  = countItemFrequent(combinedItenset,ITEMSET);			
			
		}
		//��X���GList
		System.out.println("��X���G���X :\n"+Output);
		
		return Output;
	}
	
/**
 * - �غc�l
 * @param ITEMSET :�Ҧ���ƶ��X
 * @param minSupport :�̤p�����
 */
	public Apriori(LinkedList<Object> ITEMSET,int minSupport)
	{		
		this.ITEMSET = ITEMSET;
		this.minSupport = minSupport;	
	}

	
/**
 * - �H�U��interface��k
 * 
 */	
	public  Map countItemFrequent( LinkedList itemsets , LinkedList originsets)
	{
		Map<Object,Integer> itemSet = new HashMap<Object,Integer>();

		int common_Innerlen = 2; 
		//��ӦX�ֶ}�l
		
			
		for(int i = 0; i < itemsets.size() ; i++)//�X�ֹL�� [ .... ]
		{
			LinkedList itemset = (LinkedList) itemsets.get(i) ; 	//  ����list : [ [],[],[].... ]
		
			int count = 0;  			
			//  �p��ֿn��
			
			for(int k = 0 ; k < originsets.size() ; k++) // ��l�}�C  [  ....  ]
			{
				LinkedList originset = (LinkedList) originsets.get(k); //  ����list : [ [],[],[].... ]
				
				int c = 0;
				// �P�_�O�_�����ۦP === �ƶq�@�P
				
				for(int j = 0; j<itemset.size() ; j++) //[ [ 1 ,2 ] ]
				{
					
					if(itemset.get(j) instanceof LinkedList) //�Y�J�춰�X���� [... ,[ "[1,2]" ,1], ...]
						//  =>�W�L2�ӦX�֪����p
					{
						common_Innerlen = ((LinkedList) itemset.get(j)).size() + 1; // ���`�Ӧ�������
					
						for ( int l = 0 ; l < ((LinkedList) itemset.get(j)).size() ; l++ ) // �Nlist����list���X
						{
							Object s = ((LinkedList) itemset.get(j)).get(l);
							if(!itemset.contains(s)) 
								itemset.add(s);
						}
						itemset.remove(j);  //	�Nlist����list�R��
					}

					if( originset.contains( itemset.get(j) ) )	c++;				
				}
				if( c == itemset.size()) //�N��originList�t���o��
					count++;
			}

			if(itemset.size() == common_Innerlen)
					itemSet.put(itemset , count);
		}
		
		System.out.println("....��JMap�o!");
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
			LinkedList lis = new LinkedList(); // �Ȧs
			
			for(int j = 0;j<itemset.size();j++){

				lis.add(itemset.get(j));
				
				if(itemSet.containsKey(lis.get(j)))
				{
					 Integer integer = (Integer) itemSet.get(lis.get(j));
		             int count = integer.intValue();
		             count += 1; //�p�⭫�ƭӼ�
		             itemSet.put((String) lis.get(j), new Integer(count));
				}
				else
					itemSet.put((String) lis.get(j),new Integer(1));		
			}
		}

		System.out.println("\n\n"+"....��JMap�o!");
		
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
				+ "�ھ�support�z�� :"
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
		System.out.println("�X�֨ⶵ :");
		System.out.println(list);
		return list;
	}
}