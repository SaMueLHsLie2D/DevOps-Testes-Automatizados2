# Projeto de Exercícios: DevOps – Testes Automatizados com Selenium

Este repositório contém as soluções para a lista de exercícios focada em testes automatizados de aplicações web utilizando Selenium WebDriver com Java e JUnit 5.

## Descrição

O projeto demonstra a implementação de testes automatizados cobrindo diferentes aspetos, desde a configuração inicial do WebDriver até a interação com elementos da página, verificações, navegação no histórico e captura de screenshots em caso de falha.

## Exercícios Implementados

1.  **Abrindo e Fechando um Navegador:** Configura o WebDriver, abre o Chrome, espera e fecha.
2.  **Navegação e Verificação de Título:** Navega até o Google e verifica se o título da página é "Google".
3.  **Localizando Elementos e Interagindo:** Localiza o campo de busca do Google, insere texto e clica no botão de pesquisa.
4.  **Verificando a Existência de Elementos:** Verifica se um link específico existe na página de resultados da busca.
5.  **Navegação Histórica:** Utiliza os comandos `back()`, `forward()` e `refresh()` do navegador.
6.  **Capturando Screenshots:** Implementa a captura de tela automaticamente quando um teste falha usando JUnit 5 `TestWatcher`.

## Estrutura do Projeto

O projeto segue a estrutura padrão do Maven:

```
DevOps-Testes-Automatizados2
├── pom.xml                   # Ficheiro de configuração do Maven
├── README.md                 # Este ficheiro
├── src
│   └── test
│       └── java
│           └── com
│               └── una
│                   └── selenium
│                       └── tests
│                           ├── exercise1
│                           │   └── OpenCloseBrowserTest.java
│                           ├── exercise2
│                           │   └── NavigationTitleTest.java
│                           ├── exercise3
│                           │   └── FindAndInteractTest.java
│                           ├── exercise4
│                           │   └── ElementExistenceTest.java
│                           ├── exercise5
│                           │   └── NavigationHistoryTest.java
│                           └── exercise6
│                               └── ScreenshotOnFailureTest.java
└── target/                     # Diretório de output do build (gerado pelo Maven)
    └── screenshots/            # Screenshots de testes falhados
```

## Pré-requisitos

*   **Java Development Kit (JDK):** Versão 21 ou superior.
*   **Apache Maven:** Para gestão de dependências e execução dos testes.
*   **Google Chrome:** Navegador web.
*   **ChromeDriver:** O WebDriver correspondente à sua versão do Google Chrome. O `WebDriverManager` incluído no `pom.xml` tentará fazer o download e configurar automaticamente.

## Configuração

1.  **Clone o Repositório:**
    ```bash
    git clone <URL_DO_REPOSITORIO>
    cd DevOps-Testes-Automatizados2
    ```
2.  **Verifique o JDK e Maven:** Certifique-se de que o JDK 21+ e o Maven estão instalados e configurados nas variáveis de ambiente do seu sistema.
    ```bash
    java -version
    mvn -version
    ```
3.  **WebDriver:** O `WebDriverManager` deve cuidar do download do ChromeDriver. Se encontrar problemas, pode baixar manualmente o ChromeDriver [aqui](https://googlechromelabs.github.io/chrome-for-testing/) e garantir que ele esteja no `PATH` do sistema ou especificar o caminho para ele no código (menos recomendado).

## Como Executar os Testes

Use o Maven para compilar o projeto e executar os testes:

```bash
# Limpa o projeto e executa todos os testes
mvn clean test
```

Os resultados dos testes serão exibidos no console. Em caso de falhas no `ScreenshotOnFailureTest`, as screenshots serão salvas no diretório `target/screenshots/`.

## Ferramentas e Tecnologias

*   **Linguagem:** Java 21
*   **Automação:** Selenium WebDriver 4.21.0
*   **Framework de Teste:** JUnit 5.10.2
*   **Gestão de Dependências/Build:** Apache Maven
*   **Gestão de WebDriver:** WebDriverManager 5.8.0
*   **Utilitários:** Apache Commons IO 2.16.1 (para manipulação de ficheiros nas screenshots)

