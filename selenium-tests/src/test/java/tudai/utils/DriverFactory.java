package tudai.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class DriverFactory {

    public static WebDriver getDriver() {
        System.setProperty("webdriver.edge.driver", "src/test/resources/msedgedriver.exe");
        return new EdgeDriver();
    }
}
