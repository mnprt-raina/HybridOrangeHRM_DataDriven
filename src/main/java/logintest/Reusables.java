package logintest;


import java.io.FileInputStream;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.dom4j.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class Reusables 
{
	WebDriver driver;
	static Document document;
	WebElement ele;
	static ExtentReports report;
	ExtentTest test;
	XSSFSheet ExcelWSheet;
	XSSFWorkbook ExcelWBook;
	XSSFCell Cell;
	XSSFRow Row;

	
	WebElement getWebElement(String locator)
	{
		try
		{
			String locatorName = document.selectSingleNode(locator).getText();							

			WebDriverWait wait = new WebDriverWait(driver, 15);  //Explicit wait
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locatorName))); 


			ele = driver.findElement(By.xpath(locatorName));

			if (ele!= null)
			{
				test.log(Status.PASS, "Element fetched with locator "+locator);
			}

		}
		catch(Exception e)
		{
			e.printStackTrace();
			test.log(Status.FAIL, "Element could not be fetched due to "+e.getMessage());
		}
		return ele;
	}

	void getWebElementAndSendKeys(String value, String locator)
	{
		getWebElement(locator).sendKeys(value);
		ele.sendKeys(Keys.ENTER);
	}
	
	public Object[][] getTableArray(String FilePath, String SheetName) throws Exception 
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

	public String getCellData(int RowNum, int ColNum) throws Exception 
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
