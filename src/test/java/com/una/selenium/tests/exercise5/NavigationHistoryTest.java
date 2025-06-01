package com.una.selenium.tests.exercise5;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Exercise 5: Historical Navigation.
 * Accesses a web page (e.g., Google homepage).
 * Navigates to another page (e.g., "About Google" page).
 * Uses driver.navigate().back() to return to the previous page.
 * Uses driver.navigate().forward() to advance again.
 * Uses driver.navigate().refresh() to reload the current page.
 * Verifies the page title at each step to ensure correct navigation.
 */
public class NavigationHistoryTest {

    private WebDriver driver;
    private WebDriverWait wait;
    private final String GOOGLE_URL = "https://www.google.com";
    // We will use an internal Google page, like "About", for the example
    // The exact link might change, so we locate it dynamically
    private final String ABOUT_LINK_TEXT_PT = "Sobre"; // Portuguese
    private final String ABOUT_LINK_TEXT_EN = "About"; // English

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
    }

    @Test
    void testBrowserHistoryNavigation() {
        // 1. Access the Google homepage
        driver.get(GOOGLE_URL);
        String initialTitle = driver.getTitle();
        assertEquals("Google", initialTitle, "Initial title is not Google.");
        System.out.println("1. On homepage: " + initialTitle);

        // 2. Navigate to another page (e.g., "About Google")
        // Locate the "About" link (may need to adjust text or selector)
        WebElement aboutLink = null;
        try {
            // Try Portuguese first
            aboutLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText(ABOUT_LINK_TEXT_PT)));
            aboutLink.click();
        } catch (Exception e) {
            System.out.println("Link '" + ABOUT_LINK_TEXT_PT + "' not found, trying '" + ABOUT_LINK_TEXT_EN + "'...");
            // Fallback if "Sobre" is not found, try "About"
            try {
                aboutLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText(ABOUT_LINK_TEXT_EN)));
                aboutLink.click();
            } catch (Exception e2) {
                System.err.println("Could not find the link '" + ABOUT_LINK_TEXT_PT + "' or '" + ABOUT_LINK_TEXT_EN + "'. Skipping navigation to the second page.");
                // If the link is not found, the test might stop or continue differently
                // Here, we just log and continue to demonstrate back/forward
                // In a real test, this could be a failure.
                // For demonstration, let's navigate somewhere else manually if the link fails
                driver.get("https://about.google/"); // Navigate directly if link click fails
            }
        }

        // Wait for the new page to load (check if the title changed)
        wait.until(ExpectedConditions.not(ExpectedConditions.titleIs(initialTitle)));
        String secondPageTitle = driver.getTitle();
        System.out.println("2. Navigated to second page: " + secondPageTitle);
        // Check if it's still a Google-related page (title might vary)
        assertTrue(secondPageTitle.toLowerCase().contains("google"), "Second page title does not contain 'google'.");

        // 3. Use driver.navigate().back() to return to the previous page (homepage)
        System.out.println("3. Navigating back...");
        driver.navigate().back();
        wait.until(ExpectedConditions.titleIs(initialTitle)); // Wait for the title to revert to the initial one
        assertEquals(initialTitle, driver.getTitle(), "Title after 'back' is not the initial one.");
        System.out.println("   Returned to: " + driver.getTitle());

        // 4. Use driver.navigate().forward() to advance again (to the second page)
        System.out.println("4. Navigating forward...");
        driver.navigate().forward();
        // Wait for the title to revert to the second page's title
        // Use a more robust wait condition if the title is dynamic
        wait.until(ExpectedConditions.titleIs(secondPageTitle));
        assertEquals(secondPageTitle, driver.getTitle(), "Title after 'forward' is not the second page's title.");
        System.out.println("   Advanced to: " + driver.getTitle());

        // 5. Use driver.navigate().refresh() to reload the current page (second page)
        System.out.println("5. Refreshing the page...");
        driver.navigate().refresh();
        // Wait a bit or check an element to ensure it reloaded
        // The title should remain the same after refresh
        wait.until(ExpectedConditions.titleIs(secondPageTitle));
        assertEquals(secondPageTitle, driver.getTitle(), "Title changed after 'refresh'.");
        System.out.println("   Refreshed: " + driver.getTitle());
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
            System.out.println("Browser closed.");
        }
    }
}

