import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

/* No package declaration needed since the file is in the default package */
public class OpenCloseBrowser {

    public static void main(String[] args) {
        // Configura o WebDriverManager para gerir o ChromeDriver
        WebDriverManager.chromedriver().setup();

        // Configura opções do Chrome (opcional, mas útil)
        ChromeOptions options = new ChromeOptions();
        // options.addArguments("--headless"); // Para executar sem abrir a janela do
        // navegador
        options.addArguments("--start-maximized"); // Inicia maximizado

        // Instancia o WebDriver para o Chrome
        WebDriver driver = new ChromeDriver(options);

        System.out.println("Navegador Chrome aberto.");

        try {
            // Adiciona uma espera explícita de 5 segundos
            // Em testes reais, prefira esperas explícitas (WebDriverWait) em vez de
            // Thread.sleep
            System.out.println("Aguardando 5 segundos...");
            Thread.sleep(Duration.ofSeconds(5).toMillis());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restaura o status de interrupção
            System.err.println("Thread interrompida durante a espera: " + e.getMessage());
        }

        // Fecha o navegador (fecha a janela atual)
        // driver.close(); // Use close() se quiser fechar apenas a aba/janela atual

        // Fecha todas as janelas do navegador abertas pela instância do WebDriver e
        // encerra a sessão
        driver.quit();
        System.out.println("Navegador Chrome fechado.");
    }
}