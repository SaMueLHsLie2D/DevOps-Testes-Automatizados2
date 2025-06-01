package com.una.selenium.tests.exercise4;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Exercise 4: Verifying the Existence of Elements.
 * Navigates to the Google search results page for "Selenium WebDriver".
 * Writes a test that checks if a specific element (e.g., a link to the official Selenium website)
 * is present on the page using findElement() and handling NoSuchElementException,
 * or using findElements() to check if the list of elements is not empty.
 */
public class ElementExistenceTest {

    private WebDriver driver;
    private WebDriverWait wait;
    private final String SEARCH_TERM = "Selenium WebDriver";
    private final String EXPECTED_LINK_PARTIAL_HREF = "selenium.dev"; // Part of the official website URL

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
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Perform the search before the test
        driver.get("https://www.google.com");
        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("q")));
        searchBox.sendKeys(SEARCH_TERM);
        // Submit the search (can be done by clicking the button or pressing Enter)
        searchBox.submit(); // submit() works on form elements
        // Wait for the results page to load (checking the title)
        wait.until(ExpectedConditions.titleContains(SEARCH_TERM));
        System.out.println("Results page for '" + SEARCH_TERM + "' loaded.");
    }

    @Test
    void testElementExistenceUsingFindElement() {
        // Method 1: Using findElement() and handling the exception
        try {
            // Try to locate a link containing the URL of the official website
            // The selector might need adjustment depending on the structure of Google results
            WebElement seleniumLink = driver
                    .findElement(By.xpath("//a[contains(@href, '" + EXPECTED_LINK_PARTIAL_HREF + "')]"));
            // If the element is found, the test implicitly passes here
            assertTrue(seleniumLink.isDisplayed(), "Selenium link found, but not visible.");
            System.out
                    .println("Method 1: Link to '" + EXPECTED_LINK_PARTIAL_HREF + "' found via findElement().");
        } catch (NoSuchElementException e) {
            // If the element is not found, NoSuchElementException is thrown
            // JUnit Assertions.fail() forces the test to fail with a message
            fail("Method 1: Link to " + EXPECTED_LINK_PARTIAL_HREF + " not found on the page.", e);
        }
    }

    @Test
    void testElementExistenceUsingFindElements() {
        // Method 2: Using findElements() and checking the list size
        // findElements() returns an empty list if no elements are found, does not throw an exception
        List<WebElement> seleniumLinks = driver
                .findElements(By.xpath("//a[contains(@href, '" + EXPECTED_LINK_PARTIAL_HREF + "')]"));

        // Verify that the list of found elements is not empty
        assertFalse(seleniumLinks.isEmpty(),
                "Method 2: No link to " + EXPECTED_LINK_PARTIAL_HREF + " was found (list empty).");

        // Optional: Verify if at least one of the found links is displayed
        boolean isAnyLinkDisplayed = seleniumLinks.stream().anyMatch(WebElement::isDisplayed);
        assertTrue(isAnyLinkDisplayed,
                "Method 2: Link(s) to " + EXPECTED_LINK_PARTIAL_HREF + " found, but none are visible.");

        System.out.println("Method 2: At least one link to " + EXPECTED_LINK_PARTIAL_HREF
                + " found via findElements(). List size: " + seleniumLinks.size());
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
            System.out.println("Browser closed.");
        }
    }
}

