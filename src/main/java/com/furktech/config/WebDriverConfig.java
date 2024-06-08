package com.furktech.config;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.furktech.utils.Utils;

import io.github.cdimascio.dotenv.Dotenv;

public class WebDriverConfig {

    static final Logger logger = LoggerFactory.getLogger(WebDriverConfig.class);
    static final Dotenv dotenv = Dotenv.load();

    /**
     * Configura o geckodriver e cria uma nova instância do FirefoxDriver
     * 
     * @param args
     * @return WebDriver (FirefoxDriver)
     */
    public static WebDriver startaDriver(String[] args) {

        configuraPathGeckoDriver(args);
        return instanciaFirefoxWebDriver(args[2]);
    }

    /**
     * Seta o path do Geckodriver. Caso o PATH tenha sido passado como argumento, o
     * PATH será setado com o caminho passado. Senão, buscará o PATH da váriavel
     * PATH_GECKODRIVER no arquivo .env, caso ela não exista, setará como
     * /snap/bin/geckodriver para Linux ou C:\\Program Files\\Geckodriver para
     * Windows
     * 
     * @param args Irá verificar se foi passado um quarto argumento (Caminho do
     *             Geckodriver)
     */
    private static void configuraPathGeckoDriver(String[] args) {

        String runningOS = Utils.getRunningOS();
        String defaultPath = "/snap/bin/geckodriver";

        if (runningOS.contains("win")) {
            defaultPath = "C:\\Program Files\\Geckodriver";
        }

        String geckoDriverPath;
        if (args.length > 3) {
            logger.info("Recebendo PATH do webdriver como argumento!. PATH Recebido: {}", args[3]);
            geckoDriverPath = args[3];
        } else {
            geckoDriverPath = dotenv.get("PATH_GECKODRIVER", defaultPath);
            logger.info("Buscando PATH default do geckodriver. PATH {}", geckoDriverPath);
        }
        System.setProperty("webdriver.gecko.driver", geckoDriverPath);
    }

    /**
     * Cria uma nova instância do FirefoxWebDriver. Caso o parâmetro modoHeadless
     * seja TRUE, irá criar a driver com a opção Headless sem interface gráfica.
     * 
     * @param modoHeadless
     * @return
     */
    private static WebDriver instanciaFirefoxWebDriver(String modoHeadless) {
        if (Boolean.parseBoolean(modoHeadless) || "TRUE".equalsIgnoreCase(modoHeadless)) {
            logger.info("Executando FIREFOX no modo HEADLESS.");

            FirefoxOptions options = new FirefoxOptions();
            options.addArguments("-headless");
            options.setLogLevel(FirefoxDriverLogLevel.TRACE);

            WebDriver webdriverHeadless = new FirefoxDriver(options);
            logger.info("Webdriver headless criado com sucesso!");
            return webdriverHeadless;
        } else {
            WebDriver webdriver = new FirefoxDriver();
            if (webdriver != null) {
                logger.info("Criou o webdriver com sucesso!");
            }
            return webdriver;
        }
    }

    /**
     * Cria um WebDriverWait para aguardar o elemento na tela por (timeout)
     * segundos, verifica a condição do elemento a cada (pollingDuration)
     * milissegundos.
     * 
     * @param webdriver       Instância do Selenium WebDriver.
     * @param timeout         Duração em segundos que aguardará o elemento em tela.
     * @param pollingDuration Intervalo de segundos em que verificará a condição do
     *                        elemento.
     * @return WebDriverWait
     */
    public static WebDriverWait configuraWait(WebDriver webdriver, Duration timeout, Duration pollingDuration) {

        Logger logger = LoggerFactory.getLogger(WebDriverConfig.class);

        logger.info("WebDriverWait configurado com timeout de {} segundos e pollingDuration de {} milissegundos",
                timeout, pollingDuration.toMillis());

        WebDriverWait wait = new WebDriverWait(webdriver, timeout, pollingDuration);
        return wait;
    }
}
