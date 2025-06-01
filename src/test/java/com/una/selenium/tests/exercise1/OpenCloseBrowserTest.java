package com.una.selenium.tests.exercise1;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Exercise 1: Opening and Closing a Browser.
 * Writes a script using Selenium WebDriver to open Google Chrome,
 * waits for 5 seconds, and then closes the browser.
 */
public class OpenCloseBrowserTest {

    private WebDriver driver;

    @BeforeEach
    void setUp() {
        // Set up WebDriverManager to manage ChromeDriver
        WebDriverManager.chromedriver().setup();

        // Configure Chrome options (optional but useful)
        ChromeOptions options = new ChromeOptions();
        // options.addArguments("--headless"); // To run without opening the browser window
        options.addArguments("--start-maximized"); // Start maximized
        options.addArguments("--disable-gpu"); // Sometimes necessary
        options.addArguments("--no-sandbox"); // Sometimes necessary in CI environments
        options.addArguments("--disable-dev-shm-usage"); // Overcome limited resource problems

        // Instantiate WebDriver for Chrome
        driver = new ChromeDriver(options);
        System.out.println("Chrome browser opened for test.");
    }

    @Test
    void testOpenWaitAndCloseBrowser() {
        // The browser is already opened in setUp()
        assertNotNull(driver, "WebDriver should be initialized.");
        System.out.println("WebDriver instance created.");

        try {
            // Add a wait of 5 seconds
            // In real tests, prefer explicit waits (WebDriverWait) over Thread.sleep
            System.out.println("Waiting for 5 seconds...");
            Thread.sleep(Duration.ofSeconds(5).toMillis());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore interruption status
            System.err.println("Thread interrupted during wait: " + e.getMessage());
        }
        // The browser will be closed in tearDown()
        System.out.println("Test logic completed, browser will be closed.");
    }

    @AfterEach
    void tearDown() {
        // Close all browser windows opened by the WebDriver instance and end the session
        if (driver != null) {
            driver.quit();
            System.out.println("Chrome browser closed after test.");
        }
    }

    // Challenge: Adapt for other browsers
    /*
    For Firefox:
    WebDriverManager.firefoxdriver().setup();
    WebDriver driver = new FirefoxDriver();

    For Edge:
    WebDriverManager.edgedriver().setup();
    WebDriver driver = new EdgeDriver();
    */
}

