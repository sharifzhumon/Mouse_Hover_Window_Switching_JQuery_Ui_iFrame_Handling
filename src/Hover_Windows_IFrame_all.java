import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Hover_Windows_IFrame_all {
	static WebDriver driver;
	static Actions a;
	static WebDriverWait w;

	// mouse hover
	static protected void mouseHover() {
		driver.get("http://the-internet.herokuapp.com/hovers");
		System.out.println(driver.getTitle());
		List<WebElement> li = driver.findElements(By.xpath("//div[@class='figure']"));

		for (WebElement it : li) {
			a.moveToElement(it).build().perform();
		}
	}

	// windows swicth
	static protected void multipleWindows() {
		driver.get("http://the-internet.herokuapp.com/windows");
		System.out.println(driver.getTitle());

		for (int i = 0; i < 1; i++) {

			driver.findElement(By.xpath("//a[text()='Elemental Selenium']")).click();
			driver.findElement(By.xpath("//a[text()='Click Here']")).click();
		}

		// get all open window ids
		Set<String> totalWindows = driver.getWindowHandles();

		System.out.println(totalWindows.size());

		// iterate the ids
		Iterator<String> it = totalWindows.iterator();

		String[] a = new String[totalWindows.size()];

		// switching to windows and get titles
		for (int i = 0; i < totalWindows.size(); i++) {
			a[i] = it.next();
			driver.switchTo().window(a[i]);
			System.out.println(driver.getTitle());
			
			if(i>0) {
				driver.close();
			}
		}
		// back to parent window
		driver.switchTo().window(a[0]);
		System.out.println(driver.getTitle());

	}

	// JQUERYUI

	// Iframe drag and drop
	static protected void drag_drop() {
		driver.get("https://jqueryui.com/droppable/");
		System.out.println(driver.getTitle());

		// scroll down the page to execute further test
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,350)", "");

		// switch to iframe
		WebElement frame = driver.findElement(By.tagName("iframe"));
		driver.switchTo().frame(frame);

		// drag and drop
		WebElement dragSource = driver.findElement(By.id("draggable"));
		WebElement dropSource = driver.findElement(By.id("droppable"));
		a.dragAndDrop(dragSource, dropSource).build().perform();

	}

	// Iframe selectable
	static protected void selectable() {
		driver.get("https://jqueryui.com/selectable/");
		System.out.println(driver.getTitle());

		// scroll down the page to execute further test
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,350)", "");


		// switch to iframe
		WebElement frame = driver.findElement(By.tagName("iframe"));
		driver.switchTo().frame(frame);

		List<WebElement> li = driver.findElements(By.cssSelector("#selectable li"));

		// explicit wait for element to clickable
		w.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#selectable li")));
		a.keyDown(Keys.CONTROL);

		for (int i = 0; i < li.size(); i++) {

			if (i % 2 == 0) {
				a.moveToElement(li.get(i)).click().build().perform();
			}

		}

	}

	// Iframe sorting elements
	static protected void Sorting() {
		driver.get("https://jqueryui.com/sortable/");
		System.out.println(driver.getTitle());

		// scroll down the page to execute further test
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,350)", "");

		// switch to iframe
		WebElement frame = driver.findElement(By.tagName("iframe"));
		driver.switchTo().frame(frame);

		// sorting elements
		WebElement itemSource = driver.findElement(By.xpath("//li[text()='Item 4']"));
		WebElement itemTarget1 = driver.findElement(By.xpath("//li[text()='Item 1']"));
		WebElement itemTarget2 = driver.findElement(By.xpath("//li[text()='Item 5']"));

		// sorting
		a.clickAndHold(itemSource).moveToElement(itemTarget1).moveByOffset(0, -50).release().build().perform();
//		a.clickAndHold(itemSource).moveToElement(itemTarget).moveByOffset(0, 10).release().build().perform();
		a.clickAndHold(itemSource).moveToElement(itemTarget2, 0, 20).release().build().perform();

	}

	static protected void resizable() {
		driver.get("https://jqueryui.com/resizable/");
		System.out.println(driver.getTitle());

		// scroll down the page to execute further test
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,400)", "");

		// switch to iframe
		WebElement frame = driver.findElement(By.tagName("iframe"));
		driver.switchTo().frame(frame);

		w.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("resizable"))));

		// resizing element
		WebElement resize = driver.findElement(By
				.cssSelector("div[class='ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se']"));

		// resize
		a.clickAndHold(resize).moveByOffset(400, 200).release().build().perform();
		System.out.println("performed");
	}

	public static void main(String[] args) {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Demiurges\\Documents\\drivers\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		w = new WebDriverWait(driver, 5);
		a = new Actions(driver);

		mouseHover();
		multipleWindows();
		drag_drop();
		selectable();
		Sorting();
		resizable();

		driver.close();

	}

}
