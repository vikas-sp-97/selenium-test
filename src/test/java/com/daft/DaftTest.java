package com.daft;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Locale;
import java.util.Properties;

public class DaftTest {
    WebDriver driver;
    Wait<WebDriver> wait;

    TestUtilities testUtil = new TestUtilities();
    Properties properties = testUtil.getProperties();

    Logger log = LoggerFactory.getLogger(DaftTest.class);

    private static String BASE_URL;

    @BeforeSuite
    @Parameters({"base_url", "browser", "wait_duration"})
    public void setUp(String base_url, String browser, Integer wait_duration) {
        log.info("Started setup for the driver..");
        driver = DriverSetup.getBrowser(browser);
        log.debug("driver setup complete");
        wait = new WebDriverWait(driver, Duration.ofSeconds(wait_duration));
        BASE_URL = base_url;
        driver.manage().window().maximize();
        driver.get(BASE_URL);
    }


    @Test(priority = 1, groups = "homepage")
    public void checkHomePageAccess() {

        log.info("Started test for the homepage");

        WebElement acceptButton = driver.findElement(By.id(properties.getProperty("accept_button_id")));
        wait.until(d -> acceptButton.isDisplayed());

        log.debug("attempting accept button click");
        acceptButton.click();

        String url = driver.getCurrentUrl();
        Assert.assertEquals(url, BASE_URL, "url mismatch!");

    }

    @Test(priority = 2, groups = "homepage", dependsOnMethods = "checkHomePageAccess")
    public void searchBoxInput() {
        log.info("Started test for search box");

        String area = "county dublin";
        WebElement areaSearchBox = driver.findElement(By.id(properties.getProperty("search_box_input_id")));
        log.debug("attempting to send area to search text box");
        areaSearchBox.sendKeys(area);

        threadWait();
        areaSearchBox.sendKeys(Keys.RETURN);
        log.info("executing search from homepage for area: " + area);
    }

    @Test(priority = 3, dependsOnGroups = "homepage")
    public void searchResultAreaPage() {
        log.info("Started test for search result page");

        threadWait();
        wait.until(ExpectedConditions.urlToBe(BASE_URL + "property-for-sale/dublin"));

        WebElement searchResult = driver.findElement(By.xpath(properties.getProperty("search_result_header_xpath")));
        wait.until(d -> searchResult.isDisplayed());

        Assert.assertTrue(searchResult.isDisplayed(), "result header element not found!");
        String res = searchResult.getText();
        log.debug("result header text: " + res);
    }

    @Test(priority = 4, dependsOnGroups = "homepage")
    public void sendFilterKeyword() {
        log.info("Started test for filter section");

        WebElement filterButton = driver.findElement(By.xpath(properties.getProperty("filter_button_xpath")));
        wait.until(d -> filterButton.isDisplayed());

        log.debug("attempting to access filter section");
        filterButton.click();

        threadWait();
        WebElement filterKeywordBox = driver.findElement(By.id(properties.getProperty("filter_keyword_box_id")));
        filterKeywordBox.sendKeys("garage");

    }

    @Test(priority = 5, dependsOnMethods = "sendFilterKeyword")
    public void checkFilteredResult() {
        log.info("Started test for filtered result");

        WebElement filterResButton = driver.findElement(By.xpath(properties.getProperty("filter_result_button_xpath")));
        log.debug("attempting filter button click");
        filterResButton.click();

        threadWait();
        WebElement searchResultAfterFilter = driver.findElement(By.xpath(properties.getProperty("search_result_header_element_xpath")));
        wait.until(d -> searchResultAfterFilter.isDisplayed());

        String resAfterFilter = searchResultAfterFilter.getText();
        log.debug("result text after filter keyword : " + resAfterFilter);

        Assert.assertTrue(searchResultAfterFilter.isDisplayed(), "Search result header element not found!");

    }

    @Test(priority = 6, dependsOnMethods = "checkFilteredResult")
    public void checkAnyFilteredRes() {
        log.info("Started test for verifying any one filtered result");
        String keyword = "garage";

        threadWait();
        WebElement resElement = driver.findElement(By.xpath(properties.getProperty("result_element_xpath")));
        log.debug("attempt to access a result from the posts");
        resElement.click();

        threadWait();
        WebElement adDescription = driver.findElement(By.xpath(properties.getProperty("result_ad_description_xpath")));
        wait.until(d -> adDescription.isDisplayed());

        log.debug("retrieving post description");
        String desc = adDescription.getText().toLowerCase(Locale.ROOT);

        Assert.assertTrue(desc.contains(keyword), "ad description doesn't contain keyword: " + keyword);
    }

    @AfterSuite
    public void tearDown() {
        driver.quit();
        log.info("Quitting driver. \nTest ended.");
    }

    public static void threadWait() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
