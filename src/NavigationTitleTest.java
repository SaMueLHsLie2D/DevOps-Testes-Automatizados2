
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NavigationTitleTest {

    private WebDriver driver;

    @BeforeEach
    void setUp() {
        // Configura o WebDriver antes de cada teste
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        // options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize(); // Maximiza a janela
    }

    @Test
    void testGoogleTitle() {
        // Navega até a página inicial do Google
        driver.get("https://www.google.com");
        System.out.println("Navegou para: " + driver.getCurrentUrl());

        // Obtém o título da página atual
        String pageTitle = driver.getTitle();
        System.out.println("Título da página: " + pageTitle);

        // Define o título esperado
        String expectedTitle = "Google";

        // Realiza a asserção para verificar se o título obtido é igual ao esperado
        assertEquals(expectedTitle, pageTitle, "O título da página não é o esperado!");
        System.out.println("Asserção do título passou!");
    }

    @AfterEach
    void tearDown() {
        // Fecha o navegador após cada teste
        if (driver != null) {
            driver.quit();
            System.out.println("Navegador fechado após o teste.");
        }
    }
}
