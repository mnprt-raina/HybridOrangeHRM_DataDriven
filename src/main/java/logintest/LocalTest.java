//local testing
package logintest;

import java.io.FileInputStream;
import java.io.InputStream;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;
  

public class LocalTest {

//	static WebDriver driver;
	public static void main(String[] args)  {
		InputStream is;
		try {
			is = new FileInputStream(System.getProperty("user.dir")+ "/src/main/resources/OR.xml");
			SAXReader saxReader = new SAXReader();					
			Document document = saxReader.read(is);	
			String locatorName = document.selectSingleNode("//menu/username").getText();
			System.out.println(locatorName);
		} catch (Exception e) {
			System.out.println("a");
			System.out.println(e.getMessage());
		}								
		
			}

}
