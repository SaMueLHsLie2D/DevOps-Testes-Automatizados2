package test;

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

public class NavigationHistoryTest {

    private WebDriver driver;
    private WebDriverWait wait;
    private final String GOOGLE_URL = "https://www.google.com";
    // Usaremos uma página interna do Google, como a de "Sobre", para o exemplo
    // O link exato pode mudar, então localizaremos dinamicamente
    private final String ABOUT_LINK_TEXT = "Sobre"; // Ou "About" dependendo do idioma

    @BeforeEach
    void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        // options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    void testBrowserHistoryNavigation() {
        // 1. Acessa a página inicial do Google
        driver.get(GOOGLE_URL);
        String initialTitle = driver.getTitle();
        assertEquals("Google", initialTitle, "Título inicial não é Google.");
        System.out.println("1. Na página inicial: " + initialTitle);

        // 2. Navega para outra página (ex: "Sobre o Google")
        // Localiza o link "Sobre" (pode precisar ajustar o texto ou seletor)
        try {
            WebElement aboutLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText(ABOUT_LINK_TEXT)));
            aboutLink.click();
        } catch (Exception e) {
            // Fallback se "Sobre" não for encontrado, tenta "About"
            try {
                WebElement aboutLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("About")));
                aboutLink.click();
            } catch (Exception e2) {
                System.err.println(
                        "Não foi possível encontrar o link 'Sobre' ou 'About'. Pulando a navegação para a segunda página.");
                // Se não encontrar o link, o teste pode parar ou continuar de outra forma
                // Aqui, vamos apenas logar e continuar para demonstrar back/forward
                // Em um teste real, isso poderia ser uma falha.
            }
        }

        // Espera a nova página carregar (verifica se o título mudou)
        wait.until(ExpectedConditions.not(ExpectedConditions.titleIs(initialTitle)));
        String secondPageTitle = driver.getTitle();
        System.out.println("2. Navegou para a segunda página: " + secondPageTitle);
        assertTrue(secondPageTitle.contains("Google"), "Título da segunda página não contém Google."); // Verifica se
                                                                                                       // ainda é uma
                                                                                                       // página do
                                                                                                       // Google

        // 3. Utiliza driver.navigate().back() para voltar à página anterior (inicial)
        System.out.println("3. Navegando para trás (back)...");
        driver.navigate().back();
        wait.until(ExpectedConditions.titleIs(initialTitle)); // Espera o título voltar ao inicial
        assertEquals(initialTitle, driver.getTitle(), "Título após 'back' não é o inicial.");
        System.out.println("   Voltou para: " + driver.getTitle());

        // 4. Utiliza driver.navigate().forward() para avançar novamente (para a segunda
        // página)
        System.out.println("4. Navegando para frente (forward)...");
        driver.navigate().forward();
        wait.until(ExpectedConditions.titleIs(secondPageTitle)); // Espera o título voltar ao da segunda página
        assertEquals(secondPageTitle, driver.getTitle(), "Título após 'forward' não é o da segunda página.");
        System.out.println("   Avançou para: " + driver.getTitle());

        // 5. Utiliza driver.navigate().refresh() para recarregar a página atual
        // (segunda página)
        System.out.println("5. Recarregando a página (refresh)...");
        driver.navigate().refresh();
        // Espera um pouco ou verifica um elemento para garantir que recarregou
        wait.until(ExpectedConditions.titleIs(secondPageTitle)); // O título deve permanecer o mesmo
        assertEquals(secondPageTitle, driver.getTitle(), "Título após 'refresh' mudou.");
        System.out.println("   Recarregou: " + driver.getTitle());
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
            System.out.println("Navegador fechado.");
        }
    }
}
