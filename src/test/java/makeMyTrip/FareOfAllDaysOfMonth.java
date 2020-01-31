package makeMyTrip;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class FareOfAllDaysOfMonth {

	private static Properties pro = new Properties();

	public static String getPropertyValue(String propertyKey) throws IOException {
		String propertyValue = null;
		try {
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			InputStream stream = loader.getResourceAsStream("application3.properties");
			pro.load(stream);
			propertyValue = pro.getProperty(propertyKey);
		} catch (Exception e) {
			e.getMessage();
		}
		return propertyValue;
	}

	public WebDriver driver;

	@BeforeClass
	public void launchBrowser() throws Exception {
		File resourcesDirectory = new File("src/test/resources/chromedriver.exe");
		String absolutePath = resourcesDirectory.getAbsolutePath();
		System.setProperty("webdriver.chrome.driver", absolutePath);

		driver = new ChromeDriver();
		driver.get("https://www.makemytrip.com/flights/");
		driver.manage().window().maximize();
		// driver.navigate().refresh();

	}

	@Test
	public void searchFlight() throws Exception {
		Thread.sleep(5000);
		String sourceCity = getPropertyValue("sourceCity");
		String destinationCity = getPropertyValue("destinationCity");

		driver.findElement(By.xpath("//input[@id='fromCity']")).sendKeys(sourceCity);
		WebDriverWait wait = new WebDriverWait(driver, 10000);
		wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//li//div//p[contains(text(), '" + sourceCity + "')]")));
		driver.findElement(By.xpath("//li//div//p[contains(text(), '" + sourceCity + "')]")).click();

		Thread.sleep(2000);

		driver.findElement(By.xpath("//input[@id='toCity']")).sendKeys(destinationCity);
		Thread.sleep(2000);
		wait = new WebDriverWait(driver, 10000);
		wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//li//div//p[contains(text(), '" + destinationCity + "')]")));
		driver.findElement(By.xpath("//li//div//p[contains(text(), '" + destinationCity + "')]")).click();

		Thread.sleep(2000);

		String currentMonthYear = driver.findElement(By.xpath("//div[@class='DayPicker-Month'][1]//div[1]//div"))
				.getText();
		String currentYear = currentMonthYear.split(" ")[1];

		String travelDate = getPropertyValue("travelMonth");
		String expectedMonth = travelDate.split("-")[0];
		String expectedYear = travelDate.split("-")[1];

		// Selection of year
		if (currentYear.equals(expectedYear)) {
			System.out.println("Expected Year is already selected.");
		} else {
			for (int i = 1; i < 12; i++) {
				driver.findElement(By.xpath("//span[@aria-label='Next Month']")).click();
				Thread.sleep(1000);
				currentMonthYear = driver.findElement(By.xpath("//div[@class='DayPicker-Month'][1]//div[1]//div"))
						.getText();
				currentYear = currentMonthYear.split(" ")[1];
				if (currentYear.equals(expectedYear)) {
					System.out.println("Expected year is get selected now.");
					break;
				}
			}
		}

		// Selection of Month
		int next = 0;
		String currentMonth = currentMonthYear.split(" ")[0];
		if (currentMonth.equals(expectedMonth)) {
			next = 1;
			System.out.println("Expected month is already selected");
		} else {
			for (int i = 1; i < 12; i++) {
				if (driver.findElement(By.xpath("//span[@aria-label='Next Month']")).isDisplayed()) {
					driver.findElement(By.xpath("//span[@aria-label='Next Month']")).click();

					Thread.sleep(1000);
					currentMonthYear = driver.findElement(By.xpath("//div[@class='DayPicker-Month'][1]//div[1]//div"))
							.getText();
					currentMonth = currentMonthYear.split(" ")[0];
					if (currentMonth.equals(expectedMonth)) {
						next = 1;
						System.out.println("Expected month is get selected now.");
						break;
					}

				} else {
					currentMonthYear = driver.findElement(By.xpath("//div[@class='DayPicker-Month'][2]//div[1]//div"))
							.getText();
					currentMonth = currentMonthYear.split(" ")[0];
					if (currentMonth.equals(expectedMonth)) {
						next = 2;
						System.out.println("Expected month is get selected now.");
						break;
					}
				}

			}
		}

		driver.findElement(By.xpath("//div[@class='DayPicker-Month'][" + next + "]//p[text()=5]")).click();
		
		driver.findElement(By.xpath("//label[@for='departure']")).click();
		
		// fetching fares of this month:
		List<Integer> fareOfAllDays = new ArrayList<>();
		List<WebElement> fareOfAllDaysElement = driver.findElements(
				By.xpath("//div//div[@class='DayPicker-Month'][" + next + "]//p[@class='priceLow todayPrice']"));

		for (WebElement fareOfDayElement : fareOfAllDaysElement) {
			fareOfAllDays.add(Integer.parseInt(fareOfDayElement.getText()));
		}

		Collections.sort(fareOfAllDays);

		System.out.println(fareOfAllDays);
	}

}
