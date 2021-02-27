package loginpage;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import loginPageObject.LoginPageObject;

public class LoginPage {

	WebDriver driver = null;
	String[][] data = null;

	@DataProvider(name = "con")
	public String[][] data() throws BiffException, IOException {
     data=getexceldata();
		return data;
	}

	public String[][] getexceldata() throws BiffException, IOException {
     FileInputStream file = new FileInputStream("C:\\sel execel\\Book1.xls");
	Workbook workbook = Workbook.getWorkbook(file);
	Sheet sheet = workbook.getSheet(0);
	int row= sheet.getRows();
	int col=sheet.getColumns();
	  String newdata[][] = new String [row-1][col];
	  for(int i=1;i<row;i++) {
		  for(int j=0;j<col;j++) {
		newdata[i-1][j]	 = sheet.getCell(j, i).getContents();
		  }
	  }
		  return newdata;
	  }
	

	@Test(dataProvider = "con")
	public void logindata(String name,String pwd) throws IOException {

		FileInputStream fileReader = new FileInputStream("config.properties");

		Properties properties = new Properties();
		properties.load(fileReader);
		String bro = properties.getProperty("Broswer");
		String driverloc = properties.getProperty("driver");
		if (bro.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", driverloc);
			driver = new ChromeDriver();
			driver.get("https://opensource-demo.orangehrmlive.com/");

		} else if (bro.equalsIgnoreCase("friefox")) {
			System.setProperty("webdriver.jecko.driver", driverloc);
			driver = new FirefoxDriver();
			driver.get("https://opensource-demo.orangehrmlive.com/");

		}

		PageFactory.initElements(driver, LoginPageObject.class);
		LoginPageObject.username.sendKeys(name);
		LoginPageObject.password.sendKeys(pwd);
		LoginPageObject.btn.click();
		
		driver.close();
	}
}
