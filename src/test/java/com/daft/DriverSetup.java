package com.daft;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

/**
 * Class to provide the driver setup
 * */
public class DriverSetup {
    static WebDriver getBrowser(String browser) {
        WebDriver driver;

        // returns the driver based on the browser being requested
        switch(browser){
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            case "safari":
                WebDriverManager.safaridriver().setup();
                driver = new SafariDriver();
                break;
            default:
                // setting chrome as default browser
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
        }

        return driver;
    }
}
