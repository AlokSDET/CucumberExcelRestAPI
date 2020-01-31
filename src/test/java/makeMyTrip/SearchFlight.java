package makeMyTrip;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SearchFlight {

	public WebDriver driver;

	@BeforeClass
	public void launchBrowser() throws Exception {
		System.setProperty("webdriver.chrome.driver", "F:\\seleniumAlok\\chromedriver.exe");

		driver = new ChromeDriver();
		driver.get("https://www.makemytrip.com/flights/");
		driver.manage().window().maximize();
		driver.navigate().refresh();

	}

	@Test
	public void searchFlight() throws Exception {

		driver.findElement(By.xpath("//input[@id='fromCity']")).sendKeys("Bangalore");

		WebDriverWait wait = new WebDriverWait(driver, 10000);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li//div//p[contains(text(),'Bangalore')]")));

		driver.findElement(By.xpath("//li//div//p[contains(text(),'Bangalore')]")).click();

		driver.findElement(By.xpath("//input[@id='toCity']")).sendKeys("Delhi");

		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li//div//p[contains(text(),'Delhi')]")));

		driver.findElement(By.xpath("//li//div//p[contains(text(),'Delhi')]")).click();

		String currentMonthYear = driver.findElement(By.xpath("//div[@class='DayPicker-Months']//div[1]/div/div"))
				.getText();

		String currentMonth = currentMonthYear.split(" ")[0];

		String currentYear = currentMonthYear.split(" ")[1];

		// You can give your desired date
		String expectedMonth = "June";
		String expectedYear = "2019";
		String expectedDate = "16";

		if (currentYear.equals(expectedYear)) {
			if (currentMonth.equals(expectedMonth)) {
				System.out.println("Expected month is already selected");
			} else {
				for (int i = 1; i < 12; i++) {
					driver.findElement(By.xpath("//span[@aria-label='Next Month']")).click();
					Thread.sleep(1000);
					currentMonthYear = driver.findElement(By.xpath("//div[@class='DayPicker-Months']//div[1]/div/div"))
							.getText();
					currentMonth = currentMonthYear.split(" ")[0];
					if (currentMonth.equals(expectedMonth)) {
						System.out.println("Expected month is get selected now.");
						break;
					}
				}
			}
		} else {
			for (int i = 1; i < 12; i++) {
				driver.findElement(By.xpath("//span[@aria-label='Next Month']")).click();
				Thread.sleep(1000);
				currentMonthYear = driver.findElement(By.xpath("//div[@class='DayPicker-Months']//div[1]/div/div"))
						.getText();
				currentYear = currentMonthYear.split(" ")[1];
				if (currentYear.equals(expectedYear)) {
					System.out.println("Expected year is get selected now.");
					break;
				}
			}
		}

		driver.findElement(By.xpath("//div[@class='DayPicker-Month'][1]//p[text()=" + expectedDate + "]")).click();

		driver.findElement(By.xpath("//a[contains(text(),'Search')]")).click();
	}
}
