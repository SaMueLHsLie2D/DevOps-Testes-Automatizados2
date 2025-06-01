package com.una.selenium.tests.exercise2;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Exercise 2: Navigation and Title Verification.
 * Creates a test that navigates to the Google homepage (https://www.google.com).
 * Uses driver.getTitle() to get the page title.
 * Implements an assertion (using JUnit) to verify if the page title is "Google".
 */
public class NavigationTitleTest {

    private WebDriver driver;

    @BeforeEach
    void setUp() {
        // Set up WebDriver before each test
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        // options.addArguments("--headless");
        options.addArguments("--start-maximized"); // Start maximized
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize(); // Maximize the window
    }

    @Test
    void testGoogleTitle() {
        // Navigate to the Google homepage
        driver.get("https://www.google.com");
        System.out.println("Navigated to: " + driver.getCurrentUrl());

        // Get the current page title
        String pageTitle = driver.getTitle();
        System.out.println("Page title: " + pageTitle);

        // Define the expected title
        String expectedTitle = "Google";

        // Perform the assertion to verify if the obtained title matches the expected one
        assertEquals(expectedTitle, pageTitle, "The page title is not as expected!");
        System.out.println("Title assertion passed!");
    }

    @AfterEach
    void tearDown() {
        // Close the browser after each test
        if (driver != null) {
            driver.quit();
            System.out.println("Browser closed after test.");
        }
    }
}

