package com.una.selenium.tests.exercise3;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Exercise 3: Locating Elements and Interacting.
 * Accesses the Google search page (https://www.google.com).
 * Locates the search field using a selector (ID, Name, CSS, or XPath).
 * Enters the text "Selenium WebDriver" into the search field using sendKeys().
 * Locates the "Google Search" or "I'm Feeling Lucky" button and clicks it using click().
 */
public class FindAndInteractTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        // options.addArguments("--headless");
        options.addArguments("--start-maximized");
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        // Configure an explicit wait (WebDriverWait) with a 10-second timeout
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    void testGoogleSearchInteraction() {
        // Access the Google search page
        driver.get("https://www.google.com");
        System.out.println("Navigated to: " + driver.getCurrentUrl());

        // Locate the search field (textarea with name='q')
        // Using By.name as one possible selector
        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("q")));
        System.out.println("Search field located.");

        // Enter the text "Selenium WebDriver" into the search field
        String searchText = "Selenium WebDriver";
        searchBox.sendKeys(searchText);
        System.out.println("Text '" + searchText + "' entered into search field.");

        // Simulate pressing Enter to submit the search (alternative to clicking the button)
        // searchBox.sendKeys(Keys.RETURN);
        // Or use submit() on the form element itself
        // searchBox.submit();

        // Locate the "Google Search" button (selector and text might vary)
        // Attempt 1: Using By.name("btnK") - common, but might change
        // WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(By.name("btnK")));

        // Attempt 2: Using a more robust XPath that searches for the button by value or aria-label
        // This XPath tries to find an input or button element with the text 'Google Search'
        // Note: The exact selector might need adjustment depending on Google's version/language
        // It's crucial to wait for the button to be clickable, especially if elements load dynamically
        WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("(//input[@name='btnK' and @type='submit'])[2] | //button[contains(@aria-label, 'Google Search')]"))
        );
        // Note: Sometimes there are multiple 'btnK' inputs, the second one is often the visible search button.
        // Adjust the XPath index [2] or selector as needed based on inspection.

        System.out.println("Search button located.");

        // Click the search button
        searchButton.click();
        System.out.println("Search button clicked.");

        // Wait for the results page to load (best practice: wait for a specific element on the results page)
        // Here, we wait for the title to contain the search term
        wait.until(ExpectedConditions.titleContains(searchText));
        System.out.println("Results page loaded. Title: " + driver.getTitle());
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
            System.out.println("Browser closed.");
        }
    }

    /*
    Challenge: Explore different locators
    - By ID: If the search field had a unique ID (e.g., By.id("APjFqb")) - IDs can be dynamic!
    - By CSS Selector: By.cssSelector("textarea[name='q']") or By.cssSelector("#APjFqb")
    - By XPath: By.xpath("//textarea[@name='q']")

    For the button:
    - By CSS Selector: By.cssSelector("input[name='btnK']")
    - By XPath (alternative): By.xpath("//div[contains(@class, 'FPdoLc')]//input[@name='btnK']")
    */
}

