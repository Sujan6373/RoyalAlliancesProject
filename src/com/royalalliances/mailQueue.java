package com.royalalliances;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.server.handler.FindElement;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.beust.jcommander.Parameter;


public class mailQueue {
	
	WebDriver driver;
	WebElement upload;

	/*****************************
	 * Need to use DATA PROVIDER
	 ******************************/

//launches the chrome browser	
	
	@Parameters({"URL"})
	@BeforeTest
	public void invokeBrowser(String url) {
		/*
		 * System.setProperty("webdriver.chrome.driver",
		 * "C:\\Users\\Sujan\\Documents\\Selenium\\chromedriver_win32\\chromedriver.exe"
		 * );
		 * 
		 * driver.manage().window().maximize();
		 * driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		 * driver.manage().timeouts().pageLoadTimeout(45, TimeUnit.SECONDS);
		 * 
		 * driver.get("https://dev.royalalliances.com/");
		 */

		try {
			System.setProperty("webdriver.chrome.driver",
					"C:\\Users\\Sujan\\Documents\\Selenium\\chromedriver_win32\\chromedriver.exe");
			driver = new ChromeDriver();
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
			driver.get(url);
			Thread.sleep(2000);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	//sends the username
	@Parameters({"feederUserName"})
	@Test(priority = 1)
	public void sendFeederUsername(String userName) throws InterruptedException {
		driver.findElement(By.name("UserName")).clear();
		driver.findElement(By.name("UserName")).sendKeys(userName);
		Thread.sleep(1000);
	}

	//sends the password
	@Parameters({"feederPassword"})	
	@Test(priority = 2)
	public void sendFeederPassword(String password) throws InterruptedException {
		driver.findElement(By.name("Password")).clear();
		driver.findElement(By.name("Password")).sendKeys(password);
		Thread.sleep(1000);
	}
		
	/*
	 * @Test(priority = 2) public void feederLogIn(String userName, String password)
	 * throws InterruptedException { sendFeederUsername(userName);
	 * sendFeederPassword(password); clickSignIn(); }
	 */
	
	//clicks on the log in button
	@Test(priority = 3)
	public void clickSignIn() {
		driver.findElement(By.xpath("//*[contains(text(),'Sign In')]")).submit();
	}
		
	//verifies the title of the page
	@Test(priority = 4)
	public void verifyDashboard() throws InterruptedException {
		Thread.sleep(3000);
		String title = driver.getTitle();
		System.out.println("The title of the page is " + title);

		Assert.assertEquals(title, "Royal Alliances | Royal Dashboard");
	}

	//selects the RA221 from the site selector
	@Test(priority = 5)
	public void selectRA221() throws InterruptedException {
		Thread.sleep(2000);
		driver.findElement(By.xpath("/html/body/div[1]/div/ul/li[5]/button")).click();
		

		Thread.sleep(1000);
		driver.findElement(By.xpath("/html/body/div[11]/ul/li[1]/label/span")).click();

		Thread.sleep(1000);
		driver.findElement(By.xpath("/html/body/div[11]/ul/li[2]/label/span")).click();

		Thread.sleep(1000);
		driver.findElement(By.xpath("/html/body/div[3]/div/div/h1")).click();
		Thread.sleep(3000);
	}

	//navigates to mailqueue page from legacy
	@Parameters({"MailQueueURL"})
	@Test(priority = 6)
	public void navigateToMailQueue(String url) throws InterruptedException {
		driver.get(url);
		Thread.sleep(3000);

		if (driver.getPageSource().contains("Mail Queue")) {
			System.out.println("Mail Queue found");
		} else {
			System.out.println("Mail Queue not found");
		}

		driver.findElement(By.className("active_header")).isDisplayed();

		String header = driver.findElement(By.className("active_header")).getText();
		System.out.println(header);

		Assert.assertEquals(header, "Mail Queue");
		System.out.println("Header for the page Mail Queue is found");
	}
	
	//prints the total links available in the RA Home page in the console
	@SuppressWarnings("unchecked")
	@Test(priority = 7)
	public void totalLinksInRAHomePage()
	{
		//@SuppressWarnings("unchecked")
		List <WebElement> RAlinks = (List<WebElement>) driver.findElements(By.tagName("a"));
		System.out.println("Total number of links = " + RAlinks.size());
		String RAL = null;
		System.out.println("Below are the list of links in RA Home Page:-");
		for(int i=0; i<RAlinks.size(); i++)
		{
			RAL = RAlinks.get(i).getText();
			//System.out.println(RAL);
		}
	}

	//uploads the mail that has only FC letters
	@Test(priority = 8)
	public void uploadFCLetterOnly() throws InterruptedException {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		String fileToken;
		String elemDateTime = null;
		String elemJobId = null;

		Thread.sleep(2000);
		driver.findElement(By.id("btnUploadfile")).click();

		Thread.sleep(2000);
		// driver.findElement(By.id("btnBrowseFiles")).click();

		WebElement upload = driver.findElement(By.id("files"));
		// Thread.sleep(1000);

		// File file = new
		// File("C:\\Users\\sshre\\OneDrive\\Documents\\TestFiles\\GuaranteedPricing\\FC53_ADC_MXD_AADC_LTR.zip");
		String filePath = "C:\\Users\\sshre\\OneDrive\\Documents\\TestFiles\\GuaranteedPricing\\FC53_ADC_MXD_AADC_LTR.zip";

		upload.sendKeys(filePath);
		;
		Thread.sleep(10000);

		// driver.findElement(By.id("files")).click();

		driver.findElement(By.id("upload_Done_button")).click();

		Thread.sleep(40000);
		driver.navigate().refresh();

		Thread.sleep(10000);

		/****************************************************************************
		 * DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		 * LocalDateTime now = LocalDateTime.now(); String date = dtf.format(now);
		 * System.out.println(dtf.format(now));
		 * 
		 * java.lang.Long pendingsize = (java.lang.Long) jse
		 * .executeScript("return $(\"#pendingFileList\").data(\"kendoGrid\")[\"_data\"].length"
		 * );
		 * 
		 * for (int i = 0; i < pendingsize; i++) { fileToken = (String)
		 * jse.executeScript(
		 * "return $(\"#pendingFileList\").data(\"kendoGrid\")[\"_data\"][" + i +
		 * "].FileToken"); elemDateTime = (String) jse.executeScript(
		 * "return $(\"#pendingFileList\").data(\"kendoGrid\")[\"_data\"][" + i +
		 * "].FileUploadedDateTime"); elemJobId = (String) jse
		 * .executeScript("return $(\"#pendingFileList\").data(\"kendoGrid\")[\"_data\"]["
		 * + i + "].JobId"); java.lang.Long elemPieceCount = (java.lang.Long)
		 * jse.executeScript(
		 * "return $(\"#pendingFileList\").data(\"kendoGrid\")[\"_data\"][" + i +
		 * "].NoOfPieces");
		 * 
		 * System.out.println("File: " + fileToken + " , " + elemDateTime + " , " +
		 * elemJobId + "," + elemPieceCount);
		 * 
		 * }
		 * 
		 * if (date == elemDateTime) {
		 * System.out.println("*********time matched for file " + elemJobId +
		 * " ***********"); } else {
		 * System.out.println(" time did not matched for the file "); }
		 *****************************************************************************/

		//String jobId = driver.findElement(By.xpath("//*[@id=\"pendingFileList\"]/table/tbody/tr[1]/td[7]")).getText();
		
		String jobId = driver.findElement(By.xpath("//div[@id=\"pendingFileList\"][1]/table/tbody/tr/td[text()='00028977'][1]")).getText();
		
		// driver.findElement(By.xpath("//*[@id=\"pendingFileList\"]/table/tbody/tr[3]/td[7]")).getText();
		Assert.assertEquals(jobId, "00028977");
		System.out.println(jobId + " is present in the page");
	}
	
	//selects the file and ship
	@Test(priority = 9)
	public void shipFile() throws InterruptedException
	{
		driver.findElement(By.xpath("(//div[@id='pendingFileList'][1]/table/tbody/tr/*[text()='Ready'])[1]/preceding-sibling::td[3]/label")).click();
		Thread.sleep(1000);
		driver.findElement(By.id("btnSubmitPendingFiles")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath(".//*[contains(@type,'submit')]")).click();
		Thread.sleep(1000);
		driver.findElement(By.id("btnSubmitSchedule")).click();
		Thread.sleep(30000);
		driver.navigate().refresh();
		Thread.sleep(2000);
	}
	
	@Test(priority = 10)
	public void validateFileIsScheduled() throws InterruptedException
	{
		driver.findElement(By.xpath(".//a[@href='#scheduledtab']")).click();
		Thread.sleep(2000);
		String status = driver.findElement(By.xpath("//td[text()='Scheduled'][1]")).getText();
		System.out.println("Status for the file is "+ status);
		Assert.assertEquals(status, "Scheduled");
		
		String jobId = driver.findElement(By.xpath("//*[contains(text(),'I24001B')][1]")).getText();
		System.out.println("Job id for te file is " + jobId);
		Assert.assertEquals(jobId, "1I24001B");
	}
	
	@Test(priority = 11)
	public void logOut() throws InterruptedException
	{
		driver.findElement(By.className("dropdown-toggle")).click();
		driver.findElement(By.xpath("//a[@href='/Account/LogOff']")).click();
		Thread.sleep(2000);
	}
	

	@Parameters({"hubUserName"})
	@Test(priority = 12)
	public void sendHubUsername(String userName) throws InterruptedException {
		driver.findElement(By.name("UserName")).clear();
		driver.findElement(By.name("UserName")).sendKeys(userName);
		Thread.sleep(1000);
	}

	//sends the password
	@Parameters({"hubPassword"})
	@Test(priority = 13)
	public void sendHubPassword(String password) throws InterruptedException {
		driver.findElement(By.name("Password")).clear();
		driver.findElement(By.name("Password")).sendKeys(password);
		Thread.sleep(1000);
		clickSignIn();
	}
	
	/*
	 * @Test(priority = 12) public void hubLogIn(String userName, String password)
	 * throws InterruptedException { sendHubUsername(userName);
	 * sendHubPassword(password); clickSignIn(); }
	 */

	//closes the instance of the browser
	/*
	 * @Test(priority = 100) public void closeBrowser() { driver.close(); }
	 */

}
