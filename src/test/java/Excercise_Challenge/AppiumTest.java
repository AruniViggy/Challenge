package Excercise_Challenge;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class AppiumTest{
	AndroidDriver<AndroidElement> driver;
	String ModelName = "Model1";
	String ServerName = "http://127.0.0.1:4723/wd/hub";



	@BeforeClass
	public void setUp() throws MalformedURLException {
		//load all neccessary information
		File appDir = new File("C:\\tools\\Challenge");
		File app = new File(appDir, "app-debug.apk");
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, ModelName);
		capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
		driver = new AndroidDriver<AndroidElement>(new URL(ServerName), capabilities);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@Test
	public void Test() throws MalformedURLException {
		int random = (int )(Math.random() * 50 + 1);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		//Verify whether the first screen was displayed
		WebElement result= (WebElement) driver.findElementByXPath("//*[@text='Practical Remove']");
		Assert.assertEquals(result.getText(),"Practical Remove", "The practical remove shoes was displayed in the list");
		//Click on a product fromm the list
		driver.findElement(By.xpath("//*[@text='Practical Remove']")).click();

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		//Verify whether the product review screen was displayed
		Assert.assertEquals("Practical Remove","Practical Remove", "Is practical remove shoes in the list?");

		//Click on add review button
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//*[@text='ADD REVIEW']")).click();

		//Add a review, rating and click the save button
		driver.findElement(By.xpath("//android.widget.EditText[@index='0']")).click();
		driver.findElement(By.xpath("//android.widget.EditText[@index='0']")).sendKeys("This is a fantastic product" + random);
		List<AndroidElement> items = driver.findElements(By.xpath("//android.widget.Spinner"));
		items.get(0).click();
		driver.findElement(By.xpath("//*[@text='1']")).click();
		driver.findElement(By.xpath("//*[@text='SAVE']")).click();

		//Verify whether the added review is on the top of the review list
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		WebElement result_review= driver.findElementByXPath("//android.widget.TextView[@index='3']");
		Assert.assertEquals(result_review.getText(), "This is a fantastic product" + random + " 1");
		Assert.assertEquals(result_review.getText(), "Amazing! 3");
	}

	@AfterClass
	public void teardown(ITestContext context){
		if(context.getFailedTests().size()>0)
			System.out.println("at least one test failed");
    	else
    		System.out.println("all tests were success");
		//close the app
		driver.quit();
	}
}
