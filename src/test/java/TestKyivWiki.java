import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class TestKyivWiki {
    public static WebDriver driver;
    static String baseUrl = "https://www.google.com/";
    static String actualString = "";
    static ChromeOptions chromeOptions;

    @BeforeClass
    public static void setUp() throws MalformedURLException {
        try {
            chromeOptions = new ChromeOptions();
            chromeOptions.setCapability("browserVersion", "83");
            chromeOptions.setCapability("platformName", "Windows 10");

            driver = new RemoteWebDriver(new URL("https://uk.wikipedia.org/wiki/Київ"), chromeOptions);
            driver.get(baseUrl);
        } catch (Exception e) {
            System.out.println("Error in setUp: " + e.getMessage());
        }
    }

    @Test
    public void GetFlag() {
        WebElement element = driver.findElement(By.className("infobox"));
        actualString = element.getText();
    }



    @AfterClass
    public static void freeResource() {
        driver.quit();
    }
}
