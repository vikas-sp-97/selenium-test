package com.daft;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.util.*;

/**
 * @Author Vikas
 *
 */
public class Daft
{
    public static void main( String[] args ){

    }

    public void someFunc() throws InterruptedException {
        WebDriverManager.chromedriver().setup();

        WebDriver driver = new ChromeDriver();

        driver.get("https://www.daft.ie/");
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement acceptButton = driver.findElement(By.id("didomi-notice-agree-button"));

        wait.until(d -> acceptButton.isDisplayed());

        acceptButton.click();

        WebElement areaSearchBox = driver.findElement(By.id("search-box-input"));
        areaSearchBox.sendKeys("county dublin");

        System.out.println("found search box");
        Thread.sleep(1000);

        areaSearchBox.sendKeys(Keys.RETURN);
        System.out.println("found 2");
        wait.until(ExpectedConditions.urlToBe("https://www.daft.ie/property-for-sale/dublin"));

        WebElement searchResult = driver.findElement(By.xpath("//h1[@class='styles__SearchH1-sc-1t5gb6v-3 guZHZl']"));
        wait.until(d -> searchResult.isDisplayed());

        String res = searchResult.getText();
        System.out.println(Arrays.toString(res.split(" ")));

        WebElement filterButton = driver.findElement(By.xpath("//button[@aria-label='Filters']/span[@class='NewButton__ButtonText-yem86a-0 cIVIcD']"));
        wait.until(d -> filterButton.isDisplayed());

        filterButton.click();

        Thread.sleep(1000);
        WebElement filterKeywordBox = driver.findElement(By.id("keywordtermsModal"));
        filterKeywordBox.sendKeys("garage");

        WebElement filterResButton = driver.findElement(By.xpath("//button[@class='NewButton__StyledButton-yem86a-2 klRAtd']"));
        filterResButton.click();

        Thread.sleep(1000);
        WebElement searchResultAfterFilter = driver.findElement(By.xpath("//h1[@class='styles__SearchH1-sc-1t5gb6v-3 guZHZl']"));
        wait.until(d -> searchResultAfterFilter.isDisplayed());

        String resAfterFilter = searchResultAfterFilter.getText();
        System.out.println(Arrays.toString(resAfterFilter.split(" ")));


        Thread.sleep(1000);
        WebElement resElement = driver.findElement(By.xpath("//div[@class='SearchPagestyled__MainColumn-v8jvjf-0 jkVJyK']/ul/li[1]"));
        resElement.click();

        Thread.sleep(1000);
        WebElement adDescription = driver.findElement(By.xpath("//div[@class='styles__StandardParagraph-sc-15fxapi-8 eMCuSm']"));
        wait.until(d -> adDescription.isDisplayed());

        String desc = adDescription.getText().toLowerCase(Locale.ROOT);
        if(desc.contains("garage")){
            System.out.println("keyword present in desc");
            driver.quit();
        }
    }
}
//114.0.5735.90