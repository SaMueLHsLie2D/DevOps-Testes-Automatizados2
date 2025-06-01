package com.una.selenium.tests.exercise6;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils; // Needs commons-io dependency
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Exercise 6: Capturing Screenshots.
 * Modifies a previous test to capture a screenshot of the page in case of test failure.
 * Uses the TakesScreenshot interface from Selenium and saves the image to a specific directory.
 */
public class ScreenshotOnFailureTest {

    // IMPORTANT: WebDriver needs to be accessible in the TestWatcher.
    // Making it static simplifies this example, but BE CAREFUL with parallel execution!
    // A better approach in complex scenarios might involve custom extensions or context management.
    static WebDriver driver;

    // Register the TestWatcher extension
    @RegisterExtension
    ScreenshotTestWatcher watcher = new ScreenshotTestWatcher();

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
    }

    @Test
    void testGoogleTitle_Success() {
        driver.get("https://www.google.com");
        String pageTitle = driver.getTitle();
        assertEquals("Google", pageTitle, "The page title is not as expected!");
        System.out.println("Success test: Title verified.");
    }

    @Test
    void testGoogleTitle_Failure() {
        driver.get("https://www.google.com");
        String pageTitle = driver.getTitle();
        // Force an assertion failure to test screenshot capture
        assertEquals("Gooogle", pageTitle, "The page title is not as expected (intentional failure)!");
        System.out.println("This print will not be executed due to the previous failure.");
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
            System.out.println("Browser closed.");
        }
    }

    // Inner or outer class implementing TestWatcher
    // Needs access to the WebDriver instance to take the screenshot
    public static class ScreenshotTestWatcher implements TestWatcher {

        @Override
        public void testFailed(ExtensionContext context, Throwable cause) {
            System.out.println("Test failed: " + context.getDisplayName() + ". Capturing screenshot...");
            if (driver instanceof TakesScreenshot) {
                File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                try {
                    // Create a unique filename with timestamp
                    String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                    // Get test method name safely
                    String testMethodName = context.getTestMethod().map(method -> method.getName()).orElse("unknown_test");
                    String filename = "screenshot_" + testMethodName + "_" + timestamp + ".png";

                    // Define the directory to save (e.g., target/screenshots)
                    // Using target/ is standard for Maven build outputs
                    File destDir = new File("target/screenshots");
                    if (!destDir.exists()) {
                        destDir.mkdirs(); // Create the directory if it doesn't exist
                    }
                    File destFile = new File(destDir, filename);

                    FileUtils.copyFile(screenshot, destFile); // Use Apache Commons IO
                    System.out.println("Screenshot saved to: " + destFile.getAbsolutePath());
                } catch (IOException e) {
                    System.err.println("Error saving screenshot: " + e.getMessage());
                }
            } else {
                System.err.println("Current WebDriver does not support screenshots.");
            }
        }

        // Optional: Implement other TestWatcher methods if needed (testSuccessful, testAborted, etc.)
    }
}

