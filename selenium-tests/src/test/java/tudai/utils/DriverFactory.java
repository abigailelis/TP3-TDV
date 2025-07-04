package tudai.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class DriverFactory {

    public enum Navegador {
        EDGE, CHROME
    }

    public static WebDriver getDriver(Navegador navegador) {
        switch (navegador) {
            case CHROME:
                WebDriverManager.chromedriver().setup();
                return new ChromeDriver();
            case EDGE:
            default:
                WebDriverManager.edgedriver().setup();
                return new EdgeDriver();
        }
    }
}
