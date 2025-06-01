package test;

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

public class FindAndInteractTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        // options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        // Configura uma espera explícita (WebDriverWait) com timeout de 10 segundos
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    void testGoogleSearchInteraction() {
        // Acessa a página de busca do Google
        driver.get("https://www.google.com");
        System.out.println("Navegou para: " + driver.getCurrentUrl());

        // Localiza o campo de busca (textarea com name='q')
        // Usando By.name como um dos seletores possíveis
        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("q")));
        System.out.println("Campo de busca localizado.");

        // Insere o texto "Selenium WebDriver" no campo de busca
        String searchText = "Selenium WebDriver";
        searchBox.sendKeys(searchText);
        System.out.println("Texto '" + searchText + "' inserido no campo de busca.");

        // Simula pressionar Enter para submeter a busca (alternativa ao clique no
        // botão)
        // searchBox.sendKeys(Keys.RETURN);

        // Localiza o botão "Pesquisa Google" (pode variar o seletor e texto)
        // Tentativa 1: Usando By.name("btnK") - comum, mas pode mudar
        // WebElement searchButton =
        // wait.until(ExpectedConditions.elementToBeClickable(By.name("btnK")));

        // Tentativa 2: Usando XPath mais robusto que busca o botão pelo valor ou
        // aria-label
        // Este XPath tenta encontrar um botão de input ou button com o texto 'Pesquisa
        // Google'
        // Nota: O seletor exato pode precisar de ajuste dependendo da versão/idioma do
        // Google
        WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath(
                        "(//input[@name='btnK' and @type='submit']) | (//button[contains(@aria-label, 'Pesquisa Google')])")));
        System.out.println("Botão de pesquisa localizado.");

        // Clica no botão de pesquisa
        // É importante esperar que o botão esteja clicável
        searchButton.click();
        System.out.println("Botão de pesquisa clicado.");

        // Espera um pouco para ver a página de resultados (opcional, melhor esperar por
        // um elemento específico)
        wait.until(ExpectedConditions.titleContains(searchText)); // Espera o título conter o termo pesquisado
        System.out.println("Página de resultados carregada. Título: " + driver.getTitle());
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
            System.out.println("Navegador fechado.");
        }
    }
}