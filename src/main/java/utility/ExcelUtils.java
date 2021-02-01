//local testing
package utility;

import java.io.FileInputStream;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils 
{
	static XSSFSheet ExcelWSheet;
	static XSSFWorkbook ExcelWBook;
	static XSSFCell Cell;
	static XSSFRow Row;

	public static void main(String args[]) throws Exception 
	{
		getTableArray("C:\\Users\\leo\\eclipse-workspace\\w2\\HybridOrangeHRM\\src\\main\\java\\testdata\\TestData.xlsx","mysheet");
	}

	public static Object[][] getTableArray(String FilePath, String SheetName) throws Exception 
	{   

		String[][] tabArray = null;

		try 
		{
			FileInputStream ExcelFile = new FileInputStream(FilePath);

			ExcelWBook = new XSSFWorkbook(ExcelFile);  // Access the required test data sheet
			ExcelWSheet = ExcelWBook.getSheet(SheetName);

			int startRow = 1;
			int startCol = 1;

			int ci,cj;

			int totalRows = ExcelWSheet.getLastRowNum(); // you can write a function as well to get Column count
			int totalCols = 2;

			tabArray=new String[totalRows][totalCols];

			ci=0;

			for (int i=startRow;i<=totalRows;i++, ci++) 
			{           	   
				cj=0;
				for (int j=startCol;j<=totalCols;j++, cj++)
				{
					tabArray[ci][cj]=getCellData(i,j);
					System.out.println(tabArray[ci][cj]);  
				}
			}

		}
		catch (Exception e)
		{

			System.out.println("Could not read the Excel sheet");
			e.printStackTrace();
		}

		return(tabArray);
	}

	public static String getCellData(int RowNum, int ColNum) throws Exception 
	{
		String cellData="";
		try
		{
			Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
			cellData = Cell.getStringCellValue();
		}
		catch (Exception e)
		{

			System.out.println(e.getMessage());	
		}
		return cellData;
	}
}
