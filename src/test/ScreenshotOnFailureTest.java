package test;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils; // Precisa adicionar a dependência commons-io
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScreenshotOnFailureTest {

    // Importante: O WebDriver precisa ser acessível no TestWatcher
    // Torná-lo estático ou usar injeção de dependência com extensões mais avançadas
    // Para simplificar, faremos o WebDriver estático neste exemplo, mas CUIDADO com
    // paralelismo!
    // Uma abordagem melhor seria usar uma extensão personalizada.
    static WebDriver driver;

    // Registra a extensão TestWatcher
    @RegisterExtension
    ScreenshotTestWatcher watcher = new ScreenshotTestWatcher();

    @BeforeEach
    void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        // options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
    }

    @Test
    void testGoogleTitle_Success() {
        driver.get("https://www.google.com");
        String pageTitle = driver.getTitle();
        assertEquals("Google", pageTitle, "O título da página não é o esperado!");
        System.out.println("Teste de sucesso: Título verificado.");
    }

    @Test
    void testGoogleTitle_Failure() {
        driver.get("https://www.google.com");
        String pageTitle = driver.getTitle();
        // Força uma falha na asserção para testar a captura de screenshot
        assertEquals("Gooogle", pageTitle, "O título da página não é o esperado (falha intencional)!");
        System.out.println("Este print não será executado devido à falha anterior.");
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
            System.out.println("Navegador fechado.");
        }
    }

    // Classe interna ou externa que implementa TestWatcher
    // Precisa ter acesso à instância do WebDriver para tirar a screenshot
    public static class ScreenshotTestWatcher implements org.junit.jupiter.api.extension.TestWatcher {

        @Override
        public void testFailed(org.junit.jupiter.api.extension.ExtensionContext context, Throwable cause) {
            System.out.println("Teste falhou: " + context.getDisplayName() + ". Capturando screenshot...");
            if (driver instanceof TakesScreenshot) {
                File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                try {
                    // Cria um nome de arquivo único com timestamp
                    String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                    String filename = "screenshot_" + context.getTestMethod().get().getName() + "_" + timestamp
                            + ".png";
                    // Define o diretório para salvar (ex: target/screenshots)
                    File destDir = new File("target/screenshots");
                    destDir.mkdirs(); // Cria o diretório se não existir
                    File destFile = new File(destDir, filename);

                    FileUtils.copyFile(screenshot, destFile); // Usa Apache Commons IO
                    System.out.println("Screenshot salva em: " + destFile.getAbsolutePath());
                } catch (IOException e) {
                    System.err.println("Erro ao salvar screenshot: " + e.getMessage());
                }
            } else {
                System.err.println("O WebDriver atual não suporta screenshots.");
            }
        }
    }
}
