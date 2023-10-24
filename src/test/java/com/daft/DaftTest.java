package com.daft;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Arrays;
import java.util.Locale;

public class DaftTest {
    WebDriver driver;
    Wait<WebDriver> wait;

    Logger log = LoggerFactory.getLogger(DaftTest.class);

    public static final String BASE_URL = "https://www.daft.ie/";


    @BeforeSuite
    public void setUp(){
        log.info("Started setup for the driver..");
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get(BASE_URL);
    }

    @Test(priority = 1, groups = "homepage")
    public void checkHomePageAccess(){
        WebElement acceptButton = driver.findElement(By.id("didomi-notice-agree-button"));

        wait.until(d -> acceptButton.isDisplayed());

        acceptButton.click();

        String url = driver.getCurrentUrl();
        Assert.assertEquals(url, BASE_URL);
    }

    @Test(priority = 2 , groups = "homepage", dependsOnMethods = "checkHomePageAccess")
    public void searchBoxInput(){
        WebElement areaSearchBox = driver.findElement(By.id("search-box-input"));
        areaSearchBox.sendKeys("county dublin");

        try {
            Thread.sleep(1000);
            areaSearchBox.sendKeys(Keys.RETURN);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test(priority = 3, dependsOnGroups = "homepage")
    public void searchResultAreaPage(){
        wait.until(ExpectedConditions.urlToBe(BASE_URL+"property-for-sale/dublin"));

        WebElement searchResult = driver.findElement(By.xpath("//h1[@class='styles__SearchH1-sc-1t5gb6v-3 guZHZl']"));
        wait.until(d -> searchResult.isDisplayed());

        String res = searchResult.getText();
        System.out.println(Arrays.toString(res.split(" ")));
    }

    @Test(priority = 4, dependsOnGroups = "homepage")
    public void sendFilterKeyword(){
        WebElement filterButton = driver.findElement(By.xpath("//button[@aria-label='Filters']/span[@class='NewButton__ButtonText-yem86a-0 cIVIcD']"));
        wait.until(d -> filterButton.isDisplayed());

        filterButton.click();

        try {
            Thread.sleep(1000);
            WebElement filterKeywordBox = driver.findElement(By.id("keywordtermsModal"));
            filterKeywordBox.sendKeys("garage");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test(priority = 5, dependsOnMethods = "sendFilterKeyword")
    public void checkFilteredResult(){
        WebElement filterResButton = driver.findElement(By.xpath("//button[@class='NewButton__StyledButton-yem86a-2 klRAtd']"));
        filterResButton.click();

        try {
            Thread.sleep(1000);
            WebElement searchResultAfterFilter = driver.findElement(By.xpath("//h1[@class='styles__SearchH1-sc-1t5gb6v-3 guZHZl']"));
            wait.until(d -> searchResultAfterFilter.isDisplayed());

            String resAfterFilter = searchResultAfterFilter.getText();
            System.out.println("----");
            System.out.println(Arrays.toString(resAfterFilter.split(" ")));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test(priority = 6, dependsOnMethods = "checkFilteredResult")
    public void checkAnyFilteredRes(){
        try {
            Thread.sleep(1000);
            WebElement resElement = driver.findElement(By.xpath("//div[@class='SearchPagestyled__MainColumn-v8jvjf-0 jkVJyK']/ul/li[1]"));
            resElement.click();

            Thread.sleep(1000);
            WebElement adDescription = driver.findElement(By.xpath("//div[@class='styles__StandardParagraph-sc-15fxapi-8 eMCuSm']"));
            wait.until(d -> adDescription.isDisplayed());

            String desc = adDescription.getText().toLowerCase(Locale.ROOT);

            Assert.assertTrue(desc.contains("garage"));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @AfterSuite
    public void tearDown(){
        driver.quit();
        log.info("Quitting driver. \nTest ended.");
    }
}
