import org.junit.AfterClass;
import org.junit.Assert;
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
import java.util.List;

public class TestKyivWiki {
    public static WebDriver driver;
    static String baseUrl = "https://uk.wikipedia.org/wiki/Київ";
    // string for different answers
    static String actualString = "";
    static ChromeOptions chromeOptions;
    // same tr List for population, average temperature
    static List<WebElement> trElements = null;

    @BeforeClass
    public static void setUp() {
        try {
            chromeOptions = new ChromeOptions();
            chromeOptions.setCapability("browserVersion", "83");
            chromeOptions.setCapability("platformName", "Windows 10");

            driver = new RemoteWebDriver(new URL("http://localhost:9515"), chromeOptions);
            driver.get(baseUrl);

            // initialize trElements
            trElements = driver.findElements(By.cssSelector("tr"));
        } catch (Exception e) {
            System.out.println("Error in setUp: " + e.getMessage());
        }
    }

    @Test
    public void GetFlag() {
        WebElement element = driver.findElement(By.cssSelector("img[alt*='COA of Kyiv Kurovskyi']"));
        actualString = element.getText();
    }

    @Test
    public void GetPopulation() {
        // Find all tr (Table Row) in html

        actualString = "";

        // Iterate all tr's until element which contains "Населення" is found.
        for (WebElement element : trElements) {
            WebElement linkElement = null;
            try {
                linkElement = element.findElement(By.linkText("Населення"));
            } catch (Exception e) {
                System.out.println("Error in GetPopulation: " + e.getMessage());
            }
            // if Link Element != null -> tr which contains Population is found.
            if(linkElement != null) {
                // obtain Population number from td inside current tr.
                actualString = element.findElement(By.cssSelector("td")).getText();
                break;
            }
        }
        System.out.println("Population of Ukraine: " + actualString);
    }

    @Test
    public void GetAprilAverageTemp() {
        // Find row for "Показник"
        // Find number of Column which contains April and save it;
        // Find row for "Середня температура"
        // get column with has the same number as saved in step 2.

        WebElement table = null;
        WebElement baseElement = null;
        List<WebElement> rowElement = null;
        boolean isMonthFound = false;

        int columnNumber = 0;

        try {
            table = driver.findElement(By.id("collapsibleTable0"));
            //table.findElement(By.)
        } catch (Exception e) {
            System.out.println(e);
        }

        baseElement = getParentBasedOnText(table, "Показник");

        if(baseElement != null) {
            rowElement = baseElement.findElements(By.cssSelector("th"));
            // iterate through workingElement, until April column is found.
            for (columnNumber = 0; columnNumber < rowElement.size(); columnNumber++) {
                System.out.println(rowElement.get(columnNumber).getText());
                if(rowElement.get(columnNumber).getText().equals("Кві")) {
                    //now columnNumber is saved;
                    isMonthFound = true;
                    break;
                }
            }
        }

        actualString = "";
        if(isMonthFound) {
            baseElement = getParentBasedOnText(table, "Середня температура");

            rowElement = baseElement.findElements(By.cssSelector("th"));
            actualString = rowElement.get(columnNumber).getText();
            System.out.println("avarage Temperature in April is: " + actualString + " °C");
        }
        Assert.assertTrue(actualString != "");
    }

    public WebElement getParentBasedOnText(WebElement baseElement, String innerText) {
        WebElement foundedElement = null;
        try {
            foundedElement = baseElement.findElement(By.xpath("//*[contains(text(),'" + innerText + "')]"));
            // obtain parent element.
            foundedElement = foundedElement.findElement(By.xpath("./.."));
        } catch (Exception e) {
        }
        return foundedElement;
    }

    @Test
    public void CoronavirusTopic() {

        WebElement contentsBlock = null;
        WebElement coronavirusLink = null;

        try {
            contentsBlock = driver.findElement(By.id("toc"));
            coronavirusLink = contentsBlock.findElement(By.partialLinkText("Епідемія коронавірусу"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        if(coronavirusLink != null) {
            System.out.println("Пункт про короновірус: " + coronavirusLink.getText());
        }
        Assert.assertTrue(coronavirusLink != null);
    }

    @Test
    public void populationDensity() {

        actualString = "";
        // Iterate all tr's until element which contains "Населення" is found.
        for (WebElement element : trElements) {
            WebElement linkElement = null;
            try {
                linkElement = element.findElement(By.linkText("Густота населення"));
            } catch (Exception e) {
            }
            // if Link Element != null -> tr which contains Population is found.
            if(linkElement != null) {
                // obtain Population number from td inside current tr.
                actualString = element.findElement(By.cssSelector("td")).getText();
                break;
            }
        }
        System.out.println("Population density of Ukraine: " + actualString);
    }

    // Task: assert that monuments count > 20
    // find by css selection title = Золоті ворота (брама Києва)
    // step up two levels
    // get by css selection all li items and save into List object
    // get size of List object
    // Assert that list size > 20
    @Test
    public void monumentsCount() {
        WebElement GoldenGatesElement;
        WebElement ulElement;
        List<WebElement> liElements = null;
        int monumentsCount = 0;
        try {
            GoldenGatesElement = driver.findElement(By.cssSelector("a[title*='Золоті ворота (брама Києва)']"));
            ulElement = GoldenGatesElement.findElement(By.xpath("./../.."));
            liElements = ulElement.findElements(By.cssSelector("li"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        if(liElements != null) {
            monumentsCount = liElements.size();
            System.out.println("Count of monuments: " + monumentsCount);
        }
        Assert.assertTrue(monumentsCount > 20);
    }

    @AfterClass
    public static void freeResource() {
        driver.quit();
    }
}
