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
	{	//讀取excel
		try 
		{
			workbook = Workbook.getWorkbook(new FileInputStream(fileToBeRead)); //舊版excel (2003)
			 // 獲得第一個工作表對象  
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
	 * 取得表格的資料輸出成linkedList
	 * 
	 * @return LinkedList of All-data
	 */
	LinkedList<E> dataLinkedList()
	{
		LinkedList<E> ITEMSET = new LinkedList<E>();	//原始集合
		
		int column = sheet.getColumns();
		int row = sheet.getRows();
		
		for(int j = 1; j < row ; j++)
		{				
			LinkedList<E> lis = new LinkedList<E>();	//暫存某列		
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
