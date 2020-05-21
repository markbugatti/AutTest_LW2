import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;


public class TestGooglePage {
    public static WebDriver driver;
    static String baseUrl = "https://www.google.com/";
    static String actualString = "";

    @BeforeClass
    public static void setUp() throws MalformedURLException {
        driver = new RemoteWebDriver(new URL("http://localhost:9515"), DesiredCapabilities.chrome());
        driver.get(baseUrl);
    }

    @Test
    public void getTitle() {
        actualString = driver.getTitle();
        Assert.assertEquals(actualString, "Google");
    }

    @Test
    public void getLogo() {
        actualString = driver.findElement(By.cssSelector("meta[property='twitter:image']")).getAttribute("property");
        Assert.assertEquals(actualString, "twitter:image");
        System.out.println(actualString);
    }

    @Test
    public void getSearchString() {
        actualString = driver.findElement(By.className("RNNXgb")).getAttribute("class");
        Assert.assertEquals(actualString, "RNNXgb");
        System.out.println(actualString);
    }

    @Test
    public void getSearchButton() {
        actualString = driver.findElement(By.className("gNO89b")).getAttribute("name");
        Assert.assertEquals(actualString, "btnK");
    }

    @Test
    public void getGmail() {
        actualString = driver.findElement(By.className("gb_g")).getText();
        Assert.assertEquals(actualString, "Gmail");
        System.out.println(actualString);
    }

    @AfterClass
    public static void freeResources() {
        driver.quit();
    }


}
