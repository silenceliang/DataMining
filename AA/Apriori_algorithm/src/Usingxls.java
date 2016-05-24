import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

class Usingxls<E> {
	
	final static String fileToBeRead = "resource.xls";
	private static Workbook workbook;
	private static Sheet sheet;
	
	/**
	 * constructor
	 */
	Usingxls()
	{	//Ū��excel
		try 
		{
			workbook = Workbook.getWorkbook(new FileInputStream(fileToBeRead)); //�ª�excel (2003)
			 // ��o�Ĥ@�Ӥu�@���H  
			sheet = workbook.getSheet(0);						
		} 
		catch (BiffException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
	}
	
		
	/**
	 * ���o��檺��ƿ�X��linkedList
	 * 
	 * @return LinkedList of All-data
	 */
	LinkedList<E> dataLinkedList()
	{
		LinkedList<E> ITEMSET = new LinkedList<E>();	//��l���X
		
		int column = sheet.getColumns();
		int row = sheet.getRows();
		
		for(int j = 1; j < row ; j++)
		{				
			LinkedList<E> lis = new LinkedList<E>();	//�Ȧs�Y�C		
			for(int k = 1 ; k < column ; k++)
			{			
				if(sheet.getCell(k,j).getContents().trim().equals(""))
					continue;				
				lis.add((E) sheet.getCell(k,j).getContents());
			}
			
			ITEMSET.add((E) lis);
		}
		workbook.close();
		
		return ITEMSET;	
	}
}
