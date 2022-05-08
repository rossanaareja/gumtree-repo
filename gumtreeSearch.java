package GumtreeUITest;


import static org.testng.Assert.assertTrue;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

	public class gumtreeSearch {
		
		WebDriver driver;
		
		@SuppressWarnings("deprecation")	
		@BeforeSuite
		public void setUp() {
			System.setProperty("webdriver.chrome.driver", "/Users/rossanaareja/eclipse/chromedriver");
			driver = new ChromeDriver(); 
			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();
			driver.get("http://gumtree.com.au");
		}
		
		@BeforeClass
		public void performSearch() {
			driver.findElement(By.className("search-bar__category-name")).click();
			System.out.println("Navigate All Categories ");
			
			driver.findElement(By.id("categoryId-wrp-option-20045")).click();
			System.out.println("Select Electronics& Computer"); 

			driver.findElement(By.id("search-query")).sendKeys("Sennheiser Headphones");
			System.out.println("input search keyword Sennheiser Headphones");
			
			driver.findElement(By.id("search-area")).sendKeys("Sydney Region, NSW");
			System.out.println("Set Location to Sydney Region, NSW");
			
			driver.findElement(By.id("srch-radius-input")).click();
			driver.findElement(By.id("srch-radius-wrp-option-20")).click();
			System.out.println("Set Radius = 20km");

			driver.findElement(By.xpath("//*[@id=\'search-form-form\']/ul/li[4]/button")).click();
			System.out.println("Click Search button");
			
			driver.findElement(By.className("user-ad-row-new-design__main-content")).click();
			System.out.println("Click random result");

			
		}
		@Test(priority=1)
		public void validateAdDetailsPage() {
			boolean adDetailspageDisplayed = driver.findElement(By.xpath("//*[@id=\'react-root\']/div/div[3]/div/div[3]/div[1]/div[1]/div/div[2]/div[3]")).isDisplayed();
			assertTrue(adDetailspageDisplayed, "Ad Details page is not displayed");	
		}
		
		@Test(priority=2)
		public void validateAdId() {
			WebElement breadcrumb = driver.findElement(By.xpath("//*[@id=\'breadcrumbs__desktop-sentinel\']/div/div[1]/span[6]"));
			String adIdBreadcrumb = breadcrumb.getText();
			String[] adID = adIdBreadcrumb.split("(?<=\\D)(?=\\d)");
			boolean adIDIsNumeric = false;
			int adIDlen = adID[1].length();
			for (int i=0; i<adIDlen; i++) {
				if (! Character.isDigit(adID[1].charAt(i))) {
					 adIDIsNumeric = false;
				}
				else {
					 adIDIsNumeric = true;
				}
			}
			assertTrue(adIDIsNumeric, "Ad ID is not numeric" + adID[1]);			
		}
		
		@Test(priority=3)
		public void validateSimilarAd() {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0,1500)", "");
			
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\'react-root\']/div/div[3]/div/div[3]/div[1]/div[5]/div[1]")));
			
			boolean isSimilarAdDisplayed = driver.findElement(By.className("vip-slidable-ads__item-wrapper")).isDisplayed();
			assertTrue(isSimilarAdDisplayed, "Similar Ads not displayed");
		} 
		
		@AfterSuite
		public void tearDown() {
			driver.quit();
		}
	}

