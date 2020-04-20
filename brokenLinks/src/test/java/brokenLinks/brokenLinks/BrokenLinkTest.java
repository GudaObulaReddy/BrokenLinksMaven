package brokenLinks.brokenLinks;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HttpsURLConnection;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.w3c.dom.stylesheets.LinkStyle;

public class BrokenLinkTest {

	public static void main(String[] args) throws MalformedURLException, IOException 
	{
		System.setProperty("webdriver.chrome.driver","C:\\Users\\Ramakrishna\\Desktop\\chromedriver\\chromedriver.exe");
		WebDriver driver=new ChromeDriver();
		driver.get("https://makemysushi.com/Recipes/how-to-make-california/404-sushi-rolls");
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(50, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		//links -- // a href<https://www.com"
		// some a tags are avilabe and no href so we have to take href element
		//img  -- // img href<http://
		//1.get the list of all links and images
		List<WebElement> linklist=driver.findElements(By.tagName("a"));
		linklist.addAll(driver.findElements(By.tagName("img")));
		System.out.println("size of  links list"+linklist.size());
		List<WebElement> activeLinks=new ArrayList<WebElement>();
		// 2 iterate linklist:extract all the links/images-
		for(int i=0;i<linklist.size();i++)
		{
			//System.out.println(linklist.get(i).getAttribute("href"));
			if(linklist.get(i).getAttribute("href")!=null && !(linklist.get(i).getAttribute("href").contains("javascript")))
			{
				
				activeLinks.add(linklist.get(i));
				
			}
		}
		// get the size of active links and images
		System.out.println("size of active links list"+activeLinks.size());
		//3.check the href url, with httpconnection api it will take care of 
		//wether the link is correct it is present inn java jars
		//200---ok
		//404 --- not found
		//500 -- internal erroe
		//400 bad request
		for(int j=0;j<activeLinks.size();j++)
		{ 
			System.out.println(activeLinks.get(j).getAttribute("href")+"hi");
			try {
			HttpsURLConnection connection=(HttpsURLConnection)new URL(activeLinks.get(j).getAttribute("href")).openConnection();
		   connection.connect();
		  String response= connection.getResponseMessage();//ok
		   connection.disconnect();
		   System.out.println(activeLinks.get(j).getAttribute("href")+"------->"+response);
			}catch (Exception e) {
			e.printStackTrace();
			continue;
				}
			}
			
			
	}

}
