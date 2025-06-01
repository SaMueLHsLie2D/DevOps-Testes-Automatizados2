package test;

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

public class ElementExistenceTest {

    private WebDriver driver;
    private WebDriverWait wait;
    private final String SEARCH_TERM = "Selenium WebDriver";
    private final String EXPECTED_LINK_PARTIAL_HREF = "selenium.dev"; // Parte da URL do site oficial

    @BeforeEach
    void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        // options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Realiza a busca antes do teste
        driver.get("https://www.google.com");
        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("q")));
        searchBox.sendKeys(SEARCH_TERM);
        // Submete a busca (pode ser clicando no botão ou pressionando Enter)
        searchBox.submit(); // submit() funciona em elementos de formulário
        // Espera que a página de resultados carregue (verificando o título)
        wait.until(ExpectedConditions.titleContains(SEARCH_TERM));
        System.out.println("Página de resultados para '" + SEARCH_TERM + "' carregada.");
    }

    @Test
    void testElementExistenceUsingFindElement() {
        // Método 1: Usando findElement() e tratando a exceção
        try {
            // Tenta localizar um link que contenha a URL do site oficial
            // O seletor pode precisar de ajuste dependendo da estrutura dos resultados do
            // Google
            WebElement seleniumLink = driver
                    .findElement(By.xpath("//a[contains(@href, '" + EXPECTED_LINK_PARTIAL_HREF + "')]"));
            // Se o elemento for encontrado, o teste passa implicitamente aqui
            assertTrue(seleniumLink.isDisplayed(), "Link do Selenium encontrado, mas não está visível.");
            System.out
                    .println("Método 1: Link para '" + EXPECTED_LINK_PARTIAL_HREF + "' encontrado via findElement().");
        } catch (NoSuchElementException e) {
            // Se o elemento não for encontrado, NoSuchElementException é lançada
            // O JUnit Assertions.fail() força a falha do teste com uma mensagem
            fail("Método 1: Link para " + EXPECTED_LINK_PARTIAL_HREF + " não encontrado na página.", e);
        }
    }

    @Test
    void testElementExistenceUsingFindElements() {
        // Método 2: Usando findElements() e verificando o tamanho da lista
        // findElements() retorna uma lista vazia se nenhum elemento for encontrado, não
        // lança exceção
        List<WebElement> seleniumLinks = driver
                .findElements(By.xpath("//a[contains(@href, '" + EXPECTED_LINK_PARTIAL_HREF + "')]"));

        // Verifica se a lista de elementos encontrados não está vazia
        assertFalse(seleniumLinks.isEmpty(),
                "Método 2: Nenhum link para " + EXPECTED_LINK_PARTIAL_HREF + " foi encontrado (lista vazia).");

        // Opcional: Verificar se pelo menos um dos links encontrados está visível
        boolean isAnyLinkDisplayed = seleniumLinks.stream().anyMatch(WebElement::isDisplayed);
        assertTrue(isAnyLinkDisplayed,
                "Método 2: Link(s) para " + EXPECTED_LINK_PARTIAL_HREF + " encontrado(s), mas nenhum está visível.");

        System.out.println("Método 2: Pelo menos um link para " + EXPECTED_LINK_PARTIAL_HREF
                + " encontrado via findElements(). Tamanho da lista: " + seleniumLinks.size());
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
            System.out.println("Navegador fechado.");
        }
    }
}
